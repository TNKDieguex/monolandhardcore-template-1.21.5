package net.dieguex.monoland.mobGeneration.mobsAbilities;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
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

        skeleton.addCommandTag("custom_skeleton");

        skeleton.refreshPositionAndAngles(pos, 0, 0);
        switch (role) {
            case GUERRERO -> {
                skeleton.setCustomName(Text.literal("§bGuerrero"));
                if (ModTimeManager.hasPassedDays(17)) {
                    setMaxHealth(skeleton, 100);
                } else {
                    setMaxHealth(skeleton, 20);
                }
                skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                equipArmor(skeleton, List.of(
                        new ItemStack(Items.DIAMOND_HELMET),
                        new ItemStack(Items.DIAMOND_CHESTPLATE),
                        new ItemStack(Items.DIAMOND_LEGGINGS),
                        new ItemStack(Items.DIAMOND_BOOTS)));
            }
            case ASESINO -> {
                skeleton.setCustomName(Text.literal("§6Asesino"));
                if (ModTimeManager.hasPassedDays(19)) {
                    setMaxHealth(skeleton, 60);
                } else {
                    setMaxHealth(skeleton, 40);
                }
                skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STICK));
                equipArmor(skeleton, List.of(
                        new ItemStack(Items.GOLDEN_HELMET),
                        new ItemStack(Items.GOLDEN_CHESTPLATE),
                        new ItemStack(Items.GOLDEN_LEGGINGS),
                        new ItemStack(Items.GOLDEN_BOOTS)));
            }
            case TACTICO -> {
                skeleton.setCustomName(Text.literal("§7Táctico"));
                if (ModTimeManager.hasPassedDays(19)) {
                    setMaxHealth(skeleton, 60);
                } else if (ModTimeManager.hasPassedDays(12)) {
                    setMaxHealth(skeleton, 20);
                } else {
                    setMaxHealth(skeleton, 40);
                }
                skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                equipArmor(skeleton, List.of(
                        new ItemStack(Items.CHAINMAIL_HELMET),
                        new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                        new ItemStack(Items.CHAINMAIL_LEGGINGS),
                        new ItemStack(Items.CHAINMAIL_BOOTS)));
            }
            case INFERNAL -> {
                skeleton.setCustomName(Text.literal("§cInfernal"));
                if (ModTimeManager.hasPassedDays(19)) {
                    setMaxHealth(skeleton, 100);
                } else {
                    setMaxHealth(skeleton, 20);
                }
                skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
                equipArmor(skeleton, List.of(
                        new ItemStack(Items.IRON_HELMET),
                        new ItemStack(Items.IRON_CHESTPLATE),
                        new ItemStack(Items.IRON_LEGGINGS),
                        new ItemStack(Items.IRON_BOOTS)));
            }
            case PESADILLA -> {
                skeleton.setCustomName(Text.literal("§4Pesadilla"));
                if (ModTimeManager.hasPassedDays(19)) {
                    setMaxHealth(skeleton, 60);
                } else if (ModTimeManager.hasPassedDays(12)) {
                    setMaxHealth(skeleton, 20);
                } else {
                    setMaxHealth(skeleton, 40);
                }
                skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                equipArmor(skeleton, List.of(
                        coloredLeather(Items.LEATHER_HELMET),
                        coloredLeather(Items.LEATHER_CHESTPLATE),
                        coloredLeather(Items.LEATHER_LEGGINGS),
                        coloredLeather(Items.LEATHER_BOOTS)));
            }
        }
        skeleton.setCustomNameVisible(true);
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
