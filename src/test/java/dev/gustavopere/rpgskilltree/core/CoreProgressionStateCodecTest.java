package dev.gustavopere.rpgskilltree.core;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CoreProgressionStateCodecTest {
    public static void main(String[] args) {
        nativeStateRoundTripPreservesUncappedProgression();
        migratedStateRoundTripPreservesAuditAndLedger();
        codecPersistsOnlyBoundedRecentTransactionWindow();
        malformedPayloadsAreRejected();
        invalidCoreStateMetadataIsRejected();
        System.out.println("CoreProgressionStateCodecTest: PASS");
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            7L,
            "rpgskilltree:codec_test",
            List.of(
                new LevelCurveBand(0L, 100L, 2L),
                new LevelCurveBand(100L, 500L, 5L)
            ),
            new MainPerkBudget(30L)
        );
    }

    private static void nativeStateRoundTripPreservesUncappedProgression() {
        CorePointLedger points = CorePointLedger.empty()
            .apply(CorePointTransaction.credit("earn:level", CorePointTransactionKind.EARN, 15L, "level", 7L))
            .apply(CorePointTransaction.allocate("spend:attribute", CorePointTransactionKind.SPEND, 4L, "attribute:strength", CorePointAllocation.ATTRIBUTE, 7L))
            .apply(CorePointTransaction.allocate("spend:perk", CorePointTransactionKind.SPEND, 3L, "perk:main", CorePointAllocation.MAIN_PERK, 7L));
        CoreProgressionState source = CoreProgressionState.nativeState(
            new CharacterProgressionState(5_000_000_000L, 123L),
            points,
            rules()
        );

        byte[] encoded = CoreProgressionStateCodec.encode(source);
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(encoded);

        eq(source.characterProgression(), decoded.characterProgression());
        eq(source.rulesVersion(), decoded.rulesVersion());
        eq(source.rulesFingerprint(), decoded.rulesFingerprint());
        eq(0, decoded.migrationSourceFormatVersion());
        eq(0L, decoded.discardedLegacyCapXp());
        eq(source.corePoints().checkpoint(), decoded.corePoints().checkpoint());
    }

    private static void migratedStateRoundTripPreservesAuditAndLedger() {
        ProgressionState legacyEmpty = ProgressionState.empty();
        PassivePointLedger legacyPoints = PassivePointLedger.of(
            Map.of(PassivePointSource.LEVEL, 12, PassivePointSource.BOSS, 3),
            5
        );
        CharacterLevelCurve legacyCurve = CharacterLevelCurve.defaultCurve();
        ProgressionState legacy = new ProgressionState(
            legacyCurve.xpRequiredForLevel(25) + 123L,
            legacyPoints,
            legacyEmpty.bossProgress(),
            legacyEmpty.classProgression(),
            legacyEmpty.mastery(),
            legacyEmpty.classChoices(),
            legacyEmpty.specializations(),
            legacyEmpty.finalTriads(),
            legacyEmpty.passiveNodes(),
            legacyEmpty.discoveries()
        );
        LegacyProgressionMigrationResult migration = LegacyProgressionMigration.migrate(legacy, 4, rules());
        CoreProgressionState source = CoreProgressionState.fromMigration(migration);
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(CoreProgressionStateCodec.encode(source));

        eq(source.characterProgression(), decoded.characterProgression());
        eq(4, decoded.migrationSourceFormatVersion());
        eq(migration.discardedLegacyCapXp(), decoded.discardedLegacyCapXp());
        eq(migration.targetRulesFingerprint(), decoded.rulesFingerprint());
        eq(source.corePoints().checkpoint(), decoded.corePoints().checkpoint());
    }

    private static void codecPersistsOnlyBoundedRecentTransactionWindow() {
        CorePointLedger points = CorePointLedger.empty();
        int operations = CorePointLedger.RECENT_TRANSACTION_LIMIT + 31;
        for (int i = 0; i < operations; i++) {
            points = points.apply(CorePointTransaction.credit(
                "codec:bulk:" + i,
                CorePointTransactionKind.EARN,
                1L,
                "level",
                7L
            ));
        }
        CoreProgressionState source = CoreProgressionState.nativeState(
            CharacterProgressionState.empty(),
            points,
            rules()
        );
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(CoreProgressionStateCodec.encode(source));

        eq((long) operations, decoded.corePoints().totalCredits());
        eq(CorePointLedger.RECENT_TRANSACTION_LIMIT, decoded.corePoints().transactions().size());
        eq((long) operations, decoded.corePoints().creditTotalsBySource().get("level"));
    }

    private static void malformedPayloadsAreRejected() {
        CoreProgressionState source = CoreProgressionState.nativeState(
            CharacterProgressionState.empty(), CorePointLedger.empty(), rules());
        byte[] valid = CoreProgressionStateCodec.encode(source);

        byte[] unsupportedVersion = valid.clone();
        ByteBuffer.wrap(unsupportedVersion).putInt(CoreProgressionStateCodec.CURRENT_VERSION + 1);
        expect(IllegalArgumentException.class, () -> CoreProgressionStateCodec.decode(unsupportedVersion));

        byte[] trailing = Arrays.copyOf(valid, valid.length + 1);
        trailing[trailing.length - 1] = 1;
        expect(IllegalArgumentException.class, () -> CoreProgressionStateCodec.decode(trailing));

        expect(IllegalArgumentException.class, () -> CoreProgressionStateCodec.decode(
            Arrays.copyOf(valid, valid.length - 1)
        ));
    }

    private static void invalidCoreStateMetadataIsRejected() {
        expect(IllegalArgumentException.class, () -> new CoreProgressionState(
            CharacterProgressionState.empty(),
            CorePointLedger.empty(),
            1L,
            "not-a-sha256",
            0,
            0L
        ));
        expect(IllegalArgumentException.class, () -> new CoreProgressionState(
            CharacterProgressionState.empty(),
            CorePointLedger.empty(),
            1L,
            rules().fingerprint(),
            0,
            1L
        ));
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
