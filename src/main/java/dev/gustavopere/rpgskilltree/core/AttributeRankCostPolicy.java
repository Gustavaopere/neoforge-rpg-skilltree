package dev.gustavopere.rpgskilltree.core;

/**
 * Supplies the Core Progression Point cost of a contiguous attribute-rank range.
 *
 * <p>The Core foundation deliberately does not define a production price curve.
 * Balance/data layers provide this policy so rank storage and mutation never imply
 * that one rank must cost one point.</p>
 */
@FunctionalInterface
public interface AttributeRankCostPolicy {
    long cost(AttributeId attribute, long startRank, long rankCount);
}
