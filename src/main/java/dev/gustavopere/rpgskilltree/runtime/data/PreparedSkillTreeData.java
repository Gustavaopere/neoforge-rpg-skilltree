package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.NodeBehaviorEffect;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Fully parsed and validated candidate. Publishing this object must not perform fallible parsing. */
public record PreparedSkillTreeData(
    List<TreeRuleCatalog.NodeRule> nodeRules,
    Map<ResourceLocation, ResourceLocation> treeIdsByNode,
    List<NodeAttributeEffect> attributeEffects,
    List<NodeBehaviorEffect> behaviorEffects,
    Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> positions
) {
    public PreparedSkillTreeData {
        nodeRules = List.copyOf(Objects.requireNonNull(nodeRules));
        treeIdsByNode = Map.copyOf(Objects.requireNonNull(treeIdsByNode));
        attributeEffects = List.copyOf(Objects.requireNonNull(attributeEffects));
        behaviorEffects = List.copyOf(Objects.requireNonNull(behaviorEffects));
        positions = Map.copyOf(Objects.requireNonNull(positions));
        SkillTreeTopologyValidator.validate(nodeRules, treeIdsByNode, Map.of());
    }

    /** Compatibility constructor for callers that do not publish behavioral effects. */
    public PreparedSkillTreeData(
        List<TreeRuleCatalog.NodeRule> nodeRules,
        Map<ResourceLocation, ResourceLocation> treeIdsByNode,
        List<NodeAttributeEffect> attributeEffects,
        Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> positions
    ) {
        this(nodeRules, treeIdsByNode, attributeEffects, List.of(), positions);
    }
}
