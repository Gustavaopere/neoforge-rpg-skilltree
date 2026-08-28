package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ProgressionCompletionClaimsTest {
    public static void main(String[] args) {
        completionClaimsAreStableAndRewardAmountIndependent();
        legacyDiscoveriesSeedCoreCompletionClaims();
        completionClaimsSurviveCoreCodecRoundTrip();
        exactRewardClaimsCoexistWithCompletionClaims();
        System.out.println("ProgressionCompletionClaimsTest: PASS");
    }

    private static void completionClaimsAreStableAndRewardAmountIndependent() {
        ProgressionRewardClaims claims = ProgressionRewardClaims.empty();
        String key = "biome:minecraft:plains";

        ProgressionRewardClaims claimed = claims.claimCompletion(key);
        eq(false, claims.isCompletionClaimed(key));
        eq(true, claimed.isCompletionClaimed(key));
        eq("completion:v1", claimed.claims().get("completion:" + key));
        if (claimed.claimCompletion(key) != claimed) {
            throw new AssertionError("completion claim replay must preserve identity");
        }
    }

    private static void legacyDiscoveriesSeedCoreCompletionClaims() {
        ProgressionState empty = ProgressionState.empty();
        ProgressionState legacy = empty.withDiscoveries(DiscoveryProgress.of(Set.of(
            "biome:minecraft:plains",
            "dimension:minecraft:the_nether"
        )));
        ProgressionRulesSnapshot rules = rules();

        CoreProgressionState migrated = CoreProgressionBootstrap.migrateDecodedLegacy(legacy, rules);
        eq(true, migrated.progressionRewardClaims().isCompletionClaimed("biome:minecraft:plains"));
        eq(true, migrated.progressionRewardClaims().isCompletionClaimed("dimension:minecraft:the_nether"));
    }

    private static void completionClaimsSurviveCoreCodecRoundTrip() {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState initial = CoreProgressionBootstrap.newPlayer(rules);
        ProgressionRewardClaims claims = initial.progressionRewardClaims()
            .claimCompletion("biome:minecraft:forest")
            .claimCompletion("dimension:minecraft:overworld");
        CoreProgressionState withClaims = initial.withProgressionRewardClaims(claims);

        CoreProgressionState decoded = CoreProgressionStateCodec.decode(
            CoreProgressionStateCodec.encode(withClaims)
        );
        eq(claims, decoded.progressionRewardClaims());
    }

    private static void exactRewardClaimsCoexistWithCompletionClaims() {
        ProgressionReward reward = ProgressionReward.characterXp(
            "quest:intro",
            250L,
            "quest:rpgskilltree:intro"
        );
        ProgressionRewardClaims claims = ProgressionRewardClaims.empty()
            .claimCompletion("biome:minecraft:desert")
            .claim(reward);

        eq(true, claims.isCompletionClaimed("biome:minecraft:desert"));
        eq(true, claims.isClaimed(reward));
        eq(2, claims.claims().size());
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            41L,
            "rpgskilltree:completion_claim_test",
            List.of(new LevelCurveBand(0L, 100L, 2L)),
            new MainPerkBudget(30L)
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
