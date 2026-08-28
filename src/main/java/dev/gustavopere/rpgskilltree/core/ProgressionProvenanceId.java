package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

/** Stable namespaced provenance identifier for persisted progression facts. */
public record ProgressionProvenanceId(String namespace, String path) {
    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern PATH = Pattern.compile("[a-z0-9/._-]+");

    public ProgressionProvenanceId {
        Objects.requireNonNull(namespace, "namespace");
        Objects.requireNonNull(path, "path");
        if (!NAMESPACE.matcher(namespace).matches()) {
            throw new IllegalArgumentException("invalid progression provenance namespace: " + namespace);
        }
        if (!PATH.matcher(path).matches()) {
            throw new IllegalArgumentException("invalid progression provenance path: " + path);
        }
    }

    public static ProgressionProvenanceId of(String serializedId) {
        String[] parts = splitNamespaced(serializedId, "progression provenance id");
        return new ProgressionProvenanceId(parts[0], parts[1]);
    }

    static String requireNamespacedId(String serializedId, String label) {
        String[] parts = splitNamespaced(serializedId, label);
        if (!NAMESPACE.matcher(parts[0]).matches() || !PATH.matcher(parts[1]).matches()) {
            throw new IllegalArgumentException("invalid " + label + ": " + serializedId);
        }
        return serializedId;
    }

    private static String[] splitNamespaced(String serializedId, String label) {
        Objects.requireNonNull(serializedId, label);
        int separator = serializedId.indexOf(':');
        if (separator <= 0
            || separator == serializedId.length() - 1
            || serializedId.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException(label + " must be a namespaced id: " + serializedId);
        }
        return new String[] {
            serializedId.substring(0, separator),
            serializedId.substring(separator + 1)
        };
    }

    public String serializedId() {
        return namespace + ":" + path;
    }

    @Override
    public String toString() {
        return serializedId();
    }
}
