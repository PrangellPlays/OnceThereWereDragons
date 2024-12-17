package dev.prangellplays.otwd.init;

import dev.prangellplays.otwd.OTWD;
import dev.prangellplays.otwd.entity.dragon.otwd.PhantomStalkerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface OTWDEntities {
    Map<EntityType<? extends Entity>, Identifier> ENTITIES = new LinkedHashMap();
    EntityType<PhantomStalkerEntity> PHANTOM_STALKER = Registry.register(Registries.ENTITY_TYPE, OTWD.id("phantom_stalker"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, PhantomStalkerEntity::new).dimensions(EntityDimensions.fixed(1.8f, 1.8f)).build());

    private static <T extends EntityType<? extends Entity>> T createEntity(String name, T entity) {
        ENTITIES.put(entity, new Identifier("otwd", name));
        return entity;
    }

    static void init() {
        ENTITIES.keySet().forEach((entityType) -> {
            Registry.register(Registries.ENTITY_TYPE, (Identifier) ENTITIES.get(entityType), entityType);
        });
    }
}
