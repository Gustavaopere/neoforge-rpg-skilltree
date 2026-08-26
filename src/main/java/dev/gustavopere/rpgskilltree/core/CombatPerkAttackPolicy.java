package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;
import java.util.OptionalDouble;

/**
 * Pure, provider-independent combat-perk policy for one confirmed attack attempt.
 *
 * <p>Provider adapters are responsible only for supplying facts that they can prove (weapon family,
 * heavy/positional state, target defense, canonical Fury gain, critical result, etc.). Missing facts
 * remain false/zero instead of being inferred heuristically here.
 */
public final class CombatPerkAttackPolicy {
    private CombatPerkAttackPolicy() {}

    public record AttackContext(
        CanonicalActionIdentity action,
        String actorId,
        String targetId,
        WeaponFamily weaponFamily,
        boolean direct,
        boolean hostile,
        boolean relevantDefense,
        boolean heavyAttack,
        boolean idealRange,
        boolean targetAdvancing,
        boolean flankOrBack,
        boolean protectedTarget,
        double targetHealthFraction,
        boolean criticalHit,
        double baseFuryGain,
        long nowMillis
    ) {
        public AttackContext {
            Objects.requireNonNull(action);
            requireId(actorId, "actorId");
            requireId(targetId, "targetId");
            Objects.requireNonNull(weaponFamily);
            if (!action.actorId().equals(actorId)) {
                throw new IllegalArgumentException("action actor must match actorId");
            }
            if (!Double.isFinite(targetHealthFraction) || targetHealthFraction < 0.0D || targetHealthFraction > 1.0D) {
                throw new IllegalArgumentException("targetHealthFraction must be in 0..1");
            }
            if (!Double.isFinite(baseFuryGain) || baseFuryGain < 0.0D) {
                throw new IllegalArgumentException("baseFuryGain must be finite and non-negative");
            }
        }

        /** Compatibility constructor for pure callers that do not yet supply provider identity. */
        public AttackContext(
            String actorId,
            String targetId,
            WeaponFamily weaponFamily,
            boolean direct,
            boolean hostile,
            boolean relevantDefense,
            boolean heavyAttack,
            boolean idealRange,
            boolean targetAdvancing,
            boolean flankOrBack,
            boolean protectedTarget,
            double targetHealthFraction,
            boolean criticalHit,
            double baseFuryGain,
            long nowMillis
        ) {
            this(
                legacyAction(actorId, targetId, weaponFamily, nowMillis),
                actorId, targetId, weaponFamily, direct, hostile, relevantDefense, heavyAttack,
                idealRange, targetAdvancing, flankOrBack, protectedTarget, targetHealthFraction,
                criticalHit, baseFuryGain, nowMillis
            );
        }

        public AttackContext withAction(CanonicalActionIdentity value) {
            return new AttackContext(
                value, actorId, targetId, weaponFamily, direct, hostile, relevantDefense, heavyAttack,
                idealRange, targetAdvancing, flankOrBack, protectedTarget, targetHealthFraction,
                criticalHit, baseFuryGain, nowMillis
            );
        }

        public AttackContext withNowMillis(long value) {
            return new AttackContext(
                actorId, targetId, weaponFamily, direct, hostile, relevantDefense, heavyAttack,
                idealRange, targetAdvancing, flankOrBack, protectedTarget, targetHealthFraction,
                criticalHit, baseFuryGain, value
            );
        }
    }

    /**
     * Provider evidence required by migrated A0012. Thermal and hunger/exhaustion evidence are mandatory;
     * TWR thirst remains an optional, independent same-action receipt and is never inferred from exhaustion.
     */
    public record FrenzyBodyEvidence(
        boolean thermalParcelAvailable,
        boolean exhaustionCostAvailable,
        boolean thirstCostReceiptAvailable
    ) {
        public static FrenzyBodyEvidence unavailable() {
            return new FrenzyBodyEvidence(false, false, false);
        }

        public boolean mandatoryTradeoffsAvailable() {
            return thermalParcelAvailable && exhaustionCostAvailable;
        }
    }

    public record HitModifiers(
        double damageMultiplier,
        double armorNegationPoints,
        double impactMultiplier,
        double guardPressureMultiplier,
        double heatContributionMultiplier,
        double exhaustionMultiplier,
        double thirstMultiplier
    ) {
        public HitModifiers {
            requirePositiveFinite(damageMultiplier, "damageMultiplier");
            requireNonNegativeFinite(armorNegationPoints, "armorNegationPoints");
            requirePositiveFinite(impactMultiplier, "impactMultiplier");
            requirePositiveFinite(guardPressureMultiplier, "guardPressureMultiplier");
            requirePositiveFinite(heatContributionMultiplier, "heatContributionMultiplier");
            requirePositiveFinite(exhaustionMultiplier, "exhaustionMultiplier");
            requirePositiveFinite(thirstMultiplier, "thirstMultiplier");
        }

        public HitModifiers(
            double damageMultiplier,
            double armorNegationPoints,
            double impactMultiplier,
            double guardPressureMultiplier
        ) {
            this(damageMultiplier, armorNegationPoints, impactMultiplier, guardPressureMultiplier, 1.0D, 1.0D, 1.0D);
        }
    }

    /**
     * Compatibility/runtime entry point. A0012 deliberately fails closed here because no causal body
     * receipts have been supplied. Provider wiring that can prove the migrated body tradeoffs must use
     * the evidence-aware overload.
     */
    public static HitModifiers beforeHit(
        AttackContext context,
        CombatPerkRanks ranks,
        NotionCombatPerkState state
    ) {
        return beforeHit(context, ranks, state, FrenzyBodyEvidence.unavailable());
    }

    public static HitModifiers beforeHit(
        AttackContext context,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        FrenzyBodyEvidence frenzyBodyEvidence
    ) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        Objects.requireNonNull(frenzyBodyEvidence);

        double armorNegation = 0.0D;
        double impact = 1.0D;
        double guardPressure = 1.0D;
        double heatContribution = 1.0D;
        double exhaustion = 1.0D;
        double thirst = 1.0D;

        if (!context.direct() || !context.hostile()) {
            return new HitModifiers(1.0D, armorNegation, impact, guardPressure);
        }
        if (!state.claimPrimaryOnce(context.action(), "combat:before-hit", context.nowMillis())) {
            return new HitModifiers(1.0D, armorNegation, impact, guardPressure);
        }
        double damage = NotionCombatPerkRules.baseDamageMultiplier(context.weaponFamily(), ranks);

        switch (context.weaponFamily()) {
            case SWORD -> {
                if (ranks.learned("A0005")
                    && state.momentum(context.actorId()) >= 3
                    && state.cooldownReady(context.actorId(), context.targetId(), "A0005", context.nowMillis())) {
                    state.consumeMomentum(context.actorId(), 2);
                    armorNegation += 12.0D;
                    impact *= 1.08D;
                    guardPressure *= 1.08D;
                    state.startCooldown(context.actorId(), context.targetId(), "A0005", context.nowMillis(), 6_000L);
                }

                if (ranks.learned("A0006")
                    && state.momentum(context.actorId()) >= 5
                    && state.consumeActorFlag(context.actorId(), NotionCombatPerkState.ActorFlag.PERFECT_RIPOSTE, context.nowMillis())) {
                    state.consumeMomentum(context.actorId(), 5);
                    if (context.criticalHit()) damage *= 1.20D;
                    impact *= 1.20D;
                    guardPressure *= 1.20D;
                    state.setActorFlag(
                        context.actorId(),
                        NotionCombatPerkState.ActorFlag.SUPPRESS_MOMENTUM_ON_RESULT,
                        Math.addExact(context.nowMillis(), 1L)
                    );
                }
            }
            case AXE -> {
                int ruptureRank = ranks.rank("A0011");
                boolean protectedForPenetration = context.relevantDefense() || context.protectedTarget();
                boolean rupture = ruptureRank > 0
                    && protectedForPenetration
                    && state.furyService().consume(
                        new CanonicalFuryService.ConsumptionRequest(
                            context.action(), true, true, context.direct(), "A0011", 40.0D, 20.0D
                        ),
                        state,
                        context.nowMillis()
                    ) == CanonicalFuryService.ConsumptionStatus.APPLIED;
                if (rupture) {
                    armorNegation += ruptureRank >= 2 ? 10.0D : 6.0D;
                    if (context.relevantDefense()) {
                        guardPressure *= ruptureRank >= 2 ? 1.35D : 1.20D;
                    }
                }

                boolean frenzy = ranks.learned("A0012")
                    && state.fury(context.actorId()) >= 75.0D
                    && frenzyBodyEvidence.mandatoryTradeoffsAvailable();
                if (frenzy) {
                    impact *= 1.10D;
                    heatContribution *= 1.25D;
                    exhaustion *= 1.15D;
                    if (frenzyBodyEvidence.thirstCostReceiptAvailable()) thirst *= 1.15D;

                    boolean heavyFrenzy = context.heavyAttack()
                        && state.furyService().consume(
                            new CanonicalFuryService.ConsumptionRequest(
                                context.action(), true, true, context.direct(), "A0012-heavy", 100.0D, 40.0D
                            ),
                            state,
                            context.nowMillis()
                        ) == CanonicalFuryService.ConsumptionStatus.APPLIED;
                    if (heavyFrenzy) {
                        impact *= 1.20D;
                        guardPressure *= 1.40D;
                    }
                }
            }
            case SPEAR -> {
                boolean masteryInterception = ranks.learned("A0018")
                    && state.distanceControl(context.actorId(), context.nowMillis()) >= 3
                    && state.consumeTargetFlag(
                        context.actorId(), context.targetId(),
                        NotionCombatPerkState.TargetFlag.INTERCEPTION_WINDOW, context.nowMillis());
                if (masteryInterception) {
                    state.consumeDistanceControl(context.actorId(), 3, context.nowMillis());
                    damage *= 1.15D;
                    impact *= 1.40D;
                    guardPressure *= 1.40D;
                } else {
                    int interceptionRank = ranks.rank("A0017");
                    if (interceptionRank > 0
                        && context.idealRange()
                        && context.targetAdvancing()
                        && state.distanceControl(context.actorId(), context.nowMillis()) >= 1) {
                        state.consumeDistanceControl(context.actorId(), 1, context.nowMillis());
                        double pressure = interceptionRank >= 2 ? 1.35D : 1.20D;
                        impact *= pressure;
                        guardPressure *= pressure;
                    }
                }
            }
            case DAGGER -> {
                if (ranks.learned("A0024")
                    && state.flow(context.actorId(), context.nowMillis()) >= 4
                    && state.consumeActorFlag(
                        context.actorId(), NotionCombatPerkState.ActorFlag.RECENT_DODGE, context.nowMillis())) {
                    state.consumeFlow(context.actorId(), 4, context.nowMillis());
                    long shadowDuration;
                    if (state.consumeActorFlag(
                        context.actorId(), NotionCombatPerkState.ActorFlag.SHADOW_DANCE_MASTERY_100, context.nowMillis())) {
                        shadowDuration = 5_000L;
                    } else if (state.consumeActorFlag(
                        context.actorId(), NotionCombatPerkState.ActorFlag.SHADOW_DANCE_MASTERY_90, context.nowMillis())) {
                        shadowDuration = 4_500L;
                    } else {
                        shadowDuration = 4_000L;
                    }
                    state.setActorFlag(
                        context.actorId(),
                        NotionCombatPerkState.ActorFlag.SHADOW_DANCE,
                        Math.addExact(context.nowMillis(), shadowDuration)
                    );
                }

                int blindSpotRank = ranks.rank("A0023");
                if (blindSpotRank > 0
                    && context.flankOrBack()
                    && state.flow(context.actorId(), context.nowMillis()) >= 2
                    && state.cooldownReady(context.actorId(), context.targetId(), "A0023", context.nowMillis())) {
                    state.consumeFlow(context.actorId(), 2, context.nowMillis());
                    armorNegation += blindSpotRank >= 2 ? 10.0D : 6.0D;
                    if (context.criticalHit()) damage *= blindSpotRank >= 2 ? 1.25D : 1.15D;
                    state.startCooldown(context.actorId(), context.targetId(), "A0023", context.nowMillis(), 4_000L);
                }

                if (ranks.learned("A0024")
                    && context.flankOrBack()
                    && state.consumeActorFlag(context.actorId(), NotionCombatPerkState.ActorFlag.SHADOW_DANCE, context.nowMillis())) {
                    damage *= 1.15D;
                    impact *= 1.20D;
                }
            }
            case HAMMER -> {
                int shockRank = ranks.rank("A0028");
                int shock = state.targetCounter(
                    context.actorId(), context.targetId(), NotionCombatPerkState.TargetCounter.SHOCK, context.nowMillis());
                if (shockRank > 0 && shock > 0) {
                    double perStack = shockRank >= 2 ? 0.12D : 0.08D;
                    guardPressure *= 1.0D + perStack * shock;
                }

                int postureBreakRank = ranks.rank("A0029");
                if (postureBreakRank > 0 && context.heavyAttack() && shock >= 3) {
                    state.consumeTargetCounter(
                        context.actorId(), context.targetId(), NotionCombatPerkState.TargetCounter.SHOCK, 3, context.nowMillis());
                    guardPressure *= postureBreakRank >= 2 ? 1.45D : 1.30D;
                    impact *= postureBreakRank >= 2 ? 1.15D : 1.10D;
                }

                if (ranks.learned("A0030")
                    && context.heavyAttack()
                    && state.consumeTargetFlag(
                        context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.DEMOLISH_WINDOW, context.nowMillis())) {
                    damage *= 1.20D;
                    impact *= 1.25D;
                }
            }
            case MACE -> {
                // A0035 Armadura Fendida is a target-side temporary armor debuff. The pure policy
                // cannot safely apply that effect and therefore fails closed: it neither consumes
                // Trauma nor converts an active flag into attacker-local armor negation.
            }
            case SCYTHE -> {
                int reapRank = ranks.rank("A0041");
                if (reapRank > 0
                    && context.targetHealthFraction() < 0.50D
                    && state.hasTargetFlag(
                        context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.REAPING_MARK, context.nowMillis())
                    && state.hasTargetFlag(
                        context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.REAPING_MATURE, context.nowMillis())) {
                    state.clearTargetFlag(context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.REAPING_MARK);
                    state.clearTargetFlag(context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.REAPING_MATURE);
                    damage *= reapRank >= 2 ? 1.20D : 1.12D;
                    impact *= reapRank >= 2 ? 1.25D : 1.15D;
                }
            }
            case BOW, CROSSBOW -> {
                // Training damage is already applied above. Projectile-specific Focus and prepared-shot
                // facts are supplied by the ranged adapter rather than inferred from a melee hit.
            }
        }

        return new HitModifiers(
            damage,
            armorNegation,
            impact,
            guardPressure,
            heatContribution,
            exhaustion,
            thirst
        );
    }

    public static void afterConfirmedHit(
        AttackContext context,
        CombatPerkRanks ranks,
        NotionCombatPerkState state
    ) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (!context.direct() || !context.hostile()) return;
        if (!state.claimPrimaryOnce(context.action(), "combat:after-confirmed-hit", context.nowMillis())) return;

        switch (context.weaponFamily()) {
            case SWORD -> {
                if (state.consumeActorFlag(
                    context.actorId(),
                    NotionCombatPerkState.ActorFlag.SUPPRESS_MOMENTUM_ON_RESULT,
                    context.nowMillis()
                )) {
                    break;
                }
                if (ranks.learned("A0004")) state.addMomentum(context.actorId(), 1, context.nowMillis());
            }
            case AXE -> {
                int furyRank = ranks.rank("A0010");
                OptionalDouble baseGain = context.baseFuryGain() > 0.0D
                    ? OptionalDouble.of(context.baseFuryGain())
                    : OptionalDouble.empty();
                state.furyService().produce(
                    new CanonicalFuryService.ProductionRequest(
                        context.action(), context.targetId(), true, true, context.direct(), context.hostile(),
                        true, furyRank, baseGain
                    ),
                    state,
                    context.nowMillis()
                );
            }
            case SPEAR -> {
                int rangeRank = ranks.rank("A0016");
                if (rangeRank > 0 && context.idealRange()) {
                    state.addDistanceControl(
                        context.actorId(),
                        1,
                        context.nowMillis(),
                        rangeRank >= 2 ? 7_000L : 5_000L
                    );
                }
            }
            case DAGGER -> CombatPerkTransitionPolicy.consumeFlowOpportunity(
                context.action(),
                context.actorId(),
                context.targetId(),
                WeaponFamily.DAGGER,
                context.direct(),
                context.hostile(),
                ranks,
                state,
                context.nowMillis()
            );
            case HAMMER -> {
                if (ranks.learned("A0028")) {
                    state.addTargetCounter(
                        context.actorId(), context.targetId(), NotionCombatPerkState.TargetCounter.SHOCK,
                        1, 3, context.nowMillis(), 6_000L);
                }
            }
            case MACE -> {
                int traumaRank = ranks.rank("A0034");
                if (traumaRank > 0 && (context.relevantDefense() || context.protectedTarget())) {
                    state.addTargetCounter(
                        context.actorId(), context.targetId(), NotionCombatPerkState.TargetCounter.TRAUMA,
                        1, 3, context.nowMillis(), traumaRank >= 2 ? 8_000L : 6_000L);
                }
            }
            case SCYTHE -> {
                int markRank = ranks.rank("A0040");
                if (markRank > 0) {
                    long expiresAt = Math.addExact(context.nowMillis(), markRank >= 2 ? 10_000L : 8_000L);
                    state.setTargetFlag(
                        context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.REAPING_MARK, expiresAt);
                    if (context.targetHealthFraction() < 0.50D) {
                        state.setTargetFlag(
                            context.actorId(), context.targetId(), NotionCombatPerkState.TargetFlag.REAPING_MATURE, expiresAt);
                    }
                }
            }
            case BOW, CROSSBOW -> {
                // Projectile-specific resource generation belongs to the ranged adapter.
            }
        }
    }

    private static void requireId(String value, String name) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
    }

    private static void requirePositiveFinite(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and positive");
        }
    }

    private static void requireNonNegativeFinite(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }

    private static CanonicalActionIdentity legacyAction(
        String actorId,
        String targetId,
        WeaponFamily family,
        long nowMillis
    ) {
        requireId(actorId, "actorId");
        requireId(targetId, "targetId");
        Objects.requireNonNull(family);
        return CanonicalActionIdentity.root(
            actorId,
            "legacy/" + family.name().toLowerCase() + "/" + targetId + "/" + nowMillis,
            "rpgskilltree:combat_policy"
        );
    }
}
