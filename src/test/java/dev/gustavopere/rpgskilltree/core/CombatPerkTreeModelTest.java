package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public final class CombatPerkTreeModelTest {
    public static void main(String[] args) {
        var all = CombatPerkTreeModel.all();
        require(all.size() == 50, "physical model must contain exactly A0001-A0050");
        require(all.stream().map(CombatPerkTreeModel.Node::nodeId).distinct().count() == 50, "physical ids must be unique");

        var swordRoot = CombatPerkTreeModel.node("A0001").orElseThrow();
        require(swordRoot.startingPoint(), "A0001 must be a branch root");
        require(swordRoot.minCharacterLevel() == 8, "A0001 level gate");
        require(swordRoot.requiredSpecializations().equals(Set.of("epic_sword")), "A0001 specialization gate");
        require(swordRoot.requiredMastery().equals(Map.of("epicfight:sword", 60)), "A0001 mastery gate");

        var hammerRoot = CombatPerkTreeModel.node("A0025").orElseThrow();
        require(hammerRoot.minCharacterLevel() == 10, "A0025 level gate");
        require(hammerRoot.requiredMastery().equals(Map.of("epicfight:heavy", 70)), "A0025 heavy mastery gate");

        var maceRoot = CombatPerkTreeModel.node("A0031").orElseThrow();
        require(maceRoot.requiredSpecializations().equals(Set.of("combat_mace")), "A0031 mace specialization gate");
        require(maceRoot.requiredMastery().equals(Map.of("combat:mace", 60)), "A0031 mace mastery lane");

        var bowRoot = CombatPerkTreeModel.node("A0043").orElseThrow();
        require(bowRoot.requiredMastery().equals(Map.of("combat:bow", 60)), "A0043 bow mastery lane");
        require(bowRoot.domains().equals(Set.of("AGILITY")), "A0043 bow domain");

        var swordCapstone = CombatPerkTreeModel.node("A0006").orElseThrow();
        require(swordCapstone.requiredMastery().equals(Map.of("epicfight:sword", 80)), "A0006 capstone mastery");
        require(swordCapstone.requiredNodeRanks().equals(Map.of(
            "rpgskilltree:combat/a0004", 1,
            "rpgskilltree:combat/a0005", 1
        )), "A0006 ranked prerequisites");
        require(swordCapstone.neighbors().equals(Set.of(
            "rpgskilltree:combat/a0004",
            "rpgskilltree:combat/a0005"
        )), "A0006 graph neighbors");

        var crossbowSecond = CombatPerkTreeModel.node("A0050").orElseThrow();
        require(!crossbowSecond.startingPoint(), "A0050 is not a root");
        require(crossbowSecond.requiredNodeRanks().equals(Map.of("rpgskilltree:combat/a0049", 2)), "A0050 dependency");
        require(crossbowSecond.neighbors().equals(Set.of("rpgskilltree:combat/a0049")), "A0050 graph neighbor");

        require(CombatPerkTreeModel.node("A0051").isEmpty(), "A0051 must remain outside this model");
        System.out.println("CombatPerkTreeModelTest: PASS");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
