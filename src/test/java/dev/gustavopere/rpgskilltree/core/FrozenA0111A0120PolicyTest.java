package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FrozenA0111A0120PolicyTest {
    public static void main(String[] args) {
        technicalConservationPreservesNativeResourceSemantics();
        autoMaintenanceSelectsOneCandidateAndCommitsAtomically();
        boundMaintenanceRequiresRealAttunementAndPreservesCooldown();
        fieldReinforcementUsesPlayerAndToolInstanceIdentity();
        bodyCostsResolveOncePerActionAndChannel();
        bodyCostPolicyMapsOnlyTheAttributedAction();
        System.out.println("FrozenA0111A0120PolicyTest: PASS");
    }

    private static void technicalConservationPreservesNativeResourceSemantics() {
        var service = new EquipmentConservationService(32);
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0111", 5));
        require(service.conserve(new EquipmentConservationService.Wear(
            action("tech-wear"), EquipmentConservationService.Family.DURABLE_TECH,
            1, true, false, false), ranks, 0.074D, 0L), "A0111 caps at 7.5 percent");
        require(!service.conserve(new EquipmentConservationService.Wear(
            action("fe-only"), EquipmentConservationService.Family.OTHER,
            1, true, false, false), ranks, 0.0D, 1L), "FE-only use never becomes durability conservation");
    }

    private static void autoMaintenanceSelectsOneCandidateAndCommitsAtomically() {
        var service = new CanonicalMaintenanceService(16);
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0112", 3));
        service.recordHostileCombat("player", 0L);
        var candidates = List.of(
            candidate("main", CanonicalMaintenanceService.Position.MAIN_HAND, 80, 100, true, false),
            candidate("chest", CanonicalMaintenanceService.Position.CHEST, 20, 100, true, false),
            new CanonicalMaintenanceService.Candidate("unpayable",
                CanonicalMaintenanceService.Position.MAIN_HAND, 1, 100,
                true, false, true, false, "provider:repair_unit", 1.0D, 5),
            candidate("stored", CanonicalMaintenanceService.Position.ADAPTER, 1, 100, false, false)
        );
        var applied = new ArrayList<CanonicalMaintenanceService.Transaction>();
        require(service.maintain("player", CanonicalMaintenanceService.Mode.AUTO, ranks, candidates,
            tx -> { applied.add(tx); return true; }, 199L).status()
            == CanonicalMaintenanceService.Status.IN_COMBAT, "needs 200 authoritative ticks out of combat");
        var success = service.maintain("player", CanonicalMaintenanceService.Mode.AUTO, ranks, candidates,
            tx -> { applied.add(tx); return true; }, 200L);
        require(success.status() == CanonicalMaintenanceService.Status.SUCCESS, "atomic cycle succeeds");
        require(success.itemInstanceId().orElseThrow().equals("chest"), "lowest durability ratio is deterministic");
        require(applied.size() == 1 && applied.get(0).resourceId().equals("provider:repair_unit"),
            "exact provider transaction executes once");
        require(service.maintain("player", CanonicalMaintenanceService.Mode.AUTO, ranks, candidates,
            tx -> true, 559L).status() == CanonicalMaintenanceService.Status.COOLDOWN,
            "rank three interval is 360 ticks and is not shortened");

        service.recordHostileCombat("failed", 0L);
        require(service.maintain("failed", CanonicalMaintenanceService.Mode.AUTO, ranks, candidates,
            tx -> false, 200L).status() == CanonicalMaintenanceService.Status.TRANSACTION_FAILED,
            "failed atomic debit/repair grants nothing");
        require(service.maintain("failed", CanonicalMaintenanceService.Mode.AUTO, ranks, candidates,
            tx -> true, 200L).status() == CanonicalMaintenanceService.Status.SUCCESS,
            "failed transaction does not burn the interval");
    }

    private static void boundMaintenanceRequiresRealAttunementAndPreservesCooldown() {
        var service = new CanonicalMaintenanceService(16);
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0114", 1));
        service.recordHostileCombat("player", 0L);
        var unbound = candidate("ordinary", CanonicalMaintenanceService.Position.MAIN_HAND, 1, 100, true, false);
        require(service.maintain("player", CanonicalMaintenanceService.Mode.BOUND_RELIC, ranks,
            List.of(unbound), tx -> true, 200L).status() == CanonicalMaintenanceService.Status.NO_CANDIDATE,
            "ordinary equipped item is not an attuned relic");
        var bound = candidate("relic", CanonicalMaintenanceService.Position.ADAPTER, 1, 100, true, true);
        require(service.maintain("player", CanonicalMaintenanceService.Mode.BOUND_RELIC, ranks,
            List.of(bound), tx -> true, 200L).status() == CanonicalMaintenanceService.Status.SUCCESS,
            "real active binding is eligible");
        service.clearTransient("player");
        require(service.maintain("player", CanonicalMaintenanceService.Mode.BOUND_RELIC, ranks,
            List.of(bound), tx -> true, 300L).status() == CanonicalMaintenanceService.Status.COOLDOWN,
            "lifecycle cannot erase successful-cycle cooldown");
    }

    private static void fieldReinforcementUsesPlayerAndToolInstanceIdentity() {
        var service = new FieldReinforcementService(64);
        for (int i = 0; i < 12; i++) {
            boolean ready = service.recordHarvest(new FieldReinforcementService.Harvest(
                "player", "tool-instance", action("harvest-" + i), true, false), 3, i);
            require(ready == (i == 11), "ready exactly after twelve legitimate harvests");
        }
        require(!service.recordHarvest(new FieldReinforcementService.Harvest(
            "player", "tool-instance", action("harvest-11"), true, false), 3, 12L),
            "duplicate harvest callback cannot add credit");
        close(0.0D, service.claimRepair(new FieldReinforcementService.Repair(
            "other", "tool-instance", 100.0D, true), 3, 20L), "ownership is player plus instance");
        close(0.0D, service.claimRepair(new FieldReinforcementService.Repair(
            "player", "tool-instance", 100.0D, false), 3, 20L), "unpaid native resource cannot consume ready state");
        close(35.0D, service.claimRepair(new FieldReinforcementService.Repair(
            "player", "tool-instance", 100.0D, true), 3, 20L), "rank three bonus uses native restored amount");
        close(0.0D, service.claimRepair(new FieldReinforcementService.Repair(
            "player", "tool-instance", 100.0D, true), 3, 21L), "ready state has one claim");

        service.invalidateDuplicatedInstance("copied-id");
        require(!service.recordHarvest(new FieldReinforcementService.Harvest(
            "player", "copied-id", action("invalid"), true, false), 3, 30L),
            "duplicated instance remains invalid until safe reidentification");
    }

    private static void bodyCostsResolveOncePerActionAndChannel() {
        var resolver = new BodyCostResolver(64);
        var root = CanonicalActionIdentity.root("player", "sprint-1", "tfc:exhaustion");
        var request = new BodyCostResolver.Request(root, BodyCostResolver.Channel.METABOLIC,
            BodyCostResolver.Cause.SPRINT, 10.0D, BodyCostResolver.Attribution.EXACT);
        var resolved = resolver.resolve(request, List.of(
            new BodyCostResolver.Saving("A0115", 0.12D),
            new BodyCostResolver.Saving("other_exact_source", 0.25D)), 0L);
        close(0.30D, resolved.savingFraction(), "explicit per-event channel cap is thirty percent");
        close(7.0D, resolved.finalCost(), "cap applies to confirmed causal cost");
        require(resolver.resolve(request, List.of(new BodyCostResolver.Saving("A0115", 0.12D)), 1L).duplicate(),
            "one action and channel resolve at most once");
        require(!resolver.resolve(new BodyCostResolver.Request(root, BodyCostResolver.Channel.HYDRATION,
            BodyCostResolver.Cause.SPRINT, 5.0D, BodyCostResolver.Attribution.EXACT),
            List.of(new BodyCostResolver.Saving("A0116", 0.12D)), 1L).duplicate(),
            "separate canonical channel resolves independently");
        var unattributed = resolver.resolve(new BodyCostResolver.Request(
            CanonicalActionIdentity.root("player", "unknown", "tfc:exhaustion"),
            BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.UNATTRIBUTED,
            10.0D, BodyCostResolver.Attribution.UNATTRIBUTED),
            List.of(new BodyCostResolver.Saving("A0115", 0.12D)), 2L);
        close(10.0D, unattributed.finalCost(), "unknown contribution fails closed");
        require(!unattributed.supported(), "UNATTRIBUTED is explicit");
        var derived = resolver.resolve(new BodyCostResolver.Request(root.child("perk:proc"),
            BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.SPRINT,
            10.0D, BodyCostResolver.Attribution.EXACT),
            List.of(new BodyCostResolver.Saving("A0115", 0.12D)), 3L);
        close(10.0D, derived.finalCost(), "derived actions cannot farm body savings");
    }

    private static void bodyCostPolicyMapsOnlyTheAttributedAction() {
        var ranks = FrozenSurvivalPerkRanks.of(Map.of(
            "A0115", 4, "A0116", 4, "A0117", 4, "A0118", 4, "A0119", 4, "A0120", 4));
        close(0.12D, FrozenBodyCostPolicy.savings(ranks,
            BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.SPRINT).get(0).fraction(),
            "sprint metabolic mapping");
        require(FrozenBodyCostPolicy.savings(ranks,
            BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.SPRINT).get(0).sourceId().equals("A0116"),
            "sprint hydration remains separate");
        require(FrozenBodyCostPolicy.savings(ranks,
            BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.SWIM).get(0).sourceId().equals("A0119"),
            "active swim maps to A0119");
        require(FrozenBodyCostPolicy.savings(ranks,
            BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.THERMAL_HOT).isEmpty(),
            "movement perks do not reduce thermal surcharge");
    }

    private static CanonicalMaintenanceService.Candidate candidate(
        String id,
        CanonicalMaintenanceService.Position position,
        int remaining,
        int maximum,
        boolean active,
        boolean bound
    ) {
        return new CanonicalMaintenanceService.Candidate(id, position, remaining, maximum,
            active, bound, true, true, "provider:repair_unit", 1.0D, 5);
    }

    private static CanonicalActionIdentity action(String id) {
        return CanonicalActionIdentity.root("player", id, "server:event");
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
