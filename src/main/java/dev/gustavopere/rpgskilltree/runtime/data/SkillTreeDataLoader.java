package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.gustavopere.rpgskilltree.core.CombatPerkTreeModel;
import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodeSpecializationGrant;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/** Parses and validates a complete candidate before any live catalog is mutated. */
public final class SkillTreeDataLoader {
    private static final ResourceLocation MAIN_TREE = ResourceLocation.parse("rpgskilltree:main");
    private static final ResourceLocation BUILTIN_SOURCE = ResourceLocation.parse("rpgskilltree:builtin/combat_perks");
    private static final ResourceLocation BUILTIN_TREE = ResourceLocation.parse("rpgskilltree:runtime/combat_perks");

    private SkillTreeDataLoader() {}

    public static PreparedSkillTreeData prepare(
        Map<ResourceLocation, JsonElement> ruleResources,
        Map<ResourceLocation, JsonElement> effectResources,
        Map<ResourceLocation, JsonElement> skillResources,
        List<TreeRuleCatalog.NodeRule> additionalRules
    ) {
        Objects.requireNonNull(ruleResources, "ruleResources");
        Objects.requireNonNull(effectResources, "effectResources");
        Objects.requireNonNull(skillResources, "skillResources");
        Objects.requireNonNull(additionalRules, "additionalRules");

        List<TreeRuleCatalog.NodeRule> rules = new ArrayList<>();
        Map<ResourceLocation, ResourceLocation> treeIdsByNode = new LinkedHashMap<>();
        Map<ResourceLocation, ResourceLocation> sourceByNode = new LinkedHashMap<>();

        sorted(ruleResources).forEach(entry -> parseRules(
            entry.getKey(), entry.getValue(), rules, treeIdsByNode, sourceByNode
        ));

        for (TreeRuleCatalog.NodeRule rule : additionalRules) {
            ResourceLocation previous = sourceByNode.putIfAbsent(rule.id(), BUILTIN_SOURCE);
            if (previous != null) {
                throw validation(BUILTIN_SOURCE, rule.id().toString(), "id",
                    "duplicate node id; first declared in " + previous);
            }
            rules.add(rule);
            treeIdsByNode.put(rule.id(), BUILTIN_TREE);
        }

        Map<ResourceLocation, TreeRuleCatalog.NodeRule> rulesById = new LinkedHashMap<>();
        for (TreeRuleCatalog.NodeRule rule : rules) rulesById.put(rule.id(), rule);
        validateRuleReferences(rules, rulesById, sourceByNode);

        List<NodeAttributeEffect> effects = parseEffects(effectResources, rulesById);
        Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> positions = parsePositions(skillResources, rulesById);
        validateRequiredPositions(rules, treeIdsByNode, sourceByNode, positions);

        return new PreparedSkillTreeData(rules, treeIdsByNode, effects, positions);
    }

    /** Existing closed combat acquisition rules remain part of the authoritative runtime candidate. */
    public static List<TreeRuleCatalog.NodeRule> closedCombatRules() {
        List<TreeRuleCatalog.NodeRule> rules = new ArrayList<>();
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            ResourceLocation id = ResourceLocation.parse(node.nodeId());
            NodePurchaseDefinition definition = new NodePurchaseDefinition(
                node.nodeId(), node.maxRank(), node.costPerRank(), node.startingPoint()
            );
            NodeAccessRequirement requirement = new NodeAccessRequirement(
                node.minCharacterLevel(),
                Set.of(),
                node.requiredMastery(),
                Set.of(),
                Set.of(),
                Set.of(),
                node.requiredNodeRanks(),
                Set.of()
            );
            Set<ResourceLocation> neighbors = new HashSet<>();
            node.neighbors().forEach(value -> neighbors.add(ResourceLocation.parse(value)));
            rules.add(new TreeRuleCatalog.NodeRule(id, definition, requirement, null, neighbors));
        }
        return List.copyOf(rules);
    }

    private static void parseRules(
        ResourceLocation source,
        JsonElement element,
        List<TreeRuleCatalog.NodeRule> rules,
        Map<ResourceLocation, ResourceLocation> treeIdsByNode,
        Map<ResourceLocation, ResourceLocation> sourceByNode
    ) {
        JsonObject root = object(source, null, "root", element);
        String treeIdText = requiredString(source, null, root, "treeId");
        ResourceLocation treeId = namespacedId(source, null, "treeId", treeIdText);
        JsonArray nodes = requiredArray(source, null, root, "nodes");

        for (JsonElement nodeElement : nodes) {
            JsonObject node = object(source, null, "nodes", nodeElement);
            String idText = requiredString(source, null, node, "id");
            ResourceLocation id = namespacedId(source, idText, "id", idText);
            ResourceLocation previous = sourceByNode.putIfAbsent(id, source);
            if (previous != null) {
                throw validation(source, idText, "id", "duplicate node id; first declared in " + previous);
            }

            int maxRank = requiredInt(source, idText, node, "maxRank");
            if (maxRank <= 0) throw validation(source, idText, "maxRank", "must be positive");
            int costPerRank = requiredInt(source, idText, node, "costPerRank");
            if (costPerRank <= 0) throw validation(source, idText, "costPerRank", "must be positive");
            boolean startingPoint = requiredBoolean(source, idText, node, "startingPoint");

            ProgressionDomain finalTriadDomain = null;
            if (node.has("finalTriadDomain") && !node.get("finalTriadDomain").isJsonNull()) {
                String value = requiredString(source, idText, node, "finalTriadDomain");
                try {
                    finalTriadDomain = ProgressionDomain.valueOf(value);
                } catch (IllegalArgumentException failure) {
                    throw validation(source, idText, "finalTriadDomain", "unknown domain " + value, failure);
                }
            }
            int finalTriadSlot = node.has("finalTriadSlot")
                ? requiredInt(source, idText, node, "finalTriadSlot") : -1;
            if (finalTriadDomain == null && finalTriadSlot != -1) {
                throw validation(source, idText, "finalTriadSlot", "requires finalTriadDomain");
            }
            if (finalTriadDomain != null && (finalTriadSlot < 0 || finalTriadSlot >= 3)) {
                throw validation(source, idText, "finalTriadSlot", "must be in 0..2");
            }
            if (finalTriadDomain != null && maxRank != 3) {
                throw validation(source, idText, "maxRank", "final triad node must have maxRank 3");
            }

            NodePurchaseDefinition definition;
            try {
                definition = new NodePurchaseDefinition(
                    idText, maxRank, costPerRank, startingPoint, finalTriadDomain, finalTriadSlot
                );
            } catch (IllegalArgumentException failure) {
                throw validation(source, idText, "definition", failure.getMessage(), failure);
            }

            Set<String> requiredClasses = stringSet(source, idText, node, "requiredClasses");
            Map<String, Integer> requiredMastery = intMap(source, idText, node, "requiredMastery", 0, false);
            Set<String> requiredSpecializations = stringSet(source, idText, node, "requiredSpecializations");
            Set<String> requiredClassChoices = stringSet(source, idText, node, "requiredClassChoices");
            Set<String> requiredNodes = resourceIdStringSet(source, idText, node, "requiredNodes");
            Map<String, Integer> requiredNodeRanks = intMap(source, idText, node, "requiredNodeRanks", 1, true);
            Set<String> requiredDiscoveries = stringSet(source, idText, node, "requiredDiscoveries");
            int minCharacterLevel = node.has("minCharacterLevel")
                ? requiredInt(source, idText, node, "minCharacterLevel") : 1;
            if (minCharacterLevel < 1) {
                throw validation(source, idText, "minCharacterLevel", "must be >= 1");
            }

            NodeAccessRequirement requirement;
            try {
                requirement = new NodeAccessRequirement(
                    minCharacterLevel,
                    requiredClasses,
                    requiredMastery,
                    requiredSpecializations,
                    requiredClassChoices,
                    requiredNodes,
                    requiredNodeRanks,
                    requiredDiscoveries
                );
            } catch (IllegalArgumentException failure) {
                throw validation(source, idText, "requirements", failure.getMessage(), failure);
            }

            NodeSpecializationGrant grant = null;
            if (node.has("grantsSpecialization") && !node.get("grantsSpecialization").isJsonNull()) {
                JsonObject grantObject = object(source, idText, "grantsSpecialization", node.get("grantsSpecialization"));
                String specializationId = requiredString(source, idText, grantObject, "id");
                int requiredRank = grantObject.has("requiredRank")
                    ? requiredInt(source, idText, grantObject, "requiredRank") : maxRank;
                if (requiredRank < 1 || requiredRank > maxRank) {
                    throw validation(source, idText, "grantsSpecialization.requiredRank",
                        "must be within 1.." + maxRank);
                }
                grant = new NodeSpecializationGrant(idText, specializationId, requiredRank);
            }

            Set<ResourceLocation> neighbors = resourceIdSet(source, idText, node, "neighbors");
            rules.add(new TreeRuleCatalog.NodeRule(id, definition, requirement, grant, neighbors));
            treeIdsByNode.put(id, treeId);
        }
    }

    private static void validateRuleReferences(
        List<TreeRuleCatalog.NodeRule> rules,
        Map<ResourceLocation, TreeRuleCatalog.NodeRule> rulesById,
        Map<ResourceLocation, ResourceLocation> sourceByNode
    ) {
        for (TreeRuleCatalog.NodeRule rule : rules) {
            ResourceLocation source = sourceByNode.getOrDefault(rule.id(), BUILTIN_SOURCE);
            String entryId = rule.id().toString();
            for (String requiredText : rule.requirement().requiredNodeIds()) {
                ResourceLocation required = namespacedId(source, entryId, "requiredNodes", requiredText);
                if (!rulesById.containsKey(required)) {
                    throw validation(source, entryId, "requiredNodes", "unknown node " + required);
                }
                if (required.equals(rule.id())) {
                    throw validation(source, entryId, "requiredNodes", "node cannot require itself");
                }
            }
            for (Map.Entry<String, Integer> ranked : rule.requirement().requiredNodeRanks().entrySet()) {
                String field = "requiredNodeRanks." + ranked.getKey();
                ResourceLocation required = namespacedId(source, entryId, field, ranked.getKey());
                TreeRuleCatalog.NodeRule target = rulesById.get(required);
                if (target == null) {
                    throw validation(source, entryId, field, "unknown node " + required);
                }
                if (ranked.getValue() > target.definition().maxRank()) {
                    throw validation(source, entryId, field,
                        "required rank " + ranked.getValue() + " exceeds maxRank " + target.definition().maxRank());
                }
            }
            for (ResourceLocation neighbor : rule.neighbors()) {
                if (!rulesById.containsKey(neighbor)) {
                    throw validation(source, entryId, "neighbors", "unknown node " + neighbor);
                }
                if (neighbor.equals(rule.id())) {
                    throw validation(source, entryId, "neighbors", "node cannot be its own neighbor");
                }
            }
        }
    }

    private static List<NodeAttributeEffect> parseEffects(
        Map<ResourceLocation, JsonElement> resources,
        Map<ResourceLocation, TreeRuleCatalog.NodeRule> rulesById
    ) {
        List<NodeAttributeEffect> effects = new ArrayList<>();
        Map<String, ResourceLocation> sourceByEffect = new HashMap<>();
        sorted(resources).forEach(entry -> {
            ResourceLocation source = entry.getKey();
            JsonObject root = object(source, null, "root", entry.getValue());
            if (!root.has("attributes")) return;
            JsonArray attributes = requiredArray(source, null, root, "attributes");
            for (JsonElement effectElement : attributes) {
                JsonObject effect = object(source, null, "attributes", effectElement);
                String effectId = requiredString(source, null, effect, "effectId");
                namespacedId(source, effectId, "effectId", effectId);
                ResourceLocation previous = sourceByEffect.putIfAbsent(effectId, source);
                if (previous != null) {
                    throw validation(source, effectId, "effectId",
                        "duplicate effect id; first declared in " + previous);
                }

                String nodeIdText = requiredString(source, effectId, effect, "nodeId");
                ResourceLocation nodeId = namespacedId(source, effectId, "nodeId", nodeIdText);
                if (!rulesById.containsKey(nodeId)) {
                    throw validation(source, effectId, "nodeId", "unknown node " + nodeId);
                }
                String attributeId = requiredString(source, effectId, effect, "attributeId");
                namespacedId(source, effectId, "attributeId", attributeId);

                String operationText = requiredString(source, effectId, effect, "operation");
                ModifierOperation operation;
                try {
                    operation = ModifierOperation.valueOf(operationText);
                } catch (IllegalArgumentException failure) {
                    throw validation(source, effectId, "operation", "unknown operation " + operationText, failure);
                }
                if (operation == ModifierOperation.OVERRIDE) {
                    throw validation(source, effectId, "operation", "OVERRIDE is not supported for node attribute effects");
                }

                double amount = requiredDouble(source, effectId, effect, "amountPerRank");
                if (!Double.isFinite(amount)) {
                    throw validation(source, effectId, "amountPerRank", "must be finite");
                }
                if (amount == 0.0D) {
                    throw validation(source, effectId, "amountPerRank", "must be non-zero");
                }

                try {
                    effects.add(new NodeAttributeEffect(effectId, nodeIdText, attributeId, operation, amount));
                } catch (IllegalArgumentException failure) {
                    throw validation(source, effectId, "effect", failure.getMessage(), failure);
                }
            }
        });
        return effects.stream().sorted(Comparator.comparing(NodeAttributeEffect::effectId)).toList();
    }

    private static Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> parsePositions(
        Map<ResourceLocation, JsonElement> resources,
        Map<ResourceLocation, TreeRuleCatalog.NodeRule> rulesById
    ) {
        Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> positions = new LinkedHashMap<>();
        Map<ResourceLocation, ResourceLocation> sourceByNode = new HashMap<>();
        sorted(resources).forEach(entry -> {
            ResourceLocation source = entry.getKey();
            JsonObject skill = object(source, null, "root", entry.getValue());
            String idText = requiredString(source, null, skill, "id");
            ResourceLocation id = namespacedId(source, idText, "id", idText);
            if (!rulesById.containsKey(id)) {
                throw validation(source, idText, "id", "layout references unknown node " + id);
            }
            ResourceLocation previous = sourceByNode.putIfAbsent(id, source);
            if (previous != null) {
                throw validation(source, idText, "id", "duplicate layout entry; first declared in " + previous);
            }
            double x = requiredDouble(source, idText, skill, "positionX");
            if (!Double.isFinite(x)) throw validation(source, idText, "positionX", "must be finite");
            double y = requiredDouble(source, idText, skill, "positionY");
            if (!Double.isFinite(y)) throw validation(source, idText, "positionY", "must be finite");
            positions.put(id, new SkillTreeDataSnapshot.NodePosition(x, y));
        });
        return Map.copyOf(positions);
    }

    private static void validateRequiredPositions(
        List<TreeRuleCatalog.NodeRule> rules,
        Map<ResourceLocation, ResourceLocation> treeIdsByNode,
        Map<ResourceLocation, ResourceLocation> sourceByNode,
        Map<ResourceLocation, SkillTreeDataSnapshot.NodePosition> positions
    ) {
        for (TreeRuleCatalog.NodeRule rule : rules) {
            if (!MAIN_TREE.equals(treeIdsByNode.get(rule.id()))) continue;
            if (!positions.containsKey(rule.id())) {
                ResourceLocation source = sourceByNode.getOrDefault(rule.id(), BUILTIN_SOURCE);
                throw validation(source, rule.id().toString(), "position",
                    "main-tree node has no server layout position");
            }
        }
    }

    private static List<Map.Entry<ResourceLocation, JsonElement>> sorted(Map<ResourceLocation, JsonElement> resources) {
        return resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.comparing(ResourceLocation::toString)))
            .toList();
    }

    private static JsonObject object(ResourceLocation source, String entryId, String field, JsonElement element) {
        if (element == null || !element.isJsonObject()) {
            throw validation(source, entryId, field, "must be a JSON object");
        }
        return element.getAsJsonObject();
    }

    private static JsonArray requiredArray(ResourceLocation source, String entryId, JsonObject object, String field) {
        JsonElement element = object.get(field);
        if (element == null || !element.isJsonArray()) {
            throw validation(source, entryId, field, "must be a JSON array");
        }
        return element.getAsJsonArray();
    }

    private static String requiredString(ResourceLocation source, String entryId, JsonObject object, String field) {
        JsonElement element = object.get(field);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
            throw validation(source, entryId, field, "must be a string");
        }
        String value = element.getAsString();
        if (value.isBlank()) throw validation(source, entryId, field, "must not be blank");
        return value;
    }

    private static int requiredInt(ResourceLocation source, String entryId, JsonObject object, String field) {
        JsonElement element = object.get(field);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) {
            throw validation(source, entryId, field, "must be an integer");
        }
        try {
            return Integer.parseInt(element.getAsString());
        } catch (NumberFormatException failure) {
            throw validation(source, entryId, field, "must be a 32-bit integer", failure);
        }
    }

    private static double requiredDouble(ResourceLocation source, String entryId, JsonObject object, String field) {
        JsonElement element = object.get(field);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) {
            throw validation(source, entryId, field, "must be numeric");
        }
        try {
            return element.getAsDouble();
        } catch (RuntimeException failure) {
            throw validation(source, entryId, field, "must be numeric", failure);
        }
    }

    private static boolean requiredBoolean(ResourceLocation source, String entryId, JsonObject object, String field) {
        JsonElement element = object.get(field);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isBoolean()) {
            throw validation(source, entryId, field, "must be a boolean");
        }
        return element.getAsBoolean();
    }

    private static Set<String> stringSet(ResourceLocation source, String entryId, JsonObject object, String field) {
        if (!object.has(field) || object.get(field).isJsonNull()) return Set.of();
        JsonArray array = requiredArray(source, entryId, object, field);
        Set<String> result = new HashSet<>();
        for (JsonElement element : array) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                throw validation(source, entryId, field, "must contain strings only");
            }
            String value = element.getAsString();
            if (value.isBlank()) throw validation(source, entryId, field, "must not contain blank values");
            if (!result.add(value)) throw validation(source, entryId, field, "contains duplicate value " + value);
        }
        return Set.copyOf(result);
    }

    private static Set<String> resourceIdStringSet(ResourceLocation source, String entryId, JsonObject object, String field) {
        Set<String> values = stringSet(source, entryId, object, field);
        values.forEach(value -> namespacedId(source, entryId, field, value));
        return values;
    }

    private static Set<ResourceLocation> resourceIdSet(ResourceLocation source, String entryId, JsonObject object, String field) {
        Set<String> values = stringSet(source, entryId, object, field);
        Set<ResourceLocation> result = new HashSet<>();
        values.forEach(value -> result.add(namespacedId(source, entryId, field, value)));
        return Set.copyOf(result);
    }

    private static Map<String, Integer> intMap(
        ResourceLocation source,
        String entryId,
        JsonObject object,
        String field,
        int minimum,
        boolean namespacedKeys
    ) {
        if (!object.has(field) || object.get(field).isJsonNull()) return Map.of();
        JsonElement element = object.get(field);
        if (!element.isJsonObject()) throw validation(source, entryId, field, "must be a JSON object");
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            String key = entry.getKey();
            if (key.isBlank()) throw validation(source, entryId, field, "must not contain blank keys");
            if (namespacedKeys) namespacedId(source, entryId, field + "." + key, key);
            JsonElement raw = entry.getValue();
            if (!raw.isJsonPrimitive() || !raw.getAsJsonPrimitive().isNumber()) {
                throw validation(source, entryId, field + "." + key, "must be an integer");
            }
            int value;
            try {
                value = Integer.parseInt(raw.getAsString());
            } catch (NumberFormatException failure) {
                throw validation(source, entryId, field + "." + key, "must be a 32-bit integer", failure);
            }
            if (value < minimum) {
                throw validation(source, entryId, field + "." + key, "must be >= " + minimum);
            }
            result.put(key, value);
        }
        return Map.copyOf(result);
    }

    private static ResourceLocation namespacedId(ResourceLocation source, String entryId, String field, String value) {
        if (value.indexOf(':') <= 0 || value.endsWith(":")) {
            throw validation(source, entryId, field, "must be an explicit namespaced id: " + value);
        }
        try {
            return ResourceLocation.parse(value);
        } catch (RuntimeException failure) {
            throw validation(source, entryId, field, "invalid resource id " + value, failure);
        }
    }

    private static SkillTreeDataValidationException validation(
        ResourceLocation source, String entryId, String field, String detail
    ) {
        return new SkillTreeDataValidationException(source, entryId, field, detail);
    }

    private static SkillTreeDataValidationException validation(
        ResourceLocation source, String entryId, String field, String detail, Throwable cause
    ) {
        return new SkillTreeDataValidationException(source, entryId, field, detail, cause);
    }
}
