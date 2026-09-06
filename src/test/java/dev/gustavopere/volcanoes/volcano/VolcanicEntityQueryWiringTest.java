package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicEntityQueryWiringTest {
    private static final Path STAGE03_SOURCE_ROOT = Path.of(
            "src/main/java/dev/gustavopere/volcanoes/volcano");

    @Test
    void stage03ExposurePathsUseOneSharedEngineResultBoundedQuery() throws IOException {
        List<String> unboundedCallers;
        try (var files = Files.walk(STAGE03_SOURCE_ROOT)) {
            unboundedCallers = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> containsCompact(path, "getEntitiesOfClass(LivingEntity.class"))
                    .map(path -> STAGE03_SOURCE_ROOT.relativize(path).toString())
                    .sorted()
                    .toList();
        }
        assertTrue(
                unboundedCallers.isEmpty(),
                () -> "Stage 03 living-entity queries must be engine-result-bounded; offenders=" + unboundedCallers);

        Path helper = STAGE03_SOURCE_ROOT.resolve("VolcanicEntityQueryBudget.java");
        assertTrue(Files.isRegularFile(helper),
                "Stage 03 must centralize bounded living-entity discovery in VolcanicEntityQueryBudget");
        String helperSource = compact(Files.readString(helper));
        assertTrue(helperSource.contains("EntityTypeTest.forClass(LivingEntity.class)"),
                "shared collector must use a typed LivingEntity query");
        assertTrue(helperSource.contains("output,maxResults"),
                "shared collector must pass the hard caller cap into Level#getEntities maxResults");

        String geothermal = compact(read("GeothermalWorldgenRuntime.java"));
        String hazards = compact(read("VolcanicHazardWorldRuntime.java"));

        assertTrue(
                geothermal.contains("VolcanicEntityQueryBudget.collect(MAX_GEYSER_ENTITY_EXPOSURES_PER_TICK,"),
                "geyser discovery must stay engine-bounded by the global 16-entity ceiling so overlap duplicates cannot starve the remaining effect budget");
        assertTrue(
                geothermal.contains("entity->entity.isAlive()&&!exposedEntities.contains(entity.getUUID())"),
                "geyser overlap dedupe must occur inside the engine predicate before bounded results are consumed");
        assertTrue(
                geothermal.contains("if(affected>=budget){break;}"),
                "geyser application must still stop at the remaining global exposure budget");

        assertTrue(
                countOccurrences(hazards, "VolcanicEntityQueryBudget.collect(budget,") >= 2,
                "bomb and pyroclastic discovery must each use the remaining effect budget as their engine result cap");
    }

    private static boolean containsCompact(Path path, String needle) {
        try {
            return compact(Files.readString(path)).contains(needle);
        } catch (IOException failure) {
            throw new IllegalStateException("failed to audit Stage 03 source " + path, failure);
        }
    }

    private static String read(String fileName) throws IOException {
        return Files.readString(STAGE03_SOURCE_ROOT.resolve(fileName));
    }

    private static String compact(String source) {
        return source.replaceAll("\\s+", "");
    }

    private static int countOccurrences(String source, String needle) {
        int count = 0;
        int offset = 0;
        while ((offset = source.indexOf(needle, offset)) >= 0) {
            count++;
            offset += needle.length();
        }
        return count;
    }
}
