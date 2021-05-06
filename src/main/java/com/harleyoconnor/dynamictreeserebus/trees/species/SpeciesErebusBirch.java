package com.harleyoconnor.dynamictreeserebus.trees.species;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
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
public final class SpeciesErebusBirch extends Species {

    private static final String SPECIES_NAME = "erebusbirch";

    public SpeciesErebusBirch(TreeFamily treeFamily, ILeavesProperties leavesProperties) {
        super(new ResourceLocation(AddonConstants.MOD_ID, SPECIES_NAME), treeFamily, leavesProperties);

        // Setup default Birch species properties.

        //Birch are tall, skinny, fast growing trees
        setBasicGrowingParameters(0.1f, 14.0f, 4, 4, 1.25f);

        envFactor(BiomeDictionary.Type.COLD, 0.75f);
        envFactor(BiomeDictionary.Type.HOT, 0.50f);
        envFactor(BiomeDictionary.Type.DRY, 0.50f);
        envFactor(BiomeDictionary.Type.FOREST, 1.05f);

        setupStandardSeedDropping();
    }

    @Override
    public boolean showSpeciesOnWaila() {
        return false;
    }

    @Override
    public boolean isBiomePerfect(Biome biome) {
        return isOneOfBiomes(biome, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS);
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
                world.setBlockState(pos, Blocks.BROWN_MUSHROOM.getDefaultState());//Change branch to a brown mushroom
                world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 3);//Change rooty dirt to dirt
            }
            return true;
        }

        return false;
    }

}
