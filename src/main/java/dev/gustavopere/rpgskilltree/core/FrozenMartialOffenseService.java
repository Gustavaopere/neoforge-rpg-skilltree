package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** One root-action resolution for A0061-A0070 physical offense modifiers. */
public final class FrozenMartialOffenseService {
    private static final long RETENTION_MILLIS = 30_000L;
    private static final String CONSUMER = "frozen:a0061-a0070/offense";
    private final CanonicalEventLedger ledger = new CanonicalEventLedger(8_192);

    public synchronized Modifiers resolve(AttackRequest request, FrozenCombatPerkRanks ranks, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(ranks);
        if (request.targetHealthFractionBefore() < 0.0D || request.targetHealthFractionBefore() > 1.0D
            || !Double.isFinite(request.targetHealthFractionBefore())) {
            throw new IllegalArgumentException("targetHealthFractionBefore must be finite in [0,1]");
        }
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.direct()
            || !request.physical() || !request.hostileTarget()
            || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) return Modifiers.none();
        if (!ledger.claimPrimaryOnce(request.action(), CONSUMER, nowMillis, RETENTION_MILLIS)) {
            return Modifiers.duplicateResult();
        }

        double damageBonus = 0.02D * ranks.rank("A0061");
        if (request.targetHealthFractionBefore() < 0.35D) damageBonus += 0.04D * ranks.rank("A0068");
        if (request.targetHealthFractionBefore() > 0.85D) damageBonus += 0.04D * ranks.rank("A0069");
        if (request.boss()) damageBonus += 0.03D * ranks.rank("A0070");
        double criticalDamage = request.criticalHit() ? 1.0D + 0.05D * ranks.rank("A0063") : 1.0D;
        double penetration = request.penetrationProviderAvailable() ? 0.02D * ranks.rank("A0065") : 0.0D;
        double impact = request.impactProviderAvailable() ? 0.03D * ranks.rank("A0066") : 0.0D;
        return new Modifiers(false, 1.0D + damageBonus, criticalDamage, penetration, impact);
    }

    /** Contribution to the already-canonical decision; never performs a roll. */
    public static double criticalChanceBonus(
        FrozenCombatPerkRanks ranks,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        boolean physical
    ) {
        Objects.requireNonNull(ranks);
        return serverAuthoritative && eligibleActor && direct && physical ? 0.02D * ranks.rank("A0062") : 0.0D;
    }

    public static double attackSpeedMultiplier(FrozenCombatPerkRanks ranks, boolean safeProvider) {
        Objects.requireNonNull(ranks);
        return safeProvider ? 1.0D + 0.02D * ranks.rank("A0064") : 1.0D;
    }

    public static double interruptionResistance(
        FrozenCombatPerkRanks ranks,
        boolean safeProvider,
        boolean attackInProgress
    ) {
        Objects.requireNonNull(ranks);
        return safeProvider && attackInProgress ? 0.04D * ranks.rank("A0067") : 0.0D;
    }

    public record AttackRequest(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        boolean physical,
        boolean hostileTarget,
        double targetHealthFractionBefore,
        boolean boss,
        boolean criticalHit,
        boolean penetrationProviderAvailable,
        boolean impactProviderAvailable
    ) { public AttackRequest { Objects.requireNonNull(action); } }

    public record Modifiers(
        boolean duplicate,
        double damageMultiplier,
        double criticalDamageMultiplier,
        double penetrationBonus,
        double impactBonus
    ) {
        static Modifiers none() { return new Modifiers(false, 1.0D, 1.0D, 0.0D, 0.0D); }
        static Modifiers duplicateResult() { return new Modifiers(true, 1.0D, 1.0D, 0.0D, 0.0D); }
        public boolean active() {
            return damageMultiplier != 1.0D || criticalDamageMultiplier != 1.0D
                || penetrationBonus > 0.0D || impactBonus > 0.0D;
        }
    }
}
