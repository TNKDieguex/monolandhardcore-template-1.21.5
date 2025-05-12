package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class ShulkerMod {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity.getWorld() instanceof ServerWorld serverWorld))
                return;
            if (!(entity instanceof ShulkerEntity shulker)) {
                return;
            }
            if (shulker.getCommandTags().contains("custom_shulker")) {
                return;
            }
            if (ModTimeManager.hasPassedDays(14)) {
                TntEntity tnt = new TntEntity(serverWorld, shulker.getX(), shulker.getY(), shulker.getZ(), null);
                tnt.setFuse(60);
                serverWorld.spawnEntity(tnt);
                serverWorld.playSound(null, shulker.getBlockPos(), SoundEvents.ENTITY_TNT_PRIMED,
                        SoundCategory.HOSTILE, 1.0f, 1.0f);
                shulker.addCommandTag("custom_shulker");
            }
        });
    }
}
