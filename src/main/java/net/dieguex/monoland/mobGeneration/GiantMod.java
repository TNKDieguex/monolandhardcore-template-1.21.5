package net.dieguex.monoland.mobGeneration;

import java.lang.reflect.Field;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class GiantMod {
    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof GiantEntity zombieGigante) || entity.getType() == EntityType.ZOMBIFIED_PIGLIN
                    || entity.getType() == EntityType.DROWNED) {
                return;
            }
            if (entity.getCommandTags().contains("custom_zombie_giant"))
                return;
            if (ModTimeManager.hasPassedDays(20)) {
                zombieGigante.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(300);
                zombieGigante.setHealth(300.0f);
                zombieGigante.addCommandTag("custom_zombie_giant");
                try {
                    Field goalSelectorField = MobEntity.class.getDeclaredField("goalSelector");
                    goalSelectorField.setAccessible(true);
                    GoalSelector goalSelector = (GoalSelector) goalSelectorField.get(zombieGigante);

                    Field targetSelectorField = MobEntity.class.getDeclaredField("targetSelector");
                    targetSelectorField.setAccessible(true);
                    GoalSelector targetSelector = (GoalSelector) targetSelectorField.get(zombieGigante);

                    // Moverse hacia el jugador
                    goalSelector.add(1, new MeleeAttackGoal(zombieGigante, 1.0D, false));
                    // Mirar alrededor
                    goalSelector.add(2, new LookAroundGoal(zombieGigante));
                    goalSelector.add(3, new WanderAroundFarGoal(zombieGigante, 1.0));

                    // Atacar jugadores
                    targetSelector.add(1, new ActiveTargetGoal<>(zombieGigante, PlayerEntity.class, true));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
