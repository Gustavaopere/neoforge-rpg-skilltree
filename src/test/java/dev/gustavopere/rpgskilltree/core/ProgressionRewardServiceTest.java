package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class ProgressionRewardServiceTest {
    public static void main(String[] args) {
        typedRewardsMutateOnlyTheirOwnedProgressionDomain();
        exactReplayIsIdempotentAcrossAllRewardTypes();
        rewardIdCannotBeReusedWithDifferentPayload();
        claimsRoundTripAndProtectReplayAfterReload();
        invalidRewardsAreRejected();
        System.out.println("ProgressionRewardServiceTest: PASS");
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            13L,
            "rpgskilltree:typed_reward_test",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(1L)
        );
    }

    private static void typedRewardsMutateOnlyTheirOwnedProgressionDomain() {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState state = CoreProgressionBootstrap.newPlayer(rules);

        state = ProgressionRewardService.apply(
            state,
            ProgressionReward.characterXp("quest:intro:xp", 250L, "quest:intro"),
            rules
        );
        eq(2L, state.characterProgression().level());
        eq(50L, state.characterProgression().xpIntoLevel());
        eq(0L, state.corePoints().totalCredits());
        eq(0L, state.mainPerkBudgetProgression().bonus());

        state = ProgressionRewardService.apply(
            state,
            ProgressionReward.corePoints("quest:intro:points", 4L, "quest:intro"),
            rules
        );
        eq(4L, state.corePoints().totalCredits());
        eq(4L, state.corePoints().available());

        state = ProgressionRewardService.apply(
            state,
            ProgressionReward.mainPerkBudget("boss:warden:budget", 2L, "boss:warden"),
            rules
        );
        eq(2L, state.mainPerkBudgetProgression().bonus());
        eq(3L, CoreProgressionMutationService.effectivePerkBudget(state, rules).total());
        eq(3, state.progressionRewardClaims().claims().size());
    }

    private static void exactReplayIsIdempotentAcrossAllRewardTypes() {
        ProgressionRulesSnapshot rules = rules();
        ProgressionReward[] rewards = {
            ProgressionReward.characterXp("quest:once:xp", 100L, "quest:once"),
            ProgressionReward.corePoints("quest:once:points", 2L, "quest:once"),
            ProgressionReward.mainPerkBudget("quest:once:budget", 1L, "quest:once")
        };

        CoreProgressionState state = CoreProgressionBootstrap.newPlayer(rules);
        for (ProgressionReward reward : rewards) {
            CoreProgressionState applied = ProgressionRewardService.apply(state, reward, rules);
            CoreProgressionState replay = ProgressionRewardService.apply(applied, reward, rules);
            same(applied, replay);
            state = applied;
        }
        eq(1L, state.characterProgression().level());
        eq(2L, state.corePoints().totalCredits());
        eq(1L, state.mainPerkBudgetProgression().bonus());
    }

    private static void rewardIdCannotBeReusedWithDifferentPayload() {
        ProgressionRulesSnapshot rules = rules();
        ProgressionReward original = ProgressionReward.corePoints("quest:conflict", 2L, "quest:a");
        CoreProgressionState state = ProgressionRewardService.apply(
            CoreProgressionBootstrap.newPlayer(rules), original, rules);

        expect(IllegalArgumentException.class, () -> ProgressionRewardService.apply(
            state,
            ProgressionReward.corePoints("quest:conflict", 3L, "quest:a"),
            rules
        ));
        expect(IllegalArgumentException.class, () -> ProgressionRewardService.apply(
            state,
            ProgressionReward.characterXp("quest:conflict", 2L, "quest:a"),
            rules
        ));
        expect(IllegalArgumentException.class, () -> ProgressionRewardService.apply(
            state,
            ProgressionReward.corePoints("quest:conflict", 2L, "quest:b"),
            rules
        ));
    }

    private static void claimsRoundTripAndProtectReplayAfterReload() {
        ProgressionRulesSnapshot rules = rules();
        ProgressionReward reward = ProgressionReward.characterXp(
            "advancement:first_portal:xp",
            175L,
            "advancement:first_portal"
        );
        CoreProgressionState applied = ProgressionRewardService.apply(
            CoreProgressionBootstrap.newPlayer(rules), reward, rules);
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(CoreProgressionStateCodec.encode(applied));

        eq(applied.progressionRewardClaims(), decoded.progressionRewardClaims());
        eq(1, decoded.progressionRewardClaims().claims().size());
        CoreProgressionState replay = ProgressionRewardService.apply(decoded, reward, rules);
        same(decoded, replay);
        eq(applied.characterProgression(), replay.characterProgression());
    }

    private static void invalidRewardsAreRejected() {
        expect(IllegalArgumentException.class, () -> ProgressionReward.corePoints("", 1L, "quest:a"));
        expect(IllegalArgumentException.class, () -> ProgressionReward.corePoints("quest:a", 0L, "quest:a"));
        expect(IllegalArgumentException.class, () -> ProgressionReward.corePoints("quest:a", -1L, "quest:a"));
        expect(IllegalArgumentException.class, () -> ProgressionReward.corePoints("quest:a", 1L, ""));
    }

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected identical instance");
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

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
