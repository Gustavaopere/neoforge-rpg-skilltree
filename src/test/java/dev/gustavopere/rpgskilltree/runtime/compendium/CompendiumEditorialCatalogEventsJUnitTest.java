package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class CompendiumEditorialCatalogEventsJUnitTest {
    @Test
    void technicalCatalogsAreConsolidatedDeterministically() {
        CompendiumEntry wolf = entry(CompendiumEntryKind.ENTITY, "minecraft:wolf");
        CompendiumEntry oak = entry(CompendiumEntryKind.TREE, "minecraft:oak");
        CompendiumEntry taiga = entry(CompendiumEntryKind.BIOME, "minecraft:taiga");

        List<CompendiumEntry> merged = CompendiumEditorialCatalogEvents.mergeTechnicalEntries(
            List.of(wolf),
            List.of(oak),
            List.of(taiga)
        );

        assertEquals(
            List.of("BIOME|minecraft:taiga", "ENTITY|minecraft:wolf", "TREE|minecraft:oak"),
            merged.stream().map(value -> value.id().serializedId()).toList()
        );
    }

    @Test
    void duplicateTechnicalEntryIdsAreRejectedInsteadOfSilentlyOverwriting() {
        CompendiumEntry first = entry(CompendiumEntryKind.ENTITY, "minecraft:wolf");
        CompendiumEntry duplicate = entry(CompendiumEntryKind.ENTITY, "minecraft:wolf");

        IllegalArgumentException failure = assertThrows(
            IllegalArgumentException.class,
            () -> CompendiumEditorialCatalogEvents.mergeTechnicalEntries(
                List.of(first),
                List.of(duplicate),
                List.of()
            )
        );
        assertTrue(failure.getMessage().contains("duplicate"));
        assertTrue(failure.getMessage().contains("ENTITY|minecraft:wolf"));
    }

    private static CompendiumEntry entry(CompendiumEntryKind kind, String resourceLocation) {
        CompendiumEntryId id = CompendiumEntryId.of(kind, resourceLocation);
        return new CompendiumEntry(
            id,
            id.namespace(),
            "compendium.test." + id.path().replace('/', '.'),
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
