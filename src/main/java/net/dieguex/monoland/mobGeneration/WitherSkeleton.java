package net.dieguex.monoland.mobGeneration;

import java.util.List;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class WitherSkeleton {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof WitherSkeletonEntity witherSkeleton)) {
                return;
            }
            if (witherSkeleton.getCommandTags().contains("custom_witherSkeleton_emperador")) {
                return;
            }
            Random random = world.getRandom();
            if (ModTimeManager.hasPassedDays(20) && random.nextInt(50) == 1
                    && world.getRegistryKey() == ServerWorld.NETHER) {
                witherSkeleton.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(40);
                witherSkeleton.setHealth(40.0f);
                ItemStack bow = new ItemStack(Items.BOW);
                EnchantAndEffectsUtils.applyMultiple(
                        world,
                        bow, new Object[][] {
                                { "power", 100 }
                        });
                equipArmor(witherSkeleton, List.of(new ItemStack(Items.BLACK_BANNER),
                        new ItemStack(Items.GOLDEN_CHESTPLATE),
                        new ItemStack(Items.GOLDEN_LEGGINGS),
                        new ItemStack(Items.GOLDEN_BOOTS)));
                witherSkeleton.equipStack(EquipmentSlot.MAINHAND, bow);
                witherSkeleton.addCommandTag("custom_witherSkeleton_emperador");
            }
        });
    }

    private static void equipArmor(MobEntity skeleton, List<ItemStack> armorPieces) {

        skeleton.equipStack(EquipmentSlot.HEAD, armorPieces.get(0));
        skeleton.equipStack(EquipmentSlot.CHEST, armorPieces.get(1));
        skeleton.equipStack(EquipmentSlot.LEGS, armorPieces.get(2));
        skeleton.equipStack(EquipmentSlot.FEET, armorPieces.get(3));
        preventItemDrops(skeleton);
    }

    private static void preventItemDrops(MobEntity skeleton) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            skeleton.setEquipmentDropChance(slot, 0.0f);
        }
    }
}
