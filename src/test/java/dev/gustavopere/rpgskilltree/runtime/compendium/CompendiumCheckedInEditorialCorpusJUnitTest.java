package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private static final String CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities.json";
    private static final ResourceLocation EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/minecraft/entities.json"
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
        CorpusSnapshot corpus = loadCorpus();
        assertTrue(corpus.actualIds().containsAll(EXPECTED_FIRST_BATCH_IDS));
        assertEquals(corpus.actualIds().size(), corpus.loadedEntries());
    }

    @Test
    void secondMinecraftFaunaBatchIsCheckedInReviewedAndLoadable() throws Exception {
        CorpusSnapshot corpus = loadCorpus();
        assertTrue(
            corpus.actualIds().containsAll(EXPECTED_SECOND_BATCH_IDS),
            () -> "Stage 10.10 is missing second-batch vanilla fauna entries: "
                + missing(EXPECTED_SECOND_BATCH_IDS, corpus.actualIds())
        );
        assertEquals(corpus.actualIds().size(), corpus.loadedEntries());
    }

    private CorpusSnapshot loadCorpus() throws Exception {
        JsonObject pack;
        try (InputStream input = getClass().getResourceAsStream(CLASSPATH_RESOURCE)) {
            assertNotNull(input, "Stage 10.10 must ship the checked-in pt-BR editorial fauna package");
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

        var snapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(EDITORIAL_RESOURCE, pack),
            List.copyOf(technicalEntries)
        );
        return new CorpusSnapshot(Set.copyOf(actualIds), snapshot.entries().size());
    }

    private static Set<String> missing(Set<String> expected, Set<String> actual) {
        LinkedHashSet<String> missing = new LinkedHashSet<>(expected);
        missing.removeAll(actual);
        return Set.copyOf(missing);
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

    private record CorpusSnapshot(Set<String> actualIds, int loadedEntries) {}
}
