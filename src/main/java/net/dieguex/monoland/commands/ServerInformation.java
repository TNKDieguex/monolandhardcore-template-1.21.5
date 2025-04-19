package net.dieguex.monoland.commands;

import java.util.function.Supplier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
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
                                            int dias = IntegerArgumentType.getInteger(context, "dias");
                                            ModTimeManager.simulateDaysPassed(dias);
                                            context.getSource().sendMessage(
                                                    Text.literal("⏳ Día simulado cambiado a hace " + dias + " días."));
                                            return 1;
                                        }))));
    }

    private static Supplier<Text> infoEnFrancais = () -> Text.translatable("monoland.info");

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().sendFeedback(
                infoEnFrancais, true);
        return 1;
    }

}
