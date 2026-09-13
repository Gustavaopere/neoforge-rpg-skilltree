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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CompendiumCheckedInEditorialCorpusJUnitTest {
    private static final String FIRST_CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities.json";
    private static final ResourceLocation FIRST_EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/minecraft/entities.json"
    );
    private static final String SECOND_CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-02.json";
    private static final ResourceLocation SECOND_EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/minecraft/entities-02.json"
    );
    private static final Set<String> EXPECTED_FIRST_BATCH_IDS = Set.of(
        "ENTITY:minecraft:bee",
        "ENTITY:minecraft:cat",
        "ENTITY:minecraft:chicken",
        "ENTITY:minecraft:cow",
        "ENTITY:minecraft:goat",
        "ENTITY:minecraft:horse",
        "ENTITY:minecraft:pig",
        "ENTITY:minecraft:rabbit",
        "ENTITY:minecraft:sheep",
        "ENTITY:minecraft:wolf"
    );
    private static final Set<String> EXPECTED_SECOND_BATCH_IDS = Set.of(
        "ENTITY:minecraft:armadillo",
        "ENTITY:minecraft:camel",
        "ENTITY:minecraft:dolphin",
        "ENTITY:minecraft:fox",
        "ENTITY:minecraft:frog",
        "ENTITY:minecraft:llama",
        "ENTITY:minecraft:mooshroom",
        "ENTITY:minecraft:ocelot",
        "ENTITY:minecraft:panda",
        "ENTITY:minecraft:polar_bear"
    );

    @Test
    void firstMinecraftFaunaBatchRemainsCheckedInReviewedAndLoadable() throws Exception {
        assertReviewedPackage(FIRST_CLASSPATH_RESOURCE, FIRST_EDITORIAL_RESOURCE, EXPECTED_FIRST_BATCH_IDS);
    }

    @Test
    void secondMinecraftFaunaBatchIsCheckedInReviewedAndLoadable() throws Exception {
        assertReviewedPackage(SECOND_CLASSPATH_RESOURCE, SECOND_EDITORIAL_RESOURCE, EXPECTED_SECOND_BATCH_IDS);
    }

    private void assertReviewedPackage(
        String classpathResource,
        ResourceLocation editorialResource,
        Set<String> expectedIds
    ) throws Exception {
        JsonObject pack;
        try (InputStream input = getClass().getResourceAsStream(classpathResource)) {
            assertNotNull(input, "Stage 10.10 must ship reviewed pt-BR package " + classpathResource);
            pack = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        }

        assertEquals(1, pack.get("schema").getAsInt());
        assertEquals("pt_br", pack.get("language").getAsString());
        assertEquals("minecraft", pack.get("namespace").getAsString());
        assertEquals("ENTITY", pack.get("kind").getAsString());

        LinkedHashSet<String> actualIds = new LinkedHashSet<>();
        ArrayList<CompendiumEntry> technicalEntries = new ArrayList<>();
        for (JsonElement element : pack.getAsJsonArray("entries")) {
            JsonObject entry = element.getAsJsonObject();
            String entryId = entry.get("entry_id").getAsString();
            actualIds.add(entryId);
            assertEquals("REVIEWED", entry.get("review_status").getAsString(), entryId);
            assertEquals("RUNTIME", entry.get("availability").getAsString(), entryId);
            technicalEntries.add(technical(entryId));
        }

        assertEquals(expectedIds, actualIds);
        var snapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(editorialResource, pack),
            List.copyOf(technicalEntries)
        );
        assertEquals(expectedIds.size(), snapshot.entries().size());
    }

    private static CompendiumEntry technical(String entryId) {
        String resourceId = entryId.substring("ENTITY:".length());
        ResourceLocation resource = ResourceLocation.parse(resourceId);
        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, resourceId),
            resource.getNamespace(),
            "entity." + resource.getNamespace() + "." + resource.getPath(),
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-corpus-test"),
            1
        );
    }
}
