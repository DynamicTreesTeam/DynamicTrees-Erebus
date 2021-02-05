package com.harleyoconnor.dynamictreeserebus;

import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.ModRecipes;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.items.DendroPotion.DendroPotionType;
import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.trees.*;
import com.harleyoconnor.dynamictreeserebus.worldgen.BiomeDataBasePopulator;
import erebus.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Class to manage addon content.
 *
 * @author Harley O'Connor
 */
@Mod.EventBusSubscriber(modid = AddonConstants.MOD_ID)
@ObjectHolder(AddonConstants.MOD_ID)
public final class AddonContent {

	public static BlockSurfaceRoot asperRoot;

	public static ILeavesProperties asperLeavesProperties, mossbarkLeavesProperties, cypressLeavesProperties, mahoganyLeavesProperties, eucalyptusLeavesProperties, balsamLeavesProperties;

	public static ArrayList<TreeFamily> trees = new ArrayList<>();

	@SubscribeEvent
	public static void registerDataBasePopulators(final BiomeDataBasePopulatorRegistryEvent event) {
		event.register(new BiomeDataBasePopulator());
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		asperLeavesProperties = setUpLeaves(Objects.requireNonNull(TreeAsper.primitiveLeaves), "dynamictreeserebus:asper");
		mossbarkLeavesProperties = setUpLeaves(Objects.requireNonNull(TreeMossbark.primitiveLeaves), "dynamictreeserebus:mossbark");
		cypressLeavesProperties = setUpLeaves(Objects.requireNonNull(TreeCypress.primitiveLeaves), "dynamictreeserebus:cypress");
		mahoganyLeavesProperties = setUpLeaves(Objects.requireNonNull(TreeMahogany.primitiveLeaves), "deciduous");
		eucalyptusLeavesProperties = setUpLeaves(Objects.requireNonNull(TreeEucalyptus.primitiveLeaves), "acacia");
		balsamLeavesProperties = setUpLeaves(Objects.requireNonNull(TreeBalsam.primitiveLeaves), "acacia");

		LeavesPaging.getLeavesBlockForSequence(AddonConstants.MOD_ID, 0, asperLeavesProperties);
		LeavesPaging.getLeavesBlockForSequence(AddonConstants.MOD_ID, 1, mossbarkLeavesProperties);
		LeavesPaging.getLeavesBlockForSequence(AddonConstants.MOD_ID, 2, cypressLeavesProperties);
		LeavesPaging.getLeavesBlockForSequence(AddonConstants.MOD_ID, 3, mahoganyLeavesProperties);
		LeavesPaging.getLeavesBlockForSequence(AddonConstants.MOD_ID, 4, eucalyptusLeavesProperties);
		LeavesPaging.getLeavesBlockForSequence(AddonConstants.MOD_ID, 5, balsamLeavesProperties);

		Collections.addAll(trees, new TreeAsper(), new TreeMossbark(), new TreeCypress(), new TreeMahogany(), new TreeEucalyptus(), new TreeBalsam());

		trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
		ArrayList<Block> treeBlocks = new ArrayList<>();
		trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
		treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(AddonConstants.MOD_ID).values());
		registry.registerAll(treeBlocks.toArray(new Block[0]));

		DirtHelper.registerSoil(ModBlocks.MUD, DirtHelper.MUDLIKE);
	}

	public static ILeavesProperties setUpLeaves (final Block leavesBlock, final String cellKit){
		return new LeavesProperties(
				leavesBlock.getDefaultState(),
				new ItemStack(leavesBlock, 1, 0),
				TreeRegistry.findCellKit(cellKit))
		{
			@Override public ItemStack getPrimitiveLeavesItemStack() {
				return new ItemStack(leavesBlock);
			}

			@Override
			public int getLightRequirement() {
				return 0;
			}
		};
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		ArrayList<Item> treeItems = new ArrayList<>();
		trees.forEach(tree -> tree.getRegisterableItems(treeItems));
		registry.registerAll(treeItems.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
		AddonConstants.EREBUS_TREES.forEach(AddonContent::setUpSeedRecipes);
	}

	public static void setUpSeedRecipes(final String name) {
		Species treeSpecies = TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, name));
		ItemStack treeSeed = treeSpecies.getSeedStack(1);
		ItemStack treeTransformationPotion = ModItems.dendroPotion.setTargetTree(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSpecies.getFamily());
		BrewingRecipeRegistry.addRecipe(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSeed, treeTransformationPotion);
		ModRecipes.createDirtBucketExchangeRecipes(new ItemStack(Objects.requireNonNull(Block.getBlockFromName(AddonConstants.EREBUS_MOD_ID + ":sapling_" + name))), treeSeed, true);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(final ModelRegistryEvent event) {
		for (TreeFamily tree : trees) {
			ModelHelper.regModel(tree.getDynamicBranch());
			ModelHelper.regModel(tree.getCommonSpecies().getSeed());
			ModelHelper.regModel(tree);
		}
		LeavesPaging.getLeavesMapForModId(AddonConstants.MOD_ID).forEach((key, leaves) -> ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build()));
	}
}
