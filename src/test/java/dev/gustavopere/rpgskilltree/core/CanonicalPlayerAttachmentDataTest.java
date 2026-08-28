package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class CanonicalPlayerAttachmentDataTest {
    public static void main(String[] args) {
        freshEnvelopeNeedsNoCoreRules();
        legacyPresenceIsPreservedEvenWhenLegacyStateIsEmpty();
        oldCoreAndLegacyInputsAreCollectedWithoutReinterpretingThem();
        coreInitializationUsesPersistedLegacyPresenceExactlyOnce();
        codecRoundTripsUninitializedAndInitializedCore();
        malformedPayloadsFailClosed();
        System.out.println("CanonicalPlayerAttachmentDataTest: PASS");
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            31L,
            "rpgskilltree:canonical_attachment_test",
            List.of(new LevelCurveBand(0L, 100L, 2L)),
            new MainPerkBudget(30L)
        );
    }

    private static void freshEnvelopeNeedsNoCoreRules() {
        CanonicalPlayerAttachmentData data = CanonicalPlayerAttachmentData.empty();
        eq(false, data.coreProgression().isInitialized());
        progressionEq(ProgressionState.empty(), data.compatibilityProgression());
        eq(false, data.hasLegacyMigrationSource());
    }

    private static void legacyPresenceIsPreservedEvenWhenLegacyStateIsEmpty() {
        CanonicalPlayerAttachmentData data = CanonicalPlayerAttachmentData.fromMigrationInputs(
            Optional.empty(),
            Optional.of(ProgressionState.empty())
        );
        eq(false, data.coreProgression().isInitialized());
        eq(true, data.hasLegacyMigrationSource());

        CanonicalPlayerAttachmentData initialized = data.initializeCore(rules());
        eq(true, initialized.coreProgression().isInitialized());
        eq(ProgressionStateCodec.CURRENT_VERSION,
            initialized.coreProgression().state().orElseThrow().migrationSourceFormatVersion());
    }

    private static void oldCoreAndLegacyInputsAreCollectedWithoutReinterpretingThem() {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState core = CoreProgressionState.nativeState(
            new CharacterProgressionState(5_000_000_000L, 9L),
            CorePointLedger.empty(),
            rules
        );
        ProgressionState legacy = ProgressionState.empty().withMastery(MasteryState.of(Map.of("arcane", 77)));

        CanonicalPlayerAttachmentData data = CanonicalPlayerAttachmentData.fromMigrationInputs(
            Optional.of(CoreProgressionAttachmentData.initialized(core)),
            Optional.of(legacy)
        );

        if (data.coreProgression().state().orElseThrow() != core) {
            throw new AssertionError("existing initialized Core state must be reused");
        }
        progressionEq(legacy, data.compatibilityProgression());
        eq(true, data.hasLegacyMigrationSource());
    }

    private static void coreInitializationUsesPersistedLegacyPresenceExactlyOnce() {
        ProgressionRulesSnapshot rules = rules();
        CanonicalPlayerAttachmentData fresh = CanonicalPlayerAttachmentData.empty();
        CanonicalPlayerAttachmentData freshInitialized = fresh.initializeCore(rules);
        eq(0, freshInitialized.coreProgression().state().orElseThrow().migrationSourceFormatVersion());

        CanonicalPlayerAttachmentData legacy = CanonicalPlayerAttachmentData.fromMigrationInputs(
            Optional.empty(), Optional.of(ProgressionState.empty()));
        CanonicalPlayerAttachmentData migrated = legacy.initializeCore(rules);
        CoreProgressionState migratedCore = migrated.coreProgression().state().orElseThrow();
        eq(ProgressionStateCodec.CURRENT_VERSION, migratedCore.migrationSourceFormatVersion());

        CanonicalPlayerAttachmentData resumed = migrated.initializeCore(rules);
        if (resumed != migrated) {
            throw new AssertionError("initialized canonical attachment must be reused on repeated initialization");
        }
    }

    private static void codecRoundTripsUninitializedAndInitializedCore() {
        CanonicalPlayerAttachmentData uninitialized = CanonicalPlayerAttachmentData.fromMigrationInputs(
            Optional.empty(), Optional.of(ProgressionState.empty()));
        eq(uninitialized, CanonicalPlayerAttachmentDataCodec.decode(
            CanonicalPlayerAttachmentDataCodec.encode(uninitialized)));

        CanonicalPlayerAttachmentData initialized = uninitialized.initializeCore(rules());
        eq(initialized, CanonicalPlayerAttachmentDataCodec.decode(
            CanonicalPlayerAttachmentDataCodec.encode(initialized)));
    }

    private static void malformedPayloadsFailClosed() {
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentDataCodec.decode(new byte[0]));
        byte[] valid = CanonicalPlayerAttachmentDataCodec.encode(CanonicalPlayerAttachmentData.empty());

        byte[] truncated = Arrays.copyOf(valid, valid.length - 1);
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentDataCodec.decode(truncated));

        byte[] trailing = Arrays.copyOf(valid, valid.length + 1);
        trailing[trailing.length - 1] = 1;
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentDataCodec.decode(trailing));

        byte[] unsupported = valid.clone();
        unsupported[0] = 99;
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentDataCodec.decode(unsupported));
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
