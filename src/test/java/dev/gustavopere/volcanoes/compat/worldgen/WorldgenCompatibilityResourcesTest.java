package dev.gustavopere.volcanoes.compat.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class WorldgenCompatibilityResourcesTest {
    @Test
    void terralithHintsLiveInOptionalDatapackEntriesRatherThanJavaCore() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanicTerrainHints.java"));

        assertFalse(source.contains("ResourceLocation.fromNamespaceAndPath(\"terralith\""),
                "external Terralith biome IDs must not be hard-coded in the Java core");

        JsonObject json = resource("data/volcanoes/tags/worldgen/biome/is_volcanic.json");
        assertFalse(json.get("replace").getAsBoolean());

        JsonArray values = json.getAsJsonArray("values");
        Set<String> ids = StreamSupport.stream(values.spliterator(), false)
                .map(element -> element.getAsJsonObject())
                .peek(entry -> assertFalse(entry.get("required").getAsBoolean(),
                        "external Terralith hints must remain fail-soft"))
                .map(entry -> entry.get("id").getAsString())
                .collect(Collectors.toUnmodifiableSet());

        assertEquals(Set.of(
                "terralith:volcanic_crater",
                "terralith:volcanic_peaks"), ids);
    }

    @Test
    void matrixPinsVerifiedWorldgenStackAndDoesNotDeleteHostContent() throws Exception {
        String script = Files.readString(Path.of(".github/scripts/run_worldgen_matrix_case.sh"));
        String workflow = Files.readString(Path.of(".github/workflows/worldgen-compatibility-matrix.yml"));

        assertTrue(script.contains("modrinth_version IY93YaEe"), "Terralith 2.6.2 must stay pinned");
        assertTrue(script.contains("modrinth_version vNrkxC3z"), "Tectonic 3.0.26 must stay pinned");
        assertTrue(script.contains("modrinth_version aPEcdSHb"), "BWG 2.6.0 must stay pinned");
        assertTrue(script.contains("modrinth_version EAjbdreT"), "Biolith NeoForge 3.0.14 must stay pinned");

        assertTrue(script.contains("WITH_BIOLITH"), "matrix runner must expose the Biolith compatibility dimension");
        assertTrue(workflow.contains("biolith: 'true'"), "Terralith+BWG stack cases must exercise Biolith");
        assertTrue(workflow.contains("WITH_BIOLITH: ${{ matrix.biolith }}"),
                "workflow must forward the Biolith matrix flag to the runner");

        assertFalse(script.contains("write_host_compat_configs"),
                "matrix must not patch host worldgen configuration to manufacture a pass");
        assertFalse(script.contains("biomeswevegone:coconino_meadow"),
                "compatibility must preserve BWG Coconino Meadow");
        assertFalse(script.contains("biomeswevegone:sakura_grove"),
                "compatibility must preserve BWG Sakura Grove");
        assertFalse(script.contains("individual_vanilla_additions"),
                "compatibility must not disable BWG vanilla additions");
        assertFalse(script.contains("\"vanilla_additions\": {"),
                "compatibility must not disable all BWG vanilla additions");
    }

    private static JsonObject resource(String path) {
        var stream = WorldgenCompatibilityResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(stream, "missing bundled optional worldgen compatibility tag: " + path);
        try (stream; var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            throw new AssertionError("failed to read compatibility resource: " + path, exception);
        }
    }
}
