package net.dieguex.monoland.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public class EffectCleaner {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof LivingEntity living && entity.getWorld() instanceof ServerWorld) {
                living.clearStatusEffects();
            }
        });
    }
}
