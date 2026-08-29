package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodeSpecializationGrant;
import dev.gustavopere.rpgskilltree.core.SkillGraph;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Immutable publication unit for authoritative skill-tree rules, effects and server layout metadata. */
public record SkillTreeDataSnapshot(
    long revision,
    List<TreeRuleCatalog.NodeRule> nodeRules,
    Map<ResourceLocation, ResourceLocation> treeIdsByNode,
    Map<ResourceLocation, NodePurchaseDefinition> definitions,
    Map<ResourceLocation, NodeAccessRequirement> requirements,
    List<NodeSpecializationGrant> specializationGrants,
    SkillGraph graph,
    List<NodeAttributeEffect> attributeEffects,
    List<NodeAttributeEffect> clearableAttributeEffects,
    Map<ResourceLocation, NodePosition> positions
) {
    public SkillTreeDataSnapshot {
        if (revision < 0L) throw new IllegalArgumentException("revision must be non-negative");
        nodeRules = List.copyOf(Objects.requireNonNull(nodeRules));
        treeIdsByNode = Map.copyOf(Objects.requireNonNull(treeIdsByNode));
        definitions = Map.copyOf(Objects.requireNonNull(definitions));
        requirements = Map.copyOf(Objects.requireNonNull(requirements));
        specializationGrants = List.copyOf(Objects.requireNonNull(specializationGrants));
        Objects.requireNonNull(graph);
        attributeEffects = List.copyOf(Objects.requireNonNull(attributeEffects));
        clearableAttributeEffects = List.copyOf(Objects.requireNonNull(clearableAttributeEffects));
        positions = Map.copyOf(Objects.requireNonNull(positions));
    }

    public ResourceLocation treeId(ResourceLocation nodeId) {
        return treeIdsByNode.get(nodeId);
    }

    public static SkillTreeDataSnapshot empty() {
        return new SkillTreeDataSnapshot(
            0L,
            List.of(),
            Map.of(),
            Map.of(),
            Map.of(),
            List.of(),
            SkillGraph.undirected(List.of()),
            List.of(),
            List.of(),
            Map.of()
        );
    }

    public record NodePosition(double x, double y) {
        public NodePosition {
            if (!Double.isFinite(x)) throw new IllegalArgumentException("x must be finite");
            if (!Double.isFinite(y)) throw new IllegalArgumentException("y must be finite");
        }
    }
}
