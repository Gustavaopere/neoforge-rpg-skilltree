package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class SkillTreeTopologyValidatorJUnitTest {
    private static final ResourceLocation TREE = ResourceLocation.parse("rpgskilltree:test_tree");
    private static final ResourceLocation SOURCE = ResourceLocation.parse("rpgskilltree:node_rules/test_tree.json");
    private static final ResourceLocation ROOT = ResourceLocation.parse("rpgskilltree:test/root");
    private static final ResourceLocation CHILD = ResourceLocation.parse("rpgskilltree:test/child");
    private static final ResourceLocation LEAF = ResourceLocation.parse("rpgskilltree:test/leaf");

    @Test
    void acceptsReciprocalReachableAcyclicTopology() {
        List<TreeRuleCatalog.NodeRule> rules = List.of(
            rule(ROOT, true, Set.of(CHILD), NodeAccessRequirement.none()),
            rule(CHILD, false, Set.of(ROOT, LEAF), requires(ROOT)),
            rule(LEAF, false, Set.of(CHILD), requires(CHILD))
        );
        assertDoesNotThrow(() -> SkillTreeTopologyValidator.validate(rules, treeIds(rules), sources(rules)));
    }

    @Test
    void rejectsAsymmetricNeighborDeclaration() {
        List<TreeRuleCatalog.NodeRule> rules = List.of(
            rule(ROOT, true, Set.of(CHILD), NodeAccessRequirement.none()),
            rule(CHILD, false, Set.of(), requires(ROOT))
        );
        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeTopologyValidator.validate(rules, treeIds(rules), sources(rules))
        );
        assertEquals("neighbors", failure.field());
        assertTrue(failure.getMessage().contains("reciprocal"));
    }

    @Test
    void rejectsUnreachableNonStartingNode() {
        List<TreeRuleCatalog.NodeRule> rules = List.of(
            rule(ROOT, true, Set.of(), NodeAccessRequirement.none()),
            rule(CHILD, false, Set.of(), NodeAccessRequirement.none())
        );
        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeTopologyValidator.validate(rules, treeIds(rules), sources(rules))
        );
        assertEquals("graph", failure.field());
        assertEquals(CHILD.toString(), failure.entryId());
        assertTrue(failure.getMessage().contains("unreachable"));
    }

    @Test
    void rejectsRequirementCycle() {
        List<TreeRuleCatalog.NodeRule> rules = List.of(
            rule(ROOT, true, Set.of(CHILD), requires(CHILD)),
            rule(CHILD, false, Set.of(ROOT), requires(ROOT))
        );
        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeTopologyValidator.validate(rules, treeIds(rules), sources(rules))
        );
        assertEquals("requirements", failure.field());
        assertTrue(failure.getMessage().contains("cycle"));
    }

    private static TreeRuleCatalog.NodeRule rule(
        ResourceLocation id,
        boolean startingPoint,
        Set<ResourceLocation> neighbors,
        NodeAccessRequirement requirement
    ) {
        return new TreeRuleCatalog.NodeRule(
            id,
            new NodePurchaseDefinition(id.toString(), 1, 1, startingPoint),
            requirement,
            null,
            neighbors
        );
    }

    private static NodeAccessRequirement requires(ResourceLocation id) {
        return new NodeAccessRequirement(
            1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(id.toString()), Map.of(), Set.of()
        );
    }

    private static Map<ResourceLocation, ResourceLocation> treeIds(List<TreeRuleCatalog.NodeRule> rules) {
        java.util.LinkedHashMap<ResourceLocation, ResourceLocation> result = new java.util.LinkedHashMap<>();
        rules.forEach(rule -> result.put(rule.id(), TREE));
        return Map.copyOf(result);
    }

    private static Map<ResourceLocation, ResourceLocation> sources(List<TreeRuleCatalog.NodeRule> rules) {
        java.util.LinkedHashMap<ResourceLocation, ResourceLocation> result = new java.util.LinkedHashMap<>();
        rules.forEach(rule -> result.put(rule.id(), SOURCE));
        return Map.copyOf(result);
    }
}
