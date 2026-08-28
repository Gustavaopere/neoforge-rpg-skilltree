package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Persisted entity-scaling decision; must remain stable across unload/reload. */
public record EntityScalingSnapshot(
    EntityLevelResolution levelResolution,
    MobRaritySelection raritySelection
) {
    public EntityScalingSnapshot {
        Objects.requireNonNull(levelResolution, "levelResolution");
        Objects.requireNonNull(raritySelection, "raritySelection");
    }

    public EntityArchetype archetype() {
        return levelResolution.archetype();
    }

    public MobRarityKey rarity() {
        return raritySelection.rarity();
    }

    public long entityLevel() {
        return levelResolution.finalLevel();
    }
}
