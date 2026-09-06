package dev.gustavopere.volcanoes.pressure;

/**
 * Loader-neutral view of a water volume. Implementations must never force chunk loads.
 * OPEN_AIR means a free fluid surface may equalize with the external atmosphere;
 * BLOCKED includes solid/closed cells and must never be treated as a surface.
 */
public interface WaterVolumeProbe {
    boolean isColumnLoaded(String dimensionId, int blockX, int blockZ);

    WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ);
}
