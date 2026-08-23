package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public final class IronStudyPolicyTest {
    public static void main(String[] args) {
        entrySpellsNeedNoPractice();
        tierTwoRequiresProviderAndSchoolPractice();
        tierThreeAlsoRequiresMageIdentity();
        requirementsScaleForAddonSpellLevels();
        ironMasteryScalesWithRealManaSpend();
        System.out.println("IronStudyPolicyTest: PASS");
    }

    private static void entrySpellsNeedNoPractice() {
        var requirement = IronStudyPolicy.requirementForLevel(1);
        require(requirement.castingMastery() == 0, "level 1 casting requirement");
        require(requirement.schoolMastery() == 0, "level 1 school requirement");
        require(!requirement.mageRequired(), "level 1 must not require Mage");
        require(
            IronStudyPolicy.evaluate(MasteryState.empty(), "irons:fire", 1, false).allowed(),
            "Arcane-access handling lives outside study policy; level 1 itself should be study-eligible"
        );
    }

    private static void tierTwoRequiresProviderAndSchoolPractice() {
        var requirement = IronStudyPolicy.requirementForLevel(2);
        require(requirement.castingMastery() == 30, "level 2 casting requirement");
        require(requirement.schoolMastery() == 15, "level 2 school requirement");
        require(!requirement.mageRequired(), "level 2 should remain pre-Mage apprenticeship");

        require(!IronStudyPolicy.evaluate(
            MasteryState.of(Map.of("irons:casting", 29, "irons:fire", 15)), "irons:fire", 2, false
        ).allowed(), "provider mastery must be enforced");
        require(!IronStudyPolicy.evaluate(
            MasteryState.of(Map.of("irons:casting", 30, "irons:fire", 14)), "irons:fire", 2, false
        ).allowed(), "school mastery must be enforced");
        require(IronStudyPolicy.evaluate(
            MasteryState.of(Map.of("irons:casting", 30, "irons:fire", 15)), "irons:fire", 2, false
        ).allowed(), "meeting both apprenticeship requirements must allow inscription");
    }

    private static void tierThreeAlsoRequiresMageIdentity() {
        MasteryState mastery = MasteryState.of(Map.of("irons:casting", 60, "irons:fire", 30));
        var withoutMage = IronStudyPolicy.evaluate(mastery, "irons:fire", 3, false);
        require(!withoutMage.allowed(), "tier 3 inscription must require Mage identity");
        require(!withoutMage.mageSatisfied(), "Mage failure must be visible in evaluation");
        require(IronStudyPolicy.evaluate(mastery, "irons:fire", 3, true).allowed(), "Mage should unlock tier 3 study");
    }

    private static void requirementsScaleForAddonSpellLevels() {
        var requirement = IronStudyPolicy.requirementForLevel(7);
        require(requirement.castingMastery() == 180, "level 7 provider requirement should scale linearly");
        require(requirement.schoolMastery() == 90, "level 7 school requirement should scale linearly");
        require(requirement.mageRequired(), "advanced addon tiers must remain Mage study");
    }

    private static void ironMasteryScalesWithRealManaSpend() {
        SpellAction cheap = new SpellAction(
            new ActionOrigin("test:scroll", 0), "irons", "irons_spellbooks:firebolt", "fire", Set.of(), 10
        );
        MasteryState cheapState = MasteryAwardService.apply(MasteryState.empty(), MasteryPolicies.forIron(cheap));
        require(cheapState.experience("irons:casting") == 3, "cheap casts keep existing provider floor");
        require(cheapState.experience("irons:fire") == 5, "cheap casts keep existing school floor");

        SpellAction costly = new SpellAction(
            new ActionOrigin("test:spellbook", 0), "irons", "irons_spellbooks:expensive_spell", "fire", Set.of(), 120
        );
        MasteryState costlyState = MasteryAwardService.apply(MasteryState.empty(), MasteryPolicies.forIron(costly));
        require(costlyState.experience("irons:casting") == 5, "real mana expenditure should deepen provider practice");
        require(costlyState.experience("irons:fire") == 7, "real mana expenditure should deepen school practice");
        require(costlyState.experience("magic:casting") == 2, "shared casting mastery must stay provider-neutral");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
