package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.Objects;

/** Builds stable read-only progression projections for quests and provider adapters. */
public final class CoreProgressionQueryService {
    private CoreProgressionQueryService() {}

    public static CoreProgressionQuerySnapshot snapshot(
        CoreProgressionState state,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(rules, "rules");
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        CharacterProgressionState character = validated.characterProgression();
        CorePointLedger ledger = validated.corePoints();

        BigInteger nextLevelCost = character.level() == Long.MAX_VALUE
            ? BigInteger.ZERO
            : rules.levelCurve().xpToNextLevel(character.level());

        long mainPerkAllocated = ledger.allocated(CorePointAllocation.MAIN_PERK);
        long effectiveBudget = CoreProgressionMutationService.effectivePerkBudget(validated, rules).total();
        long budgetAvailable;
        long budgetOverage;
        if (mainPerkAllocated <= effectiveBudget) {
            budgetAvailable = effectiveBudget - mainPerkAllocated;
            budgetOverage = 0L;
        } else {
            budgetAvailable = 0L;
            budgetOverage = mainPerkAllocated - effectiveBudget;
        }

        return new CoreProgressionQuerySnapshot(
            character.level(),
            character.xpIntoLevel(),
            nextLevelCost,
            ledger.totalCredits(),
            ledger.available(),
            ledger.allocated(CorePointAllocation.ATTRIBUTE),
            mainPerkAllocated,
            effectiveBudget,
            budgetAvailable,
            budgetOverage,
            validated.attributeRanks(),
            rules.version(),
            rules.fingerprint()
        );
    }
}
