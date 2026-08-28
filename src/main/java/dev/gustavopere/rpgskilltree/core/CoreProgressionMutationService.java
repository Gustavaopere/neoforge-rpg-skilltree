package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Pure mutation boundary for the authoritative uncapped Core progression state.
 *
 * <p>This service does not decide reward amounts. XP-to-level progression and
 * level-derived Core Point awards are interpreted from the supplied rules snapshot;
 * direct Core Point transactions and typed budget grants remain explicitly quantified
 * by their caller.</p>
 */
public final class CoreProgressionMutationService {
    private static final String CHARACTER_LEVEL_SOURCE = "character_level";

    private CoreProgressionMutationService() {}

    public static CoreProgressionState grantXp(
        CoreProgressionState state,
        long amount,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        CharacterXpGrantResult result = CharacterProgressionService.grantXp(
            validated.characterProgression(),
            amount,
            rules.levelCurve()
        );

        long awardedPoints = rules.levelCorePointAwardPolicy().pointsAwarded(
            result.before().level(),
            result.after().level()
        );
        CorePointLedger nextLedger = validated.corePoints();
        if (awardedPoints > 0L) {
            CorePointTransaction award = CorePointTransaction.credit(
                levelAwardTransactionId(rules, result.before().level(), result.after().level()),
                CorePointTransactionKind.EARN,
                awardedPoints,
                CHARACTER_LEVEL_SOURCE,
                rules.version()
            );
            nextLedger = CorePointEconomyService.apply(
                nextLedger,
                effectivePerkBudget(validated, rules),
                award
            );
        }

        return withProgressionAndCorePoints(validated, result.after(), nextLedger);
    }

    /**
     * Privileged rollback of already-earned RPG XP.
     *
     * <p>Rollback intentionally leaves the Core Point ledger unchanged. Reversing a
     * historical economic reward is a distinct administrative/economic operation and
     * must never happen implicitly merely because character XP is corrected.</p>
     */
    public static CoreProgressionState rollbackXp(
        CoreProgressionState state,
        long amount,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        CharacterXpRollbackResult result = CharacterProgressionService.rollbackXp(
            validated.characterProgression(),
            amount,
            rules.levelCurve()
        );
        if (result.after() == validated.characterProgression()) return validated;
        return withProgressionAndCorePoints(validated, result.after(), validated.corePoints());
    }

    public static CoreProgressionState applyCorePointTransaction(
        CoreProgressionState state,
        CorePointTransaction transaction,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(transaction);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        if (transaction.rulesVersion() != rules.version()) {
            throw new IllegalArgumentException(
                "Core Point transaction rules version does not match current rules snapshot"
            );
        }
        CorePointLedger nextLedger = CorePointEconomyService.apply(
            validated.corePoints(),
            effectivePerkBudget(validated, rules),
            transaction
        );
        return withCorePoints(validated, nextLedger);
    }

    /**
     * Applies a persistent, idempotent increase to this player's main-tree budget.
     * The stable grant id is intended for quest, boss and milestone completion keys.
     */
    public static CoreProgressionState grantMainPerkBudget(
        CoreProgressionState state,
        String grantId,
        long amount,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        MainPerkBudgetProgression next = validated.mainPerkBudgetProgression().grant(grantId, amount);
        if (next == validated.mainPerkBudgetProgression()) return validated;
        return withMainPerkBudgetProgression(validated, next);
    }

    public static MainPerkBudget effectivePerkBudget(
        CoreProgressionState state,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        return state.mainPerkBudgetProgression().effectiveBudget(rules.mainPerkBudget());
    }

    private static String levelAwardTransactionId(
        ProgressionRulesSnapshot rules,
        long beforeLevel,
        long afterLevel
    ) {
        return "core:level_award:v" + rules.version() + ":" + beforeLevel + "->" + afterLevel;
    }

    private static CoreProgressionState withProgressionAndCorePoints(
        CoreProgressionState state,
        CharacterProgressionState characterProgression,
        CorePointLedger corePoints
    ) {
        return new CoreProgressionState(
            characterProgression,
            corePoints,
            state.attributeRanks(),
            state.mainPerkBudgetProgression(),
            state.progressionRewardClaims(),
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }

    private static CoreProgressionState withCorePoints(
        CoreProgressionState state,
        CorePointLedger corePoints
    ) {
        return new CoreProgressionState(
            state.characterProgression(),
            corePoints,
            state.attributeRanks(),
            state.mainPerkBudgetProgression(),
            state.progressionRewardClaims(),
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }

    private static CoreProgressionState withMainPerkBudgetProgression(
        CoreProgressionState state,
        MainPerkBudgetProgression mainPerkBudgetProgression
    ) {
        return new CoreProgressionState(
            state.characterProgression(),
            state.corePoints(),
            state.attributeRanks(),
            mainPerkBudgetProgression,
            state.progressionRewardClaims(),
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }
}
