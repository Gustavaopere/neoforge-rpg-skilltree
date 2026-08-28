package dev.gustavopere.rpgskilltree.compendium.flora;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Deterministic classifier output. A null kind means fail-closed/unclassified. */
public record FloraClassification(
    FloraKind kind,
    Set<String> categories,
    boolean ambiguous,
    boolean ignored,
    List<String> diagnostics
) {
    public FloraClassification {
        Objects.requireNonNull(categories, "categories");
        Objects.requireNonNull(diagnostics, "diagnostics");
        categories = Set.copyOf(categories);
        diagnostics = List.copyOf(diagnostics);
        if (kind == null && !categories.isEmpty()) {
            throw new IllegalArgumentException("unclassified result cannot expose botanical categories");
        }
        if (ignored && kind != null) {
            throw new IllegalArgumentException("ignored result cannot be classified");
        }
        if (ambiguous && kind != null) {
            throw new IllegalArgumentException("ambiguous result cannot choose a kind");
        }
    }

    public boolean classified() {
        return kind != null && !ambiguous && !ignored;
    }

    public static FloraClassification classified(FloraKind kind, List<String> diagnostics) {
        Objects.requireNonNull(kind, "kind");
        return new FloraClassification(kind, kind.defaultCategories(), false, false, diagnostics);
    }

    public static FloraClassification unknown(List<String> diagnostics) {
        return new FloraClassification(null, Set.of(), false, false, diagnostics);
    }

    public static FloraClassification ambiguous(List<String> diagnostics) {
        return new FloraClassification(null, Set.of(), true, false, diagnostics);
    }

    public static FloraClassification ignored(List<String> diagnostics) {
        return new FloraClassification(null, Set.of(), false, true, diagnostics);
    }
}
