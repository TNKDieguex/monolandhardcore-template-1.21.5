package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.server.world.ServerWorld;

public class GuardianMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof GuardianEntity guardian)) {
                return;
            }
            if (guardian.getCommandTags().contains("custom_guardian")) {
                return;
            }
            if (ModTimeManager.hasPassedDays(40)) {
                guardian.addCommandTag("custom_guardian");
                guardian.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 2, false, false,
                        false));
                guardian.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 3, false, false,
                        false));
                guardian.addCommandTag("custom_guardian");
            }
        });
    }
}
