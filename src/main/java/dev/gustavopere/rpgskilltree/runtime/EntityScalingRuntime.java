package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingAttachmentData;
import dev.gustavopere.rpgskilltree.core.EntityScalingBootstrap;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;

/** NeoForge persistence boundary for one entity's already-resolved scaling lifecycle. */
public final class EntityScalingRuntime {
    private EntityScalingRuntime() {}

    /**
     * Returns persisted state without invoking the initializer, or initializes and persists exactly once.
     *
     * <p>The initializer owns pure level/rarity resolution. This boundary never recalculates or rerolls
     * an entity that already has persisted scaling metadata.</p>
     */
    public static EntityScalingState getOrInitialize(
        LivingEntity entity,
        Supplier<EntityScalingState> initializer
    ) {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(initializer, "initializer");

        Optional<EntityScalingState> existing = Optional.empty();
        if (entity.hasData(ModAttachments.ENTITY_SCALING)) {
            existing = entity.getData(ModAttachments.ENTITY_SCALING).state();
        }

        EntityScalingState resolved = EntityScalingBootstrap.resumeOrInitialize(existing, initializer);
        if (existing.isPresent()) {
            return resolved;
        }

        entity.setData(
            ModAttachments.ENTITY_SCALING,
            EntityScalingAttachmentData.initialized(resolved)
        );
        return resolved;
    }

    /** Read-only view; does not materialize the default attachment. */
    public static Optional<EntityScalingState> current(LivingEntity entity) {
        Objects.requireNonNull(entity, "entity");
        if (!entity.hasData(ModAttachments.ENTITY_SCALING)) {
            return Optional.empty();
        }
        return entity.getData(ModAttachments.ENTITY_SCALING).state();
    }
}
