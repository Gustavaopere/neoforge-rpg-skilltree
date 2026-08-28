package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DiscoveryIdempotencyTest {
    public static void main(String[] args) {
        replayingSameCriterionIsNoOp();
        oneShotDefeatRewardDoesNotDuplicate();
        System.out.println("DiscoveryIdempotencyTest: PASS");
    }

    private static void replayingSameCriterionIsNoOp() {
        CompendiumEntryId zombie = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie");
        DiscoveryCriterion criterion = new DiscoveryCriterion(
            "observe_zombie", zombie, DiscoveryTriggerType.OBSERVATION, DiscoveryState.SEEN,
            Optional.of("observe"), List.of()
        );
        DiscoverySignal signal = new DiscoverySignal(
            zombie, DiscoveryTriggerType.OBSERVATION, 40L, Optional.empty(), Optional.empty()
        );

        DiscoveryTransition first = DiscoveryRuntime.apply(DiscoveryProgress.empty(), criterion, signal);
        DiscoveryTransition second = DiscoveryRuntime.apply(first.progress(), criterion, signal);

        yes(first.changed());
        no(second.changed());
        same(first.progress(), second.progress());
        eq(java.util.Set.of(), second.newObjectiveIds());
        eq(List.of(), second.newRewards());
    }

    private static void oneShotDefeatRewardDoesNotDuplicate() {
        CompendiumEntryId zombie = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie");
        DiscoveryRewardDefinition reward = new DiscoveryRewardDefinition(
            "zombie_first_defeat_xp", DiscoveryRewardKind.CHARACTER_XP, 25L, "compendium:zombie_defeat"
        );
        DiscoveryCriterion criterion = new DiscoveryCriterion(
            "defeat_zombie", zombie, DiscoveryTriggerType.DEFEAT, DiscoveryState.STUDIED,
            Optional.of("defeat"), List.of(reward)
        );
        DiscoverySignal signal = new DiscoverySignal(
            zombie, DiscoveryTriggerType.DEFEAT, 80L, Optional.empty(), Optional.empty()
        );

        DiscoveryTransition first = DiscoveryRuntime.apply(DiscoveryProgress.empty(), criterion, signal);
        DiscoveryTransition second = DiscoveryRuntime.apply(first.progress(), criterion, signal);

        eq(List.of(reward), first.newRewards());
        eq(List.of(), second.newRewards());
        eq(java.util.Set.of("zombie_first_defeat_xp"),
            second.progress().record(zombie).orElseThrow().claimedRewardIds());
    }

    private static void yes(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void no(boolean value) {
        if (value) throw new AssertionError("expected false");
    }

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected identical object reference");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
