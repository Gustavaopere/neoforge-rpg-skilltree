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

final class CompendiumCheckedInEditorialBatch13JUnitTest {
    private static final String CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/tfc/agro-batch3.json";
    private static final ResourceLocation EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/tfc/agro-batch3.json"
    );
    private static final Map<String, String> EXPECTED_TITLES = Map.ofEntries(
        Map.entry("FLORA:tfc:crop/squash", "Moringa"),
        Map.entry("FLORA:tfc:crop/pumpkin", "Abóbora"),
        Map.entry("FLORA:tfc:crop/melon", "Melancia"),
        Map.entry("FLORA:tfc:crop/canola", "Canola"),
        Map.entry("FLORA:tfc:crop/radish", "Rabanete"),
        Map.entry("FLORA:tfc:crop/alfalfa", "Alfafa"),
        Map.entry("FLORA:tfc:crop/jute", "Juta"),
        Map.entry("FLORA:tfc:crop/papyrus", "Papiro"),
        Map.entry("FLORA:tfc:crop/sugarcane", "Cana de açúcar"),
        Map.entry("FLORA:tfc:plant/blackberry_bush", "Arbusto de amora")
    );
    private static final Set<String> EXPECTED_IDS = EXPECTED_TITLES.keySet();

    @Test
    void thirdTfcAgroBatchIsCheckedInReviewedLocalizedAndLoadable() throws Exception {
        JsonObject pack;
        try (InputStream input = getClass().getResourceAsStream(CLASSPATH_RESOURCE)) {
            assertNotNull(input, "Stage 10.10 must ship the third reviewed pt-BR TFC agro package");
            pack = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        }

        assertEquals(1, pack.get("schema").getAsInt());
        assertEquals("pt_br", pack.get("language").getAsString());
        assertEquals("tfc", pack.get("namespace").getAsString());
        assertEquals("FLORA", pack.get("kind").getAsString());

        LinkedHashSet<String> actualIds = new LinkedHashSet<>();
        LinkedHashMap<String, String> actualTitles = new LinkedHashMap<>();
        ArrayList<CompendiumEntry> technicalEntries = new ArrayList<>();
        for (JsonElement element : pack.getAsJsonArray("entries")) {
            JsonObject entry = element.getAsJsonObject();
            String entryId = entry.get("entry_id").getAsString();
            actualIds.add(entryId);
            actualTitles.put(entryId, entry.get("title").getAsString());
            assertEquals("REVIEWED", entry.get("review_status").getAsString(), entryId);
            assertEquals("OPTIONAL", entry.get("availability").getAsString(), entryId);
            assertFalse(entry.get("availability_reason").getAsString().isBlank(), entryId);
            technicalEntries.add(technical(entryId));
        }

        assertEquals(EXPECTED_IDS, actualIds);
        assertEquals(EXPECTED_TITLES, actualTitles);

        var providerAbsent = CompendiumEditorialResourceLoader.prepare(
            Map.of(EDITORIAL_RESOURCE, pack),
            List.of()
        );
        assertEquals(EXPECTED_IDS.size(), providerAbsent.entries().size());

        var providerPresent = CompendiumEditorialResourceLoader.prepare(
            Map.of(EDITORIAL_RESOURCE, pack),
            List.copyOf(technicalEntries)
        );
        assertEquals(EXPECTED_IDS.size(), providerPresent.entries().size());
    }

    private static CompendiumEntry technical(String entryId) {
        String resourceId = entryId.substring("FLORA:".length());
        ResourceLocation resource = ResourceLocation.parse(resourceId);
        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.FLORA, resourceId),
            resource.getNamespace(),
            "block." + resource.getNamespace() + "." + resource.getPath().replace('/', '.'),
            Set.of("tfc"),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch13-test"),
            1
        );
    }
}
