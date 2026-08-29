package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientEntry;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientSnapshot;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ClientCompendiumStateJUnitTest {
    @AfterEach
    void clearState() {
        ClientCompendiumState.clear();
    }

    @Test
    void startsAndClearsToAnEmptySnapshot() {
        ClientCompendiumState.clear();

        assertTrue(ClientCompendiumState.get().entries().isEmpty());

        ClientCompendiumState.install(snapshot("minecraft:wolf", "Lobo"));
        assertEquals(1, ClientCompendiumState.get().entries().size());

        ClientCompendiumState.clear();
        assertTrue(ClientCompendiumState.get().entries().isEmpty());
    }

    @Test
    void installAtomicallyReplacesTheVisibleSnapshot() {
        CompendiumClientSnapshot first = snapshot("minecraft:wolf", "Lobo");
        CompendiumClientSnapshot second = snapshot("minecraft:fox", "Raposa");

        ClientCompendiumState.install(first);
        assertSame(first, ClientCompendiumState.get());

        ClientCompendiumState.install(second);
        assertSame(second, ClientCompendiumState.get());
    }

    @Test
    void eachBrowserRequestGetsIndependentNavigationState() {
        ClientCompendiumState.install(new CompendiumClientSnapshot(
            List.of(
                entry("minecraft:wolf", "Lobo"),
                entry("minecraft:fox", "Raposa")
            ),
            List.of()
        ));

        var first = ClientCompendiumState.newBrowserModel();
        var second = ClientCompendiumState.newBrowserModel();
        assertNotSame(first, second);

        first.setQuery("lobo");
        assertEquals(1, first.totalMatches());
        assertEquals(2, second.totalMatches());
        assertEquals("", second.query());
    }

    private static CompendiumClientSnapshot snapshot(String id, String displayName) {
        return new CompendiumClientSnapshot(List.of(entry(id, displayName)), List.of());
    }

    private static CompendiumClientEntry entry(String id, String displayName) {
        return new CompendiumClientEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, id),
            displayName,
            "minecraft",
            Set.of(),
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:forest"),
            true,
            false,
            true,
            true,
            false,
            CoverageState.AUTO
        );
    }
}
