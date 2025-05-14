package net.dieguex.monoland.mobGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.server.world.ServerWorld;

public class EnderDragonMod {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld)) {
                return;
            }
            if (!(entity instanceof EnderDragonEntity enderDragon)) {
                return;
            }
            if (enderDragon.getCommandTags().contains("custom_ender_dragon")) {
                return;
            }
            final float VIDA_MAX = 1000;
            enderDragon.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(VIDA_MAX);
            enderDragon.setHealth(VIDA_MAX);
            EntityAttributeInstance damageAttr = enderDragon
                    .getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
            if (damageAttr != null) {
                double baseDamage = damageAttr.getBaseValue();
                damageAttr.setBaseValue(baseDamage * 3); // 💥 triplica el daño
            }
            enderDragon.addCommandTag("custom_ender_dragon");
        });
    }
}
