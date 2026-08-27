package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Provider-neutral local player evidence used to derive an entity's relevant-player floor. */
public record RelevantPlayerCandidate(
    String playerId,
    long level,
    long distanceSquared,
    boolean engaged,
    boolean partyMember
) {
    public RelevantPlayerCandidate {
        Objects.requireNonNull(playerId, "playerId");
        if (playerId.isBlank() || !playerId.equals(playerId.trim())) {
            throw new IllegalArgumentException("playerId must be non-blank and trimmed");
        }
        if (level < 0L) {
            throw new IllegalArgumentException("level must be non-negative");
        }
        if (distanceSquared < 0L) {
            throw new IllegalArgumentException("distanceSquared must be non-negative");
        }
    }
}
