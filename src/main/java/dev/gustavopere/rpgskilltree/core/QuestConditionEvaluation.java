package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Auditable result of one declarative quest/progression condition. */
public record QuestConditionEvaluation(
    String conditionId,
    QuestProgressionFact fact,
    long actualValue,
    long requiredMinimum,
    boolean matched
) {
    public QuestConditionEvaluation {
        if (conditionId == null || conditionId.isBlank()) {
            throw new IllegalArgumentException("conditionId must not be blank");
        }
        Objects.requireNonNull(fact, "fact");
        if (actualValue < 0L) throw new IllegalArgumentException("actualValue must be non-negative");
        if (requiredMinimum < 0L) throw new IllegalArgumentException("requiredMinimum must be non-negative");
        if (matched != (actualValue >= requiredMinimum)) {
            throw new IllegalArgumentException("matched flag is inconsistent with actual/required values");
        }
    }
}
