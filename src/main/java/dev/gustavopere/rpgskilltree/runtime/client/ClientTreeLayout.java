package dev.gustavopere.rpgskilltree.runtime.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition;
import dev.gustavopere.rpgskilltree.core.CombatPerkTreeModel;
import dev.gustavopere.rpgskilltree.core.FrozenA0051A0100TreeModel;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkNodeBinding;
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
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ClientTreeLayout {
    private static final String RESOURCE_BASE = "/assets/rpgskilltree/tree/";
    private static final ClientTreeLayout MAIN = load("main");
    private static final ClientTreeLayout NOTION_COMBAT = createNotionCombat();
    private static final ClientTreeLayout FROZEN_COMBAT = createFrozenCombat();
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
        available.add(FROZEN_COMBAT);
        if (CombatPerkTreeModel.specializationIds().stream().anyMatch(state.specializations()::isUnlocked)) {
            available.add(NOTION_COMBAT);
        }
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

    private static ClientTreeLayout createNotionCombat() {
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Map<CombatPerkDefinition.WeaponFamily, Integer> familyIndices = new EnumMap<>(CombatPerkDefinition.WeaponFamily.class);

        for (CombatPerkTreeModel.Node source : CombatPerkTreeModel.all()) {
            int localIndex = familyIndices.getOrDefault(source.weaponFamily(), 0);
            familyIndices.put(source.weaponFamily(), localIndex + 1);
            double[] position = combatPosition(source.weaponFamily(), localIndex);
            NodeAccessRequirement requirement = new NodeAccessRequirement(
                source.minCharacterLevel(),
                Set.of(),
                source.requiredMastery(),
                source.requiredSpecializations(),
                Set.of(),
                Set.of(),
                source.requiredNodeRanks(),
                Set.of()
            );
            nodes.add(new Node(
                source.nodeId(),
                source.kind(),
                null,
                source.weaponFamily().name().toLowerCase(java.util.Locale.ROOT),
                source.domains().stream().sorted().toList(),
                position[0],
                position[1],
                null,
                source.maxRank(),
                source.costPerRank(),
                source.startingPoint(),
                requirement
            ));
        }

        Set<String> seenEdges = new HashSet<>();
        for (CombatPerkTreeModel.Node source : CombatPerkTreeModel.all()) {
            for (String neighbor : source.neighbors().stream().sorted().toList()) {
                String from = source.nodeId().compareTo(neighbor) <= 0 ? source.nodeId() : neighbor;
                String to = source.nodeId().compareTo(neighbor) <= 0 ? neighbor : source.nodeId();
                String key = from + "\u0000" + to;
                if (seenEdges.add(key)) edges.add(new Edge(from, to));
            }
        }
        return new ClientTreeLayout(
            "rpgskilltree:notion_combat",
            "tree.rpgskilltree.notion_combat",
            nodes,
            edges
        );
    }

    private static ClientTreeLayout createFrozenCombat() {
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Map<dev.gustavopere.rpgskilltree.core.FrozenCombatPerkDefinition.Family, Integer> familyIndices =
            new EnumMap<>(dev.gustavopere.rpgskilltree.core.FrozenCombatPerkDefinition.Family.class);
        Set<String> localIds = new HashSet<>();
        FrozenA0051A0100TreeModel.all().forEach(node -> localIds.add(node.nodeId()));

        for (FrozenA0051A0100TreeModel.Node source : FrozenA0051A0100TreeModel.all()) {
            int localIndex = familyIndices.getOrDefault(source.family(), 0);
            familyIndices.put(source.family(), localIndex + 1);
            double x = (source.family().ordinal() - 2.5D) * 270.0D;
            double y = localIndex * 105.0D;
            boolean clientRoot = source.startingPoint()
                || source.neighbors().stream().noneMatch(localIds::contains);
            NodeAccessRequirement requirement = new NodeAccessRequirement(
                source.minCharacterLevel(), Set.of(), source.requiredMastery(), source.requiredSpecializations(),
                Set.of(), source.requiredNodes(), source.requiredNodeRanks(), Set.of());
            nodes.add(new Node(
                source.nodeId(), source.kind().name().toLowerCase(java.util.Locale.ROOT), null,
                source.family().name().toLowerCase(java.util.Locale.ROOT),
                source.domains().stream().sorted().toList(), x, y, null,
                source.maxRank(), source.costPerRank(), clientRoot, requirement));
        }

        Set<String> seenEdges = new HashSet<>();
        for (FrozenA0051A0100TreeModel.Node source : FrozenA0051A0100TreeModel.all()) {
            for (String neighbor : source.neighbors()) {
                if (FrozenCombatPerkNodeBinding.catalogCode(neighbor).isEmpty()) continue;
                String from = source.nodeId().compareTo(neighbor) <= 0 ? source.nodeId() : neighbor;
                String to = source.nodeId().compareTo(neighbor) <= 0 ? neighbor : source.nodeId();
                if (seenEdges.add(from + "\u0000" + to)) edges.add(new Edge(from, to));
            }
        }
        return new ClientTreeLayout(
            "rpgskilltree:frozen_a0051_a0100",
            "tree.rpgskilltree.frozen_a0051_a0100",
            nodes,
            edges
        );
    }

    private static double[] combatPosition(CombatPerkDefinition.WeaponFamily family, int localIndex) {
        double baseX = (family.ordinal() - 4) * 260.0D;
        double[][] standard = {
            {0.0D, 0.0D},
            {-55.0D, 120.0D},
            {55.0D, 120.0D},
            {55.0D, 240.0D},
            {-20.0D, 360.0D},
            {0.0D, 500.0D}
        };
        double[][] shortBranch = {
            {0.0D, 0.0D},
            {0.0D, 140.0D}
        };
        double[][] positions = family == CombatPerkDefinition.WeaponFamily.CROSSBOW ? shortBranch : standard;
        if (localIndex < 0 || localIndex >= positions.length) {
            throw new IllegalStateException("unexpected node count for combat family " + family);
        }
        return new double[] {baseX + positions[localIndex][0], positions[localIndex][1]};
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
