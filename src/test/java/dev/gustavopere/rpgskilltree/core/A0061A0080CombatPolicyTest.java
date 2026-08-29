package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class A0061A0080CombatPolicyTest {
    public static void main(String[] args) {
        coreOffenseAndClassification();
        sharedCriticalAndCadenceComposeOnce();
        retaliationIsSingleWindow();
        executionIsTwoHitAndBossHalved();
        firstBloodIsTwoHit();
        sustainedRhythmIsFailClosed();
        stancesAreAtomicAndExclusive();
        movementStationaryAndOpportunity();
        stationaryServiceUsesCanonicalThreshold();
        System.out.println("A0061A0080CombatPolicyTest: PASS");
    }

    private static void coreOffenseAndClassification() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of(
            "A0061",5,"A0062",4,"A0063",3,"A0065",4,"A0066",4,"A0068",3,"A0069",3,"A0070",5,"A0071",5));
        A0061A0080CombatState state = new A0061A0080CombatState();
        var wounded = A0061A0080CombatPolicy.beforePhysicalHit(
            new A0061A0080CombatPolicy.HitFacts("p","t","r1",0.30D,false,false,false,false,false,true,true,1000L), ranks, state);
        close(wounded.damageMultiplier(),1.22D,"A0061 + wounded");
        close(wounded.penetrationFraction(),0.08D,"penetration");
        close(wounded.impactMultiplier(),1.12D,"impact");

        var boss = A0061A0080CombatPolicy.beforePhysicalHit(
            new A0061A0080CombatPolicy.HitFacts("p","b","r2",0.90D,true,false,false,false,false,true,true,1100L), ranks, state);
        close(boss.damageMultiplier(),1.37D,"base + intact + boss; elite must not stack");
        var elite = A0061A0080CombatPolicy.beforePhysicalHit(
            new A0061A0080CombatPolicy.HitFacts("p","e","r3",0.50D,false,true,false,false,false,true,true,1200L), ranks, state);
        close(elite.damageMultiplier(),1.25D,"base + elite");
        close(A0061A0080CombatPolicy.criticalChanceBonus(ranks),0.08D,"crit chance");
        close(A0061A0080CombatPolicy.criticalDamageMultiplier(ranks,true),1.15D,"crit damage");
    }

    private static void sharedCriticalAndCadenceComposeOnce() {
        CombatPerkRanks sword = CombatPerkRanks.of(Map.of("A0003",3,"A0002",3,"A0062",4,"A0064",4));
        close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.SWORD,sword),0.17D,
            "A0062 must compose into the existing canonical sword critical roll");
        close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.SWORD,sword),0.14D,
            "A0064 must compose into the existing provider-native sword cadence modifier");

        CombatPerkRanks hammer = CombatPerkRanks.of(Map.of("A0027",2,"A0026",2,"A0062",1,"A0064",1));
        close(NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.HAMMER,hammer),0.08D,
            "A0062 must compose for later Epic Fight families too");
        close(NotionCombatPerkRules.rhythmBonus(WeaponFamily.HAMMER,hammer),0.06D,
            "A0064 must compose for later Epic Fight families too");
    }

    private static void retaliationIsSingleWindow() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0072",3));
        A0061A0080CombatState state = new A0061A0080CombatState();
        require(A0061A0080CombatPolicy.onDirectHostileDamageTaken("p","hurt1",4.0D,true,ranks,state,1000L),"retaliation opens");
        close(A0061A0080CombatPolicy.retaliationDamageMultiplier("p",ranks,state,2000L),1.12D,"retaliation magnitude");
        require(A0061A0080CombatPolicy.onDirectHostileDamageTaken("p","hurt2",2.0D,true,ranks,state,2500L),"retaliation refreshes");
        close(A0061A0080CombatPolicy.retaliationDamageMultiplier("p",ranks,state,5001L),1.12D,"refresh no stacking");
        close(A0061A0080CombatPolicy.retaliationDamageMultiplier("p",ranks,state,5501L),1.0D,"retaliation expires");
    }

    private static void executionIsTwoHitAndBossHalved() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0073",1));
        A0061A0080CombatState state = new A0061A0080CombatState();
        var first = A0061A0080CombatPolicy.execution("p","t","root1",0.19D,false,ranks,state,true,1000L);
        require(!first.applied(),"first execution hit only arms");
        var second = A0061A0080CombatPolicy.execution("p","t","root2",0.15D,false,ranks,state,true,1500L);
        require(second.applied(),"second execution hit consumes");
        close(second.damageMultiplier(),1.18D,"execution damage"); close(second.impactMultiplier(),1.20D,"execution impact");
        require(A0061A0080CombatPolicy.execution("p","t","root3",0.10D,false,ranks,state,true,2000L).damageMultiplier()==1.0D,"cooldown blocks rearm");

        A0061A0080CombatState bossState = new A0061A0080CombatState();
        A0061A0080CombatPolicy.execution("p","boss","b1",0.19D,true,ranks,bossState,true,1000L);
        var bossSecond = A0061A0080CombatPolicy.execution("p","boss","b2",0.18D,true,ranks,bossState,true,1200L);
        close(bossSecond.damageMultiplier(),1.09D,"boss execution damage halved");
        close(bossSecond.staminaRefundFraction(),0.0D,"stamina fail closed without receipt");
    }

    private static void firstBloodIsTwoHit() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0074",1));
        A0061A0080CombatState state = new A0061A0080CombatState();
        var opener = A0061A0080CombatPolicy.firstBlood("p","t","o1",0.90D,ranks,state,true,10000L);
        require(!opener.applied(),"opener arms but gets no bonus");
        var follow = A0061A0080CombatPolicy.firstBlood("p","t","o2",0.80D,ranks,state,true,11000L);
        require(follow.applied(),"follow-up consumes opener state");
        close(follow.damageMultiplier(),1.10D,"first blood damage"); close(follow.impactMultiplier(),1.20D,"first blood impact");
    }

    private static void sustainedRhythmIsFailClosed() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0075",1));
        A0061A0080CombatState state = new A0061A0080CombatState();
        A0061A0080CombatPolicy.recordMartialAction("p","sword",ranks,state,true,true,true,1000L);
        A0061A0080CombatPolicy.recordMartialAction("p","axe",ranks,state,true,true,true,2000L);
        require(!A0061A0080CombatPolicy.recordMartialAction("p","spear",ranks,state,true,false,true,3000L),"missing thermal receipt prevents activation");
        require(!state.sustainedRhythmActive("p",3500L),"fail closed means no partial benefit");

        A0061A0080CombatState ok = new A0061A0080CombatState();
        A0061A0080CombatPolicy.recordMartialAction("p","sword",ranks,ok,true,true,true,1000L);
        A0061A0080CombatPolicy.recordMartialAction("p","axe",ranks,ok,true,true,true,2000L);
        require(A0061A0080CombatPolicy.recordMartialAction("p","spear",ranks,ok,true,true,true,3000L),"three distinct families activate");
        require(ok.sustainedRhythmActive("p",8000L),"six-second window active");
        require(!ok.sustainedRhythmActive("p",9001L),"window expires");
    }

    private static void stancesAreAtomicAndExclusive() {
        A0061A0080CombatState state = new A0061A0080CombatState();
        require(state.switchStance("p",A0061A0080CombatState.Stance.AGGRESSIVE,1000L),"activate aggressive");
        require(!state.switchStance("p",A0061A0080CombatState.Stance.CAUTIOUS,2000L),"swap cooldown");
        require(state.switchStance("p",A0061A0080CombatState.Stance.CAUTIOUS,2500L),"swap after 1.5s");
        require(state.stance("p")==A0061A0080CombatState.Stance.CAUTIOUS,"exclusive slot");
        close(A0061A0080CombatPolicy.stanceDamageMultiplier(state.stance("p")),0.95D,"cautious damage tradeoff");
        close(A0061A0080CombatPolicy.stancePhysicalResistanceDelta(state.stance("p")),0.08D,"cautious resistance");
    }

    private static void movementStationaryAndOpportunity() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0078",3,"A0079",3,"A0080",1));
        close(A0061A0080CombatPolicy.movementDamageMultiplier(ranks,true),1.12D,"sprint bonus");
        close(A0061A0080CombatPolicy.stationaryDamageMultiplier(ranks,true),1.15D,"stationary bonus");
        A0061A0080CombatState state = new A0061A0080CombatState();
        require(!A0061A0080CombatPolicy.onConfirmedDodgeAvoidance("p","d0",false,ranks,state,1000L),"mere dodge action is insufficient");
        require(A0061A0080CombatPolicy.onConfirmedDodgeAvoidance("p","d1",true,ranks,state,2000L),"confirmed avoided hostile attack arms opportunity");
        close(A0061A0080CombatPolicy.consumeOpportunityDamageMultiplier("p","hit1",ranks,state,3000L),1.15D,"opportunity consumed once");
        close(A0061A0080CombatPolicy.consumeOpportunityDamageMultiplier("p","hit2",ranks,state,3100L),1.0D,"single consumption");
    }

    private static void stationaryServiceUsesCanonicalThreshold() {
        StationaryStateService service = new StationaryStateService();
        for(int i=0;i<29;i++) require(!service.sample("p",0.001D*i,0.0D,0.0D,false),"needs 30 ticks");
        require(service.sample("p",0.029D,0.0D,0.0D,false),"30 ticks under path threshold becomes stationary");
        require(!service.sample("p",0.20D,0.0D,0.0D,false),"path above 0.10 resets immediately");
        require(!service.sample("p",0.20D,0.0D,0.0D,true),"forced transition invalidates");
    }

    private static void close(double actual,double expected,String message){ if(Math.abs(actual-expected)>1.0e-9) throw new AssertionError(message+": "+actual+" != "+expected); }
    private static void require(boolean value,String message){ if(!value) throw new AssertionError(message); }
}
