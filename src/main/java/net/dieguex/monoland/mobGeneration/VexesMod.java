package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.server.world.ServerWorld;

public class VexesMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof VexEntity vex)) {
                return;
            }
            if (vex.getCommandTags().contains("custom_vex")) {
                return;
            }
            if (ModTimeManager.hasPassedDays(20)) {
                vex.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 2, false, false,
                        false));
                vex.addCommandTag("custom_vex");
            }
        });
    }
}
