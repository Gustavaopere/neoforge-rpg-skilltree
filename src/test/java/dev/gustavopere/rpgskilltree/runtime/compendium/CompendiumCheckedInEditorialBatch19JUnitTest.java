package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CompendiumCheckedInEditorialBatch19JUnitTest {
    private static final String CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/tfc/fauna-batch3.json";
    private static final ResourceLocation RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/tfc/fauna-batch3.json"
    );
    private static final Map<String, String> EXPECTED_TITLES = Map.ofEntries(
        Map.entry("ENTITY:tfc:tilapia", "Peixe tilapia"),
        Map.entry("ENTITY:tfc:spotted_gudgeon", "Peixe spotted gudgeon"),
        Map.entry("ENTITY:tfc:peacock_bass", "Peixe peacock bass"),
        Map.entry("ENTITY:tfc:pacu", "Peixe pacu"),
        Map.entry("ENTITY:tfc:red_piranha", "Peixe red piranha"),
        Map.entry("ENTITY:tfc:cod", "Peixe cod"),
        Map.entry("ENTITY:tfc:tropical_fish", "Peixe tropical fish"),
        Map.entry("ENTITY:tfc:pufferfish", "Peixe pufferfish"),
        Map.entry("ENTITY:tfc:jellyfish", "Água-viva jellyfish"),
        Map.entry("ENTITY:tfc:isopod", "Isópode isopod")
    );
    private static final Set<String> EXPECTED_IDS = EXPECTED_TITLES.keySet();

    @Test
    void nineteenthTfcBatchFinishesFreshwaterFishAndContinuesWaterAmbientRegistrationOrder() throws Exception {
        JsonObject fauna = load(CLASSPATH_RESOURCE);
        assertHeader(fauna, "ENTITY", 10);

        LinkedHashSet<String> actualIds = new LinkedHashSet<>();
        LinkedHashMap<String, String> actualTitles = new LinkedHashMap<>();
        collect(fauna, actualIds, actualTitles);

        assertEquals(EXPECTED_IDS, actualIds);
        assertEquals(EXPECTED_TITLES, actualTitles);

        Map<ResourceLocation, JsonElement> resources = Map.of(RESOURCE, fauna);
        var providerAbsent = CompendiumEditorialResourceLoader.prepare(resources, List.of());
        assertEquals(EXPECTED_IDS.size(), providerAbsent.entries().size());

        ArrayList<CompendiumEntry> technicalEntries = new ArrayList<>();
        EXPECTED_IDS.forEach(entryId -> technicalEntries.add(technical(entryId)));
        var providerPresent = CompendiumEditorialResourceLoader.prepare(resources, List.copyOf(technicalEntries));
        assertEquals(EXPECTED_IDS.size(), providerPresent.entries().size());
    }

    private JsonObject load(String path) throws Exception {
        try (InputStream input = getClass().getResourceAsStream(path)) {
            assertNotNull(input, "Stage 10.10 must ship the nineteenth reviewed pt-BR TFC editorial package");
            return JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static void assertHeader(JsonObject pack, String kind, int expectedSize) {
        assertEquals(1, pack.get("schema").getAsInt());
        assertEquals("pt_br", pack.get("language").getAsString());
        assertEquals("tfc", pack.get("namespace").getAsString());
        assertEquals(kind, pack.get("kind").getAsString());
        assertEquals(expectedSize, pack.getAsJsonArray("entries").size());
    }

    private static void collect(JsonObject pack, Set<String> actualIds, Map<String, String> actualTitles) {
        for (JsonElement element : pack.getAsJsonArray("entries")) {
            JsonObject entry = element.getAsJsonObject();
            String entryId = entry.get("entry_id").getAsString();
            actualIds.add(entryId);
            actualTitles.put(entryId, entry.get("title").getAsString());
            assertEquals("REVIEWED", entry.get("review_status").getAsString(), entryId);
            assertEquals("OPTIONAL", entry.get("availability").getAsString(), entryId);
            assertFalse(entry.get("availability_reason").getAsString().isBlank(), entryId);
        }
    }

    private static CompendiumEntry technical(String entryId) {
        int separator = entryId.indexOf(':');
        CompendiumEntryKind kind = CompendiumEntryKind.valueOf(entryId.substring(0, separator));
        String resourceId = entryId.substring(separator + 1);
        ResourceLocation resource = ResourceLocation.parse(resourceId);
        String translationKey = "entity." + resource.getNamespace() + "." + resource.getPath().replace('/', '.');
        return new CompendiumEntry(
            CompendiumEntryId.of(kind, resourceId),
            resource.getNamespace(),
            translationKey,
            Set.of("tfc"),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch19-test"),
            1
        );
    }
}
