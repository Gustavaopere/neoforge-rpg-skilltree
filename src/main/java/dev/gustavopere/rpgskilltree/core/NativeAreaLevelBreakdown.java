package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;

/** Auditable Native Area Level output including all ordered contribution provenance. */
public record NativeAreaLevelBreakdown(
    TerritoryKey territoryKey,
    long baseLevel,
    List<NativeAreaLevelContribution> contributions,
    long rawLevelBeforeClamp,
    long levelBeforeOverride,
    OptionalLong overrideLevel,
    long resolvedLevel
) {
    public NativeAreaLevelBreakdown {
        Objects.requireNonNull(territoryKey, "territoryKey");
        Objects.requireNonNull(contributions, "contributions");
        Objects.requireNonNull(overrideLevel, "overrideLevel");
        contributions = List.copyOf(contributions);
        if (baseLevel < 0L || levelBeforeOverride < 0L || resolvedLevel < 0L) {
            throw new IllegalArgumentException("native area levels must be non-negative after clamping");
        }
        if (levelBeforeOverride != Math.max(0L, rawLevelBeforeClamp)) {
            throw new IllegalArgumentException("levelBeforeOverride must equal the clamped raw level");
        }
        if (overrideLevel.isPresent() && overrideLevel.getAsLong() < 0L) {
            throw new IllegalArgumentException("overrideLevel must be non-negative");
        }
        long expectedResolved = overrideLevel.isPresent() ? overrideLevel.getAsLong() : levelBeforeOverride;
        if (resolvedLevel != expectedResolved) {
            throw new IllegalArgumentException("resolvedLevel does not match override/clamped level");
        }
    }
}
