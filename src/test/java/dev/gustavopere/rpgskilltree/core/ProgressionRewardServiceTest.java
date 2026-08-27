package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public final class ProgressionRewardServiceTest {
    public static void main(String[] args) throws Exception {
        typedRewardsMutateOnlyTheirOwnedProgressionDomain();
        exactReplayIsIdempotentAcrossAllRewardTypes();
        rewardIdCannotBeReusedWithDifferentPayload();
        claimsRoundTripAndProtectReplayAfterReload();
        claimsSurviveUnrelatedCoreMutations();
        legacyVersionThreeDefaultsClaimsToEmpty();
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

    private static void claimsSurviveUnrelatedCoreMutations() {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState state = ProgressionRewardService.apply(
            CoreProgressionBootstrap.newPlayer(rules),
            ProgressionReward.corePoints("quest:persist:points", 4L, "quest:persist"),
            rules
        );

        state = CoreProgressionMutationService.grantXp(state, 50L, rules);
        eq(1, state.progressionRewardClaims().claims().size());
        state = CoreProgressionMutationService.grantMainPerkBudget(
            state,
            "milestone:later-budget",
            1L,
            rules
        );
        eq(1, state.progressionRewardClaims().claims().size());
        state = AttributeRankMutationService.purchase(
            state,
            AttributeId.STRENGTH,
            1L,
            "attribute:post-reward",
            "test:post-reward",
            UnitAttributeRankCostPolicy.INSTANCE,
            rules
        );
        eq(1, state.progressionRewardClaims().claims().size());
        eq(true, state.progressionRewardClaims().claims().containsKey("quest:persist:points"));
    }

    private static void legacyVersionThreeDefaultsClaimsToEmpty() throws Exception {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(legacyV3EmptyPayload(rules));
        eq(ProgressionRewardClaims.empty(), decoded.progressionRewardClaims());
        eq(MainPerkBudgetProgression.empty(), decoded.mainPerkBudgetProgression());
    }

    private static byte[] legacyV3EmptyPayload(ProgressionRulesSnapshot rules) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(bytes)) {
            out.writeInt(3);
            out.writeLong(0L);
            out.writeLong(0L);
            out.writeLong(rules.version());
            writeString(out, rules.fingerprint());
            out.writeInt(0);
            out.writeLong(0L);
            out.writeInt(0);
            out.writeLong(0L);
            out.writeLong(0L);
            out.writeInt(0);
            out.writeInt(0);
            out.writeInt(0);
        }
        return bytes.toByteArray();
    }

    private static void writeString(DataOutputStream out, String value) throws Exception {
        byte[] encoded = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(encoded.length);
        out.write(encoded);
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
