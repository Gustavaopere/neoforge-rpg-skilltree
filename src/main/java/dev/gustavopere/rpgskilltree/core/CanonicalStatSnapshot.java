package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Immutable provider-normalized values addressed only by canonical stat keys. */
public final class CanonicalStatSnapshot {
    private final Map<CanonicalStatKey, BigDecimal> values;

    private CanonicalStatSnapshot(Map<CanonicalStatKey, BigDecimal> values) {
        this.values = Map.copyOf(values);
    }

    public static CanonicalStatSnapshot of(Map<CanonicalStatKey, BigDecimal> values) {
        Objects.requireNonNull(values, "values");
        if (values.isEmpty()) {
            throw new IllegalArgumentException("canonical stat snapshot must not be empty");
        }
        HashMap<CanonicalStatKey, BigDecimal> copy = new HashMap<>();
        for (Map.Entry<CanonicalStatKey, BigDecimal> entry : values.entrySet()) {
            CanonicalStatKey key = Objects.requireNonNull(entry.getKey(), "canonical stat key");
            BigDecimal value = Objects.requireNonNull(entry.getValue(), "canonical stat value");
            copy.put(key, value);
        }
        return new CanonicalStatSnapshot(copy);
    }

    public BigDecimal value(CanonicalStatKey key) {
        Objects.requireNonNull(key, "key");
        BigDecimal value = values.get(key);
        if (value == null) {
            throw new IllegalArgumentException("canonical stat is unavailable: " + key.serializedId());
        }
        return value;
    }

    public Map<CanonicalStatKey, BigDecimal> values() {
        return values;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof CanonicalStatSnapshot snapshot
            && values.equals(snapshot.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "CanonicalStatSnapshot" + values;
    }
}
