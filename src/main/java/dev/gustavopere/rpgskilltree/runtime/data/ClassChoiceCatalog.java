package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.ClassChoiceDefinition;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ClassChoiceCatalog {
    private static volatile List<ClassChoiceDefinition> definitions = List.of();

    private ClassChoiceCatalog() {}

    public static synchronized void replace(Collection<ClassChoiceDefinition> next) {
        Objects.requireNonNull(next);
        var sorted = next.stream().sorted(Comparator.comparing(ClassChoiceDefinition::choiceId)).toList();
        long unique = sorted.stream().map(ClassChoiceDefinition::choiceId).distinct().count();
        if (unique != sorted.size()) throw new IllegalArgumentException("duplicate class choice id");
        definitions = List.copyOf(sorted);
    }

    public static Optional<ClassChoiceDefinition> definition(String choiceId) {
        Objects.requireNonNull(choiceId);
        return definitions.stream().filter(definition -> definition.choiceId().equals(choiceId)).findFirst();
    }

    public static List<ClassChoiceDefinition> definitions() {
        return definitions;
    }
}
