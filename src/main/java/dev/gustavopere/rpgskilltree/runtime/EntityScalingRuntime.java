package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingAttachmentData;
import dev.gustavopere.rpgskilltree.core.EntityScalingSnapshot;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;

/** NeoForge persistence boundary for one entity's already-resolved scaling decision. */
public final class EntityScalingRuntime {
    private EntityScalingRuntime() {}

    /**
     * Persists a candidate only for an entity that has no initialized scaling state.
     *
     * <p>Callers are responsible for producing the candidate from the pure world/entity
     * scaling services. This boundary never recalculates or rerolls level/rarity.</p>
     */
    public static EntityScalingSnapshot initializeIfAbsent(
        LivingEntity entity,
        EntityScalingSnapshot candidate
    ) {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(candidate, "candidate");

        if (entity.hasData(ModAttachments.ENTITY_SCALING)) {
            EntityScalingAttachmentData existing = entity.getData(ModAttachments.ENTITY_SCALING);
            if (existing.initialized()) {
                return existing.requireSnapshot();
            }
        }

        entity.setData(
            ModAttachments.ENTITY_SCALING,
            EntityScalingAttachmentData.initialized(candidate)
        );
        return candidate;
    }

    /** Read-only view; does not materialize the default attachment. */
    public static Optional<EntityScalingSnapshot> current(LivingEntity entity) {
        Objects.requireNonNull(entity, "entity");
        if (!entity.hasData(ModAttachments.ENTITY_SCALING)) {
            return Optional.empty();
        }
        return entity.getData(ModAttachments.ENTITY_SCALING).snapshot();
    }
}
