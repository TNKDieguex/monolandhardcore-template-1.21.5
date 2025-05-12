package net.dieguex.monoland.mobGeneration.mobsAbilities;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public class SkeletonFactory {
    private static final Random random = new Random();

    public enum SkeletonRole {
        GUERRERO, ASESINO, TACTICO, INFERNAL, PESADILLA
    }

    public static MobEntity spawnCustomSkeleton(ServerWorld world, BlockPos pos) {
        SkeletonRole role = SkeletonRole.values()[random.nextInt(SkeletonRole.values().length)];
        return createSkeletonByRole(role, world, pos);
    }

    public static MobEntity createSkeletonByRole(SkeletonRole role, ServerWorld world, BlockPos pos) {
        MobEntity skeleton;
        // Weapons
        ItemStack stick = new ItemStack(Items.STICK);
        ItemStack bow = new ItemStack(Items.BOW);
        ItemStack diamonAxe = new ItemStack(Items.DIAMOND_AXE);
        ItemStack ironAxe = new ItemStack(Items.IRON_AXE);

        if (role == SkeletonRole.TACTICO || role == SkeletonRole.PESADILLA) {
            skeleton = EntityType.WITHER_SKELETON.create(
                    world,
                    null,
                    pos,
                    SpawnReason.EVENT,
                    true,
                    false);
        } else {
            skeleton = EntityType.SKELETON.create(
                    world,
                    null,
                    pos,
                    SpawnReason.EVENT,
                    true,
                    false);
        }
        if (skeleton == null)
            return null;

        ItemStack tippedArrow = new ItemStack(Items.TIPPED_ARROW, 1);
        skeleton.addCommandTag("custom_skeleton");
        if (ModTimeManager.hasPassedDays(10)) {
            tippedArrow.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.STRONG_HARMING));
        }
        int protectionLevel = ModTimeManager.hasPassedDays(23) ? 5 : 4;

        ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
        ItemStack chest = new ItemStack(Items.DIAMOND_CHESTPLATE);
        ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
        ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);

        skeleton.refreshPositionAndAngles(pos, 0, 0);
        switch (role) {
            // esqueleto diamante
            case GUERRERO -> {
                // día 20
                if (ModTimeManager.hasPassedDays(20)) {
                    setMaxHealth(skeleton, 100);
                    skeleton.equipStack(EquipmentSlot.OFFHAND, tippedArrow);
                } else if (ModTimeManager.hasPassedDays(10)) {
                    // día 10
                    setMaxHealth(skeleton, 40);
                    skeleton.equipStack(EquipmentSlot.OFFHAND, tippedArrow);
                } else {
                    setMaxHealth(skeleton, 20);
                }
                // día 10
                if (ModTimeManager.hasPassedDays(10)) {
                    EnchantAndEffectsUtils.applyEnchantment(world, helmet, EnchantAndEffectsUtils.key("protection"),
                            protectionLevel);
                    EnchantAndEffectsUtils.applyEnchantment(world, chest, EnchantAndEffectsUtils.key("protection"),
                            protectionLevel);
                    EnchantAndEffectsUtils.applyEnchantment(world, leggings, EnchantAndEffectsUtils.key("protection"),
                            protectionLevel);
                    EnchantAndEffectsUtils.applyEnchantment(world, boots, EnchantAndEffectsUtils.key("protection"),
                            protectionLevel);

                }
                // día 0
                skeleton.equipStack(EquipmentSlot.MAINHAND, bow);
                equipArmor(skeleton, List.of(helmet, chest, leggings, boots));
            }
            // esqueleto oro
            case ASESINO -> {
                // día 20
                if (ModTimeManager.hasPassedDays(20)) {
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            stick, new Object[][] {
                                    { "sharpness", 50 }
                            });
                } else if (ModTimeManager.hasPassedDays(10)) {
                    // día 10
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            stick, new Object[][] {
                                    { "sharpness", 25 }
                            });
                } else {
                    // día 0
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            stick, new Object[][] {
                                    { "sharpness", 20 }
                            });
                }
                // día 10
                if (ModTimeManager.hasPassedDays(10)) {
                    skeleton.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.SPEED, -1, 1, false, false, false));
                }
                // día 0
                skeleton.equipStack(EquipmentSlot.MAINHAND, stick);
                equipArmor(skeleton, List.of(
                        new ItemStack(Items.GOLDEN_HELMET),
                        new ItemStack(Items.GOLDEN_CHESTPLATE),
                        new ItemStack(Items.GOLDEN_LEGGINGS),
                        new ItemStack(Items.GOLDEN_BOOTS)));
            }
            // esqueleto cota de malla
            case TACTICO -> {
                if (ModTimeManager.hasPassedDays(20)) {
                    // día 20
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            bow, new Object[][] {
                                    { "punch", 50 },
                                    { "power", 40 }
                            });
                    skeleton.equipStack(EquipmentSlot.OFFHAND, tippedArrow);
                } else if (ModTimeManager.hasPassedDays(10)) {
                    // día 10
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            bow, new Object[][] {
                                    { "punch", 30 },
                                    { "power", 25 }
                            });
                    skeleton.equipStack(EquipmentSlot.OFFHAND, tippedArrow);
                } else {
                    // día 0
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            bow, new Object[][] {
                                    { "punch", 20 },
                            });
                }
                skeleton.equipStack(EquipmentSlot.MAINHAND, bow);
                equipArmor(skeleton, List.of(
                        new ItemStack(Items.CHAINMAIL_HELMET),
                        new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                        new ItemStack(Items.CHAINMAIL_LEGGINGS),
                        new ItemStack(Items.CHAINMAIL_BOOTS)));
            }
            // esqueleto hierro
            case INFERNAL -> {
                // día 20
                if (ModTimeManager.hasPassedDays(20)) {
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            diamonAxe, new Object[][] {
                                    { "fire_aspect", 20 },
                                    { "sharpness", 25 }
                            });
                    skeleton.equipStack(EquipmentSlot.MAINHAND, diamonAxe);
                } else if (ModTimeManager.hasPassedDays(10)) {
                    // día 10
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            diamonAxe, new Object[][] {
                                    { "fire_aspect", 10 }
                            });
                    skeleton.equipStack(EquipmentSlot.MAINHAND, diamonAxe);
                } else {
                    // día 0
                    setMaxHealth(skeleton, 20);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            ironAxe, new Object[][] {
                                    { "fire_aspect", 2 }
                            });
                    skeleton.equipStack(EquipmentSlot.MAINHAND, ironAxe);
                }
                equipArmor(skeleton, List.of(
                        new ItemStack(Items.IRON_HELMET),
                        new ItemStack(Items.IRON_CHESTPLATE),
                        new ItemStack(Items.IRON_LEGGINGS),
                        new ItemStack(Items.IRON_BOOTS)));
            }
            // esqueleto cuero
            case PESADILLA -> {
                if (ModTimeManager.hasPassedDays(20)) {
                    // día 20
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            bow, new Object[][] {
                                    { "power", 60 },
                            });
                    skeleton.equipStack(EquipmentSlot.OFFHAND, tippedArrow);
                } else if (ModTimeManager.hasPassedDays(10)) {
                    // día 10
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            bow, new Object[][] {
                                    { "power", 50 },
                            });
                    skeleton.equipStack(EquipmentSlot.OFFHAND, tippedArrow);
                } else {
                    // día 0
                    setMaxHealth(skeleton, 40);
                    EnchantAndEffectsUtils.applyMultiple(world,
                            bow, new Object[][] {
                                    { "power", 10 },
                            });
                }
                skeleton.equipStack(EquipmentSlot.MAINHAND, bow);
                equipArmor(skeleton, List.of(
                        coloredLeather(Items.LEATHER_HELMET),
                        coloredLeather(Items.LEATHER_CHESTPLATE),
                        coloredLeather(Items.LEATHER_LEGGINGS),
                        coloredLeather(Items.LEATHER_BOOTS)));
            }
        }
        world.spawnEntity(skeleton);
        return skeleton;
    }

    private static void equipArmor(MobEntity skeleton, List<ItemStack> armorPieces) {

        skeleton.equipStack(EquipmentSlot.HEAD, armorPieces.get(0));
        skeleton.equipStack(EquipmentSlot.CHEST, armorPieces.get(1));
        skeleton.equipStack(EquipmentSlot.LEGS, armorPieces.get(2));
        skeleton.equipStack(EquipmentSlot.FEET, armorPieces.get(3));
        preventItemDrops(skeleton);
    }

    private static void setMaxHealth(MobEntity skeleton, float amount) {
        EntityAttributeInstance attr = skeleton.getAttributeInstance(EntityAttributes.MAX_HEALTH);
        if (attr != null)
            attr.setBaseValue(amount);
        skeleton.setHealth(amount);
    }

    private static ItemStack coloredLeather(Item armorItem) {
        ItemStack stack = new ItemStack(armorItem);
        stack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x8B0000)); // rojo oscuro
        return stack;
    }

    private static void preventItemDrops(MobEntity skeleton) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            skeleton.setEquipmentDropChance(slot, 0.0f);
        }
    }

}
