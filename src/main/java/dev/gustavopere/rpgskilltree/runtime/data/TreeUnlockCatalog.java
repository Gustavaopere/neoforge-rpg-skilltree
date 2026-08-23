package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.TreeUnlockDefinition;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Runtime catalogue for data-driven specialist-tree unlock gates. */
public final class TreeUnlockCatalog {
    private static volatile List<TreeUnlockDefinition> definitions = List.of();

    private TreeUnlockCatalog() {}

    public static synchronized void replace(Collection<TreeUnlockDefinition> next) {
        Objects.requireNonNull(next);
        var sorted = next.stream()
            .sorted(Comparator.comparing(TreeUnlockDefinition::treeId))
            .toList();
        long unique = sorted.stream().map(TreeUnlockDefinition::treeId).distinct().count();
        if (unique != sorted.size()) {
            throw new IllegalArgumentException("duplicate tree unlock definition id");
        }
        definitions = List.copyOf(sorted);
    }

    public static List<TreeUnlockDefinition> definitions() {
        return definitions;
    }

    public static Optional<TreeUnlockDefinition> definition(String treeId) {
        Objects.requireNonNull(treeId);
        return definitions.stream()
            .filter(definition -> definition.treeId().equals(treeId))
            .findFirst();
    }

    public static int size() {
        return definitions.size();
    }
}
