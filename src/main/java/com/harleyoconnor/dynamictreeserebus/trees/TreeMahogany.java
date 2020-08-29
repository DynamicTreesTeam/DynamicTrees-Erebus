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

public class TreeMahogany extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_mahogany");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_mahogany");

    public static final class SpeciesMahogany extends Species {

        public SpeciesMahogany(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.mahoganyLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.25F, 24.0F, 7, 5, 0.9F);

            this.setGrowthLogicKit(TreeRegistry.findGrowthLogicKit("jungle"));

            // Setup environment factors.
            this.envFactor(BiomeDictionary.Type.COLD, 0.15F);
            this.envFactor(BiomeDictionary.Type.DRY, 0.2F);
            this.envFactor(BiomeDictionary.Type.HOT, 1.1F);
            this.envFactor(BiomeDictionary.Type.WET, 1.1F);

            this.setSoilLongevity(16);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();

            // Add thorns generation.
            this.addGenFeature(new FeatureGenVine().setVineBlock(ModBlocks.THORNS).setQuantity(16).setMaxLength(16));
        }

        @Override
        public boolean isThick() {
            return true;
        }

        @Override
        public int maxBranchRadius() {
            return 16;
        }

    }

    public TreeMahogany() {
        super(new ResourceLocation(DynamicTreesErebus.MODID, "mahogany"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        ModContent.mahoganyLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesMahogany(this));
    }

    @Override
    public boolean isThick() {
        return true;
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
