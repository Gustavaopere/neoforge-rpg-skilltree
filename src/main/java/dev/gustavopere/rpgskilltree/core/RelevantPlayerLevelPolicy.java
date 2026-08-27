package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.OptionalLong;

/** Aggregates an already-filtered local participant set into one relevant-player level. */
@FunctionalInterface
public interface RelevantPlayerLevelPolicy {
    OptionalLong select(List<RelevantPlayerCandidate> relevantCandidates);
}
