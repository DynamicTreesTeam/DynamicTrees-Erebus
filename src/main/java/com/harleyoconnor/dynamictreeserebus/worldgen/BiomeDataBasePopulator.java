package com.harleyoconnor.dynamictreeserebus.worldgen;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import erebus.Erebus;
import erebus.ModBiomes;
import erebus.world.biomes.BiomeBaseErebus;
import erebus.world.biomes.BiomeElysianFields;
import erebus.world.biomes.BiomeUlteriorOutback;
import erebus.world.biomes.BiomeUndergroundJungle;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle dynamic trees biome population.
 *
 * @author Harley O'Connor
 */
public final class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    public void populate (final BiomeDataBase dataBase) {
        final Species acacia = TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "acacia"));
        final Species asper = TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "asper"));
        final Species eucalyptus = TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "eucalyptus"));

        final Biome subterraneanSavannah = ModBiomes.SUBTERRANEAN_SAVANNAH;
        final Biome ulteriorOutback = ModBiomes.ULTERIOR_OUTBACK;
        final Biome undergroundJungle = ModBiomes.UNDERGROUND_JUNGLE;
        final Biome elysianFields = ModBiomes.ELYSIAN_FIELDS;

        dataBase.setDensitySelector(subterraneanSavannah, (rand, noiseDensity) -> noiseDensity * 0.15, BiomeDataBase.Operation.REPLACE);
        dataBase.setChanceSelector(subterraneanSavannah, (rnd, spc, rad) -> BiomePropertySelectors.EnumChance.OK, BiomeDataBase.Operation.REPLACE);
        dataBase.setIsSubterranean(subterraneanSavannah, true);

        dataBase.setDensitySelector(ulteriorOutback, (rand, noiseDensity) -> noiseDensity * 0.08, BiomeDataBase.Operation.REPLACE);
        dataBase.setChanceSelector(ulteriorOutback, (rnd, spc, rad) -> BiomePropertySelectors.EnumChance.OK, BiomeDataBase.Operation.REPLACE);
        dataBase.setIsSubterranean(ulteriorOutback, true);

        dataBase.setDensitySelector(undergroundJungle, (rand, noiseDensity) -> noiseDensity * 0.4, BiomeDataBase.Operation.REPLACE);
        dataBase.setChanceSelector(undergroundJungle, (rnd, spc, rad) -> BiomePropertySelectors.EnumChance.OK, BiomeDataBase.Operation.REPLACE);
        dataBase.setIsSubterranean(undergroundJungle, true);

        dataBase.setDensitySelector(elysianFields, (rand, noiseDensity) -> noiseDensity * 0.2, BiomeDataBase.Operation.REPLACE);
        dataBase.setChanceSelector(elysianFields, (rnd, spc, rad) -> BiomePropertySelectors.EnumChance.OK, BiomeDataBase.Operation.REPLACE);
        dataBase.setIsSubterranean(elysianFields, true);

        // Add Cypress to random species selector for Elysian Fields.
        this.addSpeciesSelector(dataBase, elysianFields, TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "cypress")), 1);

        // Add Acacia to random species selector for Subterranean Savannah.
        this.addSpeciesSelector(dataBase, subterraneanSavannah, acacia, 1);

        // Add Asper to random species selector for Subterranean Savannah.
        this.addSpeciesSelector(dataBase, subterraneanSavannah, asper, 1);

        // Add Eucalyptus to random species selector for Ulterior Outback.
        this.addSpeciesSelector(dataBase, ulteriorOutback, eucalyptus, 1);

        // Add Acacia to random species selector for Ulterior Outback.
        this.addSpeciesSelector(dataBase, ulteriorOutback, acacia, 1);

        // Add Balsam to random species selector for Ulterior Outback.
        this.addSpeciesSelector(dataBase, ulteriorOutback, TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "balsam")), 1);

        // Add Eucalyptus to random species selector for Underground Jungle.
        this.addSpeciesSelector(dataBase, undergroundJungle, eucalyptus, 1);

        // Add Jungle to random species selector for Underground Jungle.
        this.addSpeciesSelector(dataBase, undergroundJungle, TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "jungle")), 1);

        // Add Mega Jungle to random species selector for Underground Jungle.
        this.addSpeciesSelector(dataBase, undergroundJungle, TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "megajungle")), 1);

        // Add Asper to random species selector for Underground Jungle.
        this.addSpeciesSelector(dataBase, undergroundJungle, asper, 1);

        // Add Mossbark to random species selector for Underground Jungle.
        this.addSpeciesSelector(dataBase, undergroundJungle, TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "mossbark")), 1);

        // Add Mahogany to random species selector for Underground Jungle.
        this.addSpeciesSelector(dataBase, undergroundJungle, TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "mahogany")), 1);

        // Add Mega Mahogany to random species selector for Underground Jungle.
        this.addSpeciesSelector(dataBase, undergroundJungle, TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "megamahogany")), 1);
    }

    private void addSpeciesSelector (final BiomeDataBase dataBase, final Biome biome, final Species species, final int weight) {
        dataBase.setSpeciesSelector(biome, new BiomePropertySelectors.RandomSpeciesSelector().add(weight).add(species, 5), BiomeDataBase.Operation.SPLICE_BEFORE);
    }

}

