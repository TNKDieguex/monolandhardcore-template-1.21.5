package net.dieguex.monoland.util;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class RainExposureEffect {
    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;
            if (!ModTimeManager.hasPassedDays(14))
                return;

            if (serverWorld.isThundering()) { // ✅ Solo si hay tormenta
                for (PlayerEntity player : serverWorld.getPlayers()) {
                    boolean isExposedToRain = serverWorld.isRaining() && serverWorld.isSkyVisible(player.getBlockPos());

                    if (isExposedToRain) {
                        Random random = serverWorld.getRandom();
                        if (random.nextInt(10000) == 0) { // 1 en 10000 chance

                            int extraDuration = 20 * 60; // 1 minuto en ticks
                            int totalDuration = extraDuration;

                            StatusEffectInstance current = player.getStatusEffect(StatusEffects.BLINDNESS);
                            if (current != null) {
                                totalDuration += current.getDuration(); // Sumar duración existente
                            }

                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, totalDuration, 0));
                        }
                    }
                }
            } else {
                // Si no hay tormenta, aseguramos limpiar efectos
                for (PlayerEntity player : serverWorld.getPlayers()) {
                    if (player.hasStatusEffect(StatusEffects.BLINDNESS)) {
                        player.removeStatusEffect(StatusEffects.BLINDNESS);
                    }
                }
            }
        });
    }
}
