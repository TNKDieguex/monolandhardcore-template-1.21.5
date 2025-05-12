package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.dieguex.monoland.mobGeneration.mobsAbilities.SpiderHelper;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;

public class SpiderMod {
    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof SpiderEntity spider)) {
                return;
            }
            if (spider.getCommandTags().contains("custom_spider")) {
                return;
            }
            // effects
            List<RegistryEntry<StatusEffect>> statusEffects = new ArrayList<>(List.of(
                    StatusEffects.SPEED,
                    StatusEffects.STRENGTH,
                    StatusEffects.JUMP_BOOST,
                    StatusEffects.GLOWING,
                    StatusEffects.REGENERATION,
                    StatusEffects.INVISIBILITY,
                    StatusEffects.SLOW_FALLING,
                    StatusEffects.RESISTANCE));
            // day 22
            if (ModTimeManager.hasPassedDays(20)) {
                statusEffects.remove(StatusEffects.GLOWING);
            }
            // día 14
            if (ModTimeManager.hasPassedDays(14)) {
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 5);

                if (spider.getStatusEffects().isEmpty()) {
                    EnchantAndEffectsUtils.applyEffectsToMob(spider, chosen);
                }
                if (!(spider instanceof CaveSpiderEntity)) {
                    CaveSpiderEntity caveSpider = EntityType.CAVE_SPIDER.create(
                            (ServerWorld) world,
                            null,
                            spider.getBlockPos(),
                            SpawnReason.EVENT,
                            true,
                            false);

                    if (caveSpider == null) {
                        return;
                    }
                    if (caveSpider != null) {
                        // Posición + efectos
                        caveSpider.setYaw(spider.getYaw());
                        caveSpider.setPitch(spider.getPitch());
                        for (StatusEffectInstance effect : spider.getStatusEffects()) {
                            caveSpider.addStatusEffect(effect);
                        }

                        spider.discard();
                        world.spawnEntity(caveSpider);
                        return;
                    }
                }
            }
            // día 8
            if (ModTimeManager.hasPassedDays(8)) {
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 5);
                if (spider.getStatusEffects().isEmpty()) {
                    EnchantAndEffectsUtils.applyEffectsToMob(spider, chosen);
                }
                SpiderHelper.addSkeletonRider(world, spider);
                return;
            }
            // día 5
            if (ModTimeManager.hasPassedDays(5)) {
                int count = 3 + new Random().nextInt(3);
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, count);
                if (spider.getStatusEffects().isEmpty()) {
                    EnchantAndEffectsUtils.applyEffectsToMob(spider, chosen);
                }
                SpiderHelper.addSkeletonRider(world, spider);
                return;
            }
            // día 0
            if (ModTimeManager.hasPassedDays(0)) {
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 3);
                if (spider.getStatusEffects().isEmpty()) {
                    EnchantAndEffectsUtils.applyEffectsToMob(spider, chosen);
                }
                return;
            }
        });
        ServerLivingEntityEvents.AFTER_DAMAGE
                .register((damagedEntity, source, originalHealth, damageTaken, wasBlocked) -> {
                    if (!(damagedEntity instanceof LivingEntity target))
                        return;
                    if (!ModTimeManager.hasPassedDays(20))
                        return;

                    Entity attacker = source.getAttacker();
                    if (attacker instanceof SpiderEntity || attacker instanceof CaveSpiderEntity) {
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 240, 2)); // 6s, nivel 3
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0)); // 10s
                    }
                });
    }

    public static List<RegistryEntry<StatusEffect>> pickRandom(List<RegistryEntry<StatusEffect>> pool, int count) {
        List<RegistryEntry<StatusEffect>> copy = new ArrayList<>(pool);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }

    public static int getLevel(RegistryEntry<StatusEffect> effect) {
        if (effect == StatusEffects.SPEED)
            return 2;
        if (effect == StatusEffects.STRENGTH)
            return 3;
        if (effect == StatusEffects.JUMP_BOOST)
            return 4;
        if (effect == StatusEffects.REGENERATION)
            return 3;
        if (effect == StatusEffects.RESISTANCE)
            return 2;
        if (effect == StatusEffects.POISON)
            return 2;
        return 0; // invisibility, glowing, etc.
    }
}
