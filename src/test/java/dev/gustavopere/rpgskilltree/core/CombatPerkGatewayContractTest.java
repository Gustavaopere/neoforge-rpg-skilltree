package dev.gustavopere.rpgskilltree.core;

/** Regression contract: outer-tree weapon gateways are main-tree node gates, not specialist-state ids. */
public final class CombatPerkGatewayContractTest {
    private static final String MARTIAL_GATEWAY_NODE = "rpgskilltree:martial_000";

    private CombatPerkGatewayContractTest() {}

    public static void main(String[] args) {
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            require(node.requiredSpecializations().isEmpty(),
                node.code() + " must not use SpecializationProgressionState for an outer-tree weapon gateway");
            require(node.requiredNodeRanks().getOrDefault(MARTIAL_GATEWAY_NODE, 0) >= 1,
                node.code() + " must require the learned Martial gateway node");
        }
        System.out.println("CombatPerkGatewayContractTest: PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
