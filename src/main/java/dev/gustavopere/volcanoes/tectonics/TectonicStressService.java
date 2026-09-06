package dev.gustavopere.volcanoes.tectonics;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Canonical tectonic sampler backed by deterministic plate geometry and coarse persisted stress.
 */
public final class TectonicStressService implements TectonicService {
    static final double REGION_SIZE_BLOCKS = 8_192.0;
    static final int DEFAULT_UPDATE_INTERVAL_TICKS = 600;
    static final int DEFAULT_MAX_CELLS_PER_UPDATE = 16;
    static final double SEISMIC_RELEASE_THRESHOLD = 0.70;
    private static final double STRESS_APPROACH_FACTOR = 0.125;
    private static final double SEISMIC_RESIDUAL_FACTOR = 0.28;

    private final PlateField plateField;
    private final PlateBoundaryClassifier classifier;
    private final TectonicRegionState regionState;
    private final int updateIntervalTicks;
    private final int maxCellsPerUpdate;
    private int updateCursor;

    public TectonicStressService(PlateField plateField, TectonicRegionState regionState) {
        this(
                plateField,
                regionState,
                DEFAULT_UPDATE_INTERVAL_TICKS,
                DEFAULT_MAX_CELLS_PER_UPDATE);
    }

    public TectonicStressService(
            PlateField plateField,
            TectonicRegionState regionState,
            int updateIntervalTicks,
            int maxCellsPerUpdate
    ) {
        this.plateField = Objects.requireNonNull(plateField, "plateField");
        this.classifier = new PlateBoundaryClassifier();
        this.regionState = Objects.requireNonNull(regionState, "regionState");
        if (updateIntervalTicks < 400 || updateIntervalTicks > 1_200) {
            throw new IllegalArgumentException("updateIntervalTicks must be within 400..1200");
        }
        if (maxCellsPerUpdate <= 0) {
            throw new IllegalArgumentException("maxCellsPerUpdate must be positive");
        }
        this.updateIntervalTicks = updateIntervalTicks;
        this.maxCellsPerUpdate = maxCellsPerUpdate;
    }

    @Override
    public TectonicSample sample(long worldSeed, double x, double z) {
        PlateSample plate = plateField.sample(worldSeed, x, z);
        PlateBoundarySample boundary = classifier.classify(plate);
        long regionX = floorRegion(x);
        long regionZ = floorRegion(z);
        if (!regionState.contains(regionX, regionZ)) {
            regionState.putStress(regionX, regionZ, initialStress(boundary));
        }

        return new TectonicSample(
                plate.plateId().value(),
                plate.neighborPlateId().value(),
                contextFor(boundary),
                regionState.stressAt(regionX, regionZ),
                boundary.volcanicPotential(),
                boundary.boundaryDistanceBlocks(),
                plate.motion().x(),
                plate.motion().z());
    }

    /**
     * Releases sufficiently high persisted regional stress into a non-destructive seismic event.
     * The release immediately lowers the coarse region's stored stress so the same event cannot be
     * emitted repeatedly without stress rebuilding through the long-cadence evolution step.
     */
    public Optional<SeismicEvent> tryReleaseStress(long worldSeed, double x, double z) {
        TectonicSample sample = sample(worldSeed, x, z);
        if (sample.stress() < SEISMIC_RELEASE_THRESHOLD) {
            return Optional.empty();
        }

        double contextBoost = switch (sample.context()) {
            case CONVERGENT -> 0.50;
            case TRANSFORM -> 0.40;
            case DIVERGENT -> 0.20;
            case HOTSPOT -> 0.25;
            case INTERIOR -> 0.0;
        };
        double magnitude = Math.min(8.5, 2.5 + sample.stress() * 4.5 + contextBoost);
        double radiusBlocks = 384.0 + magnitude * 180.0;
        double decayExponent = 1.35 + (1.0 - sample.stress()) * 0.75;

        long regionX = floorRegion(x);
        long regionZ = floorRegion(z);
        double residualStress = Math.max(0.05, sample.stress() * SEISMIC_RESIDUAL_FACTOR);
        regionState.putStress(regionX, regionZ, residualStress);

        return Optional.of(new SeismicEvent(
                x,
                z,
                magnitude,
                radiusBlocks,
                decayExponent,
                SeismicDamagePolicy.safeDefaults()));
    }

    /**
     * Advances at most the configured number of already-known coarse regions on the configured
     * long cadence. Returns the number of regions processed.
     */
    public int tick(long worldSeed, long gameTime) {
        if (gameTime < 0L || Math.floorMod(gameTime, (long) updateIntervalTicks) != 0L) {
            return 0;
        }

        List<TectonicRegionState.RegionStress> entries = regionState.entries();
        if (entries.isEmpty()) {
            return 0;
        }

        int count = Math.min(maxCellsPerUpdate, entries.size());
        int start = Math.floorMod(updateCursor, entries.size());
        for (int offset = 0; offset < count; offset++) {
            TectonicRegionState.RegionStress entry = entries.get((start + offset) % entries.size());
            double centerX = (entry.regionX() + 0.5) * REGION_SIZE_BLOCKS;
            double centerZ = (entry.regionZ() + 0.5) * REGION_SIZE_BLOCKS;
            PlateBoundarySample boundary = classifier.classify(plateField.sample(worldSeed, centerX, centerZ));
            double target = targetStress(boundary);
            double next = clamp01(entry.stress() + (target - entry.stress()) * STRESS_APPROACH_FACTOR);
            regionState.putStress(entry.regionX(), entry.regionZ(), next);
        }
        updateCursor = (start + count) % entries.size();
        return count;
    }

    private static long floorRegion(double coordinate) {
        return (long) Math.floor(coordinate / REGION_SIZE_BLOCKS);
    }

    private static TectonicContext contextFor(PlateBoundarySample boundary) {
        if (boundary.type() == BoundaryType.INTERIOR && boundary.hotspotIntensity() >= 0.25) {
            return TectonicContext.HOTSPOT;
        }
        return switch (boundary.type()) {
            case CONVERGENT -> TectonicContext.CONVERGENT;
            case DIVERGENT -> TectonicContext.DIVERGENT;
            case TRANSFORM -> TectonicContext.TRANSFORM;
            case INTERIOR -> TectonicContext.INTERIOR;
        };
    }

    private static double initialStress(PlateBoundarySample boundary) {
        return targetStress(boundary) * 0.25;
    }

    private static double targetStress(PlateBoundarySample boundary) {
        double boundaryTarget = switch (boundary.type()) {
            case CONVERGENT -> 0.90;
            case TRANSFORM -> 0.80;
            case DIVERGENT -> 0.55;
            case INTERIOR -> 0.08;
        };
        return Math.max(boundaryTarget, boundary.hotspotIntensity() * 0.35);
    }

    private static double clamp01(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
