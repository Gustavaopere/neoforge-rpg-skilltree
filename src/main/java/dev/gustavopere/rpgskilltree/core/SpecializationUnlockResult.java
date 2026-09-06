package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public record SpecializationUnlockResult(
    boolean unlockable,
    boolean missingEligibleClass,
    Map<String, Integer> missingMasteryExperience,
    Set<String> missingTags,
    boolean providerUnavailable,
    boolean runtimeAdapterIncomplete
) {
    public SpecializationUnlockResult {
        missingMasteryExperience = Map.copyOf(missingMasteryExperience);
        missingTags = Set.copyOf(missingTags);
    }

    /** Compatibility constructor for provider-agnostic resolution. */
    public SpecializationUnlockResult(
        boolean unlockable,
        boolean missingEligibleClass,
        Map<String, Integer> missingMasteryExperience,
        Set<String> missingTags
    ) {
        this(unlockable, missingEligibleClass, missingMasteryExperience, missingTags, false, false);
    }
}
