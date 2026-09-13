package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Immutable bounded-input profile of material factors used by deterministic stat resolution. */
public record MechanicalProfile(Map<PhysicalStat, Double> factors) {
    public MechanicalProfile {
        Objects.requireNonNull(factors, "factors");
        EnumMap<PhysicalStat, Double> copy = new EnumMap<>(PhysicalStat.class);
        for (Map.Entry<PhysicalStat, Double> entry : factors.entrySet()) {
            PhysicalStat stat = Objects.requireNonNull(entry.getKey(), "factor stat");
            Double value = Objects.requireNonNull(entry.getValue(), "factor value");
            if (!Double.isFinite(value) || value < 0.0D) {
                throw new IllegalArgumentException("mechanical factor must be finite and >= 0 for " + stat);
            }
            copy.put(stat, value);
        }
        factors = Collections.unmodifiableMap(copy);
    }

    public double value(PhysicalStat stat) {
        Objects.requireNonNull(stat, "stat");
        return factors.getOrDefault(stat, 1.0D);
    }
}
