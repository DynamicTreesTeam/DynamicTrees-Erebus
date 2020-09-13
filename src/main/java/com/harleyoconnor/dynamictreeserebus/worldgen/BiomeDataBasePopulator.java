package com.harleyoconnor.dynamictreeserebus.worldgen;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import erebus.ModBiomes;
import erebus.world.biomes.BiomeBaseErebus;
import net.minecraft.world.biome.Biome;

public final class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    public static Biome[] erebusBiomes = {
            ModBiomes.ELYSIAN_FIELDS, // Cypress trees spawn here.
            ModBiomes.FUNGAL_FOREST, // No trees, but rotten wood could eventually be added.
            ModBiomes.PETRIFIED_FOREST, // No trees.
            ModBiomes.SUBMERGED_SWAMP, // Marshwood trees spawn here, but are not currently dynamic due to them being extremely large.
            ModBiomes.SUBTERRANEAN_SAVANNAH, // Acacia trees, giant baobab (not dynamic), and asper trees spawn here.
            ModBiomes.ULTERIOR_OUTBACK, // Eucalyptus, acacia, and balsam trees spawn here.
            ModBiomes.UNDERGROUND_JUNGLE, // Jungle, Asper, Eucalyptus, Mossbark, and Mahogany trees spawn here.
            ModBiomes.VOLCANIC_DESERT // No trees, but scorched wood could eventually be added.
    };

    public void populate(BiomeDataBase dbase) {

    }

}

