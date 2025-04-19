package net.dieguex.monoland.mobGeneration;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
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
            if (ModTimeManager.hasPassedDays(6)) {
                creeper.setCustomNameVisible(true);
                creeper.setCustomName(Text.literal("§2Creeper Invisible con efecto"));
                NbtCompound tag = new NbtCompound();
                creeper.writeNbt(tag);
                tag.putBoolean("powered", true);
                creeper.readNbt(tag);
                creeper.addStatusEffect(
                        new StatusEffectInstance(StatusEffects.INVISIBILITY, 1000000, 1, false, false, false));
            }
        });
    }

}
