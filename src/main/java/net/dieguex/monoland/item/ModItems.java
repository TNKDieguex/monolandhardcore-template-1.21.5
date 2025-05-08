package net.dieguex.monoland.item;

import net.dieguex.monoland.MonolandHardcore;
import net.dieguex.monoland.item.equipement.HyperArmorMaterials;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import java.util.List;

import java.util.function.Function;

public class ModItems {
        // Items for new equipment
        public static final Item HYPER_ESSENCE = registerItem(
                        "hyper_essence",
                        settings -> new Item(settings.rarity(Rarity.EPIC)),
                        new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                        Identifier.of(MonolandHardcore.MOD_ID, "hyper_essence"))).rarity(Rarity.EPIC));

        public static final Item HYPER_UPGRADE_TEMPLATE = registerSmithingTemplateItem("hyper_upgrade_template",
                        settings -> new SmithingTemplateItem(
                                        Text.translatable("item.monolandhardcore.hyper_upgrade_template.applies_to"),
                                        Text.translatable("item.monolandhardcore.hyper_upgrade_template.description"),
                                        Text.translatable(
                                                        "item.monolandhardcore.hyper_upgrade_template.base_slot_description"),
                                        Text.translatable(
                                                        "item.monolandhardcore.hyper_upgrade_template.additions_slot_description"),
                                        List.of(
                                                        Identifier.of("container/slot/helmet"),
                                                        Identifier.of("container/slot/chestplate"),
                                                        Identifier.of("container/slot/leggings"),
                                                        Identifier.of("container/slot/boots")),
                                        List.of(
                                                        Identifier.ofVanilla("container/slot/ingot")),
                                        settings.rarity(Rarity.EPIC)));

        // Hyper Essence Armor
        public static final Item HYPER_ESSENCE_HELMET = registerItem("hyper_essence_helmet",
                        settings -> new Item(settings.armor(HyperArmorMaterials.HYPER_ESSENCE, EquipmentType.HELMET)
                                        .fireproof().rarity(Rarity.EPIC).registryKey(
                                                        RegistryKey.of(RegistryKeys.ITEM,
                                                                        Identifier.of(MonolandHardcore.MOD_ID,
                                                                                        "hyper_essence_helmet")))),
                        new Item.Settings());

        public static final Item HYPER_ESSENCE_CHESTPLATE = registerItem("hyper_essence_chestplate",
                        settings -> new Item(settings.armor(
                                        HyperArmorMaterials.HYPER_ESSENCE, EquipmentType.CHESTPLATE).fireproof()
                                        .rarity(Rarity.EPIC)
                                        .registryKey(
                                                        RegistryKey.of(RegistryKeys.ITEM,
                                                                        Identifier.of(MonolandHardcore.MOD_ID,
                                                                                        "hyper_essence_chestplate")))),
                        new Item.Settings());

        public static final Item HYPER_ESSENCE_LEGGINGS = registerItem("hyper_essence_leggings",
                        settings -> new Item(settings.armor(
                                        HyperArmorMaterials.HYPER_ESSENCE, EquipmentType.LEGGINGS).fireproof()
                                        .rarity(Rarity.EPIC)
                                        .registryKey(
                                                        RegistryKey.of(RegistryKeys.ITEM,
                                                                        Identifier.of(MonolandHardcore.MOD_ID,
                                                                                        "hyper_essence_leggings")))),
                        new Item.Settings());

        public static final Item HYPER_ESSENCE_BOOTS = registerItem("hyper_essence_boots",
                        settings -> new Item(settings.armor(
                                        HyperArmorMaterials.HYPER_ESSENCE, EquipmentType.BOOTS).fireproof()
                                        .rarity(Rarity.EPIC)
                                        .registryKey(
                                                        RegistryKey.of(RegistryKeys.ITEM,
                                                                        Identifier.of(MonolandHardcore.MOD_ID,
                                                                                        "hyper_essence_boots")))),
                        new Item.Settings());

        public static final Item black_Diamond = registerItem(
                        "black_diamond",
                        settings -> new Item(settings),
                        new Item.Settings()
                                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,
                                                        Identifier.of(MonolandHardcore.MOD_ID, "black_diamond"))));
        // new food
        public static final Item HYPER_GOLDEN_HEART = registerItem(
                        "hyper_golden_heart",
                        HeartRegenerationItem::new,
                        new Item.Settings()
                                        .maxCount(32)
                                        .food(new FoodComponent.Builder()
                                                        .saturationModifier(0f)
                                                        .alwaysEdible()
                                                        .build())
                                        .rarity(Rarity.UNCOMMON));

        private static Item registerItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM,
                                Identifier.of(MonolandHardcore.MOD_ID, path));
                return Items.register(key, factory, settings);
        }

        private static Item registerSmithingTemplateItem(String name,
                        Function<Item.Settings, SmithingTemplateItem> factory) {
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MonolandHardcore.MOD_ID, name));
                Item.Settings settings = new Item.Settings().registryKey(key);
                return Items.register(key, settings1 -> (Item) factory.apply(settings1), settings);
        }

        public static void registerModItems() {
                MonolandHardcore.LOGGER.info("📦 Ítems de Monoland registrados correctamente.");
        }
}
