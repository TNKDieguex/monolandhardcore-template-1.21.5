package net.dieguex.monoland.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class HeartRegenerationItem extends Item {

    public HeartRegenerationItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            if (attr != null && attr.getBaseValue() < 20.0f) {
                attr.setBaseValue(Math.min(20.0f, attr.getBaseValue() + 2.0f));
                player.sendMessage(Text.literal("🍊 ¡Has recuperado un corazón máximo!"), true);
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS,
                        1.0f, 1.2f);
            } else {
                player.sendMessage(Text.literal("⛔ Ya tienes la vida máxima completa."), true);
            }
        }
        return super.finishUsing(stack, world, user);
    }

}
