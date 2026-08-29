package dev.gustavopere.rpgskilltree.compendium.world;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

final class WorldDescriptorSupport {
    private static final Pattern RESOURCE = Pattern.compile("[a-z0-9_.-]+:[a-z0-9/._-]+");
    private WorldDescriptorSupport() {}

    static String text(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }

    static String resource(String value, String field) {
        String normalized = text(value, field);
        if (!RESOURCE.matcher(normalized).matches()) throw new IllegalArgumentException("invalid " + field + ": " + value);
        return normalized;
    }

    static Set<String> resources(Set<String> values) {
        if (values == null || values.isEmpty()) return Set.of();
        LinkedHashSet<String> result = new LinkedHashSet<>();
        for (String value : values) result.add(resource(value, "resource id"));
        return Set.copyOf(result);
    }

    static Set<String> texts(Set<String> values) {
        if (values == null || values.isEmpty()) return Set.of();
        LinkedHashSet<String> result = new LinkedHashSet<>();
        for (String value : values) result.add(text(value, "text value"));
        return Set.copyOf(result);
    }
}
