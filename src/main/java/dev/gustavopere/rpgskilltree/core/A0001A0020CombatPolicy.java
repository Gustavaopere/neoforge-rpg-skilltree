package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;

/**
 * Provider-independent policy for A0001-A0020. Provider adapters must supply only facts they can
 * prove; a false availability flag therefore disables the corresponding effect rather than
 * substituting another mechanic.
 */
public final class A0001A0020CombatPolicy {
    private A0001A0020CombatPolicy() {}

    public record HitFacts(
        String actorId,
        String targetId,
        String rootActionId,
        WeaponFamily family,
        boolean direct,
        boolean hostile,
        boolean actualDamage,
        boolean relevantGuardOrPosture,
        boolean armorProtected,
        boolean heavyAttack,
        boolean idealSpearRange,
        boolean critical,
        boolean impactHookAvailable,
        boolean penetrationHookAvailable,
        long nowMillis
    ) {
        public HitFacts {
            require(actorId, "actorId"); require(targetId, "targetId"); require(rootActionId, "rootActionId"); Objects.requireNonNull(family);
            if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
        }
    }

    public record HitModifiers(
        double damageMultiplier,
        double criticalChanceBonus,
        double impactMultiplier,
        double guardPressureMultiplier,
        double physicalPenetrationFraction,
        boolean suppressMomentumGain
    ) {}

    /** PRE-stage modifiers. Never invents unavailable provider semantics. */
    public static HitModifiers beforeHit(HitFacts facts, CombatPerkRanks ranks, NotionCombatPerkState state) {
        Objects.requireNonNull(facts); Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (!facts.direct() || !facts.hostile()) return neutral(ranks, facts.family());
        double damage = NotionCombatPerkRules.baseDamageMultiplier(facts.family(), ranks);
        double impact = 1.0D;
        double guard = 1.0D;
        double penetration = 0.0D;
        boolean suppressMomentum = false;

        if (facts.family() == WeaponFamily.SWORD) {
            if (ranks.learned("A0006") && state.momentum(facts.actorId(), facts.nowMillis()) >= 5
                && state.consumeRiposte(facts.actorId(), facts.nowMillis())
                && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0006:consume", facts.nowMillis())) {
                state.consumeMomentum(facts.actorId(), 5);
                if (facts.critical()) damage *= 1.20D;
                if (facts.impactHookAvailable()) { impact *= 1.20D; guard *= 1.20D; }
                suppressMomentum = true;
            } else if (ranks.learned("A0005") && facts.relevantGuardOrPosture()
                && facts.impactHookAvailable()
                && state.momentum(facts.actorId(), facts.nowMillis()) >= NotionCombatPerkRules.A0005_MIN_MOMENTUM
                && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0005:consume", facts.nowMillis())) {
                state.consumeMomentum(facts.actorId(), NotionCombatPerkRules.A0005_MOMENTUM_COST);
                impact *= NotionCombatPerkRules.A0005_IMPACT_MULTIPLIER;
                guard *= NotionCombatPerkRules.A0005_IMPACT_MULTIPLIER;
            }
        } else if (facts.family() == WeaponFamily.AXE) {
            int ruptureRank = ranks.rank("A0011");
            boolean eligibleProtection = facts.relevantGuardOrPosture() || facts.armorProtected();
            if (ruptureRank > 0 && eligibleProtection
                && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0011:spend", facts.nowMillis())
                && state.consumeFury(facts.actorId(), NotionCombatPerkRules.A0011_FURY_COST, NotionCombatPerkRules.A0011_MIN_FURY)) {
                if (facts.relevantGuardOrPosture() && facts.impactHookAvailable()) {
                    impact *= NotionCombatPerkRules.ruptureImpactMultiplier(ruptureRank);
                    guard *= NotionCombatPerkRules.ruptureImpactMultiplier(ruptureRank);
                }
                if (facts.penetrationHookAvailable()) penetration = NotionCombatPerkRules.rupturePenetrationFraction(ruptureRank);
            }
            // A0012 benefits are deliberately absent here. They are enabled only by a provider bridge
            // that can debit thermal + exhaustion + thirst from the same causal offensive action.
        } else if (facts.family() == WeaponFamily.SPEAR) {
            if (ranks.learned("A0018") && state.distanceControl(facts.actorId(), facts.nowMillis()) >= 3
                && state.consumeLineWindow(facts.actorId(), facts.targetId(), facts.nowMillis())
                && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0018:consume", facts.nowMillis())) {
                state.consumeDistanceControl(facts.actorId(), 3, facts.nowMillis());
                damage *= 1.15D;
                if (facts.impactHookAvailable()) { impact *= 1.40D; guard *= 1.40D; }
            } else {
                int rank = ranks.rank("A0017");
                if (rank > 0 && state.distanceControl(facts.actorId(), facts.nowMillis()) >= 1
                    && state.consumeInterceptWindow(facts.actorId(), facts.targetId(), facts.nowMillis())
                    && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0017:consume", facts.nowMillis())) {
                    state.consumeDistanceControl(facts.actorId(), 1, facts.nowMillis());
                    if (facts.impactHookAvailable()) {
                        impact *= NotionCombatPerkRules.interceptionImpactMultiplier(rank);
                        guard *= NotionCombatPerkRules.interceptionImpactMultiplier(rank);
                    }
                }
            }
        }
        return new HitModifiers(damage, NotionCombatPerkRules.criticalChanceBonus(facts.family(), ranks), impact, guard, penetration, suppressMomentum);
    }

    /** POST-stage confirmed-hit state changes; idempotent per root action. */
    public static void afterConfirmedHit(HitFacts facts, CombatPerkRanks ranks, NotionCombatPerkState state, boolean suppressMomentum) {
        Objects.requireNonNull(facts); Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (!facts.direct() || !facts.hostile() || !facts.actualDamage()) return;
        if (facts.family() == WeaponFamily.SWORD && ranks.learned("A0004") && !suppressMomentum
            && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0004:gain", facts.nowMillis())) {
            state.addMomentum(facts.actorId(), 1, facts.nowMillis());
        }
        if (facts.family() == WeaponFamily.AXE && ranks.rank("A0010") > 0
            && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0010:fury", facts.nowMillis())) {
            boolean switched = state.switchedAxeTarget(facts.actorId(), facts.targetId());
            state.addFury(facts.actorId(), NotionCombatPerkRules.axeFuryGain(ranks.rank("A0010"), switched));
        }
        if (facts.family() == WeaponFamily.SPEAR && ranks.rank("A0016") > 0 && facts.idealSpearRange()
            && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0016:gain", facts.nowMillis())) {
            state.addDistanceControl(facts.actorId(), 1, facts.nowMillis(), NotionCombatPerkRules.distanceControlWindowMillis(ranks.rank("A0016")));
        }
    }

    public static boolean onConfirmedTechnicalDefense(String actorId, WeaponFamily heldFamily, CombatPerkRanks ranks,
                                                        NotionCombatPerkState state, int swordMastery, long nowMillis) {
        if (heldFamily != WeaponFamily.SWORD || !ranks.learned("A0006") || state.momentum(actorId, nowMillis) < 5
            || !state.riposteCooldownReady(actorId, nowMillis)) return false;
        state.armRiposte(actorId, nowMillis, 3_000L, NotionCombatPerkRules.riposteCooldownMillis(swordMastery));
        return true;
    }

    public static void onConfirmedMiss(String actorId, WeaponFamily family, CombatPerkRanks ranks,
                                       NotionCombatPerkState state, long nowMillis) {
        if (family == WeaponFamily.SPEAR && ranks.rank("A0016") > 0) state.loseDistanceControl(actorId, 1, nowMillis);
    }

    public static void onConfirmedHostileHeavyStagger(String actorId, CombatPerkRanks ranks,
                                                       NotionCombatPerkState state, long nowMillis) {
        if (ranks.rank("A0016") > 0) state.loseDistanceControl(actorId, 1, nowMillis);
    }

    public static void onSpearRangeSample(String actorId, String targetId, boolean insideIdealRange, boolean targetAdvancing,
                                          CombatPerkRanks ranks, NotionCombatPerkState state, int spearMastery, long nowMillis) {
        if (ranks.rank("A0017") <= 0 && !ranks.learned("A0018")) return;
        state.recordSpearRange(actorId, targetId, insideIdealRange, targetAdvancing, spearMastery, nowMillis);
    }

    private static HitModifiers neutral(CombatPerkRanks ranks, WeaponFamily family) {
        return new HitModifiers(1.0D, NotionCombatPerkRules.criticalChanceBonus(family, ranks), 1.0D, 1.0D, 0.0D, false);
    }

    private static void require(String value, String name) { if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " must not be blank"); }
}
