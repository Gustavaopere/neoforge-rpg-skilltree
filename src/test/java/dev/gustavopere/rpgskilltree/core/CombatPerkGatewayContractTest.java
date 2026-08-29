package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

/** Regression contract: outer-tree weapon gateways are main-tree node gates, not specialist-state ids. */
public final class CombatPerkGatewayContractTest {
    private static final Set<String> KNOWN_GATEWAYS = Set.of(
        "epic_sword", "epic_axe", "epic_spear", "epic_dagger", "epic_hammer",
        "combat_mace", "combat_scythe", "epic_bow", "epic_crossbow", "combat_fist"
    );

    private CombatPerkGatewayContractTest() {}

    public static void main(String[] args) {
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            require(KNOWN_GATEWAYS.contains(node.gatewayId()), node.code() + " unknown gateway identity " + node.gatewayId());
            require(node.requiredNodeRanks().getOrDefault(CombatPerkTreeModel.MARTIAL_GATEWAY_NODE, 0) >= 1,
                node.code() + " must require the learned Martial gateway node");
        }
        System.out.println("CombatPerkGatewayContractTest: PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
