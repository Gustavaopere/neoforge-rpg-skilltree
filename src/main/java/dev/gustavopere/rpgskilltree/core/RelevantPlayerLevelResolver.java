package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;

/** Pure multiplayer-safe derivation of RelevantPlayerLevel from explicitly supplied local candidates. */
public final class RelevantPlayerLevelResolver {
    private RelevantPlayerLevelResolver() {}

    public static RelevantPlayerLevelResolution resolve(
        List<RelevantPlayerCandidate> candidates,
        RelevantPlayerFilter filter,
        RelevantPlayerLevelPolicy policy
    ) {
        Objects.requireNonNull(candidates, "candidates");
        Objects.requireNonNull(filter, "filter");
        Objects.requireNonNull(policy, "policy");

        HashSet<String> seenIds = new HashSet<>();
        ArrayList<RelevantPlayerCandidate> relevant = new ArrayList<>();
        for (RelevantPlayerCandidate candidate : candidates) {
            RelevantPlayerCandidate checked = Objects.requireNonNull(candidate, "candidate");
            if (!seenIds.add(checked.playerId())) {
                throw new IllegalArgumentException("duplicate player candidate id: " + checked.playerId());
            }
            if (filter.isRelevant(checked)) {
                relevant.add(checked);
            }
        }
        relevant.sort((left, right) -> left.playerId().compareTo(right.playerId()));
        List<RelevantPlayerCandidate> canonicalRelevant = List.copyOf(relevant);
        if (canonicalRelevant.isEmpty()) {
            return new RelevantPlayerLevelResolution(canonicalRelevant, OptionalLong.empty());
        }

        OptionalLong selected = Objects.requireNonNull(policy.select(canonicalRelevant), "policy result");
        if (selected.isPresent() && selected.getAsLong() < 0L) {
            throw new IllegalArgumentException("relevant player policy returned a negative level");
        }
        return new RelevantPlayerLevelResolution(canonicalRelevant, selected);
    }
}
