package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Semantic migrations for persisted progression state.
 *
 * <p>Binary-format migrations remain controlled by {@link ProgressionStateCodec}.
 * Semantic migrations are intentionally idempotent so they can normalize an old
 * identity even when the serialized layout itself did not change.</p>
 *
 * <p>Migrations must preserve player-earned knowledge: mastery, discoveries,
 * boss credit, passive investment and character XP are not reset here.</p>
 */
public final class ProgressionStateMigrations {
    private ProgressionStateMigrations() {}

    /**
     * Alpha-era identities that were temporarily modeled as classes but are
     * specializations in the current master design. Keeping the same stable id
     * prevents save data from losing identity during the category migration.
     */
    private static final Map<String, String> LEGACY_CLASS_SPECIALIZATIONS = Map.of(
        "industrialist", "industrialist",
        "logistician", "logistician",
        "prospector", "prospector"
    );

    public static ProgressionState migrate(ProgressionState state, int sourceFormatVersion) {
        Objects.requireNonNull(state);
        if (sourceFormatVersion < 1 || sourceFormatVersion > ProgressionStateCodec.CURRENT_VERSION) {
            throw new IllegalArgumentException("unsupported progression state version: " + sourceFormatVersion);
        }
        // This migration changes meaning, not bytes. It is safe and intentional
        // to run for every supported format version until all legacy identities
        // have naturally disappeared from persisted saves.
        return reclassifyLegacyClasses(state);
    }

    static ProgressionState reclassifyLegacyClasses(ProgressionState state) {
        ClassProgressionState classes = state.classProgression();
        SpecializationProgressionState specializations = state.specializations();
        boolean changed = false;

        for (Map.Entry<String, String> entry : LEGACY_CLASS_SPECIALIZATIONS.entrySet()) {
            if (!classes.isUnlocked(entry.getKey())) continue;
            classes = classes.without(entry.getKey());
            specializations = specializations.unlock(entry.getValue());
            changed = true;
        }

        if (!changed) return state;
        return state.withClassProgression(classes).withSpecializations(specializations);
    }

    public static Map<String, String> legacyClassSpecializations() {
        return Map.copyOf(new LinkedHashMap<>(LEGACY_CLASS_SPECIALIZATIONS));
    }
}
