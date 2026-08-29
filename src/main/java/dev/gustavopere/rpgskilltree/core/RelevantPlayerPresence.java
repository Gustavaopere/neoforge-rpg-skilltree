package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Immutable cached player presence used by the provider-neutral spatial index. */
public record RelevantPlayerPresence(
    String playerId,
    long level,
    int blockX,
    int blockY,
    int blockZ
) {
    public RelevantPlayerPresence {
        Objects.requireNonNull(playerId, "playerId");
        if (playerId.isBlank() || !playerId.equals(playerId.trim())) {
            throw new IllegalArgumentException("playerId must be non-blank and trimmed");
        }
        if (level < 0L) {
            throw new IllegalArgumentException("level must be non-negative");
        }
    }
}
