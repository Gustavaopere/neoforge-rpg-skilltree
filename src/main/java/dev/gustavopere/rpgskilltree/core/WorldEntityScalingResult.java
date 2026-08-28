package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Auditable result of the canonical world/entity scaling pipeline. */
public record WorldEntityScalingResult(
    NativeAreaLevelBreakdown nativeArea,
    RelevantPlayerLevelResolution relevantPlayers,
    EntityLevelResolution entityLevel,
    EntityStatScalingResult stats
) {
    public WorldEntityScalingResult {
        Objects.requireNonNull(nativeArea, "nativeArea");
        Objects.requireNonNull(relevantPlayers, "relevantPlayers");
        Objects.requireNonNull(entityLevel, "entityLevel");
        Objects.requireNonNull(stats, "stats");
        if (entityLevel.nativeAreaLevel() != nativeArea.resolvedLevel()) {
            throw new IllegalArgumentException("entity native area level must match native area resolution");
        }
        if (!entityLevel.relevantPlayerLevel().equals(relevantPlayers.relevantPlayerLevel())) {
            throw new IllegalArgumentException("entity relevant player level must match relevant-player resolution");
        }
        if (!stats.levelResolution().equals(entityLevel)) {
            throw new IllegalArgumentException("stat scaling must use the resolved entity level");
        }
    }
}
