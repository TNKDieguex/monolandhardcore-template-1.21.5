package net.dieguex.monoland.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.util.math.Box;

import java.util.List;

public class CreeperSpawnerEnd {
    private static final int SPAWN_CHANCE = 4000;

    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;

            if (serverWorld.getRegistryKey() == ServerWorld.END && ModTimeManager.hasPassedDays(9)) {
                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                    Box searchArea = player.getBoundingBox().expand(64);
                    List<EndermanEntity> endermen = serverWorld.getEntitiesByClass(
                            EndermanEntity.class, searchArea, e -> true);

                    for (EndermanEntity enderman : endermen) {
                        Random random = serverWorld.getRandom();
                        if (random.nextInt(SPAWN_CHANCE) == 0) { // 1 en 5000 probabilidad
                            BlockPos basePos = enderman.getBlockPos();
                            int offsetX = random.nextBetween(-6, 6);
                            int offsetZ = random.nextBetween(-6, 6);

                            int targetX = basePos.getX() + offsetX;
                            int targetZ = basePos.getZ() + offsetZ;

                            int y = serverWorld.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                                    new BlockPos(targetX, 0, targetZ));
                            BlockPos spawnPos = new BlockPos(targetX, y, targetZ);

                            CreeperEntity creeper = EntityType.CREEPER.create(
                                    serverWorld, null, spawnPos, SpawnReason.EVENT, true, false);

                            if (creeper != null) {
                                creeper.addCommandTag("end_spawned");
                                creeper.refreshPositionAndAngles(
                                        spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
                                serverWorld.spawnEntity(creeper);
                            }
                        }
                    }
                }
            }
        });
    }
}
