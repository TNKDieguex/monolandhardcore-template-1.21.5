package net.dieguex.monoland.mobGeneration.mobsAbilities;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.util.math.random.Random;

public class CreeperTeleport {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE
                .register((entity, source, originalHealth, damageTaken, wasBlocked) -> {
                    if (!(entity instanceof CreeperEntity creeper))
                        return;
                    if (!(creeper.getWorld() instanceof ServerWorld world))
                        return;
                    if (!world.isClient && world instanceof ServerWorld serverWorld) {
                        if (serverWorld.getRegistryKey() == ServerWorld.END) {
                            Entity attacker = source.getAttacker();
                            boolean isProjectile = source.isIn(DamageTypeTags.IS_PROJECTILE);
                            teleportAwayFrom(world, creeper, attacker);
                            creeper.damage(serverWorld, source, damageTaken);
                        }
                    }
                });
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;

            for (CreeperEntity creeper : serverWorld.getEntitiesByType(TypeFilter.instanceOf(CreeperEntity.class),
                    c -> true)) {
                if (!creeper.isAlive())
                    continue;

                for (ProjectileEntity proj : serverWorld
                        .getEntitiesByType(TypeFilter.instanceOf(ProjectileEntity.class), p -> true)) {
                    if (proj.isRemoved() || proj.getOwner() == null)
                        continue;

                    // Solo si va hacia el creeper y está cerca
                    if (proj.squaredDistanceTo(creeper) < 2.0 * 2.0) {
                        teleportAwayFrom(serverWorld, creeper, proj);
                        break;
                    }
                }
            }
        });
    }

    public static boolean teleportAwayFrom(ServerWorld world, CreeperEntity creeper, Entity attacker) {
        Vec3d dir = creeper.getPos().subtract(attacker.getPos()).normalize();
        Random rand = world.getRandom();

        double x = creeper.getX() + (rand.nextDouble() - 0.5) * 8.0 - dir.x * 16.0;
        double y = creeper.getY() + (rand.nextInt(16) - 8) - dir.y * 16.0;
        double z = creeper.getZ() + (rand.nextDouble() - 0.5) * 8.0 - dir.z * 16.0;

        BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);

        while (pos.getY() > world.getBottomY() && !world.getBlockState(pos).blocksMovement()) {
            pos.move(Direction.DOWN);
        }

        if (world.getBlockState(pos).blocksMovement()
                && !world.getBlockState(pos).getFluidState().isIn(FluidTags.WATER)) {
            boolean success = creeper.teleport(x, y, z, true);
            if (success) {
                world.emitGameEvent(GameEvent.TELEPORT, creeper.getPos(), GameEvent.Emitter.of(creeper));
                world.playSound(null, creeper.getX(), creeper.getY(), creeper.getZ(),
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE, 1.0f, 1.0f);
            }
            return success;
        }

        return false;
    }
}
