package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public record SpecializationUnlockResult(
    boolean unlockable,
    boolean missingEligibleClass,
    Map<String, Integer> missingMasteryExperience,
    Set<String> missingTags
) {
    public SpecializationUnlockResult {
        missingMasteryExperience = Map.copyOf(missingMasteryExperience);
        missingTags = Set.copyOf(missingTags);
    }
}
