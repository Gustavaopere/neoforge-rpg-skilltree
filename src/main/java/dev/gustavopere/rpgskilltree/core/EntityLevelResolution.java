package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.OptionalLong;

/** Auditable output of one entity-level calculation before stat scaling. */
public record EntityLevelResolution(
    EntityArchetype archetype,
    long nativeAreaLevel,
    OptionalLong relevantPlayerLevel,
    long baseFloor,
    long rolledLevel,
    long finalLevel
) {
    public EntityLevelResolution {
        Objects.requireNonNull(archetype, "archetype");
        Objects.requireNonNull(relevantPlayerLevel, "relevantPlayerLevel");
        if (nativeAreaLevel < 0L || baseFloor < 0L || finalLevel < 0L) {
            throw new IllegalArgumentException("resolved entity levels must be non-negative except the intermediate roll");
        }
        if (relevantPlayerLevel.isPresent() && relevantPlayerLevel.getAsLong() < 0L) {
            throw new IllegalArgumentException("relevantPlayerLevel must be non-negative");
        }
    }
}
