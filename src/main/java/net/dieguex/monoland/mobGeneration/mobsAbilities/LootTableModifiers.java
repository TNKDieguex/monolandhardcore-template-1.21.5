package net.dieguex.monoland.mobGeneration.mobsAbilities;

import java.util.List;
import java.util.Optional;

// import java.util.Optional;
// import java.util.List;
// import net.minecraft.entity.LivingEntity;
// import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
// import net.minecraft.loot.LootPool;
// import net.minecraft.loot.condition.RandomChanceLootCondition;
// import net.minecraft.loot.entry.ItemEntry;
// import net.minecraft.loot.function.SetCountLootFunction;
// import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
// import net.minecraft.loot.provider.number.UniformLootNumberProvider;
// import net.minecraft.loot.LootTable;
// import net.minecraft.registry.RegistryKey;

import net.dieguex.monoland.item.ModItems;
import net.dieguex.monoland.timeManager.ModTimeManager;
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
            // ravager drop totem of undying with 1% of chance
            if (entity.getType() == EntityType.RAVAGER && ModTimeManager.hasPassedDays(6)) {
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) == 0) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(Items.TOTEM_OF_UNDYING));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // slime drop hyper essence with 3% of chance
            if (entity.getType() == EntityType.SLIME && ModTimeManager.hasPassedDays(9)) {
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < 3) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_ESSENCE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // magma cube drop hyper essence with 8% of chance
            if (entity.getType() == EntityType.MAGMA_CUBE && ModTimeManager.hasPassedDays(9)) {
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < 8) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_ESSENCE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // ghaast drop hyper essence with 20% of chance
            if ((entity.getType() == EntityType.GHAST || entity.getType() == EntityType.CAVE_SPIDER)
                    && ModTimeManager.hasPassedDays(9)) {
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < 20) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_UPGRADE_TEMPLATE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
            // cave spider drop hyper upgrade template with 35% of chance
            if (entity.getType() == EntityType.CAVE_SPIDER && ModTimeManager.hasPassedDays(9)) {
                Random random = serverWorld.getRandom();
                if (random.nextInt(100) < 35) {
                    ItemEntity totemDrop = new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            new ItemStack(ModItems.HYPER_UPGRADE_TEMPLATE));
                    serverWorld.spawnEntity(totemDrop);
                }
            }
        });
        // Add hyper heart to elder guardian loot table with 100% of chance
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
                System.out.println("Elder Guardian hyper heart drop added!");

                tableBuilder.pools(List.of(hyper_heart));
            }
        });
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
                System.out.println("Elder Guardian hyper heart piece drop added!");

                tableBuilder.pools(List.of(hyper_heart_piece));
            }
        });
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
                System.out.println("Elder Guardian hyper soul drop added!");

                tableBuilder.pools(List.of(hyper_soul));
            }
        });
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
                System.out.println("Elder Guardian hyper heart drop added!");

                tableBuilder.pools(List.of(totem_of_undying));
            }
        });

    }

}
