package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import java.util.Objects;

public record LootNumberSummary(LootResolution resolution, double min, double max) {
    public LootNumberSummary {
        Objects.requireNonNull(resolution, "resolution");
        if (resolution == LootResolution.EXACT) {
            if (!Double.isFinite(min) || !Double.isFinite(max) || min < 0.0 || max < min) {
                throw new IllegalArgumentException("invalid exact loot number range");
            }
        } else {
            min = Double.NaN;
            max = Double.NaN;
        }
    }

    public static LootNumberSummary exact(double value) {
        return new LootNumberSummary(LootResolution.EXACT, value, value);
    }

    public static LootNumberSummary range(double min, double max) {
        return new LootNumberSummary(LootResolution.EXACT, min, max);
    }

    public static LootNumberSummary conditional() {
        return new LootNumberSummary(LootResolution.CONDITIONAL, Double.NaN, Double.NaN);
    }

    public static LootNumberSummary unavailable() {
        return new LootNumberSummary(LootResolution.UNAVAILABLE, Double.NaN, Double.NaN);
    }
}
