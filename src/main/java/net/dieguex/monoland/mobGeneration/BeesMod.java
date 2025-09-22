package net.dieguex.monoland.mobGeneration;

import java.util.List;

import net.dieguex.monoland.mixin.MobEntityAccessor;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class BeesMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;
            if (!(entity instanceof BeeEntity bee))
                return;
            if (bee.getCommandTags().contains("custom_bee"))
                return;

            // Día 8: abejas mejoradas y hostiles
            if (ModTimeManager.hasPassedDays(8)) {
                bee.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 1, false, false, false));
                bee.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 1, false, false, false));

                GoalSelector goalSelector = ((MobEntityAccessor) bee).getGoalSelector();
                GoalSelector targetSelector = ((MobEntityAccessor) bee).getTargetSelector();

                // Añadir IA de ataque cuerpo a cuerpo y objetivo de jugadores
                goalSelector.add(1, new MeleeAttackGoal(bee, 1.0D, false));
                targetSelector.add(1, new ActiveTargetGoal<>(bee, PlayerEntity.class, true));

                // Buscar jugador cercano y atacarlo
                List<? extends PlayerEntity> players = bee.getWorld().getPlayers();
                for (PlayerEntity player : players) {
                    if (player.squaredDistanceTo(bee) < 100) {
                        bee.setTarget(player);
                        bee.setAttacking(true);
                        bee.setAngryAt(player.getUuid());
                        break;
                    }
                }

                bee.addCommandTag("custom_bee");
            }
        });
    }
}