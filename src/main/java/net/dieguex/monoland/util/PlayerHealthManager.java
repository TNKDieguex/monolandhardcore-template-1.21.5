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
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity instanceof ServerPlayerEntity player))
                return;
            if (!(player.getWorld() instanceof ServerWorld))
                return;
            if (!ModTimeManager.hasPassedDays(2))
                return;

            EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            if (attr == null)
                return;

            float newMax = Math.max(MIN_HEALTH, (float) attr.getBaseValue() - 2.0f);
            attr.setBaseValue(newMax);
        });

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (!(entity instanceof ServerPlayerEntity player))
                return true;

            boolean hasTotem = false;
            for (Hand hand : Hand.values()) {
                ItemStack stack = player.getStackInHand(hand);
                if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                    hasTotem = true;
                    break;
                }
            }

            if (hasTotem) {
                sendTotemMessage(player);
                if (!ModTimeManager.hasPassedDays(2))
                    return true;
                EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
                if (attr != null) {
                    float newMax = Math.max(MIN_HEALTH, (float) attr.getBaseValue() - 1.0f);
                    attr.setBaseValue(newMax);
                }
            }
            return true;
        });
    }

    private static void sendTotemMessage(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("monoland.totem.used.life"), true);
        player.getWorld().sendEntityStatus(player, (byte) 35);

        Text playerName = player.getName().copy().styled(style -> style.withBold(true));
        Text translatedMessage = Text.translatable("monoland.totem.used", playerName)
                .copy().styled(style -> style.withColor(Formatting.GOLD).withBold(true));
        player.getServer().getPlayerManager().broadcast(translatedMessage, false);
    }
}
