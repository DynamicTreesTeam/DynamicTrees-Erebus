package com.harleyoconnor.dynamictreeserebus.proxy;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import com.harleyoconnor.dynamictreeserebus.growth.CustomCellKits;
import erebus.ModBiomes;
import erebus.blocks.EnumWood;
import erebus.world.biomes.decorators.BiomeDecoratorBaseErebus;
import erebus.world.feature.tree.WorldGenErebusHugeTree;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CommonProxy {

	// Blank generator - a generator that will replace regular ones to do nothing when called upon (don't spawn trees).
	private final WorldGenerator blankGenerator = new WorldGenerator() {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			return true;
		}

		public boolean func_180709_b(Object world, Object rand, Object pos) {
			return true;
		}
	};

	private final HashMap<String, WorldGenerator> generatorFields = new HashMap<>();

	public CommonProxy() {
		// Add field names and generators.
		this.generatorFields.put("genTreeMahogany", this.blankGenerator);
		this.generatorFields.put("genTreeJungle", this.blankGenerator);
		this.generatorFields.put("genTreeJungleLarge", this.blankGenerator);
		this.generatorFields.put("genTreeMossbark", this.blankGenerator);
		this.generatorFields.put("genTreeAsper", this.blankGenerator);
		this.generatorFields.put("genTreeJungleTall", this.blankGenerator);
		this.generatorFields.put("genTreeEucalyptus", this.blankGenerator);
		this.generatorFields.put("genTreeCypress", this.blankGenerator);
		this.generatorFields.put("genTreeAcacia", this.blankGenerator);
		this.generatorFields.put("genTreeBalsam", this.blankGenerator);
	}

	public void preInit() {
		// Initialise custom cell kits.
		new CustomCellKits();
	}
	
	public void init() {
		// Register sapling replacements.
		List<String> saplingNames = Arrays.asList(new String[]{"asper", "balsam", "cypress", "eucalyptus", "mahogany", "mossbark"});
		saplingNames.forEach(CommonProxy::registerSaplingReplacement);

		// Add blank generator for Erebus huge tree - defining this before init causes crash.
		this.generatorFields.put("genTreeMahoganyLarge", new WorldGenErebusHugeTree(true, true, EnumWood.MAHOGANY) {
			@Override
			public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
				return true;
			}

			@Override
			public void prepare(int baseHeight) {
			}
		});

		try {
			List<BiomeDecoratorBaseErebus> biomeDecorators = new ArrayList<>();

			for (final Biome biome : ModBiomes.BIOME_LIST) {
				// Get decorator for biome instance.
				final Field field = biome.getClass().getSuperclass().getDeclaredField("decorator");
				field.setAccessible(true);

				BiomeDecoratorBaseErebus decorator = (BiomeDecoratorBaseErebus) field.get(biome);
				biomeDecorators.add(decorator); // Add decorator to list of decorators.
			}

			for (BiomeDecoratorBaseErebus decorator : biomeDecorators) {
				for (String fieldName : this.generatorFields.keySet()) {
					try {
						// Set field for current tree generator if it exists.
						this.setPrivateField(decorator, fieldName, this.generatorFields.get(fieldName));
					} catch (NoSuchFieldException ignored) { }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void registerSaplingReplacement(final String speciesName) {
		TreeRegistry.registerSaplingReplacer(Block.getBlockFromName("erebus:sapling_" + speciesName).getDefaultState(), TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesErebus.MODID, speciesName)));
	}

	private void setPrivateField (final Object classInstance, final String fieldName, final Object newValue) throws Exception {
		final Field field = classInstance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(classInstance, newValue);
	}

	public void postInit() {
	}

}
