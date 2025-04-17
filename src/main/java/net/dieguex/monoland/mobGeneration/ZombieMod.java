package net.dieguex.monoland.mobGeneration;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.EquipmentSlot;

import java.rmi.registry.Registry;
import java.util.Map;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

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
            if (!(world instanceof ServerWorld))
                return;
            if (!(entity instanceof ZombieEntity zombie))
                return;

            // 🔹 Casco
            // ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
            // ItemEnchantmentsComponent.Builder helmetEnchantments = new
            // ItemEnchantmentsComponent.Builder();
            // helmetEnchantments.add(Enchantments.PROTECTION, 4);
            // helmet.set(DataComponentTypes.ENCHANTMENTS, helmetEnchantments.build());
            // helmet.set(DataComponentTypes.DROP_CHANCES, 0.0f);

            // // 🔹 Pechera
            // ItemStack chest = new ItemStack(Items.DIAMOND_CHESTPLATE);
            // ItemEnchantmentsComponent.Builder chestEnchantments = new
            // ItemEnchantmentsComponent.Builder();
            // chestEnchantments.add(Enchantments.PROTECTION, "4");
            // chest.set(DataComponentTypes.ENCHANTMENTS, chestEnchantments.build());
            // chest.set(DataComponentTypes.DROP_CHANCES, 0.0f);

            // // 🔹 Pantalones
            // ItemStack legs = new ItemStack(Items.DIAMOND_LEGGINGS);
            // ItemEnchantmentsComponent.Builder legsEnchantments = new
            // ItemEnchantmentsComponent.Builder();
            // legsEnchantments.add(Enchantments.PROTECTION, "4");
            // legs.set(DataComponentTypes.ENCHANTMENTS, legsEnchantments.build());
            // legs.set(DataComponentTypes.DROP_CHANCES, 0.0f);

            // // 🔹 Botas
            // ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
            // ItemEnchantmentsComponent.Builder bootsEnchantments = new
            // ItemEnchantmentsComponent.Builder();
            // bootsEnchantments.add(Enchantments.PROTECTION, "4");
            // boots.set(DataComponentTypes.ENCHANTMENTS, bootsEnchantments.build());
            // boots.set(DataComponentTypes.DROP_CHANCES, 0.0f);

            // // 🔹 Espada
            // ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
            // ItemEnchantmentsComponent.Builder swordEnchantments = new
            // ItemEnchantmentsComponent.Builder();
            // swordEnchantments.add(Enchantments.SHARPNESS, "5");
            // sword.set(DataComponentTypes.ENCHANTMENTS, swordEnchantments.build());
            // sword.set(DataComponentTypes.DROP_CHANCES, 0.0f);

            // 🧠 Equipar al zombie
            zombie.equipStack(EquipmentSlot.HEAD, helmet);
            zombie.equipStack(EquipmentSlot.CHEST, chestplate);
            zombie.equipStack(EquipmentSlot.LEGS, leggings);
            zombie.equipStack(EquipmentSlot.FEET, boots);
            zombie.equipStack(EquipmentSlot.MAINHAND, sword);
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
