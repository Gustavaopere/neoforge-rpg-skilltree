package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;

/** Fresh structural contract for the closed Notion batch A0081-A0100. */
public final class A0081A0100NotionContractTest {
    private static final String ARCANE_GATEWAY_NODE = "rpgskilltree:arcane_000";
    private static final String OCCULT_GATEWAY_NODE = "rpgskilltree:occult_000";
    private record Expected(String name, int maxRank, int cost, Map<String,Integer> deps) {}

    public static void main(String[] args) {
        Map<String, Expected> expected = new LinkedHashMap<>();
        add(expected,"A0081","Recuperação de Combate",3,1,Map.of("A0075",1));
        add(expected,"A0082","Vampirismo de Arma",3,1,Map.of("A0061",2));
        add(expected,"A0083","Vampirismo Mágico",3,1,Map.of());
        add(expected,"A0084","Sifão Elemental",3,1,Map.of());
        add(expected,"A0085","Sifão de Dano Periódico",3,1,Map.of());
        add(expected,"A0086","Vampirismo Universal",1,3,Map.of("A0082",3,"A0083",3,"A0085",2));
        add(expected,"A0087","Sede de Sangue",1,2,Map.of("A0075",1,"A0081",3,"A0082",2));
        add(expected,"A0088","Constituição",5,1,Map.of());
        add(expected,"A0089","Couro Endurecido",5,1,Map.of());
        add(expected,"A0090","Têmpera",5,1,Map.of("A0089",2));
        add(expected,"A0091","Base Firme",5,1,Map.of());
        add(expected,"A0092","Resistência Física",4,1,Map.of("A0089",2));
        add(expected,"A0093","Guarda Econômica",5,1,Map.of());
        add(expected,"A0094","Recuperação de Guarda",4,1,Map.of("A0093",2));
        add(expected,"A0095","Tenacidade",5,1,Map.of("A0091",2,"A0094",1));
        add(expected,"A0096","Último Fôlego",3,1,Map.of("A0092",2));
        add(expected,"A0097","Primeira Defesa",3,1,Map.of("A0088",1));
        add(expected,"A0098","Defesa em Movimento",3,1,Map.of("A0088",2));
        add(expected,"A0099","Defesa Estacionária",3,1,Map.of("A0089",2));
        add(expected,"A0100","Anti-Crítico",4,1,Map.of("A0090",2));

        check(NotionCombatPerkCatalog.all().size() == 100, "catalog must contain exactly A0001-A0100");
        expected.forEach((code,row) -> {
            CombatPerkDefinition actual = NotionCombatPerkCatalog.definition(code)
                .orElseThrow(() -> new AssertionError("missing " + code));
            check(actual.name().equals(row.name), code + " name");
            check(actual.maxRank() == row.maxRank, code + " max rank");
            check(actual.rankCost() == row.cost, code + " cost");
            check(actual.dependencies().equals(row.deps), code + " dependencies");
            CombatPerkTreeModel.node(code).orElseThrow(() -> new AssertionError("missing tree node " + code));
        });
        check(NotionCombatPerkCatalog.definition("A0101").isEmpty(), "A0101 must remain outside this batch");

        checkRankGate("A0081", CombatPerkTreeModel.MARTIAL_GATEWAY_NODE, 1);
        checkRankGate("A0082", CombatPerkTreeModel.MARTIAL_GATEWAY_NODE, 1);
        checkRankGate("A0083", ARCANE_GATEWAY_NODE, 1);
        checkRankGate("A0084", ARCANE_GATEWAY_NODE, 1);
        checkRankGate("A0085", OCCULT_GATEWAY_NODE, 1);
        for (int i = 88; i <= 100; i++) checkRankGate("A%04d".formatted(i), CombatPerkTreeModel.VITALITY_GATEWAY_NODE, 1);
        checkRankGate("A0098", CombatPerkTreeModel.AGILITY_GATEWAY_NODE, 1);
        checkRankGate("A0099", CombatPerkTreeModel.MARTIAL_GATEWAY_NODE, 1);
        A0081A0100CombatPolicyTest.main(new String[0]);
        System.out.println("A0081A0100NotionContractTest: OK");
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
