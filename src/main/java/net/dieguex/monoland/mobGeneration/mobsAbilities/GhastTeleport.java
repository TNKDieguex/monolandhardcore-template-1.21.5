package net.dieguex.monoland.mobGeneration.mobsAbilities;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;

public class GhastTeleport {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, originalHealth, damageTaken, wasBlocked) -> {
            if (!(entity instanceof GhastEntity ghast))
                return;
            if (!(ghast.getWorld() instanceof ServerWorld world))
                return;
            if (!world.isClient && world instanceof ServerWorld serverWorld) {
                if (serverWorld.getRegistryKey() == ServerWorld.END) {
                    Entity attacker = source.getAttacker();
                    boolean isProjectile = source.isIn(DamageTypeTags.IS_PROJECTILE);
                    if (world.getRandom().nextInt(100) < 20) {
                        teleportAwayFrom(world, ghast, attacker);
                    }
                    ghast.damage(serverWorld, source, damageTaken);
                }
            }
        });
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;

            for (GhastEntity ghast : serverWorld.getEntitiesByType(TypeFilter.instanceOf(GhastEntity.class),
                    c -> true)) {
                if (!ghast.isAlive())
                    continue;

                for (ProjectileEntity proj : serverWorld
                        .getEntitiesByType(TypeFilter.instanceOf(ProjectileEntity.class), p -> true)) {
                    if (proj.isRemoved() || proj.getOwner() == null)
                        continue;

                    // Solo si va hacia el ghast y está cerca
                    if (proj.squaredDistanceTo(ghast) < 4.0 * 4.0 && world.getRandom().nextInt(100) < 20) {
                        teleportAwayFrom(serverWorld, ghast, proj);
                        break;
                    }
                }
            }
        });
    }

    public static boolean teleportAwayFrom(ServerWorld world, GhastEntity ghast, Entity attacker) {
        Vec3d dir = ghast.getPos().subtract(attacker.getPos()).normalize();
        Random rand = world.getRandom();

        double x = ghast.getX() + (rand.nextDouble() - 0.5) * 8.0 - dir.x * 16.0;
        double y = ghast.getY() + (rand.nextInt(16) - 8) - dir.y * 16.0;
        double z = ghast.getZ() + (rand.nextDouble() - 0.5) * 8.0 - dir.z * 16.0;

        BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);

        while (pos.getY() > world.getBottomY() && !world.getBlockState(pos).blocksMovement()) {
            pos.move(Direction.DOWN);
        }

        if (world.getBlockState(pos).blocksMovement()
                && !world.getBlockState(pos).getFluidState().isIn(FluidTags.WATER)) {
            boolean success = ghast.teleport(x, y, z, true);
            if (success) {
                world.emitGameEvent(GameEvent.TELEPORT, ghast.getPos(), GameEvent.Emitter.of(ghast));
                world.playSound(null, ghast.getX(), ghast.getY(), ghast.getZ(),
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE, 1.0f, 1.0f);
            }
            return success;
        }

        return false;
    }
}
