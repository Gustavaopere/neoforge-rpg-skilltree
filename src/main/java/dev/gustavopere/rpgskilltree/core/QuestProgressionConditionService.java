package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure evaluator for declarative quest/progression conditions. */
public final class QuestProgressionConditionService {
    private QuestProgressionConditionService() {}

    public static QuestConditionEvaluation evaluate(
        QuestProgressionSnapshot snapshot,
        QuestProgressionCondition condition
    ) {
        Objects.requireNonNull(snapshot, "snapshot");
        Objects.requireNonNull(condition, "condition");

        long actual = switch (condition.fact()) {
            case LEVEL -> snapshot.level();
            case MASTERY_XP -> snapshot.masteryExperience(condition.subjectId());
            case CLASS_UNLOCKED -> snapshot.classUnlocked(condition.subjectId()) ? 1L : 0L;
            case SPECIALIZATION_UNLOCKED -> snapshot.specializationUnlocked(condition.subjectId()) ? 1L : 0L;
            case PERK_RANK -> snapshot.perkRank(condition.subjectId());
            case ATTRIBUTE_RANK -> snapshot.attributeRank(condition.attributeId());
        };

        return new QuestConditionEvaluation(
            condition.conditionId(),
            condition.fact(),
            actual,
            condition.minimumValue(),
            actual >= condition.minimumValue()
        );
    }
}
