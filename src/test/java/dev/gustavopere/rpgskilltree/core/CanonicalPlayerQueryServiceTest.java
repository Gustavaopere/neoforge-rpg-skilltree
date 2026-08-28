package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.RecordComponent;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class CanonicalPlayerQueryServiceTest {
    public static void main(String[] args) {
        queryCombinesAuthoritativeCoreAndCompatibilityDomains();
        snapshotDoesNotExposeLegacyProgressionAuthorities();
        mismatchedRulesFailClosed();
        System.out.println("CanonicalPlayerQueryServiceTest: PASS");
    }

    private static ProgressionRulesSnapshot rules(long version) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:canonical_player_query_test",
            List.of(new LevelCurveBand(0L, 100L, 2L)),
            new MainPerkBudget(30L)
        );
    }

    private static void queryCombinesAuthoritativeCoreAndCompatibilityDomains() {
        ProgressionRulesSnapshot rules = rules(31L);
        CoreProgressionState core = CoreProgressionState.nativeState(
            new CharacterProgressionState(5_000_000_000L, 123L),
            CorePointLedger.empty(),
            rules
        );
        ProgressionState compatibility = ProgressionState.empty()
            .withMastery(MasteryState.of(Map.of("arcane", 250, "mining", 75)))
            .withClassProgression(ClassProgressionState.of(Set.of("mage")))
            .withSpecializations(SpecializationProgressionState.of(Set.of("pyromancer")))
            .withPassiveNodes(PassiveNodeProgress.of(Map.of("rpgskilltree:arcane_001", 2)))
            .withDiscoveries(DiscoveryProgress.of(Set.of("rpgskilltree:ancient_ruin")));

        CanonicalPlayerSnapshot snapshot = CanonicalPlayerQueryService.snapshot(
            new CanonicalPlayerState(core, compatibility),
            rules
        );

        eq(5_000_000_000L, snapshot.progression().level());
        eq(123L, snapshot.progression().xpIntoLevel());
        eq(250, snapshot.mastery().experience("arcane"));
        eq(true, snapshot.classes().isUnlocked("mage"));
        eq(true, snapshot.specializations().isUnlocked("pyromancer"));
        eq(2, snapshot.passiveNodes().rank("rpgskilltree:arcane_001"));
        eq(true, snapshot.discoveries().contains("rpgskilltree:ancient_ruin"));
    }

    private static void snapshotDoesNotExposeLegacyProgressionAuthorities() {
        for (RecordComponent component : CanonicalPlayerSnapshot.class.getRecordComponents()) {
            if (component.getType() == ProgressionState.class) {
                throw new AssertionError("canonical query must not expose raw ProgressionState");
            }
            if (component.getType() == PassivePointLedger.class) {
                throw new AssertionError("canonical query must not expose legacy PassivePointLedger");
            }
            if (component.getName().equals("totalCharacterXp") || component.getName().equals("passivePoints")) {
                throw new AssertionError("canonical query must not expose legacy progression authority: " + component.getName());
            }
        }
    }

    private static void mismatchedRulesFailClosed() {
        ProgressionRulesSnapshot original = rules(31L);
        ProgressionRulesSnapshot changed = rules(32L);
        CanonicalPlayerState state = new CanonicalPlayerState(
            CoreProgressionState.nativeState(CharacterProgressionState.empty(), CorePointLedger.empty(), original),
            ProgressionState.empty()
        );
        expect(IllegalStateException.class, () -> CanonicalPlayerQueryService.snapshot(state, changed));
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
