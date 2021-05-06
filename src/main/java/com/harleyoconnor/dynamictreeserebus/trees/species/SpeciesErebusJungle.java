package com.harleyoconnor.dynamictreeserebus.trees.species;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenCocoa;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenUndergrowth;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenVine;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import com.harleyoconnor.dynamictreeserebus.AddonContent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

/**
 * @author Harley O'Connor
 */
public final class SpeciesErebusJungle extends Species {

    private static final String SPECIES_NAME = "erebusjungle";

    public SpeciesErebusJungle (TreeFamily treeFamily, ILeavesProperties leavesProperties) {
        super(new ResourceLocation(AddonConstants.MOD_ID, SPECIES_NAME), treeFamily, leavesProperties);

        // Setup default Jungle species properties.
        this.setBasicGrowingParameters(0.2F, 28.0F, 3, 2, 1.0F);
        this.setGrowthLogicKit(TreeRegistry.findGrowthLogicKit("jungle"));

        this.envFactor(BiomeDictionary.Type.COLD, 0.15F);
        this.envFactor(BiomeDictionary.Type.DRY, 0.2F);
        this.envFactor(BiomeDictionary.Type.HOT, 1.1F);
        this.envFactor(BiomeDictionary.Type.WET, 1.1F);

        this.setupStandardSeedDropping();

        this.addGenFeature(new FeatureGenCocoa());
        this.addGenFeature((new FeatureGenVine()).setQuantity(16).setMaxLength(16));
        this.addGenFeature(new FeatureGenUndergrowth());
    }

    @Override
    public boolean showSpeciesOnWaila() {
        return false;
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

    public Species getMegaSpecies() {
        return AddonContent.erebusMegaJungle;
    }

}
