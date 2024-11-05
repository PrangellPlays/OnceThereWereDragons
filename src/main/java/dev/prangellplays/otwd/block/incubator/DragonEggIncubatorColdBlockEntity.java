package dev.prangellplays.otwd.block.incubator;

import dev.prangellplays.otwd.client.screen.incubator.DragonEggIncubatorColdScreenHandler;
import dev.prangellplays.otwd.init.OTWDBlockEntities;
import dev.prangellplays.otwd.recipe.DragonEggIncubatorColdRecipe;
import dev.prangellplays.otwd.util.ImplementedInventory;
import dev.prangellplays.otwd.util.OTWDTags;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DragonEggIncubatorColdBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private static final int INPUT_SLOT = 0;
    private static final int FLUID_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private int progress = 0;
    private int maxProgress = 72;

    public DragonEggIncubatorColdBlockEntity(BlockPos pos, BlockState state) {
        super(OTWDBlockEntities.DRAGON_EGG_INCUBATOR_COLD, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DragonEggIncubatorColdBlockEntity.this.progress;
                    case 1 -> DragonEggIncubatorColdBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: DragonEggIncubatorColdBlockEntity.this.progress = value;
                    case 1: DragonEggIncubatorColdBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81) * 10; // 1 Bucket = 81000 Droplets = 1000mB || * 10 ==> 10,000mB = 10 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("title.otwd.dragon_egg_incubator_cold");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DragonEggIncubatorColdScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("dragon_egg_incubator_cold.progress", progress);
        nbt.put("dragon_egg_incubator_cold.variant", fluidStorage.variant.toNbt());
        nbt.putLong("dragon_egg_incubator_cold.fluid_amount", fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("dragon_egg_incubator_cold.progress");
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("dragon_egg_incubator_cold.variant"));
        fluidStorage.amount = nbt.getLong("dragon_egg_incubator_cold.fluid_amount");
        super.readNbt(nbt);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        fillUpOnFluid();

        if(canInsertIntoSlot() && hasRecipe()) {
            increaseCraftingProgress();
            markDirty(world, pos, state);

            if(hasCraftingFinished()) {
                craftItem();
                extractFluid();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void extractFluid() {
        if (this.getStack(OUTPUT_SLOT).isIn(OTWDTags.Items.INCUBATED_DRAGON_EGG))
            try(Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.extract(FluidVariant.of(Fluids.LAVA), 2500, transaction);
                transaction.commit();
            }
    }

    private void fillUpOnFluid() {
        if(hasFluidSourceItemInFluidSlot(FLUID_SLOT)) {
            transferItemFluidToTank(FLUID_SLOT);
        }
    }

    private void transferItemFluidToTank(int fluidItemSlot) {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.insert(FluidVariant.of(Fluids.WATER),
                    (FluidConstants.BUCKET / 81), transaction);
            transaction.commit();

            this.setStack(fluidItemSlot, new ItemStack(Items.BUCKET));
        }
    }

    private boolean hasFluidSourceItemInFluidSlot(int fluidItemSlot) {
        return this.getStack(fluidItemSlot).getItem() == Items.WATER_BUCKET;
    }

    private void craftItem() {
        Optional<DragonEggIncubatorColdRecipe> recipe = getCurrentRecipe();

        if(this.getStack(INPUT_SLOT).getItem() == null && this.getStack(INPUT_SLOT).getCount() >= 1) {
            this.removeStack(INPUT_SLOT, 1);
            this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().getOutput(null).getItem(), this.getStack(OUTPUT_SLOT).getCount() + recipe.get().getOutput(null).getCount()));
            this.hasCraftingFinished();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean hasRecipe() {
        Optional<DragonEggIncubatorColdRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack output = recipe.get().getOutput(null);

        return canInsertAmountIntoOutputSlot(output.getCount())
                && canInsertItemIntoOutputSlot(output) && hasEnoughFluidToCraft();
    }

    private boolean hasEnoughFluidToCraft() {
        return this.fluidStorage.amount >= 2500; // mB amount!
    }

    private boolean canInsertIntoSlot() {
        return this.getStack(INPUT_SLOT).isEmpty() ||
                this.getStack(INPUT_SLOT).getCount() < this.getStack(INPUT_SLOT).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.getStack(OUTPUT_SLOT).getMaxCount() >= this.getStack(OUTPUT_SLOT).getCount() + count;
    }

    private Optional<DragonEggIncubatorColdRecipe> getCurrentRecipe() {
        SimpleInventory inventory = new SimpleInventory((this.size()));
        for(int i = 0; i < this.size(); i++) {
            inventory.setStack(i, this.getStack(i));
        }

        return this.getWorld().getRecipeManager().getFirstMatch(DragonEggIncubatorColdRecipe.Type.INSTANCE, inventory, this.getWorld());
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}