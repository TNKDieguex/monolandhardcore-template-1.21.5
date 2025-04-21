package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.dieguex.monoland.timeManager.ModTimeManager;

import java.util.List;

public class GhastSpawnerEnd {
    private static final int SPAWN_CHANCE = 13000;

    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;

            if (serverWorld.getRegistryKey() == ServerWorld.END && ModTimeManager.hasPassedDays(9)) {
                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                    Box searchArea = player.getBoundingBox().expand(64);
                    List<EndermanEntity> endermen = serverWorld.getEntitiesByClass(EndermanEntity.class, searchArea,
                            e -> true);

                    for (EndermanEntity enderman : endermen) {
                        Random random = serverWorld.getRandom();
                        if (random.nextInt(SPAWN_CHANCE) == 0) {
                            BlockPos basePos = enderman.getBlockPos();
                            int offsetX = random.nextBetween(-8, 8);
                            int offsetZ = random.nextBetween(-8, 8);
                            int offsetY = random.nextBetween(0, 25); // flotando o cerca del suelo

                            BlockPos spawnPos = basePos.add(offsetX, offsetY, offsetZ);

                            GhastEntity ghast = EntityType.GHAST.create(serverWorld, null, spawnPos, SpawnReason.EVENT,
                                    true, false);

                            if (ghast != null) {
                                ghast.refreshPositionAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
                                serverWorld.spawnEntity(ghast);
                            }
                        }
                    }
                }
            }
        });
    }
}
