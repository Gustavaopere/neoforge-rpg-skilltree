package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Fully parsed and validated candidate. Publishing this object must not perform fallible parsing. */
public record PreparedSkillTreeData(
    List<TreeRuleCatalog.NodeRule> nodeRules,
    Map<ResourceLocation, ResourceLocation> treeIdsByNode,
    List<NodeAttributeEffect> attributeEffects,
    Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> positions
) {
    public PreparedSkillTreeData {
        nodeRules = List.copyOf(Objects.requireNonNull(nodeRules));
        treeIdsByNode = Map.copyOf(Objects.requireNonNull(treeIdsByNode));
        attributeEffects = List.copyOf(Objects.requireNonNull(attributeEffects));
        positions = Map.copyOf(Objects.requireNonNull(positions));
        SkillTreeTopologyValidator.validate(nodeRules, treeIdsByNode, Map.of());
    }
}
