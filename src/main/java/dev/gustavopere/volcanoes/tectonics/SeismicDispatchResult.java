package dev.gustavopere.volcanoes.tectonics;

/** Observable dispatch outcome; core dispatch never mutates blocks by itself. */
public record SeismicDispatchResult(
        int perturbationSinksNotified,
        boolean terrainModified,
        boolean structureModified
) {
    public SeismicDispatchResult {
        if (perturbationSinksNotified < 0) {
            throw new IllegalArgumentException("perturbationSinksNotified must be non-negative");
        }
    }
}
