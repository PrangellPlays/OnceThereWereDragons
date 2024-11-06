package dev.prangellplays.otwd.block.dragonegg.httyd;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeadlyNadderEggBlock extends Block {
    private static final VoxelShape SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0.28125, 0.0625, 0.28125, 0.71875, 0.375, 0.71875), VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875), VoxelShapes.cuboid(0.3125, 0.375, 0.3125, 0.6875, 0.5, 0.6875), VoxelShapes.cuboid(0.375, 0.5, 0.375, 0.625, 0.5625, 0.625));

    public DeadlyNadderEggBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("block.otwd.dragon_egg.incubated").formatted(Formatting.GRAY).append(Text.literal(": ").formatted(Formatting.GRAY)).append(Text.translatable("block.otwd.dragon_egg.incubated.false").formatted(Formatting.RED)));
    }
}
