package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record GemPowerModifier(String sourceId, double moreMultiplier) {
    public GemPowerModifier {
        Objects.requireNonNull(sourceId);
        if (sourceId.isBlank()) throw new IllegalArgumentException("sourceId must not be blank");
        if (!Double.isFinite(moreMultiplier) || moreMultiplier <= -1.0) {
            throw new IllegalArgumentException("gem power multiplier must be finite and greater than -1");
        }
    }
}
