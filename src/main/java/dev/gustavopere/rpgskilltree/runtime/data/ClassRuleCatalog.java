package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.ClassUnlockDefinition;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ClassRuleCatalog {
    private static volatile List<ClassUnlockDefinition> definitions = List.of();

    private ClassRuleCatalog() {}

    public static synchronized void replace(Collection<ClassUnlockDefinition> next) {
        Objects.requireNonNull(next);
        var sorted = next.stream()
            .sorted(Comparator.comparing(ClassUnlockDefinition::classId))
            .toList();
        long unique = sorted.stream().map(ClassUnlockDefinition::classId).distinct().count();
        if (unique != sorted.size()) throw new IllegalArgumentException("duplicate class definition id");
        definitions = List.copyOf(sorted);
    }

    public static List<ClassUnlockDefinition> definitions() {
        return definitions;
    }

    public static Optional<ClassUnlockDefinition> definition(String classId) {
        Objects.requireNonNull(classId);
        return definitions.stream().filter(definition -> definition.classId().equals(classId)).findFirst();
    }

    public static int size() {
        return definitions.size();
    }
}
