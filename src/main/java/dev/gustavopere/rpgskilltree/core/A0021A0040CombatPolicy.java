package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;

/** Pure A0021-A0040 combat policy. Provider adapters pass only facts they can prove. */
public final class A0021A0040CombatPolicy {
    private A0021A0040CombatPolicy() {}
    public record HitFacts(String actorId,String targetId,String rootActionId,WeaponFamily family,boolean direct,boolean hostile,boolean actualDamage,boolean critical,boolean repositionEligible,boolean flankOrRear,boolean heavyConfirmed,boolean protectedTarget,boolean guardPressureAvailable,boolean impactAvailable,boolean penetrationAvailable,boolean armorDebuffAvailable,double targetHealthFraction,boolean boss,long nowMillis){public HitFacts{Objects.requireNonNull(actorId);Objects.requireNonNull(targetId);Objects.requireNonNull(rootActionId);Objects.requireNonNull(family);if(!Double.isFinite(targetHealthFraction)||targetHealthFraction<0)throw new IllegalArgumentException("targetHealthFraction");}}
    public record BeforeResult(double damageMultiplier,double impactMultiplier,double guardPressureMultiplier,double physicalPenetrationFraction,boolean applyArmorSunder,double armorSunderFraction,long armorSunderDurationMillis,boolean applyBonebreaker,double outgoingPhysicalDamageMultiplier,double movementSpeedMultiplier,long bonebreakerDurationMillis){static BeforeResult neutral(){return new BeforeResult(1,1,1,0,false,0,0,false,1,1,0);}}

    public static BeforeResult beforeHit(HitFacts f,CombatPerkRanks ranks,A0021A0040CombatState state,int mastery){
        Objects.requireNonNull(f);Objects.requireNonNull(ranks);Objects.requireNonNull(state);if(!f.direct()||!f.hostile())return BeforeResult.neutral();
        double damage=1,impact=1,pressure=1,penetration=0,sunderFraction=0,outgoing=1,movement=1;boolean sunder=false,bonebreaker=false;long sunderDuration=0,boneDuration=0;
        if(f.family()==WeaponFamily.DAGGER){
            boolean danceHitPrepared=false;
            boolean danceActivationPrepared=ranks.learned("A0024")
                &&state.availableFlow(f.actorId(),f.nowMillis())>=4
                &&state.danceActivationEligible(f.actorId(),f.nowMillis())
                &&state.claimOnce(f.actorId(),f.rootActionId(),"A0024:activate",f.nowMillis())
                &&state.prepareDanceActivationCommit(f.actorId(),f.targetId(),f.rootActionId(),mastery,f.flankOrRear(),f.nowMillis());
            if(danceActivationPrepared&&f.flankOrRear()){
                damage*=1.15;if(f.impactAvailable())impact*=1.20;danceHitPrepared=true;
            }else if(ranks.learned("A0024")&&f.flankOrRear()
                &&state.claimOnce(f.actorId(),f.rootActionId(),"A0024:hit",f.nowMillis())
                &&state.prepareDanceHitCommit(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis())){
                damage*=1.15;if(f.impactAvailable())impact*=1.20;danceHitPrepared=true;
            }
            if(!danceHitPrepared){
                int rank=ranks.rank("A0023");
                if(rank>0&&f.flankOrRear()
                    &&state.availableFlow(f.actorId(),f.nowMillis())>=NotionCombatPerkRules.A0023_FLOW_COST
                    &&state.blindSpotReady(f.actorId(),f.targetId(),f.nowMillis())
                    &&(f.penetrationAvailable()||f.critical())
                    &&state.claimOnce(f.actorId(),f.rootActionId(),"A0023:consume",f.nowMillis())
                    &&state.prepareBlindSpotCommit(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis())){
                    if(f.critical())damage*=NotionCombatPerkRules.blindSpotCriticalDamageMultiplier(rank);
                    if(f.penetrationAvailable())penetration=NotionCombatPerkRules.blindSpotPenetrationFraction(rank);
                }
            }
        }else if(f.family()==WeaponFamily.HAMMER){
            int abalo=state.availableAbalo(f.actorId(),f.targetId(),f.nowMillis()),rank=ranks.rank("A0029");
            if(rank>0&&abalo>=3&&f.heavyConfirmed()
                &&state.claimOnce(f.actorId(),f.rootActionId(),"A0029:consume",f.nowMillis())
                &&state.preparePostureBreakCommit(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis())){
                if(f.guardPressureAvailable())pressure*=NotionCombatPerkRules.postureBreakPressureMultiplier(rank);
                if(f.impactAvailable())impact*=NotionCombatPerkRules.postureBreakImpactMultiplier(rank);
            }else if(ranks.rank("A0028")>0&&abalo>0&&f.guardPressureAvailable()){
                pressure*=1+NotionCombatPerkRules.abaloPressurePerCharge(ranks.rank("A0028"))*abalo;
            }
            if(ranks.learned("A0030")&&f.heavyConfirmed()
                &&state.claimOnce(f.actorId(),f.rootActionId(),"A0030:consume",f.nowMillis())
                &&state.prepareDemolitionCommit(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis())){
                damage*=1.20;if(f.impactAvailable())impact*=1.25;
            }
        }else if(f.family()==WeaponFamily.MACE){
            int rank=ranks.rank("A0035");if(rank>0&&state.trauma(f.actorId(),f.targetId(),f.nowMillis())>=3&&f.armorDebuffAvailable()&&state.claimOnce(f.actorId(),f.rootActionId(),"A0035:consume",f.nowMillis())){state.consumeTrauma(f.actorId(),f.targetId(),3,f.nowMillis());state.markSundered(f.actorId(),f.targetId(),rank,f.nowMillis());sunder=true;sunderFraction=NotionCombatPerkRules.sunderArmorFraction(rank)*(f.boss()?.5:1);sunderDuration=NotionCombatPerkRules.sunderDurationMillis(rank);}
            if(ranks.learned("A0036")&&f.heavyConfirmed()&&state.isSundered(f.actorId(),f.targetId(),f.nowMillis())&&state.bonebreakerReady(f.actorId(),f.targetId(),f.nowMillis())&&state.claimOnce(f.actorId(),f.rootActionId(),"A0036:activate",f.nowMillis())){state.startBonebreakerCooldown(f.actorId(),f.targetId(),mastery,f.nowMillis());bonebreaker=true;boneDuration=3_000;double scale=f.boss()?.5:1;outgoing=1-.08*scale;movement=1-.10*scale;}
        }
        return new BeforeResult(damage,impact,pressure,penetration,sunder,sunderFraction,sunderDuration,bonebreaker,outgoing,movement,boneDuration);
    }

    /** POST-stage state changes. A0023/A0024/A0029/A0030 commit before same-hit gains. */
    public static void afterConfirmedHit(HitFacts f,CombatPerkRanks ranks,A0021A0040CombatState state){
        Objects.requireNonNull(f);Objects.requireNonNull(ranks);Objects.requireNonNull(state);
        if(!f.direct()||!f.hostile()||!f.actualDamage()){
            if(f.family()==WeaponFamily.DAGGER)state.discardPreparedDaggerAction(f.actorId(),f.rootActionId());
            else if(f.family()==WeaponFamily.HAMMER)state.discardPreparedHammerAction(f.actorId(),f.rootActionId());
            return;
        }
        if(f.family()==WeaponFamily.DAGGER)state.commitPreparedDaggerAction(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis());
        else if(f.family()==WeaponFamily.HAMMER)state.commitPreparedHammerAction(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis());

        if(f.family()==WeaponFamily.DAGGER&&ranks.rank("A0022")>0&&f.repositionEligible()&&state.claimOnce(f.actorId(),f.rootActionId(),"A0022:flow",f.nowMillis()))state.addFlow(f.actorId(),ranks.rank("A0022"),f.nowMillis());
        if(f.family()==WeaponFamily.HAMMER&&ranks.rank("A0028")>0&&state.claimOnce(f.actorId(),f.rootActionId(),"A0028:abalo",f.nowMillis()))state.addAbalo(f.actorId(),f.targetId(),f.nowMillis());
        if(f.family()==WeaponFamily.MACE&&ranks.rank("A0034")>0&&f.protectedTarget()&&state.claimOnce(f.actorId(),f.rootActionId(),"A0034:trauma",f.nowMillis()))state.addTrauma(f.actorId(),f.targetId(),ranks.rank("A0034"),f.nowMillis());
        if(f.family()==WeaponFamily.SCYTHE&&ranks.rank("A0040")>0&&state.claimOnce(f.actorId(),f.rootActionId(),"A0040:mark",f.nowMillis()))state.applyReapingMark(f.actorId(),f.targetId(),ranks.rank("A0040"),f.targetHealthFraction(),f.nowMillis());
    }
    public static void onConfirmedDodge(String actorId,CombatPerkRanks ranks,A0021A0040CombatState state,long now){if(ranks.rank("A0022")>0||ranks.learned("A0024"))state.armDodgeReposition(actorId,now);}
    public static void onConfirmedHeavyStagger(String actorId,CombatPerkRanks ranks,A0021A0040CombatState state,long now){if(ranks.rank("A0022")>0)state.loseFlow(actorId,2,now);}
    public static void onConfirmedGuardBreak(String actorId,String targetId,CombatPerkRanks ranks,A0021A0040CombatState state,int mastery,long now){if(ranks.learned("A0030")&&state.demolitionReady(actorId,targetId,now))state.armDemolition(actorId,targetId,mastery,now);}
    public static boolean fallbackRepositionEligible(double displacement,double angle,boolean teleported,boolean knockback){return !teleported&&!knockback&&displacement>=NotionCombatPerkRules.A0022_FALLBACK_MIN_DISPLACEMENT&&Math.abs(angle)>=NotionCombatPerkRules.A0022_FALLBACK_MIN_ANGLE_DEGREES;}
}
