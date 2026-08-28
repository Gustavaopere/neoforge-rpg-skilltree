package dev.gustavopere.rpgskilltree.compendium.flora;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Canonical species grouping. One role maps to one component; exact duplicates are harmless. */
public record TreeSpeciesDescriptor(
    String resourceLocation,
    String sourceModId,
    String translationKey,
    Set<String> categories,
    List<TreeComponent> components
) {
    public TreeSpeciesDescriptor {
        resourceLocation = resourceId(resourceLocation, "resourceLocation");
        sourceModId = token(sourceModId, "sourceModId");
        translationKey = token(translationKey, "translationKey");
        Objects.requireNonNull(categories, "categories");
        Objects.requireNonNull(components, "components");
        categories = categories.stream().map(value -> token(value, "category")).collect(java.util.stream.Collectors.toUnmodifiableSet());

        Map<TreeComponentRole, TreeComponent> byRole = new EnumMap<>(TreeComponentRole.class);
        for (TreeComponent component : components) {
            Objects.requireNonNull(component, "component");
            TreeComponent existing = byRole.putIfAbsent(component.role(), component);
            if (existing != null && !existing.resourceLocation().equals(component.resourceLocation())) {
                throw new IllegalArgumentException(
                    "conflicting tree component role " + component.role() + ": "
                        + existing.resourceLocation() + " vs " + component.resourceLocation()
                );
            }
        }
        if (byRole.isEmpty()) throw new IllegalArgumentException("tree species requires at least one component");
        List<TreeComponent> normalized = new ArrayList<>(byRole.values());
        normalized.sort(java.util.Comparator.comparing(TreeComponent::role).thenComparing(TreeComponent::resourceLocation));
        components = List.copyOf(normalized);
    }

    private static String resourceId(String value, String field) {
        String normalized = token(value, field);
        int colon = normalized.indexOf(':');
        if (colon <= 0 || colon == normalized.length() - 1) throw new IllegalArgumentException(field + " must be a resource id");
        return normalized;
    }

    private static String token(String value, String field) {
        Objects.requireNonNull(value, field);
        String normalized = value.trim();
        if (normalized.isEmpty()) throw new IllegalArgumentException(field + " must not be blank");
        return normalized;
    }
}
