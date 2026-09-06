package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class SpecializationResolver {
    private SpecializationResolver() {}

    public static SpecializationUnlockResult evaluate(
        Set<String> unlockedClassIds,
        MasteryState mastery,
        InvestmentState investment,
        SpecializationDefinition definition
    ) {
        return evaluate(
            unlockedClassIds,
            mastery,
            investment,
            definition,
            SpecializationAvailability.internal()
        );
    }

    public static SpecializationUnlockResult evaluate(
        Set<String> unlockedClassIds,
        MasteryState mastery,
        InvestmentState investment,
        SpecializationDefinition definition,
        SpecializationAvailability availability
    ) {
        Objects.requireNonNull(unlockedClassIds);
        Objects.requireNonNull(mastery);
        Objects.requireNonNull(investment);
        Objects.requireNonNull(definition);
        Objects.requireNonNull(availability);

        boolean missingClass = definition.requiresClass()
            && unlockedClassIds.stream().noneMatch(definition.eligibleClassIds()::contains);

        Map<String, Integer> missingMastery = new HashMap<>();
        definition.minimumMasteryExperience().forEach((lane, required) -> {
            int missing = Math.max(0, required - mastery.experience(lane));
            if (missing > 0) {
                missingMastery.put(lane, missing);
            }
        });

        Set<String> missingTags = new HashSet<>();
        for (String tag : definition.requiredTags()) {
            if (!investment.hasTag(tag)) {
                missingTags.add(tag);
            }
        }

        boolean providerUnavailable = !availability.providerLoaded();
        boolean runtimeAdapterIncomplete = !availability.runtimeAdapterComplete();
        boolean unlockable = !missingClass
            && missingMastery.isEmpty()
            && missingTags.isEmpty()
            && availability.gatewayAvailable();
        return new SpecializationUnlockResult(
            unlockable,
            missingClass,
            missingMastery,
            missingTags,
            providerUnavailable,
            runtimeAdapterIncomplete
        );
    }
}
