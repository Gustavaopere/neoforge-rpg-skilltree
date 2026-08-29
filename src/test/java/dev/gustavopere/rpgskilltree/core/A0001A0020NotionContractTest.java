package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

/** Regression contract for the already-merged A0001-A0020 implementation batch. */
public final class A0001A0020NotionContractTest {
    public static void main(String[] args) {
        originalBatchRemainsPresent(); ranksCostsAndDependenciesMatchNotion(); familyGatesMatchNotion();
        weaponTrainingCoefficientsMatchNotion(); transientResourcesRespectCapsAndLifecycle();
        System.out.println("A0001A0020NotionContractTest: PASS");
    }
    private static void originalBatchRemainsPresent(){for(int i=1;i<=20;i++){String code="A%04d".formatted(i);require(NotionCombatPerkCatalog.definition(code).isPresent(),"missing "+code);}}
    private static void ranksCostsAndDependenciesMatchNotion(){
        expect("A0001",3,1,Map.of());expect("A0002",3,1,Map.of("A0001",2));expect("A0003",3,1,Map.of("A0001",1));expect("A0004",1,1,Map.of("A0003",2));expect("A0005",1,1,Map.of("A0002",2,"A0004",1));expect("A0006",1,2,Map.of("A0004",1,"A0005",1));
        expect("A0007",3,1,Map.of());expect("A0008",3,1,Map.of("A0007",2));expect("A0009",3,1,Map.of("A0007",1));expect("A0010",2,1,Map.of("A0009",2));expect("A0011",2,1,Map.of("A0008",2,"A0009",1));expect("A0012",1,2,Map.of("A0010",1,"A0011",1));
        expect("A0013",3,1,Map.of());expect("A0014",3,1,Map.of("A0013",2));expect("A0015",3,1,Map.of("A0013",1));expect("A0016",2,1,Map.of("A0015",2));expect("A0017",2,1,Map.of("A0014",2,"A0015",1));expect("A0018",1,2,Map.of("A0016",1,"A0017",1));expect("A0019",3,1,Map.of());expect("A0020",3,1,Map.of("A0019",2));
    }
    private static void familyGatesMatchNotion(){expectRootGate("A0001","epic_sword","epicfight:sword",60);expectCapstoneGate("A0006","epic_sword","epicfight:sword",80);expectRootGate("A0007","epic_axe","epicfight:axe",60);expectCapstoneGate("A0012","epic_axe","epicfight:axe",80);expectRootGate("A0013","epic_spear","epicfight:spear",60);expectCapstoneGate("A0018","epic_spear","epicfight:spear",80);expectRootGate("A0019","epic_dagger","epicfight:dagger",60);}
    private static void weaponTrainingCoefficientsMatchNotion(){
        var ranks=CombatPerkRanks.of(Map.ofEntries(Map.entry("A0001",3),Map.entry("A0002",3),Map.entry("A0003",3),Map.entry("A0007",3),Map.entry("A0008",3),Map.entry("A0009",3),Map.entry("A0013",3),Map.entry("A0014",3),Map.entry("A0015",3),Map.entry("A0019",3),Map.entry("A0020",3)));
        require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.SWORD,ranks),1.09D),"A0001 damage");require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.SWORD,ranks),.06D),"A0002 rhythm");require(close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.SWORD,ranks),.09D),"A0003 crit");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.AXE,ranks),1.09D),"A0007 damage");require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.AXE,ranks),.06D),"A0008 rhythm");require(close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.AXE,ranks),.09D),"A0009 crit");
        require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.SPEAR,ranks),1.09D),"A0013 damage");require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.SPEAR,ranks),.06D),"A0014 rhythm");require(close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.SPEAR,ranks),.09D),"A0015 crit");require(close(NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.DAGGER,ranks),1.09D),"A0019 damage");require(close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.DAGGER,ranks),.06D),"A0020 rhythm");
    }
    private static void transientResourcesRespectCapsAndLifecycle(){var state=new NotionCombatPerkState();state.addMomentum("player",99,1_000L);require(state.momentum("player")==5,"Momentum cap");state.addFury("player",999);require(close(state.fury("player"),100),"Fury cap");state.addDistanceControl("player",99,1_000L,7_000L);require(state.distanceControl("player")==3,"Distance cap");state.clearTransient("player");require(state.momentum("player")==0&&close(state.fury("player"),0)&&state.distanceControl("player")==0,"lifecycle clear");}
    private static void expect(String code,int maxRank,int cost,Map<String,Integer> deps){var d=NotionCombatPerkCatalog.definition(code).orElseThrow();require(d.maxRank()==maxRank,code+" max rank");require(d.rankCost()==cost,code+" rank cost");require(d.dependencies().equals(deps),code+" dependencies");}
    private static void expectRootGate(String code,String gateway,String mastery,int amount){var n=CombatPerkTreeModel.node(code).orElseThrow();require(n.startingPoint(),code+" root");require(n.minCharacterLevel()==8,code+" level");require(n.gatewayId().equals(gateway),code+" gateway identity");require(n.requiredNodeRanks().getOrDefault(CombatPerkTreeModel.MARTIAL_GATEWAY_NODE,0)==1,code+" main-tree gateway");require(n.requiredMastery().getOrDefault(mastery,0)==amount,code+" mastery");}
    private static void expectCapstoneGate(String code,String gateway,String mastery,int amount){var n=CombatPerkTreeModel.node(code).orElseThrow();require(n.gatewayId().equals(gateway),code+" gateway identity");require(n.requiredNodeRanks().getOrDefault(CombatPerkTreeModel.MARTIAL_GATEWAY_NODE,0)==1,code+" main-tree gateway");require(n.requiredMastery().getOrDefault(mastery,0)==amount,code+" capstone mastery");}
    private static boolean close(double a,double b){return Math.abs(a-b)<1e-9;}private static void require(boolean c,String m){if(!c)throw new AssertionError(m);}
}
