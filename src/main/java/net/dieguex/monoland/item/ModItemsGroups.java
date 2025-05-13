package net.dieguex.monoland.item;

import net.dieguex.monoland.MonolandHardcore;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemsGroups {
    public static final ItemGroup MONOLAND_HARDCORE_ITEMS = Registry.register(
            Registries.ITEM_GROUP, Identifier.of(MonolandHardcore.MOD_ID, "monoland_hardcore_items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.HYPER_GOLDEN_HEART))
                    .displayName(Text.translatable("itemGroup.monoland_hardcore_items"))
                    .entries((context, entries) -> {
                        entries.add(ModItems.HYPER_GOLDEN_HEART);
                        entries.add(ModItems.HYPER_SOUL);
                        entries.add(ModItems.HYPER_HEART_PIECE);
                        entries.add(ModItems.HYPER_HEART);
                        entries.add(ModItems.HYPER_ESSENCE);
                        entries.add(ModItems.HYPER_UPGRADE_TEMPLATE);
                        entries.add(ModItems.HYPER_ESSENCE_HELMET);
                        entries.add(ModItems.HYPER_ESSENCE_CHESTPLATE);
                        entries.add(ModItems.HYPER_ESSENCE_LEGGINGS);
                        entries.add(ModItems.HYPER_ESSENCE_BOOTS);
                        entries.add(ModItems.HYPER_REPAIR_ITEM);
                    })
                    .build());

    public static void registerModItemsGroups() {
        MonolandHardcore.LOGGER.info("📦 Grupos de ítems de Monoland registrados correctamente.");
    }
}
