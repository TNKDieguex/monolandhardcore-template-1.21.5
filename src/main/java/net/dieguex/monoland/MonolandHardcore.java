package net.dieguex.monoland;

import net.dieguex.monoland.mobGeneration.ZombieMod;
import net.fabricmc.api.ModInitializer;
import net.dieguex.monoland.util.ModRegistries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonolandHardcore implements ModInitializer {
	public static final String MOD_ID = "monolandhardcore";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModRegistries.registerModStuffs();
	}

}