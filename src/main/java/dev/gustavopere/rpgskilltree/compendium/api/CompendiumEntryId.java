package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;
import java.util.regex.Pattern;

public record CompendiumEntryId(CompendiumEntryKind kind, String resourceLocation) {
    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern PATH = Pattern.compile("[a-z0-9/._-]+");

    public CompendiumEntryId {
        Objects.requireNonNull(kind, "kind");
        resourceLocation = normalizeResourceLocation(resourceLocation);
    }

    public static CompendiumEntryId of(CompendiumEntryKind kind, String resourceLocation) {
        return new CompendiumEntryId(kind, resourceLocation);
    }

    public static CompendiumEntryId parse(String serializedId) {
        if (serializedId == null) throw new IllegalArgumentException("serializedId must not be null");
        String normalized = serializedId.trim();
        int separator = normalized.indexOf('|');
        if (separator <= 0 || separator == normalized.length() - 1 || normalized.indexOf('|', separator + 1) >= 0) {
            throw new IllegalArgumentException("invalid serialized compendium entry id: " + serializedId);
        }
        String kindName = normalized.substring(0, separator);
        String resourceLocation = normalized.substring(separator + 1);
        final CompendiumEntryKind kind;
        try {
            kind = CompendiumEntryKind.valueOf(kindName);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown compendium entry kind: " + kindName, exception);
        }
        return of(kind, resourceLocation);
    }

    public String namespace() {
        return resourceLocation.substring(0, resourceLocation.indexOf(':'));
    }

    public String path() {
        return resourceLocation.substring(resourceLocation.indexOf(':') + 1);
    }

    public String serializedId() {
        return kind.name() + "|" + resourceLocation;
    }

    private static String normalizeResourceLocation(String value) {
        if (value == null) throw new IllegalArgumentException("resourceLocation must not be null");
        String normalized = value.trim();
        int separator = normalized.indexOf(':');
        if (separator <= 0 || separator == normalized.length() - 1 || normalized.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException("invalid resource location: " + value);
        }
        String namespace = normalized.substring(0, separator);
        String path = normalized.substring(separator + 1);
        if (!NAMESPACE.matcher(namespace).matches() || !PATH.matcher(path).matches()) {
            throw new IllegalArgumentException("invalid resource location: " + value);
        }
        return normalized;
    }
}
