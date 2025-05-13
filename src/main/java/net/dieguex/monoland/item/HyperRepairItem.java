package net.dieguex.monoland.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HyperRepairItem extends Item {
    private static final int REPAIR_AMOUNT = 200;

    public HyperRepairItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            boolean repairedSomething = false;
            boolean hasHyperArmor = false;

            repairedSomething |= repairIfHyper(player, EquipmentSlot.HEAD, ModItems.HYPER_ESSENCE_HELMET);
            repairedSomething |= repairIfHyper(player, EquipmentSlot.CHEST, ModItems.HYPER_ESSENCE_CHESTPLATE);
            repairedSomething |= repairIfHyper(player, EquipmentSlot.LEGS, ModItems.HYPER_ESSENCE_LEGGINGS);
            repairedSomething |= repairIfHyper(player, EquipmentSlot.FEET, ModItems.HYPER_ESSENCE_BOOTS);

            hasHyperArmor = isWearingAnyHyperArmor(player);

            if (repairedSomething) {
                player.sendMessage(Text.translatable("item.monoland.repair.success"), true);
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_ANVIL_USE,
                        SoundCategory.PLAYERS, 1.0f, 1.2f);
                player.getStackInHand(hand).decrement(1);
            } else if (hasHyperArmor) {
                player.sendMessage(Text.translatable("item.monoland.repair.not_damaged"), true);
            } else {
                player.sendMessage(Text.translatable("item.monoland.repair.wrong_armor"), true);
            }
        }

        return ItemUsage.consumeHeldItem(world, player, hand);
    }

    private boolean repairIfHyper(PlayerEntity player, EquipmentSlot slot, Item expectedItem) {
        ItemStack armor = player.getEquippedStack(slot);

        if (!armor.isEmpty() && armor.isOf(expectedItem) && armor.isDamaged()) {
            int damage = armor.getDamage();
            int repaired = Math.min(REPAIR_AMOUNT, damage);
            armor.setDamage(damage - repaired);
            return true;
        }
        return false;
    }

    private boolean isWearingAnyHyperArmor(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.HYPER_ESSENCE_HELMET)
                || player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.HYPER_ESSENCE_CHESTPLATE)
                || player.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.HYPER_ESSENCE_LEGGINGS)
                || player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.HYPER_ESSENCE_BOOTS);
    }
}
