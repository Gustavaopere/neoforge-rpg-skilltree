package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record DiscoveryProgress(Set<String> discoveredKeys) {
    public DiscoveryProgress {
        Objects.requireNonNull(discoveredKeys);
        for (String key : discoveredKeys) {
            if (key == null || key.isBlank()) throw new IllegalArgumentException("discovery keys must not be blank");
        }
        discoveredKeys = Set.copyOf(discoveredKeys);
    }

    public static DiscoveryProgress empty() {
        return new DiscoveryProgress(Set.of());
    }

    public static DiscoveryProgress of(Set<String> discoveredKeys) {
        return new DiscoveryProgress(discoveredKeys);
    }

    public boolean contains(String discoveryKey) {
        return discoveredKeys.contains(discoveryKey);
    }

    public DiscoveryProgress add(String discoveryKey) {
        if (discoveryKey == null || discoveryKey.isBlank()) {
            throw new IllegalArgumentException("discoveryKey must not be blank");
        }
        if (contains(discoveryKey)) return this;
        Set<String> next = new HashSet<>(discoveredKeys);
        next.add(discoveryKey);
        return new DiscoveryProgress(next);
    }
}
