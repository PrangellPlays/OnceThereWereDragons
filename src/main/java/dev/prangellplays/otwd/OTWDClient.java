package dev.prangellplays.otwd;

import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorColdRenderer;
import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorHotRenderer;
import dev.prangellplays.otwd.client.screen.OTWDScreenHandlers;
import dev.prangellplays.otwd.client.screen.incubator.DragonEggIncubatorColdScreen;
import dev.prangellplays.otwd.client.screen.incubator.DragonEggIncubatorHotScreen;
import dev.prangellplays.otwd.init.OTWDBlockEntities;
import dev.prangellplays.otwd.init.OTWDBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class OTWDClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(OTWDBlocks.NIGHTFURY_EGG, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(OTWDBlockEntities.DRAGON_EGG_INCUBATOR_HOT, DragonEggIncubatorHotRenderer::new);
        BlockEntityRendererFactories.register(OTWDBlockEntities.DRAGON_EGG_INCUBATOR_COLD, DragonEggIncubatorColdRenderer::new);
        HandledScreens.register(OTWDScreenHandlers.DRAGON_EGG_INCUBATOR_HOT, DragonEggIncubatorHotScreen::new);
        HandledScreens.register(OTWDScreenHandlers.DRAGON_EGG_INCUBATOR_COLD, DragonEggIncubatorColdScreen::new);
    }
}
