package dev.gustavopere.rpgskilltree.core;

public final class EidolonAlchemyPolicyTest {
    public static void main(String[] args) {
        EidolonAlchemyAction first = new EidolonAlchemyAction(
            new ActionOrigin("eidolon:alchemy", 0),
            "eidolon:arcane_gold",
            true,
            true
        );
        var firstAwards = MasteryPolicies.forEidolonAlchemy(first);
        check(experience(firstAwards, "eidolon:alchemy") > 0, "confirmed alchemy result should train Eidolon alchemy");

        EidolonAlchemyAction repeat = new EidolonAlchemyAction(
            new ActionOrigin("eidolon:alchemy", 0),
            "eidolon:arcane_gold",
            true,
            false
        );
        var repeatAwards = MasteryPolicies.forEidolonAlchemy(repeat);
        check(experience(firstAwards, "eidolon:alchemy") > experience(repeatAwards, "eidolon:alchemy"),
            "first distinct recipe completion must be stronger than repetition");

        EidolonAlchemyAction unconfirmed = new EidolonAlchemyAction(
            new ActionOrigin("eidolon:alchemy", 0),
            "eidolon:arcane_gold",
            false,
            true
        );
        check(MasteryPolicies.forEidolonAlchemy(unconfirmed).isEmpty(), "alchemy attempts must not grant mastery");

        EidolonAlchemyAction proc = new EidolonAlchemyAction(
            new ActionOrigin("eidolon:alchemy", 1),
            "eidolon:arcane_gold",
            true,
            true
        );
        check(MasteryPolicies.forEidolonAlchemy(proc).isEmpty(), "derived alchemy callbacks must not create mastery loops");

        System.out.println("EidolonAlchemyPolicyTest PASS");
    }

    private static int experience(java.util.List<MasteryAward> awards, String lane) {
        return awards.stream().filter(a -> a.laneId().equals(lane)).mapToInt(MasteryAward::experience).sum();
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
