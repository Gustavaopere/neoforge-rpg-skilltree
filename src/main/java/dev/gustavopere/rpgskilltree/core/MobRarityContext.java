package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Deterministic inputs for data-driven mob rarity selection. */
public record MobRarityContext(
    EntityLevelContext levelContext,
    long deterministicSeed
) {
    public MobRarityContext {
        Objects.requireNonNull(levelContext, "levelContext");
    }

    public EntityArchetype archetype() {
        return levelContext.archetype();
    }

    public long baseFloor() {
        return levelContext.baseFloor();
    }
}
