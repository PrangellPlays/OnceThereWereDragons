package dev.prangellplays.otwd.block.incubator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonEggIncubatorHotRenderer implements BlockEntityRenderer<DragonEggIncubatorHotBlockEntity> {
    public DragonEggIncubatorHotRenderer(BlockEntityRendererFactory.Context context) {
    }

    private static final Vec3d ITEM_POS = new Vec3d(0.5, 1.5, 0.5);

    @Override
    public void render(DragonEggIncubatorHotBlockEntity incubatorBlockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack heldItem = incubatorBlockEntity.getStack(0);

        if (!heldItem.isEmpty()) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                World world = player.getWorld();
                float bob = (float) (Math.sin((player.age + tickDelta) * 0.1f) * 0.035f);
                //float rotation = player.age * 2 + tickDelta;
                renderItem(heldItem, ITEM_POS.add(0, bob, 0), 0, matrices, vertexConsumers, light, overlay, world);
            }
        }
    }

    private void renderItem(ItemStack itemStack, Vec3d offset, float yRot, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, World world) {
        matrices.push();

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.translate(offset.x, offset.y + 0.05, offset.z);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yRot));
        matrices.scale(1.3f, 1.3f, 1.3f);

        if (itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof ToolItem) {
            matrices.translate(0, 0.1f, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-45));
        }

        itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, world, (int) world.getTime());
        matrices.pop();
    }
}