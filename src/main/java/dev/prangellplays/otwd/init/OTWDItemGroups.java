package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OTWDItemGroups {
    public static final ItemGroup OTWD_ITEMS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(OTWD.MOD_ID, "otwd_items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.otwd.items")).icon(() -> new ItemStack(OTWDItems.DRAGON_SADDLE)).entries((displayContext, entries) -> {
                entries.add(OTWDItems.DRAGON_SADDLE);
                entries.add(OTWDBlocks.DRAGON_EGG_INCUBATOR_HOT);
                entries.add(OTWDBlocks.DRAGON_EGG_INCUBATOR_COLD);
                entries.add(OTWDItems.DRAGON_CRYSTAL);
                entries.add(OTWDItems.GRONCKLE_IRON_INGOT);
                entries.add(OTWDItems.RAW_GRONCKLE_IRON);
                entries.add(OTWDBlocks.GRONCKLE_IRON_ORE);
                entries.add(OTWDBlocks.GRONCKLE_IRON_BLOCK);
                entries.add(OTWDItems.POUCH);
                entries.add(OTWDItems.DRAGON_CRYSTAL_POUCH);
                entries.add(OTWDItems.DRAGON_MANUAL);
                entries.add(OTWDBlocks.LIMESTONE);
                entries.add(OTWDItems.IRON_MIX);
                entries.add(OTWDItems.DRAGON_BOOK);
            }).build());
    public static final ItemGroup OTWD_DRAGON_EGGS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(OTWD.MOD_ID, "otwd_dragon_eggs"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.otwd.dragon_egg")).icon(() -> new ItemStack(OTWDBlocks.NIGHTFURY_EGG)).entries((displayContext, entries) -> {
                //HTTYD
                entries.add(OTWDBlocks.NIGHTFURY_EGG);
                entries.add(OTWDBlocks.DEADLY_NADDER_EGG);
                entries.add(OTWDBlocks.LIGHTFURY_EGG);
                entries.add(OTWDBlocks.NIGHTLIGHT_EGG);

                //OTWD

            }).build());

    public static void init() {
    }
}
