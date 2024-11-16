package dev.prangellplays.otwd.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.prangellplays.otwd.OTWD;
import eu.midnightdust.lib.config.MidnightConfig;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, OTWD.MOD_ID);
    }
}