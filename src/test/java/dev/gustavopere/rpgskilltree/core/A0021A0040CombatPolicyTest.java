package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class A0021A0040CombatPolicyTest {
    public static void main(String[] args) {
        genericCoefficients(); flowAndBlindSpot(); shadowDance(); hammerState(); maceState(); reapingMark(); failClosedRules();
        System.out.println("A0021A0040CombatPolicyTest: PASS");
    }

    private static void genericCoefficients(){
        CombatPerkRanks r=ranks(Map.ofEntries(
            Map.entry("A0019",3),Map.entry("A0020",3),Map.entry("A0021",3),Map.entry("A0025",3),
            Map.entry("A0026",3),Map.entry("A0027",3),Map.entry("A0031",3),Map.entry("A0032",3),
            Map.entry("A0033",3),Map.entry("A0037",3),Map.entry("A0038",3),Map.entry("A0039",3)
        ));
        eq(1.09,NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.HAMMER,r));eq(.06,NotionCombatPerkRules.rhythmBonus(WeaponFamily.MACE,r));eq(.09,NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.SCYTHE,r));eq(.09,NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.DAGGER,r));
    }
    private static void flowAndBlindSpot(){
        A0021A0040CombatState s=new A0021A0040CombatState();CombatPerkRanks r=ranks(Map.of("A0022",2,"A0023",2));String a="p",t="t";
        s.recordHorizontalMovement(a,0);for(int i=0;i<4;i++)A0021A0040CombatPolicy.afterConfirmedHit(f(a,t,"f"+i,WeaponFamily.DAGGER,true,true,false,false,false,false,true,.8,100+i),r,s);
        req(s.flow(a,200)==4,"flow cap");
        var m=A0021A0040CombatPolicy.beforeHit(f(a,t,"blind",WeaponFamily.DAGGER,true,true,true,false,false,false,true,.8,300),r,s,0);
        eq(1.25,m.damageMultiplier());eq(.10,m.physicalPenetrationFraction());req(s.flow(a,300)==2,"blind spot cost");
        var cd=A0021A0040CombatPolicy.beforeHit(f(a,t,"blind2",WeaponFamily.DAGGER,true,true,true,false,false,false,true,.8,301),r,s,0);eq(1.0,cd.damageMultiplier());
        s.recordHorizontalMovement(a,1_000);s.addFlow(a,2,1_000);s.tickFlow(a,true,3_999);req(s.flow(a,3_999)>0,"no early idle decay");s.tickFlow(a,true,4_000);req(s.flow(a,4_000)==2,"first idle decay");
    }
    private static void shadowDance(){
        A0021A0040CombatState s=new A0021A0040CombatState();CombatPerkRanks r=ranks(Map.of("A0022",2,"A0024",1));for(int i=0;i<4;i++)s.addFlow("p",2,i);A0021A0040CombatPolicy.onConfirmedDodge("p",r,s,100);
        var m=A0021A0040CombatPolicy.beforeHit(f("p","t","dance",WeaponFamily.DAGGER,true,true,false,false,false,false,true,.9,101),r,s,90);
        eq(1.15,m.damageMultiplier());eq(1.20,m.impactMultiplier());req(s.flow("p",101)==0,"dance consumes all flow");req(s.consumeDanceMove("p",102),"first move benefit");req(!s.consumeDanceMove("p",103),"move once");
    }
    private static void hammerState(){
        A0021A0040CombatState s=new A0021A0040CombatState();CombatPerkRanks r=ranks(Map.of("A0028",2,"A0029",2,"A0030",1));for(int i=0;i<3;i++)s.addAbalo("p","t",i);
        var m=A0021A0040CombatPolicy.beforeHit(f("p","t","break",WeaponFamily.HAMMER,true,true,false,true,false,true,true,.7,10),r,s,80);eq(1.45,m.guardPressureMultiplier());eq(1.15,m.impactMultiplier());req(s.abalo("p","t",10)==0,"break consumes abalo");
        A0021A0040CombatPolicy.onConfirmedGuardBreak("p","t",r,s,80,20);var d=A0021A0040CombatPolicy.beforeHit(f("p","t","demo",WeaponFamily.HAMMER,true,true,false,true,false,true,true,.7,21),r,s,80);eq(1.20,d.damageMultiplier());eq(1.25,d.impactMultiplier());
    }
    private static void maceState(){
        A0021A0040CombatState s=new A0021A0040CombatState();CombatPerkRanks r=ranks(Map.of("A0034",2,"A0035",2,"A0036",1));for(int i=0;i<3;i++)s.addTrauma("p","t",2,i);
        var sunder=A0021A0040CombatPolicy.beforeHit(f("p","t","sunder",WeaponFamily.MACE,true,false,false,false,true,false,true,.8,20),r,s,80);req(sunder.applyArmorSunder(),"sunder applies");eq(.12,sunder.armorSunderFraction());req(s.isSundered("p","t",20),"sunder state");
        var bone=A0021A0040CombatPolicy.beforeHit(f("p","t","bone",WeaponFamily.MACE,true,false,false,true,true,false,true,.8,21),r,s,80);req(bone.applyBonebreaker(),"bonebreaker");eq(.92,bone.outgoingPhysicalDamageMultiplier());eq(.90,bone.movementSpeedMultiplier());
        A0021A0040CombatState boss=new A0021A0040CombatState();for(int i=0;i<3;i++)boss.addTrauma("p","b",2,i);var half=A0021A0040CombatPolicy.beforeHit(f("p","b","boss",WeaponFamily.MACE,true,false,false,false,true,true,true,.8,20),r,boss,80);eq(.06,half.armorSunderFraction());
    }
    private static void reapingMark(){
        A0021A0040CombatState s=new A0021A0040CombatState();CombatPerkRanks r=ranks(Map.of("A0040",2));A0021A0040CombatPolicy.afterConfirmedHit(f("p","t","mark",WeaponFamily.SCYTHE,true,false,false,false,false,false,true,.75,0),r,s);req(s.reapMarked("p","t",1),"marked");req(!s.reapMature("p","t",.50,1),"50 percent is not below");req(s.reapMature("p","t",.49,2),"matures below half");req(s.reapMarked("p","t",9_999),"rank2 lasts 10s");req(!s.reapMarked("p","t",10_000),"expires");
    }
    private static void failClosedRules(){
        A0021A0040CombatState s=new A0021A0040CombatState();CombatPerkRanks r=ranks(Map.of("A0023",2,"A0035",2));s.addFlow("p",2,0);s.addFlow("p",2,0);
        var front=A0021A0040CombatPolicy.beforeHit(f("p","t","front",WeaponFamily.DAGGER,true,false,true,false,false,false,true,.8,1),r,s,0);eq(0,front.physicalPenetrationFraction());
        for(int i=0;i<3;i++)s.addTrauma("p","m",2,i);var noDebuff=A0021A0040CombatPolicy.beforeHit(f("p","m","no",WeaponFamily.MACE,true,false,false,false,true,false,false,.8,3),r,s,80);req(!noDebuff.applyArmorSunder(),"A0035 fail closed without armor debuff hook");
        req(!A0021A0040CombatPolicy.fallbackRepositionEligible(2,70,true,false),"teleport not reposition");req(A0021A0040CombatPolicy.fallbackRepositionEligible(1.5,60,false,false),"exact fallback thresholds");
    }
    private static A0021A0040CombatPolicy.HitFacts f(String a,String t,String id,WeaponFamily fam,boolean reposition,boolean flank,boolean crit,boolean heavy,boolean protectedTarget,boolean boss,boolean provider,double hp,long now){return new A0021A0040CombatPolicy.HitFacts(a,t,id,fam,true,true,true,crit,reposition,flank,heavy,protectedTarget,provider,provider,provider,provider,hp,boss,now);}
    private static CombatPerkRanks ranks(Map<String,Integer> map){return CombatPerkRanks.of(map);}
    private static void eq(double e,double a){if(Math.abs(e-a)>1e-9)throw new AssertionError("expected "+e+" got "+a);}private static void req(boolean v,String m){if(!v)throw new AssertionError(m);}
}
