package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/** Auditable registry-to-catalog coverage invariant for entity pages. */
public record EntityCatalogCoverage(
    Set<String> registryIds,
    Set<String> catalogIds,
    Set<String> missingRegistryIds,
    Set<String> unexpectedCatalogIds
) {
    public EntityCatalogCoverage {
        registryIds = Set.copyOf(registryIds);
        catalogIds = Set.copyOf(catalogIds);
        missingRegistryIds = Set.copyOf(missingRegistryIds);
        unexpectedCatalogIds = Set.copyOf(unexpectedCatalogIds);
    }

    public static EntityCatalogCoverage compare(
        Collection<String> registryIds,
        Collection<CompendiumEntryId> catalogEntryIds
    ) {
        TreeSet<String> expected = new TreeSet<>();
        if (registryIds != null) {
            for (String id : registryIds) {
                if (id == null || id.isBlank()) throw new IllegalArgumentException("registry id must not be blank");
                CompendiumEntryId.of(CompendiumEntryKind.ENTITY, id);
                expected.add(id.trim());
            }
        }

        TreeSet<String> actual = new TreeSet<>();
        if (catalogEntryIds != null) {
            for (CompendiumEntryId id : catalogEntryIds) {
                if (id != null && id.kind() == CompendiumEntryKind.ENTITY) {
                    actual.add(id.resourceLocation());
                }
            }
        }

        LinkedHashSet<String> missing = new LinkedHashSet<>(expected);
        missing.removeAll(actual);
        LinkedHashSet<String> unexpected = new LinkedHashSet<>(actual);
        unexpected.removeAll(expected);
        return new EntityCatalogCoverage(expected, actual, missing, unexpected);
    }

    public boolean complete() {
        return missingRegistryIds.isEmpty();
    }
}
