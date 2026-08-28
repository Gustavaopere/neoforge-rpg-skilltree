package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Normalized mastery XP contribution. Provenance and replay identity are intentionally separate. */
public record MasteryAward(
    String laneId,
    int experience,
    String sourceId,
    String replayKey
) {
    public MasteryAward {
        Objects.requireNonNull(laneId, "laneId");
        Objects.requireNonNull(sourceId, "sourceId");
        if (laneId.isBlank() || sourceId.isBlank() || experience <= 0) {
            throw new IllegalArgumentException("mastery award must use non-blank ids and positive experience");
        }
        if (replayKey != null && replayKey.isBlank()) {
            throw new IllegalArgumentException("mastery replayKey must be null or non-blank");
        }
    }

    /** Compatibility constructor: ordinary repeatable mastery awards are not deduplicated. */
    public MasteryAward(String laneId, int experience, String sourceId) {
        this(laneId, experience, sourceId, null);
    }

    /** Creates an award whose duplicate auxiliary emissions share one stable replay key. */
    public static MasteryAward replaySafe(
        String laneId,
        int experience,
        String sourceId,
        String replayKey
    ) {
        return new MasteryAward(laneId, experience, sourceId, replayKey);
    }

    public boolean replaySafe() {
        return replayKey != null;
    }
}
