package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.ModContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TreeMossbark extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_mossbark");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_mossbark");

    public static final class SpeciesMossbark extends Species {

        public SpeciesMossbark(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.mossbarkLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.3f, 14.0f, 4, 7, 0.9f);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }

    }

    public TreeMossbark() {
        super(new ResourceLocation(DynamicTreesErebus.MODID, "mossbark"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        ModContent.mossbarkLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesMossbark(this));
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
