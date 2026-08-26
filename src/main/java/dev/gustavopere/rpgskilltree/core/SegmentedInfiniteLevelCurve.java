package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * Infinite level curve composed from a finite set of balance bands.
 *
 * <p>Each band defines a linearly increasing next-level cost. Cumulative XP is
 * calculated with arithmetic-series sums, so runtime cost depends on the number
 * of configured bands rather than on the player's level.</p>
 */
public final class SegmentedInfiniteLevelCurve implements InfiniteLevelCurve {
    private static final BigInteger TWO = BigInteger.valueOf(2L);
    private final List<LevelCurveBand> bands;

    public SegmentedInfiniteLevelCurve(List<LevelCurveBand> bands) {
        Objects.requireNonNull(bands);
        if (bands.isEmpty()) throw new IllegalArgumentException("at least one level curve band is required");
        this.bands = List.copyOf(bands);
        if (this.bands.getFirst().startLevel() != 0L) {
            throw new IllegalArgumentException("first level curve band must start at level 0");
        }
        for (int i = 1; i < this.bands.size(); i++) {
            LevelCurveBand previous = this.bands.get(i - 1);
            LevelCurveBand current = this.bands.get(i);
            if (current.startLevel() <= previous.startLevel()) {
                throw new IllegalArgumentException("level curve band starts must be strictly increasing");
            }
            BigInteger projectedPreviousCost = costAt(previous, current.startLevel());
            if (BigInteger.valueOf(current.baseXp()).compareTo(projectedPreviousCost) < 0) {
                throw new IllegalArgumentException("level curve cost must not decrease at a band boundary");
            }
        }
    }

    public List<LevelCurveBand> bands() {
        return bands;
    }

    @Override
    public BigInteger cumulativeXpToReachLevel(long level) {
        if (level < 0L) throw new IllegalArgumentException("level must be non-negative");
        if (level == 0L) return BigInteger.ZERO;

        BigInteger total = BigInteger.ZERO;
        for (int i = 0; i < bands.size(); i++) {
            LevelCurveBand band = bands.get(i);
            if (level <= band.startLevel()) break;
            long endExclusive = level;
            if (i + 1 < bands.size()) {
                endExclusive = Math.min(level, bands.get(i + 1).startLevel());
            }
            long count = endExclusive - band.startLevel();
            if (count > 0L) total = total.add(sumBandCosts(band, count));
            if (endExclusive == level) break;
        }
        return total;
    }

    @Override
    public BigInteger xpToNextLevel(long level) {
        if (level < 0L) throw new IllegalArgumentException("level must be non-negative");
        if (level == Long.MAX_VALUE) {
            throw new ArithmeticException("cannot represent a level above Long.MAX_VALUE");
        }
        return costAt(activeBand(level), level);
    }

    private LevelCurveBand activeBand(long level) {
        int low = 0;
        int high = bands.size() - 1;
        while (low < high) {
            int mid = (low + high + 1) >>> 1;
            if (bands.get(mid).startLevel() <= level) low = mid;
            else high = mid - 1;
        }
        return bands.get(low);
    }

    private static BigInteger costAt(LevelCurveBand band, long level) {
        long offset = level - band.startLevel();
        if (offset < 0L) throw new IllegalArgumentException("level precedes curve band");
        return BigInteger.valueOf(band.baseXp())
            .add(BigInteger.valueOf(band.growthPerLevel()).multiply(BigInteger.valueOf(offset)));
    }

    private static BigInteger sumBandCosts(LevelCurveBand band, long count) {
        BigInteger n = BigInteger.valueOf(count);
        BigInteger base = BigInteger.valueOf(band.baseXp());
        BigInteger growth = BigInteger.valueOf(band.growthPerLevel());
        BigInteger arithmeticOffsetSum = n.multiply(n.subtract(BigInteger.ONE)).divide(TWO);
        return n.multiply(base).add(growth.multiply(arithmeticOffsetSum));
    }
}
