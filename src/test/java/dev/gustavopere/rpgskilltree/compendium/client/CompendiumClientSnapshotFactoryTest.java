package dev.gustavopere.rpgskilltree.compendium.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialBlock;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialContent;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSnapshot;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSource;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialAvailability;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialReviewStatus;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialSourceType;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class CompendiumClientSnapshotFactoryTest {
    private static final CompendiumEntryId WOLF = id(CompendiumEntryKind.ENTITY, "minecraft:wolf");
    private static final CompendiumEntryId TAIGA = id(CompendiumEntryKind.BIOME, "minecraft:taiga");
    private static final CompendiumEntryId SECRET = id(CompendiumEntryKind.STRUCTURE, "minecraft:stronghold");

    @Test
    void overlaysMatchingEditorialAndKeepsTechnicalFallbackForUncuratedPages() {
        CompendiumEditorialSnapshot editorial = CompendiumEditorialSnapshot.fromEntries(List.of(
            editorial(WOLF, "Lobo", List.of(TAIGA))
        ));

        CompendiumClientSnapshot snapshot = CompendiumClientSnapshotFactory.create(
            List.of(technical(WOLF, VisibilityPolicy.VISIBLE), technical(TAIGA, VisibilityPolicy.VISIBLE)),
            List.of(client(WOLF, true), client(TAIGA, true)),
            editorial,
            false
        );

        assertEquals("Lobo", snapshot.page(WOLF).orElseThrow().editorialContent().orElseThrow().title());
        assertTrue(snapshot.page(TAIGA).orElseThrow().editorialContent().isEmpty());
    }

    @Test
    void editorialReferencesAreFilteredToIdsAuthorizedInTheClientProjection() {
        CompendiumEditorialSnapshot editorial = CompendiumEditorialSnapshot.fromEntries(List.of(
            editorial(WOLF, "Lobo", List.of(TAIGA, SECRET))
        ));

        CompendiumClientSnapshot snapshot = CompendiumClientSnapshotFactory.create(
            List.of(
                technical(WOLF, VisibilityPolicy.VISIBLE),
                technical(TAIGA, VisibilityPolicy.VISIBLE),
                technical(SECRET, VisibilityPolicy.VISIBLE)
            ),
            List.of(client(WOLF, true), client(TAIGA, true)),
            editorial,
            false
        );

        assertEquals(
            List.of(TAIGA),
            snapshot.page(WOLF).orElseThrow().editorialContent().orElseThrow().references()
        );
        assertFalse(snapshot.page(SECRET).isPresent());
    }

    @Test
    void canonicalVisibilityStillControlsWhetherAClientPageExists() {
        CompendiumClientSnapshot snapshot = CompendiumClientSnapshotFactory.create(
            List.of(technical(WOLF, VisibilityPolicy.HIDE_ENTRY_UNTIL_DISCOVERED)),
            List.of(client(WOLF, false)),
            CompendiumEditorialSnapshot.fromEntries(List.of(editorial(WOLF, "Lobo", List.of()))),
            false
        );

        assertTrue(snapshot.page(WOLF).isEmpty());
        assertEquals(List.of(WOLF), snapshot.entries().stream().map(CompendiumClientEntry::id).toList());
    }

    @Test
    void clientProjectionWithoutCanonicalEntryIsRejectedFailClosed() {
        IllegalArgumentException failure = assertThrows(
            IllegalArgumentException.class,
            () -> CompendiumClientSnapshotFactory.create(
                List.of(),
                List.of(client(WOLF, true)),
                CompendiumEditorialSnapshot.empty(),
                false
            )
        );
        assertTrue(failure.getMessage().contains("canonical"));
    }

    private static CompendiumEntryId id(CompendiumEntryKind kind, String value) {
        return CompendiumEntryId.of(kind, value);
    }

    private static CompendiumEntry technical(CompendiumEntryId id, VisibilityPolicy visibility) {
        return new CompendiumEntry(
            id,
            id.namespace(),
            "compendium.test." + id.path().replace('/', '.'),
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            visibility,
            new CompendiumProvenance(FactSource.REGISTRY, "test"),
            1
        );
    }

    private static CompendiumClientEntry client(CompendiumEntryId id, boolean discovered) {
        return new CompendiumClientEntry(
            id,
            id.path(),
            id.namespace(),
            Set.of(),
            Set.of(),
            Set.of(),
            Set.of(),
            discovered,
            false,
            false,
            false,
            false,
            CoverageState.CURATED
        );
    }

    private static CompendiumEditorialContent editorial(
        CompendiumEntryId id,
        String title,
        List<CompendiumEntryId> references
    ) {
        return new CompendiumEditorialContent(
            id,
            title,
            new CompendiumEditorialBlock(
                "Resumo editorial verificado.",
                List.of(new CompendiumEditorialSource(EditorialSourceType.RUNTIME, id.resourceLocation(), null))
            ),
            List.of(),
            references,
            EditorialReviewStatus.REVIEWED,
            EditorialAvailability.RUNTIME,
            null
        );
    }
}
