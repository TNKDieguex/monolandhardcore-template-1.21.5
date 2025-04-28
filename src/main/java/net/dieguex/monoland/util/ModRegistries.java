package net.dieguex.monoland.util;

//imports from custom classes
import net.dieguex.monoland.commands.ServerInformation;
import net.dieguex.monoland.item.ModItems;
import net.dieguex.monoland.item.ModItemsGroups;
import net.dieguex.monoland.mobGeneration.ZombieMod;
import net.dieguex.monoland.mobGeneration.mobsAbilities.CreeperTeleport;
import net.dieguex.monoland.mobGeneration.mobsAbilities.GhastTeleport;
import net.dieguex.monoland.mobGeneration.mobsAbilities.MobsDrops;
import net.dieguex.monoland.mobGeneration.SpiderMod;
import net.dieguex.monoland.mobGeneration.CreeperMod;
import net.dieguex.monoland.mobGeneration.GhastMod;
import net.dieguex.monoland.mobGeneration.ModEntityGeneration;
import net.dieguex.monoland.mobGeneration.PhantomsMod;
import net.dieguex.monoland.mobGeneration.SkeletonMod;
//imports from the Fabric API
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.command.v2.*;

public class ModRegistries {

    public static void registerModStuffs() {
        ModTimeManager.init();
        ItemsMod();
        registerCommandsANDUtility();
        registerZombieMod();
        registerSpiderMod();
        registerCreeperMod();
        registerSkeletonMod();
        registerGhastMod();
        registerPhantomMod();
    }

    private static void registerZombieMod() {
        ZombieMod.register();
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
    }

    private static void registerGhastMod() {
        GhastTeleport.register();
        GhastMod.register();
    }

    private static void registerPhantomMod() {
        PhantomsMod.register();
    }

    private static void ItemsMod() {
        ModItemsGroups.registerModItemsGroups();
        ModItems.registerModItems();
    }

    private static void registerCommandsANDUtility() {
        PlayerHealthManager.register();
        DeathStormManager.init();
        MobsDrops.register();
        EffectCleaner.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ServerInformation.register(dispatcher, registryAccess, environment);
        });
    }

}
