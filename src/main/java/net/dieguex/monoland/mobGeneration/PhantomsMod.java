package net.dieguex.monoland.mobGeneration;

import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

public class PhantomsMod {
    private static final List<RegistryEntry<StatusEffect>> statusEffects = new ArrayList<>(List.of(
            StatusEffects.SPEED,
            StatusEffects.STRENGTH,
            StatusEffects.JUMP_BOOST,
            StatusEffects.GLOWING,
            StatusEffects.REGENERATION,
            StatusEffects.INVISIBILITY,
            StatusEffects.SLOW_FALLING,
            StatusEffects.RESISTANCE));

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;

            if (entity instanceof PhantomEntity phantom) {
                if (ModTimeManager.hasPassedDays(5) && !phantom.getCommandTags().contains("custom_phantom")) {
                    NbtCompound tag = new NbtCompound();
                    phantom.writeNbt(tag);
                    tag.putInt("size", 8);
                    phantom.readNbt(tag);
                    phantom.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(40.0);
                    phantom.setHealth(40.0f);
                    if (ModTimeManager.hasPassedDays(20)) {
                        List<RegistryEntry<StatusEffect>> chosen = SpiderMod.pickRandom(statusEffects, 5);
                        EnchantAndEffectsUtils.applyEffectsToMob(phantom, chosen);
                    }
                    phantom.addCommandTag("custom_phantom");
                }
                if (ModTimeManager.hasPassedDays(20)) {
                    int sizePhantom = ModTimeManager.hasPassedDays(25) ? 25 : 1;
                    Random random = world.getRandom();
                    if (random.nextInt(100) < sizePhantom) {
                        GhastEntity ghast = EntityType.GHAST.create(world, null,
                                phantom.getBlockPos(), SpawnReason.NATURAL, true, false);
                        if (ghast == null)
                            return;
                        ghast.setYaw(phantom.getYaw());
                        ghast.setPitch(phantom.getPitch());
                        ghast.addCommandTag("custom_ender_ghast_to_generate");
                        phantom.discard();
                        world.spawnEntity(ghast);
                    }
                }
            }
        });

    }
}
