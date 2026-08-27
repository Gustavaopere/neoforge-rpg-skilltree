package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Objects;

/** Inputs available to one stat-specific effective-value policy. */
public record EffectiveStatContext(
    CanonicalStatKey statKey,
    BigDecimal providerValue,
    long progressionLevel
) {
    public EffectiveStatContext {
        Objects.requireNonNull(statKey, "statKey");
        Objects.requireNonNull(providerValue, "providerValue");
        if (progressionLevel < 0L) {
            throw new IllegalArgumentException("progressionLevel must be non-negative");
        }
    }
}
