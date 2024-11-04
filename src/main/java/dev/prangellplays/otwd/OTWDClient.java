package dev.prangellplays.otwd;

import dev.prangellplays.otwd.init.OTWDBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class OTWDClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(OTWDBlocks.NIGHTFURY_EGG, RenderLayer.getCutout());
    }
}
