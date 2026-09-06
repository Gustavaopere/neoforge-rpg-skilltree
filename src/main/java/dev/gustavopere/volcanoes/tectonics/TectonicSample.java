package dev.gustavopere.volcanoes.tectonics;

import java.util.Objects;

/** Pure, world-independent tectonic classification at one horizontal position. */
public record TectonicSample(
        long plateId,
        long neighborPlateId,
        TectonicContext context,
        double stress,
        double volcanicPotential,
        double boundaryDistanceBlocks,
        double motionX,
        double motionZ
) {
    public TectonicSample {
        context = Objects.requireNonNull(context, "context");
        stress = requireUnit("stress", stress);
        volcanicPotential = requireUnit("volcanicPotential", volcanicPotential);
        if (!Double.isFinite(boundaryDistanceBlocks) || boundaryDistanceBlocks < 0.0) {
            throw new IllegalArgumentException("boundaryDistanceBlocks must be finite and non-negative");
        }
        if (!Double.isFinite(motionX) || !Double.isFinite(motionZ)) {
            throw new IllegalArgumentException("motion vector must be finite");
        }
    }

    private static double requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }
}
