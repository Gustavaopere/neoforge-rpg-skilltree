package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.StaticTectonicService;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import dev.gustavopere.volcanoes.tectonics.VoronoiPlateField;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.Objects;
import java.util.Optional;

/**
 * Stateless geothermal-potential resolver for deterministic new-chunk world generation.
 *
 * <p>The resolver deliberately depends only on immutable tectonic geometry and the deterministic
 * volcano-site projection. It never reads persisted volcanoes or magma SavedData, so geothermal
 * placement cannot change merely because a chunk generated before or after runtime simulation.
 * Nearby projected magma chambers may raise the static tectonic baseline; when no canonical
 * volcano candidate exists, the result is exactly the tectonic-only potential.</p>
 */
public final class GeothermalWorldgenResolver {
    private static final double POTENTIAL_EPSILON = 1.0e-12;

    private final TectonicService tectonics;
    private final VolcanoWorldgenResolver volcanoes;
    private final GeothermalActivityService activity;

    public GeothermalWorldgenResolver(
            TectonicService tectonics,
            VolcanoWorldgenResolver volcanoes,
            GeothermalActivityService activity
    ) {
        this.tectonics = Objects.requireNonNull(tectonics, "tectonics");
        this.volcanoes = Objects.requireNonNull(volcanoes, "volcanoes");
        this.activity = Objects.requireNonNull(activity, "activity");
    }

    /** Builds the canonical persistence-free resolver used by geothermal worldgen. */
    public static GeothermalWorldgenResolver createDefault(int magmaInfluenceRadiusBlocks) {
        if (magmaInfluenceRadiusBlocks <= 0) {
            throw new IllegalArgumentException("magmaInfluenceRadiusBlocks must be positive");
        }
        TectonicService tectonics = new StaticTectonicService(new VoronoiPlateField());
        VolcanoWorldgenResolver volcanoes = new VolcanoWorldgenResolver(
                new VolcanoCandidateField(
                        VolcanoWorldgenResolver.DEFAULT_CELL_SIZE_BLOCKS,
                        VolcanoWorldgenResolver.DEFAULT_LATTICE_SPACING_BLOCKS),
                tectonics,
                new VolcanoSitePlanner(
                        VolcanoWorldgenResolver.DEFAULT_PERSISTED_SPACING_BLOCKS,
                        VolcanoWorldgenResolver.DEFAULT_MINIMUM_PLACEMENT_SCORE),
                magmaInfluenceRadiusBlocks);
        return new GeothermalWorldgenResolver(
                tectonics,
                volcanoes,
                new GeothermalActivityService(magmaInfluenceRadiusBlocks));
    }

    /** Returns deterministic geothermal potential in {@code [0, 1]} at the supplied position. */
    public double potentialAt(long worldSeed, BlockPos position) {
        Objects.requireNonNull(position, "position");

        TectonicSample sample = tectonics.sample(worldSeed, position.getX(), position.getZ());
        double potential = activity.potential(sample);

        for (VolcanoSite site : volcanoes.sitesAffectingChunk(worldSeed, new ChunkPos(position))) {
            double magmaDistance = horizontalDistance(position, site.center());
            MagmaChamber chamber = MagmaChamberFactory.initialFor(site);
            potential = Math.max(potential, activity.potential(sample, magmaDistance, chamber));
        }
        return potential;
    }

    /**
     * Returns the deterministic volcano whose magma contribution most strongly raises geothermal
     * potential at the supplied position. Purely tectonic points deliberately return empty.
     */
    public Optional<VolcanoSite> causalVolcanoAt(long worldSeed, BlockPos position) {
        Objects.requireNonNull(position, "position");

        TectonicSample sample = tectonics.sample(worldSeed, position.getX(), position.getZ());
        double baseline = activity.potential(sample);
        double strongestPotential = baseline;
        VolcanoSite strongest = null;

        for (VolcanoSite site : volcanoes.sitesAffectingChunk(worldSeed, new ChunkPos(position))) {
            double magmaDistance = horizontalDistance(position, site.center());
            double candidatePotential = activity.potential(
                    sample,
                    magmaDistance,
                    MagmaChamberFactory.initialFor(site));
            if (candidatePotential > strongestPotential + POTENTIAL_EPSILON
                    || (strongest != null
                    && Math.abs(candidatePotential - strongestPotential) <= POTENTIAL_EPSILON
                    && site.persistenceId().compareTo(strongest.persistenceId()) < 0)) {
                strongestPotential = candidatePotential;
                strongest = site;
            }
        }
        return Optional.ofNullable(strongest);
    }

    private static double horizontalDistance(BlockPos first, BlockPos second) {
        double dx = (double) first.getX() - second.getX();
        double dz = (double) first.getZ() - second.getZ();
        return Math.hypot(dx, dz);
    }
}
