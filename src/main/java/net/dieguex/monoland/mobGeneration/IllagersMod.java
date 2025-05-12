package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class IllagersMod {
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
            if (entity.getType() == EntityType.PILLAGER) {
                if (ModTimeManager.hasPassedDays(10)) {
                    illager.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, -1, 2, false, false,
                            false));
                    ItemStack crossbow = illager.getEquippedStack(EquipmentSlot.MAINHAND);
                    if (!crossbow.isEmpty()) {
                        EnchantUtils.applyMultiple(world,
                                crossbow, new Object[][] {
                                        { "quick_charge", 10 }
                                });
                    }
                    preventItemDrops(illager);
                    illager.addCommandTag("custom_illager");
                }
            }
        });
    }

    private static void preventItemDrops(MobEntity illager) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            illager.setEquipmentDropChance(slot, 0.0f);
        }
    }
}
