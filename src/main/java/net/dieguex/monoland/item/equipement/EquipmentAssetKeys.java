package net.dieguex.monoland.item.equipement;

import net.dieguex.monoland.MonolandHardcore;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface EquipmentAssetKeys {
    RegistryKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = RegistryKey
            .ofRegistry(Identifier.ofVanilla("equipment_asset"));

    RegistryKey<EquipmentAsset> HYPER_ESSENCES = register("hyper_essences");

    private static RegistryKey<EquipmentAsset> register(String name) {
        return RegistryKey.of(REGISTRY_KEY, Identifier.of(MonolandHardcore.MOD_ID, name));
    }
}