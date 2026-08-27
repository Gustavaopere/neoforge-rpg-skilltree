package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

/** Stable dimension + territory-cell identity. Cell sizing is deliberately external policy. */
public record TerritoryKey(String dimensionId, long cellX, long cellZ) {
    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern PATH = Pattern.compile("[a-z0-9/._-]+");

    public TerritoryKey {
        Objects.requireNonNull(dimensionId, "dimensionId");
        int separator = dimensionId.indexOf(':');
        if (separator <= 0 || separator == dimensionId.length() - 1
            || dimensionId.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException("dimension id must be namespaced: " + dimensionId);
        }
        String namespace = dimensionId.substring(0, separator);
        String path = dimensionId.substring(separator + 1);
        if (!NAMESPACE.matcher(namespace).matches() || !PATH.matcher(path).matches()) {
            throw new IllegalArgumentException("invalid dimension id: " + dimensionId);
        }
    }

    public static TerritoryKey of(String dimensionId, long cellX, long cellZ) {
        return new TerritoryKey(dimensionId, cellX, cellZ);
    }
}
