package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class A0021A0040NotionContractTest {
    private A0021A0040NotionContractTest() {}

    public static void main(String[] args) {
        closedRangeIsExact();
        ranksCostsDependenciesMatchFreshNotion();
        familiesAndGatesMatchFreshNotion();
        System.out.println("A0021A0040NotionContractTest: PASS");
    }

    private static void closedRangeIsExact() {
        require(NotionCombatPerkCatalog.all().size() == 40, "catalog must contain exactly A0001-A0040 after this batch");
        for (int i = 21; i <= 40; i++) {
            require(NotionCombatPerkCatalog.definition("A%04d".formatted(i)).isPresent(), "missing A%04d".formatted(i));
        }
        require(NotionCombatPerkCatalog.definition("A0041").isEmpty(), "A0041 must remain outside this batch");
    }

    private static void ranksCostsDependenciesMatchFreshNotion() {
        expect("A0021",3,1,Map.of("A0019",1));
        expect("A0022",2,1,Map.of("A0021",2));
        expect("A0023",2,1,Map.of("A0020",2,"A0021",1));
        expect("A0024",1,2,Map.of("A0022",1,"A0023",1));
        expect("A0025",3,1,Map.of());
        expect("A0026",3,1,Map.of("A0025",2));
        expect("A0027",3,1,Map.of("A0025",1));
        expect("A0028",2,1,Map.of("A0027",2));
        expect("A0029",2,1,Map.of("A0026",2,"A0027",1));
        expect("A0030",1,2,Map.of("A0028",1,"A0029",1));
        expect("A0031",3,1,Map.of());
        expect("A0032",3,1,Map.of("A0031",2));
        expect("A0033",3,1,Map.of("A0031",1));
        expect("A0034",2,1,Map.of("A0033",2));
        expect("A0035",2,1,Map.of("A0032",2,"A0033",1));
        expect("A0036",1,2,Map.of("A0034",1,"A0035",1));
        expect("A0037",3,1,Map.of());
        expect("A0038",3,1,Map.of("A0037",2));
        expect("A0039",3,1,Map.of("A0037",1));
        expect("A0040",2,1,Map.of("A0039",2));
    }

    private static void familiesAndGatesMatchFreshNotion() {
        family("A0021","DAGGER"); family("A0024","DAGGER");
        family("A0025","HAMMER"); family("A0030","HAMMER");
        family("A0031","MACE"); family("A0036","MACE");
        family("A0037","SCYTHE"); family("A0040","SCYTHE");

        gate("A0024","epic_dagger","epicfight:dagger",80,true);
        gate("A0025","epic_hammer","epicfight:hammer",70,false);
        gate("A0030","epic_hammer","epicfight:hammer",80,true);
        gate("A0031","combat_mace","combat:mace",60,false);
        gate("A0036","combat_mace","combat:mace",80,true);
        gate("A0037","combat_scythe","combat:scythe",60,false);
        require(!CombatPerkTreeModel.node("A0040").orElseThrow().terminal(), "A0040 is explicitly not a terminal");
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

    private static void gate(String code, String specialization, String masteryKey, int mastery, boolean terminal) {
        CombatPerkTreeModel.Node n = CombatPerkTreeModel.node(code).orElseThrow();
        require(n.requiredSpecializations().contains(specialization), code+" specialization");
        require(n.requiredMastery().getOrDefault(masteryKey,0) >= mastery, code+" mastery");
        require(n.terminal()==terminal, code+" terminal");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
