package net.dieguex.monoland.commands;

import net.dieguex.monoland.timeManager.ModTimeManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ServerInfoMessages {
    public static void send(ServerCommandSource source) {
        int day = ModTimeManager.getDaysPassed();

        source.sendMessage(Text.literal("==============================")
                .styled(style -> style.withColor(Formatting.RED)));

        if (day >= 0) {
            source.sendMessage(
                    Text.translatable("infoserver.day0.title").styled(style -> style.withColor(Formatting.DARK_RED)));
            source.sendMessage(Text.translatable("infoserver.day0.spiders"));
            source.sendMessage(Text.translatable("infoserver.day0.skeletons"));
            source.sendMessage(Text.translatable("infoserver.day0.breezes"));
        }

        if (day >= 5) {
            source.sendMessage(Text.literal(""));
            source.sendMessage(Text.translatable("infoserver.day5.title")
                    .styled(style -> style.withColor(Formatting.DARK_RED)));
            source.sendMessage(Text.translatable("infoserver.day5.pigmans"));
            source.sendMessage(Text.translatable("infoserver.day5.spiders"));
            source.sendMessage(Text.translatable("infoserver.day5.ravagers"));
            source.sendMessage(Text.translatable("infoserver.day5.phantoms"));
            source.sendMessage(Text.translatable("infoserver.day5.creepers"));
            source.sendMessage(Text.translatable("infoserver.day5.drops_removed"));
            source.sendMessage(Text.translatable("infoserver.day5.creaking"));
        }

        if (day >= 8) {
            source.sendMessage(Text.literal(""));
            source.sendMessage(Text.translatable("infoserver.day8.title")
                    .styled(style -> style.withColor(Formatting.DARK_RED)));
            source.sendMessage(Text.translatable("infoserver.day8.spiders"));
            source.sendMessage(Text.translatable("infoserver.day8.giga_slime"));
            source.sendMessage(Text.translatable("infoserver.day8.giga_magma"));
            source.sendMessage(Text.translatable("infoserver.day8.ghasts"));
            source.sendMessage(Text.translatable("infoserver.day8.new_armor"));
            source.sendMessage(Text.translatable("infoserver.day8.ravager"));
            source.sendMessage(Text.translatable("infoserver.day8.bees"));
            source.sendMessage(Text.translatable("infoserver.day8.endermite"));
        }

        if (day >= 10) {
            source.sendMessage(Text.literal(""));
            source.sendMessage(Text.translatable("infoserver.day10.title")
                    .styled(style -> style.withColor(Formatting.DARK_RED)));
            source.sendMessage(Text.translatable("infoserver.day10.skeletons"));
            source.sendMessage(Text.translatable("infoserver.day10.creepers"));
            source.sendMessage(Text.translatable("infoserver.day10.pigmans"));
            source.sendMessage(Text.translatable("infoserver.day10.zombies"));
            source.sendMessage(Text.translatable("infoserver.day10.pillagers"));
            source.sendMessage(Text.translatable("infoserver.day10.endermen"));
            source.sendMessage(Text.translatable("infoserver.day10.silverfish"));
        }

        if (day >= 14) {
            source.sendMessage(Text.literal(""));
            source.sendMessage(Text.translatable("infoserver.day14.title")
                    .styled(style -> style.withColor(Formatting.DARK_RED)));
            source.sendMessage(Text.translatable("infoserver.day14.drop_update"));
            source.sendMessage(Text.translatable("infoserver.day14.creaking"));
            source.sendMessage(Text.translatable("infoserver.day14.spiders"));
            source.sendMessage(Text.translatable("infoserver.day14.death_storm"));
            source.sendMessage(Text.translatable("infoserver.day14.ghast_attack"));
            source.sendMessage(Text.translatable("infoserver.day14.endermen"));
            source.sendMessage(Text.translatable("infoserver.day14.guardians"));
            source.sendMessage(Text.translatable("infoserver.day14.shulkers"));
            source.sendMessage(Text.translatable("infoserver.day14.ultra_ravager"));
            source.sendMessage(Text.translatable("infoserver.day14.end_event"));
        }

        if (day >= 20) {
            source.sendMessage(Text.literal(""));
            source.sendMessage(Text.translatable("infoserver.day20.title")
                    .styled(style -> style.withColor(Formatting.DARK_RED)));
            source.sendMessage(Text.translatable("infoserver.day20.skeletons"));
            source.sendMessage(Text.translatable("infoserver.day20.spiders"));
            source.sendMessage(Text.translatable("infoserver.day20.blazes"));
            source.sendMessage(Text.translatable("infoserver.day20.shulkers"));
            source.sendMessage(Text.translatable("infoserver.day20.phantoms"));
            source.sendMessage(Text.translatable("infoserver.day20.ravagers"));
            source.sendMessage(Text.translatable("infoserver.day20.llamas"));
            source.sendMessage(Text.translatable("infoserver.day20.vexes"));
            source.sendMessage(Text.translatable("infoserver.day20.vindicators"));
            source.sendMessage(Text.translatable("infoserver.day20.giants"));
            source.sendMessage(Text.translatable("infoserver.day20.wither_emperor"));
        }

        if (day >= 25) {
            source.sendMessage(Text.literal(""));
            source.sendMessage(Text.translatable("infoserver.day25.title")
                    .styled(style -> style.withColor(Formatting.DARK_RED)));
            source.sendMessage(Text.translatable("infoserver.day25.endermen"));
            source.sendMessage(Text.translatable("infoserver.day25.drowned"));
            source.sendMessage(Text.translatable("infoserver.day25.vexes"));
            source.sendMessage(Text.translatable("infoserver.day25.phantoms"));
            source.sendMessage(Text.translatable("infoserver.day25.vindicators"));
            source.sendMessage(Text.translatable("infoserver.day25.skeletons"));
        }

        source.sendMessage(Text.literal("==============================")
                .styled(style -> style.withColor(Formatting.RED)));
    }
}
