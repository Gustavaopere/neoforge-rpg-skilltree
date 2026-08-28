package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class ProgressionCompletionClaimsTest {
    public static void main(String[] args) {
        completionClaimsAreStableAndRewardAmountIndependent();
        legacyDiscoveriesSeedCoreCompletionClaims();
        firstCompletionAwardsOnceAndClaimsOnlyAfterAward();
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

    private static void firstCompletionAwardsOnceAndClaimsOnlyAfterAward() {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState initial = CoreProgressionBootstrap.newPlayer(rules);
        SemanticAction action = new SemanticAction(
            SemanticActionType.BIOME_DISCOVERED,
            "minecraft:forest",
            new ActionOrigin("test:first_completion", 0),
            SemanticActionAuthorship.DIRECT_PLAYER,
            SemanticActionContext.empty()
        );
        XpPolicy award25 = ignored -> Optional.of(new CharacterXpAward(
            "test:biome_discovery",
            25L,
            Set.of()
        ));
        String completionKey = "biome:minecraft:forest";

        SemanticProgressionResult awarded = SemanticProgressionService.applyFirstCompletion(
            initial,
            completionKey,
            action,
            ignored -> AntiFarmDecision.allow(),
            award25,
            rules
        );
        eq(SemanticXpDecision.AWARDED, awarded.semanticXp().decision());
        eq(25L, awarded.state().characterProgression().xpIntoLevel());
        eq(true, awarded.state().progressionRewardClaims().isCompletionClaimed(completionKey));

        SemanticProgressionResult replay = SemanticProgressionService.applyFirstCompletion(
            awarded.state(),
            completionKey,
            action,
            ignored -> AntiFarmDecision.allow(),
            ignored -> Optional.of(new CharacterXpAward("test:rebalanced", 75L, Set.of())),
            rules
        );
        if (replay.state() != awarded.state()) {
            throw new AssertionError("first-completion replay must preserve state identity");
        }
        eq(SemanticXpDecision.NO_AWARD, replay.semanticXp().decision());
        eq("first_completion_already_claimed", replay.semanticXp().reason());

        String blockedKey = "biome:minecraft:blocked";
        SemanticProgressionResult blocked = SemanticProgressionService.applyFirstCompletion(
            initial,
            blockedKey,
            action,
            ignored -> AntiFarmDecision.reject("blocked"),
            award25,
            rules
        );
        if (blocked.state() != initial) {
            throw new AssertionError("anti-farm rejection must preserve state identity");
        }
        eq(false, blocked.state().progressionRewardClaims().isCompletionClaimed(blockedKey));

        String noAwardKey = "biome:minecraft:no_award";
        SemanticProgressionResult noAward = SemanticProgressionService.applyFirstCompletion(
            initial,
            noAwardKey,
            action,
            ignored -> AntiFarmDecision.allow(),
            ignored -> Optional.empty(),
            rules
        );
        if (noAward.state() != initial) {
            throw new AssertionError("policy no-award must preserve state identity");
        }
        eq(false, noAward.state().progressionRewardClaims().isCompletionClaimed(noAwardKey));
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
