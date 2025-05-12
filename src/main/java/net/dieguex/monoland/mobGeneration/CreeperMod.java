package net.dieguex.monoland.mobGeneration;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.world.ServerWorld;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.minecraft.entity.effect.StatusEffectInstance;

public class CreeperMod {

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof CreeperEntity creeper)) {
                return;
            }
            if (ModTimeManager.hasPassedDays(5)) {
                ServerWorld serverWorld = (ServerWorld) world;

                if (serverWorld.getRegistryKey() == ServerWorld.OVERWORLD
                        || serverWorld.getRegistryKey() == ServerWorld.END) {

                    NbtCompound tag = new NbtCompound();
                    creeper.writeNbt(tag);
                    tag.putBoolean("powered", true);
                    creeper.readNbt(tag);
                    if (ModTimeManager.hasPassedDays(10)) {
                        creeper.addStatusEffect(
                                new StatusEffectInstance(StatusEffects.SPEED, -1, 1, false, false, false));
                        creeper.addStatusEffect(
                                new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 1, false, false, false));
                    }
                    creeper.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.INVISIBILITY, -1, 0, false, false, false));

                }
            }
        });
    }

}
