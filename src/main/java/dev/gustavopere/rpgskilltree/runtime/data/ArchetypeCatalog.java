package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.ArchetypeDefinition;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Runtime catalogue for data-driven emergent class/archetype definitions. */
public final class ArchetypeCatalog {
    private static volatile List<ArchetypeDefinition> definitions = List.of();

    private ArchetypeCatalog() {}

    public static synchronized void replace(Collection<ArchetypeDefinition> next) {
        Objects.requireNonNull(next);
        var sorted = next.stream()
            .sorted(Comparator.comparing(ArchetypeDefinition::id))
            .toList();
        long unique = sorted.stream().map(ArchetypeDefinition::id).distinct().count();
        if (unique != sorted.size()) {
            throw new IllegalArgumentException("duplicate archetype definition id");
        }
        definitions = List.copyOf(sorted);
    }

    public static List<ArchetypeDefinition> definitions() {
        return definitions;
    }

    public static Optional<ArchetypeDefinition> definition(String id) {
        Objects.requireNonNull(id);
        return definitions.stream().filter(definition -> definition.id().equals(id)).findFirst();
    }

    public static int size() {
        return definitions.size();
    }
}
