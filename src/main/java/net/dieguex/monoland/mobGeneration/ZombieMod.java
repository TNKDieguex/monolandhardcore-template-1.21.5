package net.dieguex.monoland.mobGeneration;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.List;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class ZombieMod {
    private static ItemStack goldenHelmet = new ItemStack(Items.GOLDEN_HELMET);
    private static ItemStack goldenChestplate = new ItemStack(Items.GOLDEN_CHESTPLATE);
    private static ItemStack goldenLeggings = new ItemStack(Items.GOLDEN_LEGGINGS);
    private static ItemStack goldenBoots = new ItemStack(Items.GOLDEN_BOOTS);
    private static ItemStack goldenSword = new ItemStack(Items.GOLDEN_SWORD);

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof ZombieEntity zombie) || entity.getType() == EntityType.ZOMBIFIED_PIGLIN
                    || entity.getType() == EntityType.GIANT) {
                return;
            }
            if (entity.getCommandTags().contains("custom_zombie"))
                return;
            if (ModTimeManager.hasPassedDays(10)) {
                ZombifiedPiglinMod.equipArmor(zombie, List.of(
                        goldenHelmet,
                        goldenChestplate,
                        goldenLeggings,
                        goldenBoots));
                zombie.equipStack(EquipmentSlot.MAINHAND, goldenSword);
                zombie.addCommandTag("custom_zombie");
            }
            if (ModTimeManager.hasPassedDays(5) && entity.getType() == EntityType.DROWNED) {
                EntityAttributeInstance damageAttr = zombie.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
                if (damageAttr != null) {
                    double baseDamage = damageAttr.getBaseValue();
                    damageAttr.setBaseValue(baseDamage * 3); // 💥 triplica el daño
                    zombie.addCommandTag("strong_drowned");
                }
            }
            Random random = world.getRandom();
            if (ModTimeManager.hasPassedDays(20) && !(entity.getType() == EntityType.DROWNED)) {
                if (random.nextInt(100) < 10) {
                    GiantEntity giant = EntityType.GIANT.create(
                            world,
                            null,
                            zombie.getBlockPos(),
                            SpawnReason.EVENT,
                            true,
                            false);
                    if (giant == null)
                        return;
                    giant.setYaw(zombie.getYaw());
                    giant.setPitch(zombie.getPitch());

                    zombie.discard();
                    world.spawnEntity(giant);
                } else {
                    zombie.addCommandTag("custom_zombie");
                }
            }
        });
    }

}
