package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public class OTWDItems {
    protected static final Map<Item, Identifier> ITEMS = new LinkedHashMap();
    public static final Item DRAGON_SADDLE;

    public static void init() {
        ITEMS.forEach((item, id) -> {
            Registry.register(Registries.ITEM, id, item);
        });
    }

    protected static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, OTWD.id(name));
        return item;
    }

    public OTWDItems() {
    }

    static {
        DRAGON_SADDLE = register((String) "dragon_saddle", (Item) (new Item(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
    }
}
