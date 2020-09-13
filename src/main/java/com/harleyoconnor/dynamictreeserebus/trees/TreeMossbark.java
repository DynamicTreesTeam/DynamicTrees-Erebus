package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.ModContent;
import com.harleyoconnor.dynamictreeserebus.util.NumberUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

public final class TreeMossbark extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_mossbark");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_mossbark");

    public static final class SpeciesMossbark extends Species {

        public SpeciesMossbark(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.mossbarkLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.3f, 14.0f, 4, 4, 0.9f);

            // Set environment factors.
            this.envFactor(BiomeDictionary.Type.COLD, 1.2F);
            this.envFactor(BiomeDictionary.Type.DRY, 0.1F);
            this.envFactor(BiomeDictionary.Type.HOT, 0.3F);
            this.envFactor(BiomeDictionary.Type.WET, 1.3F);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }

        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
            for (EnumFacing dir : EnumFacing.VALUES) probMap[dir.getIndex()] = 0; // Reset values.

            // TODO: Find a way to store the height at which it branches off at, potentially using the rooty dirt tile entity.

            final int signalHeight = (pos.getY() - signal.rootPos.getY()); // Get height of signal.

            // Make chance of branching off if at the right height.
            if (signalHeight > this.lowestBranchHeight && signal.isInTrunk()) this.incrementHorizontals(probMap, (NumberUtils.getRandomIntBetween(1, 15 / (int) signal.energy) == 1) ? 4 : 0);

            // Allow chance of growing outwards once away from the trunk.
            if (!signal.isInTrunk() && !(world.getBlockState(pos.up()).getBlock() instanceof BlockBranch) && isInHorizontalRange(pos, signal.rootPos, 4))
                probMap[getRelativeFace(pos, signal.rootPos).getIndex()] = NumberUtils.getRandomIntBetween(1, Math.max((int) Math.ceil(6 / signal.energy), 1)) == 1 ? 6 : 0;

            probMap[signal.dir.getOpposite().getIndex()] = 0; // Disable the direction we came from.
            probMap[EnumFacing.DOWN.getIndex()] = 0; // Disallow growing downwards.
            // Disallow growing up in trunk so it spreads out instead.
            probMap[EnumFacing.UP.getIndex()] = signal.isInTrunk() || world.getBlockState(pos.offset(getRelativeFace(pos, signal.rootPos))).getBlock() instanceof BlockBranch ? 0 : 5;

            return probMap;
        }

        private int[] incrementHorizontals (final int[] probMap, final int amount) {
            for (EnumFacing dir : EnumFacing.HORIZONTALS) probMap[dir.getIndex()] += amount;
            return probMap;
        }

        private EnumFacing getRelativeFace (final BlockPos signalPos, final BlockPos rootPos) {
            if (signalPos.getZ() < rootPos.getZ()) return EnumFacing.NORTH;
            else if (signalPos.getZ() > rootPos.getZ()) return EnumFacing.SOUTH;
            else if (signalPos.getX() > rootPos.getX()) return EnumFacing.EAST;
            else if (signalPos.getX() < rootPos.getX()) return EnumFacing.WEST;
            else return EnumFacing.UP;
        }

        private boolean isInHorizontalRange (final BlockPos firstPos, final BlockPos secondPos, final int range) {
            return (firstPos.getX() - secondPos.getX() < range && firstPos.getX() - secondPos.getX() > -range) && (firstPos.getZ() - secondPos.getZ() < range && firstPos.getZ() - secondPos.getZ() > -range);
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
