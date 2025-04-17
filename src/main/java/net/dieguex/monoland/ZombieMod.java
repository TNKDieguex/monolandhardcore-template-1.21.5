package net.dieguex.monoland;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.EquipmentSlot;

import java.util.Map;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ZombieMod {
    // Armour and weapons
    private static ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
    private static ItemStack chestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
    private static ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
    private static ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
    private static ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);

    // Enchantments

    public static void register() {
        System.out.println("El mod está vivo!");

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;
            // check if the entity is a zombie so we can equip it with the diamond armor
            if (entity instanceof ZombieEntity zombie) {

                // zombie equipment
                zombie.equipStack(EquipmentSlot.MAINHAND, sword);
                zombie.equipStack(EquipmentSlot.HEAD, helmet);
                zombie.equipStack(EquipmentSlot.CHEST, chestplate);
                zombie.equipStack(EquipmentSlot.LEGS, leggings);
                zombie.equipStack(EquipmentSlot.FEET, boots);

                // work to the logic of the baby zombie
                // zombie.discard();

                // ZombieEntity baby = new ZombieEntity(EntityType.ZOMBIE, world);
                // baby.setBaby(true);
                // baby.setCustomNameVisible(true);
                // baby.setCustomName(net.minecraft.text.Text.of("Baby Zombie"));
                // baby.setPosition(zombie.getX(), zombie.getY(), zombie.getZ());

                // world.spawnEntity(baby);
            }
        });
    }

}
