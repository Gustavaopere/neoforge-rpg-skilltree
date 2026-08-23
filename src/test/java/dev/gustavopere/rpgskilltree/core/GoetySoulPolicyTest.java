package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public final class GoetySoulPolicyTest {
    public static void main(String[] args) {
        var emptyNodes = PassiveNodeProgress.empty();
        var emptyClasses = ClassProgressionState.empty();
        check(100 == GoetySoulPolicy.adjustedGain(emptyNodes, emptyClasses, 100), "empty progression must not alter Soul gain");
        check(100 == GoetySoulPolicy.adjustedSpellCost(emptyNodes, emptyClasses, Set.of("summoning"), 100), "empty progression must not alter Soul cost");

        var occult = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:occult_000", 1,
            "rpgskilltree:occult_001", 1
        ));
        check(110 == GoetySoulPolicy.adjustedGain(occult, emptyClasses, 100), "two early OCCULT nodes should add 10% Soul gain");
        check(90 == GoetySoulPolicy.adjustedSpellCost(occult, emptyClasses, Set.of(), 100), "two early OCCULT nodes should reduce Goety spell Soul cost by 10%");

        var warlock = ClassProgressionState.of(Set.of("warlock"));
        check(120 == GoetySoulPolicy.adjustedGain(occult, warlock, 100), "Warlock should deepen Soul harvesting");
        check(80 == GoetySoulPolicy.adjustedSpellCost(occult, warlock, Set.of("void"), 100), "Warlock should deepen Soul casting efficiency");

        var necromancer = ClassProgressionState.of(Set.of("necromancer"));
        check(80 == GoetySoulPolicy.adjustedSpellCost(occult, necromancer, Set.of("summoning"), 100), "Necromancer should reduce summoning Soul costs");
        check(90 == GoetySoulPolicy.adjustedSpellCost(occult, necromancer, Set.of("frost"), 100), "Necromancer discount must not leak into non-summoning magic");

        CombatAction servantKill = new CombatAction(
            new ActionOrigin("goety:servant_kill", 0),
            "goety",
            "servant",
            "minecraft:zombie",
            Set.of("servant_kill", "summoning", "necromancer"),
            20.0D
        );
        var awards = MasteryPolicies.forGoetyServant(servantKill);
        check(awards.stream().anyMatch(a -> a.laneId().equals("goety:servants")), "servant hostile kill should train servant mastery");
        check(awards.stream().anyMatch(a -> a.laneId().equals("summoning:practice")), "servant hostile kill should feed unified summoning practice");
        check(awards.stream().anyMatch(a -> a.laneId().equals("goety:necromancy")), "Necromancer-owned servant kill should feed necromancy mastery");

        CombatAction procKill = new CombatAction(
            new ActionOrigin("goety:servant_kill", 1),
            "goety", "servant", "minecraft:zombie", Set.of("servant_kill"), 20.0D
        );
        check(MasteryPolicies.forGoetyServant(procKill).isEmpty(), "proc-depth servant actions must not produce mastery loops");

        System.out.println("GoetySoulPolicyTest PASS");
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
