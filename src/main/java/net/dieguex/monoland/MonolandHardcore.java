package net.dieguex.monoland;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonolandHardcore implements ModInitializer {
	public static final String MOD_ID = "monolandhardcore";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ZombieMod.register();
	}
}