package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class CanonicalPlayerStateFoundationTest {
    public static void main(String[] args) {
        newPlayerUsesOneCanonicalEnvelope();
        legacyOnlyBootstrapMigratesCoreAndPreservesCompatibilityState();
        existingCoreWinsWithoutRemigration();
        codecRoundTripsBothSections();
        mismatchedRulesAndMalformedPayloadsFailClosed();
        System.out.println("CanonicalPlayerStateFoundationTest: PASS");
    }

    private static ProgressionRulesSnapshot rules(long version, long baseXp) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:canonical_player_state_test",
            List.of(
                new LevelCurveBand(0L, baseXp, 2L),
                new LevelCurveBand(100L, Math.max(500L, baseXp + 300L), 5L)
            ),
            new MainPerkBudget(30L)
        );
    }

    private static ProgressionState legacyState() {
        ProgressionState empty = ProgressionState.empty();
        return new ProgressionState(
            CharacterLevelCurve.defaultCurve().xpRequiredForLevel(12) + 77L,
            PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, 11, PassivePointSource.BOSS, 2), 4),
            BossProgress.of(java.util.Set.of("rpgskilltree:test_boss")),
            ClassProgressionState.of(java.util.Set.of("mage")),
            MasteryState.of(Map.of("arcane", 250)),
            empty.classChoices(),
            SpecializationProgressionState.of(java.util.Set.of("pyromancer")),
            empty.finalTriads(),
            PassiveNodeProgress.of(Map.of("rpgskilltree:arcane_001", 2)),
            DiscoveryProgress.of(java.util.Set.of("rpgskilltree:test_discovery"))
        );
    }

    private static void newPlayerUsesOneCanonicalEnvelope() {
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        CanonicalPlayerState state = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(), Optional.empty(), rules);

        eq(CharacterProgressionState.empty(), state.coreProgression().characterProgression());
        progressionEq(ProgressionState.empty(), state.compatibilityProgression());
        eq(rules.version(), state.coreProgression().rulesVersion());
    }

    private static void legacyOnlyBootstrapMigratesCoreAndPreservesCompatibilityState() {
        ProgressionState legacy = legacyState();
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        CanonicalPlayerState state = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(), Optional.of(legacy), rules);

        progressionEq(legacy, state.compatibilityProgression());
        eq(11L, state.coreProgression().characterProgression().level());
        eq(13L, state.coreProgression().corePoints().totalCredits());
        eq(4L, state.coreProgression().corePoints().allocated(CorePointAllocation.MAIN_PERK));
        eq(ProgressionStateCodec.CURRENT_VERSION, state.coreProgression().migrationSourceFormatVersion());
    }

    private static void existingCoreWinsWithoutRemigration() {
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        CoreProgressionState core = CoreProgressionState.nativeState(
            new CharacterProgressionState(5_000_000_000L, 123L),
            CorePointLedger.empty(),
            rules
        );
        ProgressionState legacy = legacyState();

        CanonicalPlayerState state = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.of(core), Optional.of(legacy), rules);

        if (state.coreProgression() != core) {
            throw new AssertionError("matching Core state must be reused without remigration");
        }
        progressionEq(legacy, state.compatibilityProgression());
    }

    private static void codecRoundTripsBothSections() {
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        CanonicalPlayerState state = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(), Optional.of(legacyState()), rules);

        byte[] encoded = CanonicalPlayerStateCodec.encode(state);
        CanonicalPlayerState decoded = CanonicalPlayerStateCodec.decode(encoded);

        eq(state, decoded);
        if (encoded.length <= 16) throw new AssertionError("canonical payload unexpectedly empty");
    }

    private static void mismatchedRulesAndMalformedPayloadsFailClosed() {
        ProgressionRulesSnapshot original = rules(21L, 100L);
        CanonicalPlayerState state = CanonicalPlayerStateBootstrap.bootstrap(
            Optional.empty(), Optional.empty(), original);
        ProgressionRulesSnapshot changed = rules(22L, 100L);

        expect(IllegalStateException.class, () -> CanonicalPlayerStateBootstrap.resume(state, changed));
        expect(IllegalArgumentException.class, () -> CanonicalPlayerStateCodec.decode(new byte[0]));

        byte[] encoded = CanonicalPlayerStateCodec.encode(state);
        byte[] truncated = Arrays.copyOf(encoded, encoded.length - 1);
        expect(IllegalArgumentException.class, () -> CanonicalPlayerStateCodec.decode(truncated));

        byte[] trailing = Arrays.copyOf(encoded, encoded.length + 1);
        trailing[trailing.length - 1] = 1;
        expect(IllegalArgumentException.class, () -> CanonicalPlayerStateCodec.decode(trailing));

        byte[] unsupported = encoded.clone();
        unsupported[0] = 0;
        unsupported[1] = 0;
        unsupported[2] = 0;
        unsupported[3] = 99;
        expect(IllegalArgumentException.class, () -> CanonicalPlayerStateCodec.decode(unsupported));
    }

    private static void progressionEq(ProgressionState expected, ProgressionState actual) {
        if (!Arrays.equals(ProgressionStateCodec.encode(expected), ProgressionStateCodec.encode(actual))) {
            throw new AssertionError("persisted progression states differ");
        }
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
