package net.dieguex.monoland.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class GhastTeleport {
    private static final int TELEPORT_RANGE = 14;

    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, originalHealth, damageTaken, wasBlocked) -> {
            if (!(entity instanceof GhastEntity ghast))
                return;

            World world = ghast.getWorld();
            if (!world.isClient && world instanceof ServerWorld serverWorld) {
                if (serverWorld.getRegistryKey() == ServerWorld.END) {
                    attemptTeleport(serverWorld, ghast);
                }
            }
        });
    }

    private static void attemptTeleport(ServerWorld world, GhastEntity ghast) {
        Random random = world.getRandom();

        for (int tries = 0; tries < 10; tries++) {
            double x = ghast.getX() + random.nextBetween(-TELEPORT_RANGE, TELEPORT_RANGE);
            double y = ghast.getY() + random.nextBetween(0, 25);
            double z = ghast.getZ() + random.nextBetween(-TELEPORT_RANGE, TELEPORT_RANGE);

            BlockPos targetPos = new BlockPos((int) x, (int) y, (int) z);

            if (world.isAir(targetPos)) {
                ghast.teleport(x, targetPos.getY() + y, z, true);

                world.spawnParticles(ParticleTypes.PORTAL, x, y, z, 40, 0.5, 0.5, 0.5, 0.1);
                world.playSound(null, x, y, z, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE, 1.0f, 1.0f);
                return;
            }
        }
    }
}
