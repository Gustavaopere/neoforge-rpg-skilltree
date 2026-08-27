package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Enforces the canonical ordering: authorship -> anti-farm -> XP policy. */
public final class SemanticXpPipeline {
    private SemanticXpPipeline() {}

    public static SemanticXpResult evaluate(
        SemanticAction action,
        AntiFarmService antiFarmService,
        XpPolicy xpPolicy
    ) {
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(antiFarmService, "antiFarmService");
        Objects.requireNonNull(xpPolicy, "xpPolicy");

        if (!action.authorship().creditable()) {
            return SemanticXpResult.rejectedAuthorship();
        }

        AntiFarmDecision antiFarm = Objects.requireNonNull(
            antiFarmService.evaluate(action),
            "antiFarmService result"
        );
        if (!antiFarm.allowed()) {
            return SemanticXpResult.rejectedAntiFarm(antiFarm.reason());
        }

        var award = Objects.requireNonNull(xpPolicy.resolve(action), "xpPolicy result");
        return award.map(SemanticXpResult::awarded).orElseGet(SemanticXpResult::noAward);
    }
}
