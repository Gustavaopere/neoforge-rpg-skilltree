package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

/** Stable extensible identifier for one entity behavior package. */
public record EntityBehaviorKey(String namespace, String path) {
    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern PATH = Pattern.compile("[a-z0-9/._-]+");

    public EntityBehaviorKey {
        Objects.requireNonNull(namespace, "namespace");
        Objects.requireNonNull(path, "path");
        if (!NAMESPACE.matcher(namespace).matches()) {
            throw new IllegalArgumentException("invalid entity behavior namespace: " + namespace);
        }
        if (!PATH.matcher(path).matches()) {
            throw new IllegalArgumentException("invalid entity behavior path: " + path);
        }
    }

    public static EntityBehaviorKey of(String serializedId) {
        Objects.requireNonNull(serializedId, "serializedId");
        int separator = serializedId.indexOf(':');
        if (separator <= 0 || separator == serializedId.length() - 1
            || serializedId.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException("entity behavior id must be namespaced: " + serializedId);
        }
        return new EntityBehaviorKey(
            serializedId.substring(0, separator),
            serializedId.substring(separator + 1)
        );
    }

    public String serializedId() {
        return namespace + ":" + path;
    }

    @Override
    public String toString() {
        return serializedId();
    }
}
