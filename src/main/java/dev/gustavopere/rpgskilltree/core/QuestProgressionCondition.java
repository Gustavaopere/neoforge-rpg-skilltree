package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

/** Declarative, data-friendly quest condition over the public progression snapshot. */
public record QuestProgressionCondition(
    String conditionId,
    QuestProgressionFact fact,
    String subjectId,
    long minimumValue
) {
    private static final Pattern NAMESPACED_ID = Pattern.compile("[a-z0-9_.-]+:[a-z0-9/._-]+");

    public QuestProgressionCondition {
        Objects.requireNonNull(fact, "fact");
        conditionId = requireNamespacedId(conditionId, "conditionId");
        if (minimumValue < 0L) throw new IllegalArgumentException("minimumValue must be non-negative");

        if (fact == QuestProgressionFact.LEVEL) {
            if (subjectId == null) subjectId = "";
            if (!subjectId.isEmpty()) throw new IllegalArgumentException("LEVEL condition must not have a subjectId");
        } else {
            subjectId = requireText(subjectId, "subjectId");
        }

        if (fact == QuestProgressionFact.CLASS_UNLOCKED && minimumValue != 1L) {
            throw new IllegalArgumentException("CLASS_UNLOCKED requires minimumValue=1");
        }
        if (fact == QuestProgressionFact.ATTRIBUTE_RANK) {
            parseAttribute(subjectId);
        }
    }

    public static QuestProgressionCondition minimumLevel(String conditionId, long minimumLevel) {
        return new QuestProgressionCondition(conditionId, QuestProgressionFact.LEVEL, "", minimumLevel);
    }

    public static QuestProgressionCondition minimumMasteryXp(
        String conditionId,
        String masteryId,
        long minimumXp
    ) {
        return new QuestProgressionCondition(conditionId, QuestProgressionFact.MASTERY_XP, masteryId, minimumXp);
    }

    public static QuestProgressionCondition classUnlocked(String conditionId, String classId) {
        return new QuestProgressionCondition(conditionId, QuestProgressionFact.CLASS_UNLOCKED, classId, 1L);
    }

    public static QuestProgressionCondition perkRankAtLeast(
        String conditionId,
        String perkId,
        long minimumRank
    ) {
        return new QuestProgressionCondition(conditionId, QuestProgressionFact.PERK_RANK, perkId, minimumRank);
    }

    public static QuestProgressionCondition attributeRankAtLeast(
        String conditionId,
        AttributeId attribute,
        long minimumRank
    ) {
        Objects.requireNonNull(attribute, "attribute");
        return new QuestProgressionCondition(
            conditionId,
            QuestProgressionFact.ATTRIBUTE_RANK,
            attribute.serializedId(),
            minimumRank
        );
    }

    AttributeId attributeId() {
        if (fact != QuestProgressionFact.ATTRIBUTE_RANK) {
            throw new IllegalStateException("condition is not an ATTRIBUTE_RANK condition");
        }
        return parseAttribute(subjectId);
    }

    private static AttributeId parseAttribute(String subjectId) {
        for (AttributeId attribute : AttributeId.values()) {
            if (attribute.serializedId().equals(subjectId)) return attribute;
        }
        throw new IllegalArgumentException("unknown attribute id: " + subjectId);
    }

    private static String requireNamespacedId(String value, String label) {
        String text = requireText(value, label);
        if (!NAMESPACED_ID.matcher(text).matches()) {
            throw new IllegalArgumentException(label + " must be a lowercase namespaced id: " + value);
        }
        return text;
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " must not be blank");
        return value;
    }
}
