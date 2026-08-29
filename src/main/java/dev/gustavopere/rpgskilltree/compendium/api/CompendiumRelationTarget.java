package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;
import java.util.regex.Pattern;

public record CompendiumRelationTarget(
    CompendiumRelationTargetKind kind,
    String resourceLocation,
    CompendiumEntryId entryId
) {
    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern PATH = Pattern.compile("[a-z0-9/._-]+");

    public CompendiumRelationTarget {
        Objects.requireNonNull(kind, "kind");
        if (kind == CompendiumRelationTargetKind.ENTRY) {
            Objects.requireNonNull(entryId, "entryId");
            if (resourceLocation != null) throw new IllegalArgumentException("ENTRY relation target must not define resourceLocation");
        } else {
            if (entryId != null) throw new IllegalArgumentException(kind + " relation target must not define entryId");
            resourceLocation = normalizeResourceLocation(resourceLocation);
        }
    }

    public static CompendiumRelationTarget entry(CompendiumEntryId entryId) {
        return new CompendiumRelationTarget(CompendiumRelationTargetKind.ENTRY, null, entryId);
    }

    public static CompendiumRelationTarget item(String resourceLocation) {
        return resource(CompendiumRelationTargetKind.ITEM, resourceLocation);
    }

    public static CompendiumRelationTarget itemTag(String resourceLocation) {
        return resource(CompendiumRelationTargetKind.ITEM_TAG, resourceLocation);
    }

    public static CompendiumRelationTarget block(String resourceLocation) {
        return resource(CompendiumRelationTargetKind.BLOCK, resourceLocation);
    }

    public static CompendiumRelationTarget blockTag(String resourceLocation) {
        return resource(CompendiumRelationTargetKind.BLOCK_TAG, resourceLocation);
    }

    public static CompendiumRelationTarget resource(CompendiumRelationTargetKind kind, String resourceLocation) {
        if (kind == CompendiumRelationTargetKind.ENTRY) throw new IllegalArgumentException("ENTRY target requires CompendiumEntryId");
        return new CompendiumRelationTarget(kind, resourceLocation, null);
    }

    public String serializedTarget() {
        return kind.name() + "|" + (kind == CompendiumRelationTargetKind.ENTRY ? entryId.serializedId() : resourceLocation);
    }

    private static String normalizeResourceLocation(String value) {
        if (value == null) throw new IllegalArgumentException("resource target must not be null");
        String normalized = value.trim();
        int separator = normalized.indexOf(':');
        if (separator <= 0 || separator == normalized.length() - 1 || normalized.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException("invalid resource target: " + value);
        }
        String namespace = normalized.substring(0, separator);
        String path = normalized.substring(separator + 1);
        if (!NAMESPACE.matcher(namespace).matches() || !PATH.matcher(path).matches()) {
            throw new IllegalArgumentException("invalid resource target: " + value);
        }
        return normalized;
    }
}
