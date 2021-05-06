package com.harleyoconnor.dynamictreeserebus.trees.species;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.systems.featuregen.*;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.function.BiFunction;

/**
 * @author Harley O'Connor
 */
public final class SpeciesErebusMegaJungle extends Species {

    private static final String SPECIES_NAME = "erebusmegajungle";

    public SpeciesErebusMegaJungle(TreeFamily treeFamily, ILeavesProperties leavesProperties) {
        super(new ResourceLocation(AddonConstants.MOD_ID, SPECIES_NAME), treeFamily, leavesProperties);

        // Setup default Mega Jungle species properties.
        this.setRequiresTileEntity(true);

        this.setBasicGrowingParameters(0.32F, 32.0F, 7, 8, 0.9F);
        this.setGrowthLogicKit(TreeRegistry.findGrowthLogicKit("jungle"));

        this.envFactor(BiomeDictionary.Type.COLD, 0.15F);
        this.envFactor(BiomeDictionary.Type.DRY, 0.2F);
        this.envFactor(BiomeDictionary.Type.HOT, 1.1F);
        this.envFactor(BiomeDictionary.Type.WET, 1.1F);

        this.setSoilLongevity(16);

        this.addGenFeature((new FeatureGenVine()).setQuantity(16).setMaxLength(16));
        this.addGenFeature(new FeatureGenFlareBottom());
        this.addGenFeature(new FeatureGenClearVolume(8));
        this.addGenFeature(new FeatureGenMound(999));
        this.addGenFeature((new FeatureGenRoots(9)).setScaler(this.getRootScaler()));
    }

    protected BiFunction<Integer, Integer, Integer> getRootScaler() {
        return (inRadius, trunkRadius) -> {
            float scale = MathHelper.clamp(trunkRadius >= 9 ? (float)trunkRadius / 18.0F : 0.0F, 0.0F, 1.0F);
            return (int)((float)inRadius * scale);
        };
    }

    public boolean isBiomePerfect(Biome biome) {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE);
    }

    public ItemStack getSeedStack(int qty) {
        return this.treeFamily.getCommonSpecies().getSeedStack(qty);
    }

    public Seed getSeed() {
        return this.treeFamily.getCommonSpecies().getSeed();
    }

    public int maxBranchRadius() {
        return 24;
    }

    public boolean isThick() {
        return true;
    }

    public boolean isMega() {
        return true;
    }

}
