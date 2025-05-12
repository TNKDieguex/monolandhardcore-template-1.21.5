package net.dieguex.monoland.mobGeneration;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class ZombieMod {
    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof ZombieEntity zombie) || entity.getType() == EntityType.ZOMBIFIED_PIGLIN
                    || entity.getType() == EntityType.DROWNED || entity.getType() == EntityType.GIANT) {
                return;
            }
            if (entity.getCommandTags().contains("custom_zombie"))
                return;
            Random random = world.getRandom();
            if (ModTimeManager.hasPassedDays(20)) {
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
