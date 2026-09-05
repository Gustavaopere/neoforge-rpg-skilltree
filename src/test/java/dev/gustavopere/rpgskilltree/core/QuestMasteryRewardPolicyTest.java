package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class QuestMasteryRewardPolicyTest {
    public static void main(String[] args) {
        acceptsCanonicalReplaySafeRewards();
        rejectsNonCanonicalMasteryLanes();
        rejectsRepeatableUntrackedRewards();
        System.out.println("QuestMasteryRewardPolicyTest: PASS");
    }

    private static void acceptsCanonicalReplaySafeRewards() {
        MasteryAward reward = MasteryAward.replaySafe(
            MasteryLaneCatalog.MAGIC_CASTING,
            25,
            "rpgskilltree:quest/arcane_initiation",
            "quest:arcane_initiation/reward/mastery"
        );

        same(reward, QuestMasteryRewardPolicy.validate(reward));
    }

    private static void rejectsNonCanonicalMasteryLanes() {
        MasteryAward reward = MasteryAward.replaySafe(
            "quest:invented_lane",
            25,
            "rpgskilltree:quest/invalid_lane",
            "quest:invalid_lane/reward/mastery"
        );

        expect(IllegalArgumentException.class, () -> QuestMasteryRewardPolicy.validate(reward));
    }

    private static void rejectsRepeatableUntrackedRewards() {
        MasteryAward reward = new MasteryAward(
            MasteryLaneCatalog.MAGIC_CASTING,
            25,
            "rpgskilltree:quest/repeatable_without_identity"
        );

        expect(IllegalArgumentException.class, () -> QuestMasteryRewardPolicy.validate(reward));
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected same instance");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
