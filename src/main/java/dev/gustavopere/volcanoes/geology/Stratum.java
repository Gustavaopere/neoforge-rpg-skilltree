package dev.gustavopere.volcanoes.geology;

/** One half-open vertical geological interval: [minY, maxYExclusive). */
public record Stratum(int minY, int maxYExclusive, String rockProfileId) {
    public Stratum {
        if (minY >= maxYExclusive) {
            throw new IllegalArgumentException("A stratum must have positive vertical thickness");
        }
        if (rockProfileId == null || rockProfileId.isBlank()) {
            throw new IllegalArgumentException("rockProfileId must not be blank");
        }
    }

    public boolean contains(int y) {
        return y >= minY && y < maxYExclusive;
    }
}
