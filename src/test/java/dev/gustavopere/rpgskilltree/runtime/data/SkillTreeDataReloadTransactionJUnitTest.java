package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SkillTreeDataReloadTransactionJUnitTest {
    private static final ResourceLocation OLD_TREE = ResourceLocation.parse("rpgskilltree:test/old_tree");
    private static final ResourceLocation OLD_NODE = ResourceLocation.parse("rpgskilltree:test/old_node");
    private static final ResourceLocation NEW_TREE = ResourceLocation.parse("rpgskilltree:test/new_tree");
    private static final ResourceLocation NEW_NODE = ResourceLocation.parse("rpgskilltree:test/new_node");

    @AfterEach
    void resetCatalogs() {
        SkillTreeDataReloadTransaction.abort();
        TreeArchitectureCatalog.replace(List.of());
        TreeRuleCatalog.replace(List.of());
        NodeEffectCatalog.replace(List.of());
    }

    @Test
    void invalidFinalStageDoesNotPublishAnyPartialCatalog() {
        TreeArchitectureCatalog.replace(List.of(tree(OLD_TREE)));
        TreeRuleCatalog.replace(List.of(rule(OLD_NODE)));
        NodeAttributeEffect oldEffect = effect("rpgskilltree:test/old_effect", OLD_NODE);
        NodeEffectCatalog.replace(List.of(oldEffect));

        SkillTreeDataReloadTransaction.begin();
        SkillTreeDataReloadTransaction.stageTrees(List.of(
            new SkillTreeDataReloadTransaction.TreeEntry(
                ResourceLocation.parse("rpgskilltree:tree_architecture/new"),
                tree(NEW_TREE)
            )
        ));
        SkillTreeDataReloadTransaction.stageNodeRules(List.of(
            new SkillTreeDataReloadTransaction.NodeRuleEntry(
                ResourceLocation.parse("rpgskilltree:node_rules/new"),
                NEW_TREE,
                rule(NEW_NODE)
            )
        ));
        SkillTreeDataReloadTransaction.stageEffects(List.of(
            new SkillTreeDataReloadTransaction.EffectEntry(
                ResourceLocation.parse("rpgskilltree:node_effects/broken"),
                effect("rpgskilltree:test/broken_effect", ResourceLocation.parse("rpgskilltree:test/missing"))
            )
        ));

        assertThrows(SkillTreeDataValidationException.class, SkillTreeDataReloadTransaction::commit);

        assertEquals(List.of(OLD_TREE.toString()), TreeArchitectureCatalog.ids());
        assertEquals(Set.of(OLD_NODE.toString()), TreeRuleCatalog.definitions().keySet());
        assertEquals(List.of(oldEffect), NodeEffectCatalog.attributeEffects());
    }

    @Test
    void unknownTreeFailsWithResourceNodeAndFieldContext() {
        ResourceLocation source = ResourceLocation.parse("rpgskilltree:node_rules/context_case");

        SkillTreeDataReloadTransaction.begin();
        SkillTreeDataReloadTransaction.stageTrees(List.of(
            new SkillTreeDataReloadTransaction.TreeEntry(
                ResourceLocation.parse("rpgskilltree:tree_architecture/known"),
                tree(OLD_TREE)
            )
        ));
        SkillTreeDataReloadTransaction.stageNodeRules(List.of(
            new SkillTreeDataReloadTransaction.NodeRuleEntry(source, NEW_TREE, rule(NEW_NODE))
        ));
        SkillTreeDataReloadTransaction.stageEffects(List.of());

        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            SkillTreeDataReloadTransaction::commit
        );
        assertEquals(source, failure.resource());
        assertEquals(NEW_NODE.toString(), failure.subjectId());
        assertEquals("treeId", failure.field());
        assertTrue(failure.getMessage().contains(source.toString()));
        assertTrue(failure.getMessage().contains(NEW_NODE.toString()));
        assertTrue(failure.getMessage().contains("treeId"));
    }

    @Test
    void validTransactionPublishesRulesTreesAndEffectsTogether() {
        NodeAttributeEffect newEffect = effect("rpgskilltree:test/new_effect", NEW_NODE);

        SkillTreeDataReloadTransaction.begin();
        SkillTreeDataReloadTransaction.stageTrees(List.of(
            new SkillTreeDataReloadTransaction.TreeEntry(
                ResourceLocation.parse("rpgskilltree:tree_architecture/new"),
                tree(NEW_TREE)
            )
        ));
        SkillTreeDataReloadTransaction.stageNodeRules(List.of(
            new SkillTreeDataReloadTransaction.NodeRuleEntry(
                ResourceLocation.parse("rpgskilltree:node_rules/new"),
                NEW_TREE,
                rule(NEW_NODE)
            )
        ));
        SkillTreeDataReloadTransaction.stageEffects(List.of(
            new SkillTreeDataReloadTransaction.EffectEntry(
                ResourceLocation.parse("rpgskilltree:node_effects/new"),
                newEffect
            )
        ));
        SkillTreeDataReloadTransaction.commit();

        assertEquals(List.of(NEW_TREE.toString()), TreeArchitectureCatalog.ids());
        assertEquals(Set.of(NEW_NODE.toString()), TreeRuleCatalog.definitions().keySet());
        assertEquals(List.of(newEffect), NodeEffectCatalog.attributeEffects());
    }

    @Test
    void rankedRequirementCannotReferenceMissingNode() {
        ResourceLocation source = ResourceLocation.parse("rpgskilltree:node_rules/ranked_requirement");
        ResourceLocation missing = ResourceLocation.parse("rpgskilltree:test/missing_ranked_node");
        TreeRuleCatalog.NodeRule rule = new TreeRuleCatalog.NodeRule(
            NEW_NODE,
            new NodePurchaseDefinition(NEW_NODE.toString(), 1, 1, true),
            new NodeAccessRequirement(1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(), Map.of(missing.toString(), 1), Set.of()),
            null,
            Set.of()
        );

        SkillTreeDataReloadTransaction.begin();
        SkillTreeDataReloadTransaction.stageTrees(List.of(
            new SkillTreeDataReloadTransaction.TreeEntry(
                ResourceLocation.parse("rpgskilltree:tree_architecture/new"),
                tree(NEW_TREE)
            )
        ));
        SkillTreeDataReloadTransaction.stageNodeRules(List.of(
            new SkillTreeDataReloadTransaction.NodeRuleEntry(source, NEW_TREE, rule)
        ));
        SkillTreeDataReloadTransaction.stageEffects(List.of());

        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            SkillTreeDataReloadTransaction::commit
        );
        assertEquals("requiredNodeRanks", failure.field());
        assertEquals(source, failure.resource());
    }

    private static TreeArchitectureCatalog.TreeDefinition tree(ResourceLocation id) {
        return new TreeArchitectureCatalog.TreeDefinition(
            id,
            "test",
            Set.of(),
            "rpgskilltree",
            List.of(),
            TreeArchitectureCatalog.GateDefinition.none(),
            Set.of(),
            Set.of()
        );
    }

    private static TreeRuleCatalog.NodeRule rule(ResourceLocation id) {
        return new TreeRuleCatalog.NodeRule(
            id,
            new NodePurchaseDefinition(id.toString(), 1, 1, true),
            NodeAccessRequirement.none(),
            null,
            Set.of()
        );
    }

    private static NodeAttributeEffect effect(String effectId, ResourceLocation nodeId) {
        return new NodeAttributeEffect(
            effectId,
            nodeId.toString(),
            "minecraft:max_health",
            ModifierOperation.ADD_FLAT,
            1.0D
        );
    }
}
