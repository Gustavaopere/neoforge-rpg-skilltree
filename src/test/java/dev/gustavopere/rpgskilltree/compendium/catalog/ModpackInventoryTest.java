package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class ModpackInventoryTest {
    public static void main(String[] args) {
        embeddedDependencyIsNotPromotedToTopLevel();
        driftReportsModAndRegistryChanges();
        snapshotOwnsImmutableCollections();
        System.out.println("ModpackInventoryTest: PASS");
    }

    private static void embeddedDependencyIsNotPromotedToTopLevel() {
        ModpackSnapshot snapshot = new ModpackSnapshot(
            "sha256:fixture",
            List.of(
                ModpackModEntry.topLevel("neoforge-21.1.248 (modloader)", "neoforge", "NeoForge", "21.1.248", null),
                ModpackModEntry.topLevel("appliedenergistics2-19.2.17.jar", "ae2", "Applied Energistics 2", "19.2.17", null)
            ),
            List.of(
                ModpackModEntry.embedded("META-INF/jarjar/GrandPower-3.0.2.jar", "grandpower", "Grand Power", "3.0.2", "ae2")
            ),
            List.of()
        );

        eq(2, snapshot.topLevelMods().size());
        eq(1, snapshot.embeddedDependencies().size());
        eq("grandpower", snapshot.embeddedDependencies().getFirst().modId());
        eq("ae2", snapshot.embeddedDependencies().getFirst().parentModId());
    }

    private static void driftReportsModAndRegistryChanges() {
        RegistryInventoryEntry zombie = entry(InventoryKind.ENTITY, "minecraft:zombie");
        RegistryInventoryEntry grizzly = entry(InventoryKind.ENTITY, "alexsmobs:grizzly_bear");
        RegistryInventoryEntry nucleeper = entry(InventoryKind.ENTITY, "alexscaves:nucleeper");

        ModpackSnapshot before = new ModpackSnapshot(
            "sha256:before",
            List.of(
                ModpackModEntry.topLevel("a.jar", "a", "A", "1", null),
                ModpackModEntry.topLevel("b.jar", "b", "B", "1", null)
            ),
            List.of(),
            List.of(zombie, grizzly)
        );
        ModpackSnapshot after = new ModpackSnapshot(
            "sha256:after",
            List.of(
                ModpackModEntry.topLevel("a.jar", "a", "A", "1", null),
                ModpackModEntry.topLevel("c.jar", "c", "C", "1", null)
            ),
            List.of(),
            List.of(zombie, nucleeper)
        );

        InventoryDriftReport drift = InventoryDrift.compare(before, after);
        eq(Set.of("c"), drift.addedMods());
        eq(Set.of("b"), drift.removedMods());
        eq(Set.of("ENTITY|alexscaves:nucleeper"), drift.addedRegistryEntries());
        eq(Set.of("ENTITY|alexsmobs:grizzly_bear"), drift.removedRegistryEntries());
    }

    private static void snapshotOwnsImmutableCollections() {
        ModpackSnapshot snapshot = new ModpackSnapshot(
            "sha256:immutable",
            List.of(ModpackModEntry.topLevel("a.jar", "a", "A", "1", null)),
            List.of(),
            List.of(entry(InventoryKind.BIOME, "minecraft:plains"))
        );
        expect(UnsupportedOperationException.class, () -> snapshot.topLevelMods().clear());
        expect(UnsupportedOperationException.class, () -> snapshot.registryEntries().clear());
    }

    private static RegistryInventoryEntry entry(InventoryKind kind, String id) {
        String namespace = id.substring(0, id.indexOf(':'));
        return new RegistryInventoryEntry(kind, id, namespace, "translation." + id.replace(':', '.'), namespace, "fixture", true);
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
