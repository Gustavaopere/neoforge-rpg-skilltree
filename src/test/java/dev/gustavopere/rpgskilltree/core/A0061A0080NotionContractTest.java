package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;

/** Fresh structural contract for the closed Notion batch A0061-A0080. */
public final class A0061A0080NotionContractTest {
    private record Expected(String name, int maxRank, int cost, Map<String,Integer> deps) {}

    public static void main(String[] args) {
        Map<String, Expected> expected = new LinkedHashMap<>();
        add(expected,"A0061","Força Aplicada",5,1,Map.of());
        add(expected,"A0062","Golpe Preciso",4,1,Map.of());
        add(expected,"A0063","Impacto Crítico",3,1,Map.of("A0062",2));
        add(expected,"A0064","Ritmo de Combate",4,1,Map.of());
        add(expected,"A0065","Penetração Física",4,1,Map.of("A0061",2));
        add(expected,"A0066","Impacto Marcial",4,1,Map.of("A0061",1));
        add(expected,"A0067","Firmeza Ofensiva",4,1,Map.of("A0066",1));
        add(expected,"A0068","Dano contra Feridos",3,1,Map.of("A0061",1));
        add(expected,"A0069","Dano contra Íntegros",3,1,Map.of("A0061",1));
        add(expected,"A0070","Dano contra Chefes",5,1,Map.of("A0061",1));
        add(expected,"A0071","Dano contra Elites",5,1,Map.of("A0061",1));
        add(expected,"A0072","Retaliação",3,1,Map.of("A0067",1));
        add(expected,"A0073","Janela de Execução",1,2,Map.of("A0068",2));
        add(expected,"A0074","Primeiro Sangue",1,2,Map.of("A0069",2));
        add(expected,"A0075","Ritmo Sustentado",1,2,Map.of("A0061",3,"A0064",2));
        add(expected,"A0076","Postura Agressiva",1,1,Map.of("A0061",3,"A0064",1));
        add(expected,"A0077","Postura Cautelosa",1,1,Map.of("A0067",2));
        add(expected,"A0078","Ataque em Movimento",3,1,Map.of("A0064",2));
        add(expected,"A0079","Ataque Estacionário",3,1,Map.of("A0061",2));
        add(expected,"A0080","Golpe de Oportunidade",1,2,Map.of("A0078",2));

        check(NotionCombatPerkCatalog.all().size() >= 80, "catalog must preserve A0001-A0080");
        expected.forEach((code,row) -> {
            CombatPerkDefinition actual = NotionCombatPerkCatalog.definition(code)
                .orElseThrow(() -> new AssertionError("missing " + code));
            check(actual.name().equals(row.name), code + " name");
            check(actual.maxRank() == row.maxRank, code + " max rank");
            check(actual.rankCost() == row.cost, code + " cost");
            check(actual.dependencies().equals(row.deps), code + " dependencies");
            CombatPerkTreeModel.Node node = CombatPerkTreeModel.node(code)
                .orElseThrow(() -> new AssertionError("missing tree node " + code));
            check(node.requiredNodeRanks().getOrDefault(CombatPerkTreeModel.MARTIAL_GATEWAY_NODE,0) >= 1,
                code + " MARTIAL gateway");
        });

        checkRankGate("A0078","rpgskilltree:agility_000",1);
        checkRankGate("A0079","rpgskilltree:vitality_000",1);
        checkRankGate("A0080","rpgskilltree:agility_002",1);
        System.out.println("A0061A0080NotionContractTest: OK");
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
