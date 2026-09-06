package dev.gustavopere.volcanoes.tectonics;

import java.util.List;
import java.util.Objects;

/** Fan-out dispatcher for non-destructive seismic consequences. */
public final class SeismicEventDispatcher {
    private final List<SeismicPerturbationSink> perturbationSinks;

    public SeismicEventDispatcher(List<SeismicPerturbationSink> perturbationSinks) {
        Objects.requireNonNull(perturbationSinks, "perturbationSinks");
        this.perturbationSinks = List.copyOf(perturbationSinks);
        if (this.perturbationSinks.stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("perturbationSinks must not contain null");
        }
    }

    public SeismicDispatchResult dispatch(SeismicEvent event) {
        Objects.requireNonNull(event, "event");
        for (SeismicPerturbationSink sink : perturbationSinks) {
            sink.onSeismicEvent(event);
        }
        // Terrain/structure damage is deliberately not executed by core dispatch.
        return new SeismicDispatchResult(perturbationSinks.size(), false, false);
    }
}
