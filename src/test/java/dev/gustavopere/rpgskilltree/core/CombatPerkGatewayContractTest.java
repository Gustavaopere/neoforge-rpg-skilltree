package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

/** Regression contract: semantic gateways are main-tree node gates, not specialist-state ids. */
public final class CombatPerkGatewayContractTest {
    private static final Set<String> KNOWN_GATEWAYS = Set.of(
        "epic_sword", "epic_axe", "epic_spear", "epic_dagger", "epic_hammer",
        "combat_mace", "combat_scythe", "epic_bow", "epic_crossbow", "combat_fist",
        "martial_core", "martial_agility_bridge", "martial_vitality_bridge"
    );

    private CombatPerkGatewayContractTest() {}

    public static void main(String[] args) {
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            require(KNOWN_GATEWAYS.contains(node.gatewayId()), node.code() + " unknown gateway identity " + node.gatewayId());
            require(node.requiredNodeRanks().getOrDefault(CombatPerkTreeModel.MARTIAL_GATEWAY_NODE, 0) >= 1,
                node.code() + " must require the learned Martial gateway node");
        }
        require(CombatPerkTreeModel.node("A0078").orElseThrow().requiredNodeRanks()
            .getOrDefault(CombatPerkTreeModel.AGILITY_GATEWAY_NODE,0) >= 1, "A0078 must require AGILITY corridor");
        require(CombatPerkTreeModel.node("A0079").orElseThrow().requiredNodeRanks()
            .getOrDefault(CombatPerkTreeModel.VITALITY_GATEWAY_NODE,0) >= 1, "A0079 must require VITALITY corridor");
        require(CombatPerkTreeModel.node("A0080").orElseThrow().requiredNodeRanks()
            .getOrDefault(CombatPerkTreeModel.AGILITY_DODGE_NODE,0) >= 1, "A0080 must require dodge branch access");
        System.out.println("CombatPerkGatewayContractTest: PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
