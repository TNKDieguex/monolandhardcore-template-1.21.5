package net.dieguex.monoland.mobGeneration;

import java.util.List;

import net.dieguex.monoland.mixin.MobEntityAccessor;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class EndermanMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof EndermanEntity enderman)) {
                return;
            }
            if (enderman.getCommandTags().contains("custom_enderman")) {
                return;
            }
            if (ModTimeManager.hasPassedDays(25)) {
                enderman.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 9, false, false,
                        false));
                enderman.addCommandTag("custom_enderman");
            } else if (ModTimeManager.hasPassedDays(14)) {
                enderman.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 1, false, false,
                        false));
                enderman.addCommandTag("custom_enderman");
                Random random = world.getRandom();
                if (random.nextInt(100) > 5)
                    return; // 5% de probabilidad de que el enderman ataque
            } else if (ModTimeManager.hasPassedDays(10)) {
                enderman.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 1, false, false,
                        false));
                enderman.addCommandTag("custom_enderman");
            }
            if (ModTimeManager.hasPassedDays(14)) {
                GoalSelector targetSelector = ((MobEntityAccessor) enderman).getTargetSelector();
                targetSelector.add(1, new ActiveTargetGoal<>(enderman, PlayerEntity.class, true));
                List<? extends PlayerEntity> players = enderman.getWorld().getPlayers();

                for (PlayerEntity player : players) {
                    if (player.squaredDistanceTo(enderman) < 10 * 10) { // rango de visión
                        enderman.setTarget(player);
                        enderman.setAttacking(true);
                        enderman.setAngryAt(player.getUuid());
                        break;
                    }
                }
            }
        });
    }
}
