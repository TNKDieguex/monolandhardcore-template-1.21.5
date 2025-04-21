package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;

public class CreeperTeleport {
    private static final int TELEPORT_RANGE = 14;

    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE
                .register((entity, source, originalHealth, damageTaken, wasBlocked) -> {
                    if (!(entity instanceof CreeperEntity creeper))
                        return;
                    World world = creeper.getWorld();
                    if (!world.isClient && world instanceof ServerWorld serverWorld) {
                        if (serverWorld.getRegistryKey() == ServerWorld.END) {
                            CreeperTeleport.attemptTeleport(serverWorld, creeper);
                        }
                    }
                });
    }

    private static void attemptTeleport(ServerWorld world, CreeperEntity creeper) {
        Random random = world.getRandom();

        for (int tries = 0; tries < 15; tries++) {
            double x = creeper.getX() + random.nextBetween(-TELEPORT_RANGE, TELEPORT_RANGE);
            double z = creeper.getZ() + random.nextBetween(-TELEPORT_RANGE, TELEPORT_RANGE);
            double y = creeper.getY() + random.nextBetween(-2, 2);

            BlockPos pos = new BlockPos((int) x, (int) y - 1, (int) z);
            BlockPos above = pos.up();
            if (!world.getBlockState(pos).isAir() && world.getBlockState(above).isAir()) {
                creeper.teleport(x, y, z, true);
                world.playSound(
                        null,
                        creeper.getX(),
                        creeper.getY(),
                        creeper.getZ(),
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                        SoundCategory.HOSTILE,
                        1.0f,
                        1.0f);

                world.spawnParticles(ParticleTypes.PORTAL, x, y, z, 30, 0.5, 0.5, 0.5, 0.1);
                return;
            }
        }
    }
}
