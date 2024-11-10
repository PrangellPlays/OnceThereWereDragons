package dev.prangellplays.otwd.util;

import dev.prangellplays.otwd.OTWD;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class OTWDTags {
    public static class Blocks {

        private static TagKey<Block> createBlockTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(OTWD.MOD_ID, name));
        }

        private static TagKey<Block> createCommonBlockTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> DRAGON_EGG_HOT = createItemTag("dragon_egg_hot");
        public static final TagKey<Item> DRAGON_EGG_COLD = createItemTag("dragon_egg_cold");
        public static final TagKey<Item> INCUBATED_DRAGON_EGG = createItemTag("incubated_dragon_egg");
        public static final TagKey<Item> GRONCKLE_IRON = createItemTag("gronckle_iron");
        public static final TagKey<Item> INCUBATOR = createItemTag("incubator");

        private static TagKey<Item> createItemTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(OTWD.MOD_ID, name));
        }

        private static TagKey<Item> createCommonItemTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
        }
    }

    public static void init() {
    }
}
