package net.dieguex.monoland.mobGeneration;

import java.util.ArrayList;
import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

public class IllagersMod {
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
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof IllagerEntity illager)) {
                return;
            }
            if (illager.getCommandTags().contains("custom_illager")) {
                return;
            }
            if (ModTimeManager.hasPassedDays(25)) {
                List<RegistryEntry<StatusEffect>> chosen = SpiderMod.pickRandom(statusEffects, 5);
                if (illager.getStatusEffects().isEmpty()) {
                    EnchantAndEffectsUtils.applyEffectsToMob(illager, chosen);
                }
            }
            if (entity.getType() == EntityType.PILLAGER) {
                if (ModTimeManager.hasPassedDays(10)) {
                    illager.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, -1, 2, false, false,
                            false));
                    ItemStack crossbow = illager.getEquippedStack(EquipmentSlot.MAINHAND);
                    if (!crossbow.isEmpty()) {
                        EnchantAndEffectsUtils.applyMultiple(world,
                                crossbow, new Object[][] {
                                        { "quick_charge", 10 }
                                });
                    }
                    preventItemDrops(illager);
                    illager.addCommandTag("custom_illager");
                }
            }
            if (entity.getType() == EntityType.VINDICATOR) {
                ItemStack diamonAxe = new ItemStack(Items.DIAMOND_AXE);
                EnchantAndEffectsUtils.applyMultiple(world,
                        diamonAxe, new Object[][] {
                                { "sharpness", 5 }
                        });
                illager.equipStack(EquipmentSlot.MAINHAND, diamonAxe);
            }
        });
    }

    private static void preventItemDrops(MobEntity illager) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            illager.setEquipmentDropChance(slot, 0.0f);
        }
    }
}
