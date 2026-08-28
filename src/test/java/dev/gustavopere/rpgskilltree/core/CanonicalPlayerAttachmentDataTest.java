package dev.gustavopere.rpgskilltree.core;

import java.nio.ByteBuffer;
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
        compatibilityMutationBeforeCoreInitializationBecomesMigrationSource();
        codecRoundTripsUninitializedAndInitializedCore();
        futureSchemaMigrationPolicyIsSequentialAndBounded();
        canonicalMigrationRegistryIsFailClosedUntilARealStepExists();
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

    private static void futureSchemaMigrationPolicyIsSequentialAndBounded() {
        CanonicalPlayerAttachmentMigrationChain chain = new CanonicalPlayerAttachmentMigrationChain(
            3,
            64,
            List.of(
                new CanonicalPlayerAttachmentMigrationStep(
                    1,
                    2,
                    encoded -> upgradeVersion(encoded, 1, 2, (byte) 0x22)
                ),
                new CanonicalPlayerAttachmentMigrationStep(
                    2,
                    3,
                    encoded -> upgradeVersion(encoded, 2, 3, (byte) 0x33)
                )
            )
        );

        byte[] v1 = payload(1, (byte) 0x11);
        byte[] v3 = chain.migrateToCurrent(v1);
        eq(3, encodedVersion(v3));
        arrayEq(payload(3, (byte) 0x11, (byte) 0x22, (byte) 0x33), v3);

        byte[] alreadyCurrent = chain.migrateToCurrent(v3);
        arrayEq(v3, alreadyCurrent);
        if (alreadyCurrent == v3) {
            throw new AssertionError("current-version migration must return a defensive copy");
        }

        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(payload(4, (byte) 1)));
        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(payload(0, (byte) 1)));
        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(new byte[3]));
        expect(IllegalArgumentException.class, () -> chain.migrateToCurrent(new byte[65]));

        CanonicalPlayerAttachmentMigrationChain gap = new CanonicalPlayerAttachmentMigrationChain(
            3,
            64,
            List.of(new CanonicalPlayerAttachmentMigrationStep(
                1,
                2,
                encoded -> upgradeVersion(encoded, 1, 2, (byte) 1)
            ))
        );
        expect(IllegalArgumentException.class, () -> gap.migrateToCurrent(v1));

        expect(IllegalArgumentException.class, () -> new CanonicalPlayerAttachmentMigrationStep(
            1,
            3,
            encoded -> encoded
        ));
        expect(IllegalArgumentException.class, () -> new CanonicalPlayerAttachmentMigrationChain(
            3,
            64,
            List.of(
                new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> payload(2)),
                new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> payload(2))
            )
        ));

        CanonicalPlayerAttachmentMigrationChain wrongHeader = new CanonicalPlayerAttachmentMigrationChain(
            2,
            64,
            List.of(new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> encoded.clone()))
        );
        expect(IllegalArgumentException.class, () -> wrongHeader.migrateToCurrent(v1));

        CanonicalPlayerAttachmentMigrationChain emptyOutput = new CanonicalPlayerAttachmentMigrationChain(
            2,
            64,
            List.of(new CanonicalPlayerAttachmentMigrationStep(1, 2, encoded -> new byte[0]))
        );
        expect(IllegalArgumentException.class, () -> emptyOutput.migrateToCurrent(v1));
    }

    private static void canonicalMigrationRegistryIsFailClosedUntilARealStepExists() {
        byte[] current = CanonicalPlayerAttachmentDataCodec.encode(CanonicalPlayerAttachmentData.empty());
        byte[] normalized = CanonicalPlayerAttachmentMigrations.toCurrent(current);
        arrayEq(current, normalized);
        if (normalized == current) {
            throw new AssertionError("canonical migration normalization must return a defensive copy");
        }

        byte[] future = current.clone();
        ByteBuffer.wrap(future).putInt(CanonicalPlayerAttachmentDataCodec.CURRENT_VERSION + 1);
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentMigrations.toCurrent(future));
    }

    private static byte[] upgradeVersion(
        byte[] encoded,
        int expectedVersion,
        int nextVersion,
        byte marker
    ) {
        if (encodedVersion(encoded) != expectedVersion) {
            throw new IllegalArgumentException("unexpected migration source version");
        }
        byte[] upgraded = Arrays.copyOf(encoded, encoded.length + 1);
        ByteBuffer.wrap(upgraded).putInt(nextVersion);
        upgraded[upgraded.length - 1] = marker;
        return upgraded;
    }

    private static byte[] payload(int version, byte... body) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + body.length);
        buffer.putInt(version);
        buffer.put(body);
        return buffer.array();
    }

    private static int encodedVersion(byte[] encoded) {
        if (encoded.length < Integer.BYTES) {
            throw new IllegalArgumentException("payload too short for version header");
        }
        return ByteBuffer.wrap(encoded).getInt();
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
        ByteBuffer.wrap(unsupported).putInt(99);
        expect(IllegalArgumentException.class, () -> CanonicalPlayerAttachmentDataCodec.decode(unsupported));
    }

    private static void progressionEq(ProgressionState expected, ProgressionState actual) {
        if (!Arrays.equals(ProgressionStateCodec.encode(expected), ProgressionStateCodec.encode(actual))) {
            throw new AssertionError("persisted progression states differ");
        }
    }

    private static void arrayEq(byte[] expected, byte[] actual) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError("byte arrays differ");
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
