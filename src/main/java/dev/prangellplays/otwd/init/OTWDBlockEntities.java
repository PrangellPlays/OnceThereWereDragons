package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorColdBlockEntity;
import dev.prangellplays.otwd.block.incubator.DragonEggIncubatorHotBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OTWDBlockEntities {
    public static final BlockEntityType<DragonEggIncubatorColdBlockEntity> DRAGON_EGG_INCUBATOR_COLD = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(OTWD.MOD_ID, "dragon_egg_incubator_cold"), FabricBlockEntityTypeBuilder.create(DragonEggIncubatorColdBlockEntity::new, OTWDBlocks.DRAGON_EGG_INCUBATOR_COLD).build(null));
    public static final BlockEntityType<DragonEggIncubatorHotBlockEntity> DRAGON_EGG_INCUBATOR_HOT = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(OTWD.MOD_ID, "dragon_egg_incubator_hot"), FabricBlockEntityTypeBuilder.create(DragonEggIncubatorHotBlockEntity::new, OTWDBlocks.DRAGON_EGG_INCUBATOR_HOT).build(null));

    public static void init() {
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, DRAGON_EGG_INCUBATOR_COLD);
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, DRAGON_EGG_INCUBATOR_HOT);
    }
}
