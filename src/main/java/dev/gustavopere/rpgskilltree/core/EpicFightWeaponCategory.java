package dev.gustavopere.rpgskilltree.core;

import java.util.Locale;
import java.util.Objects;

/** Normalizes Epic Fight and addon weapon-category names into stable mastery-lane suffixes. */
public final class EpicFightWeaponCategory {
    private EpicFightWeaponCategory() {}

    public static String normalize(String raw) {
        Objects.requireNonNull(raw);
        String normalized = raw.trim().toLowerCase(Locale.ROOT)
            .replace(':', '/')
            .replaceAll("[^a-z0-9/_-]+", "_")
            .replaceAll("_+", "_");
        if (normalized.isBlank()) throw new IllegalArgumentException("blank weapon category");
        return normalized;
    }
}
