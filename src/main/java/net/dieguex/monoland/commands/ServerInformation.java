package net.dieguex.monoland.commands;

import java.util.function.Supplier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.dieguex.monoland.timeManager.*;
import static net.minecraft.server.command.CommandManager.argument;

public class ServerInformation {

        public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                        CommandRegistryAccess registryAccess,
                        CommandManager.RegistrationEnvironment environment) {
                dispatcher.register(
                                CommandManager.literal("info")
                                                .executes(ServerInformation::run));

                dispatcher.register(
                                CommandManager.literal("modtime")
                                                .then(CommandManager.literal("set")
                                                                .requires(source -> source.hasPermissionLevel(2))
                                                                .then(argument("dias", IntegerArgumentType.integer(0))
                                                                                .executes(context -> {
                                                                                        int dias = IntegerArgumentType
                                                                                                        .getInteger(context,
                                                                                                                        "dias");
                                                                                        ModTimeManager.simulateDaysPassed(
                                                                                                        dias);
                                                                                        context.getSource().sendMessage(
                                                                                                        Text.literal("⏳ Día simulado cambiado a hace "
                                                                                                                        + dias
                                                                                                                        + " días."));
                                                                                        return 1;
                                                                                }))));
                dispatcher.register(
                                CommandManager.literal("resetvida")
                                                .requires(source -> source.hasPermissionLevel(2)) // Solo admins
                                                .executes(context -> {
                                                        ServerPlayerEntity player = context.getSource().getPlayer();
                                                        EntityAttributeInstance attr = player.getAttributeInstance(
                                                                        EntityAttributes.MAX_HEALTH);
                                                        if (attr != null) {
                                                                attr.setBaseValue(20.0f); // vida máxima default
                                                                player.setHealth(20.0f); // también recupera vida actual
                                                                player.sendMessage(Text.literal(
                                                                                "✨ ¡Vida máxima restaurada!"), false);
                                                        }
                                                        return 1;
                                                }));
        }

        private static Supplier<Text> serverInfo = () -> Text.translatable("monoland.info");

        private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
                context.getSource().sendFeedback(
                                serverInfo, true);
                return 1;
        }

}
