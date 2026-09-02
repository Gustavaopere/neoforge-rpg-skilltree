package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;

/** Focused structural contract for the Chat 1 approved A0101-A0110 batch. */
public final class A0101A0110NotionContractTest {
    private record Expected(String name, int maxRank, int cost, Map<String,Integer> deps) {}

    public static void main(String[] args) {
        Map<String,Expected> expected = new LinkedHashMap<>();
        add(expected,"A0101","Fortificação contra Projéteis",4,1,Map.of("A0089",1));
        add(expected,"A0102","Proteção Arcana",4,1,Map.of("A0088",2));
        add(expected,"A0103","Proteção Ambiental",4,1,Map.of("A0088",2));
        add(expected,"A0104","Segundo Vento",1,2,Map.of("A0096",3));
        add(expected,"A0105","Casca Reativa",1,2,Map.of("A0089",3,"A0090",2));
        add(expected,"A0106","Guarda de Emergência",1,3,Map.of("A0104",1,"A0105",1,"A0095",3));
        add(expected,"A0107","Conversão de Impacto",1,2,Map.of("A0093",3,"A0095",3));
        add(expected,"A0108","Pele de Pedra",1,2,Map.of("A0092",3,"A0100",2,"A0090",2));
        add(expected,"A0109","Fortaleza Ambulante",1,3,Map.of("A0108",1,"A0091",3));
        add(expected,"A0110","Conservação de Equipamento I",5,1,Map.of());

        check(NotionCombatPerkCatalog.all().size() == 110, "catalog must contain exactly A0001-A0110");
        expected.forEach((code,row) -> {
            CombatPerkDefinition actual = NotionCombatPerkCatalog.definition(code)
                .orElseThrow(() -> new AssertionError("missing " + code));
            check(actual.name().equals(row.name), code + " name");
            check(actual.maxRank() == row.maxRank, code + " max rank");
            check(actual.rankCost() == row.cost, code + " cost");
            check(actual.dependencies().equals(row.deps), code + " dependencies");
            CombatPerkTreeModel.node(code).orElseThrow(() -> new AssertionError("missing tree node " + code));
        });
        check(NotionCombatPerkCatalog.definition("A0111").isEmpty(), "A0111 must remain outside this batch");

        checkRankGate("A0101", CombatPerkTreeModel.VITALITY_GATEWAY_NODE, 1);
        checkRankGate("A0102", CombatPerkTreeModel.VITALITY_GATEWAY_NODE, 1);
        checkRankGate("A0102", CombatPerkTreeModel.ARCANE_GATEWAY_NODE, 1);
        checkRankGate("A0103", CombatPerkTreeModel.VITALITY_GATEWAY_NODE, 1);
        checkRankGate("A0103", CombatPerkTreeModel.SURVIVAL_GATEWAY_NODE, 1);
        for (String code : ListHolder.VITALITY_ONLY) checkRankGate(code, CombatPerkTreeModel.VITALITY_GATEWAY_NODE, 1);
        checkRankGate("A0107", CombatPerkTreeModel.MARTIAL_GATEWAY_NODE, 1);
        checkRankGate("A0110", CombatPerkTreeModel.SURVIVAL_GATEWAY_NODE, 1);
        DamageMitigationResolverTest.main(new String[0]);
        System.out.println("A0101A0110NotionContractTest: OK");
    }

    private static final class ListHolder {
        private static final java.util.List<String> VITALITY_ONLY = java.util.List.of("A0104","A0105","A0106","A0107","A0108","A0109");
    }

    private static void checkRankGate(String code,String nodeId,int rank) {
        CombatPerkTreeModel.Node node = CombatPerkTreeModel.node(code).orElseThrow();
        check(node.requiredNodeRanks().getOrDefault(nodeId,0) >= rank, code + " requires " + nodeId);
    }

    private static void add(Map<String,Expected> map,String code,String name,int rank,int cost,Map<String,Integer> deps) {
        map.put(code,new Expected(name,rank,cost,deps));
    }

    private static void check(boolean condition,String message) {
        if(!condition) throw new AssertionError(message);
    }
}
