package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class CanonicalPlayerAttachmentDataTest {
    public static void main(String[] args) {
        freshEnvelopeNeedsNoCoreRules();
        legacyPresenceIsPreservedEvenWhenLegacyStateIsEmpty();
        oldCoreAndLegacyInputsAreCollectedWithoutReinterpretingThem();
        coreInitializationUsesPersistedLegacyPresenceExactlyOnce();
        compatibilityMutationBeforeCoreInitializationBecomesMigrationSource();
        codecRoundTripsUninitializedAndInitializedCore();
        codecRoundTripsRichPersistedState();
        CanonicalPlayerAttachmentMigrationChainTest.main(new String[0]);
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

    private static void compatibilityMutationBeforeCoreInitializationBecomesMigrationSource() {
        CanonicalPlayerAttachmentData fresh = CanonicalPlayerAttachmentData.empty();
        ProgressionState equivalentEmpty = ProgressionStateCodec.decode(
            ProgressionStateCodec.encode(ProgressionState.empty()));
        CanonicalPlayerAttachmentData unchanged = fresh.withCompatibilityProgression(equivalentEmpty);
        eq(false, unchanged.hasLegacyMigrationSource());

        ProgressionState empty = ProgressionState.empty();
        ProgressionState progressed = new ProgressionState(
            CharacterLevelCurve.defaultCurve().xpRequiredForLevel(4) + 25L,
            PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, 3), 0),
            empty.bossProgress(),
            empty.classProgression(),
            empty.mastery(),
            empty.classChoices(),
            empty.specializations(),
            empty.finalTriads(),
            empty.passiveNodes(),
            empty.discoveries()
        );
        CanonicalPlayerAttachmentData updated = fresh.withCompatibilityProgression(progressed);
        eq(true, updated.hasLegacyMigrationSource());

        CoreProgressionState migrated = updated.initializeCore(rules())
            .coreProgression().state().orElseThrow();
        eq(3L, migrated.characterProgression().level());
        eq(3L, migrated.corePoints().totalCredits());
        eq(ProgressionStateCodec.CURRENT_VERSION, migrated.migrationSourceFormatVersion());
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

    private static void codecRoundTripsRichPersistedState() {
        ProgressionState empty = ProgressionState.empty();
        ProgressionState progressed = new ProgressionState(
            CharacterLevelCurve.defaultCurve().xpRequiredForLevel(12) + 77L,
            PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, 11, PassivePointSource.BOSS, 2), 4),
            BossProgress.of(Set.of("rpgskilltree:test_boss")),
            ClassProgressionState.of(Set.of("mage")),
            MasteryState.of(Map.of("arcane", 250, "projectile", 91)),
            empty.classChoices(),
            SpecializationProgressionState.of(Set.of("pyromancer")),
            empty.finalTriads(),
            PassiveNodeProgress.of(Map.of("rpgskilltree:arcane_001", 2, "rpgskilltree:agility_000", 3)),
            DiscoveryProgress.of(Set.of("rpgskilltree:test_discovery", "minecraft:the_nether"))
        );

        CanonicalPlayerAttachmentData original = CanonicalPlayerAttachmentData.fromMigrationInputs(
            Optional.empty(), Optional.of(progressed));
        original = original.initializeCore(rules());

        byte[] encoded = CanonicalPlayerAttachmentDataCodec.encode(original);
        CanonicalPlayerAttachmentData restored = CanonicalPlayerAttachmentDataCodec.decode(encoded);

        eq(original, restored);
        progressionEq(progressed, restored.compatibilityProgression());
        eq(original.coreProgression(), restored.coreProgression());
        eq(true, restored.hasLegacyMigrationSource());
        if (encoded.length <= 32) throw new AssertionError("rich canonical attachment payload unexpectedly small");
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
