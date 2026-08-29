package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/**
 * Stages the server-side skill-tree datapack pieces and publishes them only after the complete
 * candidate state has passed local and cross-catalog validation.
 */
public final class SkillTreeDataReloadTransaction {
    public record TreeEntry(ResourceLocation source, TreeArchitectureCatalog.TreeDefinition definition) {
        public TreeEntry {
            Objects.requireNonNull(source, "source");
            Objects.requireNonNull(definition, "definition");
        }
    }

    public record NodeRuleEntry(
        ResourceLocation source,
        ResourceLocation treeId,
        TreeRuleCatalog.NodeRule rule
    ) {
        public NodeRuleEntry {
            Objects.requireNonNull(source, "source");
            Objects.requireNonNull(treeId, "treeId");
            Objects.requireNonNull(rule, "rule");
        }
    }

    public record EffectEntry(ResourceLocation source, NodeAttributeEffect effect) {
        public EffectEntry {
            Objects.requireNonNull(source, "source");
            Objects.requireNonNull(effect, "effect");
        }
    }

    private static Transaction current;

    private SkillTreeDataReloadTransaction() {}

    public static synchronized void begin() {
        current = new Transaction();
    }

    public static synchronized void stageTrees(List<TreeEntry> entries) {
        Transaction transaction = requireCurrent();
        transaction.trees = List.copyOf(Objects.requireNonNull(entries, "entries"));
        transaction.treesStaged = true;
    }

    public static synchronized void stageNodeRules(List<NodeRuleEntry> entries) {
        Transaction transaction = requireCurrent();
        transaction.nodeRules = List.copyOf(Objects.requireNonNull(entries, "entries"));
        transaction.nodeRulesStaged = true;
    }

    public static synchronized void stageEffects(List<EffectEntry> entries) {
        Transaction transaction = requireCurrent();
        transaction.effects = List.copyOf(Objects.requireNonNull(entries, "entries"));
        transaction.effectsStaged = true;
    }

    public static synchronized void commit() {
        Transaction transaction = requireCurrent();
        try {
            transaction.requireComplete();
            validateCrossCatalog(transaction);

            TreeArchitectureCatalog.PreparedSnapshot architecture = prepareTrees(transaction.trees);
            TreeRuleCatalog.PreparedSnapshot rules = prepareRules(transaction.nodeRules);
            NodeEffectCatalog.PreparedSnapshot effects = prepareEffects(transaction.effects);

            // No validation occurs after this point. All three prepared snapshots are known-good.
            TreeArchitectureCatalog.publish(architecture);
            TreeRuleCatalog.publish(rules);
            NodeEffectCatalog.publish(effects);
        } finally {
            current = null;
        }
    }

    public static synchronized void abort() {
        current = null;
    }

    private static Transaction requireCurrent() {
        if (current == null) {
            throw new IllegalStateException("skill-tree reload transaction has not begun");
        }
        return current;
    }

    private static void validateCrossCatalog(Transaction transaction) {
        Map<ResourceLocation, TreeEntry> trees = uniqueTrees(transaction.trees);
        Map<ResourceLocation, NodeRuleEntry> nodes = uniqueNodes(transaction.nodeRules);
        uniqueEffects(transaction.effects);

        validateTreeBridges(trees);
        validateNodes(trees, nodes);
        validateEffects(nodes, transaction.effects);
    }

    private static Map<ResourceLocation, TreeEntry> uniqueTrees(List<TreeEntry> entries) {
        Map<ResourceLocation, TreeEntry> byId = new HashMap<>();
        Map<String, TreeEntry> byCatalogCode = new HashMap<>();
        for (TreeEntry entry : entries) {
            ResourceLocation id = entry.definition().id();
            if (byId.putIfAbsent(id, entry) != null) {
                throw error(entry.source(), id.toString(), "id", "duplicate tree id: " + id);
            }
            for (TreeArchitectureCatalog.BranchDefinition branch : entry.definition().branches()) {
                if (branch.catalogCode() == null) continue;
                TreeEntry previous = byCatalogCode.putIfAbsent(branch.catalogCode(), entry);
                if (previous != null) {
                    throw error(
                        entry.source(),
                        id.toString(),
                        "branches.catalogCode",
                        "duplicate catalog code: " + branch.catalogCode()
                    );
                }
            }
        }
        return Map.copyOf(byId);
    }

    private static Map<ResourceLocation, NodeRuleEntry> uniqueNodes(List<NodeRuleEntry> entries) {
        Map<ResourceLocation, NodeRuleEntry> byId = new HashMap<>();
        for (NodeRuleEntry entry : entries) {
            ResourceLocation id = entry.rule().id();
            if (byId.putIfAbsent(id, entry) != null) {
                throw error(entry.source(), id.toString(), "id", "duplicate node id: " + id);
            }
        }
        return Map.copyOf(byId);
    }

    private static void uniqueEffects(List<EffectEntry> entries) {
        Set<String> ids = new HashSet<>();
        for (EffectEntry entry : entries) {
            if (!ids.add(entry.effect().effectId())) {
                throw error(
                    entry.source(),
                    entry.effect().effectId(),
                    "effectId",
                    "duplicate effect id: " + entry.effect().effectId()
                );
            }
        }
    }

    private static void validateTreeBridges(Map<ResourceLocation, TreeEntry> trees) {
        for (TreeEntry entry : trees.values()) {
            for (ResourceLocation bridge : entry.definition().bridges()) {
                if (bridge.equals(entry.definition().id())) {
                    throw error(entry.source(), entry.definition().id().toString(), "bridges", "tree cannot bridge to itself");
                }
                if (!trees.containsKey(bridge)) {
                    throw error(
                        entry.source(),
                        entry.definition().id().toString(),
                        "bridges",
                        "unknown tree bridge: " + bridge
                    );
                }
            }
        }
    }

    private static void validateNodes(
        Map<ResourceLocation, TreeEntry> trees,
        Map<ResourceLocation, NodeRuleEntry> nodes
    ) {
        for (NodeRuleEntry entry : nodes.values()) {
            ResourceLocation id = entry.rule().id();
            if (!trees.containsKey(entry.treeId())) {
                throw error(entry.source(), id.toString(), "treeId", "unknown tree: " + entry.treeId());
            }

            for (String required : entry.rule().requirement().requiredNodeIds()) {
                ResourceLocation requiredId = parseReference(entry, "requiredNodes", required);
                validateRequiredReference(entry, id, requiredId, nodes, "requiredNodes");
            }

            for (Map.Entry<String, Integer> required : entry.rule().requirement().requiredNodeRanks().entrySet()) {
                ResourceLocation requiredId = parseReference(entry, "requiredNodeRanks", required.getKey());
                validateRequiredReference(entry, id, requiredId, nodes, "requiredNodeRanks");
                NodePurchaseDefinition requiredDefinition = nodes.get(requiredId).rule().definition();
                if (required.getValue() > requiredDefinition.maxRank()) {
                    throw error(
                        entry.source(),
                        id.toString(),
                        "requiredNodeRanks",
                        "required rank " + required.getValue() + " exceeds maxRank "
                            + requiredDefinition.maxRank() + " for " + requiredId
                    );
                }
            }

            for (ResourceLocation neighbor : entry.rule().neighbors()) {
                if (neighbor.equals(id)) {
                    throw error(entry.source(), id.toString(), "neighbors", "node cannot be its own neighbor");
                }
                if (!nodes.containsKey(neighbor)) {
                    throw error(entry.source(), id.toString(), "neighbors", "unknown neighbor: " + neighbor);
                }
            }
        }
    }

    private static ResourceLocation parseReference(NodeRuleEntry entry, String field, String value) {
        try {
            return ResourceLocation.parse(value);
        } catch (RuntimeException failure) {
            throw SkillTreeDataValidationException.wrap(entry.source(), entry.rule().id().toString(), field, failure);
        }
    }

    private static void validateRequiredReference(
        NodeRuleEntry entry,
        ResourceLocation id,
        ResourceLocation requiredId,
        Map<ResourceLocation, NodeRuleEntry> nodes,
        String field
    ) {
        if (requiredId.equals(id)) {
            throw error(entry.source(), id.toString(), field, "node cannot require itself");
        }
        if (!nodes.containsKey(requiredId)) {
            throw error(entry.source(), id.toString(), field, "unknown required node: " + requiredId);
        }
    }

    private static void validateEffects(Map<ResourceLocation, NodeRuleEntry> nodes, List<EffectEntry> effects) {
        for (EffectEntry entry : effects) {
            ResourceLocation nodeId;
            try {
                nodeId = ResourceLocation.parse(entry.effect().nodeId());
            } catch (RuntimeException failure) {
                throw SkillTreeDataValidationException.wrap(
                    entry.source(), entry.effect().effectId(), "nodeId", failure
                );
            }
            if (!nodes.containsKey(nodeId)) {
                throw error(
                    entry.source(),
                    entry.effect().effectId(),
                    "nodeId",
                    "unknown effect node: " + nodeId
                );
            }
        }
    }

    private static TreeArchitectureCatalog.PreparedSnapshot prepareTrees(List<TreeEntry> entries) {
        try {
            return TreeArchitectureCatalog.prepare(entries.stream().map(TreeEntry::definition).toList());
        } catch (RuntimeException failure) {
            TreeEntry context = entries.isEmpty() ? null : entries.getFirst();
            if (context == null) throw failure;
            throw SkillTreeDataValidationException.wrap(
                context.source(), context.definition().id().toString(), "tree", failure
            );
        }
    }

    private static TreeRuleCatalog.PreparedSnapshot prepareRules(List<NodeRuleEntry> entries) {
        try {
            return TreeRuleCatalog.prepare(entries.stream().map(NodeRuleEntry::rule).toList());
        } catch (RuntimeException failure) {
            NodeRuleEntry context = entries.isEmpty() ? null : entries.getFirst();
            if (context == null) throw failure;
            throw SkillTreeDataValidationException.wrap(
                context.source(), context.rule().id().toString(), "node", failure
            );
        }
    }

    private static NodeEffectCatalog.PreparedSnapshot prepareEffects(List<EffectEntry> entries) {
        try {
            return NodeEffectCatalog.prepare(entries.stream().map(EffectEntry::effect).toList());
        } catch (RuntimeException failure) {
            EffectEntry context = entries.isEmpty() ? null : entries.getFirst();
            if (context == null) throw failure;
            throw SkillTreeDataValidationException.wrap(
                context.source(), context.effect().effectId(), "effect", failure
            );
        }
    }

    private static SkillTreeDataValidationException error(
        ResourceLocation resource,
        String subjectId,
        String field,
        String detail
    ) {
        return new SkillTreeDataValidationException(resource, subjectId, field, detail);
    }

    private static final class Transaction {
        private List<TreeEntry> trees = List.of();
        private List<NodeRuleEntry> nodeRules = List.of();
        private List<EffectEntry> effects = List.of();
        private boolean treesStaged;
        private boolean nodeRulesStaged;
        private boolean effectsStaged;

        private void requireComplete() {
            if (!treesStaged || !nodeRulesStaged || !effectsStaged) {
                throw new IllegalStateException(
                    "incomplete skill-tree reload transaction: trees=" + treesStaged
                        + " nodeRules=" + nodeRulesStaged + " effects=" + effectsStaged
                );
            }
        }
    }
}
