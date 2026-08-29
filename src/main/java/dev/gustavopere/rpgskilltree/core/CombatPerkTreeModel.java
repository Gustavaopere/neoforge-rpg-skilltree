package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Acquisition topology/gates for the currently closed A0001-A0100 range. */
public final class CombatPerkTreeModel {
    public static final String MARTIAL_GATEWAY_NODE = "rpgskilltree:martial_000";
    public static final String ARCANE_GATEWAY_NODE = "rpgskilltree:arcane_000";
    public static final String OCCULT_GATEWAY_NODE = "rpgskilltree:occult_000";
    public static final String AGILITY_GATEWAY_NODE = "rpgskilltree:agility_000";
    public static final String AGILITY_DODGE_NODE = "rpgskilltree:agility_002";
    public static final String VITALITY_GATEWAY_NODE = "rpgskilltree:vitality_000";
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
        String gatewayId,
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
        family(map, 31, 36, "combat_mace", 8, "combat:mace", 60, 36,
            new String[][]{{"A0031","A0032"},{"A0031","A0033"},{"A0032","A0035"},{"A0033","A0034"},{"A0033","A0035"},{"A0034","A0036"},{"A0035","A0036"}});
        family(map, 37, 42, "combat_scythe", 8, "combat:scythe", 60, 42,
            new String[][]{{"A0037","A0038"},{"A0037","A0039"},{"A0039","A0040"},{"A0038","A0041"},{"A0039","A0041"},{"A0040","A0042"},{"A0041","A0042"}});
        family(map, 43, 48, "epic_bow", 8, "epicfight:bow", 60, 48,
            new String[][]{{"A0043","A0044"},{"A0043","A0045"},{"A0045","A0046"},{"A0044","A0047"},{"A0045","A0047"},{"A0046","A0048"},{"A0047","A0048"}});
        family(map, 49, 54, "epic_crossbow", 8, "epicfight:crossbow", 60, 54,
            new String[][]{{"A0049","A0050"},{"A0049","A0051"},{"A0050","A0052"},{"A0051","A0052"},{"A0052","A0053"},{"A0052","A0054"},{"A0053","A0054"}});
        family(map, 55, 60, "combat_fist", 8, "combat:fist", 60, 60,
            new String[][]{{"A0055","A0056"},{"A0055","A0057"},{"A0057","A0058"},{"A0056","A0059"},{"A0058","A0059"},{"A0058","A0060"},{"A0059","A0060"}});
        martialFoundations(map);
        sustainAndVitality(map);
        return Map.copyOf(map);
    }

    private static void family(Map<String, Node> target, int first, int last, String gatewayId,
                               int rootLevel, String masteryKey, int rootMastery, int terminalNumber,
                               String[][] edges) {
        Map<String, LinkedHashSet<String>> adjacency = new LinkedHashMap<>();
        for (int i = first; i <= last; i++) adjacency.put(code(i), new LinkedHashSet<>());
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
                code, CombatPerkNodeBinding.nodeIdUnchecked(code), definition.maxRank(), definition.rankCost(),
                root, root ? rootLevel : 1,
                mastery == 0 ? Map.of() : Map.of(masteryKey, mastery), gatewayId,
                dependencyNodeRanks(definition.dependencies()),
                adjacency.get(code).stream().map(CombatPerkNodeBinding::nodeIdUnchecked).collect(java.util.stream.Collectors.toUnmodifiableSet()),
                terminal
            ));
        }
    }

    private static void martialFoundations(Map<String, Node> target) {
        Map<String, LinkedHashSet<String>> adjacency = new LinkedHashMap<>();
        for (int i = 61; i <= 80; i++) adjacency.put(code(i), new LinkedHashSet<>());
        for (int i = 61; i <= 80; i++) {
            String child = code(i);
            NotionCombatPerkCatalog.definition(child).orElseThrow().dependencies().keySet().forEach(parent -> {
                if (adjacency.containsKey(parent)) {
                    adjacency.get(parent).add(child);
                    adjacency.get(child).add(parent);
                }
            });
        }
        for (int i = 61; i <= 80; i++) {
            String perk = code(i);
            CombatPerkDefinition definition = NotionCombatPerkCatalog.definition(perk).orElseThrow();
            LinkedHashMap<String,Integer> gates = new LinkedHashMap<>(dependencyNodeRanks(definition.dependencies()));
            if (perk.equals("A0078")) gates.put(AGILITY_GATEWAY_NODE, 1);
            if (perk.equals("A0079")) gates.put(VITALITY_GATEWAY_NODE, 1);
            if (perk.equals("A0080")) gates.put(AGILITY_DODGE_NODE, 1);
            String gatewayId = perk.equals("A0079") ? "martial_vitality_bridge"
                : (perk.equals("A0078") || perk.equals("A0080")) ? "martial_agility_bridge" : "martial_core";
            target.put(perk, new Node(
                perk, CombatPerkNodeBinding.nodeIdUnchecked(perk), definition.maxRank(), definition.rankCost(),
                definition.dependencies().isEmpty(), 1, Map.of(), gatewayId, Map.copyOf(gates),
                adjacency.get(perk).stream().map(CombatPerkNodeBinding::nodeIdUnchecked).collect(java.util.stream.Collectors.toUnmodifiableSet()),
                false
            ));
        }
    }

    private static void sustainAndVitality(Map<String, Node> target) {
        Map<String, LinkedHashSet<String>> adjacency = new LinkedHashMap<>();
        for (int i = 81; i <= 100; i++) adjacency.put(code(i), new LinkedHashSet<>());

        for (int i = 81; i <= 100; i++) {
            String child = code(i);
            for (String parent : NotionCombatPerkCatalog.definition(child).orElseThrow().dependencies().keySet()) {
                if (adjacency.containsKey(parent)) {
                    adjacency.get(parent).add(child);
                    adjacency.get(child).add(parent);
                } else if (target.containsKey(parent)) {
                    adjacency.get(child).add(parent);
                    addExistingNeighbor(target, parent, child);
                }
            }
        }

        for (int i = 81; i <= 100; i++) {
            String perk = code(i);
            CombatPerkDefinition definition = NotionCombatPerkCatalog.definition(perk).orElseThrow();
            LinkedHashMap<String,Integer> gates = dependencyRanksOnly(definition.dependencies());
            String gatewayId;

            if (perk.equals("A0081") || perk.equals("A0082") || perk.equals("A0087")) {
                gates.put(MARTIAL_GATEWAY_NODE, 1);
                gatewayId = "martial_sustain";
            } else if (perk.equals("A0083") || perk.equals("A0084")) {
                gates.put(ARCANE_GATEWAY_NODE, 1);
                gatewayId = perk.equals("A0084") ? "arcane_elemental_sustain" : "arcane_sustain";
            } else if (perk.equals("A0085")) {
                gates.put(OCCULT_GATEWAY_NODE, 1);
                gatewayId = "occult_dot_sustain";
            } else if (perk.equals("A0086")) {
                gatewayId = "hybrid_sustain_convergence";
            } else {
                gates.put(VITALITY_GATEWAY_NODE, 1);
                if (perk.equals("A0093") || perk.equals("A0099")) gates.put(MARTIAL_GATEWAY_NODE, 1);
                if (perk.equals("A0098")) gates.put(AGILITY_GATEWAY_NODE, 1);
                gatewayId = perk.equals("A0098") ? "vitality_agility_bridge"
                    : (perk.equals("A0093") || perk.equals("A0099")) ? "vitality_martial_bridge"
                    : "vitality_core";
            }

            boolean terminal = perk.equals("A0087");
            target.put(perk, new Node(
                perk, CombatPerkNodeBinding.nodeIdUnchecked(perk), definition.maxRank(), definition.rankCost(),
                definition.dependencies().isEmpty(), 1, Map.of(), gatewayId, Map.copyOf(gates),
                adjacency.get(perk).stream().map(CombatPerkNodeBinding::nodeIdUnchecked).collect(java.util.stream.Collectors.toUnmodifiableSet()),
                terminal
            ));
        }
    }

    private static void addExistingNeighbor(Map<String, Node> target, String existingCode, String newCode) {
        Node existing = target.get(existingCode);
        if (existing == null) return;
        LinkedHashSet<String> neighbors = new LinkedHashSet<>(existing.neighbors());
        neighbors.add(CombatPerkNodeBinding.nodeIdUnchecked(newCode));
        target.put(existingCode, new Node(
            existing.code(), existing.nodeId(), existing.maxRank(), existing.costPerRank(), existing.startingPoint(),
            existing.minCharacterLevel(), existing.requiredMastery(), existing.gatewayId(), existing.requiredNodeRanks(),
            Set.copyOf(neighbors), existing.terminal()
        ));
    }

    private static Map<String, Integer> dependencyNodeRanks(Map<String, Integer> dependencies) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        result.put(MARTIAL_GATEWAY_NODE, 1);
        dependencies.forEach((perk, rank) -> result.put(CombatPerkNodeBinding.nodeIdUnchecked(perk), rank));
        return Map.copyOf(result);
    }

    private static LinkedHashMap<String,Integer> dependencyRanksOnly(Map<String,Integer> dependencies) {
        LinkedHashMap<String,Integer> result = new LinkedHashMap<>();
        dependencies.forEach((perk, rank) -> result.put(CombatPerkNodeBinding.nodeIdUnchecked(perk), rank));
        return result;
    }

    private static String code(int number) { return "A%04d".formatted(number); }
}
