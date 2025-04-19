package net.dieguex.monoland.mobGeneration;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class ZombieMod {
    // Armour and weapons
    private static ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
    private static ItemStack chestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
    private static ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
    private static ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
    private static ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);

    // Enchantments

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof ZombieEntity zombie)) {
                return;
            }

            // Retrieve Enchantment from Registry using RegistryKey
            RegistryKey<Enchantment> enchantmentKey = Enchantments.PROTECTION;

            EnchantmentHelper enchantmentHelper = new EnchantmentHelper();

            // Equip zombie with enchanted items, difficulty day 6
            if (ModTimeManager.hasPassedDays(6)) {
                zombie.setCustomNameVisible(true);
                zombie.setCustomName(Text.literal("§1Zombie Protector"));
                zombie.equipStack(EquipmentSlot.HEAD, helmet);
                zombie.equipStack(EquipmentSlot.CHEST, chestplate);
                zombie.equipStack(EquipmentSlot.LEGS, leggings);
                zombie.equipStack(EquipmentSlot.FEET, boots);
                zombie.equipStack(EquipmentSlot.MAINHAND, sword);
            }
        });

        // work to the logic of the baby zombie
        // zombie.discard();

        // ZombieEntity baby = new ZombieEntity(EntityType.ZOMBIE, world);
        // baby.setBaby(true);
        // baby.setCustomNameVisible(true);
        // baby.setCustomName(net.minecraft.text.Text.of("Baby Zombie"));
        // baby.setPosition(zombie.getX(), zombie.getY(), zombie.getZ());

        // world.spawnEntity(baby);
    }

}
