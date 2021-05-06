package com.harleyoconnor.dynamictreeserebus.trees.species;

import com.ferreusveritas.dynamictrees.ModBlocks;
import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorApple;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

/**
 * @author Harley O'Connor
 */
public final class SpeciesErebusOak extends Species {

    private static final String SPECIES_NAME = "erebusoak";

    public SpeciesErebusOak(TreeFamily treeFamily, ILeavesProperties leavesProperties) {
        super(new ResourceLocation(AddonConstants.MOD_ID, SPECIES_NAME), treeFamily, leavesProperties);

        // Setup default Oak species properties.

        //Oak trees are about as average as you can get
        setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 0.8f);

        envFactor(BiomeDictionary.Type.COLD, 0.75f);
        envFactor(BiomeDictionary.Type.HOT, 0.50f);
        envFactor(BiomeDictionary.Type.DRY, 0.50f);
        envFactor(BiomeDictionary.Type.FOREST, 1.05f);

        if(ModConfigs.worldGen && !ModConfigs.enableAppleTrees) {//If we've disabled apple trees we still need some way to get apples.
            addDropCreator(new DropCreatorApple());
        }

        setupStandardSeedDropping();
    }

    @Override
    public boolean showSpeciesOnWaila() {
        return false;
    }

    @Override
    public boolean isBiomePerfect(Biome biome) {
        return isOneOfBiomes(biome, Biomes.FOREST, Biomes.FOREST_HILLS);
    }

    public ItemStack getSeedStack(int qty) {
        return this.treeFamily.getCommonSpecies().getSeedStack(qty);
    }

    public Seed getSeed() {
        return this.treeFamily.getCommonSpecies().getSeed();
    }

    @Override
    public boolean rot(World world, BlockPos pos, int neighborCount, int radius, Random random, boolean rapid) {
        if(super.rot(world, pos, neighborCount, radius, random, rapid)) {
            if(radius > 4 && TreeHelper.isRooty(world.getBlockState(pos.down())) && world.getLightFor(EnumSkyBlock.SKY, pos) < 4) {
                world.setBlockState(pos, random.nextInt(3) == 0 ? ModBlocks.blockStates.redMushroom : ModBlocks.blockStates.brownMushroom);//Change branch to a mushroom
                world.setBlockState(pos.down(), ModBlocks.blockStates.podzol);//Change rooty dirt to Podzol
            }
            return true;
        }

        return false;
    }

}
