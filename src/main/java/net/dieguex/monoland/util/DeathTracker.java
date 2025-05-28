package net.dieguex.monoland.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.player.PlayerEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathTracker {
    private static final File deathFile = new File("mod_data/muertes.json");
    private static final Map<UUID, Integer> deaths = new HashMap<>();
    private static final Gson gson = new Gson();

    public static void init() {
        try {
            if (!deathFile.exists()) {
                deathFile.getParentFile().mkdirs();
                save();
            }
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof PlayerEntity player) {
                incrementDeath(player);
            }
        });
    }

    public static void incrementDeath(PlayerEntity player) {
        UUID uuid = player.getUuid();
        deaths.put(uuid, deaths.getOrDefault(uuid, 0) + 1);
        save();
    }

    public static Map<UUID, Integer> getDeaths() {
        return deaths;
    }

    private static void save() {
        try (FileWriter writer = new FileWriter(deathFile)) {
            gson.toJson(deaths, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load() {
        if (deathFile.exists()) {
            try (FileReader reader = new FileReader(deathFile)) {
                Type type = new TypeToken<Map<UUID, Integer>>() {
                }.getType();
                Map<UUID, Integer> loaded = gson.fromJson(reader, type);
                if (loaded != null) {
                    deaths.putAll(loaded);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}