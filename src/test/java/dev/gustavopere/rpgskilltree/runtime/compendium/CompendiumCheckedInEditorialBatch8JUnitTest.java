package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonArray;
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

final class CompendiumCheckedInEditorialBatch8JUnitTest {
    private static final String CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch8.json";
    private static final ResourceLocation EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/minecraft/entities-batch8.json"
    );
    private static final Set<String> EXPECTED_IDS = Set.of(
        "ENTITY:minecraft:hoglin",
        "ENTITY:minecraft:piglin",
        "ENTITY:minecraft:piglin_brute",
        "ENTITY:minecraft:slime",
        "ENTITY:minecraft:villager",
        "ENTITY:minecraft:wandering_trader",
        "ENTITY:minecraft:zoglin",
        "ENTITY:minecraft:zombie_horse",
        "ENTITY:minecraft:zombie_villager",
        "ENTITY:minecraft:zombified_piglin"
    );

    @Test
    void eighthMinecraftRemainingRegularMobBatchIsCheckedInReviewedAndLoadable() throws Exception {
        JsonObject pack = loadPack();

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

        assertEquals(EXPECTED_IDS, actualIds);
        var snapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(EDITORIAL_RESOURCE, pack),
            List.copyOf(technicalEntries)
        );
        assertEquals(EXPECTED_IDS.size(), snapshot.entries().size());
    }

    @Test void diagnosticHoglinLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:hoglin"); }
    @Test void diagnosticPiglinLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:piglin"); }
    @Test void diagnosticPiglinBruteLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:piglin_brute"); }
    @Test void diagnosticSlimeLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:slime"); }
    @Test void diagnosticVillagerLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:villager"); }
    @Test void diagnosticWanderingTraderLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:wandering_trader"); }
    @Test void diagnosticZoglinLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:zoglin"); }
    @Test void diagnosticZombieHorseLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:zombie_horse"); }
    @Test void diagnosticZombieVillagerLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:zombie_villager"); }
    @Test void diagnosticZombifiedPiglinLoads() throws Exception { assertSingleEntryLoads("ENTITY:minecraft:zombified_piglin"); }

    private void assertSingleEntryLoads(String expectedId) throws Exception {
        JsonObject sourcePack = loadPack();
        JsonObject singlePack = new JsonObject();
        singlePack.addProperty("schema", sourcePack.get("schema").getAsInt());
        singlePack.addProperty("language", sourcePack.get("language").getAsString());
        singlePack.addProperty("namespace", sourcePack.get("namespace").getAsString());
        singlePack.addProperty("kind", sourcePack.get("kind").getAsString());
        JsonArray singleEntries = new JsonArray();
        for (JsonElement element : sourcePack.getAsJsonArray("entries")) {
            JsonObject entry = element.getAsJsonObject();
            if (expectedId.equals(entry.get("entry_id").getAsString())) {
                singleEntries.add(entry.deepCopy());
                break;
            }
        }
        assertEquals(1, singleEntries.size(), expectedId);
        singlePack.add("entries", singleEntries);
        var snapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(EDITORIAL_RESOURCE, singlePack),
            List.of(technical(expectedId))
        );
        assertEquals(1, snapshot.entries().size(), expectedId);
    }

    private JsonObject loadPack() throws Exception {
        try (InputStream input = getClass().getResourceAsStream(CLASSPATH_RESOURCE)) {
            assertNotNull(input, "Stage 10.10 must ship the eighth reviewed pt-BR vanilla regular-mob package");
            return JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        }
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
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch8-test"),
            1
        );
    }
}
