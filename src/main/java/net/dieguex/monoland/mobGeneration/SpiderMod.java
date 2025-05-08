package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.dieguex.monoland.mobGeneration.mobsAbilities.SpiderHelper;
import net.dieguex.monoland.timeManager.ModTimeManager;

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
            List<RegistryEntry<StatusEffect>> statusEffectsExtra = List.of(
                    StatusEffects.POISON,
                    StatusEffects.NAUSEA);
            // day 22
            if (ModTimeManager.hasPassedDays(16)) {
                statusEffects.addAll(statusEffectsExtra);
                statusEffects.remove(StatusEffects.GLOWING);
            }
            // day 18
            if (ModTimeManager.hasPassedDays(12)) {
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 5);

                if (spider.getStatusEffects().isEmpty()) {
                    applyEffectsToSpider(spider, chosen);
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

                        world.spawnEntity(caveSpider);
                        spider.discard();
                        return;
                    }
                }
            }
            // day 9
            if (ModTimeManager.hasPassedDays(9)) {
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 5);
                if (spider.getStatusEffects().isEmpty()) {
                    applyEffectsToSpider(spider, chosen);
                }
                SpiderHelper.addSkeletonRider(world, spider);
                return;
            }
            // day 6
            if (ModTimeManager.hasPassedDays(6)) {
                int count = 3 + new Random().nextInt(3);
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, count);
                if (spider.getStatusEffects().isEmpty()) {
                    applyEffectsToSpider(spider, chosen);
                }
                SpiderHelper.addSkeletonRider(world, spider);
                return;
            }
            // day 0
            if (ModTimeManager.hasPassedDays(0)) {
                List<RegistryEntry<StatusEffect>> chosen = pickRandom(statusEffects, 3);
                if (spider.getStatusEffects().isEmpty()) {
                    applyEffectsToSpider(spider, chosen);
                }
                return;
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
        if (effect == StatusEffects.POISON)
            return 3;
        return 0; // invisibility, glowing, etc.
    }

    private static void applyEffectsToSpider(SpiderEntity spider, List<RegistryEntry<StatusEffect>> effects) {
        for (RegistryEntry<StatusEffect> effect : effects) {
            spider.addStatusEffect(new StatusEffectInstance(effect, 1000000, getLevel(effect), false, false, false));
        }
    }

}
