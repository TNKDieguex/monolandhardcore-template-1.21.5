package net.dieguex.monoland.timeManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.Duration;

public class ModTimeManager {
    private static final File timeFile = new File("mod_data/first_boot.txt");
    private static Instant startTime;

    public static void init() {
        try {
            if (!timeFile.exists()) {
                timeFile.getParentFile().mkdirs();
                Files.writeString(timeFile.toPath(), Instant.now().toString());
            }
            String saved = Files.readString(timeFile.toPath());
            startTime = Instant.parse(saved);
        } catch (Exception e) {
            e.printStackTrace();
            startTime = Instant.now();
        }
    }

    public static void simulateDaysPassed(long daysAgo) {

        Instant simulatedStart = Instant.now().minus(Duration.ofDays(daysAgo));
        try {
            Files.writeString(timeFile.toPath(), simulatedStart.toString());
            startTime = simulatedStart;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPassedDays(int days) {
        return Duration.between(startTime, Instant.now()).toDays() >= days;
    }
}
