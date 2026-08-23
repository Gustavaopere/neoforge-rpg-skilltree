package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Versioned semantic migrations for persisted progression state.
 *
 * <p>These migrations intentionally preserve player-earned knowledge. They may
 * reclassify an identity when the design model changes, but they must not erase
 * mastery, discoveries, boss credit, passive investment, or character XP.</p>
 */
public final class ProgressionStateMigrations {
    private ProgressionStateMigrations() {}

    private static final int CLASS_SPECIALIZATION_RECLASS_VERSION = 5;

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

    public static ProgressionState migrate(ProgressionState state, int sourceVersion) {
        Objects.requireNonNull(state);
        if (sourceVersion < 1 || sourceVersion > ProgressionStateCodec.CURRENT_VERSION) {
            throw new IllegalArgumentException("unsupported progression state version: " + sourceVersion);
        }
        ProgressionState current = state;
        if (sourceVersion < CLASS_SPECIALIZATION_RECLASS_VERSION) {
            current = reclassifyLegacyClasses(current);
        }
        return current;
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
