package net.dieguex.monoland.util;

//imports from custom classes
import net.dieguex.monoland.commands.ServerInformation;
import net.dieguex.monoland.mobGeneration.ZombieMod;
import net.dieguex.monoland.mobGeneration.SpiderMod;
import net.dieguex.monoland.mobGeneration.CreeperMod;

//imports from the Fabric API
import net.dieguex.monoland.timeManager.ModTimeManager;
import net.fabricmc.fabric.api.command.v2.*;

public class ModRegistries {

    public static void registerModStuffs() {
        ModTimeManager.init();
        registerCommands();
        registerZombieMod();
        registerSpiderMod();
        registerCreeperMod();
    }

    private static void registerZombieMod() {
        ZombieMod.register();
    }

    private static void registerSpiderMod() {
        SpiderMod.register();
    }

    private static void registerCreeperMod() {
        CreeperMod.register();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ServerInformation.register(dispatcher, registryAccess, environment);
        });
    }

}
