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

final class CompendiumCheckedInEditorialBatch17JUnitTest {
    private static final String TREES_CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/tfc/trees-batch3.json";
    private static final String FAUNA_CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/tfc/fauna-batch1.json";
    private static final ResourceLocation TREES_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/tfc/trees-batch3.json"
    );
    private static final ResourceLocation FAUNA_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/tfc/fauna-batch1.json"
    );
    private static final Map<String, String> EXPECTED_TITLES = Map.ofEntries(
        Map.entry("TREE:tfc:wood/sapling/oak", "Muda de carvalho"),
        Map.entry("TREE:tfc:wood/sapling/palm", "Muda de palmeira"),
        Map.entry("TREE:tfc:wood/sapling/pine", "Muda de pinheiro"),
        Map.entry("TREE:tfc:wood/sapling/rosewood", "Muda de jacarandá"),
        Map.entry("TREE:tfc:wood/sapling/sequoia", "Muda de sequoia"),
        Map.entry("TREE:tfc:wood/sapling/spruce", "Muda de espruce"),
        Map.entry("TREE:tfc:wood/sapling/sycamore", "Muda de sicômoro"),
        Map.entry("TREE:tfc:wood/sapling/white_cedar", "Muda de cedro branco"),
        Map.entry("TREE:tfc:wood/sapling/willow", "Muda de salgueiro"),
        Map.entry("ENTITY:tfc:bluegill", "Peixe bluegill")
    );
    private static final Set<String> EXPECTED_IDS = EXPECTED_TITLES.keySet();

    @Test
    void seventeenthTfcBatchClosesWoodsAndStartsAquaticFauna() throws Exception {
        JsonObject trees = load(TREES_CLASSPATH_RESOURCE);
        JsonObject fauna = load(FAUNA_CLASSPATH_RESOURCE);
        assertHeader(trees, "TREE", 9);
        assertHeader(fauna, "ENTITY", 1);

        LinkedHashSet<String> actualIds = new LinkedHashSet<>();
        LinkedHashMap<String, String> actualTitles = new LinkedHashMap<>();
        collect(trees, actualIds, actualTitles);
        collect(fauna, actualIds, actualTitles);

        assertEquals(EXPECTED_IDS, actualIds);
        assertEquals(EXPECTED_TITLES, actualTitles);

        Map<ResourceLocation, JsonElement> resources = Map.of(
            TREES_RESOURCE, trees,
            FAUNA_RESOURCE, fauna
        );
        var providerAbsent = CompendiumEditorialResourceLoader.prepare(resources, List.of());
        assertEquals(EXPECTED_IDS.size(), providerAbsent.entries().size());

        ArrayList<CompendiumEntry> technicalEntries = new ArrayList<>();
        EXPECTED_IDS.forEach(entryId -> technicalEntries.add(technical(entryId)));
        var providerPresent = CompendiumEditorialResourceLoader.prepare(resources, List.copyOf(technicalEntries));
        assertEquals(EXPECTED_IDS.size(), providerPresent.entries().size());
    }

    private JsonObject load(String path) throws Exception {
        try (InputStream input = getClass().getResourceAsStream(path)) {
            assertNotNull(input, "Stage 10.10 must ship the seventeenth reviewed pt-BR TFC editorial package");
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
        String translationKey = kind == CompendiumEntryKind.ENTITY
            ? "entity." + resource.getNamespace() + "." + resource.getPath().replace('/', '.')
            : "block." + resource.getNamespace() + "." + resource.getPath().replace('/', '.');
        return new CompendiumEntry(
            CompendiumEntryId.of(kind, resourceId),
            resource.getNamespace(),
            translationKey,
            Set.of("tfc"),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch17-test"),
            1
        );
    }
}
