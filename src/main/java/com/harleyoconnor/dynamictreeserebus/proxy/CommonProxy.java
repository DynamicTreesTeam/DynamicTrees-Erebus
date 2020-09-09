package com.harleyoconnor.dynamictreeserebus.proxy;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.growth.CustomCellKits;
import erebus.ModBiomes;
import erebus.blocks.EnumWood;
import erebus.world.biomes.BiomeUndergroundJungle;
import erebus.world.biomes.decorators.BiomeDecoratorBaseErebus;
import erebus.world.feature.tree.WorldGenErebusHugeTree;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.lang.reflect.Field;
import java.util.Random;

public class CommonProxy {

	/*private static class BlankWorldGenerator extends WorldGenErebusHugeTree {

		public BlankWorldGenerator(boolean notify, boolean genThorns, EnumWood wood) {
			super(notify, genThorns, wood);
		}

		@Override
		public boolean func_180709_b(World world, Random rand, BlockPos pos) {
			return true;
		}

		@Override
		public void prepare(int baseHeight) {
		}
	};

	private final WorldGenerator blankGenerator = new BlankWorldGenerator(true, true, EnumWood.MAHOGANY);

	private final WorldGenMegaJungle megaJungle = new WorldGenMegaJungle(false, 0, 0, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState()) {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			return super.generate(worldIn, rand, position);
		}
	};*/
	
	public void preInit() {
		// Initialise custom cell kits.
		new CustomCellKits();
	}
	
	public void init() {
		// Register sapling replacements.
		registerSaplingReplacement("asper");
		registerSaplingReplacement("balsam");
		registerSaplingReplacement("cypress");
		registerSaplingReplacement("eucalyptus");
		registerSaplingReplacement("mahogany");
		registerSaplingReplacement("mossbark");

		/*try {
			BiomeDecoratorBaseErebus biomeDecorator = null;

			for (final Biome biome : ModBiomes.BIOME_LIST) {
				if (!(biome instanceof BiomeUndergroundJungle)) continue;

				final Field field = biome.getClass().getSuperclass().getDeclaredField("decorator");
				field.setAccessible(true);
				biomeDecorator = (BiomeDecoratorBaseErebus) field.get(biome);
			}

			if (biomeDecorator != null) {
				this.setPrivateField(biomeDecorator, "genTreeMahogany", this.blankGenerator);
				this.setPrivateField(biomeDecorator, "genTreeMahoganyLarge", this.blankGenerator);
				this.setPrivateField(biomeDecorator, "genTreeJungle", this.blankGenerator);
				this.setPrivateField(biomeDecorator, "genTreeJungleLarge", this.blankGenerator);
				this.setPrivateField(biomeDecorator, "genTreeMossbark", this.blankGenerator);
				this.setPrivateField(biomeDecorator, "genTreeAsper", this.blankGenerator);
				this.setPrivateField(biomeDecorator, "genTreeJungleTall", this.blankGenerator);
				this.setPrivateField(biomeDecorator, "genTreeEucalyptus", this.blankGenerator);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	private static void registerSaplingReplacement(final String speciesName) {
		TreeRegistry.registerSaplingReplacer(Block.getBlockFromName("erebus:sapling_" + speciesName).getDefaultState(), TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesErebus.MODID, speciesName)));
	}

	private void setPrivateField (final Object classInstance, final String fieldName, final Object newValue) throws Exception {
		final Field field = classInstance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(classInstance, newValue);
	}

	public void postInit() {
	}

}
