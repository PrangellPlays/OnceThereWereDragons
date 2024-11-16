package dev.prangellplays.otwd.block;

import dev.prangellplays.otwd.client.screen.dragon_manual.DragonManualScreen;
import dev.prangellplays.otwd.init.OTWDItems;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlacedDragonManualBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape X_AXIS_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0.125, 0, 0.25, 0.875, 0.03125, 0.75), VoxelShapes.cuboid(0.125, 0.09375, 0.25, 0.875, 0.125, 0.75), VoxelShapes.cuboid(0.125, 0.03125, 0.71875, 0.875, 0.09375, 0.75), VoxelShapes.cuboid(0.125, 0.03125, 0.28125, 0.8625, 0.09375, 0.71875));
    private static final VoxelShape Z_AXIS_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0.25, 0, 0.125, 0.75, 0.03125, 0.875), VoxelShapes.cuboid(0.25, 0.09375, 0.125, 0.75, 0.125, 0.875), VoxelShapes.cuboid(0.71875, 0.03125, 0.125, 0.75, 0.09375, 0.875), VoxelShapes.cuboid(0.28125, 0.03125, 0.13125, 0.71875, 0.09375, 0.86875));
    public PlacedDragonManualBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = (Direction)state.get(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = (Direction)state.get(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isSneaking()) {
            player.setStackInHand(player.getActiveHand(), OTWDItems.DRAGON_MANUAL.getDefaultStack());
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS,1.0f, 1.0f, true);
        } else {
            if (world.isClient) {
                MinecraftClient.getInstance().setScreen(new DragonManualScreen());
            }
        }


        return super.onUse(state, world, pos, player, hand, hit);
    }
}
