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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CompendiumCheckedInEditorialBatch11JUnitTest {
    private static final String BIOME_CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/alexscaves/biomes-batch1.json";
    private static final String STRUCTURE_CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/alexscaves/structures-batch1.json";
    private static final ResourceLocation BIOME_EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/alexscaves/biomes-batch1.json"
    );
    private static final ResourceLocation STRUCTURE_EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/alexscaves/structures-batch1.json"
    );
    private static final Set<String> EXPECTED_IDS = Set.of(
        "BIOME:alexscaves:abyssal_chasm",
        "BIOME:alexscaves:candy_cavity",
        "BIOME:alexscaves:forlorn_hollows",
        "BIOME:alexscaves:magnetic_caves",
        "BIOME:alexscaves:primordial_caves",
        "BIOME:alexscaves:toxic_caves",
        "STRUCTURE:alexscaves:abyssal_ruins",
        "STRUCTURE:alexscaves:acid_pit",
        "STRUCTURE:alexscaves:cake_cave",
        "STRUCTURE:alexscaves:dino_bowl"
    );

    @Test
    void firstAlexsCavesWorldBatchIsCheckedInReviewedAndLoadable() throws Exception {
        JsonObject biomes = readPack(BIOME_CLASSPATH_RESOURCE, "BIOME", 6);
        JsonObject structures = readPack(STRUCTURE_CLASSPATH_RESOURCE, "STRUCTURE", 4);

        LinkedHashSet<String> actualIds = new LinkedHashSet<>();
        ArrayList<CompendiumEntry> technicalEntries = new ArrayList<>();
        for (JsonObject pack : List.of(biomes, structures)) {
            for (JsonElement element : pack.getAsJsonArray("entries")) {
                JsonObject entry = element.getAsJsonObject();
                String entryId = entry.get("entry_id").getAsString();
                actualIds.add(entryId);
                assertEquals("REVIEWED", entry.get("review_status").getAsString(), entryId);
                assertEquals("RUNTIME", entry.get("availability").getAsString(), entryId);
                technicalEntries.add(technical(entryId));
            }
        }

        assertEquals(EXPECTED_IDS, actualIds);
        Map<ResourceLocation, JsonElement> resources = new LinkedHashMap<>();
        resources.put(BIOME_EDITORIAL_RESOURCE, biomes);
        resources.put(STRUCTURE_EDITORIAL_RESOURCE, structures);
        var snapshot = CompendiumEditorialResourceLoader.prepare(resources, List.copyOf(technicalEntries));
        assertEquals(EXPECTED_IDS.size(), snapshot.entries().size());
    }

    private JsonObject readPack(String classpathResource, String kind, int expectedEntries) throws Exception {
        JsonObject pack;
        try (InputStream input = getClass().getResourceAsStream(classpathResource)) {
            assertNotNull(input, "Stage 10.10 must ship the reviewed pt-BR Alex's Caves " + kind + " package");
            pack = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        }
        assertEquals(1, pack.get("schema").getAsInt());
        assertEquals("pt_br", pack.get("language").getAsString());
        assertEquals("alexscaves", pack.get("namespace").getAsString());
        assertEquals(kind, pack.get("kind").getAsString());
        assertEquals(expectedEntries, pack.getAsJsonArray("entries").size());
        return pack;
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
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch11-test"),
            1
        );
    }
}
