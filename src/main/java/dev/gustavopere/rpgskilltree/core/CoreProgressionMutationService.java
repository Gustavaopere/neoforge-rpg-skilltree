package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Pure mutation boundary for the authoritative uncapped Core progression state.
 *
 * <p>This service does not decide reward amounts. Callers provide already-quantified
 * XP grants or Core Point transactions; this boundary validates the rules snapshot,
 * delegates arithmetic/economy policy, and preserves migration audit metadata.</p>
 */
public final class CoreProgressionMutationService {
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
        return withCharacterProgression(validated, result.after());
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
            rules.mainPerkBudget(),
            transaction
        );
        return withCorePoints(validated, nextLedger);
    }

    private static CoreProgressionState withCharacterProgression(
        CoreProgressionState state,
        CharacterProgressionState characterProgression
    ) {
        return new CoreProgressionState(
            characterProgression,
            state.corePoints(),
            state.attributeRanks(),
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
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }
}
