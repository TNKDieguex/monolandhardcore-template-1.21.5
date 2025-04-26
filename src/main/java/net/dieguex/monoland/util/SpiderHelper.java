package net.dieguex.monoland.util;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.server.world.ServerWorld;

public class SpiderHelper {
    public static void addSkeletonRider(ServerWorld world, SpiderEntity spider) {
        if (!spider.getPassengerList().isEmpty()) {
            return;
        }
        MobEntity skeleton = SkeletonFactory.spawnCustomSkeleton(world, spider.getBlockPos());
        if (skeleton != null) {
            // Colocar ligeramente por encima
            skeleton.refreshPositionAndAngles(
                    spider.getX(),
                    spider.getY() + 1,
                    spider.getZ(),
                    spider.getYaw(),
                    spider.getPitch());

            // Spawnearlo y montarlo
            world.spawnEntity(skeleton);
            skeleton.startRiding(spider, true);
        }
    }
}
