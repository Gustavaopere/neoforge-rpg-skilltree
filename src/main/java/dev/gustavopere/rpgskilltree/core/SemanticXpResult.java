package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

public record SemanticXpResult(
    SemanticXpDecision decision,
    Optional<CharacterXpAward> award,
    String reason
) {
    public SemanticXpResult {
        Objects.requireNonNull(decision, "decision");
        Objects.requireNonNull(award, "award");
        Objects.requireNonNull(reason, "reason");
        if ((decision == SemanticXpDecision.AWARDED) != award.isPresent()) {
            throw new IllegalArgumentException("only AWARDED results may contain an XP award");
        }
    }

    public static SemanticXpResult awarded(CharacterXpAward award) {
        return new SemanticXpResult(SemanticXpDecision.AWARDED, Optional.of(award), "");
    }

    public static SemanticXpResult rejectedAuthorship() {
        return new SemanticXpResult(SemanticXpDecision.REJECTED_AUTHORSHIP, Optional.empty(), "uncredited_authorship");
    }

    public static SemanticXpResult rejectedAntiFarm(String reason) {
        return new SemanticXpResult(SemanticXpDecision.REJECTED_ANTI_FARM, Optional.empty(), reason);
    }

    public static SemanticXpResult noAward() {
        return new SemanticXpResult(SemanticXpDecision.NO_AWARD, Optional.empty(), "xp_policy_no_award");
    }
}
