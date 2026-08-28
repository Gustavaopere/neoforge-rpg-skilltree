package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DiscoveryRuntimeTest {
    public static void main(String[] args) {
        observationMovesUnknownToSeen();
        lowerTargetNeverRegressesState();
        variantAndObjectiveCanAdvanceWithoutRediscoveringSpecies();
        mismatchedSignalIsRejected();
        forgedInspectionIsRejected();
        System.out.println("DiscoveryRuntimeTest: PASS");
    }

    private static void observationMovesUnknownToSeen() {
        CompendiumEntryId pig = entity("minecraft:pig");
        DiscoveryCriterion criterion = criterion("observe_pig", pig, DiscoveryTriggerType.OBSERVATION, DiscoveryState.SEEN, "observe");
        DiscoveryTransition result = DiscoveryRuntime.apply(
            DiscoveryProgress.empty(),
            criterion,
            signal(pig, DiscoveryTriggerType.OBSERVATION, Optional.empty())
        );
        eq(DiscoveryState.UNKNOWN, result.previousState());
        eq(DiscoveryState.SEEN, result.currentState());
        yes(result.changed());
        eq(DiscoveryState.SEEN, result.progress().record(pig).orElseThrow().state());
        eq(java.util.Set.of("observe"), result.newObjectiveIds());
    }

    private static void lowerTargetNeverRegressesState() {
        CompendiumEntryId pig = entity("minecraft:pig");
        DiscoveryRecord mastered = new DiscoveryRecord(
            pig, DiscoveryState.MASTERED, 1L, Optional.empty(), java.util.Set.of(), java.util.Set.of(), java.util.Set.of()
        );
        DiscoveryTransition result = DiscoveryRuntime.apply(
            DiscoveryProgress.empty().withRecord(mastered),
            criterion("observe_pig", pig, DiscoveryTriggerType.OBSERVATION, DiscoveryState.SEEN, null),
            signal(pig, DiscoveryTriggerType.OBSERVATION, Optional.empty())
        );
        eq(DiscoveryState.MASTERED, result.currentState());
    }

    private static void variantAndObjectiveCanAdvanceWithoutRediscoveringSpecies() {
        CompendiumEntryId frog = entity("minecraft:frog");
        DiscoveryProgress seen = DiscoveryProgress.empty().withRecord(new DiscoveryRecord(
            frog, DiscoveryState.SEEN, 10L, Optional.empty(), java.util.Set.of(), java.util.Set.of("observe"), java.util.Set.of()
        ));
        DiscoveryCriterion criterion = criterion("study_warm_frog", frog, DiscoveryTriggerType.VARIANT, DiscoveryState.SEEN, "variant_warm");
        DiscoveryTransition result = DiscoveryRuntime.apply(
            seen,
            criterion,
            signal(frog, DiscoveryTriggerType.VARIANT, Optional.of("minecraft:warm"))
        );
        eq(DiscoveryState.SEEN, result.previousState());
        eq(DiscoveryState.SEEN, result.currentState());
        eq(java.util.Set.of("minecraft:warm"), result.newVariantIds());
        eq(java.util.Set.of("variant_warm"), result.newObjectiveIds());
        yes(result.changed());
    }

    private static void mismatchedSignalIsRejected() {
        CompendiumEntryId pig = entity("minecraft:pig");
        CompendiumEntryId cow = entity("minecraft:cow");
        DiscoveryCriterion criterion = criterion("observe_pig", pig, DiscoveryTriggerType.OBSERVATION, DiscoveryState.SEEN, null);
        throwsIllegal(() -> DiscoveryRuntime.apply(
            DiscoveryProgress.empty(), criterion, signal(cow, DiscoveryTriggerType.OBSERVATION, Optional.empty())
        ));
        throwsIllegal(() -> DiscoveryRuntime.apply(
            DiscoveryProgress.empty(), criterion, signal(pig, DiscoveryTriggerType.DEFEAT, Optional.empty())
        ));
    }

    private static void forgedInspectionIsRejected() {
        CompendiumEntryId pig = entity("minecraft:pig");
        CompendiumEntryId cow = entity("minecraft:cow");
        yes(DiscoveryInspectionValidator.isValid(pig, pig, 16.0D, 64.0D, true, true));
        no(DiscoveryInspectionValidator.isValid(pig, cow, 16.0D, 64.0D, true, true));
        no(DiscoveryInspectionValidator.isValid(pig, pig, 65.0D, 64.0D, true, true));
        no(DiscoveryInspectionValidator.isValid(pig, pig, 16.0D, 64.0D, false, true));
        no(DiscoveryInspectionValidator.isValid(pig, pig, 16.0D, 64.0D, true, false));
    }

    private static DiscoveryCriterion criterion(
        String id,
        CompendiumEntryId entry,
        DiscoveryTriggerType trigger,
        DiscoveryState state,
        String objective
    ) {
        return new DiscoveryCriterion(id, entry, trigger, state, Optional.ofNullable(objective), List.of());
    }

    private static DiscoverySignal signal(
        CompendiumEntryId entry,
        DiscoveryTriggerType trigger,
        Optional<String> variant
    ) {
        return new DiscoverySignal(
            entry, trigger, 200L,
            Optional.of(new DiscoveryOrigin("minecraft:overworld", 0, 0)),
            variant
        );
    }

    private static CompendiumEntryId entity(String id) {
        return CompendiumEntryId.of(CompendiumEntryKind.ENTITY, id);
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
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
