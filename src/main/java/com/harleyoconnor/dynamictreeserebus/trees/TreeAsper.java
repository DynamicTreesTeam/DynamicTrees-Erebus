package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenRoots;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import com.harleyoconnor.dynamictreeserebus.AddonContent;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.util.NumberUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * Asper tree class.
 *
 * @author Harley O'Connor
 */
public final class TreeAsper extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_asper");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_asper");

    public static final class SpeciesAsper extends Species {

        private final int minTrunkRadiusForRoots = 2;

        public SpeciesAsper(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, AddonContent.asperLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.6f, 6.0f, 1, 3, 0.6f);

            // Add extra Asper logs to drops, otherwise it only drops sticks.
            this.addDropCreator(new DropCreator(new ResourceLocation(AddonConstants.MOD_ID, "extraasper")) {
                @Override
                public List<ItemStack> getLogsDrop(World world, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, float volume) {
                    volume *= 6;
                    dropList.add(new ItemStack(primitiveLog, NumberUtils.getRandomIntBetween(1, (int) (volume <= 1 ? 1 : volume) * 2), 0));
                    return dropList;
                }
            });

            // Set environment factors.
            this.envFactor(BiomeDictionary.Type.COLD, 0.8F);
            this.envFactor(BiomeDictionary.Type.DRY, 0.6F);
            this.envFactor(BiomeDictionary.Type.HOT, 1.3F);
            this.envFactor(BiomeDictionary.Type.WET, 0.9F);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();

            // Add roots generation.
            this.addGenFeature(new FeatureGenRoots(this.minTrunkRadiusForRoots).setScaler(getRootScaler()));
        }

        protected BiFunction<Integer, Integer, Integer> getRootScaler() {
            return (inRadius, trunkRadius) -> {
                float scale = MathHelper.clamp(trunkRadius >= this.minTrunkRadiusForRoots ? (trunkRadius / 10f) : 0, 0, 1);
                return (int) (inRadius * scale);
            };
        }

        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
            probMap = super.customDirectionManipulation(world, pos, radius, signal, probMap);

            final int signalHeight = (pos.getY() - signal.rootPos.getY()); // Get height of signal.

            // Allow growing outwards at height of 3 or rarely at a height of 2.
            if ((signalHeight == 3 || (signalHeight == 2 && NumberUtils.getRandomIntBetween(1, 100000) == 1)) && signal.isInTrunk())
                for (EnumFacing dir : EnumFacing.HORIZONTALS) probMap[dir.getIndex()] = (int) signal.energy * 2;
            else for (EnumFacing dir : EnumFacing.HORIZONTALS) probMap[dir.getIndex()] = 0;

            // Disallow growing upwards if the height is greater than 5 or the signal is in a branch.
            probMap[EnumFacing.UP.getIndex()] = signalHeight > 5 || !signal.isInTrunk() ? 0 : 5;
            probMap[signal.dir.getOpposite().ordinal()] = 0; // Disable the direction we came from.

            return probMap;
        }

    }

    public TreeAsper() {
        super(new ResourceLocation(AddonConstants.MOD_ID, "asper"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        AddonContent.asperLeavesProperties.setTree(this);

        AddonContent.asperRoot = new BlockSurfaceRoot(Material.WOOD, this.getName() + "root");

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesAsper(this));
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(primitiveLog, qty, 0);
    }

    @Override
    public BlockSurfaceRoot getSurfaceRoots() {
        return AddonContent.asperRoot;
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(AddonContent.asperRoot);
        return super.getRegisterableBlocks(blockList);
    }

}
