package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.NodeBehaviorEffect;
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
     * either compatibility projection or the canonical volatile snapshot is replaced.
     */
    public static synchronized void publish(PreparedSkillTreeData prepared) {
        Objects.requireNonNull(prepared, "prepared");
        SkillTreeDataSnapshot previous = current;
        SkillTreeDataSnapshot next = buildSnapshot(prepared, Math.addExact(previous.revision(), 1L), previous);

        // Both projection installers only perform immutable copies of already validated state.
        // No parser/reference validation is allowed past this boundary.
        TreeRuleCatalog.installValidated(
            next.definitions(),
            next.requirements(),
            next.specializationGrants(),
            next.graph()
        );
        NodeEffectCatalog.installValidated(
            next.attributeEffects(),
            next.clearableAttributeEffects(),
            next.behaviorEffects()
        );
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
            previous.behaviorEffects(),
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
        Map<ResourceLocation, NodeAccessRequirement> requirements = new LinkedHashMap<>();
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

        Map<ResourceLocation, NodePurchaseDefinition> allDefinitions = new HashMap<>();
        rules.forEach(rule -> allDefinitions.put(rule.id(), rule.definition()));

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
                NodePurchaseDefinition required = allDefinitions.get(requiredId);
                if (required == null) {
                    throw new IllegalArgumentException("unknown ranked required node: " + rule.id() + " -> " + requiredId);
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

        Set<String> effectIds = new HashSet<>();
        Map<String, NodeAttributeEffect> effectsById = new LinkedHashMap<>();
        for (NodeAttributeEffect effect : prepared.attributeEffects()) {
            if (!knownIds.contains(ResourceLocation.parse(effect.nodeId()))) {
                throw new IllegalArgumentException("node effect references unknown node: " + effect.effectId());
            }
            if (!effectIds.add(effect.effectId())) {
                throw new IllegalArgumentException("duplicate node effect id: " + effect.effectId());
            }
            effectsById.put(effect.effectId(), effect);
        }
        List<NodeAttributeEffect> effects = effectsById.values().stream()
            .sorted(Comparator.comparing(NodeAttributeEffect::effectId))
            .toList();

        List<NodeBehaviorEffect> behaviors = new ArrayList<>();
        for (NodeBehaviorEffect effect : prepared.behaviorEffects()) {
            if (!knownIds.contains(ResourceLocation.parse(effect.nodeId()))) {
                throw new IllegalArgumentException("behavior effect references unknown node: " + effect.effectId());
            }
            if (!effectIds.add(effect.effectId())) {
                throw new IllegalArgumentException("duplicate node effect id: " + effect.effectId());
            }
            behaviors.add(effect);
        }
        behaviors = behaviors.stream()
            .sorted(Comparator.comparing(NodeBehaviorEffect::effectId))
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
            behaviors,
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
