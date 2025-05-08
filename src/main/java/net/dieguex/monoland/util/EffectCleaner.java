package net.dieguex.monoland.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.server.world.ServerWorld;

public class EffectCleaner {

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof AreaEffectCloudEntity cloud) || !(world instanceof ServerWorld serverWorld))
                return;

            if (cloud.getOwner() == null)
                cloud.discard(); // eliminar la nube si no tiene dueño
        });
    }

}
