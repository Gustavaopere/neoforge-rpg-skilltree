package dev.gustavopere.volcanoes.tectonics;

import java.util.Objects;

/**
 * Pure tectonic sampler for deterministic world generation.
 *
 * <p>Unlike {@link TectonicStressService}, this service has no SavedData dependency and always
 * reports zero dynamic stress. Site placement can therefore depend on immutable plate geometry
 * without changing when a chunk happens to generate.</p>
 */
public final class StaticTectonicService implements TectonicService {
    private static final double HOTSPOT_CONTEXT_THRESHOLD = 0.25;

    private final PlateField plateField;
    private final PlateBoundaryClassifier classifier;

    public StaticTectonicService(PlateField plateField) {
        this.plateField = Objects.requireNonNull(plateField, "plateField");
        this.classifier = new PlateBoundaryClassifier();
    }

    @Override
    public TectonicSample sample(long worldSeed, double x, double z) {
        PlateSample plate = plateField.sample(worldSeed, x, z);
        PlateBoundarySample boundary = classifier.classify(plate);
        TectonicContext context = contextFor(boundary);

        return new TectonicSample(
                plate.plateId().value(),
                plate.neighborPlateId().value(),
                context,
                0.0,
                boundary.volcanicPotential(),
                boundary.boundaryDistanceBlocks(),
                plate.motion().x(),
                plate.motion().z());
    }

    private static TectonicContext contextFor(PlateBoundarySample boundary) {
        if (boundary.type() == BoundaryType.INTERIOR
                && boundary.hotspotIntensity() >= HOTSPOT_CONTEXT_THRESHOLD) {
            return TectonicContext.HOTSPOT;
        }
        return switch (boundary.type()) {
            case CONVERGENT -> TectonicContext.CONVERGENT;
            case DIVERGENT -> TectonicContext.DIVERGENT;
            case TRANSFORM -> TectonicContext.TRANSFORM;
            case INTERIOR -> TectonicContext.INTERIOR;
        };
    }
}
