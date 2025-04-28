package net.dieguex.monoland.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DeathStormManager {
    private static int remainingStormTicks = 0;
    private static final int TICKS_PER_MINUTE = 1200; // 20 ticks/segundo * 60 segundos
    private static final int STORM_DURATION_PER_DEATH = 30 * TICKS_PER_MINUTE; // 30 minutos = 36000 ticks

    public static void init() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof PlayerEntity && !entity.getWorld().isClient) {
                if (entity.getWorld() instanceof ServerWorld serverWorld) {
                    boolean newStorm = remainingStormTicks == 0; // ✅ Saber si es nueva tormenta o extendida

                    remainingStormTicks += STORM_DURATION_PER_DEATH;
                    serverWorld.setWeather(0, remainingStormTicks, true, true);

                    Text message;
                    if (newStorm) {
                        message = Text.translatable("monoland.storm.start")
                                .styled(style -> style.withColor(Formatting.RED));
                    } else {
                        message = Text.translatable("monoland.storm.extend")
                                .styled(style -> style.withColor(Formatting.GOLD));
                    }

                    serverWorld.getServer().getPlayerManager().broadcast(message, false);
                }
            }
        });

        ServerTickEvents.START_WORLD_TICK.register(world -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;

            if (remainingStormTicks > 0) {
                remainingStormTicks--;

                serverWorld.setWeather(0, remainingStormTicks, true, true);

                if (serverWorld.getTime() % 20 == 0) {
                    int totalSeconds = remainingStormTicks / 20;
                    int hours = totalSeconds / 3600;
                    int minutes = (totalSeconds % 3600) / 60;
                    int seconds = totalSeconds % 60;

                    String formattedTime;
                    if (hours > 0) {
                        formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    } else {
                        formattedTime = String.format("%02d:%02d", minutes, seconds);
                    }

                    Text actionBarMessage = Text.translatable("monoland.storm.remaining", formattedTime);

                    for (PlayerEntity player : serverWorld.getPlayers()) {
                        player.sendMessage(actionBarMessage, true);
                    }
                }
            }
        });
    }
}
