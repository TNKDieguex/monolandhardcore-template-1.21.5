package net.dieguex.monoland.mobGeneration;

import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.server.world.ServerWorld;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

public class SlimeMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;

            if (entity instanceof SlimeEntity slime) {
                if (ModTimeManager.hasPassedDays(9) && slime.getSize() < 16) {
                    slime.setSize(16, true);
                }
            }
        });
    }
}
