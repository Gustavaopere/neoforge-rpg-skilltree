package dev.gustavopere.rpgskilltree.compendium.integration.flora;

import dev.gustavopere.rpgskilltree.compendium.flora.FloraAdapterContribution;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraKind;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Optional Dynamic Trees bridge with no compile-time dependency on Dynamic Trees or addons. */
public final class DynamicTreesFloraAdapter {
    private DynamicTreesFloraAdapter() {}

    public static Optional<FloraAdapterContribution> enrich(
        Set<String> loadedMods,
        String resourceId,
        Map<String, String> stableMetadata
    ) {
        if (!loadedMods.contains("dynamictrees")) return Optional.empty();
        if (resourceId == null || resourceId.indexOf(':') <= 0) return Optional.empty();
        String familyId = stableMetadata.get("family_id");
        if (familyId == null || familyId.isBlank()) return Optional.empty();

        LinkedHashMap<String, String> metadata = new LinkedHashMap<>();
        stableMetadata.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> metadata.put(entry.getKey(), entry.getValue()));
        return Optional.of(new FloraAdapterContribution(
            FloraKind.TREE_COMPONENT,
            Set.of("dynamic_tree"),
            metadata
        ));
    }
}
