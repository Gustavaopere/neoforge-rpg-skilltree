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

final class CompendiumCheckedInEditorialBatch18JUnitTest {
    private static final String CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/betterend/biomes-batch1.json";
    private static final ResourceLocation EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/betterend/biomes-batch1.json"
    );
    private static final Set<String> EXPECTED_IDS = Set.of(
        "BIOME:betterend:amber_land",
        "BIOME:betterend:blossoming_spires",
        "BIOME:betterend:chorus_forest",
        "BIOME:betterend:crystal_mountains",
        "BIOME:betterend:dragon_graveyards",
        "BIOME:betterend:dry_shrubland",
        "BIOME:betterend:dust_wastelands",
        "BIOME:betterend:foggy_mushroomland",
        "BIOME:betterend:glowing_grasslands",
        "BIOME:betterend:ice_starfield"
    );

    @Test
    void firstBetterEndBiomeBatchIsCheckedInReviewedAndLoadable() throws Exception {
        JsonObject pack;
        try (InputStream input = getClass().getResourceAsStream(CLASSPATH_RESOURCE)) {
            assertNotNull(input, "Stage 10.10 must ship the first reviewed pt-BR BetterEnd biome package");
            pack = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        }

        assertEquals(1, pack.get("schema").getAsInt());
        assertEquals("pt_br", pack.get("language").getAsString());
        assertEquals("betterend", pack.get("namespace").getAsString());
        assertEquals("BIOME", pack.get("kind").getAsString());
        assertEquals(10, pack.getAsJsonArray("entries").size());

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
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch18-test"),
            1
        );
    }
}
