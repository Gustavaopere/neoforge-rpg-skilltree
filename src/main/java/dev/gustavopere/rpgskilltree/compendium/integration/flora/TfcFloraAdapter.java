package dev.gustavopere.rpgskilltree.compendium.integration.flora;

import dev.gustavopere.rpgskilltree.compendium.flora.FloraAdapterContribution;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraKind;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Optional TFC bridge with no compile-time TerraFirmaCraft dependency. */
public final class TfcFloraAdapter {
    private TfcFloraAdapter() {}

    public static Optional<FloraAdapterContribution> enrich(
        Set<String> loadedMods,
        String resourceId,
        Map<String, String> stableMetadata
    ) {
        if (!loadedMods.contains("tfc") || !resourceId.startsWith("tfc:")) return Optional.empty();
        String kindToken = stableMetadata.get("kind");
        FloraKind kind = switch (kindToken == null ? "" : kindToken.trim()) {
            case "crop" -> FloraKind.CROP;
            case "flora" -> FloraKind.FLORA;
            case "fungus" -> FloraKind.FUNGUS;
            case "aquatic_flora" -> FloraKind.AQUATIC_FLORA;
            case "tree", "tree_component" -> FloraKind.TREE_COMPONENT;
            default -> null;
        };
        if (kind == null) return Optional.empty();

        LinkedHashMap<String, String> metadata = new LinkedHashMap<>();
        stableMetadata.entrySet().stream()
            .filter(entry -> !entry.getKey().equals("kind"))
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> metadata.put(entry.getKey(), entry.getValue()));
        return Optional.of(new FloraAdapterContribution(kind, Set.of("tfc"), metadata));
    }
}
