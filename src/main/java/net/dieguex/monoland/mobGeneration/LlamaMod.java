package net.dieguex.monoland.mobGeneration;

import java.util.List;

import net.dieguex.monoland.mixin.MobEntityAccessor;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.server.world.ServerWorld;

public class LlamaMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof LlamaEntity llama)) {
                return;
            }
            if (entity.getCommandTags().contains("custom_llama"))
                return;

            // día 20
            if (ModTimeManager.hasPassedDays(20)) {
                GoalSelector targetSelector = ((MobEntityAccessor) llama).getTargetSelector();
                targetSelector.add(1, new ActiveTargetGoal<>(llama, PlayerEntity.class, true));
                List<? extends PlayerEntity> players = llama.getWorld().getPlayers();

                for (PlayerEntity player : players) {
                    if (player.squaredDistanceTo(llama) < 8 * 8) { // rango de visión
                        llama.setTarget(player);
                        llama.setAttacking(true);
                        break;
                    }
                }
            }
        });
        ServerLivingEntityEvents.AFTER_DAMAGE
                .register((damagedEntity, source, originalHealth, damageTaken, wasBlocked) -> {
                    if (!(damagedEntity instanceof LivingEntity livingEntity) || !ModTimeManager.hasPassedDays(20))
                        return;

                    if (source.getSource() instanceof LlamaSpitEntity llamaSpit) {
                        if (llamaSpit.getOwner() instanceof LlamaEntity) {
                            livingEntity.addStatusEffect(
                                    new StatusEffectInstance(StatusEffects.POISON, 1200, 2));
                            livingEntity.addStatusEffect(
                                    new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0));
                            livingEntity.addStatusEffect(
                                    new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 0));
                        }
                    }
                });
    }
}
