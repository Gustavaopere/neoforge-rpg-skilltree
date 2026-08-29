package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Acquisition topology/gates for the closed A0001-A0020 batch. */
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
        family(map, "sword", "epic_sword", 1,
            new String[][]{{"A0001","A0002"},{"A0001","A0003"},{"A0002","A0005"},{"A0003","A0004"},{"A0004","A0005"},{"A0004","A0006"},{"A0005","A0006"}});
        family(map, "axe", "epic_axe", 7,
            new String[][]{{"A0007","A0008"},{"A0007","A0009"},{"A0008","A0011"},{"A0009","A0010"},{"A0009","A0011"},{"A0010","A0012"},{"A0011","A0012"}});
        family(map, "spear", "epic_spear", 13,
            new String[][]{{"A0013","A0014"},{"A0013","A0015"},{"A0014","A0017"},{"A0015","A0016"},{"A0015","A0017"},{"A0016","A0018"},{"A0017","A0018"}});
        family(map, "dagger", "epic_dagger", 19,
            new String[][]{{"A0019","A0020"}});
        return Map.copyOf(map);
    }

    private static void family(Map<String, Node> target, String masterySuffix, String specialization, int first,
                               String[][] edges) {
        int last = first == 19 ? 20 : first + 5;
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
            boolean terminal = first != 19 && i == last;
            int mastery = root ? 60 : terminal ? 80 : 0;
            target.put(code, new Node(
                code,
                CombatPerkNodeBinding.nodeIdUnchecked(code),
                definition.maxRank(),
                definition.rankCost(),
                root,
                root ? 8 : 1,
                mastery == 0 ? Map.of() : Map.of("epicfight:" + masterySuffix, mastery),
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
