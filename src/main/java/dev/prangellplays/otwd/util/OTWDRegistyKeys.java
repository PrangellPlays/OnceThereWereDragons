package dev.prangellplays.otwd.util;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public class OTWDRegistyKeys {
    public static final RegistryKey<Registry<Structure>> DRAGON_NEST = of("worldgen/structure");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(new Identifier(id));
    }
}
