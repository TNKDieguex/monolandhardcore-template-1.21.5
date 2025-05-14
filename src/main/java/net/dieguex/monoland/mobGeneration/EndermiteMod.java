package net.dieguex.monoland.mobGeneration;

import java.util.ArrayList;
import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

public class EndermiteMod {
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
        // Register the event listener for Endermite entity loading
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;

            if (entity instanceof EndermiteEntity endermite) {
                if (ModTimeManager.hasPassedDays(8)) {
                    if (endermite.getCommandTags() == null
                            || !endermite.getCommandTags().contains("custom_endermite")) {
                        EntityAttributeInstance damageAttr = endermite
                                .getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
                        if (damageAttr != null) {
                            double baseDamage = damageAttr.getBaseValue();
                            damageAttr.setBaseValue(baseDamage * 3);
                        }
                        List<RegistryEntry<StatusEffect>> chosen = SpiderMod.pickRandom(statusEffects, 5);
                        if (endermite.getStatusEffects().isEmpty()) {
                            EnchantAndEffectsUtils.applyEffectsToMob(endermite, chosen);
                        }
                        endermite.addCommandTag("custom_endermite");
                    }
                }
            }
        });
    }
}
