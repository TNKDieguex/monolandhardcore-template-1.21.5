package net.dieguex.monoland.mobGeneration;

import java.lang.reflect.Field;
import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;

public class ZombifiedPiglinMod {
    private static ItemStack diamondHelmet = new ItemStack(Items.DIAMOND_HELMET);
    private static ItemStack diamondChestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
    private static ItemStack diamondLeggings = new ItemStack(Items.DIAMOND_LEGGINGS);
    private static ItemStack diamondBoots = new ItemStack(Items.DIAMOND_BOOTS);
    private static ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof ZombifiedPiglinEntity zombifiedPiglin)) {
                return;
            }
            if (entity.getCommandTags().contains("custom_zombified_piglin"))
                return;

            // día 5
            if (ModTimeManager.hasPassedDays(5)) {
                try {
                    Field targetSelectorField = MobEntity.class.getDeclaredField("targetSelector");
                    targetSelectorField.setAccessible(true);
                    GoalSelector targetSelector = (GoalSelector) targetSelectorField.get(zombifiedPiglin);
                    targetSelector.add(1, new ActiveTargetGoal<>(zombifiedPiglin, PlayerEntity.class, true));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                List<? extends PlayerEntity> players = zombifiedPiglin.getWorld().getPlayers();

                for (PlayerEntity player : players) {
                    if (player.squaredDistanceTo(zombifiedPiglin) < 10 * 10) { // rango de visión
                        zombifiedPiglin.setTarget(player);
                        zombifiedPiglin.setAttacking(true);
                        zombifiedPiglin.setAngryAt(player.getUuid());
                        break;
                    }
                }
            }

            // día 10
            // Equip zombie with enchanted items
            if (ModTimeManager.hasPassedDays(10) && world.getRegistryKey() == ServerWorld.NETHER) {
                equipArmor(zombifiedPiglin, List.of(
                        diamondHelmet,
                        diamondChestplate,
                        diamondLeggings,
                        diamondBoots));
                zombifiedPiglin.equipStack(EquipmentSlot.MAINHAND, diamondSword);
                zombifiedPiglin.addCommandTag("custom_zombified_piglin");
            }
        });
    }

    public static void equipArmor(MobEntity zombifiedPiglin, List<ItemStack> armorPieces) {

        zombifiedPiglin.equipStack(EquipmentSlot.HEAD, armorPieces.get(0));
        zombifiedPiglin.equipStack(EquipmentSlot.CHEST, armorPieces.get(1));
        zombifiedPiglin.equipStack(EquipmentSlot.LEGS, armorPieces.get(2));
        zombifiedPiglin.equipStack(EquipmentSlot.FEET, armorPieces.get(3));
        preventItemDrops(zombifiedPiglin);
    }

    private static void preventItemDrops(MobEntity zombifiedPiglin) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            zombifiedPiglin.setEquipmentDropChance(slot, 0.0f);
        }
    }
}
