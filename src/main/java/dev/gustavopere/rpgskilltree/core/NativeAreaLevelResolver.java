package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Validates and exposes Native Area Level without coupling the core to a concrete world-threat formula. */
public final class NativeAreaLevelResolver {
    private NativeAreaLevelResolver() {}

    public static long resolve(TerritoryKey territoryKey, NativeAreaLevelPolicy policy) {
        Objects.requireNonNull(territoryKey, "territoryKey");
        Objects.requireNonNull(policy, "policy");
        long level = policy.levelFor(territoryKey);
        if (level < 0L) {
            throw new IllegalArgumentException("native area level must be non-negative");
        }
        return level;
    }
}
