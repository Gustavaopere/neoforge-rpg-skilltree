package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FrozenA0101A0110PolicyTest {
    public static void main(String[] args) {
        catalogProjectsExactlyFiftyFrozenNodes();
        mitigationDeduplicatesCanonicalAliasesWithoutHiddenCap();
        mitigationRejectsBypassProcAndDuplicateCallbacks();
        deliveryAndNatureStayOrthogonal();
        secondWindUsesThresholdPulsesAndPersistentCooldown();
        reactiveShellCountsCanonicalDirectHitsOnly();
        emergencyGuardIncludesTriggerAndSingleFatalSave();
        impactConversionRemainsFailClosedUntilP0035();
        stoneSkinAndLoadBenefitsRequireTheirCosts();
        manualDurabilityConservationRunsAfterNativePreventionOnce();
        System.out.println("FrozenA0101A0110PolicyTest: PASS");
    }

    private static void catalogProjectsExactlyFiftyFrozenNodes() {
        require(FrozenA0101A0150Catalog.all().size() == 50, "third frozen batch size");
        for (int i = 101; i <= 150; i++) {
            String code = "A%04d".formatted(i);
            require(FrozenA0101A0150Catalog.definition(code).isPresent(), "missing " + code);
        }
        require(FrozenA0101A0150Catalog.definition("A0100").isEmpty(), "prior checkpoint stays separate");
        require(FrozenA0101A0150Catalog.definition("A0151").isEmpty(), "next batch stays separate");
        var a101 = FrozenA0101A0150Catalog.definition("A0101").orElseThrow();
        require(a101.maxRank() == 4 && a101.rankCost() == 1, "A0101 rank/cost");
        require(a101.dependencies().equals(Map.of("A0089", 1)), "A0101 ranked dependency");
        require(a101.requiredGateways().equals(Set.of(FrozenSurvivalPerkDefinition.Domain.VITALITY)),
            "A0101 VITALITY gate");
        var a107 = FrozenA0101A0150Catalog.definition("A0107").orElseThrow();
        require(a107.fallback() == FrozenSurvivalPerkDefinition.Fallback.FAIL_CLOSED,
            "A0107 remains fail-closed pending P-0035");
    }

    private static void mitigationDeduplicatesCanonicalAliasesWithoutHiddenCap() {
        var resolver = new DamageMitigationResolver(64);
        var action = CanonicalActionIdentity.root("attacker", "attack-1", "neoforge:damage");
        var result = resolver.resolve(
            new DamageMitigationResolver.Request(action, "victim", 100.0D, false),
            List.of(
                new DamageMitigationResolver.Modifier("a0102", "magic_resistance", 0.08D),
                new DamageMitigationResolver.Modifier("provider_alias", "magic_resistance", 0.08D),
                new DamageMitigationResolver.Modifier("a0108", "stone_skin", 0.15D)
            ),
            1_000L
        );
        close(78.2D, result.finalDamage(), "distinct modifiers compose multiplicatively");
        require(result.appliedCanonicalModifierIds().equals(Set.of("magic_resistance", "stone_skin")),
            "aliases deduplicate");

        var uncapped = resolver.resolve(
            new DamageMitigationResolver.Request(
                CanonicalActionIdentity.root("attacker", "attack-2", "neoforge:damage"),
                "victim", 100.0D, false),
            List.of(
                new DamageMitigationResolver.Modifier("one", "one", 0.60D),
                new DamageMitigationResolver.Modifier("two", "two", 0.60D)
            ),
            1_001L
        );
        close(16.0D, uncapped.finalDamage(), "no invented aggregate defense cap");
    }

    private static void mitigationRejectsBypassProcAndDuplicateCallbacks() {
        var resolver = new DamageMitigationResolver(64);
        var root = CanonicalActionIdentity.root("attacker", "attack", "neoforge:damage");
        var modifier = List.of(new DamageMitigationResolver.Modifier("a0101", "projectile", 0.08D));
        var first = resolver.resolve(new DamageMitigationResolver.Request(root, "victim", 50.0D, false), modifier, 10L);
        close(46.0D, first.finalDamage(), "first callback applies");
        var duplicate = resolver.resolve(new DamageMitigationResolver.Request(root, "victim", 46.0D, false), modifier, 11L);
        require(duplicate.duplicateEvent(), "duplicate callback identified");
        close(46.0D, duplicate.finalDamage(), "duplicate does not mitigate twice");
        close(50.0D, resolver.resolve(
            new DamageMitigationResolver.Request(
                CanonicalActionIdentity.root("attacker", "bypass", "neoforge:damage"), "victim", 50.0D, true),
            modifier, 12L).finalDamage(), "bypass remains authoritative");
        close(50.0D, resolver.resolve(
            new DamageMitigationResolver.Request(root.child("rpgskilltree:derived"), "other", 50.0D, false),
            modifier, 13L).finalDamage(), "derived damage cannot re-enter mitigation perks");
    }

    private static void deliveryAndNatureStayOrthogonal() {
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0101", 4, "A0102", 4, "A0103", 4));
        var magicProjectile = FrozenDamageMitigationPolicy.modifiers(ranks,
            new FrozenDamageMitigationPolicy.Facts(true, true, false, false, false, false));
        require(magicProjectile.size() == 1 && magicProjectile.get(0).sourceId().equals("A0102"),
            "magic projectile is magic, not physical projectile");
        var physicalProjectile = FrozenDamageMitigationPolicy.modifiers(ranks,
            new FrozenDamageMitigationPolicy.Facts(true, false, true, false, false, false));
        require(physicalProjectile.size() == 1 && physicalProjectile.get(0).sourceId().equals("A0101"),
            "physical projectile uses A0101 once");
        require(FrozenDamageMitigationPolicy.modifiers(ranks,
            new FrozenDamageMitigationPolicy.Facts(false, false, false, true, true, false)).isEmpty(),
            "thermal environment is excluded from A0103");
        require(FrozenDamageMitigationPolicy.modifiers(ranks,
            new FrozenDamageMitigationPolicy.Facts(false, false, false, true, false, true)).size() == 1,
            "explicit non-elemental environment uses A0103");
    }

    private static void secondWindUsesThresholdPulsesAndPersistentCooldown() {
        var service = new SecondWindService(64);
        var trigger = CanonicalActionIdentity.root("enemy", "hit-1", "neoforge:damage");
        require(service.onDamage(new SecondWindService.Damage(
            "player", trigger, 30.0D, 20.0D, 100.0D, true, true), 1, 0L), "crossing arms A0104");
        require(service.claimPulse("player", 19L).isEmpty(), "first pulse waits 20 ticks");
        close(0.024D, service.claimPulse("player", 20L).orElseThrow(), "pulse fraction");
        var cancel = CanonicalActionIdentity.root("enemy", "hit-2", "neoforge:damage");
        require(!service.onDamage(new SecondWindService.Damage(
            "player", cancel, 20.0D, 18.0D, 100.0D, true, true), 1, 21L), "remaining below does not rearm");
        require(service.claimPulse("player", 40L).isEmpty(), "new direct hit cancels exactly next pulse");
        close(0.024D, service.claimPulse("player", 60L).orElseThrow(), "later pulse remains scheduled");
        service.clearTransient("player");
        require(!service.onDamage(new SecondWindService.Damage(
            "player", CanonicalActionIdentity.root("enemy", "hit-3", "neoforge:damage"),
            30.0D, 20.0D, 100.0D, true, true), 1, 100L), "lifecycle cannot reset cooldown");
    }

    private static void reactiveShellCountsCanonicalDirectHitsOnly() {
        var service = new ReactiveShellService(64);
        require(!service.record(new ReactiveShellService.Hit("player", action("h1"), true, true), 1, 0L), "first");
        require(!service.record(new ReactiveShellService.Hit("player", action("h1"), true, true), 1, 1L), "duplicate ignored");
        require(!service.record(new ReactiveShellService.Hit("player", action("dot"), true, false), 1, 2L), "periodic ignored");
        require(!service.record(new ReactiveShellService.Hit("player", action("h2"), true, true), 1, 20L), "second");
        require(service.record(new ReactiveShellService.Hit("player", action("h3"), true, true), 1, 40L), "third activates");
        var bonuses = service.bonuses("player", 10.0D, 0.0D, 41L);
        close(1.5D, bonuses.armor(), "relative armor bonus");
        close(0.0D, bonuses.toughness(), "zero base remains zero");
        service.clearTransient("player");
        close(0.0D, service.bonuses("player", 10.0D, 10.0D, 42L).armor(),
            "lifecycle clears transient armor window");
        require(!service.record(new ReactiveShellService.Hit("player", action("h4"), true, true), 1, 43L),
            "lifecycle cannot bypass persistent cooldown");
    }

    private static void emergencyGuardIncludesTriggerAndSingleFatalSave() {
        var service = new EmergencyGuardService(64);
        var first = service.resolve(new EmergencyGuardService.Damage(
            "player", action("lethal"), 100.0D, 20.0D, 30.0D, true), 1, 0L);
        close(19.0D, first.finalDamage(), "trigger hit reduced and limited to one health");
        require(first.activated() && first.fatalSaveConsumed(), "same hit may consume fatal save");
        var next = service.resolve(new EmergencyGuardService.Damage(
            "player", action("next"), 100.0D, 10.0D, 20.0D, true), 1, 20L);
        close(13.0D, next.finalDamage(), "window still mitigates later hit");
        require(!next.fatalSaveConsumed(), "fatal save never reappears in window");
        service.clearTransient("player");
        var cooldown = service.resolve(new EmergencyGuardService.Damage(
            "player", action("cooldown"), 100.0D, 20.0D, 30.0D, true), 1, 100L);
        close(30.0D, cooldown.finalDamage(), "lifecycle cannot bypass 180 second cooldown");
    }

    private static void impactConversionRemainsFailClosedUntilP0035() {
        require(!FrozenA0107IntegrationPolicy.providerCertified(), "P-0035 not certified on this branch");
        require(FrozenA0107IntegrationPolicy.maximumConvertibleFraction() == 0.0D,
            "no impact conversion scaffold grants behavior");
    }

    private static void stoneSkinAndLoadBenefitsRequireTheirCosts() {
        require(!FrozenDefensiveTradeoffPolicy.stoneSkin(1, true, false).active(),
            "stone skin benefit requires movement cost");
        var stone = FrozenDefensiveTradeoffPolicy.stoneSkin(1, true, true);
        close(0.15D, stone.physicalReduction(), "stone skin benefit");
        close(-0.08D, stone.movementSpeedMultiplierDelta(), "stone skin cost");
        require(!FrozenDefensiveTradeoffPolicy.load(1,
            FrozenDefensiveTradeoffPolicy.LoadStage.EXTREME, false).active(),
            "load benefit requires stamina regen cost hook");
        var heavy = FrozenDefensiveTradeoffPolicy.load(1,
            FrozenDefensiveTradeoffPolicy.tfcStage(2), true);
        close(0.08D, heavy.physicalReduction(), "TFC two overburdening items are extreme");
        close(-0.20D, heavy.staminaRegenMultiplierDelta(), "extreme stamina tradeoff");
    }

    private static void manualDurabilityConservationRunsAfterNativePreventionOnce() {
        var service = new EquipmentConservationService(64);
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0110", 5));
        var eligible = new EquipmentConservationService.Wear(
            action("wear"), EquipmentConservationService.Family.MANUAL_TOOL,
            1, true, false, false);
        require(service.conserve(eligible, ranks, 0.01D, 0L), "5 percent conservation can cancel one confirmed point");
        require(!service.conserve(eligible, ranks, 0.01D, 1L), "same wear event cannot roll twice");
        require(!service.conserve(new EquipmentConservationService.Wear(
            action("native"), EquipmentConservationService.Family.MANUAL_TOOL,
            0, true, true, false), ranks, 0.0D, 2L), "native prevention means no skill roll");
        require(!service.conserve(new EquipmentConservationService.Wear(
            action("indestructible"), EquipmentConservationService.Family.MANUAL_TOOL,
            1, true, false, true), ranks, 0.0D, 3L), "indestructible item is ineligible");
    }

    private static CanonicalActionIdentity action(String id) {
        return CanonicalActionIdentity.root("enemy", id, "neoforge:damage");
    }

    private static void close(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.000001D) {
            throw new AssertionError(message + ": " + expected + " != " + actual);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
