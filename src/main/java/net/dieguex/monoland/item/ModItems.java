package net.dieguex.monoland.item;

import net.dieguex.monoland.MonolandHardcore;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class ModItems {
    public static final Item HYPER_GOLDEN_HEART = registerItem(
            "hyper_golden_heart",
            HeartRegenerationItem::new,
            new Item.Settings()
                    .maxCount(32)
                    .food(new FoodComponent.Builder()
                            .saturationModifier(0f)
                            .alwaysEdible()
                            .build()));

    private static Item registerItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM,
                Identifier.of("monolandhardcore", path));
        return Items.register(key, factory, settings);
    }

    public static void registerModItems() {
        MonolandHardcore.LOGGER.info("📦 Ítems de Monoland registrados correctamente.");
    }
}
