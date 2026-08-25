package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Provider facts used to resolve the one canonical critical boolean for an action. */
public record CanonicalCriticalRequest(
    CanonicalActionIdentity action,
    boolean serverAuthoritative,
    boolean eligibleActor,
    boolean direct,
    boolean providerCritical,
    double bonusChance
) {
    public CanonicalCriticalRequest {
        Objects.requireNonNull(action);
        if (!Double.isFinite(bonusChance) || bonusChance < 0.0D || bonusChance > 1.0D) {
            throw new IllegalArgumentException("bonusChance must be finite and in 0..1");
        }
    }

    public CanonicalCriticalRequest withAction(CanonicalActionIdentity value) {
        return new CanonicalCriticalRequest(
            value,
            serverAuthoritative,
            eligibleActor,
            direct,
            providerCritical,
            bonusChance
        );
    }
}
