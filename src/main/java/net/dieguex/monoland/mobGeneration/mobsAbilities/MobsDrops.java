package net.dieguex.monoland.mobGeneration.mobsAbilities;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;

public class MobsDrops {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity.getWorld() instanceof ServerWorld serverWorld))
                return;

            if (entity.getType() == EntityType.RAVAGER && ModTimeManager.hasPassedDays(6)) {
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) == 0) { // 1 en 100 probabilidad
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(Items.TOTEM_OF_UNDYING));
                    serverWorld.spawnEntity(totemDrop);

                    System.out.println("🔥 Un Ravager soltó un Tótem por probabilidad especial!");
                }
            }
        });
    }

}
