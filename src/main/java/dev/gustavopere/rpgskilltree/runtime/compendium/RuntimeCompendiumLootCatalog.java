package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.provider.loot.CompendiumLootSnapshot;
import java.util.Objects;

public final class RuntimeCompendiumLootCatalog {
    private static volatile CompendiumLootSnapshot snapshot = CompendiumLootSnapshot.empty();

    private RuntimeCompendiumLootCatalog() {}

    public static CompendiumLootSnapshot snapshot() {
        return snapshot;
    }

    public static void publish(CompendiumLootSnapshot candidate) {
        snapshot = Objects.requireNonNull(candidate, "candidate");
    }
}
