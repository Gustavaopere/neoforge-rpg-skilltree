package dev.gustavopere.rpgskilltree.core;

/** Selects whether one local player candidate participates in entity-level floor calculation. */
@FunctionalInterface
public interface RelevantPlayerFilter {
    boolean isRelevant(RelevantPlayerCandidate candidate);
}
