package com.harleyoconnor.dynamictreeserebus.worldgen;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBasePopulatorJson;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import erebus.core.handler.configs.ConfigHandler;
import net.minecraft.util.ResourceLocation;

public final class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    public static final String RESOURCEPATH = "worldgen/default.json";

    private final BiomeDataBasePopulatorJson jsonPopulator;

    public BiomeDataBasePopulator (){
        jsonPopulator = new BiomeDataBasePopulatorJson(new ResourceLocation(AddonConstants.MOD_ID, RESOURCEPATH));
    }

    public void populate(BiomeDataBase dbase) {
        ModConfigs.dimensionBlacklist.remove(ConfigHandler.INSTANCE.erebusDimensionID);
        jsonPopulator.populate(dbase);
    }

}

