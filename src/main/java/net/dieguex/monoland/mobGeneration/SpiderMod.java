package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffect;
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

            // effects
            List<RegistryEntry<StatusEffect>> statusEffects = List.of(
                    StatusEffects.SPEED,
                    StatusEffects.STRENGTH,
                    StatusEffects.JUMP_BOOST,
                    StatusEffects.GLOWING,
                    StatusEffects.REGENERATION,
                    StatusEffects.INVISIBILITY,
                    StatusEffects.SLOW_FALLING,
                    StatusEffects.RESISTANCE);

            // day 0
            if (ModTimeManager.hasPassedDays(0)) {
                spider.setCustomNameVisible(true);
                spider.setCustomName(Text.literal("Spider day 0"));
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 3);
                for (RegistryEntry<StatusEffect> type : chosen) {
                    spider.addStatusEffect(new StatusEffectInstance(type, 1000000, getLevel(type), false,
                            false, false));
                }
            }

            if (ModTimeManager.hasPassedDays(6)) {
                spider.setCustomNameVisible(true);
                spider.setCustomName(Text.literal("Spider day 6"));
                int count = 3 + new Random().nextInt(3);
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, count);
                for (RegistryEntry<StatusEffect> type : chosen) {
                    spider.addStatusEffect(new StatusEffectInstance(type, 1000000, getLevel(type), false,
                            false, false));
                }
            }

            if (ModTimeManager.hasPassedDays(9)) {
                spider.setCustomNameVisible(true);
                spider.setCustomName(Text.literal("Spider day 9"));
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 5);
                for (RegistryEntry<StatusEffect> type : chosen) {
                    spider.addStatusEffect(new StatusEffectInstance(type, 1000000, getLevel(type), false,
                            false, false));
                }
            }
            if (ModTimeManager.hasPassedDays(18)) {
                spider.setCustomNameVisible(true);
                spider.setCustomName(Text.literal("Spider day 18"));

                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 5);
                for (RegistryEntry<StatusEffect> type : chosen) {
                    spider.addStatusEffect(new StatusEffectInstance(type, 1000000, getLevel(type), false,
                            false, false));
                }

                // it get crashed when i load the world
                // BlockPos pos = spider.getBlockPos();
                // CaveSpiderEntity caveSpider = EntityType.CAVE_SPIDER.create(world,
                // null,
                // pos,
                // SpawnReason.NATURAL,
                // true,
                // false);
                // if (caveSpider != null) {
                // world.spawnEntity(caveSpider);
                // spider.discard();
                // }
            }
        });
    }

    private static List<RegistryEntry<StatusEffect>> pickRandom(List<RegistryEntry<StatusEffect>> pool, int count) {
        List<RegistryEntry<StatusEffect>> copy = new ArrayList<>(pool);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }

    private static int getLevel(RegistryEntry<StatusEffect> effect) {
        if (effect == StatusEffects.SPEED)
            return 3;
        if (effect == StatusEffects.STRENGTH)
            return 4;
        if (effect == StatusEffects.JUMP_BOOST)
            return 5;
        if (effect == StatusEffects.REGENERATION)
            return 4;
        if (effect == StatusEffects.RESISTANCE)
            return 3;
        return 0; // invisibility, glowing, etc.
    }

}
