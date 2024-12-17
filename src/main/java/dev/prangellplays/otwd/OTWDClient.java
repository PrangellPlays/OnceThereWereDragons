package dev.prangellplays.otwd;

import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorColdRenderer;
import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorHotRenderer;
import dev.prangellplays.otwd.client.renderer.InvisibleEntityRenderer;
import dev.prangellplays.otwd.client.screen.OTWDScreenHandlers;
import dev.prangellplays.otwd.client.screen.incubator.DragonEggIncubatorColdScreen;
import dev.prangellplays.otwd.client.screen.incubator.DragonEggIncubatorHotScreen;
import dev.prangellplays.otwd.client.screen.pouch.DragonCrystalPouchScreen;
import dev.prangellplays.otwd.client.screen.pouch.PouchScreen;
import dev.prangellplays.otwd.init.OTWDBlockEntities;
import dev.prangellplays.otwd.init.OTWDBlocks;
import dev.prangellplays.otwd.init.OTWDEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.InputUtil;

public class OTWDClient implements ClientModInitializer {
    //Keybinding
    public static KeyBinding hover;
    public static KeyBinding meleeAttack;
    public static KeyBinding primaryAttack;
    public static KeyBinding secondaryAttack;
    public static KeyBinding specialAbility;
    public static KeyBinding increaseSpeed;
    public static KeyBinding decreaseSpeed;

    @Override
    public void onInitializeClient() {
        //Block Render Layer Map
        BlockRenderLayerMap.INSTANCE.putBlock(OTWDBlocks.NIGHTFURY_EGG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OTWDBlocks.DEADLY_NADDER_EGG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OTWDBlocks.LIGHTFURY_EGG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OTWDBlocks.NIGHTLIGHT_EGG, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(OTWDBlocks.DRAGON_MANUAL_PLACED, RenderLayer.getCutout());

        //Block Entity Renderer
        BlockEntityRendererFactories.register(OTWDBlockEntities.DRAGON_EGG_INCUBATOR_HOT, DragonEggIncubatorHotRenderer::new);
        BlockEntityRendererFactories.register(OTWDBlockEntities.DRAGON_EGG_INCUBATOR_COLD, DragonEggIncubatorColdRenderer::new);

        //Screen
        HandledScreens.register(OTWDScreenHandlers.DRAGON_EGG_INCUBATOR_HOT, DragonEggIncubatorHotScreen::new);
        HandledScreens.register(OTWDScreenHandlers.DRAGON_EGG_INCUBATOR_COLD, DragonEggIncubatorColdScreen::new);
        HandledScreens.register(OTWDScreenHandlers.POUCH, PouchScreen::new);
        HandledScreens.register(OTWDScreenHandlers.DRAGON_CRYSTAL_POUCH, DragonCrystalPouchScreen::new);

        //Entity
        EntityRendererRegistry.register(OTWDEntities.PHANTOM_STALKER, InvisibleEntityRenderer::new);

        //Keybinding
        hover = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.otwd.hover", InputUtil.Type.KEYSYM, 72, "category.otwd"));
        meleeAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.otwd.melee_attack", InputUtil.Type.KEYSYM, 71, "category.otwd"));
        primaryAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.otwd.primary_attack", InputUtil.Type.KEYSYM, 321, "category.otwd"));
        secondaryAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.otwd.secondary_attack", InputUtil.Type.KEYSYM, 322, "category.otwd"));
        specialAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.otwd.special_ability", InputUtil.Type.KEYSYM, 323, "category.otwd"));
        increaseSpeed = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.otwd.increase_speed", InputUtil.Type.KEYSYM, 32, "category.otwd"));
        decreaseSpeed = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.otwd.decrease_speed", InputUtil.Type.KEYSYM, 341, "category.otwd"));
    }
}
