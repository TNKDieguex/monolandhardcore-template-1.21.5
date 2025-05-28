package net.dieguex.monoland.util;

import net.dieguex.monoland.commands.EndAccessManager;
//imports from custom classes
import net.dieguex.monoland.commands.ServerInformation;
import net.dieguex.monoland.item.ModItems;
import net.dieguex.monoland.item.ModItemsGroups;
import net.dieguex.monoland.mobGeneration.ZombifiedPiglinMod;
import net.dieguex.monoland.mobGeneration.mobsAbilities.CreeperTeleport;
import net.dieguex.monoland.mobGeneration.mobsAbilities.GhastTeleport;
import net.dieguex.monoland.mobGeneration.mobsAbilities.LootTableModifiers;
import net.dieguex.monoland.mobGeneration.SpiderMod;
import net.dieguex.monoland.mobGeneration.VexesMod;
import net.dieguex.monoland.mobGeneration.WitherSkeleton;
import net.dieguex.monoland.mobGeneration.ZombieMod;
import net.dieguex.monoland.mobGeneration.BeesMod;
import net.dieguex.monoland.mobGeneration.BlazeMod;
import net.dieguex.monoland.mobGeneration.CreakingMod;
import net.dieguex.monoland.mobGeneration.CreeperMod;
import net.dieguex.monoland.mobGeneration.EndCrystalEntityMod;
import net.dieguex.monoland.mobGeneration.EnderDragonMod;
import net.dieguex.monoland.mobGeneration.EndermanMod;
import net.dieguex.monoland.mobGeneration.EndermiteMod;
import net.dieguex.monoland.mobGeneration.GhastMod;
import net.dieguex.monoland.mobGeneration.GiantMod;
import net.dieguex.monoland.mobGeneration.GuardianMod;
import net.dieguex.monoland.mobGeneration.IllagersMod;
import net.dieguex.monoland.mobGeneration.LlamaMod;
import net.dieguex.monoland.mobGeneration.MagmaCubeMod;
import net.dieguex.monoland.mobGeneration.ModEntityGeneration;
import net.dieguex.monoland.mobGeneration.PhantomsMod;
import net.dieguex.monoland.mobGeneration.RavagersMod;
import net.dieguex.monoland.mobGeneration.ShulkerMod;
import net.dieguex.monoland.mobGeneration.SilverFish;
import net.dieguex.monoland.mobGeneration.SkeletonMod;
import net.dieguex.monoland.mobGeneration.SlimeMod;
//imports from the Fabric API
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.command.v2.*;

public class ModRegistries {

    public static void registerModStuffs() {
        ModTimeManager.init();
        DeathTracker.init();
        registerItemsMod();
        registerCommandsANDUtility();
        registerZombieMod();
        registerSpiderMod();
        registerCreeperMod();
        registerSkeletonMod();
        registerGhastMod();
        registerPhantomMod();
        registerSlimeModAndMagmaCubeMod();
        registerBlazeMod();
        registerGuardianMod();
        registerRavagersMod();
        registerIllagersMod();
        registerEndermanMod();
        registerSilverFishMod();
        registerShulkerMod();
        // Passives
        registerPassivesMod();
    }

    private static void registerZombieMod() {
        ZombieMod.register();
        ZombifiedPiglinMod.register();
        GiantMod.register();
    }

    private static void registerSpiderMod() {
        SpiderMod.register();
    }

    private static void registerCreeperMod() {
        CreeperTeleport.register();
        CreeperMod.register();
        ModEntityGeneration.registerNaturalSpawns();
    }

    private static void registerSkeletonMod() {
        SkeletonMod.register();
        WitherSkeleton.register();
    }

    private static void registerGhastMod() {
        GhastTeleport.register();
        GhastMod.register();
    }

    private static void registerPhantomMod() {
        PhantomsMod.register();
    }

    private static void registerSlimeModAndMagmaCubeMod() {
        SlimeMod.register();
        MagmaCubeMod.register();
    }

    private static void registerBlazeMod() {
        BlazeMod.register();
    }

    private static void registerGuardianMod() {
        GuardianMod.register();
    }

    private static void registerItemsMod() {
        ModItemsGroups.registerModItemsGroups();
        ModItems.registerModItems();
    }

    private static void registerRavagersMod() {
        RavagersMod.register();
    }

    private static void registerIllagersMod() {
        IllagersMod.register();
        VexesMod.register();
    }

    private static void registerEndermanMod() {
        EndermanMod.register();
        EnderDragonMod.register();
        EndCrystalEntityMod.register();
        EndermiteMod.register();
    }

    private static void registerSilverFishMod() {
        SilverFish.register();
    }

    private static void registerShulkerMod() {
        ShulkerMod.register();
    }

    // Passives
    private static void registerPassivesMod() {
        CreakingMod.register();
        BeesMod.register();
        LlamaMod.register();
    }

    private static void registerCommandsANDUtility() {
        PlayerHealthManager.register();
        DeathStormManager.register();
        RainExposureEffect.register();
        EndAccessManager.register();
        EffectCleaner.register();
        LootTableModifiers.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ServerInformation.register(dispatcher, registryAccess, environment);
        });
    }

}
