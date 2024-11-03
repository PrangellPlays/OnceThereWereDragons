package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.block.dragonegg.httyd.NightfuryEggBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class OTWDBlocks {
    protected static final Map<Block, Identifier> BLOCKS = new LinkedHashMap();
    //DRAGON EGG
    //HTTYD
    public static final Block NIGHTFURY_EGG;

    //OTWD

    public static void init() {
        BLOCKS.forEach((block, id) -> {
            Registry.register(Registries.BLOCK, id, block);
        });
    }

    protected static <T extends Block> T register(String name, T block) {
        BLOCKS.put(block, OTWD.id(name));
        return block;
    }

    protected static <T extends Block> T registerWithItem(String name, T block) {
        return registerWithItem(name, block, new FabricItemSettings());
    }

    protected static <T extends Block> T registerWithItem(String name, T block, FabricItemSettings settings) {
        return registerWithItem(name, block, (b) -> {
            return new BlockItem(b, settings);
        });
    }

    protected static <T extends Block> T registerWithItem(String name, T block, Function<T, BlockItem> itemGenerator) {
        OTWDItems.register(name, (BlockItem)itemGenerator.apply(block));
        return register(name, block);
    }

    public OTWDBlocks() {
    }

    static {
        //DRAGON EGG
        //HTTYD
        NIGHTFURY_EGG = registerWithItem("nightfury_egg", new NightfuryEggBlock(AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).strength(0.5F).sounds(BlockSoundGroup.STONE).nonOpaque()));

        //OTWD
    }
}