package dev.gustavopere.volcanoes.pressure;

/** Loader-neutral water-depth lookup used only when a player is actually immersed. */
@FunctionalInterface
public interface PressureWaterDepthLookup {
    WaterDepthSample sample(PressureEntityContext context, long gameTick);
}
