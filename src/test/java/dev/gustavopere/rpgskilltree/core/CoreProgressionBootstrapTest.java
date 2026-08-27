package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class CoreProgressionBootstrapTest {
    public static void main(String[] args) {
        newPlayersStartNativeAtLevelZero();
        decodedLegacyStateMigratesIntoCoreExactlyOnce();
        matchingCoreStateResumesWithoutRewrite();
        mismatchedRulesAreRejected();
        invalidPersistedCharacterPositionIsRejected();
        attachmentEnvelopeRoundTripsInitializationState();
        malformedAttachmentEnvelopeIsRejected();
        System.out.println("CoreProgressionBootstrapTest: PASS");
    }

    private static ProgressionRulesSnapshot rules(long version, long baseXp) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:bootstrap_test",
            List.of(
                new LevelCurveBand(0L, baseXp, 2L),
                new LevelCurveBand(100L, Math.max(500L, baseXp + 300L), 5L)
            ),
            new MainPerkBudget(30L)
        );
    }

    private static void newPlayersStartNativeAtLevelZero() {
        ProgressionRulesSnapshot rules = rules(11L, 100L);
        CoreProgressionState state = CoreProgressionBootstrap.newPlayer(rules);

        eq(CharacterProgressionState.empty(), state.characterProgression());
        eq(0L, state.corePoints().totalCredits());
        eq(rules.version(), state.rulesVersion());
        eq(rules.fingerprint(), state.rulesFingerprint());
        eq(0, state.migrationSourceFormatVersion());
        eq(0L, state.discardedLegacyCapXp());
    }

    private static void decodedLegacyStateMigratesIntoCoreExactlyOnce() {
        ProgressionState empty = ProgressionState.empty();
        PassivePointLedger legacyPoints = PassivePointLedger.of(
            Map.of(PassivePointSource.LEVEL, 12, PassivePointSource.BOSS, 3),
            5
        );
        CharacterLevelCurve legacyCurve = CharacterLevelCurve.defaultCurve();
        ProgressionState legacy = new ProgressionState(
            legacyCurve.xpRequiredForLevel(25) + 123L,
            legacyPoints,
            empty.bossProgress(),
            empty.classProgression(),
            empty.mastery(),
            empty.classChoices(),
            empty.specializations(),
            empty.finalTriads(),
            empty.passiveNodes(),
            empty.discoveries()
        );
        ProgressionRulesSnapshot rules = rules(11L, 100L);

        CoreProgressionState migrated = CoreProgressionBootstrap.migrateDecodedLegacy(legacy, rules);
        CoreProgressionState resumed = CoreProgressionBootstrap.resume(migrated, rules);

        eq(24L, migrated.characterProgression().level());
        eq(15L, migrated.corePoints().totalCredits());
        eq(5L, migrated.corePoints().allocated(CorePointAllocation.MAIN_PERK));
        eq(ProgressionStateCodec.CURRENT_VERSION, migrated.migrationSourceFormatVersion());
        if (resumed != migrated) throw new AssertionError("matching persisted Core state must be reused");
    }

    private static void matchingCoreStateResumesWithoutRewrite() {
        ProgressionRulesSnapshot rules = rules(11L, 100L);
        CoreProgressionState state = CoreProgressionState.nativeState(
            new CharacterProgressionState(5_000_000_000L, 123L),
            CorePointLedger.empty(),
            rules
        );
        CoreProgressionState resumed = CoreProgressionBootstrap.resume(state, rules);
        if (resumed != state) throw new AssertionError("resume must not rewrite a matching state");
    }

    private static void mismatchedRulesAreRejected() {
        ProgressionRulesSnapshot original = rules(11L, 100L);
        ProgressionRulesSnapshot changedSameVersion = rules(11L, 120L);
        ProgressionRulesSnapshot newer = rules(12L, 100L);
        CoreProgressionState state = CoreProgressionState.nativeState(
            CharacterProgressionState.empty(), CorePointLedger.empty(), original);

        expect(IllegalStateException.class, () -> CoreProgressionBootstrap.resume(state, changedSameVersion));
        expect(IllegalStateException.class, () -> CoreProgressionBootstrap.resume(state, newer));
    }

    private static void invalidPersistedCharacterPositionIsRejected() {
        ProgressionRulesSnapshot rules = rules(11L, 100L);
        CoreProgressionState invalid = new CoreProgressionState(
            new CharacterProgressionState(0L, 100L),
            CorePointLedger.empty(),
            rules.version(),
            rules.fingerprint(),
            0,
            0L
        );
        expect(IllegalStateException.class, () -> CoreProgressionBootstrap.resume(invalid, rules));
    }

    private static void attachmentEnvelopeRoundTripsInitializationState() {
        CoreProgressionAttachmentData uninitialized = CoreProgressionAttachmentData.uninitialized();
        eq(false, uninitialized.isInitialized());
        eq(Optional.empty(), uninitialized.state());
        eq(uninitialized, CoreProgressionAttachmentDataCodec.decode(
            CoreProgressionAttachmentDataCodec.encode(uninitialized)));

        ProgressionRulesSnapshot rules = rules(11L, 100L);
        CoreProgressionState state = CoreProgressionBootstrap.newPlayer(rules);
        CoreProgressionAttachmentData initialized = CoreProgressionAttachmentData.initialized(state);
        eq(true, initialized.isInitialized());
        eq(Optional.of(state), initialized.state());
        eq(initialized, CoreProgressionAttachmentDataCodec.decode(
            CoreProgressionAttachmentDataCodec.encode(initialized)));
    }

    private static void malformedAttachmentEnvelopeIsRejected() {
        expect(IllegalArgumentException.class, () -> CoreProgressionAttachmentDataCodec.decode(new byte[0]));
        expect(IllegalArgumentException.class, () -> CoreProgressionAttachmentDataCodec.decode(new byte[] {2}));
        expect(IllegalArgumentException.class, () -> CoreProgressionAttachmentDataCodec.decode(new byte[] {0, 1}));
        expect(IllegalArgumentException.class, () -> CoreProgressionAttachmentDataCodec.decode(new byte[] {1}));

        byte[] valid = CoreProgressionAttachmentDataCodec.encode(
            CoreProgressionAttachmentData.initialized(CoreProgressionBootstrap.newPlayer(rules(11L, 100L))));
        byte[] truncated = Arrays.copyOf(valid, valid.length - 1);
        expect(IllegalArgumentException.class, () -> CoreProgressionAttachmentDataCodec.decode(truncated));
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
