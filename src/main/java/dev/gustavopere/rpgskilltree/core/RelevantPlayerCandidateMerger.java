package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Provider-neutral merge of bounded spatial evidence with optional party evidence. */
public final class RelevantPlayerCandidateMerger {
    private RelevantPlayerCandidateMerger() {}

    public static List<RelevantPlayerCandidate> merge(
        List<RelevantPlayerCandidate> spatialCandidates,
        List<RelevantPlayerCandidate> partyCandidates,
        int maxCandidates
    ) {
        Objects.requireNonNull(spatialCandidates, "spatialCandidates");
        Objects.requireNonNull(partyCandidates, "partyCandidates");
        if (maxCandidates <= 0) throw new IllegalArgumentException("maxCandidates must be positive");

        Map<String, Entry> merged = new HashMap<>();
        for (RelevantPlayerCandidate raw : spatialCandidates) {
            RelevantPlayerCandidate candidate = Objects.requireNonNull(raw, "spatial candidate");
            mergeOne(merged, candidate, 0, false);
        }
        for (RelevantPlayerCandidate raw : partyCandidates) {
            RelevantPlayerCandidate candidate = Objects.requireNonNull(raw, "party candidate");
            if (!candidate.partyMember()) {
                throw new IllegalArgumentException("party adapter candidate must set partyMember=true");
            }
            mergeOne(merged, candidate, 1, true);
        }

        ArrayList<Entry> ordered = new ArrayList<>(merged.values());
        ordered.sort(
            Comparator.comparingInt(Entry::sourcePriority)
                .thenComparingLong(entry -> entry.candidate().distanceSquared())
                .thenComparing(entry -> entry.candidate().playerId())
        );
        if (ordered.size() > maxCandidates) {
            ordered.subList(maxCandidates, ordered.size()).clear();
        }
        return ordered.stream().map(Entry::candidate).toList();
    }

    private static void mergeOne(
        Map<String, Entry> merged,
        RelevantPlayerCandidate incoming,
        int sourcePriority,
        boolean partyEvidence
    ) {
        Entry existingEntry = merged.get(incoming.playerId());
        if (existingEntry == null) {
            merged.put(incoming.playerId(), new Entry(incoming, sourcePriority));
            return;
        }

        RelevantPlayerCandidate existing = existingEntry.candidate();
        if (existing.level() != incoming.level()) {
            throw new IllegalArgumentException("conflicting relevant-player level for " + incoming.playerId());
        }
        RelevantPlayerCandidate combined = new RelevantPlayerCandidate(
            incoming.playerId(),
            incoming.level(),
            Math.min(existing.distanceSquared(), incoming.distanceSquared()),
            existing.engaged() || incoming.engaged(),
            existing.partyMember() || incoming.partyMember() || partyEvidence
        );
        merged.put(incoming.playerId(), new Entry(combined, Math.min(existingEntry.sourcePriority(), sourcePriority)));
    }

    private record Entry(RelevantPlayerCandidate candidate, int sourcePriority) {}
}
