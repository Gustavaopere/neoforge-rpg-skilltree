package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Objects;

public record ModpackModEntry(
    String filename,
    String modId,
    String displayName,
    String runtimeVersion,
    String publishedVersion,
    boolean topLevel,
    String parentModId
) {
    public ModpackModEntry {
        filename = require(filename, "filename");
        modId = require(modId, "modId");
        displayName = displayName == null || displayName.isBlank() ? modId : displayName.trim();
        runtimeVersion = runtimeVersion == null ? "" : runtimeVersion.trim();
        publishedVersion = publishedVersion == null || publishedVersion.isBlank() ? null : publishedVersion.trim();
        parentModId = parentModId == null || parentModId.isBlank() ? null : parentModId.trim();
        if (topLevel && parentModId != null) {
            throw new IllegalArgumentException("top-level mod cannot declare parentModId: " + modId);
        }
        if (!topLevel && parentModId == null) {
            throw new IllegalArgumentException("embedded dependency requires parentModId: " + modId);
        }
    }

    public static ModpackModEntry topLevel(
        String filename,
        String modId,
        String displayName,
        String runtimeVersion,
        String publishedVersion
    ) {
        return new ModpackModEntry(filename, modId, displayName, runtimeVersion, publishedVersion, true, null);
    }

    public static ModpackModEntry embedded(
        String filename,
        String modId,
        String displayName,
        String runtimeVersion,
        String parentModId
    ) {
        return new ModpackModEntry(filename, modId, displayName, runtimeVersion, null, false, parentModId);
    }

    private static String require(String value, String name) {
        Objects.requireNonNull(value, name);
        String normalized = value.trim();
        if (normalized.isEmpty()) throw new IllegalArgumentException(name + " cannot be blank");
        return normalized;
    }
}
