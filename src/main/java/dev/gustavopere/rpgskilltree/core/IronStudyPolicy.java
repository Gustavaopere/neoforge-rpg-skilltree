package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Progression gate for permanently cataloguing Iron's spells into spellbooks.
 * Scroll casting remains the practice path; advanced inscription requires demonstrated
 * provider and school mastery, and tier 3+ spells require the emergent Mage identity.
 */
public final class IronStudyPolicy {
    private static final int CASTING_XP_PER_TIER = 30;
    private static final int SCHOOL_XP_PER_TIER = 15;
    private static final int MAGE_REQUIRED_FROM_LEVEL = 3;

    private IronStudyPolicy() {}

    public static Requirement requirementForLevel(int spellLevel) {
        if (spellLevel < 1) throw new IllegalArgumentException("spellLevel must be >= 1");
        int tiersAboveEntry = spellLevel - 1;
        return new Requirement(
            Math.multiplyExact(tiersAboveEntry, CASTING_XP_PER_TIER),
            Math.multiplyExact(tiersAboveEntry, SCHOOL_XP_PER_TIER),
            spellLevel >= MAGE_REQUIRED_FROM_LEVEL
        );
    }

    public static Evaluation evaluate(
        MasteryState mastery,
        String schoolMasteryLane,
        int spellLevel,
        boolean mageIdentity
    ) {
        Objects.requireNonNull(mastery);
        Objects.requireNonNull(schoolMasteryLane);
        if (schoolMasteryLane.isBlank()) throw new IllegalArgumentException("schoolMasteryLane must not be blank");

        Requirement requirement = requirementForLevel(spellLevel);
        int casting = mastery.experience("irons:casting");
        int school = mastery.experience(schoolMasteryLane);
        boolean mageSatisfied = !requirement.mageRequired() || mageIdentity;
        return new Evaluation(
            casting >= requirement.castingMastery()
                && school >= requirement.schoolMastery()
                && mageSatisfied,
            requirement,
            casting,
            school,
            mageSatisfied
        );
    }

    public record Requirement(int castingMastery, int schoolMastery, boolean mageRequired) {
        public Requirement {
            if (castingMastery < 0 || schoolMastery < 0) {
                throw new IllegalArgumentException("mastery requirements must be >= 0");
            }
        }
    }

    public record Evaluation(
        boolean allowed,
        Requirement requirement,
        int currentCastingMastery,
        int currentSchoolMastery,
        boolean mageSatisfied
    ) {
        public Evaluation {
            Objects.requireNonNull(requirement);
        }
    }
}
