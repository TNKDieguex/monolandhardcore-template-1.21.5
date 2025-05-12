package net.dieguex.monoland.mobGeneration;

import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class RavagersMod {

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            // armadura cota de malla
            ItemStack helmet = new ItemStack(Items.CHAINMAIL_HELMET);
            ItemStack chest = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
            ItemStack leggings = new ItemStack(Items.CHAINMAIL_LEGGINGS);
            ItemStack boots = new ItemStack(Items.CHAINMAIL_BOOTS);
            // cota de malla protección 2
            EnchantAndEffectsUtils.applyEnchantment(world, helmet, EnchantAndEffectsUtils.key("protection"), 2);
            EnchantAndEffectsUtils.applyEnchantment(world, chest, EnchantAndEffectsUtils.key("protection"), 2);
            EnchantAndEffectsUtils.applyEnchantment(world, leggings, EnchantAndEffectsUtils.key("protection"), 2);
            EnchantAndEffectsUtils.applyEnchantment(world, boots, EnchantAndEffectsUtils.key("protection"), 2);
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof RavagerEntity ravager)) {
                return;
            }
            if (ravager.getCommandTags().contains("custom_ravager") || ravager.getCommandTags()
                    .contains("custom_ravager_ultra")) {
                return;
            }
            // día 20
            if (ModTimeManager.hasPassedDays(20)) {
                ravager.addCommandTag("custom_ravager_ultra_to_generate");
            } else if (ModTimeManager.hasPassedDays(14)) {
                Random random = world.getRandom();
                if (random.nextInt(100) < 25) {
                    ravager.addCommandTag("custom_ravager_ultra_to_generate");
                    return;
                }
            }

            // día 14
            if (ravager.getCommandTags().contains("custom_ravager_ultra_to_generate")) {
                ravager.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 1, false, false));
                ravager.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 1, false, false));
                ravager.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(250);
                ravager.setHealth(250.0f);
                ZombifiedPiglinEntity zombifiedPiglin = EntityType.ZOMBIFIED_PIGLIN.create(world, null,
                        ravager.getBlockPos(), SpawnReason.EVENT, true, false);
                if (zombifiedPiglin != null) {
                    zombifiedPiglin.refreshPositionAndAngles(ravager.getX(), ravager.getY(), ravager.getZ(), 0, 0);
                    zombifiedPiglin.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(75.0);
                    zombifiedPiglin.setHealth(75.0f);

                    zombifiedPiglin.setPersistent();
                    ZombifiedPiglinMod.equipArmor(zombifiedPiglin, List.of(helmet, chest, leggings, boots));
                    ItemStack woodenSword = new ItemStack(Items.WOODEN_SWORD);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            woodenSword, new Object[][] {
                                    { "punch", 10 }
                            });
                    zombifiedPiglin.equipStack(EquipmentSlot.MAINHAND, woodenSword);
                    zombifiedPiglin.addCommandTag("Manuelito_Esclavo");
                    zombifiedPiglin.startRiding(ravager, true);
                    world.spawnEntity(zombifiedPiglin);
                }
                ravager.addCommandTag("custom_ravager_ultra");
                return;
            } else if (ModTimeManager.hasPassedDays(8)) {
                // día 8
                ravager.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 1, false, false));
                ravager.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 0, false, false));
                ravager.addCommandTag("custom_ravager");
            }

        });
    }
}
