package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.entity.mob.SpiderEntity;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class SpiderMod {
    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof SpiderEntity spider)) {
                return;
            }
            EntityAttributeInstance speed = spider.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
            EntityAttributeInstance health = spider.getAttributeInstance(EntityAttributes.MAX_HEALTH);

            if (ModTimeManager.hasPassedDays(6)) {
                spider.setCustomNameVisible(true);
                spider.setCustomName(Text.literal("§4Araña Sangrienta"));
                spider.setInvisible(true);
                if (speed != null) {
                    speed.setBaseValue(0.3);
                }
                if (health != null) {
                    health.setBaseValue(40.0);
                    spider.setHealth(40.0f);
                }

            }
            if (ModTimeManager.hasPassedDays(12)) {
                spider.setCustomNameVisible(true);
                spider.setCustomName(Text.literal("§2Araña maldita"));
                spider.setInvisible(false);
                if (speed != null) {
                    speed.setBaseValue(0.5);
                }
                if (health != null) {
                    health.setBaseValue(80.0);
                    spider.setHealth(80.0f);
                }
                spider.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1000000, 1, false, false, false));
                spider.addStatusEffect(
                        new StatusEffectInstance(StatusEffects.INVISIBILITY, 1000000, 1, false, false, false));
            }
        });
    }
}
