package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;

/** Pure A0021-A0040 combat policy. Provider adapters pass only facts they can prove. */
public final class A0021A0040CombatPolicy {
    private A0021A0040CombatPolicy() {}
    public record HitFacts(String actorId,String targetId,String rootActionId,WeaponFamily family,boolean direct,boolean hostile,boolean actualDamage,boolean critical,boolean repositionEligible,boolean flankOrRear,boolean heavyConfirmed,boolean protectedTarget,boolean guardPressureAvailable,boolean impactAvailable,boolean penetrationAvailable,boolean armorDebuffAvailable,double targetHealthFraction,boolean boss,long nowMillis){public HitFacts{Objects.requireNonNull(actorId);Objects.requireNonNull(targetId);Objects.requireNonNull(rootActionId);Objects.requireNonNull(family);if(!Double.isFinite(targetHealthFraction)||targetHealthFraction<0)throw new IllegalArgumentException("targetHealthFraction");}}
    public record BeforeResult(double damageMultiplier,double impactMultiplier,double guardPressureMultiplier,double physicalPenetrationFraction,boolean applyArmorSunder,double armorSunderFraction,long armorSunderDurationMillis,boolean applyBonebreaker,double outgoingPhysicalDamageMultiplier,double movementSpeedMultiplier,long bonebreakerDurationMillis){static BeforeResult neutral(){return new BeforeResult(1,1,1,0,false,0,0,false,1,1,0);}}
    public record AfterResult(boolean armorSunderCommitted,boolean bonebreakerCommitted){static AfterResult neutral(){return new AfterResult(false,false);}}

    public static BeforeResult beforeHit(HitFacts f,CombatPerkRanks ranks,A0021A0040CombatState state,int mastery){
        Objects.requireNonNull(f);Objects.requireNonNull(ranks);Objects.requireNonNull(state);if(!f.direct()||!f.hostile())return BeforeResult.neutral();
        double damage=1,impact=1,pressure=1,penetration=0,sunderFraction=0,outgoing=1,movement=1;boolean sunder=false,bonebreaker=false;long sunderDuration=0,boneDuration=0;
        if(f.family()==WeaponFamily.DAGGER){
            if(ranks.learned("A0024")&&state.flow(f.actorId(),f.nowMillis())>=4&&state.danceActivationEligible(f.actorId(),f.nowMillis())&&state.claimOnce(f.actorId(),f.rootActionId(),"A0024:activate",f.nowMillis())){state.consumeFlow(f.actorId(),4,f.nowMillis());state.activateDance(f.actorId(),mastery,f.nowMillis());}
            if(f.flankOrRear()&&state.consumeDanceHit(f.actorId(),f.nowMillis())){damage*=1.15;if(f.impactAvailable())impact*=1.20;}
            else{int rank=ranks.rank("A0023");if(rank>0&&f.flankOrRear()&&state.flow(f.actorId(),f.nowMillis())>=NotionCombatPerkRules.A0023_FLOW_COST&&state.blindSpotReady(f.actorId(),f.targetId(),f.nowMillis())&&(f.penetrationAvailable()||f.critical())&&state.claimOnce(f.actorId(),f.rootActionId(),"A0023:consume",f.nowMillis())){state.consumeFlow(f.actorId(),NotionCombatPerkRules.A0023_FLOW_COST,f.nowMillis());if(f.critical())damage*=NotionCombatPerkRules.blindSpotCriticalDamageMultiplier(rank);if(f.penetrationAvailable())penetration=NotionCombatPerkRules.blindSpotPenetrationFraction(rank);state.startBlindSpotCooldown(f.actorId(),f.targetId(),f.nowMillis());}}
        }else if(f.family()==WeaponFamily.HAMMER){
            int abalo=state.abalo(f.actorId(),f.targetId(),f.nowMillis()),rank=ranks.rank("A0029");
            if(rank>0&&abalo>=3&&f.heavyConfirmed()&&state.claimOnce(f.actorId(),f.rootActionId(),"A0029:consume",f.nowMillis())){state.consumeAbalo(f.actorId(),f.targetId(),3,f.nowMillis());if(f.guardPressureAvailable())pressure*=NotionCombatPerkRules.postureBreakPressureMultiplier(rank);if(f.impactAvailable())impact*=NotionCombatPerkRules.postureBreakImpactMultiplier(rank);}else if(ranks.rank("A0028")>0&&abalo>0&&f.guardPressureAvailable())pressure*=1+NotionCombatPerkRules.abaloPressurePerCharge(ranks.rank("A0028"))*abalo;
            if(ranks.learned("A0030")&&f.heavyConfirmed()&&state.consumeDemolition(f.actorId(),f.targetId(),f.nowMillis())&&state.claimOnce(f.actorId(),f.rootActionId(),"A0030:consume",f.nowMillis())){damage*=1.20;if(f.impactAvailable())impact*=1.25;}
        }else if(f.family()==WeaponFamily.MACE){
            boolean preexistingSunder=state.isSundered(f.actorId(),f.targetId(),f.nowMillis());
            int rank=ranks.rank("A0035");
            if(rank>0&&state.trauma(f.actorId(),f.targetId(),f.nowMillis())>=3&&f.armorDebuffAvailable()&&state.prepareSunder(f.actorId(),f.targetId(),f.rootActionId(),rank,f.nowMillis())){
                sunder=true;
                sunderFraction=NotionCombatPerkRules.sunderArmorFraction(rank)*(f.boss()?.5:1);
                sunderDuration=NotionCombatPerkRules.sunderDurationMillis(rank);
            }
            if(ranks.learned("A0036")&&f.heavyConfirmed()&&preexistingSunder&&state.bonebreakerReady(f.actorId(),f.targetId(),f.nowMillis())&&state.prepareBonebreaker(f.actorId(),f.targetId(),f.rootActionId(),mastery,f.nowMillis())){
                bonebreaker=true;
                boneDuration=3_000;
                double scale=f.boss()?.5:1;
                outgoing=1-.08*scale;
                movement=1-.10*scale;
            }
        }
        return new BeforeResult(damage,impact,pressure,penetration,sunder,sunderFraction,sunderDuration,bonebreaker,outgoing,movement,boneDuration);
    }

    /**
     * POST-only irreversible commit. A0035/A0036 reservations are created in PRE but Trauma,
     * Sundered state and Bonebreaker cooldown change only after real direct hostile damage.
     */
    public static AfterResult afterConfirmedHit(HitFacts f,CombatPerkRanks ranks,A0021A0040CombatState state){
        if(!f.direct()||!f.hostile()||!f.actualDamage())return AfterResult.neutral();
        boolean sunderCommitted=false,bonebreakerCommitted=false;
        if(f.family()==WeaponFamily.MACE){
            if(ranks.rank("A0035")>0)sunderCommitted=state.commitPreparedSunder(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis());
            if(ranks.learned("A0036"))bonebreakerCommitted=state.commitPreparedBonebreaker(f.actorId(),f.targetId(),f.rootActionId(),f.nowMillis());
        }
        if(f.family()==WeaponFamily.DAGGER&&ranks.rank("A0022")>0&&f.repositionEligible()&&state.claimOnce(f.actorId(),f.rootActionId(),"A0022:flow",f.nowMillis()))state.addFlow(f.actorId(),ranks.rank("A0022"),f.nowMillis());
        if(f.family()==WeaponFamily.HAMMER&&ranks.rank("A0028")>0&&state.claimOnce(f.actorId(),f.rootActionId(),"A0028:abalo",f.nowMillis()))state.addAbalo(f.actorId(),f.targetId(),f.nowMillis());
        if(f.family()==WeaponFamily.MACE&&ranks.rank("A0034")>0&&f.protectedTarget()&&state.claimOnce(f.actorId(),f.rootActionId(),"A0034:trauma",f.nowMillis()))state.addTrauma(f.actorId(),f.targetId(),ranks.rank("A0034"),f.nowMillis());
        if(f.family()==WeaponFamily.SCYTHE&&ranks.rank("A0040")>0&&state.claimOnce(f.actorId(),f.rootActionId(),"A0040:mark",f.nowMillis()))state.applyReapingMark(f.actorId(),f.targetId(),ranks.rank("A0040"),f.targetHealthFraction(),f.nowMillis());
        return new AfterResult(sunderCommitted,bonebreakerCommitted);
    }
    public static void onConfirmedDodge(String actorId,CombatPerkRanks ranks,A0021A0040CombatState state,long now){if(ranks.rank("A0022")>0||ranks.learned("A0024"))state.armDodgeReposition(actorId,now);}
    public static void onConfirmedHeavyStagger(String actorId,CombatPerkRanks ranks,A0021A0040CombatState state,long now){if(ranks.rank("A0022")>0)state.loseFlow(actorId,2,now);}
    public static void onConfirmedGuardBreak(String actorId,String targetId,CombatPerkRanks ranks,A0021A0040CombatState state,int mastery,long now){if(ranks.learned("A0030")&&state.demolitionReady(actorId,targetId,now))state.armDemolition(actorId,targetId,mastery,now);}
    public static boolean fallbackRepositionEligible(double displacement,double angle,boolean teleported,boolean knockback){return !teleported&&!knockback&&displacement>=NotionCombatPerkRules.A0022_FALLBACK_MIN_DISPLACEMENT&&Math.abs(angle)>=NotionCombatPerkRules.A0022_FALLBACK_MIN_ANGLE_DEGREES;}
}
