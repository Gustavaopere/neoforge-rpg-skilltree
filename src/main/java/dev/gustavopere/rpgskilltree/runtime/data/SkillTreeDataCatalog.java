package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodeSpecializationGrant;
import dev.gustavopere.rpgskilltree.core.SkillGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/** Single publication boundary for the authoritative skill-tree datapack state. */
public final class SkillTreeDataCatalog {
    private static volatile SkillTreeDataSnapshot current = SkillTreeDataSnapshot.empty();

    private SkillTreeDataCatalog() {}

    public static SkillTreeDataSnapshot current() {
        return current;
    }

    /**
     * Publishes one fully prepared candidate. All fallible structural work happens before
     * either the compatibility projection or the canonical volatile snapshot is replaced.
     */
    public static synchronized void publish(PreparedSkillTreeData prepared) {
        Objects.requireNonNull(prepared, "prepared");
        SkillTreeDataSnapshot previous = current;
        SkillTreeDataSnapshot next = buildSnapshot(prepared, Math.addExact(previous.revision(), 1L), previous);

        // Compatibility projection for existing effect runtime/tests. This method performs
        // only defensive immutable copies; validation and history construction already ran.
        NodeEffectCatalog.installValidated(next.attributeEffects(), next.clearableAttributeEffects());
        current = next;
    }

    static synchronized void replaceRulesForLegacy(Collection<TreeRuleCatalog.NodeRule> rules) {
        Objects.requireNonNull(rules, "rules");
        SkillTreeDataSnapshot previous = current;
        Map<ResourceLocation, ResourceLocation> treeIds = new HashMap<>();
        for (TreeRuleCatalog.NodeRule rule : rules) {
            treeIds.put(
                rule.id(),
                previous.treeIdsByNode().getOrDefault(rule.id(), ResourceLocation.parse("rpgskilltree:legacy"))
            );
        }
        Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> positions = new HashMap<>();
        previous.positions().forEach((id, position) -> {
            if (treeIds.containsKey(id)) positions.put(id, position);
        });
        publish(new PreparedSkillTreeData(
            List.copyOf(rules),
            treeIds,
            previous.attributeEffects(),
            positions
        ));
    }

    private static SkillTreeDataSnapshot buildSnapshot(
        PreparedSkillTreeData prepared,
        long revision,
        SkillTreeDataSnapshot previous
    ) {
        List<TreeRuleCatalog.NodeRule> rules = prepared.nodeRules().stream()
            .sorted(Comparator.comparing(rule -> rule.id().toString()))
            .toList();

        Map<ResourceLocation, NodePurchaseDefinition> definitions = new LinkedHashMap<>();
        Map<ResourceLocation, dev.gustavopere.rpgskilltree.core.NodeAccessRequirement> requirements = new LinkedHashMap<>();
        List<NodeSpecializationGrant> specializationGrants = new ArrayList<>();
        Set<SkillGraph.Edge> edges = new HashSet<>();
        Set<ResourceLocation> knownIds = new HashSet<>();

        for (TreeRuleCatalog.NodeRule rule : rules) {
            if (!knownIds.add(rule.id())) {
                throw new IllegalArgumentException("duplicate node rule: " + rule.id());
            }
        }
        if (!prepared.treeIdsByNode().keySet().equals(knownIds)) {
            throw new IllegalArgumentException("tree id index must match the published node set");
        }

        for (TreeRuleCatalog.NodeRule rule : rules) {
            definitions.put(rule.id(), rule.definition());
            requirements.put(rule.id(), rule.requirement());
            if (rule.specializationGrant() != null) specializationGrants.add(rule.specializationGrant());

            for (String requiredNode : rule.requirement().requiredNodeIds()) {
                ResourceLocation requiredId = ResourceLocation.parse(requiredNode);
                if (!knownIds.contains(requiredId)) {
                    throw new IllegalArgumentException("unknown required node: " + rule.id() + " -> " + requiredId);
                }
                if (requiredId.equals(rule.id())) {
                    throw new IllegalArgumentException("node cannot require itself: " + rule.id());
                }
            }
            for (Map.Entry<String, Integer> ranked : rule.requirement().requiredNodeRanks().entrySet()) {
                ResourceLocation requiredId = ResourceLocation.parse(ranked.getKey());
                NodePurchaseDefinition required = definitions.get(requiredId);
                if (required == null) {
                    // Definitions later in deterministic order may not be inserted yet; resolve from rules.
                    required = rules.stream()
                        .filter(candidate -> candidate.id().equals(requiredId))
                        .map(TreeRuleCatalog.NodeRule::definition)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(
                            "unknown ranked required node: " + rule.id() + " -> " + requiredId
                        ));
                }
                if (ranked.getValue() > required.maxRank()) {
                    throw new IllegalArgumentException(
                        "required node rank exceeds maxRank: " + rule.id() + " -> " + requiredId
                    );
                }
            }
            for (ResourceLocation neighbor : rule.neighbors()) {
                if (!knownIds.contains(neighbor)) {
                    throw new IllegalArgumentException("unknown node rule neighbor: " + rule.id() + " -> " + neighbor);
                }
                String a = rule.id().toString();
                String b = neighbor.toString();
                edges.add(a.compareTo(b) <= 0 ? new SkillGraph.Edge(a, b) : new SkillGraph.Edge(b, a));
            }
        }

        Map<String, NodeAttributeEffect> effectsById = new LinkedHashMap<>();
        for (NodeAttributeEffect effect : prepared.attributeEffects()) {
            if (!knownIds.contains(ResourceLocation.parse(effect.nodeId()))) {
                throw new IllegalArgumentException("node effect references unknown node: " + effect.effectId());
            }
            if (effectsById.put(effect.effectId(), effect) != null) {
                throw new IllegalArgumentException("duplicate node effect id: " + effect.effectId());
            }
        }
        List<NodeAttributeEffect> effects = effectsById.values().stream()
            .sorted(Comparator.comparing(NodeAttributeEffect::effectId))
            .toList();

        for (ResourceLocation positioned : prepared.positions().keySet()) {
            if (!knownIds.contains(positioned)) {
                throw new IllegalArgumentException("layout position references unknown node: " + positioned);
            }
        }

        Map<ClearableKey, NodeAttributeEffect> clearable = new LinkedHashMap<>();
        previous.clearableAttributeEffects().forEach(effect -> clearable.put(ClearableKey.of(effect), effect));
        effects.forEach(effect -> clearable.put(ClearableKey.of(effect), effect));
        List<NodeAttributeEffect> clearableEffects = clearable.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .toList();

        return new SkillTreeDataSnapshot(
            revision,
            rules,
            prepared.treeIdsByNode(),
            definitions,
            requirements,
            specializationGrants,
            SkillGraph.undirected(new ArrayList<>(edges)),
            effects,
            clearableEffects,
            prepared.positions()
        );
    }

    private record ClearableKey(String effectId, String attributeId) implements Comparable<ClearableKey> {
        static ClearableKey of(NodeAttributeEffect effect) {
            return new ClearableKey(effect.effectId(), effect.attributeId());
        }

        @Override
        public int compareTo(ClearableKey other) {
            int byEffect = effectId.compareTo(other.effectId);
            return byEffect != 0 ? byEffect : attributeId.compareTo(other.attributeId);
        }
    }
}
