package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialBlock;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialContent;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSnapshot;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSource;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialAvailability;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialReviewStatus;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialSourceType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class RuntimeCompendiumEditorialCatalogJUnitTest {
    private static final CompendiumEntryId WOLF_ID =
        CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf");

    @BeforeEach
    void resetCatalog() {
        RuntimeCompendiumEditorialCatalog.resetForTests();
    }

    @Test
    void startsEmptyAndPublishAtomicallyReplacesTheWholeSnapshot() {
        assertTrue(RuntimeCompendiumEditorialCatalog.snapshot().entries().isEmpty());

        CompendiumEditorialSnapshot candidate = CompendiumEditorialSnapshot.fromEntries(List.of(wolfEditorial()));
        assertSame(candidate, RuntimeCompendiumEditorialCatalog.publish(candidate));
        assertSame(candidate, RuntimeCompendiumEditorialCatalog.snapshot());
    }

    @Test
    void rejectedValidationCandidatePreservesTheLastGoodSnapshot() {
        CompendiumEditorialSnapshot lastGood = CompendiumEditorialSnapshot.fromEntries(List.of(wolfEditorial()));
        RuntimeCompendiumEditorialCatalog.publish(lastGood);

        Map<ResourceLocation, com.google.gson.JsonElement> invalid = Map.of(
            ResourceLocation.parse("rpgskilltree:compendium/editorial/pt_br/minecraft/entities.json"),
            JsonParser.parseString("""
                {"schema":1,"language":"pt_br","namespace":"minecraft","kind":"ENTITY","entries":[{
                  "entry_id":"ENTITY:minecraft:wolf","title":"TODO",
                  "summary":{"text":"Resumo válido.","sources":[{"type":"RUNTIME","ref":"minecraft:entity_type/minecraft:wolf"}]},
                  "sections":{},"references":[],"review_status":"REVIEWED","availability":"RUNTIME"
                }]}
                """)
        );

        assertThrows(
            CompendiumEditorialValidationException.class,
            () -> CompendiumEditorialResourceLoader.prepare(invalid, List.of(wolfTechnical()))
        );
        assertSame(lastGood, RuntimeCompendiumEditorialCatalog.snapshot());
    }

    @Test
    void programmingFailureIsNotSwallowedAndCannotClobberTheLastGoodSnapshot() {
        CompendiumEditorialSnapshot lastGood = CompendiumEditorialSnapshot.fromEntries(List.of(wolfEditorial()));
        RuntimeCompendiumEditorialCatalog.publish(lastGood);

        assertThrows(NullPointerException.class, () -> RuntimeCompendiumEditorialCatalog.publish(null));
        assertSame(lastGood, RuntimeCompendiumEditorialCatalog.snapshot());
    }

    private static CompendiumEditorialContent wolfEditorial() {
        return new CompendiumEditorialContent(
            WOLF_ID,
            "Lobo",
            new CompendiumEditorialBlock(
                "Resumo válido.",
                List.of(new CompendiumEditorialSource(
                    EditorialSourceType.RUNTIME,
                    "minecraft:entity_type/minecraft:wolf",
                    null
                ))
            ),
            List.of(),
            List.of(),
            EditorialReviewStatus.REVIEWED,
            EditorialAvailability.RUNTIME,
            null
        );
    }

    private static CompendiumEntry wolfTechnical() {
        return new CompendiumEntry(
            WOLF_ID,
            "minecraft",
            "entity.minecraft.wolf",
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "test"),
            1
        );
    }
}
