package net.dieguex.monoland.mobGeneration;

import java.util.ArrayList;
import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

public class SilverFish {
    private static final List<RegistryEntry<StatusEffect>> statusEffects = new ArrayList<>(List.of(
            StatusEffects.SPEED,
            StatusEffects.STRENGTH,
            StatusEffects.JUMP_BOOST,
            StatusEffects.GLOWING,
            StatusEffects.REGENERATION,
            StatusEffects.INVISIBILITY,
            StatusEffects.SLOW_FALLING,
            StatusEffects.RESISTANCE));

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof SilverfishEntity silverfish)) {
                return;
            }
            if (silverfish.getCommandTags().contains("custom_silverfish")) {
                return;
            }

            if (ModTimeManager.hasPassedDays(10)) {
                List<RegistryEntry<StatusEffect>> chosen = SpiderMod.pickRandom(statusEffects, 5);
                if (silverfish.getStatusEffects().isEmpty()) {
                    EnchantAndEffectsUtils.applyEffectsToMob(silverfish, chosen);
                }
                silverfish.addCommandTag("custom_silverfish");
            }
        });
    }
}
