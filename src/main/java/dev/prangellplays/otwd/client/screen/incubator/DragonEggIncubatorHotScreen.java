package dev.prangellplays.otwd.client.screen.incubator;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.client.screen.renderer.FluidStackRenderer;
import dev.prangellplays.otwd.util.MouseUtil;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class DragonEggIncubatorHotScreen extends HandledScreen<DragonEggIncubatorHotScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(OTWD.MOD_ID, "textures/gui/incubator/dragon_egg_incubator_gui.png");
    private FluidStackRenderer fluidStackRenderer;

    public DragonEggIncubatorHotScreen(DragonEggIncubatorHotScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
        assignFluidStackRenderer();
    }

    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer((FluidConstants.BUCKET / 81) * 10, true, 16, 34);
    }

    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
            context.drawTooltip(Screens.getTextRenderer(this), renderer.getTooltip(handler.blockEntity.fluidStorage, TooltipContext.Default.BASIC),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderFluidTooltip(context, mouseX, mouseY, x, y, 14, 19, fluidStackRenderer);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        fluidStackRenderer.drawFluid(context, handler.blockEntity.fluidStorage, x + 14, y + 19, 16, 34, (FluidConstants.BUCKET / 81) * 10);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}