package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;

/**
 * Uncapped-by-design character level curve.
 *
 * <p>The cumulative function is the source of truth: level {@code 0} must map to
 * zero XP and every subsequent level must have a strictly larger cumulative
 * threshold. Implementations are expected to calculate thresholds directly;
 * they must not materialize a finite level table.</p>
 */
@FunctionalInterface
public interface InfiniteLevelCurve {
    /** Total RPG XP required to have reached {@code level}. */
    BigInteger cumulativeXpToReachLevel(long level);

    /**
     * XP required to advance from {@code level} to {@code level + 1}.
     *
     * <p>This convenience view is intentionally {@link BigInteger}-based so the
     * curve can detect machine-representation limits without silently wrapping.</p>
     */
    default BigInteger xpToNextLevel(long level) {
        if (level < 0) throw new IllegalArgumentException("level must be non-negative");
        if (level == Long.MAX_VALUE) {
            throw new ArithmeticException("cannot represent a level above Long.MAX_VALUE");
        }
        BigInteger current = cumulativeXpToReachLevel(level);
        BigInteger next = cumulativeXpToReachLevel(level + 1L);
        if (current == null || next == null) throw new IllegalArgumentException("curve thresholds must not be null");
        if (current.signum() < 0) throw new IllegalArgumentException("curve thresholds must be non-negative");
        BigInteger cost = next.subtract(current);
        if (cost.signum() <= 0) throw new IllegalArgumentException("curve must be strictly increasing");
        return cost;
    }
}
