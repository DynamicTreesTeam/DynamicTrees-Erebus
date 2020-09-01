package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.ModContent;
import erebus.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class TreeBalsam extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_balsam");
    public static final Block primitiveLogResinless = Block.getBlockFromName("erebus:log_balsam_resinless");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_balsam");

    public static final class SpeciesBalsam extends Species {

        public SpeciesBalsam(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.balsamLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.4F, 18.0F, 5, 5, 1.0F);

            // Add resin drop creator.
            this.addDropCreator(new DropCreator(new ResourceLocation(DynamicTreesErebus.MODID, "resin")) {
                @Override
                public List<ItemStack> getLogsDrop(World world, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, float volume) {
                    if (volume >= 1) dropList.add(new ItemStack(Item.getByNameOrId("erebus:materials"), getRandomNumber((int) volume, (int) (volume) * 3), 40));
                    return dropList;
                }
            });

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }

        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
            for (EnumFacing dir : EnumFacing.VALUES) probMap[dir.getIndex()] = 0; // Reset values.

            final int currentHeight = pos.getY() - signal.rootPos.getY(); // Get height up tree.
            probMap[EnumFacing.DOWN.getIndex()] = 0; // Disallow growing downwards.

            // Make chance of branching off if at the right height.
            if (currentHeight >= this.lowestBranchHeight) for (EnumFacing dir : EnumFacing.HORIZONTALS) probMap[dir.getIndex()] = (getRandomNumber(1, 100) == 1) ? (int) signal.energy * 2 : 0;

            // Allow branching off an extra block.
            if (!signal.isInTrunk()) if (signal.numTurns == 1 && signal.delta.getX() < 2 && signal.delta.getZ() < 2) probMap[signal.dir.getIndex()] = (int) signal.energy * 3;

            probMap[signal.dir.getOpposite().getIndex()] = 0; // Disable the direction we came from.

            return probMap;
        }

        private int getRandomNumber (int min, int max) {
            return new Random().nextInt((max + 1) - min) + min;
        }

    }

    public TreeBalsam() {
        super(new ResourceLocation(DynamicTreesErebus.MODID, "balsam"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        ModContent.balsamLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesBalsam(this));
    }

    @Override
    public boolean autoCreateBranch() {
        return true;
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(primitiveLogResinless, qty, 0);
    }

}
