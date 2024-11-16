package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.block.PlacedDragonManualBlock;
import dev.prangellplays.otwd.block.dragonegg.httyd.DeadlyNadderEggBlock;
import dev.prangellplays.otwd.block.dragonegg.httyd.LightfuryEggBlock;
import dev.prangellplays.otwd.block.dragonegg.httyd.NightfuryEggBlock;
import dev.prangellplays.otwd.block.dragonegg.httyd.NightlightEggBlock;
import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorColdBlock;
import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorHotBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
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
    //Dragon Egg
    //HTTYD
    public static final Block NIGHTFURY_EGG;
    public static final Block DEADLY_NADDER_EGG;
    public static final Block LIGHTFURY_EGG;
    public static final Block NIGHTLIGHT_EGG;

    //OTWD


    //Gronckle Iron
    public static final Block GRONCKLE_IRON_ORE;
    public static final Block GRONCKLE_IRON_BLOCK;

    //Incubator
    public static final Block DRAGON_EGG_INCUBATOR_COLD;
    public static final Block DRAGON_EGG_INCUBATOR_HOT;

    public static final Block LIMESTONE;

    public static final Block DRAGON_MANUAL_PLACED;

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
        //Dragon Egg
        //HTTYD
        NIGHTFURY_EGG = registerWithItem("nightfury_egg", new NightfuryEggBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(0.5F).sounds(BlockSoundGroup.METAL).nonOpaque().notSolid()));
        DEADLY_NADDER_EGG = registerWithItem("deadly_nadder_egg", new DeadlyNadderEggBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIAMOND_BLUE).strength(0.5F).sounds(BlockSoundGroup.METAL).nonOpaque().notSolid()));
        LIGHTFURY_EGG = registerWithItem("lightfury_egg", new LightfuryEggBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE_GRAY).strength(0.5F).sounds(BlockSoundGroup.METAL).nonOpaque().notSolid()));
        NIGHTLIGHT_EGG = registerWithItem("nightlight_egg", new NightlightEggBlock(AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE_GRAY).strength(0.5F).sounds(BlockSoundGroup.METAL).nonOpaque().notSolid()));

        //OTWD


        //Gronckle Iron
        GRONCKLE_IRON_ORE = registerWithItem("gronckle_iron_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F)));
        GRONCKLE_IRON_BLOCK = registerWithItem("gronckle_iron_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).instrument(Instrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));

        //Incubator
        DRAGON_EGG_INCUBATOR_COLD = registerWithItem("dragon_egg_incubator_cold", new DragonEggIncubatorColdBlock(AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).instrument(Instrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));
        DRAGON_EGG_INCUBATOR_HOT = registerWithItem("dragon_egg_incubator_hot", new DragonEggIncubatorHotBlock(AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).instrument(Instrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));

        LIMESTONE = registerWithItem("limestone", new Block(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(Instrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.STONE)));

        DRAGON_MANUAL_PLACED = register("dragon_manual_placed", new PlacedDragonManualBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.8F).sounds(BlockSoundGroup.SNOW)));
    }
}
