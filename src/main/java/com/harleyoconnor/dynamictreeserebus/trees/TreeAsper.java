package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.ModContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TreeAsper extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_asper");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_asper");

    public static final class SpeciesAsper extends Species {

        public SpeciesAsper(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.asperLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.1f, 10.0f, 1, 1, 0.4f);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }

    }

    public TreeAsper() {
        super(new ResourceLocation(DynamicTreesErebus.MODID, "asper"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        ModContent.asperLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesAsper(this));
    }

    @Override
    public boolean autoCreateBranch() {
        return true;
    }

}
