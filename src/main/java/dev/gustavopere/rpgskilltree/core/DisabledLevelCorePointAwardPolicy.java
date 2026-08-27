package dev.gustavopere.rpgskilltree.core;

/** Compatibility policy that intentionally awards no Core Points from levels. */
public enum DisabledLevelCorePointAwardPolicy implements LevelCorePointAwardPolicy {
    INSTANCE;

    @Override
    public long pointsAwarded(long beforeLevel, long afterLevel) {
        if (beforeLevel < 0L || afterLevel < 0L) {
            throw new IllegalArgumentException("levels must be non-negative");
        }
        if (afterLevel < beforeLevel) {
            throw new IllegalArgumentException("afterLevel must not be below beforeLevel");
        }
        return 0L;
    }

    @Override
    public String canonicalForm() {
        return "disabled";
    }
}
