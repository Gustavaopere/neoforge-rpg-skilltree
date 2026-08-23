package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

public final class EidolonRitualPolicyTest {
    public static void main(String[] args) {
        EidolonRitualAction first = new EidolonRitualAction(
            new ActionOrigin("eidolon:ritual", 0),
            "eidolon:summon_zombie",
            Set.of("confirmed_ritual", "summoning"),
            true
        );
        var firstAwards = MasteryPolicies.forEidolonRitual(first);
        check(experience(firstAwards, "eidolon:ritual") == 8, "first ritual completion should receive the strong discovery reward");
        check(experience(firstAwards, "occult:practice") == 4, "first ritual completion should train occult practice");
        check(experience(firstAwards, "summoning:practice") == 4, "summoning ritual should train unified summoning practice");

        EidolonRitualAction repeat = new EidolonRitualAction(
            new ActionOrigin("eidolon:ritual", 0),
            "eidolon:summon_zombie",
            Set.of("confirmed_ritual", "summoning"),
            false
        );
        var repeatAwards = MasteryPolicies.forEidolonRitual(repeat);
        check(experience(repeatAwards, "eidolon:ritual") == 3, "repeat ritual completion must be reduced");
        check(experience(repeatAwards, "summoning:practice") == 2, "repeat summon ritual reward must be reduced");

        EidolonRitualAction unconfirmed = new EidolonRitualAction(
            new ActionOrigin("eidolon:ritual", 0),
            "eidolon:repelling",
            Set.of(),
            true
        );
        check(MasteryPolicies.forEidolonRitual(unconfirmed).isEmpty(), "ritual attempts must not grant mastery");

        EidolonRitualAction proc = new EidolonRitualAction(
            new ActionOrigin("eidolon:ritual", 1),
            "eidolon:repelling",
            Set.of("confirmed_ritual"),
            true
        );
        check(MasteryPolicies.forEidolonRitual(proc).isEmpty(), "derived ritual callbacks must not create mastery loops");

        System.out.println("EidolonRitualPolicyTest PASS");
    }

    private static int experience(java.util.List<MasteryAward> awards, String lane) {
        return awards.stream().filter(a -> a.laneId().equals(lane)).mapToInt(MasteryAward::experience).sum();
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
