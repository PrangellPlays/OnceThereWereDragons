package dev.prangellplays.otwd.item;

import dev.prangellplays.otwd.OTWD;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DragonCrystalItem extends Item {
    private static final Logger LOGGER = LogManager.getLogger();

    public DragonCrystalItem(Settings settings) {
        super(settings);
    }

    public ActionResult captureDragon(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        if (!player.getWorld().isClient) {
            String entityId = EntityType.getId(target.getType()).toString();

            if (!OTWD.crystalWhitelist.contains(entityId)) {
                if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                    serverPlayerEntity.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.translatable("item.otwd.dragon_crystal.cannot_capture")));
                }
                return ActionResult.FAIL;
            }

            if (player.getItemCooldownManager().isCoolingDown(this)) {
                return ActionResult.PASS;
            }

            NbtCompound nbt = stack.getOrCreateNbt();

            if (nbt.contains("CapturedDragon")) {
                return ActionResult.FAIL;
            }

            try {
                NbtCompound entityTag = new NbtCompound();
                target.writeNbt(entityTag);

                nbt.putString("CapturedDragonType", entityId);
                nbt.put("CapturedDragon", entityTag);

                target.remove(Entity.RemovalReason.DISCARDED);
            } catch (Exception e) {
                LOGGER.error("Error capturing dragon: " + e.getMessage());
                return ActionResult.FAIL;
            }

            player.getWorld().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.MASTER, 1.0f, 1.0f);
            player.getItemCooldownManager().set(this, 20);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        NbtCompound nbt = stack.getNbt();

        if (player.getItemCooldownManager().isCoolingDown(this)) {
            return TypedActionResult.pass(stack);
        }

        if (nbt != null && nbt.contains("CapturedDragon") && nbt.contains("CapturedDragonType")) {
            if (!world.isClient) {
                try {
                    NbtCompound entityTag = nbt.getCompound("CapturedDragon");
                    String entityTypeString = nbt.getString("CapturedDragonType");

                    Optional<EntityType<?>> optionalEntityType = Registries.ENTITY_TYPE.getOrEmpty(new Identifier(entityTypeString));
                    if (optionalEntityType.isPresent()) {
                        EntityType<?> entityType = optionalEntityType.get();
                        LivingEntity entity = (LivingEntity) entityType.create(world);
                        if (entity != null) {
                            entity.readNbt(entityTag);

                            HitResult hitResult = player.raycast(5.0D, 1.0F, false); // 5 blocks distance

                            if (hitResult.getType() == HitResult.Type.BLOCK) {
                                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                                BlockPos targetPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());

                                entity.refreshPositionAndAngles(
                                        targetPos.getX() + 0.5,
                                        targetPos.getY(),
                                        targetPos.getZ() + 0.5,
                                        player.getYaw(),
                                        player.getPitch()
                                );

                                world.spawnEntity(entity);

                                nbt.remove("CapturedDragon");
                                nbt.remove("CapturedDragonType");

                                stack.setNbt(nbt);
                            }
                        } else {
                            LOGGER.error("Failed to create dragon from captured data.");
                        }
                    } else {
                        LOGGER.error("Dragon type not found in registry: " + entityTypeString);
                    }
                } catch (Exception e) {
                    LOGGER.error("Error releasing dragon: " + e.getMessage());
                }

                player.getItemCooldownManager().set(this, 20);

                return TypedActionResult.success(stack);
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains("CapturedDragonType")) {
            String entityTypeString = nbt.getString("CapturedDragonType");
            tooltip.add(Text.translatable("item.otwd.dragon_crystal.captured").formatted(Formatting.GRAY).append(Text.literal(": ").formatted(Formatting.GRAY)).append(Text.literal(entityTypeString).formatted(Formatting.GREEN)));
        } else {
            tooltip.add(Text.translatable("item.otwd.dragon_crystal.no_dragon").formatted(Formatting.DARK_RED));
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().contains("CapturedDragonType");
    }
}