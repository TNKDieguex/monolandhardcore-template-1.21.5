package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.mixin.MobEntityAccessor;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class GiantMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;
            if (!(entity instanceof GiantEntity zombieGigante))
                return;
            if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN || entity.getType() == EntityType.DROWNED)
                return;
            if (entity.getCommandTags().contains("custom_zombie_giant"))
                return;

            if (ModTimeManager.hasPassedDays(20)) {
                zombieGigante.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(300);
                zombieGigante.setHealth(300.0f);
                zombieGigante.addCommandTag("custom_zombie_giant");

                GoalSelector goalSelector = ((MobEntityAccessor) zombieGigante).getGoalSelector();
                GoalSelector targetSelector = ((MobEntityAccessor) zombieGigante).getTargetSelector();

                goalSelector.add(1, new MeleeAttackGoal(zombieGigante, 1.0D, false));
                goalSelector.add(2, new LookAroundGoal(zombieGigante));
                goalSelector.add(3, new WanderAroundFarGoal(zombieGigante, 1.0));

                targetSelector.add(1, new ActiveTargetGoal<>(zombieGigante, PlayerEntity.class, true));
            }
        });
    }
}
