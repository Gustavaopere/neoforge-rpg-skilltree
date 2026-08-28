package dev.gustavopere.rpgskilltree.compendium.flora;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Fail-soft enrichment returned by optional flora integrations. */
public record FloraAdapterContribution(
    FloraKind kind,
    Set<String> categories,
    Map<String, String> metadata
) {
    public FloraAdapterContribution {
        Objects.requireNonNull(kind, "kind");
        Objects.requireNonNull(categories, "categories");
        Objects.requireNonNull(metadata, "metadata");
        categories = categories.stream().map(FloraAdapterContribution::text).collect(java.util.stream.Collectors.toUnmodifiableSet());
        metadata = metadata.entrySet().stream().collect(java.util.stream.Collectors.toUnmodifiableMap(
            entry -> text(entry.getKey()), entry -> text(entry.getValue())
        ));
    }

    private static String text(String value) {
        Objects.requireNonNull(value, "text");
        String normalized = value.trim();
        if (normalized.isEmpty()) throw new IllegalArgumentException("text must not be blank");
        return normalized;
    }
}
