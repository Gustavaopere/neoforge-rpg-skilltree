package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Objects;

public final class CompendiumCatalog {
    private volatile CompendiumCatalogSnapshot snapshot = CompendiumCatalogSnapshot.empty();

    public CompendiumCatalogSnapshot snapshot() {
        return snapshot;
    }

    public CompendiumCatalogSnapshot publish(CompendiumCatalogBuilder builder) {
        Objects.requireNonNull(builder, "builder");
        CompendiumCatalogSnapshot validated = builder.build();
        snapshot = validated;
        return validated;
    }
}
