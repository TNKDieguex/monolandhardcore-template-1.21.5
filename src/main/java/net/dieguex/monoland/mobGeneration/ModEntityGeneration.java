package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.entity.EntityType;

public class ModEntityGeneration {
        // Generate creepers in the end dimension
        public static void registerNaturalSpawns() {
                BiomeModifications.addSpawn(
                                BiomeSelectors.includeByKey(BiomeKeys.THE_END, BiomeKeys.END_BARRENS,
                                                BiomeKeys.END_HIGHLANDS,
                                                BiomeKeys.END_MIDLANDS,
                                                BiomeKeys.SMALL_END_ISLANDS),
                                SpawnGroup.MONSTER, EntityType.CREEPER, 20, 1, 3);
                BiomeModifications.addSpawn(
                                BiomeSelectors.includeByKey(BiomeKeys.THE_END, BiomeKeys.END_BARRENS,
                                                BiomeKeys.END_HIGHLANDS,
                                                BiomeKeys.END_MIDLANDS,
                                                BiomeKeys.SMALL_END_ISLANDS),
                                SpawnGroup.MONSTER, EntityType.GHAST, 40, 1, 4);
        }
}
