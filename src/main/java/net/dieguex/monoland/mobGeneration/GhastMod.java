package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

public class GhastMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof GhastEntity ghast)) {
                return;
            }

            // ghast changes in the end
            if (world.getRegistryKey() == ServerWorld.END) {
                final float VIDA_MAX = 200;
                ghast.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(VIDA_MAX);
                ghast.setHealth(VIDA_MAX);

                if (ModTimeManager.hasPassedDays(10)) {
                    ghast.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.SPEED, -1, 2, false, false, false));
                    ghast.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 2, false, false, false));
                }
            }
            // ghast changes in the nether
            if (world.getRegistryKey() == ServerWorld.NETHER) {
                float pointsDeVie = randomMinMax(40, 60);
                float explosionRatio = randomMinMax(3, 5);

                ghast.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(pointsDeVie);
                ghast.setHealth(pointsDeVie);
                if (ModTimeManager.hasPassedDays(14)) {

                    NbtCompound nbt = new NbtCompound();
                    ghast.writeNbt(nbt);
                    nbt.putByte("ExplosionPower", (byte) 1);
                    ghast.readNbt(nbt);

                    ServerLivingEntityEvents.AFTER_DAMAGE
                            .register((damagedEntity, source, originalHealth, damageTaken, wasBlocked) -> {
                                if (!(damagedEntity instanceof LivingEntity livingEntity))
                                    return;

                                if (source.getSource() instanceof FireballEntity fireball) {
                                    if (fireball.getOwner() instanceof GhastEntity) {
                                        livingEntity.addStatusEffect(
                                                new StatusEffectInstance(StatusEffects.LEVITATION, 100, 49));
                                        livingEntity.addStatusEffect(
                                                new StatusEffectInstance(StatusEffects.WITHER, 400, 4));
                                    }
                                }
                            });

                } else if (ModTimeManager.hasPassedDays(8)) {
                    NbtCompound nbt = new NbtCompound();
                    ghast.writeNbt(nbt);
                    nbt.putByte("ExplosionPower", (byte) explosionRatio);
                    ghast.readNbt(nbt);
                }
            }
        });
    }

    private static float randomMinMax(int min, int max) {
        return (float) (min + Math.random() * (max - min));
    }

}
