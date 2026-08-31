package dev.gustavopere.volcanoes.tectonics;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/** Integration port for seismic consumers that must preserve the source dimension. */
@FunctionalInterface
public interface DimensionalSeismicPerturbationSink {
    void onSeismicEvent(ResourceKey<Level> dimension, SeismicEvent event);
}
