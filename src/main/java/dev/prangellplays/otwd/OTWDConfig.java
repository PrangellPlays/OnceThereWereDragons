package dev.prangellplays.otwd;

import eu.midnightdust.lib.config.MidnightConfig;

public class OTWDConfig extends MidnightConfig {
    public static final String otwd = "otwd";
    @Entry(category = otwd)
    public static boolean enableHTTYD = true;
    @Entry(category = otwd)
    public static boolean enableOTWD = true;
    @Entry(category = otwd)
    public static boolean dragonBreak = true;
    @Entry(category = otwd)
    public static boolean roleplay = false;
}
