package net.dieguex.monoland.util;

import net.dieguex.monoland.commands.ServerInformation;
import net.dieguex.monoland.mobGeneration.ZombieMod;
import net.fabricmc.fabric.api.command.v2.*;
import net.minecraft.block.entity.VaultBlockEntity.Server;

public class ModRegistries {

    public static void registerModStuffs() {
        registerCommands();
        registerZombieMod();
    }

    private static void registerZombieMod() {
        ZombieMod.register();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ServerInformation.register(dispatcher, registryAccess, environment);
        });
    }

}
