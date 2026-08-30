package dev.gustavopere.rpgskilltree.compendium.editorial;

import java.util.List;
import java.util.Objects;

public record CompendiumEditorialBlock(String text, List<CompendiumEditorialSource> sources) {
    public CompendiumEditorialBlock {
        text = requireText(text, "text");
        sources = List.copyOf(Objects.requireNonNull(sources, "sources"));
        if (sources.isEmpty()) throw new IllegalArgumentException("sources must not be empty");
        for (CompendiumEditorialSource source : sources) Objects.requireNonNull(source, "source");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
