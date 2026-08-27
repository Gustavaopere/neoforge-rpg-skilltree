package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Canonical attribute economy: one Core Progression Point buys one attribute rank.
 *
 * <p>The policy is intentionally independent from the current rank so the uncapped
 * attribute tree never acquires an implicit escalating price curve.</p>
 */
public enum UnitAttributeRankCostPolicy implements AttributeRankCostPolicy {
    INSTANCE;

    @Override
    public long cost(AttributeId attribute, long startRank, long rankCount) {
        Objects.requireNonNull(attribute, "attribute");
        if (startRank < 0L) {
            throw new IllegalArgumentException("startRank must be non-negative");
        }
        if (rankCount <= 0L) {
            throw new IllegalArgumentException("rankCount must be positive");
        }
        return rankCount;
    }
}
