package net.dieguex.monoland.mobGeneration.mobsAbilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.server.world.ServerWorld;

public class SpiderHelper {
    public static void addSkeletonRider(ServerWorld world, SpiderEntity spider) {
        if (!spider.getPassengerList().isEmpty()) {
            return;
        }
        MobEntity skeleton = SkeletonFactory.spawnCustomSkeleton(world, spider.getBlockPos(), EntityType.SKELETON);
        if (skeleton != null) {
            skeleton.refreshPositionAndAngles(
                    spider.getX(),
                    spider.getY() + 1,
                    spider.getZ(),
                    spider.getYaw(),
                    spider.getPitch());

            world.spawnEntity(skeleton);
            skeleton.startRiding(spider, true);
        }
    }
}
