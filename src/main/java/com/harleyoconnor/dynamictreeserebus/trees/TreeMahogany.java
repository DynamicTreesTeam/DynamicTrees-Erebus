package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.systems.featuregen.*;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.trees.TreeJungle;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.ModContent;
import erebus.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.function.BiFunction;

public final class TreeMahogany extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_mahogany");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_mahogany");

    public static final class SpeciesMahogany extends Species {

        public SpeciesMahogany(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.mahoganyLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.25F, 24.0F, 7, 5, 0.9F);

            // Give it the jungle growth logic.
            this.setGrowthLogicKit(TreeRegistry.findGrowthLogicKit("jungle"));

            // Set environment factors.
            this.envFactor(BiomeDictionary.Type.COLD, 0.15F);
            this.envFactor(BiomeDictionary.Type.DRY, 0.2F);
            this.envFactor(BiomeDictionary.Type.HOT, 1.2F);
            this.envFactor(BiomeDictionary.Type.WET, 1.2F);

            // Set soil to last longer.
            this.setSoilLongevity(16);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();

            // Add thorns generation.
            this.addGenFeature(new FeatureGenVine().setVineBlock(ModBlocks.THORNS).setQuantity(16).setMaxLength(16));
            this.addGenFeature(new FeatureGenUndergrowth());
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE);
        }

        @Override
        public int maxBranchRadius() {
            return 16;
        }

        @Override
        public Species getMegaSpecies() {
            return ((TreeMahogany) this.treeFamily).megaSpecies;
        }

    }

    // TODO: Doesn't currently work with gigas potion...?
    public static final class SpeciesMegaMahogany extends Species {

        public SpeciesMegaMahogany(TreeFamily treeFamily) {
            super(new ResourceLocation(DynamicTreesErebus.MODID, "mega" + treeFamily.getName().getResourcePath()), treeFamily, ModContent.mahoganyLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.32F, 32.0F, 7, 8, 0.9F);

            // Give it the jungle growth logic.
            this.setGrowthLogicKit(TreeRegistry.findGrowthLogicKit("jungle"));

            // Set environment factors.
            this.envFactor(BiomeDictionary.Type.COLD, 0.15F);
            this.envFactor(BiomeDictionary.Type.DRY, 0.2F);
            this.envFactor(BiomeDictionary.Type.HOT, 1.1F);
            this.envFactor(BiomeDictionary.Type.WET, 1.1F);

            // Set soil to last longer.
            this.setSoilLongevity(16);

            // Add extra generation features.
            this.addGenFeature(new FeatureGenVine().setVineBlock(ModBlocks.THORNS).setQuantity(16).setMaxLength(16));
            this.addGenFeature(new FeatureGenFlareBottom());
            this.addGenFeature(new FeatureGenClearVolume(8));
            this.addGenFeature(new FeatureGenMound(999));
            this.addGenFeature((new FeatureGenRoots(9)).setScaler(this.getRootScaler()));
        }

        protected BiFunction<Integer, Integer, Integer> getRootScaler() {
            return (inRadius, trunkRadius) -> {
                float scale = MathHelper.clamp(trunkRadius >= 9 ? (trunkRadius / 18f) : 0, 0, 1);
                return (int) (inRadius * scale);
            };
        }

        @Override
        public int maxBranchRadius() {
            return 24;
        }

        @Override
        public boolean isThick() {
            return true;
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE);
        }

        @Override
        public boolean isMega() {
            return true;
        }

        @Override
        public ItemStack getSeedStack(int qty) {
            return this.treeFamily.getCommonSpecies().getSeedStack(qty);
        }

        @Override
        public Seed getSeed() {
            return this.treeFamily.getCommonSpecies().getSeed();
        }

    }

    Species megaSpecies;
    BlockSurfaceRoot surfaceRootBlock;

    public TreeMahogany() {
        super(new ResourceLocation(DynamicTreesErebus.MODID, "mahogany"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        ModContent.mahoganyLeavesProperties.setTree(this);

        // Set surface root.
        surfaceRootBlock = new BlockSurfaceRoot(Material.WOOD, getName() + "root");

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        megaSpecies = new SpeciesMegaMahogany(this);
        this.setCommonSpecies(new SpeciesMahogany(this));
    }

    @Override
    public void registerSpecies(IForgeRegistry<Species> speciesRegistry) {
        super.registerSpecies(speciesRegistry);
        speciesRegistry.register(megaSpecies);
    }

    @Override
    public boolean isThick() {
        return true;
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList = super.getRegisterableBlocks(blockList);
        blockList.add(surfaceRootBlock);
        return blockList;
    }

    @Override
    public BlockSurfaceRoot getSurfaceRoots() {
        return surfaceRootBlock;
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(primitiveLog, qty, 0);
    }

}
