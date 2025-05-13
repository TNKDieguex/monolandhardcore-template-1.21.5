package net.dieguex.monoland.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.dieguex.monoland.timeManager.*;
import static net.minecraft.server.command.CommandManager.argument;

public class ServerInformation {
        public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                        CommandRegistryAccess registryAccess,
                        CommandManager.RegistrationEnvironment environment) {
                dispatcher.register(
                                CommandManager.literal("infoserver")
                                                .executes(context -> {
                                                        ServerInfoMessages.send(context.getSource());
                                                        return 1;
                                                }));

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
                                                                                                        Text.translatable(
                                                                                                                        "monoland.simulated.day.changed",
                                                                                                                        dias));
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
                dispatcher.register(CommandManager.literal("dayinfo")
                                .executes(context -> {
                                        ServerCommandSource source = context.getSource();
                                        int daysPassed = ModTimeManager.getDaysPassed();

                                        source.sendFeedback(() -> Text.translatable("monoland.current.day", daysPassed)
                                                        .copyContentOnly()
                                                        .styled(style -> style.withColor(Formatting.YELLOW)), false);

                                        if (daysPassed >= 12) {
                                                source.sendFeedback(() -> Text
                                                                .translatable("monoland.days.passed.12"),
                                                                false);
                                        }

                                        return 1;
                                }));
        }
}
