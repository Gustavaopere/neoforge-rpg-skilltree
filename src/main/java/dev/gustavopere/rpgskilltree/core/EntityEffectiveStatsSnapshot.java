package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Immutable persisted snapshot of the exact effective stats resolved for one entity. */
public final class EntityEffectiveStatsSnapshot {
    private final Map<CanonicalStatKey, BigDecimal> values;

    private EntityEffectiveStatsSnapshot(Map<CanonicalStatKey, BigDecimal> values) {
        this.values = Map.copyOf(values);
    }

    public static EntityEffectiveStatsSnapshot of(Map<CanonicalStatKey, BigDecimal> values) {
        Objects.requireNonNull(values, "values");
        if (values.isEmpty()) {
            throw new IllegalArgumentException("entity effective stats snapshot must not be empty");
        }
        HashMap<CanonicalStatKey, BigDecimal> copy = new HashMap<>();
        for (Map.Entry<CanonicalStatKey, BigDecimal> entry : values.entrySet()) {
            CanonicalStatKey key = Objects.requireNonNull(entry.getKey(), "canonical stat key");
            BigDecimal value = Objects.requireNonNull(entry.getValue(), "effective stat value");
            copy.put(key, value);
        }
        return new EntityEffectiveStatsSnapshot(copy);
    }

    public static EntityEffectiveStatsSnapshot from(RpgEffectiveStats effectiveStats) {
        Objects.requireNonNull(effectiveStats, "effectiveStats");
        return of(effectiveStats.values());
    }

    public Map<CanonicalStatKey, BigDecimal> values() {
        return values;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof EntityEffectiveStatsSnapshot snapshot
            && values.equals(snapshot.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "EntityEffectiveStatsSnapshot" + values;
    }
}
