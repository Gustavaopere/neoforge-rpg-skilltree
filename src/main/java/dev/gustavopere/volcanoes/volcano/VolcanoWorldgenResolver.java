package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.StaticTectonicService;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import dev.gustavopere.volcanoes.tectonics.VoronoiPlateField;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Stateless canonical resolver shared by volcano terrain generation and deferred persistence.
 */
public final class VolcanoWorldgenResolver {
    public static final int DEFAULT_CELL_SIZE_BLOCKS = 4_096;
    public static final int DEFAULT_LATTICE_SPACING_BLOCKS = 1_024;
    public static final double DEFAULT_PERSISTED_SPACING_BLOCKS = 2_048.0;
    public static final double DEFAULT_MINIMUM_PLACEMENT_SCORE = 0.55;

    private final VolcanoCandidateField candidateField;
    private final TectonicService tectonics;
    private final VolcanoSitePlanner planner;
    private final int maximumFootprintRadiusBlocks;

    public VolcanoWorldgenResolver(
            VolcanoCandidateField candidateField,
            TectonicService tectonics,
            VolcanoSitePlanner planner,
            int maximumFootprintRadiusBlocks
    ) {
        this.candidateField = Objects.requireNonNull(candidateField, "candidateField");
        this.tectonics = Objects.requireNonNull(tectonics, "tectonics");
        this.planner = Objects.requireNonNull(planner, "planner");
        if (maximumFootprintRadiusBlocks <= 0) {
            throw new IllegalArgumentException("maximumFootprintRadiusBlocks must be positive");
        }
        this.maximumFootprintRadiusBlocks = maximumFootprintRadiusBlocks;
    }

    public static VolcanoWorldgenResolver createDefault(int maximumFootprintRadiusBlocks) {
        return new VolcanoWorldgenResolver(
                new VolcanoCandidateField(DEFAULT_CELL_SIZE_BLOCKS, DEFAULT_LATTICE_SPACING_BLOCKS),
                new StaticTectonicService(new VoronoiPlateField()),
                new VolcanoSitePlanner(DEFAULT_PERSISTED_SPACING_BLOCKS, DEFAULT_MINIMUM_PLACEMENT_SCORE),
                maximumFootprintRadiusBlocks);
    }

    public List<VolcanoSite> sitesAffectingChunk(long worldSeed, ChunkPos chunk) {
        return sitesAffectingChunk(worldSeed, chunk, VolcanicTerrainHintProvider.none());
    }

    public List<VolcanoSite> sitesAffectingChunk(
            long worldSeed,
            ChunkPos chunk,
            VolcanicTerrainHintProvider terrainHints
    ) {
        Objects.requireNonNull(chunk, "chunk");
        Objects.requireNonNull(terrainHints, "terrainHints");
        List<VolcanoSite> sites = new ArrayList<>();
        for (BlockPos center : candidateField.centersAffectingChunk(
                worldSeed,
                chunk,
                maximumFootprintRadiusBlocks)) {
            resolve(worldSeed, center, terrainHints).ifPresent(sites::add);
        }
        return List.copyOf(sites);
    }

    public Optional<VolcanoSite> siteOwnedByChunk(long worldSeed, ChunkPos chunk) {
        return siteOwnedByChunk(worldSeed, chunk, VolcanicTerrainHintProvider.none());
    }

    public Optional<VolcanoSite> siteOwnedByChunk(
            long worldSeed,
            ChunkPos chunk,
            VolcanicTerrainHintProvider terrainHints
    ) {
        Objects.requireNonNull(chunk, "chunk");
        Objects.requireNonNull(terrainHints, "terrainHints");
        for (BlockPos center : candidateField.centersAffectingChunk(worldSeed, chunk, 0)) {
            if (candidateField.ownsCenter(chunk, center)) {
                return resolve(worldSeed, center, terrainHints);
            }
        }
        return Optional.empty();
    }

    private Optional<VolcanoSite> resolve(
            long worldSeed,
            BlockPos center,
            VolcanicTerrainHintProvider terrainHints
    ) {
        return planner.candidate(
                worldSeed,
                center,
                tectonics.sample(worldSeed, center.getX(), center.getZ()),
                terrainHints.isVolcanic(center));
    }
}
