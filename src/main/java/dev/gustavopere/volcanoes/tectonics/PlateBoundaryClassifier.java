package dev.gustavopere.volcanoes.tectonics;

import java.util.Objects;

/** Pure kinematic classifier for the nearest plate boundary. */
public final class PlateBoundaryClassifier {
    static final double BOUNDARY_INFLUENCE_BLOCKS = 4_096.0;
    private static final double NORMAL_SPEED_THRESHOLD = 0.25;
    private static final double SHEAR_SPEED_THRESHOLD = 0.25;

    public PlateBoundarySample classify(PlateSample sample) {
        Objects.requireNonNull(sample, "sample");

        PlateVector motion = sample.motion();
        PlateVector neighborMotion = sample.neighborMotion();
        PlateVector normal = sample.boundaryNormal();

        double relativeX = motion.x() - neighborMotion.x();
        double relativeZ = motion.z() - neighborMotion.z();
        double normalRelativeSpeed = relativeX * normal.x() + relativeZ * normal.z();
        double tangentX = -normal.z();
        double tangentZ = normal.x();
        double shearSpeed = Math.abs(relativeX * tangentX + relativeZ * tangentZ);

        BoundaryType type = classifyType(
                sample.boundaryDistanceBlocks(),
                normalRelativeSpeed,
                shearSpeed);
        double boundaryPotential = switch (type) {
            case CONVERGENT -> 0.85;
            case DIVERGENT -> 0.55;
            case TRANSFORM -> 0.12;
            case INTERIOR -> 0.05;
        };
        double hotspotPotential = sample.hotspotIntensity() == 0.0
                ? 0.0
                : 0.05 + 0.95 * sample.hotspotIntensity();
        double volcanicPotential = Math.max(boundaryPotential, hotspotPotential);

        return new PlateBoundarySample(
                sample.plateId(),
                sample.neighborPlateId(),
                type,
                sample.boundaryDistanceBlocks(),
                normalRelativeSpeed,
                shearSpeed,
                volcanicPotential,
                sample.hotspotIntensity());
    }

    private static BoundaryType classifyType(
            double boundaryDistanceBlocks,
            double normalRelativeSpeed,
            double shearSpeed
    ) {
        if (boundaryDistanceBlocks > BOUNDARY_INFLUENCE_BLOCKS) {
            return BoundaryType.INTERIOR;
        }
        if (normalRelativeSpeed >= NORMAL_SPEED_THRESHOLD) {
            return BoundaryType.CONVERGENT;
        }
        if (normalRelativeSpeed <= -NORMAL_SPEED_THRESHOLD) {
            return BoundaryType.DIVERGENT;
        }
        if (shearSpeed >= SHEAR_SPEED_THRESHOLD) {
            return BoundaryType.TRANSFORM;
        }
        return BoundaryType.INTERIOR;
    }
}
