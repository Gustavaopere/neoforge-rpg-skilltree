package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

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

    /**
     * Applies one semantic XP action only for the first persistent completion key.
     *
     * <p>The completion claim is written only after an XP award is accepted. Authorship,
     * anti-farm and policy rejection therefore never burn the first-completion key.</p>
     */
    public static SemanticProgressionResult applyFirstCompletion(
        CoreProgressionState state,
        String completionKey,
        SemanticAction action,
        AntiFarmService antiFarmService,
        XpPolicy xpPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(completionKey, "completionKey");
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(antiFarmService, "antiFarmService");
        Objects.requireNonNull(xpPolicy, "xpPolicy");
        Objects.requireNonNull(rules, "rules");
        if (completionKey.isBlank()) throw new IllegalArgumentException("completionKey must not be blank");

        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        if (validated.progressionRewardClaims().isCompletionClaimed(completionKey)) {
            return new SemanticProgressionResult(
                validated,
                new SemanticXpResult(
                    SemanticXpDecision.NO_AWARD,
                    Optional.empty(),
                    "first_completion_already_claimed"
                )
            );
        }

        SemanticProgressionResult result = apply(
            validated,
            action,
            antiFarmService,
            xpPolicy,
            rules
        );
        if (result.semanticXp().decision() != SemanticXpDecision.AWARDED) {
            return result;
        }

        ProgressionRewardClaims claims = result.state().progressionRewardClaims()
            .claimCompletion(completionKey);
        CoreProgressionState claimed = result.state().withProgressionRewardClaims(claims);
        return new SemanticProgressionResult(claimed, result.semanticXp());
    }
}
