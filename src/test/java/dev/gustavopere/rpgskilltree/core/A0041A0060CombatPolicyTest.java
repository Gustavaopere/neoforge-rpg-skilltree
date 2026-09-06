package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class A0041A0060CombatPolicyTest {
    private A0041A0060CombatPolicyTest() {}

    public static void main(String[] args) {
        scytheReservesMatureMarkUntilConfirmedPostAndHarvestTransfersOnlyToDifferentTarget();
        focusUsesExactRankedRatesAndFailClosedShotCommit();
        crossbowCadenceRequiresHitThenNativeReloadAndConsumesAtomically();
        fistSequenceAndHeavyConsumersAreProviderGated();
        masteryCooldownsMatchNotion();
        System.out.println("A0041A0060CombatPolicyTest: PASS");
    }

    private static void scytheReservesMatureMarkUntilConfirmedPostAndHarvestTransfersOnlyToDifferentTarget() {
        var legacy = new A0021A0040CombatState();
        var state = new A0041A0060CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0040",2,"A0041",2,"A0042",1));
        long now = 1_000L;
        legacy.applyReapingMark("p","t1",2,0.70,now);
        require(legacy.reapMature("p","t1",0.40,now+100), "mark should mature on >=50 to <50 crossing");

        var cut = A0041A0060CombatPolicy.scytheCut("p","t1","hit-1",ranks,legacy,state,0.40,true,now+150);
        close(cut.damageMultiplier(),1.20,"A0041 rank2 damage");
        close(cut.impactMultiplier(),1.25,"A0041 rank2 impact");
        require(legacy.reapMarked("p","t1",now+151), "A0041 PRE must reserve, not consume, the mature mark");
        var duplicate = A0041A0060CombatPolicy.scytheCut("p","t1","hit-1",ranks,legacy,state,0.40,true,now+152);
        close(duplicate.damageMultiplier(),1.0,"same causal action cannot reserve twice");

        legacy.applyReapingMark("p","dead",2,0.70,now+200);
        legacy.reapMature("p","dead",0.30,now+250);
        require(A0041A0060CombatPolicy.armBattleHarvestOnKill("p","dead",ranks,legacy,state,80,true,now+300), "legitimate mature-mark kill should arm A0042");
        require(!A0041A0060CombatPolicy.consumeBattleHarvestOnHit("p","dead",ranks,state,now+350), "A0042 cannot transfer to the killed target");
        require(A0041A0060CombatPolicy.consumeBattleHarvestOnHit("p","t2",ranks,state,now+351), "A0042 should transfer to a different target");
        require(!A0041A0060CombatPolicy.consumeBattleHarvestOnHit("p","t3",ranks,state,now+352), "A0042 transfer is single-use");
    }

    private static void focusUsesExactRankedRatesAndFailClosedShotCommit() {
        var state = new A0041A0060CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0046",2,"A0047",2,"A0048",1));
        close(A0041A0060CombatPolicy.focusStableGain(1,1_000),8.0,"A0046 rank1 stable gain");
        close(A0041A0060CombatPolicy.focusStableGain(2,1_000),10.0,"A0046 rank2 stable gain");
        close(A0041A0060CombatPolicy.focusDistantHitGain(2),12.5,"A0046 rank2 distant hit gain");
        state.addFocus("p",100.0);

        var unavailable = A0041A0060CombatPolicy.tryDominatedShot("p","bow-1",ranks,state,true,600,false,false,2_000);
        require(!unavailable.active(), "A0047 must fail closed when neither speed nor penetration is safely supported");
        close(state.focus("p"),100.0,"fail-closed A0047 must not consume Focus");

        var penetrationOnly = A0041A0060CombatPolicy.tryDominatedShot("p","bow-2",ranks,state,true,600,false,true,2_100);
        require(penetrationOnly.active(), "A0047 may activate when one semantic component is safe");
        close(state.focus("p"),75.0,"A0047 consumes 25 Focus atomically");
        close(penetrationOnly.penetrationFraction(),0.12,"A0047 rank2 penetration");
        close(penetrationOnly.launchSpeedMultiplier(),1.0,"unavailable projectile speed must stay omitted");

        state.addFocus("p",25.0);
        var prepared = A0041A0060CombatPolicy.tryPreparedShot("p","bow-3",ranks,state,80,true,1_300,2_200);
        require(prepared.prepared(), "A0048 should arm/commit at 80 Focus and 1.25s stable aim");
        close(state.focus("p"),50.0,"A0048 consumes 50 Focus on the shot, before knowing hit/miss");
        var hit = A0041A0060CombatPolicy.resolveBowHit(prepared,15.0,false);
        close(hit.damageMultiplier(),1.20,"A0048 damage survives when penetration hook is unavailable");
        close(hit.penetrationFraction(),0.0,"A0048 penetration must fail closed independently");
    }

    private static void crossbowCadenceRequiresHitThenNativeReloadAndConsumesAtomically() {
        var state = new A0041A0060CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0052",2,"A0053",2,"A0054",1));
        long now = 10_000;
        A0041A0060CombatPolicy.recordCrossbowHit("p","shot-1","xbow",ranks,state,now);
        require(!A0041A0060CombatPolicy.onCrossbowReloadComplete("p","xbow",ranks,state,false,now+1_000), "reload without native ammo consumption must not count");
        require(state.cadence("p")==0,"invalid reload cannot add Cadence");
        require(A0041A0060CombatPolicy.onCrossbowReloadComplete("p","xbow",ranks,state,true,now+1_100), "hit + native reload inside rank2 8s window should add Cadence");
        require(state.cadence("p")==1,"Cadence +1");

        state.addCadence("p");
        var unavailable = A0041A0060CombatPolicy.tryPiercingBolt("p","bolt-1",ranks,state,true,false,false,now+2_000);
        require(!unavailable.applied(), "A0053 must fail closed when penetration and impact are both unavailable");
        require(state.cadence("p")==2,"fail-closed A0053 must not spend Cadence");
        var impactOnly = A0041A0060CombatPolicy.tryPiercingBolt("p","bolt-2",ranks,state,true,false,true,now+2_100);
        require(impactOnly.applied(),"A0053 may reserve an impact-only safe component");
        close(impactOnly.impactMultiplier(),1.25,"A0053 rank2 impact");
        require(state.cadence("p")==2,"A0053 release reserves rather than consumes Cadence");
        require(A0041A0060CombatPolicy.commitPiercingBolt("p","bolt-2",ranks,state,now+2_150),"A0053 commits only after correlated projectile creation");
        require(state.cadence("p")==0,"A0053 commit consumes two Cadence exactly once");

        state.addCadence("p"); state.addCadence("p"); state.addCadence("p");
        require(A0041A0060CombatPolicy.armAdjustedMechanismOnReload("p",ranks,state,80,true,now+3_000),"A0054 should arm from three clean Cadence on a complete native reload");
        require(state.cadence("p")==3,"arming A0054 must not consume Cadence before a projectile exists");
        var adjusted = A0041A0060CombatPolicy.tryAdjustedCrossbowShot("p","bolt-3",ranks,state,now+3_100);
        require(adjusted.applied(),"A0054 next shot should reserve the activation");
        close(adjusted.damageMultiplier(),1.15,"A0054 damage");
        require(state.cadence("p")==3,"A0054 release must not pre-consume Cadence");
        require(A0041A0060CombatPolicy.commitAdjustedCrossbowShot("p","bolt-3",ranks,state,now+3_150),"A0054 commits on correlated projectile creation");
        require(state.cadence("p")==0,"A0054 commit consumes all Cadence");
        require(!A0041A0060CombatPolicy.tryAdjustedCrossbowShot("p","bolt-4",ranks,state,now+3_200).applied(),"A0054 activation is single-use");
    }

    private static void fistSequenceAndHeavyConsumersAreProviderGated() {
        var state = new A0041A0060CombatState();
        var ranks = CombatPerkRanks.of(Map.of("A0058",2,"A0059",2,"A0060",1));
        long now=20_000;
        for(int i=0;i<5;i++) A0041A0060CombatPolicy.afterConfirmedFistHit("p","f"+i,ranks,state,now+i*500L);
        require(state.sequence("p",now+2_100)==5,"A0058 should cap Sequence at five inside rank2 2.5s windows");

        var noHeavy=A0041A0060CombatPolicy.beforeFistHeavy("p","heavy-0",ranks,state,80,false,true,true,now+2_200);
        require(!noHeavy.applied(),"A0059/A0060 cannot infer heavy/finalizer");
        require(state.sequence("p",now+2_200)==5,"fail-closed heavy must not consume Sequence");

        var finisher=A0041A0060CombatPolicy.beforeFistHeavy("p","heavy-1",ranks,state,80,true,false,true,now+2_300);
        require(finisher.applied()&&finisher.finalCombination(),"A0060 takes precedence at five Sequence");
        close(finisher.damageMultiplier(),1.18,"A0060 damage");
        close(finisher.impactMultiplier(),1.25,"A0060 impact");
        close(finisher.staminaRefundFraction(),0.0,"A0060 stamina refund remains fail-closed without causal receipts");
        require(state.sequence("p",now+2_300)==0,"A0060 consumes all Sequence");

        for(int i=0;i<3;i++) A0041A0060CombatPolicy.afterConfirmedFistHit("p","g"+i,ranks,state,now+3_000+i*400L);
        var rhythm=A0041A0060CombatPolicy.beforeFistHeavy("p","heavy-2",ranks,state,80,true,true,true,now+4_300);
        require(rhythm.applied()&&!rhythm.finalCombination(),"A0059 should consume three Sequence below capstone threshold");
        close(rhythm.guardPressureMultiplier(),1.40,"A0059 rank2 pressure");
        close(rhythm.impactMultiplier(),1.15,"A0059 rank2 impact");
        require(state.sequence("p",now+4_300)==0,"A0059 consumes three Sequence");
    }

    private static void masteryCooldownsMatchNotion() {
        require(A0041A0060CombatPolicy.battleHarvestCooldownMillis(80)==10_000,"A0042 mastery80 cooldown");
        require(A0041A0060CombatPolicy.battleHarvestCooldownMillis(90)==9_000,"A0042 mastery90 cooldown");
        require(A0041A0060CombatPolicy.battleHarvestCooldownMillis(100)==8_000,"A0042 mastery100 cooldown");
        require(A0041A0060CombatPolicy.preparedShotCooldownMillis(80)==8_000,"A0048 mastery80 cooldown");
        require(A0041A0060CombatPolicy.preparedShotCooldownMillis(90)==7_000,"A0048 mastery90 cooldown");
        require(A0041A0060CombatPolicy.preparedShotCooldownMillis(100)==6_000,"A0048 mastery100 cooldown");
        require(A0041A0060CombatPolicy.finalCombinationCooldownMillis(80)==8_000,"A0060 mastery80 cooldown");
        require(A0041A0060CombatPolicy.finalCombinationCooldownMillis(90)==7_000,"A0060 mastery90 cooldown");
        require(A0041A0060CombatPolicy.finalCombinationCooldownMillis(100)==6_000,"A0060 mastery100 cooldown");
    }

    private static void close(double actual,double expected,String message){
        if(Math.abs(actual-expected)>1.0E-9) throw new AssertionError(message+": expected "+expected+", got "+actual);
    }
    private static void require(boolean value,String message){if(!value)throw new AssertionError(message);}
}
