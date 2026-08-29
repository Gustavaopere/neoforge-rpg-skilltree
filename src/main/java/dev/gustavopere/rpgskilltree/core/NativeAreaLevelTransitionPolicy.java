package dev.gustavopere.rpgskilltree.core;

/**
 * Pure bounded transition policy for Native Area sources that intentionally keep state.
 *
 * <p>Deterministically recomputable sources do not need this policy. A stateful provider
 * can persist its own current level and use this contract to apply configured rise/decay
 * limits without embedding balance constants in the core.</p>
 */
public record NativeAreaLevelTransitionPolicy(
    long minLevel,
    long maxLevel,
    long maxRisePerStep,
    long maxDecayPerStep
) {
    public NativeAreaLevelTransitionPolicy {
        if (minLevel < 0L) throw new IllegalArgumentException("minLevel must be non-negative");
        if (maxLevel < minLevel) throw new IllegalArgumentException("maxLevel must be >= minLevel");
        if (maxRisePerStep < 0L) throw new IllegalArgumentException("maxRisePerStep must be non-negative");
        if (maxDecayPerStep < 0L) throw new IllegalArgumentException("maxDecayPerStep must be non-negative");
    }

    /** Initializes a newly materialized state directly at the bounded target. */
    public long initialize(long targetLevel) {
        return boundedTarget(targetLevel);
    }

    /** Moves one already-persisted state toward the bounded target by at most the configured step. */
    public long transition(long currentLevel, long targetLevel) {
        if (currentLevel < minLevel || currentLevel > maxLevel) {
            throw new IllegalArgumentException("currentLevel is outside transition policy bounds");
        }
        long target = boundedTarget(targetLevel);
        if (target > currentLevel) {
            long delta = target - currentLevel;
            return currentLevel + Math.min(delta, maxRisePerStep);
        }
        if (target < currentLevel) {
            long delta = currentLevel - target;
            return currentLevel - Math.min(delta, maxDecayPerStep);
        }
        return currentLevel;
    }

    public long boundedTarget(long targetLevel) {
        if (targetLevel < 0L) throw new IllegalArgumentException("targetLevel must be non-negative");
        return Math.max(minLevel, Math.min(maxLevel, targetLevel));
    }
}
