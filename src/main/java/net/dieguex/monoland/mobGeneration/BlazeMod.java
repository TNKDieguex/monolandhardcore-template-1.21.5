package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.server.world.ServerWorld;

public class BlazeMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof BlazeEntity blaze)) {
                return;
            }
            if (blaze.getCommandTags().contains("custom_blaze")) {
                return;
            }
            // día 20
            if (ModTimeManager.hasPassedDays(20)) {
                blaze.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(200);
                blaze.setHealth(200);
                blaze.addCommandTag("custom_blaze");
            }
        });
    }

}
