package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public final class NodeAccessRequirementTest {
    public static void main(String[] args) {
        NodeAccessRequirement requirement = new NodeAccessRequirement(
            1,
            Set.of(),
            Map.of(),
            Set.of(),
            Set.of(),
            Set.of("rpgskilltree:occult_000"),
            Set.of("eidolon:ritual:completed")
        );

        ProgressionState empty = ProgressionState.empty();
        check(!NodeAccessResolver.satisfied(empty, requirement, CharacterLevelCurve.defaultCurve()),
            "missing node and discovery must reject access");

        ProgressionState nodeOnly = empty.withPassiveNodes(
            PassiveNodeProgress.of(Map.of("rpgskilltree:occult_000", 1))
        );
        check(!NodeAccessResolver.satisfied(nodeOnly, requirement, CharacterLevelCurve.defaultCurve()),
            "required discovery must be enforced independently");

        ProgressionState ready = nodeOnly.withDiscoveries(
            DiscoveryProgress.of(Set.of("eidolon:ritual:completed"))
        );
        check(NodeAccessResolver.satisfied(ready, requirement, CharacterLevelCurve.defaultCurve()),
            "required node plus discovery must satisfy access");

        System.out.println("NodeAccessRequirementTest PASS");
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
