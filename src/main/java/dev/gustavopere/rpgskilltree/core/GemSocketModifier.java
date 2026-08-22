package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record GemSocketModifier(String sourceId, int additionalSockets) {
    public GemSocketModifier {
        Objects.requireNonNull(sourceId);
        if (sourceId.isBlank()) throw new IllegalArgumentException("sourceId must not be blank");
        if (additionalSockets <= 0) throw new IllegalArgumentException("additionalSockets must be positive");
    }
}
