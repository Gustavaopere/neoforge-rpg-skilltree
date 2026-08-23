package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

/** Regression coverage for data-driven provider identities such as Mage and Sorcerer. */
public final class ProviderIdentityClassTest {
    private static final String ARCANE_AWAKENING = "rpgskilltree:arcane_000";

    public static void main(String[] args) {
        providerPoliciesAwardIdentityMastery();
        requiresBothTreeInvestmentAndProviderMastery();
        mageAndSorcererCanCoexist();
        respecRemovesIdentityWithoutDeletingMastery();
        System.out.println("ProviderIdentityClassTest: PASS");
    }

    private static void providerPoliciesAwardIdentityMastery() {
        SpellAction iron = new SpellAction(
            new ActionOrigin("test:iron", 0),
            "irons",
            "irons_spellbooks:fireball",
            "fire",
            Set.of(),
            10
        );
        MasteryState ironMastery = MasteryAwardService.apply(MasteryState.empty(), MasteryPolicies.forIron(iron));
        require(ironMastery.experience("irons:casting") == 3, "Iron casts must advance Mage identity mastery");
        require(ironMastery.experience("irons:fire") == 5, "Iron school mastery must remain intact");

        SpellAction ars = new SpellAction(
            new ActionOrigin("test:ars", 0),
            "ars",
            "ars_nouveau:projectile>ars_nouveau:harm",
            "composition",
            Set.of("projectile"),
            10
        );
        MasteryState arsMastery = MasteryAwardService.apply(MasteryState.empty(), MasteryPolicies.forArs(ars));
        require(arsMastery.experience("ars:casting") == 3, "Ars casts must advance Sorcerer identity mastery");
        require(arsMastery.experience("ars:projectile") == 3, "Ars composition mastery must remain intact");
    }

    private static void requiresBothTreeInvestmentAndProviderMastery() {
        ClassUnlockDefinition mage = mageDefinition();

        require(!ClassRequirementPolicy.satisfied(ProgressionState.empty(), mage), "empty state must not be Mage");

        ProgressionState nodeOnly = ProgressionState.empty().withPassiveNodes(
            PassiveNodeProgress.of(Map.of(ARCANE_AWAKENING, 1))
        );
        require(!ClassRequirementPolicy.satisfied(nodeOnly, mage), "node alone must not be Mage");

        ProgressionState masteryOnly = ProgressionState.empty().withMastery(
            MasteryState.of(Map.of("irons:casting", 60))
        );
        require(!ClassRequirementPolicy.satisfied(masteryOnly, mage), "mastery alone must not bypass Arcane Awakening");

        ProgressionState eligible = nodeOnly.withMastery(MasteryState.of(Map.of("irons:casting", 60)));
        require(ClassRequirementPolicy.satisfied(eligible, mage), "node plus Iron mastery must satisfy Mage identity");
        require(
            ProgressionService.reconcileAutomaticClasses(eligible, java.util.List.of(mage)).state()
                .classProgression().isUnlocked("mage"),
            "eligible Mage identity must unlock automatically"
        );
    }

    private static void mageAndSorcererCanCoexist() {
        ProgressionState state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(ARCANE_AWAKENING, 1)))
            .withMastery(MasteryState.of(Map.of(
                "irons:casting", 60,
                "ars:casting", 60
            )));

        ProgressionState reconciled = ProgressionService.reconcileAutomaticClasses(
            state,
            java.util.List.of(mageDefinition(), sorcererDefinition())
        ).state();

        require(reconciled.classProgression().isUnlocked("mage"), "Mage should unlock");
        require(reconciled.classProgression().isUnlocked("sorcerer"), "Sorcerer should unlock");
    }

    private static void respecRemovesIdentityWithoutDeletingMastery() {
        ProgressionState state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(ARCANE_AWAKENING, 1)))
            .withMastery(MasteryState.of(Map.of("irons:casting", 80)));
        state = ProgressionService.reconcileAutomaticClasses(state, java.util.List.of(mageDefinition())).state();
        require(state.classProgression().isUnlocked("mage"), "Mage should initially be unlocked");

        ProgressionState respecced = state.withPassiveNodes(PassiveNodeProgress.empty());
        ProgressionState reconciled = ProgressionService.reconcileAutomaticClasses(
            respecced,
            java.util.List.of(mageDefinition())
        ).state();

        require(!reconciled.classProgression().isUnlocked("mage"), "Mage must be removed when Arcane Awakening is removed");
        require(reconciled.mastery().experience("irons:casting") == 80, "respec must preserve earned mastery");
    }

    private static ClassUnlockDefinition mageDefinition() {
        return new ClassUnlockDefinition(
            "mage",
            Set.of(),
            true,
            0,
            Map.of("irons:casting", 60),
            Set.of(ARCANE_AWAKENING)
        );
    }

    private static ClassUnlockDefinition sorcererDefinition() {
        return new ClassUnlockDefinition(
            "sorcerer",
            Set.of(),
            true,
            0,
            Map.of("ars:casting", 60),
            Set.of(ARCANE_AWAKENING)
        );
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
