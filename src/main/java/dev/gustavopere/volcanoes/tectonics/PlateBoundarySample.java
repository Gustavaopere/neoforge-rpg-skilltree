package dev.gustavopere.volcanoes.tectonics;

import java.util.Objects;

/** Derived kinematic classification of the nearest tectonic boundary. */
public record PlateBoundarySample(
        PlateId plateId,
        PlateId neighborPlateId,
        BoundaryType type,
        double boundaryDistanceBlocks,
        double normalRelativeSpeed,
        double shearSpeed,
        double volcanicPotential,
        double hotspotIntensity
) {
    public PlateBoundarySample {
        plateId = Objects.requireNonNull(plateId, "plateId");
        neighborPlateId = Objects.requireNonNull(neighborPlateId, "neighborPlateId");
        type = Objects.requireNonNull(type, "type");
        if (!Double.isFinite(boundaryDistanceBlocks) || boundaryDistanceBlocks < 0.0) {
            throw new IllegalArgumentException("boundaryDistanceBlocks must be finite and non-negative");
        }
        if (!Double.isFinite(normalRelativeSpeed) || Math.abs(normalRelativeSpeed) > 2.000000001) {
            throw new IllegalArgumentException("normalRelativeSpeed must be finite and within physical bounds");
        }
        if (!Double.isFinite(shearSpeed) || shearSpeed < 0.0 || shearSpeed > 2.000000001) {
            throw new IllegalArgumentException("shearSpeed must be finite and within physical bounds");
        }
        if (!Double.isFinite(volcanicPotential) || volcanicPotential < 0.0 || volcanicPotential > 1.0) {
            throw new IllegalArgumentException("volcanicPotential must be within [0, 1]");
        }
        if (!Double.isFinite(hotspotIntensity) || hotspotIntensity < 0.0 || hotspotIntensity > 1.0) {
            throw new IllegalArgumentException("hotspotIntensity must be within [0, 1]");
        }
    }
}
