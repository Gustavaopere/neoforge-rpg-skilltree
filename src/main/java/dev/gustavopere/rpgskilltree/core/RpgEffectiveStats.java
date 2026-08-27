package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Immutable result of resolving provider-normalized canonical stats through RPG policies. */
public final class RpgEffectiveStats {
    private final long progressionLevel;
    private final Map<CanonicalStatKey, BigDecimal> values;

    RpgEffectiveStats(long progressionLevel, Map<CanonicalStatKey, BigDecimal> values) {
        if (progressionLevel < 0L) {
            throw new IllegalArgumentException("progressionLevel must be non-negative");
        }
        Objects.requireNonNull(values, "values");
        if (values.isEmpty()) {
            throw new IllegalArgumentException("effective stat values must not be empty");
        }
        HashMap<CanonicalStatKey, BigDecimal> copy = new HashMap<>();
        for (Map.Entry<CanonicalStatKey, BigDecimal> entry : values.entrySet()) {
            CanonicalStatKey key = Objects.requireNonNull(entry.getKey(), "canonical stat key");
            BigDecimal value = Objects.requireNonNull(entry.getValue(), "effective stat value");
            copy.put(key, value);
        }
        this.progressionLevel = progressionLevel;
        this.values = Map.copyOf(copy);
    }

    public long progressionLevel() {
        return progressionLevel;
    }

    public BigDecimal value(CanonicalStatKey key) {
        Objects.requireNonNull(key, "key");
        BigDecimal value = values.get(key);
        if (value == null) {
            throw new IllegalArgumentException("effective stat is unavailable: " + key.serializedId());
        }
        return value;
    }

    public Map<CanonicalStatKey, BigDecimal> values() {
        return values;
    }
}
