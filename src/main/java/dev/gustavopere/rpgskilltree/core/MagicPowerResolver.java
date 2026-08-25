package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Applies A0144 once to one direct provider-classified magic outcome. */
public final class MagicPowerResolver {
    private final CanonicalEventLedger claims;

    public MagicPowerResolver(int maxClaims) { claims = new CanonicalEventLedger(maxClaims); }

    public synchronized Resolution resolve(Request request, int rank, long nowTick) {
        Objects.requireNonNull(request);
        if (rank <= 0 || !request.providerClassifiedMagic() || !request.realPlayerOwner()
            || !ProcGuard.mayTriggerSecondaryEffect(request.outcome().action().origin())) {
            return new Resolution(request.nativeValue(), false, false);
        }
        boolean claimed = claims.claimPrimaryOnce(request.outcome().action(),
            "magic_power:" + request.outcome().outcomeId(), nowTick, 1_200L);
        if (!claimed) return new Resolution(request.nativeValue(), false, true);
        return new Resolution(request.nativeValue() * (1.0D + 0.02D * Math.min(rank, 5)), true, false);
    }

    public record Request(
        CanonicalOutcomeIdentity outcome,
        double nativeValue,
        boolean providerClassifiedMagic,
        boolean realPlayerOwner
    ) {
        public Request {
            Objects.requireNonNull(outcome);
            if (!Double.isFinite(nativeValue) || nativeValue < 0.0D) {
                throw new IllegalArgumentException("nativeValue must be finite and non-negative");
            }
        }
    }

    public record Resolution(double adjustedValue, boolean applied, boolean duplicate) {}
}
