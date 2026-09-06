package dev.gustavopere.volcanoes.tectonics;

/** Integration port for systems that should react to seismic releases without owning tectonics. */
@FunctionalInterface
public interface SeismicPerturbationSink {
    void onSeismicEvent(SeismicEvent event);
}
