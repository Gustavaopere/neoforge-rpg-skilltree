package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class CombatPerkNodeBindingTest {
    public static void main(String[] args) {
        require(CombatPerkNodeBinding.nodeId("A0001").equals("rpgskilltree:combat/a0001"), "A0001 physical id");
        require(CombatPerkNodeBinding.nodeId("A0050").equals("rpgskilltree:combat/a0050"), "A0050 physical id");
        require(CombatPerkNodeBinding.catalogCode("rpgskilltree:combat/a0001").orElseThrow().equals("A0001"), "reverse A0001");
        require(CombatPerkNodeBinding.catalogCode("rpgskilltree:combat/a0050").orElseThrow().equals("A0050"), "reverse A0050");
        require(CombatPerkNodeBinding.catalogCode("rpgskilltree:martial_000").isEmpty(), "legacy martial nodes must not alias A codes");
        require(CombatPerkNodeBinding.catalogCode("rpgskilltree:combat/a0051").isEmpty(), "A0051 must remain outside this batch");

        PassiveNodeProgress progress = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:combat/a0001", 3,
            "rpgskilltree:combat/a0002", 2,
            "rpgskilltree:martial_000", 1
        ));
        CombatPerkRanks ranks = CombatPerkNodeBinding.ranks(progress);
        require(ranks.rank("A0001") == 3, "A0001 rank binding");
        require(ranks.rank("A0002") == 2, "A0002 rank binding");
        require(ranks.ranks().size() == 2, "unrelated physical nodes must be ignored");

        System.out.println("CombatPerkNodeBindingTest: PASS");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
