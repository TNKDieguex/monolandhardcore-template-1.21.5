package net.dieguex.monoland.util;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class PlayerHealthManager {
    private static final double MIN_HEALTH = 2.0f; // 1 corazón

    public static void register() {
        if (ModTimeManager.hasPassedDays(7)) {
            ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
                if (!(entity instanceof ServerPlayerEntity player))
                    return;
                if (!(player.getWorld() instanceof ServerWorld))
                    return;

                float loss = 2.0f;

                EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
                if (attr != null) {
                    double newMax = Math.max(MIN_HEALTH, attr.getBaseValue() - loss);
                    attr.setBaseValue(newMax);
                    System.out.println("❤️ Vida máxima de " + player.getName().getString() + " ahora es: " + newMax);
                }
            });
        } else {
            // regenerar vida al morir, o poner un comando para recuperar toda la vida

            // ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            // if (!(entity instanceof ServerPlayerEntity player))
            // return;
            // if (!(player.getWorld() instanceof ServerWorld))
            // return;

            // float loss = 2.0f;

            // EntityAttributeInstance attr =
            // player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            // if (attr != null) {
            // double newMax = Math.max(MIN_HEALTH, attr.getBaseValue() - loss);
            // attr.setBaseValue(newMax);
            // System.out.println("❤️ Vida máxima de " + player.getName().getString() + "
            // ahora es: " + newMax);
            // }
            // });
        }
    }
}
