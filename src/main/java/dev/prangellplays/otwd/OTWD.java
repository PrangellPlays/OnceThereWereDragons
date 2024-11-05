package dev.prangellplays.otwd;

import dev.prangellplays.otwd.init.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OTWD implements ModInitializer {
	public static final String MOD_ID = "otwd";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		OTWDBlocks.init();
		OTWDItems.init();
		OTWDItemGroups.init();
		OTWDBlockEntities.init();
		OTWDRecipes.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}