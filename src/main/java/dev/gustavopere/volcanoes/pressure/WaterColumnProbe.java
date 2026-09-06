package dev.gustavopere.volcanoes.pressure;

/** Minimal world adapter used by the bounded depth resolver. Implementations must not load chunks. */
public interface WaterColumnProbe {
    boolean isColumnLoaded(String dimensionId, int blockX, int blockZ);

    boolean isWater(String dimensionId, int blockX, int blockY, int blockZ);
}
