package dev.prangellplays.otwd.item;

import dev.prangellplays.otwd.block.PlacedDragonManualBlock;
import dev.prangellplays.otwd.client.screen.dragon_manual.DragonManualScreen;
import dev.prangellplays.otwd.init.OTWDBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class DragonManualItem extends Item {
    public DragonManualItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            BlockPos raycastResult = raycast(world, user, RaycastContext.FluidHandling.NONE).getBlockPos();
            if (!world.getBlockState(raycastResult.add(0, 1, 0)).isAir()) {
                return TypedActionResult.pass(getDefaultStack());
            } else {
                world.setBlockState(raycastResult.add(0, 1, 0), OTWDBlocks.DRAGON_MANUAL_PLACED.getDefaultState().with(PlacedDragonManualBlock.FACING, user.getHorizontalFacing().getOpposite()));
                world.playSound(raycastResult.getX(), raycastResult.getY(), raycastResult.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS,1.0f, 1.0f, true);
                if (!user.isCreative()) {
                    user.getStackInHand(user.getActiveHand()).decrement(1);
                }
            }
        } else {
            if (world.isClient) {
                MinecraftClient.getInstance().setScreen(new DragonManualScreen());
            }
        }
        return super.use(world, user, hand);
    }
}
