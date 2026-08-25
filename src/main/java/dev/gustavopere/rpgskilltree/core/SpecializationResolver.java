package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SpecializationResolver {
    private SpecializationResolver() {}

    public static SpecializationUnlockResult evaluate(
        Set<String> unlockedClassIds,
        MasteryState mastery,
        InvestmentState investment,
        SpecializationDefinition definition
    ) {
        return evaluate(unlockedClassIds, mastery, investment, 1, definition);
    }

    public static SpecializationUnlockResult evaluate(
        Set<String> unlockedClassIds,
        MasteryState mastery,
        InvestmentState investment,
        int characterLevel,
        SpecializationDefinition definition
    ) {
        if (characterLevel < 1) throw new IllegalArgumentException("characterLevel must be >= 1");
        boolean missingClass = definition.requiresClass()
            && unlockedClassIds.stream().noneMatch(definition.eligibleClassIds()::contains);

        Map<String, Integer> missingMastery = new HashMap<>();
        definition.minimumMasteryExperience().forEach((lane, required) -> {
            int missing = Math.max(0, required - mastery.experience(lane));
            if (missing > 0) missingMastery.put(lane, missing);
        });

        Set<String> missingTags = new HashSet<>();
        for (String tag : definition.requiredTags()) {
            if (!investment.hasTag(tag)) missingTags.add(tag);
        }

        int missingLevels = Math.max(0, definition.minimumCharacterLevel() - characterLevel);
        Map<ProgressionDomain, Integer> missingDomain = new HashMap<>();
        definition.minimumDomainInvestment().forEach((domain, required) -> {
            int missing = Math.max(0, required - investment.domainScore(domain));
            if (missing > 0) missingDomain.put(domain, missing);
        });

        boolean unlockable = !missingClass
            && missingMastery.isEmpty()
            && missingTags.isEmpty()
            && missingLevels == 0
            && missingDomain.isEmpty();
        return new SpecializationUnlockResult(
            unlockable,
            missingClass,
            missingMastery,
            missingTags,
            missingLevels,
            missingDomain
        );
    }
}
