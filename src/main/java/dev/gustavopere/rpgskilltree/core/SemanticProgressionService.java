package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Pure composition boundary for semantic gameplay XP and the authoritative Core state.
 *
 * <p>Only an awarded semantic action may mutate progression. Authorship rejection,
 * anti-farm rejection and policy no-award preserve the exact input state instance.</p>
 */
public final class SemanticProgressionService {
    private SemanticProgressionService() {}

    public static SemanticProgressionResult apply(
        CoreProgressionState state,
        SemanticAction action,
        AntiFarmService antiFarmService,
        XpPolicy xpPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(antiFarmService, "antiFarmService");
        Objects.requireNonNull(xpPolicy, "xpPolicy");
        Objects.requireNonNull(rules, "rules");

        SemanticXpResult semanticXp = SemanticXpPipeline.evaluate(action, antiFarmService, xpPolicy);
        if (semanticXp.decision() != SemanticXpDecision.AWARDED) {
            return new SemanticProgressionResult(state, semanticXp);
        }

        CharacterXpAward award = semanticXp.award().orElseThrow();
        CoreProgressionState next = CoreProgressionMutationService.grantXp(state, award.amount(), rules);
        return new SemanticProgressionResult(next, semanticXp);
    }
}
