package dev.gustavopere.rpgskilltree.compendium.flora;

import java.util.Objects;
import java.util.Set;

/** Pure descriptor of stable evidence gathered by runtime adapters. */
public record FloraClassificationEvidence(
    String blockId,
    Set<String> tagIds,
    boolean cropClass,
    boolean saplingClass,
    boolean flowerClass,
    boolean fungusClass,
    boolean aquaticClass,
    boolean decorativeOnly,
    FloraKind explicitOverride,
    boolean explicitIgnore
) {
    public FloraClassificationEvidence {
        blockId = normalizeId(blockId);
        Objects.requireNonNull(tagIds, "tagIds");
        tagIds = tagIds.stream().map(FloraClassificationEvidence::normalizeId).collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    private static String normalizeId(String value) {
        Objects.requireNonNull(value, "resource id");
        String normalized = value.trim();
        if (normalized.isEmpty() || normalized.indexOf(':') <= 0 || normalized.endsWith(":")) {
            throw new IllegalArgumentException("invalid resource id: " + value);
        }
        return normalized;
    }
}
