package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalog;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalogBuilder;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalogSnapshot;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityCatalogCoverage;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

/** Server-authoritative, atomically published catalog of generic entity pages. */
public final class RuntimeCompendiumEntityCatalog {
    private static final CompendiumCatalog CATALOG = new CompendiumCatalog();

    private RuntimeCompendiumEntityCatalog() {}

    public static CompendiumCatalogSnapshot snapshot() {
        return CATALOG.snapshot();
    }

    public static CompendiumCatalogSnapshot publishFromRegistries() {
        List<CompendiumEntry> entries = RuntimeEntityCatalogCollector.collectEntries();
        CompendiumCatalogBuilder builder = new CompendiumCatalogBuilder();
        entries.forEach(builder::add);

        CompendiumCatalogSnapshot candidate = builder.build();
        EntityCatalogCoverage coverage = EntityCatalogCoverage.compare(
            BuiltInRegistries.ENTITY_TYPE.keySet().stream().map(ResourceLocation::toString).toList(),
            candidate.entries().stream().map(CompendiumEntry::id).toList()
        );
        if (!coverage.complete() || !coverage.unexpectedCatalogIds().isEmpty()) {
            throw new IllegalStateException(
                "Incomplete Compendium entity catalog: missing=" + coverage.missingRegistryIds()
                    + ", unexpected=" + coverage.unexpectedCatalogIds()
            );
        }

        return CATALOG.publish(builder);
    }
}
