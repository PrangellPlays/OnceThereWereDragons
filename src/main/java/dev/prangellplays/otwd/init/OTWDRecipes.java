package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.recipe.DragonEggIncubatorColdRecipe;
import dev.prangellplays.otwd.recipe.DragonEggIncubatorHotRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OTWDRecipes {
    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(OTWD.MOD_ID, DragonEggIncubatorHotRecipe.Serializer.ID), DragonEggIncubatorHotRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(OTWD.MOD_ID, DragonEggIncubatorHotRecipe.Type.ID), DragonEggIncubatorHotRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(OTWD.MOD_ID, DragonEggIncubatorColdRecipe.Serializer.ID), DragonEggIncubatorColdRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(OTWD.MOD_ID, DragonEggIncubatorColdRecipe.Type.ID), DragonEggIncubatorColdRecipe.Type.INSTANCE);
    }
}
