package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class DiscoveryProgressTest {
    public static void main(String[] args) {
        discoveryStateIsMonotonic();
        progressUsesCanonicalEntryIdentity();
        recordCollectionsAreDefensivelyCopied();
        absentCatalogEntryCanRemainPersisted();
        independentPlayersDoNotShareDiscoveryProgress();
        System.out.println("DiscoveryProgressTest: PASS");
    }

    private static void discoveryStateIsMonotonic() {
        DiscoveryRecord seen = record(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:pig"),
            DiscoveryState.SEEN,
            Set.of(), Set.of(), Set.of()
        );
        eq(DiscoveryState.SEEN, seen.advanceTo(DiscoveryState.UNKNOWN).state());
        eq(DiscoveryState.STUDIED, seen.advanceTo(DiscoveryState.STUDIED).state());
        eq(DiscoveryState.MASTERED, DiscoveryState.SEEN.max(DiscoveryState.MASTERED));
        yes(DiscoveryState.MASTERED.atLeast(DiscoveryState.SEEN));
        no(DiscoveryState.SEEN.atLeast(DiscoveryState.STUDIED));
    }

    private static void progressUsesCanonicalEntryIdentity() {
        CompendiumEntryId entity = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "example:same_id");
        CompendiumEntryId flora = CompendiumEntryId.of(CompendiumEntryKind.FLORA, "example:same_id");
        DiscoveryProgress progress = DiscoveryProgress.empty()
            .withRecord(record(entity, DiscoveryState.SEEN, Set.of(), Set.of(), Set.of()))
            .withRecord(record(flora, DiscoveryState.STUDIED, Set.of(), Set.of(), Set.of()));

        eq(DiscoveryState.SEEN, progress.record(entity).orElseThrow().state());
        eq(DiscoveryState.STUDIED, progress.record(flora).orElseThrow().state());
        eq(2, progress.records().size());
    }

    private static void recordCollectionsAreDefensivelyCopied() {
        LinkedHashSet<String> variants = new LinkedHashSet<>(Set.of("minecraft:warm"));
        LinkedHashSet<String> objectives = new LinkedHashSet<>(Set.of("observe"));
        LinkedHashSet<String> rewards = new LinkedHashSet<>(Set.of("seen_reward"));
        DiscoveryRecord record = record(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:frog"),
            DiscoveryState.SEEN,
            variants,
            objectives,
            rewards
        );

        variants.add("minecraft:cold");
        objectives.add("breed");
        rewards.add("master_reward");

        eq(Set.of("minecraft:warm"), record.variantIds());
        eq(Set.of("observe"), record.completedObjectiveIds());
        eq(Set.of("seen_reward"), record.claimedRewardIds());
        throwsUnsupported(() -> record.variantIds().add("x"));
    }

    private static void absentCatalogEntryCanRemainPersisted() {
        CompendiumEntryId removed = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "removedmod:lost_creature");
        DiscoveryProgress progress = DiscoveryProgress.empty().withRecord(
            record(removed, DiscoveryState.MASTERED, Set.of("removedmod:variant"), Set.of("study"), Set.of("reward"))
        );
        eq(removed, progress.record(removed).orElseThrow().entryId());
        eq(DiscoveryState.MASTERED, progress.record(removed).orElseThrow().state());
    }

    private static void independentPlayersDoNotShareDiscoveryProgress() {
        CompendiumEntryId pig = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:pig");
        DiscoveryProgress playerOne = DiscoveryProgress.empty().withRecord(
            record(pig, DiscoveryState.SEEN, Set.of(), Set.of("observe"), Set.of())
        );
        DiscoveryProgress playerTwo = DiscoveryProgress.empty();

        yes(playerOne.record(pig).isPresent());
        no(playerTwo.record(pig).isPresent());
        eq(1, playerOne.records().size());
        eq(0, playerTwo.records().size());
    }

    private static DiscoveryRecord record(
        CompendiumEntryId id,
        DiscoveryState state,
        Set<String> variants,
        Set<String> objectives,
        Set<String> rewards
    ) {
        return new DiscoveryRecord(
            id,
            state,
            120L,
            Optional.of(new DiscoveryOrigin("minecraft:overworld", 2, -3)),
            variants,
            objectives,
            rewards
        );
    }

    private static void throwsUnsupported(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void yes(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void no(boolean value) {
        if (value) throw new AssertionError("expected false");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
