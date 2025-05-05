package net.dieguex.monoland.item.equipement;

import java.util.Map;
import com.google.common.collect.Maps;

import net.dieguex.monoland.item.registry.tag.ItemTags;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.item.equipment.ArmorMaterial;

public class HyperArmorMaterials {
        // Equivalente a la durabilidad base de cada pieza multiplicada
        public static final int BASE_DURABILITY = 45;

        public static final ArmorMaterial HYPER_ESSENCE = new ArmorMaterial(
                        BASE_DURABILITY,
                        createDefenseMap(5, 8, 10, 5, 12),
                        15,
                        SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
                        4.0F,
                        0.2F,
                        ItemTags.REPAIRS_HYPER_ESSENCE_ARMOR,
                        EquipmentAssetKeys.HYPER_ESSENCES);

        private static Map<EquipmentType, Integer> createDefenseMap(int bootsDefense, int leggingsDefense,
                        int chestplateDefense, int helmetDefense, int bodyDefense) {
                return Maps.newEnumMap(
                                Map.of(EquipmentType.BOOTS, bootsDefense,
                                                EquipmentType.LEGGINGS, leggingsDefense,
                                                EquipmentType.CHESTPLATE, chestplateDefense,
                                                EquipmentType.HELMET, helmetDefense,
                                                EquipmentType.BODY, bodyDefense));
        }

}
