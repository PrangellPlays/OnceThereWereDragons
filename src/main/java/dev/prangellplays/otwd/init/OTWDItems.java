package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.item.DragonBookItem;
import dev.prangellplays.otwd.item.DragonCrystalItem;
import dev.prangellplays.otwd.item.DragonManualItem;
import dev.prangellplays.otwd.item.IronMixItem;
import dev.prangellplays.otwd.item.dragonegg.httyd.LightfuryEggIncubatedItem;
import dev.prangellplays.otwd.item.dragonegg.httyd.NightlightEggIncubatedItem;
import dev.prangellplays.otwd.item.pouch.DragonCrystalPouchItem;
import dev.prangellplays.otwd.item.pouch.PouchItem;
import dev.prangellplays.otwd.item.dragonegg.httyd.DeadlyNadderIncubatedItem;
import dev.prangellplays.otwd.item.dragonegg.httyd.NightfuryEggIncubatedItem;
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
    public static final Item DRAGON_CRYSTAL;
    public static final Item DRAGON_MANUAL;
    public static final Item IRON_MIX;
    public static final Item DRAGON_BOOK;

    //Gronckle Iron
    public static final Item GRONCKLE_IRON_INGOT;
    public static final Item RAW_GRONCKLE_IRON;

    //Pouch
    public static final Item POUCH;
    public static final Item DRAGON_CRYSTAL_POUCH;

    //Incubated Dragon Egg
    //HTTYD
    public static final Item NIGHTFURY_EGG_INCUBATED;
    public static final Item DEADLY_NADDER_EGG_INCUBATED;
    public static final Item LIGHTFURY_EGG_INCUBATED;
    public static final Item NIGHTLIGHT_EGG_INCUBATED;

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
        DRAGON_CRYSTAL = register((String) "dragon_crystal", (Item) (new DragonCrystalItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        DRAGON_MANUAL = register((String) "dragon_manual", (Item) (new DragonManualItem(new FabricItemSettings().maxCount(1).rarity(Rarity.COMMON))));
        IRON_MIX = register((String) "iron_mix", (Item) (new IronMixItem(new FabricItemSettings().rarity(Rarity.COMMON))));
        DRAGON_BOOK = register((String) "dragon_book", (Item) (new DragonBookItem(new FabricItemSettings().rarity(Rarity.COMMON))));

        //Gronckle Iron
        GRONCKLE_IRON_INGOT = register((String) "gronckle_iron_ingot", (Item) (new Item(new FabricItemSettings().fireproof().rarity(Rarity.COMMON))));
        RAW_GRONCKLE_IRON = register((String) "raw_gronckle_iron", (Item) (new Item(new FabricItemSettings().fireproof().rarity(Rarity.COMMON))));

        //Pouch
        POUCH = register((String) "pouch", (Item) (new PouchItem(new FabricItemSettings().maxCount(1).rarity(Rarity.COMMON))));
        DRAGON_CRYSTAL_POUCH = register((String) "dragon_crystal_pouch", (Item) (new DragonCrystalPouchItem(new FabricItemSettings().fireproof().maxCount(1).rarity(Rarity.COMMON))));

        //Incubated Dragon Egg
        //HTTYD
        NIGHTFURY_EGG_INCUBATED = register((String) "nightfury_egg_incubated", (Item) (new NightfuryEggIncubatedItem(new FabricItemSettings().maxCount(1).fireproof())));
        DEADLY_NADDER_EGG_INCUBATED = register((String) "deadly_nadder_egg_incubated", (Item) (new DeadlyNadderIncubatedItem(new FabricItemSettings().maxCount(1).fireproof())));
        LIGHTFURY_EGG_INCUBATED = register((String) "lightfury_egg_incubated", (Item) (new LightfuryEggIncubatedItem(new FabricItemSettings().maxCount(1).fireproof())));
        NIGHTLIGHT_EGG_INCUBATED = register((String) "nightlight_egg_incubated", (Item) (new NightlightEggIncubatedItem(new FabricItemSettings().maxCount(1).fireproof())));
    }
}
