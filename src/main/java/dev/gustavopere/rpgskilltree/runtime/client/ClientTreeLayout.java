package dev.gustavopere.rpgskilltree.runtime.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.SkillGraph;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ClientTreeLayout {
    private static final String RESOURCE_BASE = "/assets/rpgskilltree/tree/";
    private static final ClientTreeLayout MAIN = load("main");
    private static final ClientTreeLayout TECHNOMANCER = load("technomancer");
    private static final ClientTreeLayout WARLOCK = load("warlock");
    private static final ClientTreeLayout DRUID = load("druid");
    private static final ClientTreeLayout METAMORPH = load("metamorph");

    public record Node(
        String id,
        String kind,
        String domain,
        String archetype,
        List<String> domains,
        double x,
        double y,
        Integer finalTriadSlot,
        int maxRank,
        int costPerRank,
        boolean startingPoint,
        NodeAccessRequirement requirement
    ) {
        public Node {
            domains = domains == null ? List.of() : List.copyOf(domains);
            requirement = requirement == null ? NodeAccessRequirement.none() : requirement;
        }

        public boolean finalTriad() {
            return finalTriadSlot != null;
        }

        public String groupLabel() {
            if (domain != null && !domain.isBlank()) return domain;
            if (archetype != null && !archetype.isBlank()) return archetype.toUpperCase();
            if (!domains.isEmpty()) return String.join(" + ", domains);
            return kind.toUpperCase();
        }
    }

    public record Edge(String from, String to) {}

    private final String id;
    private final String displayKey;
    private final List<Node> nodes;
    private final List<Edge> edges;
    private final Map<String, Node> nodesById;
    private final Map<String, NodePurchaseDefinition> definitions;
    private final Map<String, NodeAccessRequirement> requirements;
    private final SkillGraph graph;

    private ClientTreeLayout(String id, String displayKey, List<Node> nodes, List<Edge> edges) {
        this.id = id;
        this.displayKey = displayKey;
        this.nodes = List.copyOf(nodes);
        this.edges = List.copyOf(edges);

        Map<String, Node> byId = new LinkedHashMap<>();
        Map<String, NodePurchaseDefinition> defs = new LinkedHashMap<>();
        Map<String, NodeAccessRequirement> reqs = new LinkedHashMap<>();
        for (Node node : nodes) {
            byId.put(node.id(), node);
            ProgressionDomain triadDomain = node.finalTriad()
                ? ProgressionDomain.valueOf(node.domain())
                : null;
            defs.put(node.id(), new NodePurchaseDefinition(
                node.id(),
                node.maxRank(),
                node.costPerRank(),
                node.startingPoint(),
                triadDomain,
                node.finalTriad() ? node.finalTriadSlot() : -1
            ));
            reqs.put(node.id(), node.requirement());
        }
        this.nodesById = Map.copyOf(byId);
        this.definitions = Map.copyOf(defs);
        this.requirements = Map.copyOf(reqs);
        this.graph = SkillGraph.undirected(
            edges.stream().map(edge -> new SkillGraph.Edge(edge.from(), edge.to())).toList()
        );
    }

    public static ClientTreeLayout main() {
        return MAIN;
    }

    public static List<ClientTreeLayout> availableFor(ProgressionState state) {
        List<ClientTreeLayout> available = new ArrayList<>();
        available.add(MAIN);
        if (state.classProgression().isUnlocked("technomancer")) available.add(TECHNOMANCER);
        if (state.classProgression().isUnlocked("warlock")) available.add(WARLOCK);
        if (state.classProgression().isUnlocked("druid")) available.add(DRUID);
        if (state.classProgression().isUnlocked("metamorph")) available.add(METAMORPH);
        return List.copyOf(available);
    }

    public String id() {
        return id;
    }

    public String displayKey() {
        return displayKey;
    }

    public List<Node> nodes() {
        return nodes;
    }

    public List<Edge> edges() {
        return edges;
    }

    public Node node(String id) {
        return nodesById.get(id);
    }

    public Map<String, NodePurchaseDefinition> definitions() {
        return definitions;
    }

    public Map<String, NodeAccessRequirement> requirements() {
        return requirements;
    }

    public SkillGraph graph() {
        return graph;
    }

    private static ClientTreeLayout load(String resourceName) {
        String resourcePath = RESOURCE_BASE + resourceName + ".json";
        try (InputStream stream = ClientTreeLayout.class.getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Missing client tree resource: " + resourcePath);
            }
            JsonObject root = JsonParser.parseReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8)
            ).getAsJsonObject();

            List<Node> nodes = new ArrayList<>();
            for (JsonElement element : root.getAsJsonArray("nodes")) {
                JsonObject node = element.getAsJsonObject();
                List<String> domains = new ArrayList<>();
                if (node.has("domains")) {
                    for (JsonElement domain : node.getAsJsonArray("domains")) {
                        domains.add(domain.getAsString());
                    }
                }
                NodeAccessRequirement requirement = new NodeAccessRequirement(
                    node.has("minCharacterLevel") ? node.get("minCharacterLevel").getAsInt() : 1,
                    readStringSet(node.getAsJsonArray("requiredClasses")),
                    readIntMap(node.getAsJsonObject("requiredMastery")),
                    readStringSet(node.getAsJsonArray("requiredSpecializations")),
                    readStringSet(node.getAsJsonArray("requiredClassChoices")),
                    readStringSet(node.getAsJsonArray("requiredNodes")),
                    readIntMap(node.getAsJsonObject("requiredNodeRanks")),
                    readStringSet(node.getAsJsonArray("requiredDiscoveries"))
                );
                Integer finalTriadSlot = node.has("finalTriadSlot") ? node.get("finalTriadSlot").getAsInt() : null;
                nodes.add(new Node(
                    node.get("id").getAsString(),
                    node.get("kind").getAsString(),
                    optionalString(node, "domain"),
                    optionalString(node, "archetype"),
                    domains,
                    node.get("x").getAsDouble(),
                    node.get("y").getAsDouble(),
                    finalTriadSlot,
                    node.has("maxRank") ? node.get("maxRank").getAsInt() : finalTriadSlot == null ? 1 : 3,
                    node.has("costPerRank") ? node.get("costPerRank").getAsInt() : 1,
                    node.has("startingPoint") ? node.get("startingPoint").getAsBoolean() : "rpgskilltree:core_00".equals(node.get("id").getAsString()),
                    requirement
                ));
            }

            List<Edge> edges = new ArrayList<>();
            for (JsonElement element : root.getAsJsonArray("edges")) {
                JsonArray edge = element.getAsJsonArray();
                edges.add(new Edge(edge.get(0).getAsString(), edge.get(1).getAsString()));
            }
            return new ClientTreeLayout(
                root.get("id").getAsString(),
                root.has("displayKey") ? root.get("displayKey").getAsString() : "tree.rpgskilltree.main",
                nodes,
                edges
            );
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to close client tree resource", exception);
        }
    }

    private static Set<String> readStringSet(JsonArray values) {
        if (values == null) return Set.of();
        java.util.HashSet<String> result = new java.util.HashSet<>();
        values.forEach(value -> result.add(value.getAsString()));
        return Set.copyOf(result);
    }

    private static Map<String, Integer> readIntMap(JsonObject values) {
        if (values == null) return Map.of();
        HashMap<String, Integer> result = new HashMap<>();
        values.entrySet().forEach(entry -> result.put(entry.getKey(), entry.getValue().getAsInt()));
        return Map.copyOf(result);
    }

    private static String optionalString(JsonObject object, String key) {
        return object.has(key) && !object.get(key).isJsonNull() ? object.get(key).getAsString() : null;
    }
}
