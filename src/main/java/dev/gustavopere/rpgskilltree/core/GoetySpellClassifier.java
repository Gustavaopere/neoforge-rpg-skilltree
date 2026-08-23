package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/** Normalizes Goety spell types without depending on Goety classes in the core module. */
public final class GoetySpellClassifier {
    private static final List<String> TYPE_ORDER = List.of(
        "necromancy", "nether", "ill", "frost", "geomancy",
        "wind", "storm", "abyss", "wild", "void"
    );

    private GoetySpellClassifier() {}

    public static Set<String> classify(Collection<String> rawTypes, boolean summoning) {
        Objects.requireNonNull(rawTypes);
        LinkedHashSet<String> tags = new LinkedHashSet<>();
        for (String rawType : rawTypes) {
            if (rawType == null) continue;
            String normalized = normalize(rawType);
            if (!normalized.isBlank() && !normalized.equals("none")) tags.add(normalized);
        }
        if (summoning) tags.add("summoning");
        return Set.copyOf(tags);
    }

    public static String primaryDiscipline(Set<String> tags) {
        Objects.requireNonNull(tags);
        for (String type : TYPE_ORDER) if (tags.contains(type)) return type;
        if (tags.contains("summoning")) return "summoning";
        return "dark_arts";
    }

    private static String normalize(String rawType) {
        String normalized = rawType.trim().toLowerCase(Locale.ROOT)
            .replace(' ', '_')
            .replace('-', '_');
        int separator = normalized.indexOf(':');
        if (separator >= 0 && separator + 1 < normalized.length()) {
            normalized = normalized.substring(separator + 1);
        }
        return normalized;
    }
}
