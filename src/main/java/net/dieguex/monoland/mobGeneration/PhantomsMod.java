package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

public class PhantomsMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld))
                return;

            if (entity instanceof PhantomEntity phantom) {
                if (ModTimeManager.hasPassedDays(5) && !phantom.getCommandTags().contains("custom_phantom")) {
                    NbtCompound tag = new NbtCompound();
                    phantom.writeNbt(tag);
                    tag.putInt("size", 8);
                    phantom.readNbt(tag);
                    phantom.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(40.0);
                    phantom.setHealth(40.0f);
                    phantom.addCommandTag("custom_phantom");
                }
            }
        });

    }
}
