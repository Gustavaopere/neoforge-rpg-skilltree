package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.OptionalLong;

/** Inputs already classified by the server adapter for one entity-level resolution. */
public record EntityLevelContext(
    long nativeAreaLevel,
    OptionalLong relevantPlayerLevel,
    EntityArchetype archetype
) {
    public EntityLevelContext {
        Objects.requireNonNull(relevantPlayerLevel, "relevantPlayerLevel");
        Objects.requireNonNull(archetype, "archetype");
        if (nativeAreaLevel < 0L) {
            throw new IllegalArgumentException("nativeAreaLevel must be non-negative");
        }
        if (relevantPlayerLevel.isPresent() && relevantPlayerLevel.getAsLong() < 0L) {
            throw new IllegalArgumentException("relevantPlayerLevel must be non-negative");
        }
    }

    public static EntityLevelContext withRelevantPlayer(
        long nativeAreaLevel,
        long relevantPlayerLevel,
        EntityArchetype archetype
    ) {
        return new EntityLevelContext(nativeAreaLevel, OptionalLong.of(relevantPlayerLevel), archetype);
    }

    public static EntityLevelContext nativeOnly(long nativeAreaLevel, EntityArchetype archetype) {
        return new EntityLevelContext(nativeAreaLevel, OptionalLong.empty(), archetype);
    }

    public long baseFloor() {
        return relevantPlayerLevel.isPresent()
            ? Math.max(nativeAreaLevel, relevantPlayerLevel.getAsLong())
            : nativeAreaLevel;
    }
}
