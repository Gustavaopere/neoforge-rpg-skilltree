package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class A0041A0060NotionContractTest {
    private A0041A0060NotionContractTest() {}

    public static void main(String[] args) {
        closedRangeIsPresent();
        ranksCostsDependenciesMatchFreshNotion();
        familiesAndGatesMatchFreshNotion();
        System.out.println("A0041A0060NotionContractTest: PASS");
    }

    private static void closedRangeIsPresent() {
        for (int i = 41; i <= 60; i++) {
            require(NotionCombatPerkCatalog.definition("A%04d".formatted(i)).isPresent(), "missing A%04d".formatted(i));
        }
    }

    private static void ranksCostsDependenciesMatchFreshNotion() {
        expect("A0041",2,1,Map.of("A0038",2,"A0039",1));
        expect("A0042",1,2,Map.of("A0040",1,"A0041",1));
        expect("A0043",3,1,Map.of());
        expect("A0044",3,1,Map.of("A0043",2));
        expect("A0045",3,1,Map.of("A0043",1));
        expect("A0046",2,1,Map.of("A0045",2));
        expect("A0047",2,1,Map.of("A0044",2,"A0045",1));
        expect("A0048",1,2,Map.of("A0046",1,"A0047",1));
        expect("A0049",3,1,Map.of());
        expect("A0050",3,1,Map.of("A0049",2));
        expect("A0051",3,1,Map.of("A0049",1));
        expect("A0052",2,1,Map.of("A0050",2,"A0051",2));
        expect("A0053",2,1,Map.of("A0052",1));
        expect("A0054",1,2,Map.of("A0052",2,"A0053",1));
        expect("A0055",3,1,Map.of());
        expect("A0056",3,1,Map.of("A0055",2));
        expect("A0057",3,1,Map.of("A0055",1));
        expect("A0058",2,1,Map.of("A0057",2));
        expect("A0059",2,1,Map.of("A0058",1,"A0056",2));
        expect("A0060",1,2,Map.of("A0058",2,"A0059",1));
    }

    private static void familiesAndGatesMatchFreshNotion() {
        family("A0041","SCYTHE"); family("A0042","SCYTHE");
        family("A0043","BOW"); family("A0048","BOW");
        family("A0049","CROSSBOW"); family("A0054","CROSSBOW");
        family("A0055","FIST"); family("A0060","FIST");
        gate("A0042","combat_scythe","combat:scythe",80,true);
        gate("A0043","epic_bow","epicfight:bow",60,false);
        gate("A0048","epic_bow","epicfight:bow",80,true);
        gate("A0049","epic_crossbow","epicfight:crossbow",60,false);
        gate("A0054","epic_crossbow","epicfight:crossbow",80,true);
        gate("A0055","combat_fist","combat:fist",60,false);
        gate("A0060","combat_fist","combat:fist",80,true);
    }

    private static void expect(String code, int maxRank, int cost, Map<String,Integer> dependencies) {
        CombatPerkDefinition d = NotionCombatPerkCatalog.definition(code).orElseThrow();
        require(d.maxRank()==maxRank, code+" maxRank");
        require(d.rankCost()==cost, code+" rankCost");
        require(d.dependencies().equals(dependencies), code+" dependencies "+d.dependencies());
    }

    private static void family(String code, String expected) {
        require(NotionCombatPerkCatalog.definition(code).orElseThrow().weaponFamily().name().equals(expected), code+" family");
    }

    private static void gate(String code, String gateway, String masteryKey, int mastery, boolean terminal) {
        CombatPerkTreeModel.Node n = CombatPerkTreeModel.node(code).orElseThrow();
        require(n.gatewayId().equals(gateway), code+" gateway identity");
        require(n.requiredNodeRanks().getOrDefault(CombatPerkTreeModel.MARTIAL_GATEWAY_NODE,0)==1, code+" main-tree gateway");
        require(n.requiredMastery().getOrDefault(masteryKey,0) >= mastery, code+" mastery");
        require(n.terminal()==terminal, code+" terminal");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
