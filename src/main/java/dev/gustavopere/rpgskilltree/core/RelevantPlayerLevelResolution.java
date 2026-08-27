package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;

/** Auditable result of filtering local multiplayer participants and deriving their entity-level floor. */
public record RelevantPlayerLevelResolution(
    List<RelevantPlayerCandidate> relevantCandidates,
    OptionalLong relevantPlayerLevel
) {
    public RelevantPlayerLevelResolution {
        Objects.requireNonNull(relevantCandidates, "relevantCandidates");
        Objects.requireNonNull(relevantPlayerLevel, "relevantPlayerLevel");
        relevantCandidates = List.copyOf(relevantCandidates);

        HashSet<String> ids = new HashSet<>();
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (RelevantPlayerCandidate candidate : relevantCandidates) {
            RelevantPlayerCandidate checked = Objects.requireNonNull(candidate, "relevant candidate");
            if (!ids.add(checked.playerId())) {
                throw new IllegalArgumentException("duplicate relevant player id: " + checked.playerId());
            }
            min = Math.min(min, checked.level());
            max = Math.max(max, checked.level());
        }

        if (relevantCandidates.isEmpty()) {
            if (relevantPlayerLevel.isPresent()) {
                throw new IllegalStateException("cannot resolve a player floor without relevant candidates");
            }
        } else if (relevantPlayerLevel.isPresent()) {
            long resolved = relevantPlayerLevel.getAsLong();
            if (resolved < 0L) {
                throw new IllegalArgumentException("relevant player level must be non-negative");
            }
            if (resolved < min || resolved > max) {
                throw new IllegalStateException("relevant player level must remain inside the relevant participant range");
            }
        }
    }
}
