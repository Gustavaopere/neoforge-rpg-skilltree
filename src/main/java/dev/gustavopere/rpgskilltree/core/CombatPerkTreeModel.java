package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Pure physical-tree projection of the audited A0001-A0050 semantic catalog.
 *
 * <p>The projection is shared by the server rule catalog and the client layout so rank gates,
 * specialization gates and physical ids cannot drift between the two sides.
 */
public final class CombatPerkTreeModel {
    public record Node(
        String code,
        String nodeId,
        String name,
        WeaponFamily weaponFamily,
        String kind,
        int maxRank,
        int costPerRank,
        boolean startingPoint,
        int minCharacterLevel,
        Set<String> requiredSpecializations,
        Map<String, Integer> requiredMastery,
        Map<String, Integer> requiredNodeRanks,
        Set<String> neighbors,
        Set<String> domains
    ) {
        public Node {
            requiredSpecializations = Set.copyOf(requiredSpecializations);
            requiredMastery = Map.copyOf(requiredMastery);
            requiredNodeRanks = Map.copyOf(requiredNodeRanks);
            neighbors = Set.copyOf(neighbors);
            domains = Set.copyOf(domains);
        }
    }

    private record FamilyGate(
        String specializationId,
        int minimumLevel,
        String masteryLane,
        int rootMastery,
        int capstoneMastery,
        Set<String> domains
    ) {
        private FamilyGate {
            domains = Set.copyOf(domains);
        }
    }

    private static final Map<WeaponFamily, FamilyGate> FAMILY_GATES = familyGates();
    private static final List<Node> ALL = build();
    private static final Map<String, Node> BY_CODE = index();

    private CombatPerkTreeModel() {}

    public static List<Node> all() {
        return ALL;
    }

    public static Optional<Node> node(String code) {
        if (code == null) return Optional.empty();
        return Optional.ofNullable(BY_CODE.get(code));
    }

    public static Set<String> specializationIds() {
        Set<String> ids = new HashSet<>();
        FAMILY_GATES.values().forEach(gate -> ids.add(gate.specializationId()));
        return Set.copyOf(ids);
    }

    private static List<Node> build() {
        Map<String, Set<String>> neighborCodes = new HashMap<>();
        for (CombatPerkDefinition definition : NotionCombatPerkCatalog.all()) {
            neighborCodes.put(definition.code(), new HashSet<>());
        }
        for (CombatPerkDefinition definition : NotionCombatPerkCatalog.all()) {
            for (String dependency : definition.dependencies().keySet()) {
                neighborCodes.get(definition.code()).add(dependency);
                neighborCodes.get(dependency).add(definition.code());
            }
        }

        List<Node> nodes = new ArrayList<>();
        for (CombatPerkDefinition definition : NotionCombatPerkCatalog.all()) {
            FamilyGate gate = FAMILY_GATES.get(definition.weaponFamily());
            if (gate == null) {
                throw new IllegalStateException("missing physical tree gate for " + definition.weaponFamily());
            }
            boolean root = definition.dependencies().isEmpty();
            boolean capstone = definition.rankCost() == 2;

            Map<String, Integer> mastery = Map.of();
            if (root) mastery = Map.of(gate.masteryLane(), gate.rootMastery());
            else if (capstone) mastery = Map.of(gate.masteryLane(), gate.capstoneMastery());

            Map<String, Integer> rankedDependencies = new LinkedHashMap<>();
            definition.dependencies().forEach((dependency, requiredRank) ->
                rankedDependencies.put(CombatPerkNodeBinding.nodeId(dependency), requiredRank)
            );

            Set<String> neighbors = new HashSet<>();
            neighborCodes.get(definition.code()).forEach(code ->
                neighbors.add(CombatPerkNodeBinding.nodeId(code))
            );

            nodes.add(new Node(
                definition.code(),
                CombatPerkNodeBinding.nodeId(definition.code()),
                definition.name(),
                definition.weaponFamily(),
                capstone ? "keystone" : definition.maxRank() < 3 ? "notable" : "lesser",
                definition.maxRank(),
                definition.rankCost(),
                root,
                gate.minimumLevel(),
                Set.of(gate.specializationId()),
                mastery,
                rankedDependencies,
                neighbors,
                gate.domains()
            ));
        }
        return List.copyOf(nodes);
    }

    private static Map<String, Node> index() {
        Map<String, Node> index = new LinkedHashMap<>();
        for (Node node : ALL) {
            if (index.put(node.code(), node) != null) {
                throw new IllegalStateException("duplicate physical combat node code: " + node.code());
            }
        }
        if (index.size() != 50) {
            throw new IllegalStateException("physical combat tree must contain exactly A0001-A0050");
        }
        return Map.copyOf(index);
    }

    private static Map<WeaponFamily, FamilyGate> familyGates() {
        EnumMap<WeaponFamily, FamilyGate> gates = new EnumMap<>(WeaponFamily.class);
        gates.put(WeaponFamily.SWORD, new FamilyGate("epic_sword", 8, "epicfight:sword", 60, 80, Set.of("MARTIAL")));
        gates.put(WeaponFamily.AXE, new FamilyGate("epic_axe", 8, "epicfight:axe", 60, 80, Set.of("MARTIAL")));
        gates.put(WeaponFamily.SPEAR, new FamilyGate("epic_spear", 8, "epicfight:spear", 60, 80, Set.of("MARTIAL", "AGILITY")));
        gates.put(WeaponFamily.DAGGER, new FamilyGate("epic_dagger", 8, "epicfight:dagger", 60, 80, Set.of("MARTIAL", "AGILITY")));
        gates.put(WeaponFamily.HAMMER, new FamilyGate("epic_hammer", 10, "epicfight:heavy", 70, 80, Set.of("MARTIAL", "VITALITY")));
        gates.put(WeaponFamily.MACE, new FamilyGate("combat_mace", 8, "combat:mace", 60, 80, Set.of("MARTIAL")));
        gates.put(WeaponFamily.SCYTHE, new FamilyGate("combat_scythe", 8, "combat:scythe", 60, 80, Set.of("MARTIAL")));
        gates.put(WeaponFamily.BOW, new FamilyGate("epic_bow", 8, "combat:bow", 60, 80, Set.of("AGILITY")));
        gates.put(WeaponFamily.CROSSBOW, new FamilyGate("epic_crossbow", 8, "combat:crossbow", 60, 80, Set.of("AGILITY")));
        if (gates.size() != WeaponFamily.values().length) {
            throw new IllegalStateException("every combat weapon family must have a physical tree gate");
        }
        return Map.copyOf(gates);
    }
}
