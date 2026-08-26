package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class LegacyProgressionMigrationTest {
    public static void main(String[] args) {
        legacyOriginMapsToLevelZero();
        completedLevelUpsAndPartialProgressArePreserved();
        legacyCapExcessDoesNotCreateRetroactiveLevels();
        persistedPointLedgerIsMigratedInsteadOfRecomputed();
        migrationIsDeterministicAndVersioned();
        invalidSourceVersionsAreRejected();
        System.out.println("LegacyProgressionMigrationTest: PASS");
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            7L,
            "rpgskilltree:migration_test",
            List.of(new LevelCurveBand(0L, 200L, 0L)),
            new MainPerkBudget(30L)
        );
    }

    private static void legacyOriginMapsToLevelZero() {
        LegacyProgressionMigrationResult migrated = LegacyProgressionMigration.migrate(
            ProgressionState.empty(),
            4,
            rules()
        );
        eq(new CharacterProgressionState(0L, 0L), migrated.characterProgression());
        eq(0L, migrated.discardedLegacyCapXp());
        eq(0L, migrated.corePoints().totalCredits());
    }

    private static void completedLevelUpsAndPartialProgressArePreserved() {
        CharacterLevelCurve legacyCurve = CharacterLevelCurve.defaultCurve();
        int legacyLevel = 10;
        long floor = legacyCurve.xpRequiredForLevel(legacyLevel);
        long legacyCost = legacyCurve.xpToNextLevel(legacyLevel);
        long legacyInto = legacyCost / 2L;
        ProgressionState legacy = stateWith(floor + legacyInto, PassivePointLedger.empty());

        LegacyProgressionMigrationResult migrated = LegacyProgressionMigration.migrate(legacy, 4, rules());
        long expectedPartial = BigInteger.valueOf(legacyInto)
            .multiply(BigInteger.valueOf(200L))
            .divide(BigInteger.valueOf(legacyCost))
            .longValueExact();

        eq(9L, migrated.characterProgression().level());
        eq(expectedPartial, migrated.characterProgression().xpIntoLevel());
        eq(0L, migrated.discardedLegacyCapXp());

        ProgressionState exactBoundary = stateWith(
            legacyCurve.xpRequiredForLevel(20),
            PassivePointLedger.empty()
        );
        eq(
            new CharacterProgressionState(19L, 0L),
            LegacyProgressionMigration.migrate(exactBoundary, 4, rules()).characterProgression()
        );
    }

    private static void legacyCapExcessDoesNotCreateRetroactiveLevels() {
        CharacterLevelCurve legacyCurve = CharacterLevelCurve.defaultCurve();
        long level100Floor = legacyCurve.xpRequiredForLevel(100);
        long inertCapExcess = 12_345L;
        ProgressionState legacy = stateWith(level100Floor + inertCapExcess, PassivePointLedger.empty());

        LegacyProgressionMigrationResult migrated = LegacyProgressionMigration.migrate(legacy, 4, rules());
        eq(new CharacterProgressionState(99L, 0L), migrated.characterProgression());
        eq(inertCapExcess, migrated.discardedLegacyCapXp());
    }

    private static void persistedPointLedgerIsMigratedInsteadOfRecomputed() {
        PassivePointLedger legacyLedger = PassivePointLedger.of(
            Map.of(
                PassivePointSource.LEVEL, 7,
                PassivePointSource.BOSS, 3,
                PassivePointSource.ADMIN, 2
            ),
            4
        );
        ProgressionState legacy = stateWith(0L, legacyLedger);

        LegacyProgressionMigrationResult migrated = LegacyProgressionMigration.migrate(legacy, 4, rules());
        CorePointLedger points = migrated.corePoints();

        eq(12L, points.totalCredits());
        eq(4L, points.allocated(CorePointAllocation.MAIN_PERK));
        eq(0L, points.allocated(CorePointAllocation.ATTRIBUTE));
        eq(8L, points.available());
        eq(3L, points.transactions().stream().filter(t -> t.kind() == CorePointTransactionKind.MIGRATION).count());
        eq(1L, points.transactions().stream().filter(t -> t.kind() == CorePointTransactionKind.SPEND).count());
    }

    private static void migrationIsDeterministicAndVersioned() {
        PassivePointLedger legacyLedger = PassivePointLedger.of(
            Map.of(PassivePointSource.ADVANCEMENT, 5, PassivePointSource.LEVEL, 8),
            2
        );
        ProgressionState legacy = stateWith(1_234L, legacyLedger);
        LegacyProgressionMigrationResult first = LegacyProgressionMigration.migrate(legacy, 4, rules());
        LegacyProgressionMigrationResult second = LegacyProgressionMigration.migrate(legacy, 4, rules());

        eq(first.characterProgression(), second.characterProgression());
        eq(first.corePoints().transactions(), second.corePoints().transactions());
        eq(4, first.sourceFormatVersion());
        eq(7L, first.targetRulesVersion());
        eq(rules().fingerprint(), first.targetRulesFingerprint());
    }

    private static void invalidSourceVersionsAreRejected() {
        expect(IllegalArgumentException.class, () -> LegacyProgressionMigration.migrate(
            ProgressionState.empty(),
            0,
            rules()
        ));
        expect(IllegalArgumentException.class, () -> LegacyProgressionMigration.migrate(
            ProgressionState.empty(),
            ProgressionStateCodec.CURRENT_VERSION + 1,
            rules()
        ));
    }

    private static ProgressionState stateWith(long totalXp, PassivePointLedger ledger) {
        ProgressionState empty = ProgressionState.empty();
        return new ProgressionState(
            totalXp,
            ledger,
            empty.bossProgress(),
            empty.classProgression(),
            empty.mastery(),
            empty.classChoices(),
            empty.specializations(),
            empty.finalTriads(),
            empty.passiveNodes(),
            empty.discoveries()
        );
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
