package net.dieguex.monoland.mobGeneration;

import java.lang.reflect.Field;
import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class BeesMod {
    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof BeeEntity bee)) {
                return;
            }
            // día 5
            if (ModTimeManager.hasPassedDays(8)) {
                bee.addStatusEffect(
                        new StatusEffectInstance(StatusEffects.SPEED, -1, 1, false, false, false));
                bee.addStatusEffect(
                        new StatusEffectInstance(StatusEffects.STRENGTH, -1, 1, false, false, false));
                try {
                    Field targetSelectorField = MobEntity.class.getDeclaredField("targetSelector");
                    targetSelectorField.setAccessible(true);
                    GoalSelector targetSelector = (GoalSelector) targetSelectorField.get(bee);
                    targetSelector.add(1, new ActiveTargetGoal<>(bee, PlayerEntity.class, true));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                List<? extends PlayerEntity> players = bee.getWorld().getPlayers();

                for (PlayerEntity player : players) {
                    if (player.squaredDistanceTo(bee) < 5 * 5) { // rango de visión
                        bee.setTarget(player);
                        bee.setAttacking(true);
                        bee.setAngryAt(player.getUuid());
                        break;
                    }
                }
            }
        });
    }
}
