package net.dieguex.monoland.mobGeneration;

import net.dieguex.monoland.mobGeneration.mobsAbilities.SkeletonFactory;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.server.world.ServerWorld;

public class SkeletonMod {

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;

            if (!(entity instanceof SkeletonEntity skeleton) || entity instanceof WitherSkeletonEntity)
                return;
            if (entity.getCommandTags().contains("custom_skeleton"))
                return;

            if (ModTimeManager.hasPassedDays(0)) {
                MobEntity upgraded = SkeletonFactory.spawnCustomSkeleton(serverWorld, skeleton.getBlockPos());
                if (upgraded != null) {
                    upgraded.setYaw(skeleton.getYaw());
                    upgraded.setPitch(skeleton.getPitch());
                    skeleton.discard();
                }
                return;
            }
        });
    }
}
