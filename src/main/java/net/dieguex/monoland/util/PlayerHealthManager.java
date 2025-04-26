package net.dieguex.monoland.util;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

public class PlayerHealthManager {
    private static final float MIN_HEALTH = 10.0f; // 5 corazónes

    public static void register() {
        if (ModTimeManager.hasPassedDays(6)) {
            ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
                if (!(entity instanceof ServerPlayerEntity player))
                    return;
                if (!(player.getWorld() instanceof ServerWorld))
                    return;

                EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
                if (attr != null) {
                    float newMax = Math.max(MIN_HEALTH, (float) attr.getBaseValue() - 2.0f);
                    attr.setBaseValue(newMax);
                    System.out.println("☠️ Muerte: " + player.getName().getString()
                            + " pierde 1 corazón permanente. Vida máxima ahora: " + newMax);
                }
            });

            // Detectar uso de tótem justo antes de morir
            ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
                if (!(entity instanceof ServerPlayerEntity player))
                    return true;

                float finalHealth = player.getHealth() - amount;

                for (Hand hand : Hand.values()) {
                    ItemStack itemStack = player.getStackInHand(hand);
                    if (finalHealth <= 0.0f && itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
                        EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
                        if (attr != null) {
                            float newMax = Math.max(MIN_HEALTH, (float) attr.getBaseValue() - 1.0f);
                            attr.setBaseValue(newMax);
                        }

                        player.sendMessage(Text.literal("⚠️ Perdiste medio corazón permanente al usar el tótem."),
                                true);
                        player.getWorld().sendEntityStatus(player, (byte) 35); // efecto visual
                        Text playerName = player.getName().copy().styled(style -> style.withBold(true));

                        Text translatedMessage = Text.translatable("monoland.totem.used", playerName)
                                .copy().styled(style -> style.withColor(Formatting.YELLOW));

                        player.getServer().getPlayerManager().broadcast(translatedMessage, false);
                        break;
                    }
                }

                return true;
            });
        }

    }
}
