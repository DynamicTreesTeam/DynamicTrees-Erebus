package com.harleyoconnor.dynamictreeserebus.worldgen;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import erebus.ModBiomes;
import erebus.core.handler.configs.ConfigHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

/**
 * Class to handle dynamic trees biome population.
 *
 * @author Harley O'Connor
 */
public final class BiomeDataBasePopulatorOld implements IBiomeDataBasePopulator {

    public void populate (final BiomeDataBase dataBase) {
        final Species acacia = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "acacia"));
        final Species asper = TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "asper"));
        final Species eucalyptus = TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "eucalyptus"));

        ModConfigs.dimensionBlacklist.remove(ConfigHandler.INSTANCE.erebusDimensionID);

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
        dataBase.setSpeciesSelector(elysianFields, new BiomePropertySelectors.RandomSpeciesSelector()
                .add(TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "cypress")), 1),
                BiomeDataBase.Operation.SPLICE_BEFORE);

        // Add Acacia and Asper to random species selector for Subterranean Savannah.
        dataBase.setSpeciesSelector(elysianFields, new BiomePropertySelectors.RandomSpeciesSelector()
                        .add(acacia, 1)
                        .add(asper, 1),
                BiomeDataBase.Operation.SPLICE_BEFORE);

        // Add Eucalyptus, Acacia and Balsam to random species selector for Ulterior Outback.
        dataBase.setSpeciesSelector(elysianFields, new BiomePropertySelectors.RandomSpeciesSelector()
                        .add(eucalyptus, 1)
                        .add(acacia, 1)
                        .add(TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "balsam")), 1),
                BiomeDataBase.Operation.SPLICE_BEFORE);

        // Add Eucalyptus, Jungle, Mega Jungle, Asper, Mossbark, Mahogany and Mega Mahogany to random species selector for Underground Jungle.
        dataBase.setSpeciesSelector(elysianFields, new BiomePropertySelectors.RandomSpeciesSelector()
                        .add(eucalyptus, 1)
                        .add(TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "jungle")), 1)
                        .add(TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "megajungle")), 1)
                        .add(asper, 1)
                        .add(TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "mossbark")), 1)
                        .add(TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "mahogany")), 1)
                        .add(TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, "megamahogany")), 1),
                BiomeDataBase.Operation.SPLICE_BEFORE);
    }
}