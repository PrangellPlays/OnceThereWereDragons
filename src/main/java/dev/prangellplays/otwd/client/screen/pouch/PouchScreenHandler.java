package dev.prangellplays.otwd.client.screen.pouch;

import dev.prangellplays.otwd.client.screen.OTWDScreenHandlers;
import dev.prangellplays.otwd.client.screen.util.Dimension;
import dev.prangellplays.otwd.client.screen.util.Point;
import dev.prangellplays.otwd.item.DragonCrystalItem;
import dev.prangellplays.otwd.item.pouch.DragonCrystalPouchItem;
import dev.prangellplays.otwd.item.pouch.PouchItem;
import dev.prangellplays.otwd.util.InventoryUtils;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class PouchScreenHandler extends ScreenHandler {

    private final ItemStack pouchStack;
    private final int padding = 8;
    private final int titleSpace = 10;

    public PouchScreenHandler(int synchronizationID, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(synchronizationID, playerInventory, packetByteBuf.readItemStack());
    }

    public PouchScreenHandler(int synchronizationID, PlayerInventory playerInventory, ItemStack backpackStack) {
        super(OTWDScreenHandlers.POUCH, synchronizationID);
        this.pouchStack = backpackStack;

        if (backpackStack.getItem() instanceof PouchItem) {
            setupContainer(playerInventory, backpackStack);
        } else {
            PlayerEntity player = playerInventory.player;
            this.onClosed(player);
        }
    }

    private void setupContainer(PlayerInventory playerInventory, ItemStack backpackStack) {
        Dimension dimension = getDimension();
        int rowWidth = 5;
        int numberOfRows = 3;

        NbtList tag = backpackStack.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE);
        PouchInventory inventory = new PouchInventory(rowWidth * numberOfRows) {
            @Override
            public void markDirty() {
                backpackStack.getOrCreateNbt().put("Inventory", InventoryUtils.toTag(this));
                super.markDirty();
            }
        };

        InventoryUtils.fromTag(tag, inventory);

        for (int y = 0; y < numberOfRows; y++) {
            for (int x = 0; x < rowWidth; x++) {
                Point backpackSlotPosition = getBackpackSlotPosition(dimension, x, y);
                addSlot(new PouchLockedSlot(inventory, y * rowWidth + x, backpackSlotPosition.x + 1, backpackSlotPosition.y + 1));
            }
        }

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                Point playerInvSlotPosition = getPlayerInvSlotPosition(dimension, x, y);
                this.addSlot(new PouchLockedSlot(playerInventory, x + y * 9 + 9, playerInvSlotPosition.x + 1, playerInvSlotPosition.y + 1));
            }
        }

        for (int x = 0; x < 9; ++x) {
            Point playerInvSlotPosition = getPlayerInvSlotPosition(dimension, x, 3);
            this.addSlot(new PouchLockedSlot(playerInventory, x, playerInvSlotPosition.x + 1, playerInvSlotPosition.y + 1));
        }
    }

    public PouchItem getItem() {
        return (PouchItem) pouchStack.getItem();
    }

    public Dimension getDimension() {
        return new Dimension(padding * 2 + Math.max(5, 9) * 18, padding * 2 + titleSpace * 2 + 8 + (3 + 4) * 18);
    }

    public Point getBackpackSlotPosition(Dimension dimension, int x, int y) {
        return new Point(dimension.getWidth() / 2 - 5 * 9 + x * 18, padding + titleSpace + y * 18);
    }

    public Point getPlayerInvSlotPosition(Dimension dimension, int x, int y) {
        return new Point(dimension.getWidth() / 2 - 9 * 9 + x * 18, dimension.getHeight() - padding - 4 * 18 - 3 + y * 18 + (y == 3 ? 4 : 0));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return pouchStack.getItem() instanceof PouchItem;
    }

    public ItemStack getBackpackStack() {
        return pouchStack;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack toInsert = slot.getStack();
            itemStack = toInsert.copy();
            if (index < 3 * 5) {
                if (!this.insertItem(toInsert, 3 * 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(toInsert, 0, 3 * 5, false)) {
                return ItemStack.EMPTY;
            }

            if (toInsert.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    private class PouchLockedSlot extends Slot {

        public PouchLockedSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return stackMovementIsAllowed(getStack());
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if (inventory instanceof PouchInventory) {
                Item item = stack.getItem();
                if (item instanceof BlockItem blockItem) {
                    return !(blockItem.getBlock() instanceof ShulkerBoxBlock);
                }
                if (item instanceof DragonCrystalItem dragonCrystalItem) {
                    return false;
                }
            }

            return stackMovementIsAllowed(stack);
        }

        private boolean stackMovementIsAllowed(ItemStack stack) {
            if (stack.getItem() instanceof DragonCrystalPouchItem) {
                return false;
            }
            return !(stack.getItem() instanceof PouchItem) && stack != pouchStack;
        }
    }

    public static class PouchInventory extends SimpleInventory {

        public PouchInventory(int slots) {
            super(slots);
        }
    }

}