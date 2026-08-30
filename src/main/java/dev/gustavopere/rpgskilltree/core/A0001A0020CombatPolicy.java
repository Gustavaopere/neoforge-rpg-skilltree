package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;

/**
 * Provider-independent policy for A0001-A0020. Provider adapters must supply only facts they can
 * prove; a false availability flag therefore disables the corresponding component rather than
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
        boolean guardOrPostureHookAvailable,
        boolean idealSpearRange,
        boolean critical,
        boolean impactHookAvailable,
        boolean penetrationHookAvailable,
        boolean frenzyBodyCostPaid,
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
        boolean suppressMomentumGain,
        boolean frenzyTradeoff
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
        boolean frenzyTradeoff = false;

        if (facts.family() == WeaponFamily.SWORD) {
            if (ranks.learned("A0006") && state.momentum(facts.actorId(), facts.nowMillis()) >= 5
                && state.consumeRiposte(facts.actorId(), facts.nowMillis())
                && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0006:consume", facts.nowMillis())) {
                state.consumeMomentum(facts.actorId(), 5);
                if (facts.critical()) damage *= 1.20D;
                if (facts.impactHookAvailable()) { impact *= 1.20D; guard *= 1.20D; }
                suppressMomentum = true;
            } else {
                boolean nativeDefense = facts.guardOrPostureHookAvailable() && facts.relevantGuardOrPosture();
                boolean armorFallback = !facts.guardOrPostureHookAvailable() && facts.armorProtected();
                boolean openingHasSafeComponent = nativeDefense
                    ? facts.impactHookAvailable() || facts.penetrationHookAvailable()
                    : armorFallback && facts.penetrationHookAvailable();
                if (ranks.learned("A0005")
                    && (nativeDefense || armorFallback)
                    && state.sameSwordSequenceTarget(facts.actorId(), facts.targetId())
                    && state.momentum(facts.actorId(), facts.nowMillis()) >= NotionCombatPerkRules.A0005_MIN_MOMENTUM
                    && state.openingCooldownReady(facts.actorId(), facts.targetId(), facts.nowMillis())
                    && openingHasSafeComponent
                    && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0005:consume", facts.nowMillis())) {
                    state.consumeMomentum(facts.actorId(), NotionCombatPerkRules.A0005_MOMENTUM_COST);
                    if (nativeDefense && facts.impactHookAvailable()) {
                        impact *= NotionCombatPerkRules.A0005_IMPACT_MULTIPLIER;
                        guard *= NotionCombatPerkRules.A0005_IMPACT_MULTIPLIER;
                    }
                    if (facts.penetrationHookAvailable()) {
                        penetration = NotionCombatPerkRules.A0005_PENETRATION_FRACTION;
                    }
                    state.startOpeningCooldown(facts.actorId(), facts.targetId(), facts.nowMillis());
                }
            }
        } else if (facts.family() == WeaponFamily.AXE) {
            boolean frenzyAvailable = ranks.learned("A0012")
                && state.fury(facts.actorId()) + 1.0E-9D >= NotionCombatPerkRules.A0012_FRENZY_THRESHOLD
                && NotionCombatPerkRules.frenzyBaselineAvailable(facts.impactHookAvailable(), facts.frenzyBodyCostPaid());
            boolean peakReady = frenzyAvailable
                && state.fury(facts.actorId()) + 1.0E-9D >= NotionCombatPerkRules.A0012_PEAK_THRESHOLD;

            if (peakReady
                && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0012:peak", facts.nowMillis())
                && state.consumeFury(facts.actorId(), NotionCombatPerkRules.A0012_PEAK_FURY_COST, NotionCombatPerkRules.A0012_PEAK_THRESHOLD)) {
                impact = Math.max(impact, NotionCombatPerkRules.A0012_PEAK_IMPACT_MULTIPLIER);
                if (facts.guardOrPostureHookAvailable() && facts.relevantGuardOrPosture()) {
                    guard = Math.max(guard, NotionCombatPerkRules.A0012_PEAK_GUARD_PRESSURE_MULTIPLIER);
                }
                frenzyTradeoff = true;
            } else {
                int ruptureRank = ranks.rank("A0011");
                boolean nativeDefense = facts.guardOrPostureHookAvailable() && facts.relevantGuardOrPosture();
                boolean armorFallback = !facts.guardOrPostureHookAvailable() && facts.armorProtected();
                boolean hasSafeComponent = nativeDefense
                    ? facts.impactHookAvailable() || facts.penetrationHookAvailable()
                    : armorFallback && facts.penetrationHookAvailable();
                if (ruptureRank > 0 && (nativeDefense || armorFallback) && hasSafeComponent
                    && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0011:spend", facts.nowMillis())
                    && state.consumeFury(facts.actorId(), NotionCombatPerkRules.A0011_FURY_COST, NotionCombatPerkRules.A0011_MIN_FURY)) {
                    if (nativeDefense && facts.impactHookAvailable()) {
                        impact *= NotionCombatPerkRules.ruptureImpactMultiplier(ruptureRank);
                        guard *= NotionCombatPerkRules.ruptureImpactMultiplier(ruptureRank);
                    }
                    if (facts.penetrationHookAvailable()) {
                        penetration = NotionCombatPerkRules.rupturePenetrationFraction(ruptureRank);
                    }
                }

                // A0011 may itself drop Fury below the Frenzy threshold, so eligibility is sampled
                // again after the spend. The adapter must have paid A0012's body cost before setting
                // frenzyBodyCostPaid=true; without that receipt the benefit remains fail-closed.
                if (ranks.learned("A0012")
                    && state.fury(facts.actorId()) + 1.0E-9D >= NotionCombatPerkRules.A0012_FRENZY_THRESHOLD
                    && NotionCombatPerkRules.frenzyBaselineAvailable(facts.impactHookAvailable(), facts.frenzyBodyCostPaid())) {
                    impact = Math.max(impact, NotionCombatPerkRules.A0012_FRENZY_IMPACT_MULTIPLIER);
                    frenzyTradeoff = true;
                }
            }
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
                    // Offensive displacement reduction is intentionally omitted until the provider
                    // exposes a native recognized offensive-movement receipt for the same target.
                }
            }
        }
        return new HitModifiers(
            damage,
            NotionCombatPerkRules.criticalChanceBonus(facts.family(), ranks),
            impact,
            guard,
            penetration,
            suppressMomentum,
            frenzyTradeoff
        );
    }

    /** POST-stage confirmed-hit state changes; idempotent per root action. */
    public static void afterConfirmedHit(HitFacts facts, CombatPerkRanks ranks, NotionCombatPerkState state, boolean suppressMomentum) {
        Objects.requireNonNull(facts); Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (!facts.direct() || !facts.hostile() || !facts.actualDamage()) return;
        if (facts.family() == WeaponFamily.SWORD) {
            if (ranks.learned("A0004") && !suppressMomentum
                && state.claimOnce(facts.actorId(), facts.rootActionId(), "A0004:gain", facts.nowMillis())) {
                state.addMomentum(facts.actorId(), 1, facts.nowMillis());
            }
            state.recordSwordSequenceTarget(facts.actorId(), facts.targetId());
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

    /** Confirmed dodge/parry/perfect-guard event while the sword discipline is active. */
    public static boolean onConfirmedTechnicalDefense(String actorId, String defenseEventId, WeaponFamily heldFamily,
                                                        CombatPerkRanks ranks, NotionCombatPerkState state,
                                                        int swordMastery, long nowMillis) {
        Objects.requireNonNull(ranks); Objects.requireNonNull(state);
        if (heldFamily != WeaponFamily.SWORD) return false;
        if (ranks.learned("A0004")
            && state.claimOnce(actorId, defenseEventId, "A0004:defense-gain", nowMillis)) {
            state.addMomentum(actorId, 1, nowMillis);
        }
        if (!ranks.learned("A0006") || state.momentum(actorId, nowMillis) < 5
            || !state.riposteCooldownReady(actorId, nowMillis)) return false;
        state.armRiposte(actorId, nowMillis, 3_000L, NotionCombatPerkRules.riposteCooldownMillis(swordMastery));
        return true;
    }

    public static void onConfirmedMiss(String actorId, WeaponFamily family, CombatPerkRanks ranks,
                                       NotionCombatPerkState state, long nowMillis) {
        if (family == WeaponFamily.SWORD && ranks.learned("A0004")) state.loseMomentum(actorId, 1);
        if (family == WeaponFamily.SPEAR && ranks.rank("A0016") > 0) state.loseDistanceControl(actorId, 1, nowMillis);
    }

    /** Only call this after a provider has positively identified hostile heavy stagger/impact. */
    public static void onConfirmedHostileHeavyStagger(String actorId, CombatPerkRanks ranks,
                                                       NotionCombatPerkState state, long nowMillis) {
        if (ranks.learned("A0004")) state.loseMomentum(actorId, 2);
        if (ranks.rank("A0016") > 0) state.loseDistanceControl(actorId, 1, nowMillis);
    }

    public static void onSpearRangeSample(String actorId, String targetId, boolean insideIdealRange, boolean targetAdvancing,
                                          CombatPerkRanks ranks, NotionCombatPerkState state, int spearMastery, long nowMillis) {
        if (ranks.rank("A0017") <= 0 && !ranks.learned("A0018")) return;
        state.recordSpearRange(actorId, targetId, insideIdealRange, targetAdvancing, spearMastery, nowMillis);
    }

    public static void tick(String actorId, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(state);
        state.tickTransient(actorId, nowMillis);
    }

    public static boolean isIdealSpearRange(double distance, double effectiveReach) {
        if (!Double.isFinite(distance) || !Double.isFinite(effectiveReach) || distance < 0.0D || effectiveReach <= 0.0D) return false;
        double fraction = distance / effectiveReach;
        return fraction + 1.0E-9D >= NotionCombatPerkRules.SPEAR_IDEAL_MIN_FRACTION
            && fraction <= NotionCombatPerkRules.SPEAR_IDEAL_MAX_FRACTION + 1.0E-9D;
    }

    /** Geometric approach is enough to open A0017/A0018 windows, never to rewrite movement. */
    public static boolean isAdvancingToward(double attackerX, double attackerZ, double targetX, double targetZ,
                                             double targetMotionX, double targetMotionZ) {
        if (!Double.isFinite(attackerX) || !Double.isFinite(attackerZ) || !Double.isFinite(targetX)
            || !Double.isFinite(targetZ) || !Double.isFinite(targetMotionX) || !Double.isFinite(targetMotionZ)) return false;
        double toAttackerX = attackerX - targetX;
        double toAttackerZ = attackerZ - targetZ;
        double distanceSquared = toAttackerX * toAttackerX + toAttackerZ * toAttackerZ;
        double motionSquared = targetMotionX * targetMotionX + targetMotionZ * targetMotionZ;
        if (distanceSquared <= 1.0E-9D || motionSquared <= 1.0E-9D) return false;
        return targetMotionX * toAttackerX + targetMotionZ * toAttackerZ > 1.0E-9D;
    }

    private static HitModifiers neutral(CombatPerkRanks ranks, WeaponFamily family) {
        return new HitModifiers(1.0D, NotionCombatPerkRules.criticalChanceBonus(family, ranks), 1.0D, 1.0D, 0.0D, false, false);
    }

    private static void require(String value, String name) { if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " must not be blank"); }
}