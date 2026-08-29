package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

/** Regression contract: semantic gateways are main-tree node gates, not specialist-state ids. */
public final class CombatPerkGatewayContractTest {
    private static final Set<String> KNOWN_GATEWAYS = Set.of(
        "epic_sword", "epic_axe", "epic_spear", "epic_dagger", "epic_hammer",
        "combat_mace", "combat_scythe", "epic_bow", "epic_crossbow", "combat_fist",
        "martial_core", "martial_agility_bridge", "martial_vitality_bridge",
        "martial_sustain", "arcane_sustain", "arcane_elemental_sustain", "occult_dot_sustain",
        "hybrid_sustain_convergence", "vitality_core", "vitality_martial_bridge", "vitality_agility_bridge"
    );

    private CombatPerkGatewayContractTest() {}

    public static void main(String[] args) {
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            require(KNOWN_GATEWAYS.contains(node.gatewayId()), node.code() + " unknown gateway identity " + node.gatewayId());
            int number = Integer.parseInt(node.code().substring(1));
            if (number <= 80 || number == 81 || number == 82 || number == 87) {
                requireGate(node, CombatPerkTreeModel.MARTIAL_GATEWAY_NODE, node.code() + " must require MARTIAL gateway");
            } else if (number == 83 || number == 84) {
                requireGate(node, CombatPerkTreeModel.ARCANE_GATEWAY_NODE, node.code() + " must require ARCANE gateway");
            } else if (number == 85) {
                requireGate(node, CombatPerkTreeModel.OCCULT_GATEWAY_NODE, "A0085 must require OCCULT gateway");
            } else if (number >= 88 && number <= 100) {
                requireGate(node, CombatPerkTreeModel.VITALITY_GATEWAY_NODE, node.code() + " must require VITALITY gateway");
            }
        }
        requireGate(CombatPerkTreeModel.node("A0078").orElseThrow(), CombatPerkTreeModel.AGILITY_GATEWAY_NODE,
            "A0078 must require AGILITY corridor");
        requireGate(CombatPerkTreeModel.node("A0079").orElseThrow(), CombatPerkTreeModel.VITALITY_GATEWAY_NODE,
            "A0079 must require VITALITY corridor");
        requireGate(CombatPerkTreeModel.node("A0080").orElseThrow(), CombatPerkTreeModel.AGILITY_DODGE_NODE,
            "A0080 must require dodge branch access");
        requireGate(CombatPerkTreeModel.node("A0093").orElseThrow(), CombatPerkTreeModel.MARTIAL_GATEWAY_NODE,
            "A0093 must require MARTIAL guard corridor");
        requireGate(CombatPerkTreeModel.node("A0098").orElseThrow(), CombatPerkTreeModel.AGILITY_GATEWAY_NODE,
            "A0098 must require AGILITY corridor");
        requireGate(CombatPerkTreeModel.node("A0099").orElseThrow(), CombatPerkTreeModel.MARTIAL_GATEWAY_NODE,
            "A0099 must require MARTIAL corridor");
        System.out.println("CombatPerkGatewayContractTest: PASS");
    }

    private static void requireGate(CombatPerkTreeModel.Node node, String gate, String message) {
        require(node.requiredNodeRanks().getOrDefault(gate, 0) >= 1, message);
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
