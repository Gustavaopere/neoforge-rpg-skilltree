package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Provider-neutral inputs for archetype-specific entity stat scaling. */
public record EntityStatScalingContext(
    EntityLevelResolution levelResolution,
    CanonicalStatSnapshot providerStats
) {
    public EntityStatScalingContext {
        Objects.requireNonNull(levelResolution, "levelResolution");
        Objects.requireNonNull(providerStats, "providerStats");
    }

    public EntityArchetype archetype() {
        return levelResolution.archetype();
    }

    public long entityLevel() {
        return levelResolution.finalLevel();
    }
}
