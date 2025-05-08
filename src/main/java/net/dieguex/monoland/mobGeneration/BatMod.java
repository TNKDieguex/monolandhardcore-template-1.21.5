package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.passive.BatEntity;

import net.minecraft.server.world.ServerWorld;

public class BatMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof BatEntity murcielago)) {
                return;
            }
            if (ModTimeManager.hasPassedDays(30)) {
                BlazeEntity blaze = EntityType.BLAZE.create(world, null, murcielago.getBlockPos(), SpawnReason.EVENT,
                        true, false);
                if (blaze != null) {
                    blaze.setYaw(murcielago.getYaw());
                    blaze.setPitch(murcielago.getPitch());
                    blaze.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 2, false, false,
                            false));

                    world.spawnEntity(blaze);
                    murcielago.discard();
                    return;
                }
            }
        });
    }

}
