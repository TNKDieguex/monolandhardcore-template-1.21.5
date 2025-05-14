package net.dieguex.monoland.mobGeneration.mobsAbilities;

import java.util.List;
import java.util.Optional;

import net.dieguex.monoland.item.ModItems;
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.dieguex.monoland.util.EnchantAndEffectsUtils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;

public class LootTableModifiers {
    private static final Optional<RegistryKey<LootTable>> ELDER_GUARDIAN_KEY = EntityType.ELDER_GUARDIAN
            .getLootTableKey();
    private static final Optional<RegistryKey<LootTable>> GUARDIAN_KEY = EntityType.GUARDIAN
            .getLootTableKey();
    private static final Optional<RegistryKey<LootTable>> PIGLIN_BRUTE_KEY = EntityType.PIGLIN_BRUTE
            .getLootTableKey();
    private static final Optional<RegistryKey<LootTable>> WARDEN_KEY = EntityType.WARDEN
            .getLootTableKey();

    public LootTableModifiers() {
        throw new AssertionError();
    }

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity.getWorld() instanceof ServerWorld serverWorld))
                return;
            // día 0
            // breeze drop unbreakable shield with 20% of chance
            if (entity.getType() == EntityType.BREEZE && ModTimeManager.hasPassedDays(0)) {
                ItemStack unbreakableShield = new ItemStack(Items.SHIELD);
                unbreakableShield.set(DataComponentTypes.UNBREAKABLE, net.minecraft.util.Unit.INSTANCE);
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < 20) {
                    ItemEntity unbreakableShieldDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            unbreakableShield);
                    serverWorld.spawnEntity(unbreakableShieldDrop);
                }
            }
            // día 5
            // ravager drop totem of undying with 1% of chance
            if (entity.getType() == EntityType.RAVAGER && ModTimeManager.hasPassedDays(5)) {
                int totemDropChance = ModTimeManager.hasPassedDays(8) ? 20 : 1;
                if (entity.getCommandTags().contains("custom_ravager_ultra"))
                    totemDropChance = 100;
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < totemDropChance) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(Items.TOTEM_OF_UNDYING));
                    serverWorld.spawnEntity(totemDrop);
                }
            }

            if (entity.getType() == EntityType.CREAKING && ModTimeManager.hasPassedDays(5)) {
                int totemDropChance = ModTimeManager.hasPassedDays(14) ? 20 : 100;
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < totemDropChance) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(Items.TOTEM_OF_UNDYING));
                    ItemEntity enchantedGoldenAppleDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 3));
                    serverWorld.spawnEntity(enchantedGoldenAppleDrop);
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // iron golem drop removed
            if (entity.getType() == EntityType.IRON_GOLEM && ModTimeManager.hasPassedDays(5)) {
                serverWorld.getEntitiesByType(EntityType.ITEM, item -> item.squaredDistanceTo(entity) < 4.0 &&
                        item.age < 5 // recién spawneado
                ).forEach(ItemEntity::discard);
            }
            // zombified piglin drop removed
            if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN && ModTimeManager.hasPassedDays(5)) {
                serverWorld.getEntitiesByType(EntityType.ITEM, item -> item.squaredDistanceTo(entity) < 4.0 &&
                        item.age < 5 // recién spawneado
                ).forEach(ItemEntity::discard);
                if (ModTimeManager.hasPassedDays(14) && entity.getCommandTags().contains("Manuelito_Esclavo")) {
                    ItemEntity ingotGoldDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(Items.GOLD_INGOT, 32));
                    serverWorld.spawnEntity(ingotGoldDrop);

                }
            }
            // evoker drop removed
            if (entity.getType() == EntityType.EVOKER && ModTimeManager.hasPassedDays(5)) {
                serverWorld.getEntitiesByType(EntityType.ITEM, item -> item.squaredDistanceTo(entity) < 4.0 &&
                        item.age < 5 // recién spawneado
                ).forEach(ItemEntity::discard);
            }

            // día 8
            // slime drop hyper essence with 20% of chance
            // hyper essence drop chance is 8% if the player has passed 14 days
            if (entity.getType() == EntityType.SLIME && ModTimeManager.hasPassedDays(8)) {
                int hyperEssenceDropChance = ModTimeManager.hasPassedDays(14) ? 8 : 20;
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < hyperEssenceDropChance) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_ESSENCE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // magma cube drop hyper essence with 30% of chance
            // hyper essence drop chance is 14% if the player has passed 14 days
            if (entity.getType() == EntityType.MAGMA_CUBE && ModTimeManager.hasPassedDays(8)) {
                int hyperEssenceDropChance = ModTimeManager.hasPassedDays(14) ? 14 : 30;
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < hyperEssenceDropChance) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_ESSENCE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // ghaast drop hyper upgrade template with 20% of chance
            // hyper upgrade template drop chance is 10% if the player has passed 14 days
            if (entity.getType() == EntityType.GHAST && ModTimeManager.hasPassedDays(8)) {
                int hyperEssenceDropChance = ModTimeManager.hasPassedDays(14) ? 10 : 20;
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < hyperEssenceDropChance) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_UPGRADE_TEMPLATE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // cave spider drop hyper upgrade template with 35% of chance
            // hyper upgrade template drop chance is 10% if the player has passed 14 days
            if (entity.getType() == EntityType.CAVE_SPIDER && ModTimeManager.hasPassedDays(8)) {
                int hyperEssenceDropChance = ModTimeManager.hasPassedDays(14) ? 10 : 35;
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < hyperEssenceDropChance) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_UPGRADE_TEMPLATE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }

            // día 14
            if (entity.getType() == EntityType.SHULKER && ModTimeManager.hasPassedDays(14)) {
                serverWorld.getEntitiesByType(EntityType.ITEM, item -> item.squaredDistanceTo(entity) < 4.0 &&
                        item.age < 5 // recién spawneado
                ).forEach(ItemEntity::discard);
                int shulkerShell = ModTimeManager.hasPassedDays(20) ? 2 : 20;
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < shulkerShell) {
                    ItemEntity shulkerDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(Items.SHULKER_SHELL));
                    serverWorld.spawnEntity(shulkerDrop);
                }
            }

            // día 20
            // Giant drop bow with power 10
            if (entity.getType() == EntityType.GIANT && ModTimeManager.hasPassedDays(20)) {
                ItemStack bow = new ItemStack(Items.BOW);
                EnchantAndEffectsUtils.applyMultiple(serverWorld,
                        bow, new Object[][] {
                                { "power", 10 }
                        });
                ItemEntity giantDrop = new ItemEntity(
                        serverWorld,
                        entity.getX(), entity.getY(), entity.getZ(),
                        bow);
                serverWorld.spawnEntity(giantDrop);
            }
            // Wither skeleton drop totem of undying with 50% of chance
            // enchanted golden apple with 35% of chance
            // hyper golden heart with 10% of chance
            if (entity.getType() == EntityType.WITHER_SKELETON && ModTimeManager.hasPassedDays(20) &&
                    entity.getCommandTags().contains("custom_witherSkeleton_emperador")) {
                ItemEntity totemDrop = new ItemEntity(
                        serverWorld,
                        entity.getX(), entity.getY(), entity.getZ(),
                        new ItemStack(Items.TOTEM_OF_UNDYING, 3));
                ItemEntity enchantedGoldenAppleDrop = new ItemEntity(
                        serverWorld,
                        entity.getX(), entity.getY(), entity.getZ(),
                        new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 3));
                ItemEntity hyperGoldenHeart = new ItemEntity(
                        serverWorld,
                        entity.getX(), entity.getY(), entity.getZ(),
                        new ItemStack(ModItems.HYPER_GOLDEN_HEART, 2));
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < 50)
                    serverWorld.spawnEntity(totemDrop);
                if (random.nextInt(100) < 35)
                    serverWorld.spawnEntity(enchantedGoldenAppleDrop);
                if (random.nextInt(100) < 10)
                    serverWorld.spawnEntity(hyperGoldenHeart);
            }
        });
        // Add hyper heart to elder guardian loot table with 60% of chance
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            System.out.println(key);
            if (ELDER_GUARDIAN_KEY.get() == key) {
                LootPool hyper_heart = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.6f))
                        .with(ItemEntry.builder(ModItems.HYPER_HEART))
                        .apply(SetCountLootFunction
                                .builder(UniformLootNumberProvider.create(1.0f, 3.0f))
                                .build())
                        .build();
                tableBuilder.pools(List.of(hyper_heart));
            }
        });
        // Add hyper heart piece to guardian loot table with 80% of chance
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            System.out.println(key);
            if (GUARDIAN_KEY.get() == key) {
                LootPool hyper_heart_piece = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.8f))
                        .with(ItemEntry.builder(ModItems.HYPER_HEART_PIECE))
                        .apply(SetCountLootFunction
                                .builder(UniformLootNumberProvider.create(1.0f, 4.0f))
                                .build())
                        .build();
                tableBuilder.pools(List.of(hyper_heart_piece));
            }
        });
        // Add hyper soul to piglin brute loot table with 40% of chance
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            System.out.println(key);
            if (PIGLIN_BRUTE_KEY.get() == key) {
                LootPool hyper_soul = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.4f))
                        .with(ItemEntry.builder(ModItems.HYPER_SOUL))
                        .apply(SetCountLootFunction
                                .builder(UniformLootNumberProvider.create(1.0f, 2.0f))
                                .build())
                        .build();
                tableBuilder.pools(List.of(hyper_soul));
            }
        });
        // Add totem of undying to warden loot table with 20% of chance
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            System.out.println(key);
            if (WARDEN_KEY.get() == key) {
                LootPool totem_of_undying = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .with(ItemEntry.builder(Items.TOTEM_OF_UNDYING))
                        .apply(SetCountLootFunction
                                .builder(UniformLootNumberProvider.create(1.0f, 2.0f))
                                .build())
                        .build();
                tableBuilder.pools(List.of(totem_of_undying));
            }
        });

    }

}
