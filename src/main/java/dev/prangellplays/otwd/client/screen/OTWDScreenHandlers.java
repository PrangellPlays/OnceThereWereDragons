package dev.prangellplays.otwd.client.screen;

import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.client.screen.incubator.DragonEggIncubatorColdScreenHandler;
import dev.prangellplays.otwd.client.screen.incubator.DragonEggIncubatorHotScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class OTWDScreenHandlers {
    public static final ScreenHandlerType<DragonEggIncubatorHotScreenHandler> DRAGON_EGG_INCUBATOR_HOT = Registry.register(Registries.SCREEN_HANDLER, new Identifier(OTWD.MOD_ID, "dragon_egg_incubator_hot"), new ExtendedScreenHandlerType<>(DragonEggIncubatorHotScreenHandler::new));
    public static final ScreenHandlerType<DragonEggIncubatorColdScreenHandler> DRAGON_EGG_INCUBATOR_COLD = Registry.register(Registries.SCREEN_HANDLER, new Identifier(OTWD.MOD_ID, "dragon_egg_incubator_cold"), new ExtendedScreenHandlerType<>(DragonEggIncubatorColdScreenHandler::new));

    public static void init() {
    }
}