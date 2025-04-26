package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.server.world.ServerWorld;

public class GhastMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof GhastEntity ghast)) {
                return;
            }

            // ghast changes in the end
            if (world.getRegistryKey() == ServerWorld.END) {

                if (ModTimeManager.hasPassedDays(12)) {
                    ghast.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.SPEED, 1000000, 2, false, false, false));
                    ghast.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.RESISTANCE, 1000000, 2, false, false, false));
                }
            }
            // ghast changes in the nether
            if (world.getRegistryKey() == ServerWorld.NETHER) {

                if (ModTimeManager.hasPassedDays(12)) {
                    ghast.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.SPEED, 1000000, 2, false, false, false));
                    ghast.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.RESISTANCE, 1000000, 2, false, false, false));
                }
            }
        });
    }
}
