package net.dieguex.monoland.commands;

import java.util.Set;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//por mientras, hasta que salga la version de Cardboard para la 1.21.5 que es un mod para utilizar plugins de Bukkit en Minecraft
public class EndAccessManager {

    private static boolean endEnabled = false;

    public static void register() {
        // 🔥 Comandos de activar y desactivar
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("enableend")
                    .requires(source -> source.hasPermissionLevel(2))
                    .executes(context -> {
                        endEnabled = true;
                        context.getSource().sendFeedback(() -> Text.translatable("monoland.enabled.end")
                                .styled(style -> style.withColor(Formatting.GREEN)), false);
                        return 1;
                    }));

            dispatcher.register(CommandManager.literal("disableend")
                    .requires(source -> source.hasPermissionLevel(2))
                    .executes(context -> {
                        endEnabled = false;
                        context.getSource().sendFeedback(() -> Text.translatable("monoland.disabled.end")
                                .styled(style -> style.withColor(Formatting.RED)), false);
                        return 1;
                    }));
        });

        // 🚪 Bloquear cambio de dimensión al End
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ServerPlayerEntity player) {
                if (!endEnabled && player.getWorld().getRegistryKey() == World.END) {
                    ServerWorld overworld = player.getServer().getOverworld();

                    // Conseguir información de respawn
                    BlockPos spawnPos = world.getSpawnPos();
                    player.teleport(
                            overworld,
                            spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(),
                            Set.of(PositionFlag.X, PositionFlag.Y, PositionFlag.Z),
                            player.getYaw(),
                            player.getPitch(),
                            true);

                    player.sendMessage(
                            Text.translatable("monoland.disabled.end.info")
                                    .styled(style -> style.withColor(Formatting.RED)),
                            true);
                }
            }
        });
    }
}
