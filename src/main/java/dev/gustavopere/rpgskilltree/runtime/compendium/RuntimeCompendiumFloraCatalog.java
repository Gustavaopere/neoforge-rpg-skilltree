package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalog;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalogBuilder;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalogSnapshot;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraCatalogCoverage;

/** Server-authoritative flora/tree/crop catalog, isolated from the entity catalog. */
public final class RuntimeCompendiumFloraCatalog {
    private static final CompendiumCatalog CATALOG = new CompendiumCatalog();

    private RuntimeCompendiumFloraCatalog() {}

    public static CompendiumCatalogSnapshot snapshot() {
        return CATALOG.snapshot();
    }

    public static CompendiumCatalogSnapshot publishFromRegistries() {
        RuntimeFloraCatalogCollector.CollectionResult collected = RuntimeFloraCatalogCollector.collect();
        CompendiumCatalogBuilder builder = new CompendiumCatalogBuilder();
        collected.entries().forEach(builder::add);

        CompendiumCatalogSnapshot candidate = builder.build();
        FloraCatalogCoverage coverage = FloraCatalogCoverage.compare(
            collected.expectedEntryIds(),
            candidate.entries().stream().map(CompendiumEntry::id).toList(),
            collected.ambiguousBlockIds()
        );
        if (!coverage.complete()) {
            throw new IllegalStateException(
                "Incomplete Compendium flora catalog: missing=" + coverage.missingEntryIds()
                    + ", unexpected=" + coverage.unexpectedEntryIds()
                    + ", ambiguous=" + coverage.ambiguousBlockIds()
            );
        }

        return CATALOG.publish(builder);
    }
}
