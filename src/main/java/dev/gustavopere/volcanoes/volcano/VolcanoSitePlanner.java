package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import net.minecraft.core.BlockPos;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Pure deterministic planner for sparse volcano-site candidates. */
public final class VolcanoSitePlanner {
    private static final double BOUNDARY_INFLUENCE_BLOCKS = 2_048.0;

    private final double minimumSpacingBlocks;
    private final double minimumPlacementScore;

    public VolcanoSitePlanner(double minimumSpacingBlocks, double minimumPlacementScore) {
        if (!Double.isFinite(minimumSpacingBlocks) || minimumSpacingBlocks <= 0.0) {
            throw new IllegalArgumentException("minimumSpacingBlocks must be finite and positive");
        }
        if (!Double.isFinite(minimumPlacementScore)
                || minimumPlacementScore < 0.0
                || minimumPlacementScore > 1.0) {
            throw new IllegalArgumentException("minimumPlacementScore must be within [0, 1]");
        }
        this.minimumSpacingBlocks = minimumSpacingBlocks;
        this.minimumPlacementScore = minimumPlacementScore;
    }

    /**
     * Scores immutable tectonic geometry plus an optional terrain hint. Persisted stress is
     * intentionally ignored so site placement remains stable regardless of when a chunk generates.
     */
    public double placementScore(TectonicSample sample, boolean volcanicTerrainHint) {
        Objects.requireNonNull(sample, "sample");
        double contextBoost = switch (sample.context()) {
            case CONVERGENT -> 0.25;
            case DIVERGENT -> 0.16;
            case TRANSFORM -> 0.02;
            case HOTSPOT -> 0.30;
            case INTERIOR -> 0.0;
        };
        double boundaryBoost = switch (sample.context()) {
            case CONVERGENT, DIVERGENT, TRANSFORM ->
                    0.12 * clamp01(1.0 - sample.boundaryDistanceBlocks() / BOUNDARY_INFLUENCE_BLOCKS);
            case HOTSPOT, INTERIOR -> 0.0;
        };
        double terrainBoost = volcanicTerrainHint ? 0.12 : 0.0;
        return clamp01(sample.volcanicPotential() * 0.62 + contextBoost + boundaryBoost + terrainBoost);
    }

    public VolcanoType typeFor(TectonicSample sample) {
        Objects.requireNonNull(sample, "sample");
        return switch (sample.context()) {
            case CONVERGENT -> sample.volcanicPotential() >= 0.96
                    && sample.boundaryDistanceBlocks() <= 96.0
                    ? VolcanoType.CALDERA
                    : VolcanoType.STRATOVOLCANO;
            case DIVERGENT, TRANSFORM -> VolcanoType.FISSURE;
            case HOTSPOT, INTERIOR -> VolcanoType.SHIELD;
        };
    }

    /**
     * Resolves the canonical site implied by immutable worldgen inputs only.
     *
     * <p>This method deliberately does not consult persisted sites. The deterministic coarse
     * candidate field supplies worldgen spacing, while {@link #plan} remains the persistence-aware
     * path for integrations and later migration tooling.</p>
     */
    public Optional<VolcanoSite> candidate(
            long worldSeed,
            BlockPos center,
            TectonicSample sample,
            boolean volcanicTerrainHint
    ) {
        Objects.requireNonNull(center, "center");
        Objects.requireNonNull(sample, "sample");
        if (placementScore(sample, volcanicTerrainHint) < minimumPlacementScore) {
            return Optional.empty();
        }

        VolcanoType type = typeFor(sample);
        return Optional.of(new VolcanoSite(
                stableId(worldSeed, center),
                center,
                type,
                VolcanoState.DORMANT,
                sample.context(),
                sample.plateId(),
                sample.neighborPlateId(),
                sample.volcanicPotential()));
    }

    public Optional<VolcanoSite> plan(
            long worldSeed,
            BlockPos center,
            TectonicSample sample,
            boolean volcanicTerrainHint,
            VolcanicRegionService existingSites
    ) {
        Objects.requireNonNull(existingSites, "existingSites");
        Optional<VolcanoSite> candidate = candidate(worldSeed, center, sample, volcanicTerrainHint);
        if (candidate.isEmpty()) {
            return Optional.empty();
        }
        if (!existingSites.nearby(center, minimumSpacingBlocks).isEmpty()) {
            return Optional.empty();
        }
        return candidate;
    }

    private static UUID stableId(long worldSeed, BlockPos center) {
        String key = "volcanoes:site:" + worldSeed + ':' + center.getX() + ':' + center.getZ();
        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    }

    private static double clamp01(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
