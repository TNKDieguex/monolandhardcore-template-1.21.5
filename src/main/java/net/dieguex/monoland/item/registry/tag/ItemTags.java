package net.dieguex.monoland.item.registry.tag;

import net.minecraft.util.Identifier;
import net.dieguex.monoland.MonolandHardcore;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ItemTags {
    public static final TagKey<Item> REPAIRS_HYPER_ESSENCE_ARMOR = of("repairs_hyper_essence_armor");
    public static final TagKey<Item> HYPER_ESSENCE_TOOL_MATERIALS = of("hyper_essence_tool_materials");
    public static final TagKey<Item> CHEST_ARMOR = of("chest_armor");
    public static final TagKey<Item> LEG_ARMOR = of("leg_armor");
    public static final TagKey<Item> HEAD_ARMOR = of("head_armor");
    public static final TagKey<Item> FOOT_ARMOR = of("foot_armor");
    public static final TagKey<Item> CHEST_ARMOR_ENCHANTABLE = of("enchantable/chest_armor");
    public static final TagKey<Item> LEG_ARMOR_ENCHANTABLE = of("enchantable/leg_armor");
    public static final TagKey<Item> HEAD_ARMOR_ENCHANTABLE = of("enchantable/head_armor");
    public static final TagKey<Item> FOOT_ARMOR_ENCHANTABLE = of("enchantable/foot_armor");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(MonolandHardcore.MOD_ID, id));
    }
}