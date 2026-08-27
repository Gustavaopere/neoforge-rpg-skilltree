package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.Objects;

/** Pure progression arithmetic; persistence and reward policy live outside this service. */
public final class CharacterProgressionService {
    private CharacterProgressionService() {}

    /**
     * Applies non-negative RPG XP without iterating once per gained level.
     *
     * <p>The target level is resolved by binary search over cumulative curve
     * thresholds, so a legitimate large grant does not create an unbounded
     * per-level loop. BigInteger is used only for intermediate checked
     * arithmetic; persisted state remains a Level-0 {@code long} plus partial
     * XP.</p>
     */
    public static CharacterXpGrantResult grantXp(
        CharacterProgressionState state,
        long amount,
        InfiniteLevelCurve curve
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(curve);
        if (amount < 0) throw new IllegalArgumentException("XP grant must be non-negative");

        BigInteger zeroThreshold = threshold(curve, 0L);
        if (zeroThreshold.signum() != 0) {
            throw new IllegalArgumentException("Level 0 cumulative XP must be zero");
        }

        BigInteger currentThreshold = threshold(curve, state.level());
        validateStatePosition(state, curve, currentThreshold);

        if (amount == 0L) {
            return new CharacterXpGrantResult(state, state, 0L, 0L);
        }
        if (state.level() == Long.MAX_VALUE) {
            throw new ArithmeticException("cannot progress beyond Long.MAX_VALUE level");
        }

        BigInteger totalXp = currentThreshold
            .add(BigInteger.valueOf(state.xpIntoLevel()))
            .add(BigInteger.valueOf(amount));
        BigInteger technicalCeiling = threshold(curve, Long.MAX_VALUE);
        if (totalXp.compareTo(technicalCeiling) > 0) {
            throw new ArithmeticException("XP grant exceeds representable character level range");
        }

        long targetLevel = resolveLevel(curve, totalXp, state.level());
        BigInteger targetThreshold = threshold(curve, targetLevel);
        BigInteger remainder = totalXp.subtract(targetThreshold);
        if (remainder.signum() < 0) {
            throw new IllegalStateException("curve resolution produced a negative XP remainder");
        }

        if (targetLevel < Long.MAX_VALUE) {
            BigInteger targetCost = levelCost(curve, targetLevel, targetThreshold);
            if (remainder.compareTo(targetCost) >= 0) {
                throw new IllegalStateException("curve resolution did not select the highest reached level");
            }
        } else if (remainder.signum() != 0) {
            throw new ArithmeticException("cannot persist XP beyond Long.MAX_VALUE level");
        }

        final long partialXp;
        try {
            partialXp = remainder.longValueExact();
        } catch (ArithmeticException overflow) {
            throw new ArithmeticException("partial XP exceeds the persisted long representation");
        }

        CharacterProgressionState next = new CharacterProgressionState(targetLevel, partialXp);
        long gained = targetLevel - state.level();
        return new CharacterXpGrantResult(state, next, amount, gained);
    }

    private static void validateStatePosition(
        CharacterProgressionState state,
        InfiniteLevelCurve curve,
        BigInteger currentThreshold
    ) {
        if (state.level() == Long.MAX_VALUE) {
            if (state.xpIntoLevel() != 0L) {
                throw new IllegalArgumentException("technical maximum level cannot contain partial XP");
            }
            return;
        }
        BigInteger cost = levelCost(curve, state.level(), currentThreshold);
        if (BigInteger.valueOf(state.xpIntoLevel()).compareTo(cost) >= 0) {
            throw new IllegalArgumentException("xpIntoLevel must be below the next-level cost");
        }
    }

    private static long resolveLevel(InfiniteLevelCurve curve, BigInteger totalXp, long minimumLevel) {
        long low = minimumLevel;
        long high = Long.MAX_VALUE;
        while (low < high) {
            long delta = high - low;
            long mid = low + (delta >>> 1) + (delta & 1L);
            if (threshold(curve, mid).compareTo(totalXp) <= 0) {
                low = mid;
            } else {
                high = mid - 1L;
            }
        }
        return low;
    }

    private static BigInteger levelCost(InfiniteLevelCurve curve, long level, BigInteger currentThreshold) {
        if (level == Long.MAX_VALUE) {
            throw new ArithmeticException("cannot calculate a cost above Long.MAX_VALUE level");
        }
        BigInteger nextThreshold = threshold(curve, level + 1L);
        BigInteger cost = nextThreshold.subtract(currentThreshold);
        if (cost.signum() <= 0) {
            throw new IllegalArgumentException("curve must be strictly increasing");
        }
        return cost;
    }

    private static BigInteger threshold(InfiniteLevelCurve curve, long level) {
        if (level < 0) throw new IllegalArgumentException("level must be non-negative");
        BigInteger threshold = curve.cumulativeXpToReachLevel(level);
        if (threshold == null) throw new IllegalArgumentException("curve threshold must not be null");
        if (threshold.signum() < 0) throw new IllegalArgumentException("curve threshold must be non-negative");
        return threshold;
    }
}
