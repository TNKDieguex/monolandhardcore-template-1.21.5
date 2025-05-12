package net.dieguex.monoland.util;

import net.dieguex.monoland.mobGeneration.SpiderMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class EnchantAndEffectsUtils {

    /**
     * Aplica un encantamiento seguro a un ItemStack.
     *
     * @param world El mundo (para obtener el registry manager)
     * @param stack El objeto que será encantado
     * @param key   El RegistryKey del encantamiento (ej: Enchantments.PROTECTION)
     * @param level El nivel del encantamiento
     */
    public static void applyEnchantment(World world, ItemStack stack, RegistryKey<Enchantment> key, int level) {
        RegistryWrapper.WrapperLookup registryLookup = world.getRegistryManager();
        RegistryWrapper<Enchantment> enchantmentRegistry = registryLookup.getOrThrow(RegistryKeys.ENCHANTMENT);

        Optional<RegistryEntry.Reference<Enchantment>> enchantmentEntryOpt = enchantmentRegistry.getOptional(key);
        enchantmentEntryOpt.ifPresent(enchantmentEntry -> stack.addEnchantment(enchantmentEntry, level));
    }

    /**
     * Crea un RegistryKey<Enchantment> a partir de un nombre como "sharpness"
     */
    public static RegistryKey<Enchantment> key(String name) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("minecraft", name));
    }

    /**
     * Aplica múltiples encantamientos rápidamente
     */
    public static void applyMultiple(World world, ItemStack stack, Object[][] enchants) {
        for (Object[] entry : enchants) {
            String enchantId = (String) entry[0];
            int level = (int) entry[1];
            applyEnchantment(world, stack, key(enchantId), level);
        }
    }

    public static void applyEffectsToMob(MobEntity mob, List<RegistryEntry<StatusEffect>> effects) {
        for (RegistryEntry<StatusEffect> effect : effects) {
            mob.addStatusEffect(
                    new StatusEffectInstance(effect, -1, SpiderMod.getLevel(effect), false, false, false));
        }
    }
}