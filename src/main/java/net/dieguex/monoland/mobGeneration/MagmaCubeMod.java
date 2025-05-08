package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.server.world.ServerWorld;

public class MagmaCubeMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;

            if (entity instanceof MagmaCubeEntity magmaCube) {
                if (ModTimeManager.hasPassedDays(9)) {
                    if (magmaCube.getCommandTags() == null
                            || !magmaCube.getCommandTags().contains("MagmaCubeOriginal")) {
                        magmaCube.setSize(17, false);
                        magmaCube.addCommandTag("MagmaCubeOriginal");
                    }
                }
            }
        });
    }
}
