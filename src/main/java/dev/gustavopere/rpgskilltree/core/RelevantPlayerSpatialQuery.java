package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

/** Auditable result of one bounded spatial candidate lookup. */
public record RelevantPlayerSpatialQuery(
    List<RelevantPlayerCandidate> candidates,
    int indexedPlayers,
    int scannedPlayers,
    long visitedCells
) {
    public RelevantPlayerSpatialQuery {
        Objects.requireNonNull(candidates, "candidates");
        candidates = List.copyOf(candidates);
        if (indexedPlayers < 0 || scannedPlayers < 0 || scannedPlayers > indexedPlayers) {
            throw new IllegalArgumentException("invalid player scan counters");
        }
        if (visitedCells < 0L) {
            throw new IllegalArgumentException("visitedCells must be non-negative");
        }
    }
}
