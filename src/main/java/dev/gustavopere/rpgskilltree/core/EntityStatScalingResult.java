package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Auditable entity-level + canonical effective-stat result for one classified entity. */
public record EntityStatScalingResult(
    EntityLevelResolution levelResolution,
    RpgEffectiveStats effectiveStats
) {
    public EntityStatScalingResult {
        Objects.requireNonNull(levelResolution, "levelResolution");
        Objects.requireNonNull(effectiveStats, "effectiveStats");
        if (effectiveStats.progressionLevel() != levelResolution.finalLevel()) {
            throw new IllegalArgumentException("effective stats level must match resolved entity level");
        }
    }

    public EntityArchetype archetype() {
        return levelResolution.archetype();
    }

    public long entityLevel() {
        return levelResolution.finalLevel();
    }
}
