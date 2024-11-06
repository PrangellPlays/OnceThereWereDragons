package dev.prangellplays.otwd.item.dragonegg.httyd;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeadlyNadderIncubatedItem extends Item {
    public DeadlyNadderIncubatedItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Entity entity = ((EntityType<Entity>) EntityType.get("minecraft:wolf").get()).create(world);
        if (entity instanceof WolfEntity wolf) {
            wolf.setOwner(user);
        }
        entity.setCustomName(Text.literal("Deadly Nadder"));
        entity.setPosition(raycast(world, user, RaycastContext.FluidHandling.NONE).getPos());
        world.spawnEntity(entity);
        if (!user.isCreative()) {
            if (user.getMainHandStack().isOf(this)) {
                user.getMainHandStack().decrement(1);
            } else if (user.getOffHandStack().isOf(this)) {
                user.getOffHandStack().decrement(1);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("block.otwd.dragon_egg.incubated").formatted(Formatting.GRAY).append(Text.literal(": ").formatted(Formatting.GRAY)).append(Text.translatable("block.otwd.dragon_egg.incubated.true").formatted(Formatting.GREEN)));
    }
}
