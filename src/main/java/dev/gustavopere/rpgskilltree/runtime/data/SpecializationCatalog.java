package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.SpecializationDefinition;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Runtime catalogue for data-driven specialization eligibility definitions. */
public final class SpecializationCatalog {
    private static volatile List<SpecializationDefinition> definitions = List.of();

    private SpecializationCatalog() {}

    public static synchronized void replace(Collection<SpecializationDefinition> next) {
        Objects.requireNonNull(next);
        var sorted = next.stream()
            .sorted(Comparator.comparing(SpecializationDefinition::specializationId))
            .toList();
        long unique = sorted.stream().map(SpecializationDefinition::specializationId).distinct().count();
        if (unique != sorted.size()) {
            throw new IllegalArgumentException("duplicate specialization definition id");
        }
        definitions = List.copyOf(sorted);
    }

    public static List<SpecializationDefinition> definitions() {
        return definitions;
    }

    public static Optional<SpecializationDefinition> definition(String id) {
        Objects.requireNonNull(id);
        return definitions.stream()
            .filter(definition -> definition.specializationId().equals(id))
            .findFirst();
    }

    public static int size() {
        return definitions.size();
    }
}
