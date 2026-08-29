package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Immutable integration-facing progression view for quest and narrative adapters. */
public record QuestProgressionSnapshot(
    CoreProgressionQuerySnapshot core,
    Map<String, Long> masteryExperience,
    Set<String> unlockedClassIds,
    Set<String> unlockedSpecializationIds,
    Map<String, Long> perkRanks
) {
    /** Public quest-integration contract revision. Increment only for incompatible semantic changes. */
    public static final int CONTRACT_VERSION = 1;

    public QuestProgressionSnapshot {
        Objects.requireNonNull(core, "core");
        Objects.requireNonNull(masteryExperience, "masteryExperience");
        Objects.requireNonNull(unlockedClassIds, "unlockedClassIds");
        Objects.requireNonNull(unlockedSpecializationIds, "unlockedSpecializationIds");
        Objects.requireNonNull(perkRanks, "perkRanks");

        HashMap<String, Long> masteryCopy = new HashMap<>();
        masteryExperience.forEach((id, value) -> {
            String stableId = requireId(id, "mastery id");
            Long amount = Objects.requireNonNull(value, "mastery XP");
            if (amount < 0L) throw new IllegalArgumentException("mastery XP must be non-negative");
            masteryCopy.put(stableId, amount);
        });

        for (String classId : unlockedClassIds) requireId(classId, "class id");
        for (String specializationId : unlockedSpecializationIds) requireId(specializationId, "specialization id");

        HashMap<String, Long> perkCopy = new HashMap<>();
        perkRanks.forEach((id, value) -> {
            String stableId = requireId(id, "perk id");
            Long rank = Objects.requireNonNull(value, "perk rank");
            if (rank <= 0L) throw new IllegalArgumentException("stored perk rank must be positive");
            perkCopy.put(stableId, rank);
        });

        masteryExperience = Map.copyOf(masteryCopy);
        unlockedClassIds = Set.copyOf(unlockedClassIds);
        unlockedSpecializationIds = Set.copyOf(unlockedSpecializationIds);
        perkRanks = Map.copyOf(perkCopy);
    }

    /** Source-compatible constructor for callers that predate specialization exposure. */
    public QuestProgressionSnapshot(
        CoreProgressionQuerySnapshot core,
        Map<String, Long> masteryExperience,
        Set<String> unlockedClassIds,
        Map<String, Long> perkRanks
    ) {
        this(core, masteryExperience, unlockedClassIds, Set.of(), perkRanks);
    }

    public static QuestProgressionSnapshot from(
        CoreProgressionQuerySnapshot core,
        MasteryState mastery,
        ClassProgressionState classes,
        PassiveNodeProgress perks
    ) {
        return from(core, mastery, classes, SpecializationProgressionState.empty(), perks);
    }

    public static QuestProgressionSnapshot from(
        CoreProgressionQuerySnapshot core,
        MasteryState mastery,
        ClassProgressionState classes,
        SpecializationProgressionState specializations,
        PassiveNodeProgress perks
    ) {
        Objects.requireNonNull(mastery, "mastery");
        Objects.requireNonNull(classes, "classes");
        Objects.requireNonNull(specializations, "specializations");
        Objects.requireNonNull(perks, "perks");

        Map<String, Long> masteryValues = new HashMap<>();
        mastery.experience().forEach((id, value) -> masteryValues.put(id, value.longValue()));
        Map<String, Long> perkValues = new HashMap<>();
        perks.ranks().forEach((id, value) -> perkValues.put(id, value.longValue()));
        return new QuestProgressionSnapshot(
            core,
            masteryValues,
            classes.unlockedClassIds(),
            specializations.unlockedSpecializationIds(),
            perkValues
        );
    }

    public long level() {
        return core.level();
    }

    public long masteryExperience(String masteryId) {
        return masteryExperience.getOrDefault(requireId(masteryId, "mastery id"), 0L);
    }

    public boolean classUnlocked(String classId) {
        return unlockedClassIds.contains(requireId(classId, "class id"));
    }

    public boolean specializationUnlocked(String specializationId) {
        return unlockedSpecializationIds.contains(requireId(specializationId, "specialization id"));
    }

    public long perkRank(String perkId) {
        return perkRanks.getOrDefault(requireId(perkId, "perk id"), 0L);
    }

    public long attributeRank(AttributeId attribute) {
        return core.attributeRanks().rank(Objects.requireNonNull(attribute, "attribute"));
    }

    private static String requireId(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " must not be blank");
        return value;
    }
}
