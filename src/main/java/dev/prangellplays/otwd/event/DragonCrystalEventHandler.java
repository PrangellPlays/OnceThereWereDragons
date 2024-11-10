package dev.prangellplays.otwd.event;

import dev.prangellplays.otwd.item.DragonCrystalItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class DragonCrystalEventHandler {
    public static void init() {
        UseEntityCallback.EVENT.register((PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) -> {
            ItemStack stack = player.getStackInHand(hand);

            if (entity instanceof PlayerEntity) {
                return ActionResult.PASS;
            }

            if (entity instanceof LivingEntity targetEntity) {

                if (stack.getItem() instanceof DragonCrystalItem dragonCrystal) {
                    ActionResult result = dragonCrystal.captureDragon(stack, player, targetEntity, hand);
                    if (result == ActionResult.SUCCESS) {
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}
