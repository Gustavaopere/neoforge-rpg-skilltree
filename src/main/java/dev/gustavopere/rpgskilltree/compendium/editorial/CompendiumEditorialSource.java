package dev.gustavopere.rpgskilltree.compendium.editorial;

import java.util.Objects;

public record CompendiumEditorialSource(EditorialSourceType type, String ref, String note) {
    public CompendiumEditorialSource {
        Objects.requireNonNull(type, "type");
        ref = requireText(ref, "ref");
        if (note != null) note = requireText(note, "note");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
