package dev.gustavopere.rpgskilltree.compendium.editorial;

import java.util.Objects;
import java.util.regex.Pattern;

public record CompendiumEditorialSection(String sectionId, CompendiumEditorialBlock block) {
    private static final Pattern SECTION_ID = Pattern.compile("[a-z0-9_][a-z0-9_.-]*");

    public CompendiumEditorialSection {
        sectionId = requireSectionId(sectionId);
        Objects.requireNonNull(block, "block");
    }

    private static String requireSectionId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("sectionId must not be blank");
        }
        String normalized = value.trim();
        if (!SECTION_ID.matcher(normalized).matches()) {
            throw new IllegalArgumentException("invalid editorial section id: " + value);
        }
        return normalized;
    }
}
