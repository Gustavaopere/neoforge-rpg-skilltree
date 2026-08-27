package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class QuestProgressionHooksFoundationTest {
    public static void main(String[] args) {
        snapshotCombinesCanonicalAndLegacyProgressionWithoutAliasing();
        declarativeConditionsCoverCoreQuestFacts();
        conditionIdsAndSubjectsAreValidated();
        hugeLevelsAndAttributeRanksRemainSupported();
        System.out.println("QuestProgressionHooksFoundationTest: PASS");
    }

    private static void snapshotCombinesCanonicalAndLegacyProgressionWithoutAliasing() {
        CoreProgressionQuerySnapshot core = coreSnapshot(
            42L,
            AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, 7L))
        );
        Map<String, Integer> masterySource = new HashMap<>();
        masterySource.put("fire", 120);
        MasteryState mastery = MasteryState.of(masterySource);
        ClassProgressionState classes = ClassProgressionState.of(Set.of("mage", "spellblade"));
        PassiveNodeProgress perks = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:a0001", 2,
            "rpgskilltree:a0042", 1
        ));

        QuestProgressionSnapshot snapshot = QuestProgressionSnapshot.from(core, mastery, classes, perks);
        eq(42L, snapshot.level());
        eq(120L, snapshot.masteryExperience("fire"));
        eq(true, snapshot.classUnlocked("mage"));
        eq(false, snapshot.classUnlocked("warrior"));
        eq(2L, snapshot.perkRank("rpgskilltree:a0001"));
        eq(7L, snapshot.attributeRank(AttributeId.INTELLIGENCE));

        masterySource.put("fire", 999);
        eq(120L, snapshot.masteryExperience("fire"));
        expect(UnsupportedOperationException.class, () -> snapshot.masteryExperience().put("ice", 1));
        expect(UnsupportedOperationException.class, () -> snapshot.unlockedClassIds().add("warrior"));
        expect(UnsupportedOperationException.class, () -> snapshot.perkRanks().put("rpgskilltree:a9999", 1));
    }

    private static void declarativeConditionsCoverCoreQuestFacts() {
        QuestProgressionSnapshot snapshot = QuestProgressionSnapshot.from(
            coreSnapshot(42L, AttributeRanks.of(Map.of(AttributeId.STRENGTH, 12L))),
            MasteryState.of(Map.of("fire", 120, "projectile", 30)),
            ClassProgressionState.of(Set.of("mage")),
            PassiveNodeProgress.of(Map.of("rpgskilltree:a0001", 2))
        );

        assertMatch(snapshot, QuestProgressionCondition.minimumLevel("rpgskilltree:quest/level_40", 40L), 42L, 40L, true);
        assertMatch(snapshot, QuestProgressionCondition.minimumMasteryXp("rpgskilltree:quest/fire_100", "fire", 100L), 120L, 100L, true);
        assertMatch(snapshot, QuestProgressionCondition.classUnlocked("rpgskilltree:quest/mage", "mage"), 1L, 1L, true);
        assertMatch(snapshot, QuestProgressionCondition.perkRankAtLeast("rpgskilltree:quest/a0001_rank_3", "rpgskilltree:a0001", 3L), 2L, 3L, false);
        assertMatch(snapshot, QuestProgressionCondition.attributeRankAtLeast("rpgskilltree:quest/strength_10", AttributeId.STRENGTH, 10L), 12L, 10L, true);

        QuestConditionEvaluation missingMastery = QuestProgressionConditionService.evaluate(
            snapshot,
            QuestProgressionCondition.minimumMasteryXp("rpgskilltree:quest/unknown_mastery", "alchemy", 1L)
        );
        eq(0L, missingMastery.actualValue());
        eq(false, missingMastery.matched());
    }

    private static void conditionIdsAndSubjectsAreValidated() {
        expect(IllegalArgumentException.class, () -> QuestProgressionCondition.minimumLevel("level_10", 10L));
        expect(IllegalArgumentException.class, () -> QuestProgressionCondition.minimumLevel("RpgSkillTree:level_10", 10L));
        expect(IllegalArgumentException.class, () -> QuestProgressionCondition.minimumLevel("rpgskilltree:Level 10", 10L));
        expect(IllegalArgumentException.class, () -> QuestProgressionCondition.minimumLevel("rpgskilltree:negative", -1L));
        expect(IllegalArgumentException.class, () -> QuestProgressionCondition.minimumMasteryXp("rpgskilltree:test", "", 1L));
        expect(IllegalArgumentException.class, () -> QuestProgressionCondition.classUnlocked("rpgskilltree:test", ""));
        expect(IllegalArgumentException.class, () -> QuestProgressionCondition.perkRankAtLeast("rpgskilltree:test", "", 1L));
    }

    private static void hugeLevelsAndAttributeRanksRemainSupported() {
        long huge = 5_000_000_000L;
        QuestProgressionSnapshot snapshot = QuestProgressionSnapshot.from(
            coreSnapshot(huge, AttributeRanks.of(Map.of(AttributeId.DETERMINATION, huge))),
            MasteryState.empty(),
            ClassProgressionState.empty(),
            PassiveNodeProgress.empty()
        );
        assertMatch(snapshot, QuestProgressionCondition.minimumLevel("rpgskilltree:quest/huge_level", huge), huge, huge, true);
        assertMatch(snapshot, QuestProgressionCondition.attributeRankAtLeast("rpgskilltree:quest/huge_determination", AttributeId.DETERMINATION, huge), huge, huge, true);
    }

    private static CoreProgressionQuerySnapshot coreSnapshot(long level, AttributeRanks attributes) {
        return new CoreProgressionQuerySnapshot(
            level,
            0L,
            BigInteger.valueOf(100L),
            20L,
            8L,
            5L,
            7L,
            10L,
            3L,
            0L,
            attributes,
            1L,
            "a".repeat(64)
        );
    }

    private static void assertMatch(
        QuestProgressionSnapshot snapshot,
        QuestProgressionCondition condition,
        long actual,
        long required,
        boolean matched
    ) {
        QuestConditionEvaluation evaluation = QuestProgressionConditionService.evaluate(snapshot, condition);
        eq(condition.conditionId(), evaluation.conditionId());
        eq(condition.fact(), evaluation.fact());
        eq(actual, evaluation.actualValue());
        eq(required, evaluation.requiredMinimum());
        eq(matched, evaluation.matched());
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
