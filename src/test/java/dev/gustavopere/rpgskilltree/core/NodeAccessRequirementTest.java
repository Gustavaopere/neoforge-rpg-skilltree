package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public final class NodeAccessRequirementTest {
    public static void main(String[] args) {
        nodeAndDiscoveryRequirementsAreIndependent();
        masteryThresholdIsRequiredExactly();
        System.out.println("NodeAccessRequirementTest PASS");
    }

    private static void nodeAndDiscoveryRequirementsAreIndependent() {
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
    }

    private static void masteryThresholdIsRequiredExactly() {
        NodeAccessRequirement requirement = new NodeAccessRequirement(
            1,
            Set.of(),
            Map.of("epicfight:sword", 80),
            Set.of(),
            Set.of()
        );

        ProgressionState below = ProgressionState.empty().withMastery(
            MasteryState.of(Map.of("epicfight:sword", 79))
        );
        check(!NodeAccessResolver.satisfied(below, requirement, CharacterLevelCurve.defaultCurve()),
            "mastery below the configured threshold must reject access");

        ProgressionState exact = ProgressionState.empty().withMastery(
            MasteryState.of(Map.of("epicfight:sword", 80))
        );
        check(NodeAccessResolver.satisfied(exact, requirement, CharacterLevelCurve.defaultCurve()),
            "mastery at the configured threshold must satisfy access");
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
