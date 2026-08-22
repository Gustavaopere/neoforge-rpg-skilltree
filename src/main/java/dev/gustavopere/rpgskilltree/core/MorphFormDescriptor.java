package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

public record MorphFormDescriptor(String entityId, MorphFormCategory category, Set<String> tags) {
    public MorphFormDescriptor {
        Objects.requireNonNull(entityId);
        Objects.requireNonNull(category);
        Objects.requireNonNull(tags);
        if (entityId.isBlank()) throw new IllegalArgumentException("entityId must not be blank");
        tags = Set.copyOf(tags);
    }

    public boolean explicitlyBlacklisted() {
        return tags.contains("rpgskilltree:morph_blacklist");
    }
}
