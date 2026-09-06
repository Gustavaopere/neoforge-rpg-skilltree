package dev.gustavopere.volcanoes.tectonics;

/** Pure deterministic tectonic plate field requiring only seed and horizontal coordinates. */
@FunctionalInterface
public interface PlateField {
    PlateSample sample(long worldSeed, double x, double z);
}
