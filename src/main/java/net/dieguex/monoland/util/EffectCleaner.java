package net.dieguex.monoland.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.AreaEffectCloudEntity;

public class EffectCleaner {

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof AreaEffectCloudEntity cloud))
                return;

            if (cloud.getOwner() == null)
                cloud.discard(); // eliminar la nube si no tiene dueño
        });
    }

}
