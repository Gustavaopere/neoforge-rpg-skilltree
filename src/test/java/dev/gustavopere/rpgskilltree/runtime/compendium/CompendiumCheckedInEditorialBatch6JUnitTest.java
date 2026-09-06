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

final class CompendiumCheckedInEditorialBatch6JUnitTest {
    private static final String CLASSPATH_RESOURCE =
        "/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch6.json";
    private static final ResourceLocation EDITORIAL_RESOURCE = ResourceLocation.parse(
        "rpgskilltree:compendium/editorial/pt_br/minecraft/entities-batch6.json"
    );
    private static final Set<String> EXPECTED_IDS = Set.of(
        "ENTITY:minecraft:blaze",
        "ENTITY:minecraft:breeze",
        "ENTITY:minecraft:elder_guardian",
        "ENTITY:minecraft:enderman",
        "ENTITY:minecraft:evoker",
        "ENTITY:minecraft:ghast",
        "ENTITY:minecraft:guardian",
        "ENTITY:minecraft:phantom",
        "ENTITY:minecraft:pillager",
        "ENTITY:minecraft:wither_skeleton"
    );

    @Test
    void sixthMinecraftProgressionHostileBatchIsCheckedInReviewedAndLoadable() throws Exception {
        JsonObject pack;
        try (InputStream input = getClass().getResourceAsStream(CLASSPATH_RESOURCE)) {
            assertNotNull(input, "Stage 10.10 must ship the sixth reviewed pt-BR vanilla hostile-creature package");
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

        assertEquals(EXPECTED_IDS, actualIds);
        var snapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(EDITORIAL_RESOURCE, pack),
            List.copyOf(technicalEntries)
        );
        assertEquals(EXPECTED_IDS.size(), snapshot.entries().size());
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
            new CompendiumProvenance(FactSource.REGISTRY, "checked-in-editorial-batch6-test"),
            1
        );
    }
}
