package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public record SpecializationUnlockResult(
    boolean unlockable,
    boolean missingEligibleClass,
    Map<String, Integer> missingMasteryExperience,
    Set<String> missingTags,
    int missingCharacterLevels,
    Map<ProgressionDomain, Integer> missingDomainInvestment
) {
    public SpecializationUnlockResult {
        missingMasteryExperience = Map.copyOf(missingMasteryExperience);
        missingTags = Set.copyOf(missingTags);
        missingDomainInvestment = Map.copyOf(missingDomainInvestment);
        if (missingCharacterLevels < 0) throw new IllegalArgumentException("missingCharacterLevels must be >= 0");
        if (missingDomainInvestment.values().stream().anyMatch(value -> value == null || value < 0)) {
            throw new IllegalArgumentException("missing domain investment must be >= 0");
        }
    }

    public SpecializationUnlockResult(
        boolean unlockable,
        boolean missingEligibleClass,
        Map<String, Integer> missingMasteryExperience,
        Set<String> missingTags
    ) {
        this(unlockable, missingEligibleClass, missingMasteryExperience, missingTags, 0, Map.of());
    }
}
