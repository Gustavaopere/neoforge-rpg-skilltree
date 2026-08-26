package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public final class ProgressionRulesSnapshotTest {
    public static void main(String[] args) {
        segmentedCurveStartsAtLevelZeroAndCrossesBands();
        hugeLevelsAreCalculatedWithoutFiniteThresholdTables();
        snapshotFingerprintIsDeterministicAndContentSensitive();
        invalidCurveDefinitionsAreRejected();
        System.out.println("ProgressionRulesSnapshotTest: PASS");
    }

    private static void segmentedCurveStartsAtLevelZeroAndCrossesBands() {
        var bands = List.of(
            new LevelCurveBand(0L, 100L, 10L),
            new LevelCurveBand(3L, 150L, 20L)
        );
        InfiniteLevelCurve curve = new SegmentedInfiniteLevelCurve(bands);

        eq(BigInteger.ZERO, curve.cumulativeXpToReachLevel(0L));
        eq(BigInteger.valueOf(100L), curve.xpToNextLevel(0L));
        eq(BigInteger.valueOf(110L), curve.xpToNextLevel(1L));
        eq(BigInteger.valueOf(120L), curve.xpToNextLevel(2L));
        eq(BigInteger.valueOf(150L), curve.xpToNextLevel(3L));
        eq(BigInteger.valueOf(480L), curve.cumulativeXpToReachLevel(4L));
    }

    private static void hugeLevelsAreCalculatedWithoutFiniteThresholdTables() {
        InfiniteLevelCurve curve = new SegmentedInfiniteLevelCurve(List.of(
            new LevelCurveBand(0L, 100L, 2L)
        ));
        BigInteger atFiveBillion = curve.cumulativeXpToReachLevel(5_000_000_000L);
        eq(true, atFiveBillion.signum() > 0);
        eq(BigInteger.valueOf(10_000_000_100L), curve.xpToNextLevel(5_000_000_000L));
    }

    private static void snapshotFingerprintIsDeterministicAndContentSensitive() {
        var curve = List.of(
            new LevelCurveBand(0L, 100L, 10L),
            new LevelCurveBand(50L, 800L, 15L)
        );
        var a = new ProgressionRulesSnapshot(7L, "rpgskilltree:alpha_rules", curve, new MainPerkBudget(30L));
        var b = new ProgressionRulesSnapshot(7L, "rpgskilltree:alpha_rules", curve, new MainPerkBudget(30L));
        var changedCurve = new ProgressionRulesSnapshot(
            7L,
            "rpgskilltree:alpha_rules",
            List.of(new LevelCurveBand(0L, 100L, 11L)),
            new MainPerkBudget(30L)
        );
        var changedBudget = new ProgressionRulesSnapshot(7L, "rpgskilltree:alpha_rules", curve, new MainPerkBudget(31L));

        eq(a.fingerprint(), b.fingerprint());
        eq(false, a.fingerprint().equals(changedCurve.fingerprint()));
        eq(false, a.fingerprint().equals(changedBudget.fingerprint()));
        eq(BigInteger.valueOf(100L), a.levelCurve().xpToNextLevel(0L));
    }

    private static void invalidCurveDefinitionsAreRejected() {
        expect(IllegalArgumentException.class, () -> new SegmentedInfiniteLevelCurve(List.of()));
        expect(IllegalArgumentException.class, () -> new SegmentedInfiniteLevelCurve(List.of(
            new LevelCurveBand(1L, 100L, 0L)
        )));
        expect(IllegalArgumentException.class, () -> new SegmentedInfiniteLevelCurve(List.of(
            new LevelCurveBand(0L, 100L, 0L),
            new LevelCurveBand(10L, 50L, 0L)
        )));
        expect(IllegalArgumentException.class, () -> new ProgressionRulesSnapshot(
            0L,
            "rpgskilltree:bad",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(0L)
        ));
        expect(IllegalArgumentException.class, () -> new ProgressionRulesSnapshot(
            1L,
            "not_namespaced",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(0L)
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
