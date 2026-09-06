package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CompendiumCheckedInEditorialBatch20JUnitTest {
    private static final String CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/betterend/biomes-batch3.json";
    private static final ResourceLocation EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/betterend/biomes-batch3.json"
    );
    private static final Set<String> EXPECTED_IDS = Set.of(
        "BIOME:betterend:painted_mountains",
        "BIOME:betterend:empty_end_cave",
        "BIOME:betterend:empty_smaragdant_cave",
        "BIOME:betterend:lush_smaragdant_cave",
        "BIOME:betterend:empty_aurora_cave",
        "BIOME:betterend:lush_aurora_cave",
        "BIOME:betterend:jade_cave"
    );

    @Test
    void finalBetterEndBiomeBatchIsCheckedInReviewedAndLoadable() throws Exception {
        JsonObject pack;
        try (InputStream input = getClass().getResourceAsStream(CLASSPATH_RESOURCE)) {
            assertNotNull(input, "Stage 10.10 must ship the final reviewed BetterEnd biome package");
            pack = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        }

        assertEquals(1, pack.get("schema").getAsInt());
        assertEquals("pt_br", pack.get("language").getAsString());
        assertEquals("betterend", pack.get("namespace").getAsString());
        assertEquals("BIOME", pack.get("kind").getAsString());
        assertEquals(7, pack.getAsJsonArray("entries").size());

        List<CompendiumEntry> technicalEntries = pack.getAsJsonArray("entries").asList().stream()
            .map(JsonElement::getAsJsonObject)
            .peek(entry -> {
                String entryId = entry.get("entry_id").getAsString();
                assertEquals("REVIEWED", entry.get("review_status").getAsString(), entryId);
                assertEquals("RUNTIME", entry.get("availability").getAsString(), entryId);
            })
            .map(entry -> technical(entry.get("entry_id").getAsString()))
            .toList();

        Set<String> actualIds = pack.getAsJsonArray("entries").asList().stream()
            .map(JsonElement::getAsJsonObject)
            .map(entry -> entry.get("entry_id").getAsString())
            .collect(java.util.stream.Collectors.toSet());
        assertEquals(EXPECTED_IDS, actualIds);

        var snapshot = CompendiumEditorialResourceLoader.prepare(Map.of(EDITORIAL_RESOURCE, pack), technicalEntries);
        assertEquals(EXPECTED_IDS.size(), snapshot.entries().size());
    }

    private static CompendiumEntry technical(String entryId) {
        int separator = entryId.indexOf(':');
        CompendiumEntryKind kind = CompendiumEntryKind.valueOf(entryId.substring(0, separator));
        String resourceId = entryId.substring(separator + 1);
        ResourceLocation resource = ResourceLocation.parse(resourceId);
        return new CompendiumEntry(
            CompendiumEntryId.of(kind, resourceId),
            resource.getNamespace(),
            "compendium.test." + resource.getNamespace() + "." + resource.getPath(),
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch20-test"),
            1
        );
    }
}
