package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Server-authoritative application boundary for typed quest, boss and milestone rewards.
 *
 * <p>Every reward carries a stable id. Exact replay is a no-op even after save/reload;
 * reusing the same id with a different type, amount or source is rejected.</p>
 */
public final class ProgressionRewardService {
    private static final String CORE_POINT_TRANSACTION_PREFIX = "core:typed_reward:";
    private static final String PERK_BUDGET_GRANT_PREFIX = "typed_reward:";

    private ProgressionRewardService() {}

    public static CoreProgressionState apply(
        CoreProgressionState state,
        ProgressionReward reward,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(reward, "reward");
        Objects.requireNonNull(rules, "rules");

        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        ProgressionRewardClaims claims = validated.progressionRewardClaims();
        if (claims.isClaimed(reward)) return validated;

        CoreProgressionState applied = switch (reward.type()) {
            case CHARACTER_XP -> CoreProgressionMutationService.grantXp(
                validated,
                reward.amount(),
                rules
            );
            case CORE_POINTS -> CoreProgressionMutationService.applyCorePointTransaction(
                validated,
                CorePointTransaction.credit(
                    CORE_POINT_TRANSACTION_PREFIX + reward.rewardId(),
                    CorePointTransactionKind.EARN,
                    reward.amount(),
                    reward.sourceId(),
                    rules.version()
                ),
                rules
            );
            case MAIN_PERK_BUDGET -> CoreProgressionMutationService.grantMainPerkBudget(
                validated,
                PERK_BUDGET_GRANT_PREFIX + reward.rewardId(),
                reward.amount(),
                rules
            );
        };

        return withRewardClaims(applied, claims.claim(reward));
    }

    private static CoreProgressionState withRewardClaims(
        CoreProgressionState state,
        ProgressionRewardClaims rewardClaims
    ) {
        return new CoreProgressionState(
            state.characterProgression(),
            state.corePoints(),
            state.attributeRanks(),
            state.mainPerkBudgetProgression(),
            rewardClaims,
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }
}
