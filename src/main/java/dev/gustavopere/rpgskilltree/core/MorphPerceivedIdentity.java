package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Species/faction/physiology view exposed to ecological adapters while morphed. */
public record MorphPerceivedIdentity(
    String speciesId,
    MorphFormCategory category,
    Set<String> factions,
    Set<String> traits
) {
    public MorphPerceivedIdentity {
        Objects.requireNonNull(speciesId);
        Objects.requireNonNull(category);
        Objects.requireNonNull(factions);
        Objects.requireNonNull(traits);
        if (speciesId.isBlank()) throw new IllegalArgumentException("speciesId must not be blank");
        factions = copyNonBlank(factions, "factions");
        traits = copyNonBlank(traits, "traits");
    }

    private static Set<String> copyNonBlank(Set<String> values, String field) {
        for (String value : values) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(field + " contains blank value");
            }
        }
        return Set.copyOf(values);
    }
}
