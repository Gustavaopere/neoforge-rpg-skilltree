package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Resolves one post-provider damage value exactly once for one canonical action and target. */
public final class DamageMitigationResolver {
    private static final long CLAIM_RETENTION = 3_600L;
    private final CanonicalEventLedger claims;

    public DamageMitigationResolver(int maxClaims) {
        claims = new CanonicalEventLedger(maxClaims);
    }

    public synchronized Resolution resolve(Request request, List<Modifier> modifiers, long nowTick) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(modifiers);
        if (request.bypassesMitigation() || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) {
            return new Resolution(request.incomingDamage(), Set.of(), false, false);
        }
        boolean claimed = claims.claimPrimaryOnce(
            request.action(), "damage_mitigation:" + request.targetId(), nowTick, CLAIM_RETENTION);
        if (!claimed) return new Resolution(request.incomingDamage(), Set.of(), true, true);

        LinkedHashMap<String, Modifier> canonical = new LinkedHashMap<>();
        for (Modifier modifier : modifiers) {
            Objects.requireNonNull(modifier);
            canonical.putIfAbsent(modifier.canonicalModifierId(), modifier);
        }
        double result = request.incomingDamage();
        for (Modifier modifier : canonical.values()) result *= 1.0D - modifier.reductionFraction();
        return new Resolution(result, Set.copyOf(canonical.keySet()), false, true);
    }

    public record Request(
        CanonicalActionIdentity action,
        String targetId,
        double incomingDamage,
        boolean bypassesMitigation
    ) {
        public Request {
            Objects.requireNonNull(action);
            requireId(targetId, "targetId");
            requireFiniteNonNegative(incomingDamage, "incomingDamage");
        }
    }

    public record Modifier(String sourceId, String canonicalModifierId, double reductionFraction) {
        public Modifier {
            requireId(sourceId, "sourceId");
            requireId(canonicalModifierId, "canonicalModifierId");
            if (!Double.isFinite(reductionFraction) || reductionFraction < 0.0D || reductionFraction >= 1.0D) {
                throw new IllegalArgumentException("reductionFraction must be in [0,1)");
            }
        }
    }

    public record Resolution(
        double finalDamage,
        Set<String> appliedCanonicalModifierIds,
        boolean duplicateEvent,
        boolean eligible
    ) {
        public Resolution {
            requireFiniteNonNegative(finalDamage, "finalDamage");
            appliedCanonicalModifierIds = Set.copyOf(appliedCanonicalModifierIds);
        }
    }

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    private static void requireFiniteNonNegative(double value, String field) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(field + " must be finite and non-negative");
        }
    }
}
