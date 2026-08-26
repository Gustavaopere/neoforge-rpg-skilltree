package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

/** Post-modlist migration contract for A0012/A0046/A0048 provider boundaries. */
public final class MigratedA0001A0050ProviderContractTest {
    public static void main(String[] args) {
        a0012UsesMigratedNameAndFailsClosedWithoutMandatoryBodyTradeoffs();
        a0012AppliesOnlyProvenSameActionBodyTradeoffs();
        a0046KeepsBodyAxesIndependentAndNeutralWhenUnavailable();
        a0046AppliesCanonicalBodyScalarsToGainsAndLosses();
        System.out.println("MigratedA0001A0050ProviderContractTest: PASS");
    }

    private static void a0012UsesMigratedNameAndFailsClosedWithoutMandatoryBodyTradeoffs() {
        var definition = NotionCombatPerkCatalog.definition("A0012").orElseThrow();
        require(definition.name().equals("Maestria de Machados — Frenesi do Saqueador"), "A0012 migrated display name");

        var state = new NotionCombatPerkState();
        state.addFury("p", 100.0D, 0L);
        var modifiers = CombatPerkAttackPolicy.beforeHit(
            axeContext("no-body", true, 1_000L),
            CombatPerkRanks.of(Map.of("A0012", 1)),
            state
        );

        require(close(modifiers.impactMultiplier(), 1.0D), "A0012 must fail closed without thermal parcel + exhaustion cost evidence");
        require(close(modifiers.guardPressureMultiplier(), 1.0D), "failed-closed A0012 cannot grant heavy guard pressure");
        require(close(state.fury("p"), 100.0D), "failed-closed A0012 cannot consume Fury");
    }

    private static void a0012AppliesOnlyProvenSameActionBodyTradeoffs() {
        var ranks = CombatPerkRanks.of(Map.of("A0012", 1));

        var noThirstState = new NotionCombatPerkState();
        noThirstState.addFury("p", 100.0D, 0L);
        var noThirst = CombatPerkAttackPolicy.beforeHit(
            axeContext("body-no-thirst", true, 1_000L),
            ranks,
            noThirstState,
            new CombatPerkAttackPolicy.FrenzyBodyEvidence(true, true, false)
        );
        require(close(noThirst.impactMultiplier(), 1.32D), "A0012 valid Frenzy heavy impact = 1.10 x 1.20");
        require(close(noThirst.guardPressureMultiplier(), 1.40D), "A0012 heavy guard pressure is +40%");
        require(close(noThirst.heatContributionMultiplier(), 1.25D), "A0012 thermal parcel multiplier");
        require(close(noThirst.exhaustionMultiplier(), 1.15D), "A0012 hunger/exhaustion multiplier");
        require(close(noThirst.thirstMultiplier(), 1.0D), "absence of TWR same-action receipt omits only thirst");
        require(close(noThirstState.fury("p"), 60.0D), "A0012 valid max-Fury heavy consumes exactly 40 Fury");

        var thirstState = new NotionCombatPerkState();
        thirstState.addFury("p", 75.0D, 0L);
        var withThirst = CombatPerkAttackPolicy.beforeHit(
            axeContext("body-thirst", false, 2_000L),
            ranks,
            thirstState,
            new CombatPerkAttackPolicy.FrenzyBodyEvidence(true, true, true)
        );
        require(close(withThirst.impactMultiplier(), 1.10D), "A0012 direct Frenzy hit impact");
        require(close(withThirst.thirstMultiplier(), 1.15D), "TWR thirst multiplier requires its own causal receipt");
        require(close(thirstState.fury("p"), 75.0D), "non-heavy Frenzy does not consume Fury");

        var missingThermalState = new NotionCombatPerkState();
        missingThermalState.addFury("p", 100.0D, 0L);
        var missingThermal = CombatPerkAttackPolicy.beforeHit(
            axeContext("missing-thermal", true, 3_000L),
            ranks,
            missingThermalState,
            new CombatPerkAttackPolicy.FrenzyBodyEvidence(false, true, true)
        );
        require(close(missingThermal.impactMultiplier(), 1.0D), "exhaustion/TWR cannot substitute missing causal thermal parcel");
        require(close(missingThermal.thirstMultiplier(), 1.0D), "inactive Frenzy applies no isolated thirst cost");
    }

    private static void a0046KeepsBodyAxesIndependentAndNeutralWhenUnavailable() {
        var neutral = CanonicalBodyStateScalars.neutral();
        require(close(neutral.gainMultiplier(), 1.0D), "missing body providers are neutral for Focus gain");
        require(close(neutral.lossMultiplier(), 1.0D), "missing body providers are neutral for Focus loss");

        var temperatureOnly = new CanonicalBodyStateScalars(
            new CanonicalBodyStateScalars.Axis(0.80D, 1.20D),
            CanonicalBodyStateScalars.Axis.neutral(),
            CanonicalBodyStateScalars.Axis.neutral()
        );
        require(close(temperatureOnly.gainMultiplier(), 0.80D), "temperature is an independent BODY axis");
        require(close(temperatureOnly.lossMultiplier(), 1.20D), "temperature loss scalar is independent");

        var hydrationOnly = new CanonicalBodyStateScalars(
            CanonicalBodyStateScalars.Axis.neutral(),
            new CanonicalBodyStateScalars.Axis(0.90D, 1.10D),
            CanonicalBodyStateScalars.Axis.neutral()
        );
        require(close(hydrationOnly.gainMultiplier(), 0.90D), "hydration is not inferred from exhaustion");

        var fatigueOnly = new CanonicalBodyStateScalars(
            CanonicalBodyStateScalars.Axis.neutral(),
            CanonicalBodyStateScalars.Axis.neutral(),
            new CanonicalBodyStateScalars.Axis(0.70D, 1.30D)
        );
        require(close(fatigueOnly.gainMultiplier(), 0.70D), "Minecraft fatigue/exhaustion is its own axis");
        require(close(hydrationOnly.hydration().gainMultiplier(), 0.90D), "TWR hydration remains explicit");
        require(close(fatigueOnly.hydration().gainMultiplier(), 1.0D), "exhaustion cannot synthesize hydration");
    }

    private static void a0046AppliesCanonicalBodyScalarsToGainsAndLosses() {
        var body = new CanonicalBodyStateScalars(
            new CanonicalBodyStateScalars.Axis(0.80D, 1.20D),
            new CanonicalBodyStateScalars.Axis(0.90D, 1.10D),
            CanonicalBodyStateScalars.Axis.neutral()
        );
        var service = new CanonicalFocusService(30_000L, 64);
        var state = new NotionCombatPerkState();
        var aim = CanonicalActionIdentity.root("p", "aim-body", "neoforge:arrow_nock");

        service.sampleAim(new CanonicalFocusService.AimSampleRequest(
            aim, true, true, true, false, 1, 0.0D, 0.0D, body
        ), state, 0L);
        require(service.sampleAim(new CanonicalFocusService.AimSampleRequest(
            aim, true, true, true, false, 1, 0.0D, 0.0D, body
        ), state, 500L) == CanonicalFocusService.AimStatus.STABLE_GAIN, "stable aim with body axes applies");
        require(close(state.focus("p"), 2.88D), "rank1 +4/interval x 0.8 temperature x 0.9 hydration");

        var distantState = new NotionCombatPerkState();
        var hit = CanonicalActionIdentity.root("q", "shot-body", "neoforge:projectile");
        require(service.creditDistantProjectileHit(
            new CanonicalFocusService.DistantHitRequest(hit, "arrow-body", true, true, true, 12.0D, 1, body),
            distantState,
            1_000L
        ) == CanonicalFocusService.DistantHitStatus.APPLIED, "distant body-scaled hit applies");
        require(close(distantState.focus("q"), 7.20D), "distant Focus gain uses the same independent gain axes");

        distantState.addFocus("q", 50.0D, 1_000L);
        require(service.applyHeavyImpactLoss("q", true, true, body, distantState, 2_000L), "body-scaled heavy loss applies");
        require(close(distantState.focus("q"), 24.20D), "heavy loss = 25 x 1.2 x 1.1");
    }

    private static CombatPerkAttackPolicy.AttackContext axeContext(String actionId, boolean heavy, long nowMillis) {
        return new CombatPerkAttackPolicy.AttackContext(
            CanonicalActionIdentity.root("p", actionId, "epicfight:damage_pre"),
            "p",
            "mob",
            WeaponFamily.AXE,
            true,
            true,
            true,
            heavy,
            false,
            false,
            false,
            true,
            1.0D,
            false,
            0.0D,
            nowMillis
        );
    }

    private static boolean close(double actual, double expected) {
        return Math.abs(actual - expected) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
