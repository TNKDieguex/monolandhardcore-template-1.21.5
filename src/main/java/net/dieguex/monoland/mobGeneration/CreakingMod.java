package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreakingEntity;
import net.minecraft.server.world.ServerWorld;

public class CreakingMod {
    public static void register() {
        // Register the event listener for Creaking entity loading
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;

            if (entity instanceof CreakingEntity creaking) {
                if (ModTimeManager.hasPassedDays(5)) {
                    if (creaking.getCommandTags() == null
                            || !creaking.getCommandTags().contains("custom_creaking")) {
                        EntityAttributeInstance damageAttr = creaking
                                .getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
                        if (damageAttr != null) {
                            double baseDamage = damageAttr.getBaseValue();
                            damageAttr.setBaseValue(baseDamage * 6);
                        }
                        creaking.addStatusEffect(
                                new StatusEffectInstance(StatusEffects.SPEED, -1, 1, false, false));

                        creaking.addCommandTag("custom_creaking");
                    }
                }
            }
        });
    }
}
