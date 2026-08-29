package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

final class CanonicalMainTreeTopologyJUnitTest {
    private static final Path ROOT = Path.of("").toAbsolutePath();
    private static final Path BLUEPRINT = ROOT.resolve("src/main/resources/data/rpgskilltree/tree_blueprints/main.json");
    private static final Path LAYOUT = ROOT.resolve("generated/main-tree-layout.json");
    private static final Path RULES = ROOT.resolve("src/main/resources/data/rpgskilltree/node_rules/main.json");
    private static final Path SKILLS = ROOT.resolve("src/main/resources/data/rpgskilltree/skills/main");

    @Test
    void canonicalMainTreeIsCompleteReachableAndLayoutSynchronized() throws IOException {
        JsonObject blueprint = json(BLUEPRINT);
        JsonObject layout = json(LAYOUT);
        JsonObject rules = json(RULES);

        assertEquals("rpgskilltree:main", blueprint.get("id").getAsString());
        assertEquals("rpgskilltree:main", layout.get("id").getAsString());
        assertEquals("rpgskilltree:main", rules.get("treeId").getAsString());

        int target = blueprint.get("target_node_count").getAsInt();
        assertEquals(512, target);
        assertEquals(target, layout.get("target_node_count").getAsInt());
        assertEquals(target, layout.get("actual_node_count").getAsInt());

        JsonArray layoutNodes = layout.getAsJsonArray("nodes");
        JsonArray ruleNodes = rules.getAsJsonArray("nodes");
        assertEquals(target, layoutNodes.size());
        assertEquals(target, ruleNodes.size());

        Map<String, JsonObject> layoutById = indexRawLayout(layoutNodes);
        Map<String, JsonObject> rulesById = indexNamespaced(ruleNodes);
        Map<String, JsonObject> skillsById = loadSkills();
        assertEquals(target, layoutById.size());
        assertEquals(target, rulesById.size());
        assertEquals(target, skillsById.size());
        assertEquals(layoutById.keySet(), rulesById.keySet());
        assertEquals(layoutById.keySet(), skillsById.keySet());

        validateBudgets(blueprint, layoutNodes);
        validateFinalTriads(layoutNodes, rulesById);
        validatePositions(layoutById, skillsById);

        Set<String> layoutEdges = edgesFromLayout(layout.getAsJsonArray("edges"));
        Set<String> ruleEdges = edgesFromRules(ruleNodes);
        Set<String> skillEdges = edgesFromSkills(skillsById);
        assertEquals(layoutEdges, ruleEdges, "authoritative node_rules graph must equal generated layout graph");
        assertEquals(layoutEdges, skillEdges, "UI directConnections must equal generated layout graph");

        Map<String, Set<String>> adjacency = adjacency(layoutEdges, layoutById.keySet());
        Set<String> roots = new HashSet<>();
        for (Map.Entry<String, JsonObject> entry : rulesById.entrySet()) {
            if (entry.getValue().get("startingPoint").getAsBoolean()) roots.add(entry.getKey());
        }
        assertEquals(Set.of("rpgskilltree:core_00"), roots, "canonical main tree must have one intentional root");
        assertEquals(target, reachable(adjacency, roots).size(), "all 512 main-tree nodes must be reachable from core_00");
        adjacency.forEach((node, neighbors) -> assertFalse(neighbors.isEmpty(), "unintentional orphan node: " + node));
    }

    private static void validateBudgets(JsonObject blueprint, JsonArray layoutNodes) {
        int regionBudgetTotal = 0;
        Map<String, Integer> actualByDomain = new HashMap<>();
        Map<String, Integer> kindCounts = new HashMap<>();
        for (JsonElement element : layoutNodes) {
            JsonObject node = element.getAsJsonObject();
            kindCounts.merge(node.get("kind").getAsString(), 1, Integer::sum);
            if (node.has("domain")) actualByDomain.merge(node.get("domain").getAsString(), 1, Integer::sum);
        }

        for (JsonElement element : blueprint.getAsJsonArray("regions")) {
            JsonObject region = element.getAsJsonObject();
            String id = region.get("id").getAsString();
            int budget = region.get("node_budget").getAsInt();
            regionBudgetTotal += budget;
            assertEquals(budget, actualByDomain.getOrDefault(id, 0), "region budget mismatch for " + id);
        }

        int core = blueprint.get("shared_core_nodes").getAsInt();
        int bridges = blueprint.get("hybrid_bridge_nodes").getAsInt();
        int keystones = blueprint.get("outer_keystone_nodes").getAsInt();
        assertEquals(core, kindCounts.getOrDefault("core", 0));
        assertEquals(bridges, kindCounts.getOrDefault("hybrid", 0));
        assertEquals(keystones, kindCounts.getOrDefault("keystone", 0));
        assertEquals(blueprint.get("target_node_count").getAsInt(), regionBudgetTotal + core + bridges + keystones);
    }

    private static void validateFinalTriads(JsonArray layoutNodes, Map<String, JsonObject> rulesById) {
        Map<String, Set<Integer>> slotsByDomain = new HashMap<>();
        int total = 0;
        for (JsonElement element : layoutNodes) {
            JsonObject node = element.getAsJsonObject();
            if (!"final_triad".equals(node.get("kind").getAsString())) continue;
            total++;
            String domain = node.get("domain").getAsString();
            int slot = node.get("finalTriadSlot").getAsInt();
            slotsByDomain.computeIfAbsent(domain, ignored -> new HashSet<>()).add(slot);

            String id = namespaced(node.get("id").getAsString());
            JsonObject rule = rulesById.get(id);
            assertNotNull(rule, "missing final-triad rule " + id);
            assertEquals(3, rule.get("maxRank").getAsInt());
            assertEquals(domain, rule.get("finalTriadDomain").getAsString());
            assertEquals(slot, rule.get("finalTriadSlot").getAsInt());
        }
        assertEquals(33, total, "11 domains x 3 final-triad nodes");
        assertEquals(11, slotsByDomain.size());
        slotsByDomain.forEach((domain, slots) -> assertEquals(Set.of(0, 1, 2), slots, "final triad slots for " + domain));
    }

    private static void validatePositions(Map<String, JsonObject> layoutById, Map<String, JsonObject> skillsById) {
        layoutById.forEach((id, layoutNode) -> {
            JsonObject skill = skillsById.get(id);
            assertNotNull(skill, "missing UI skill " + id);
            assertEquals(layoutNode.get("x").getAsDouble(), skill.get("positionX").getAsDouble(), "positionX " + id);
            assertEquals(layoutNode.get("y").getAsDouble(), skill.get("positionY").getAsDouble(), "positionY " + id);
        });
    }

    private static Map<String, JsonObject> indexRawLayout(JsonArray nodes) {
        Map<String, JsonObject> result = new LinkedHashMap<>();
        for (JsonElement element : nodes) {
            JsonObject node = element.getAsJsonObject();
            String id = namespaced(node.get("id").getAsString());
            assertTrue(result.put(id, node) == null, "duplicate layout node " + id);
        }
        return result;
    }

    private static Map<String, JsonObject> indexNamespaced(JsonArray nodes) {
        Map<String, JsonObject> result = new LinkedHashMap<>();
        for (JsonElement element : nodes) {
            JsonObject node = element.getAsJsonObject();
            String id = namespaced(node.get("id").getAsString());
            assertTrue(result.put(id, node) == null, "duplicate rule node " + id);
        }
        return result;
    }

    private static Map<String, JsonObject> loadSkills() throws IOException {
        Map<String, JsonObject> result = new LinkedHashMap<>();
        try (Stream<Path> files = Files.list(SKILLS)) {
            for (Path path : files.filter(p -> p.getFileName().toString().endsWith(".json")).sorted().toList()) {
                JsonObject skill = json(path);
                String id = namespaced(skill.get("id").getAsString());
                assertTrue(result.put(id, skill) == null, "duplicate UI skill " + id);
            }
        }
        return result;
    }

    private static Set<String> edgesFromLayout(JsonArray edges) {
        Set<String> result = new HashSet<>();
        for (JsonElement element : edges) {
            JsonArray edge = element.getAsJsonArray();
            assertEquals(2, edge.size());
            result.add(edge(namespaced(edge.get(0).getAsString()), namespaced(edge.get(1).getAsString())));
        }
        return result;
    }

    private static Set<String> edgesFromRules(JsonArray nodes) {
        Set<String> result = new HashSet<>();
        for (JsonElement element : nodes) {
            JsonObject node = element.getAsJsonObject();
            String id = namespaced(node.get("id").getAsString());
            JsonArray neighbors = node.has("neighbors") ? node.getAsJsonArray("neighbors") : new JsonArray();
            for (JsonElement neighbor : neighbors) result.add(edge(id, namespaced(neighbor.getAsString())));
        }
        return result;
    }

    private static Set<String> edgesFromSkills(Map<String, JsonObject> skillsById) {
        Set<String> result = new HashSet<>();
        skillsById.forEach((id, skill) -> {
            JsonArray connections = skill.has("directConnections") ? skill.getAsJsonArray("directConnections") : new JsonArray();
            for (JsonElement target : connections) result.add(edge(id, namespaced(target.getAsString())));
        });
        return result;
    }

    private static Map<String, Set<String>> adjacency(Set<String> edges, Set<String> nodes) {
        Map<String, Set<String>> result = new HashMap<>();
        nodes.forEach(node -> result.put(node, new HashSet<>()));
        for (String encoded : edges) {
            String[] pair = encoded.split("\\|", 2);
            assertTrue(result.containsKey(pair[0]), "unknown edge endpoint " + pair[0]);
            assertTrue(result.containsKey(pair[1]), "unknown edge endpoint " + pair[1]);
            result.get(pair[0]).add(pair[1]);
            result.get(pair[1]).add(pair[0]);
        }
        return result;
    }

    private static Set<String> reachable(Map<String, Set<String>> adjacency, Set<String> roots) {
        Set<String> reached = new HashSet<>(roots);
        ArrayDeque<String> pending = new ArrayDeque<>(roots);
        while (!pending.isEmpty()) {
            String current = pending.removeFirst();
            for (String neighbor : adjacency.getOrDefault(current, Set.of())) {
                if (reached.add(neighbor)) pending.addLast(neighbor);
            }
        }
        return reached;
    }

    private static String edge(String first, String second) {
        return first.compareTo(second) <= 0 ? first + "|" + second : second + "|" + first;
    }

    private static String namespaced(String id) {
        return id.contains(":") ? id : "rpgskilltree:" + id;
    }

    private static JsonObject json(Path path) throws IOException {
        try (var reader = Files.newBufferedReader(path)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }
}
