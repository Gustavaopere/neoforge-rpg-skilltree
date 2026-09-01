package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeInvestmentMetadata;
import java.util.Map;
import java.util.Objects;

/** Fully prepared skill-tree reload payload, including read-only class projection metadata. */
public record PreparedSkillTreeReload(
    PreparedSkillTreeData skillTreeData,
    Map<String, NodeInvestmentMetadata> classInvestmentMetadata
) {
    public PreparedSkillTreeReload {
        Objects.requireNonNull(skillTreeData, "skillTreeData");
        classInvestmentMetadata = Map.copyOf(
            Objects.requireNonNull(classInvestmentMetadata, "classInvestmentMetadata")
        );
    }
}
