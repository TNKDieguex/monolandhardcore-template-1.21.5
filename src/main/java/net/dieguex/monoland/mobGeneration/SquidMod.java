package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.server.world.ServerWorld;

public class SquidMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof SquidEntity squid)) {
                return;
            }
            if (ModTimeManager.hasPassedDays(30)) {
                GuardianEntity guardian = EntityType.GUARDIAN.create(world, null, squid.getBlockPos(),
                        SpawnReason.EVENT, true, false);
                if (guardian != null) {
                    guardian.setYaw(squid.getYaw());
                    guardian.setPitch(squid.getPitch());
                    guardian.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 1, false, false,
                            false));
                    world.spawnEntity(guardian);
                    squid.discard();
                    return;
                }

            }
        });
    }
}
