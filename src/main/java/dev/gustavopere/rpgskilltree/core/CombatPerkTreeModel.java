package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Acquisition topology/gates for the currently closed A0001-A0040 range. */
public final class CombatPerkTreeModel {
    private static final Map<String, Node> NODES = build();
    private CombatPerkTreeModel() {}

    public record Node(
        String code,
        String nodeId,
        int maxRank,
        int costPerRank,
        boolean startingPoint,
        int minCharacterLevel,
        Map<String, Integer> requiredMastery,
        Set<String> requiredSpecializations,
        Map<String, Integer> requiredNodeRanks,
        Set<String> neighbors,
        boolean terminal
    ) {}

    public static Optional<Node> node(String code) { return Optional.ofNullable(NODES.get(code)); }
    public static List<Node> all() { return List.copyOf(NODES.values()); }

    private static Map<String, Node> build() {
        LinkedHashMap<String, Node> map = new LinkedHashMap<>();
        family(map, 1, 6, "epic_sword", 8, "epicfight:sword", 60, 6,
            new String[][]{{"A0001","A0002"},{"A0001","A0003"},{"A0002","A0005"},{"A0003","A0004"},{"A0004","A0005"},{"A0004","A0006"},{"A0005","A0006"}});
        family(map, 7, 12, "epic_axe", 8, "epicfight:axe", 60, 12,
            new String[][]{{"A0007","A0008"},{"A0007","A0009"},{"A0008","A0011"},{"A0009","A0010"},{"A0009","A0011"},{"A0010","A0012"},{"A0011","A0012"}});
        family(map, 13, 18, "epic_spear", 8, "epicfight:spear", 60, 18,
            new String[][]{{"A0013","A0014"},{"A0013","A0015"},{"A0014","A0017"},{"A0015","A0016"},{"A0015","A0017"},{"A0016","A0018"},{"A0017","A0018"}});
        family(map, 19, 24, "epic_dagger", 8, "epicfight:dagger", 60, 24,
            new String[][]{{"A0019","A0020"},{"A0019","A0021"},{"A0020","A0023"},{"A0021","A0022"},{"A0021","A0023"},{"A0022","A0024"},{"A0023","A0024"}});
        family(map, 25, 30, "epic_hammer", 10, "epicfight:heavy", 70, 30,
            new String[][]{{"A0025","A0026"},{"A0025","A0027"},{"A0026","A0029"},{"A0027","A0028"},{"A0027","A0029"},{"A0028","A0030"},{"A0029","A0030"}});
        family(map, 31, 36, "combat_mace", 8, "epicfight:mace", 60, 36,
            new String[][]{{"A0031","A0032"},{"A0031","A0033"},{"A0032","A0035"},{"A0033","A0034"},{"A0033","A0035"},{"A0034","A0036"},{"A0035","A0036"}});
        family(map, 37, 40, "combat_scythe", 8, "epicfight:scythe", 60, -1,
            new String[][]{{"A0037","A0038"},{"A0037","A0039"},{"A0039","A0040"}});
        return Map.copyOf(map);
    }

    private static void family(Map<String, Node> target, int first, int last, String specialization,
                               int rootLevel, String masteryKey, int rootMastery, int terminalNumber,
                               String[][] edges) {
        Map<String, java.util.LinkedHashSet<String>> adjacency = new LinkedHashMap<>();
        for (int i = first; i <= last; i++) adjacency.put(code(i), new java.util.LinkedHashSet<>());
        for (String[] edge : edges) {
            adjacency.get(edge[0]).add(edge[1]);
            adjacency.get(edge[1]).add(edge[0]);
        }
        for (int i = first; i <= last; i++) {
            String code = code(i);
            CombatPerkDefinition definition = NotionCombatPerkCatalog.definition(code).orElseThrow();
            boolean root = i == first;
            boolean terminal = i == terminalNumber;
            int mastery = terminal ? 80 : root ? rootMastery : 0;
            target.put(code, new Node(
                code,
                CombatPerkNodeBinding.nodeIdUnchecked(code),
                definition.maxRank(),
                definition.rankCost(),
                root,
                root ? rootLevel : 1,
                mastery == 0 ? Map.of() : Map.of(masteryKey, mastery),
                Set.of(specialization),
                dependencyNodeRanks(definition.dependencies()),
                adjacency.get(code).stream().map(CombatPerkNodeBinding::nodeIdUnchecked).collect(java.util.stream.Collectors.toUnmodifiableSet()),
                terminal
            ));
        }
    }

    private static Map<String, Integer> dependencyNodeRanks(Map<String, Integer> dependencies) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        dependencies.forEach((code, rank) -> result.put(CombatPerkNodeBinding.nodeIdUnchecked(code), rank));
        return Map.copyOf(result);
    }

    private static String code(int number) { return "A%04d".formatted(number); }
}
