package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialBlock;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialContent;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSnapshot;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSource;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialAvailability;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialReviewStatus;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialSourceType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class RuntimeCompendiumEditorialCatalogJUnitTest {
    private static final CompendiumEntryId WOLF_ID =
        CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf");
    private static final CompendiumEntryId FOX_ID =
        CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:fox");

    @BeforeEach
    void resetCatalog() {
        RuntimeCompendiumEditorialCatalog.resetForTests();
    }

    @Test
    void startsEmptyAndValidCandidatesReplaceTheWholeSnapshotAtomically() {
        assertTrue(RuntimeCompendiumEditorialCatalog.snapshot().entries().isEmpty());

        CompendiumEditorialSnapshot first = snapshot(wolfEditorial());
        RuntimeCompendiumEditorialCatalog.PublicationResult firstResult =
            RuntimeCompendiumEditorialCatalog.tryPublish(() -> first);
        assertTrue(firstResult.published());
        assertSame(first, firstResult.snapshot());
        assertEquals("", firstResult.diagnostic());
        assertSame(first, RuntimeCompendiumEditorialCatalog.snapshot());

        CompendiumEditorialSnapshot second = snapshot(foxEditorial());
        RuntimeCompendiumEditorialCatalog.PublicationResult secondResult =
            RuntimeCompendiumEditorialCatalog.tryPublish(() -> second);
        assertTrue(secondResult.published());
        assertSame(second, secondResult.snapshot());
        assertSame(second, RuntimeCompendiumEditorialCatalog.snapshot());
    }

    @Test
    void firstRecoverableValidationFailureKeepsTheEmptySnapshot() {
        RuntimeCompendiumEditorialCatalog.PublicationResult result =
            RuntimeCompendiumEditorialCatalog.tryPublish(() -> {
                throw new CompendiumEditorialValidationException("corpus editorial inválido");
            });

        assertFalse(result.published());
        assertSame(RuntimeCompendiumEditorialCatalog.snapshot(), result.snapshot());
        assertTrue(result.snapshot().entries().isEmpty());
        assertTrue(result.diagnostic().contains("corpus editorial inválido"));
    }

    @Test
    void recoverableValidationFailurePreservesTheLastGoodSnapshot() {
        CompendiumEditorialSnapshot lastGood = snapshot(wolfEditorial());
        assertTrue(RuntimeCompendiumEditorialCatalog.tryPublish(() -> lastGood).published());

        RuntimeCompendiumEditorialCatalog.PublicationResult rejected =
            RuntimeCompendiumEditorialCatalog.tryPublish(() -> {
                throw new CompendiumEditorialValidationException("referência editorial não resolvida");
            });

        assertFalse(rejected.published());
        assertSame(lastGood, rejected.snapshot());
        assertSame(lastGood, RuntimeCompendiumEditorialCatalog.snapshot());
        assertTrue(rejected.diagnostic().contains("referência editorial não resolvida"));
    }

    @Test
    void newServerLifecycleClearsThePreviousServersLastGoodSnapshot() {
        CompendiumEditorialSnapshot worldA = snapshot(wolfEditorial());
        assertTrue(RuntimeCompendiumEditorialCatalog.tryPublish(() -> worldA).published());
        assertSame(worldA, RuntimeCompendiumEditorialCatalog.snapshot());

        RuntimeCompendiumEditorialCatalog.beginServerLifecycle();
        assertTrue(RuntimeCompendiumEditorialCatalog.snapshot().entries().isEmpty());

        RuntimeCompendiumEditorialCatalog.PublicationResult rejected =
            RuntimeCompendiumEditorialCatalog.tryPublish(() -> {
                throw new CompendiumEditorialValidationException("corpus do mundo B inválido");
            });

        assertFalse(rejected.published());
        assertTrue(rejected.snapshot().entries().isEmpty());
        assertSame(RuntimeCompendiumEditorialCatalog.snapshot(), rejected.snapshot());
    }

    @Test
    void programmingFailureIsNotSwallowedAndCannotClobberTheLastGoodSnapshot() {
        CompendiumEditorialSnapshot lastGood = snapshot(wolfEditorial());
        assertTrue(RuntimeCompendiumEditorialCatalog.tryPublish(() -> lastGood).published());

        assertThrows(
            NullPointerException.class,
            () -> RuntimeCompendiumEditorialCatalog.tryPublish(() -> {
                throw new NullPointerException("programming bug");
            })
        );
        assertSame(lastGood, RuntimeCompendiumEditorialCatalog.snapshot());

        assertThrows(NullPointerException.class, () -> RuntimeCompendiumEditorialCatalog.tryPublish(() -> null));
        assertSame(lastGood, RuntimeCompendiumEditorialCatalog.snapshot());
    }

    private static CompendiumEditorialSnapshot snapshot(CompendiumEditorialContent content) {
        return CompendiumEditorialSnapshot.fromEntries(List.of(content));
    }

    private static CompendiumEditorialContent wolfEditorial() {
        return editorial(WOLF_ID, "Lobo");
    }

    private static CompendiumEditorialContent foxEditorial() {
        return editorial(FOX_ID, "Raposa");
    }

    private static CompendiumEditorialContent editorial(CompendiumEntryId id, String title) {
        return new CompendiumEditorialContent(
            id,
            title,
            new CompendiumEditorialBlock(
                "Resumo válido.",
                List.of(new CompendiumEditorialSource(
                    EditorialSourceType.RUNTIME,
                    "minecraft:entity_type/" + id.resourceLocation(),
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
}
