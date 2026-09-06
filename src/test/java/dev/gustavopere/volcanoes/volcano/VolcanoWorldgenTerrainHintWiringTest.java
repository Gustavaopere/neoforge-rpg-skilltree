package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoWorldgenTerrainHintWiringTest {
    @Test
    void featureAndPersistenceUseTheSameLevelBackedTerrainHintProvider() throws Exception {
        String feature = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanoWorldgenFeature.java"));
        String runtime = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanoWorldgenRuntime.java"));

        assertTrue(feature.contains("VolcanoWorldgenTerrainHints.forLevel(level)"),
                "terrain generation must resolve site admission from the actual biome tags");
        assertTrue(runtime.contains("VolcanoWorldgenTerrainHints.forLevel(level)"),
                "deferred persistence must use the same biome-hint authority as terrain generation");
        assertTrue(feature.contains("RESOLVER.sitesAffectingChunk(level.getSeed(), chunk, terrainHints)"));
        assertTrue(runtime.contains("RESOLVER.siteOwnedByChunk(worldSeed, chunk, terrainHints)"));
    }
}
