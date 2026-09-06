package dev.gustavopere.volcanoes.tectonics;

import java.util.Objects;

/** Deterministic tectonic plate information at one horizontal coordinate. */
public record PlateSample(
        PlateId plateId,
        double centerX,
        double centerZ,
        PlateVector motion,
        PlateId neighborPlateId,
        PlateVector neighborMotion,
        PlateVector boundaryNormal,
        double boundaryDistanceBlocks,
        double hotspotIntensity
) {
    public PlateSample {
        plateId = Objects.requireNonNull(plateId, "plateId");
        motion = Objects.requireNonNull(motion, "motion");
        neighborPlateId = Objects.requireNonNull(neighborPlateId, "neighborPlateId");
        neighborMotion = Objects.requireNonNull(neighborMotion, "neighborMotion");
        boundaryNormal = Objects.requireNonNull(boundaryNormal, "boundaryNormal");
        if (plateId.equals(neighborPlateId)) {
            throw new IllegalArgumentException("neighborPlateId must differ from plateId");
        }
        if (!Double.isFinite(centerX) || !Double.isFinite(centerZ)) {
            throw new IllegalArgumentException("plate center must be finite");
        }
        if (!Double.isFinite(boundaryDistanceBlocks) || boundaryDistanceBlocks < 0.0) {
            throw new IllegalArgumentException("boundaryDistanceBlocks must be finite and non-negative");
        }
        if (!Double.isFinite(hotspotIntensity) || hotspotIntensity < 0.0 || hotspotIntensity > 1.0) {
            throw new IllegalArgumentException("hotspotIntensity must be within [0, 1]");
        }
    }
}
