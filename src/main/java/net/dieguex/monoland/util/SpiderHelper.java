package net.dieguex.monoland.util;

import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.SpawnReason;

public class SpiderHelper {
    public static void addSkeletonRider(ServerWorld world, SpiderEntity spider) {
        SkeletonEntity skeleton = EntityType.SKELETON.create(
                world,
                null,
                spider.getBlockPos(),
                SpawnReason.EVENT,
                true,
                false);

        if (skeleton != null) {
            world.spawnEntity(skeleton);
            skeleton.startRiding(spider, true);
        }
    }
}
