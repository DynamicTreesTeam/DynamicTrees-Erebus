package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.ModContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class TreeAsper extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_asper");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_asper");

    public static final class SpeciesAsper extends Species {

        public SpeciesAsper(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.asperLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.1f, 10.0f, 1, 1, 0.4f);

            // Add extra Asper logs to drops, otherwise it only drops sticks.
            this.addDropCreator(new DropCreator(new ResourceLocation(DynamicTreesErebus.MODID, "extraasper")) {
                public List<ItemStack> getLogsDrop(World world, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, float volume) {
                    volume *= 6;
                    dropList.add(new ItemStack(primitiveLog, getRandomNumber(1, (int) (volume <= 1 ? 1 : volume) * 2), 0));
                    return dropList;
                }
            });

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }

        private int getRandomNumber (int min, int max) {
            return new Random().nextInt((max + 1) - min) + min;
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
