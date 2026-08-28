package dev.gustavopere.rpgskilltree.compendium.entity;

import java.util.Map;

/** Typed, bounded special-case facts for one known entity family. */
public record EntityVariantSnapshot(
    String family,
    Map<String, String> textFacts,
    Map<String, Long> numericFacts,
    Map<String, Boolean> booleanFacts
) {
    public EntityVariantSnapshot {
        if (family == null || family.trim().isEmpty()) {
            throw new IllegalArgumentException("family must not be blank");
        }
        family = family.trim();
        textFacts = Map.copyOf(textFacts == null ? Map.of() : textFacts);
        numericFacts = Map.copyOf(numericFacts == null ? Map.of() : numericFacts);
        booleanFacts = Map.copyOf(booleanFacts == null ? Map.of() : booleanFacts);
        validateKeys(textFacts, "textFacts");
        validateKeys(numericFacts, "numericFacts");
        validateKeys(booleanFacts, "booleanFacts");
    }

    private static void validateKeys(Map<String, ?> facts, String label) {
        for (Map.Entry<String, ?> entry : facts.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isBlank()) {
                throw new IllegalArgumentException(label + " contains a blank key");
            }
            if (entry.getValue() == null) {
                throw new IllegalArgumentException(label + " contains a null value");
            }
        }
    }
}
