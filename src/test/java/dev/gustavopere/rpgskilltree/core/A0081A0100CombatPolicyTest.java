package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** Behavioral RED contract for the closed Notion batch A0081-A0100. */
public final class A0081A0100CombatPolicyTest {
    public static void main(String[] args) {
        sustainUsesHighestCoefficientAndOneMovingCap();
        recoveryIsDelayedAndIndependent();
        bloodThirstRequiresMandatoryTradeoffs();
        vitalityFormulasAndPhysicalComposition();
        openingMovementAndStationaryDefense();
        providerBoundariesFailClosed();
        antiCriticalOnlyTouchesDecomposedCriticalPortion();
        System.out.println("A0081A0100CombatPolicyTest: PASS");
    }

    private static void sustainUsesHighestCoefficientAndOneMovingCap() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0082",3,"A0083",3,"A0084",3,"A0085",3,"A0086",1));
        close(A0081A0100CombatPolicy.sustainCoefficient(ranks,true,true,true,true),0.018D,"largest specialized coefficient wins");
        close(A0081A0100CombatPolicy.sustainCoefficient(CombatPerkRanks.of(Map.of("A0086",1)),true,false,false,false),0.01D,"universal fills uncovered source");

        SustainResolver resolver = new SustainResolver();
        var first = resolver.resolve(new SustainResolver.Request("p","root-1",true,true,true,100,100,100,100,1.0D,
            SustainResolver.NativeCorrelation.NONE,0.0D,List.of(0.018D,0.015D)),0L);
        require(first.status()==SustainResolver.Status.AUTHORIZED,"first sustain event");
        close(first.skillTreeHealing(),1.8D,"highest coefficient, not sum");
        var duplicate = resolver.resolve(new SustainResolver.Request("p","root-1",true,true,true,100,100,100,100,1.0D,
            SustainResolver.NativeCorrelation.NONE,0.0D,List.of(0.018D)),1L);
        require(duplicate.status()==SustainResolver.Status.DUPLICATE_EVENT,"one resolution per root action");
        var capped = resolver.resolve(new SustainResolver.Request("p","root-2",true,true,true,100,100,100,100,1.0D,
            SustainResolver.NativeCorrelation.NONE,0.0D,List.of(0.018D)),2L);
        close(capped.skillTreeHealing(),1.2D,"shared cap is 3% max health per moving 20 ticks");
        var moved = resolver.resolve(new SustainResolver.Request("p","root-3",true,true,true,100,100,100,100,1.0D,
            SustainResolver.NativeCorrelation.NONE,0.0D,List.of(0.018D)),20L);
        close(moved.skillTreeHealing(),1.8D,"expired payments do not create carry-over");
        var ambiguous = resolver.resolve(new SustainResolver.Request("p","root-4",true,true,true,100,100,100,100,1.0D,
            SustainResolver.NativeCorrelation.AMBIGUOUS,0.0D,List.of(0.018D)),21L);
        require(ambiguous.status()==SustainResolver.Status.AMBIGUOUS_NATIVE_FAIL_CLOSED,"ambiguous native lifesteal fails closed");
    }

    private static void recoveryIsDelayedAndIndependent() {
        CombatRecoveryService recovery = new CombatRecoveryService();
        close(recovery.recordDamage(new CombatRecoveryService.DamageRequest("p","hit-1",true,true,true,true,true,100,32,32,3),0L),8.0D,"reserve caps at 8% max health");
        require(recovery.offerInstallment("p",100,20,2999L).isEmpty(),"three-second delay");
        var first = recovery.offerInstallment("p",100,20,3000L).orElseThrow();
        close(first.attemptedHealing(),2.0D,"quarter of frozen snapshot");
        require(recovery.confirmHealed(first,1.5D),"confirm actual healing");
        close(recovery.reserve("p",3000L),6.5D,"subtract actual healing only");
        recovery.recordHostileDamage("p",true,3001L);
        require(recovery.offerInstallment("p",100,20,3002L).isEmpty(),"hostile damage interrupts phase");
        close(recovery.reserve("p",13002L),0.0D,"reserve expires after ten seconds out of combat");
    }

    private static void bloodThirstRequiresMandatoryTradeoffs() {
        BloodThirstService absent = new BloodThirstService(null);
        absent.recordHostileDamage("p",25,100,true,0L);
        require(!absent.active("p",1L),"no body provider means no A0087 benefit");
        ToggleBodyProvider provider = new ToggleBodyProvider();
        BloodThirstService service = new BloodThirstService(provider);
        service.recordHostileDamage("p",10,100,true,0L);
        require(service.recordHostileDamage("p",15,100,true,100L),"25% hostile loss in six seconds activates");
        close(service.weaponMinimumCoefficient("p",101L),0.03D,"minimum weapon lifesteal");
        close(service.healingReceivedMultiplier("p",101L),1.08D,"healing received multiplier");
        require(provider.lastHeat==0.20D && provider.lastExhaustion==0.15D,"mandatory heat and exhaustion receipts");
        provider.available=false;
        require(!service.active("p",102L),"loss of mandatory provider ends benefit");
    }

    private static void vitalityFormulasAndPhysicalComposition() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0088",5,"A0089",5,"A0090",5,"A0091",5,"A0092",4,"A0096",3));
        close(A0081A0100CombatPolicy.maxHealthMultiplier(ranks),1.10D,"A0088");
        close(A0081A0100CombatPolicy.armorMultiplier(ranks),1.10D,"A0089");
        close(A0081A0100CombatPolicy.toughnessMultiplier(ranks),1.10D,"A0090");
        close(A0081A0100CombatPolicy.knockbackResistanceDelta(ranks),0.15D,"A0091");
        close(A0081A0100CombatPolicy.physicalDamageMultiplier(ranks,0.29D),0.92D*0.88D,"A0092 and A0096 compose independently once");
        close(A0081A0100CombatPolicy.physicalDamageMultiplier(ranks,0.30D),0.92D,"A0096 uses strict pre-impact below 30%");
    }

    private static void openingMovementAndStationaryDefense() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0097",3,"A0098",3,"A0099",3));
        A0081A0100DefenseState state = new A0081A0100DefenseState();
        state.recordEligibleHostileDamage("p",0L);
        close(A0081A0100CombatPolicy.openingDefenseMultiplier("p",ranks,state,9999L),1.0D,"opening not ready before ten seconds");
        close(A0081A0100CombatPolicy.openingDefenseMultiplier("p",ranks,state,10000L),0.85D,"opening defense ready");
        require(state.reserveOpeningDefense("p","incoming-root",10000L),"opening reserves one causal root");
        close(A0081A0100CombatPolicy.openingDefenseMultiplier("p",ranks,state,10001L),1.0D,"active reservation blocks overlap");
        require(state.commitOpeningDefense("p","incoming-root",10001L),"matching post-damage root commits opening defense");
        close(A0081A0100CombatPolicy.openingDefenseMultiplier("p",ranks,state,10002L),1.0D,"commit restarts preparation interval");
        close(A0081A0100CombatPolicy.movingDefenseMultiplier(ranks,true),0.91D,"real server sprint");
        close(A0081A0100CombatPolicy.movingDefenseMultiplier(ranks,false),1.0D,"forced/passive movement cannot qualify");
        close(A0081A0100CombatPolicy.stationaryDefenseMultiplier(ranks,true),0.88D,"shared stationary state");
    }

    private static void providerBoundariesFailClosed() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0093",5,"A0094",4));
        close(A0081A0100CombatPolicy.guardCostMultiplier(ranks,false),1.0D,"A0093 fails closed without causal guard-cost contract");
        close(A0081A0100CombatPolicy.guardRecoveryMultiplier(ranks,false),1.0D,"A0094 fails closed without break+recovery contract");
        close(A0081A0100CombatPolicy.guardCostMultiplier(ranks,true),0.90D,"safe guard-cost provider may apply A0093");
        close(A0081A0100CombatPolicy.guardRecoveryMultiplier(ranks,true),1.12D,"safe guard recovery provider may apply A0094");

        CombatPerkDefinition tenacity = NotionCombatPerkCatalog.definition("A0095").orElseThrow();
        require(tenacity.dependencies().equals(Map.of("A0091",2)),"A0095 depends only on A0091 rank 2");
        require(tenacity.providerCapabilities().equals(Set.of("epicfight:stun_armor")),"A0095 uses provider-native stun armor");
    }

    private static void antiCriticalOnlyTouchesDecomposedCriticalPortion() {
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0100",4));
        close(A0081A0100CombatPolicy.antiCriticalDamage(100,50,ranks,true,true),142.0D,"16% reduction applies only to additional critical portion");
        close(A0081A0100CombatPolicy.antiCriticalDamage(100,50,ranks,true,false),150.0D,"missing decomposition fails closed");
        close(A0081A0100CombatPolicy.antiCriticalDamage(100,0,ranks,false,true),100.0D,"ordinary hit unaffected");
    }

    private static final class ToggleBodyProvider implements BloodThirstService.BodyProvider {
        boolean available=true; double lastHeat; double lastExhaustion;
        public boolean acquire(String actor,double heat,double exhaustion,long durationTicks,long cooldownTicks){ lastHeat=heat; lastExhaustion=exhaustion; return available; }
        public boolean maintain(String actor,double heat,double exhaustion){ return available; }
        public void release(String actor) {}
    }
    private static void close(double actual,double expected,String message){ if(Math.abs(actual-expected)>1.0e-9) throw new AssertionError(message+": "+actual+" != "+expected); }
    private static void require(boolean value,String message){ if(!value) throw new AssertionError(message); }
}
