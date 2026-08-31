package dev.gustavopere.volcanoes.tectonics;

/** Explicit safety switches for optional seismic block damage. */
public record SeismicDamagePolicy(boolean terrainDamage, boolean structureDamage) {
    public static SeismicDamagePolicy safeDefaults() {
        return new SeismicDamagePolicy(false, false);
    }
}
