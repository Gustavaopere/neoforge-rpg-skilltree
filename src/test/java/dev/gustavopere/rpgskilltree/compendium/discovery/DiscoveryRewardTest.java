package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DiscoveryRewardTest {
    public static void main(String[] args) {
        stableRewardIdClaimsAcrossDifferentCriteria();
        invalidRewardDefinitionIsRejected();
        System.out.println("DiscoveryRewardTest: PASS");
    }

    private static void stableRewardIdClaimsAcrossDifferentCriteria() {
        CompendiumEntryId pig = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:pig");
        DiscoveryRewardDefinition reward = new DiscoveryRewardDefinition(
            "pig_study_xp", DiscoveryRewardKind.CHARACTER_XP, 15L, "compendium:pig"
        );
        DiscoveryCriterion observe = new DiscoveryCriterion(
            "observe_pig", pig, DiscoveryTriggerType.OBSERVATION, DiscoveryState.SEEN,
            Optional.of("observe"), List.of(reward)
        );
        DiscoveryCriterion defeat = new DiscoveryCriterion(
            "defeat_pig", pig, DiscoveryTriggerType.DEFEAT, DiscoveryState.STUDIED,
            Optional.of("defeat"), List.of(reward)
        );

        DiscoveryTransition first = DiscoveryRuntime.apply(
            DiscoveryProgress.empty(), observe,
            new DiscoverySignal(pig, DiscoveryTriggerType.OBSERVATION, 1L, Optional.empty(), Optional.empty())
        );
        DiscoveryTransition second = DiscoveryRuntime.apply(
            first.progress(), defeat,
            new DiscoverySignal(pig, DiscoveryTriggerType.DEFEAT, 2L, Optional.empty(), Optional.empty())
        );

        eq(List.of(reward), first.newRewards());
        eq(List.of(), second.newRewards());
        eq(DiscoveryState.STUDIED, second.currentState());
        eq(java.util.Set.of("observe", "defeat"),
            second.progress().record(pig).orElseThrow().completedObjectiveIds());
        eq(java.util.Set.of("pig_study_xp"),
            second.progress().record(pig).orElseThrow().claimedRewardIds());
    }

    private static void invalidRewardDefinitionIsRejected() {
        throwsIllegal(() -> new DiscoveryRewardDefinition("", DiscoveryRewardKind.CHARACTER_XP, 1L, "source"));
        throwsIllegal(() -> new DiscoveryRewardDefinition("reward", DiscoveryRewardKind.CHARACTER_XP, 0L, "source"));
        throwsIllegal(() -> new DiscoveryRewardDefinition("reward", DiscoveryRewardKind.CHARACTER_XP, 1L, " "));
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
