package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Explicit ecological relationships owned by one observer faction. */
public record MorphFactionRelations(
    Set<String> allies,
    Set<String> enemies,
    Set<String> fears
) {
    public MorphFactionRelations {
        Objects.requireNonNull(allies);
        Objects.requireNonNull(enemies);
        Objects.requireNonNull(fears);
        allies = normalized(allies, "allies");
        enemies = normalized(enemies, "enemies");
        fears = normalized(fears, "fears");
        rejectOverlap(allies, enemies, "allies", "enemies");
        rejectOverlap(allies, fears, "allies", "fears");
        rejectOverlap(enemies, fears, "enemies", "fears");
    }

    public static MorphFactionRelations neutral() {
        return new MorphFactionRelations(Set.of(), Set.of(), Set.of());
    }

    private static Set<String> normalized(Set<String> values, String field) {
        Set<String> copy = new HashSet<>();
        for (String value : values) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(field + " contains a blank faction id");
            }
            if (!copy.add(value)) {
                throw new IllegalArgumentException(field + " contains duplicate faction " + value);
            }
        }
        return Set.copyOf(copy);
    }

    private static void rejectOverlap(Set<String> left, Set<String> right, String leftName, String rightName) {
        Set<String> overlap = new HashSet<>(left);
        overlap.retainAll(right);
        if (!overlap.isEmpty()) {
            throw new IllegalArgumentException(leftName + " and " + rightName + " overlap: " + overlap);
        }
    }
}
