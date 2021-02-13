package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import erebus.Erebus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

/**
 * @author Harley O'Connor
 */
public final class SpeciesErebusAcacia extends Species {

    private static final String SPECIES_NAME = "erebusacacia";

    public SpeciesErebusAcacia(TreeFamily treeFamily, ILeavesProperties leavesProperties) {
        super(new ResourceLocation(AddonConstants.MOD_ID, SPECIES_NAME), treeFamily, leavesProperties);

        // Setup default Acacia species properties.

        //Acacia Trees are short, very slowly growing trees
        setBasicGrowingParameters(0.15f, 12.0f, 0, 3, 0.7f);

        envFactor(BiomeDictionary.Type.COLD, 0.25f);
        envFactor(BiomeDictionary.Type.NETHER, 0.75f);
        envFactor(BiomeDictionary.Type.WET, 0.75f);

        setupStandardSeedDropping();
    }

    @Override
    public boolean isBiomePerfect(Biome biome) {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA);
    }

    public ItemStack getSeedStack(int qty) {
        return this.treeFamily.getCommonSpecies().getSeedStack(qty);
    }

    public Seed getSeed() {
        return this.treeFamily.getCommonSpecies().getSeed();
    }

    @Override
    protected void setStandardSoils() {
        addAcceptableSoils(DirtHelper.DIRTLIKE, DirtHelper.HARDCLAYLIKE);
    }

}
