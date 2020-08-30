package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenVine;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.ModContent;
import erebus.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

public class TreeEucalyptus extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_eucalyptus");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_eucalyptus");

    public static final class SpeciesEucalyptus extends Species {

        public SpeciesEucalyptus(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.eucalyptusLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.4F, 18.0F, 5, 7, 1.0F);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }
        
    }

    public TreeEucalyptus() {
        super(new ResourceLocation(DynamicTreesErebus.MODID, "eucalyptus"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        ModContent.eucalyptusLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesEucalyptus(this));
    }

    @Override
    public boolean autoCreateBranch() {
        return true;
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(primitiveLog, qty, 0);
    }

}
