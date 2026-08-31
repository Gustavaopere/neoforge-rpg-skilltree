package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient state for A0021-A0040. Nothing here is persisted. */
public final class A0021A0040CombatState {
    private static final double FORCED_REPOSITION_MOTION_EPSILON_SQUARED = 1.0E-4D;
    private static final int FORCED_REPOSITION_RELEASE_QUIET_TICKS = 3;
    private static final long PREPARED_COMMIT_TTL_MILLIS = 30_000L;

    public enum PreparedDaggerCommit { NONE, BLIND_SPOT, DANCE_ACTIVATE, DANCE_HIT }
    public enum PreparedHammerCommit { NONE, POSTURE_BREAK, DEMOLITION }

    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<String, Long> claims = new HashMap<>();

    public synchronized boolean claimOnce(String actorId,String rootActionId,String consumer,long now){
        require(actorId);require(rootActionId);require(consumer);claims.entrySet().removeIf(e->e.getValue()<=now);
        String key=actorId+'\0'+rootActionId+'\0'+consumer;if(claims.containsKey(key))return false;claims.put(key,Math.addExact(now,30_000L));return true;
    }

    public synchronized int flow(String actorId,long now){Actor a=actor(actorId);if(a.flow>0&&a.flowExpiresAt<=now){a.flow=0;a.flowExpiresAt=0;a.nextIdleDecayAt=0;}return a.flow;}
    public synchronized int addFlow(String actorId,int rank,long now){Actor a=actor(actorId);flow(actorId,now);a.flow=Math.min(NotionCombatPerkRules.FLOW_CAP,a.flow+1);a.flowExpiresAt=Math.addExact(now,NotionCombatPerkRules.flowDurationMillis(rank));return a.flow;}
    public synchronized int consumeFlow(String actorId,int amount,long now){Actor a=actor(actorId);flow(actorId,now);int used=Math.min(Math.max(amount,0),a.flow);a.flow-=used;if(a.flow==0){a.flowExpiresAt=0;a.nextIdleDecayAt=0;}return used;}
    public synchronized void loseFlow(String actorId,int amount,long now){consumeFlow(actorId,amount,now);}
    public synchronized void recordHorizontalMovement(String actorId,long now){Actor a=actor(actorId);a.nextIdleDecayAt=Math.addExact(now,NotionCombatPerkRules.A0022_IDLE_BEFORE_DECAY_MILLIS);}

    /** Flow visible to PRE after still-uncommitted A0023/A0024 reservations. */
    public synchronized int availableFlow(String actorId,long now){
        Actor a=actor(actorId);int current=flow(actorId,now);prunePreparedDaggerActions(a,now);int reserved=0;
        for(PreparedDaggerAction prepared:a.preparedDaggerActions.values()){
            if(prepared.kind==PreparedDaggerCommit.BLIND_SPOT)reserved+=NotionCombatPerkRules.A0023_FLOW_COST;
            else if(prepared.kind==PreparedDaggerCommit.DANCE_ACTIVATE)reserved+=4;
        }
        return Math.max(0,current-reserved);
    }

    /** A0022 idle decay is a property of Flow itself; a current lock-on/combat target is not required. */
    public synchronized void tickFlow(String actorId,boolean ignoredInCombat,long now){tickFlow(actorId,now);}
    public synchronized void tickFlow(String actorId,long now){Actor a=actor(actorId);if(flow(actorId,now)<=0)return;if(a.nextIdleDecayAt==0L)a.nextIdleDecayAt=Math.addExact(now,NotionCombatPerkRules.A0022_IDLE_BEFORE_DECAY_MILLIS);if(now<a.nextIdleDecayAt)return;long steps=1L+(now-a.nextIdleDecayAt)/1_000L;loseFlow(actorId,(int)Math.min(steps,Integer.MAX_VALUE),now);if(a.flow>0)a.nextIdleDecayAt=Math.addExact(a.nextIdleDecayAt,Math.multiplyExact(steps,1_000L));}
    public synchronized boolean blindSpotReady(String actorId,String target,long now){return actor(actorId).blindSpotCooldown.getOrDefault(require(target),0L)<=now;}
    public synchronized void startBlindSpotCooldown(String actorId,String target,long now){actor(actorId).blindSpotCooldown.put(require(target),Math.addExact(now,NotionCombatPerkRules.A0023_TARGET_COOLDOWN_MILLIS));}

    public synchronized boolean prepareBlindSpotCommit(String actorId,String targetId,String rootActionId,long now){
        Actor a=actor(actorId);prunePreparedDaggerActions(a,now);String target=require(targetId),root=require(rootActionId);
        PreparedDaggerAction existing=a.preparedDaggerActions.get(root);
        if(existing!=null)return existing.kind==PreparedDaggerCommit.BLIND_SPOT&&existing.targetId.equals(target);
        if(availableFlow(actorId,now)<NotionCombatPerkRules.A0023_FLOW_COST||!blindSpotReady(actorId,target,now))return false;
        if(a.preparedDaggerActions.values().stream().anyMatch(p->p.kind==PreparedDaggerCommit.BLIND_SPOT&&p.targetId.equals(target)))return false;
        a.preparedDaggerActions.put(root,new PreparedDaggerAction(PreparedDaggerCommit.BLIND_SPOT,target,0,false,Math.addExact(now,PREPARED_COMMIT_TTL_MILLIS)));
        return true;
    }

    public synchronized void armDodgeReposition(String actorId,long now){Actor a=actor(actorId);a.dodgeRepositionUntil=Math.addExact(now,NotionCombatPerkRules.A0022_REPOSITION_WINDOW_MILLIS);a.dodgeDanceActivationUntil=Math.addExact(now,NotionCombatPerkRules.A0024_ACTIVATION_REPOSITION_WINDOW_MILLIS);}
    public synchronized boolean repositionActive(String actorId,long now){Actor a=actor(actorId);return a.dodgeRepositionUntil>now||a.fallbackRepositionUntil>now;}
    public synchronized boolean danceActivationEligible(String actorId,long now){Actor a=actor(actorId);return a.dodgeDanceActivationUntil>now||a.fallbackDanceActivationUntil>now;}
    public synchronized void activateDance(String actorId,int mastery,long now){Actor a=actor(actorId);a.dodgeDanceActivationUntil=0L;a.fallbackDanceActivationUntil=0L;a.danceUntil=Math.addExact(now,NotionCombatPerkRules.shadowDanceDurationMillis(mastery));a.danceMoveAvailable=true;a.danceHitAvailable=true;}
    public synchronized boolean consumeDanceMove(String actorId,long now){Actor a=actor(actorId);if(a.danceUntil<=now||!a.danceMoveAvailable)return false;a.danceMoveAvailable=false;return true;}
    public synchronized boolean consumeDanceHit(String actorId,long now){Actor a=actor(actorId);if(a.danceUntil<=now||!a.danceHitAvailable)return false;a.danceHitAvailable=false;return true;}

    public synchronized boolean prepareDanceActivationCommit(String actorId,String targetId,String rootActionId,int mastery,boolean consumeFirstHit,long now){
        Actor a=actor(actorId);prunePreparedDaggerActions(a,now);String target=require(targetId),root=require(rootActionId);
        PreparedDaggerAction existing=a.preparedDaggerActions.get(root);
        if(existing!=null)return existing.kind==PreparedDaggerCommit.DANCE_ACTIVATE&&existing.targetId.equals(target);
        if(availableFlow(actorId,now)<4||!danceActivationEligible(actorId,now))return false;
        if(a.preparedDaggerActions.values().stream().anyMatch(p->p.kind==PreparedDaggerCommit.DANCE_ACTIVATE))return false;
        a.preparedDaggerActions.put(root,new PreparedDaggerAction(PreparedDaggerCommit.DANCE_ACTIVATE,target,mastery,consumeFirstHit,Math.addExact(now,PREPARED_COMMIT_TTL_MILLIS)));
        return true;
    }

    public synchronized boolean prepareDanceHitCommit(String actorId,String targetId,String rootActionId,long now){
        Actor a=actor(actorId);prunePreparedDaggerActions(a,now);String target=require(targetId),root=require(rootActionId);
        PreparedDaggerAction existing=a.preparedDaggerActions.get(root);
        if(existing!=null)return existing.kind==PreparedDaggerCommit.DANCE_HIT&&existing.targetId.equals(target);
        if(a.danceUntil<=now||!a.danceHitAvailable)return false;
        if(a.preparedDaggerActions.values().stream().anyMatch(p->p.kind==PreparedDaggerCommit.DANCE_HIT||(p.kind==PreparedDaggerCommit.DANCE_ACTIVATE&&p.consumeFirstHit)))return false;
        a.preparedDaggerActions.put(root,new PreparedDaggerAction(PreparedDaggerCommit.DANCE_HIT,target,0,true,Math.addExact(now,PREPARED_COMMIT_TTL_MILLIS)));
        return true;
    }

    public synchronized PreparedDaggerCommit commitPreparedDaggerAction(String actorId,String targetId,String rootActionId,long now){
        Actor a=actor(actorId);prunePreparedDaggerActions(a,now);PreparedDaggerAction prepared=a.preparedDaggerActions.remove(require(rootActionId));
        if(prepared==null||!prepared.targetId.equals(require(targetId)))return PreparedDaggerCommit.NONE;
        if(prepared.kind==PreparedDaggerCommit.BLIND_SPOT){
            if(!blindSpotReady(actorId,targetId,now)||consumeFlow(actorId,NotionCombatPerkRules.A0023_FLOW_COST,now)!=NotionCombatPerkRules.A0023_FLOW_COST)return PreparedDaggerCommit.NONE;
            startBlindSpotCooldown(actorId,targetId,now);return PreparedDaggerCommit.BLIND_SPOT;
        }
        if(prepared.kind==PreparedDaggerCommit.DANCE_ACTIVATE){
            if(consumeFlow(actorId,4,now)!=4)return PreparedDaggerCommit.NONE;
            activateDance(actorId,prepared.mastery,now);
            if(prepared.consumeFirstHit)a.danceHitAvailable=false;
            return PreparedDaggerCommit.DANCE_ACTIVATE;
        }
        if(prepared.kind==PreparedDaggerCommit.DANCE_HIT){
            if(!a.danceHitAvailable)return PreparedDaggerCommit.NONE;
            a.danceHitAvailable=false;return PreparedDaggerCommit.DANCE_HIT;
        }
        return PreparedDaggerCommit.NONE;
    }

    public synchronized void discardPreparedDaggerAction(String actorId,String rootActionId){actor(actorId).preparedDaggerActions.remove(require(rootActionId));}

    /**
     * Samples the approved A0022 geometry on server positions. The angular term compares the
     * target-to-player horizontal vector at the baseline and current sample, so camera rotation
     * alone cannot satisfy it. Teleports invalidate the route immediately; knockback suppresses
     * sampling until forced horizontal motion has remained quiet for three consecutive ticks.
     */
    public synchronized boolean sampleFallbackReposition(
        String actorId,
        String targetId,
        double playerX,
        double playerZ,
        double targetX,
        double targetZ,
        long now
    ) {
        Actor a=actor(actorId);String target=require(targetId);
        if(a.fallbackRepositionSuppressed){
            clearFallbackReposition(a);
            return false;
        }
        RepositionSample sample=a.repositionSample;
        if(sample==null||!sample.targetId.equals(target)){
            a.repositionSample=new RepositionSample(target,playerX,playerZ,targetX,targetZ);
            return false;
        }
        double dx=playerX-sample.playerX,dz=playerZ-sample.playerZ;
        double displacement=Math.sqrt(dx*dx+dz*dz);
        double ax=sample.playerX-sample.targetX,az=sample.playerZ-sample.targetZ;
        double bx=playerX-targetX,bz=playerZ-targetZ;
        double al=Math.sqrt(ax*ax+az*az),bl=Math.sqrt(bx*bx+bz*bz);
        double angle=0.0D;
        if(al>1.0E-9D&&bl>1.0E-9D){double cos=Math.max(-1.0D,Math.min(1.0D,(ax*bx+az*bz)/(al*bl)));angle=Math.toDegrees(Math.acos(cos));}
        if(!A0021A0040CombatPolicy.fallbackRepositionEligible(displacement,angle,false,false))return false;
        a.fallbackRepositionUntil=Math.addExact(now,NotionCombatPerkRules.A0022_REPOSITION_WINDOW_MILLIS);
        a.fallbackDanceActivationUntil=Math.addExact(now,NotionCombatPerkRules.A0024_ACTIVATION_REPOSITION_WINDOW_MILLIS);
        a.repositionSample=new RepositionSample(target,playerX,playerZ,targetX,targetZ);
        return true;
    }

    public synchronized void invalidateFallbackReposition(String actorId){clearFallbackReposition(actor(actorId));}

    /** Starts a conservative exclusion window for knockback/other explicitly forced displacement. */
    public synchronized void beginForcedRepositionSuppression(String actorId){
        Actor a=actor(actorId);
        a.fallbackRepositionSuppressed=true;
        a.forcedRepositionQuietTicks=0;
        clearFallbackReposition(a);
    }

    public synchronized boolean fallbackRepositionSuppressed(String actorId){
        Actor a=actors.get(require(actorId));
        return a!=null&&a.fallbackRepositionSuppressed;
    }

    /**
     * Returns whether forced-motion suppression remains active after this server tick. A release
     * requires three consecutive ticks with negligible horizontal velocity; any renewed movement
     * resets the quiet counter. No geometric baseline survives the suppression window.
     */
    public synchronized boolean updateForcedRepositionSuppression(String actorId,double horizontalMotionSquared){
        Actor a=actors.get(require(actorId));
        if(a==null||!a.fallbackRepositionSuppressed)return false;
        clearFallbackReposition(a);
        if(!Double.isFinite(horizontalMotionSquared)||horizontalMotionSquared>FORCED_REPOSITION_MOTION_EPSILON_SQUARED){
            a.forcedRepositionQuietTicks=0;
            return true;
        }
        a.forcedRepositionQuietTicks++;
        if(a.forcedRepositionQuietTicks<FORCED_REPOSITION_RELEASE_QUIET_TICKS)return true;
        a.fallbackRepositionSuppressed=false;
        a.forcedRepositionQuietTicks=0;
        return false;
    }

    public synchronized int abalo(String actorId,String target,long now){TargetStack s=actor(actorId).abalo.get(require(target));if(s==null)return 0;if(s.expiresAt<=now){actor(actorId).abalo.remove(target);return 0;}return s.count;}
    public synchronized int addAbalo(String actorId,String target,long now){Actor a=actor(actorId);String t=require(target);int count=Math.min(NotionCombatPerkRules.ABALO_CAP,abalo(actorId,t,now)+1);a.abalo.put(t,new TargetStack(count,Math.addExact(now,NotionCombatPerkRules.ABALO_DURATION_MILLIS)));return count;}
    public synchronized int consumeAbalo(String actorId,String target,int amount,long now){Actor a=actor(actorId);String t=require(target);TargetStack old=a.abalo.get(t);int current=abalo(actorId,t,now),used=Math.min(Math.max(amount,0),current),left=current-used;if(left==0)a.abalo.remove(t);else a.abalo.put(t,new TargetStack(left,old==null?Math.addExact(now,NotionCombatPerkRules.ABALO_DURATION_MILLIS):old.expiresAt));return used;}
    public synchronized int availableAbalo(String actorId,String targetId,long now){
        Actor a=actor(actorId);String target=require(targetId);int current=abalo(actorId,target,now);prunePreparedHammerActions(a,now);int reserved=0;
        for(PreparedHammerAction prepared:a.preparedHammerActions.values())if(prepared.kind==PreparedHammerCommit.POSTURE_BREAK&&prepared.targetId.equals(target))reserved+=3;
        return Math.max(0,current-reserved);
    }
    public synchronized boolean demolitionReady(String actorId,String target,long now){return actor(actorId).demolitionCooldown.getOrDefault(require(target),0L)<=now;}
    public synchronized void armDemolition(String actorId,String target,int mastery,long now){Actor a=actor(actorId);String t=require(target);if(!demolitionReady(actorId,t,now))return;a.demolitionWindow.put(t,Math.addExact(now,4_000L));a.demolitionCooldown.put(t,Math.addExact(now,NotionCombatPerkRules.demolitionCooldownMillis(mastery)));}
    public synchronized boolean demolitionActive(String actorId,String targetId,long now){Actor a=actor(actorId);String target=require(targetId);Long until=a.demolitionWindow.get(target);if(until==null)return false;if(until<=now){a.demolitionWindow.remove(target);return false;}return true;}
    public synchronized boolean consumeDemolition(String actorId,String target,long now){Long until=actor(actorId).demolitionWindow.remove(require(target));return until!=null&&until>now;}

    public synchronized boolean preparePostureBreakCommit(String actorId,String targetId,String rootActionId,long now){
        Actor a=actor(actorId);prunePreparedHammerActions(a,now);String target=require(targetId),root=require(rootActionId);
        PreparedHammerAction existing=a.preparedHammerActions.get(root);
        if(existing!=null)return existing.kind==PreparedHammerCommit.POSTURE_BREAK&&existing.targetId.equals(target);
        if(availableAbalo(actorId,target,now)<3)return false;
        if(a.preparedHammerActions.values().stream().anyMatch(p->p.kind==PreparedHammerCommit.POSTURE_BREAK&&p.targetId.equals(target)))return false;
        a.preparedHammerActions.put(root,new PreparedHammerAction(PreparedHammerCommit.POSTURE_BREAK,target,Math.addExact(now,PREPARED_COMMIT_TTL_MILLIS)));
        return true;
    }

    public synchronized boolean prepareDemolitionCommit(String actorId,String targetId,String rootActionId,long now){
        Actor a=actor(actorId);prunePreparedHammerActions(a,now);String target=require(targetId),root=require(rootActionId);
        PreparedHammerAction existing=a.preparedHammerActions.get(root);
        if(existing!=null)return existing.kind==PreparedHammerCommit.DEMOLITION&&existing.targetId.equals(target);
        if(!demolitionActive(actorId,target,now))return false;
        if(a.preparedHammerActions.values().stream().anyMatch(p->p.kind==PreparedHammerCommit.DEMOLITION&&p.targetId.equals(target)))return false;
        a.preparedHammerActions.put(root,new PreparedHammerAction(PreparedHammerCommit.DEMOLITION,target,Math.addExact(now,PREPARED_COMMIT_TTL_MILLIS)));
        return true;
    }

    public synchronized PreparedHammerCommit commitPreparedHammerAction(String actorId,String targetId,String rootActionId,long now){
        Actor a=actor(actorId);prunePreparedHammerActions(a,now);PreparedHammerAction prepared=a.preparedHammerActions.remove(require(rootActionId));
        if(prepared==null||!prepared.targetId.equals(require(targetId)))return PreparedHammerCommit.NONE;
        if(prepared.kind==PreparedHammerCommit.POSTURE_BREAK){
            return consumeAbalo(actorId,targetId,3,now)==3?PreparedHammerCommit.POSTURE_BREAK:PreparedHammerCommit.NONE;
        }
        if(prepared.kind==PreparedHammerCommit.DEMOLITION){
            return a.demolitionWindow.remove(require(targetId))!=null?PreparedHammerCommit.DEMOLITION:PreparedHammerCommit.NONE;
        }
        return PreparedHammerCommit.NONE;
    }

    public synchronized void discardPreparedHammerAction(String actorId,String rootActionId){actor(actorId).preparedHammerActions.remove(require(rootActionId));}

    public synchronized int trauma(String actorId,String target,long now){TargetStack s=actor(actorId).trauma.get(require(target));if(s==null)return 0;if(s.expiresAt<=now){actor(actorId).trauma.remove(target);return 0;}return s.count;}
    public synchronized int addTrauma(String actorId,String target,int rank,long now){Actor a=actor(actorId);String t=require(target);int count=Math.min(NotionCombatPerkRules.TRAUMA_CAP,trauma(actorId,t,now)+1);a.trauma.put(t,new TargetStack(count,Math.addExact(now,NotionCombatPerkRules.traumaDurationMillis(rank))));return count;}
    public synchronized int consumeTrauma(String actorId,String target,int amount,long now){Actor a=actor(actorId);String t=require(target);TargetStack old=a.trauma.get(t);int current=trauma(actorId,t,now),used=Math.min(Math.max(amount,0),current),left=current-used;if(left==0)a.trauma.remove(t);else a.trauma.put(t,new TargetStack(left,old==null?now+1:old.expiresAt));return used;}
    public synchronized void markSundered(String actorId,String target,int rank,long now){actor(actorId).sunderedUntil.put(require(target),Math.addExact(now,NotionCombatPerkRules.sunderDurationMillis(rank)));}
    public synchronized boolean isSundered(String actorId,String target,long now){return actor(actorId).sunderedUntil.getOrDefault(require(target),0L)>now;}
    public synchronized boolean bonebreakerReady(String actorId,String target,long now){return actor(actorId).bonebreakerCooldown.getOrDefault(require(target),0L)<=now;}
    public synchronized void startBonebreakerCooldown(String actorId,String target,int mastery,long now){actor(actorId).bonebreakerCooldown.put(require(target),Math.addExact(now,NotionCombatPerkRules.bonebreakerCooldownMillis(mastery)));}

    /** New marks start immature even when first applied below 50%; only a >=50 -> <50 crossing matures them. */
    public synchronized void applyReapingMark(String actorId,String target,int rank,double healthFraction,long now){Actor a=actor(actorId);String t=require(target);ReapMark old=a.reapMarks.get(t);boolean active=old!=null&&old.expiresAt>now;boolean mature=active&&old.mature;double previous=active?old.lastHealthFraction:healthFraction;if(active&&!mature&&previous>=NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION&&healthFraction<NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION)mature=true;a.reapMarks.put(t,new ReapMark(Math.addExact(now,NotionCombatPerkRules.reapingMarkDurationMillis(rank)),mature,healthFraction));}
    public synchronized boolean reapMarked(String actorId,String target,long now){ReapMark m=actor(actorId).reapMarks.get(require(target));if(m==null)return false;if(m.expiresAt<=now){actor(actorId).reapMarks.remove(target);return false;}return true;}
    public synchronized boolean reapMature(String actorId,String target,double healthFraction,long now){Actor a=actor(actorId);String t=require(target);ReapMark m=a.reapMarks.get(t);if(m==null||m.expiresAt<=now){a.reapMarks.remove(t);return false;}boolean mature=m.mature||m.lastHealthFraction>=NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION&&healthFraction<NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION;a.reapMarks.put(t,new ReapMark(m.expiresAt,mature,healthFraction));return mature;}
    public synchronized boolean consumeMatureReap(String actorId,String target,double healthFraction,long now){Actor a=actor(actorId);String t=require(target);if(!reapMature(actorId,t,healthFraction,now))return false;a.reapMarks.remove(t);return true;}
    public synchronized void updateReapingMaturityForTarget(String target,double healthFraction,long now){String t=require(target);for(Actor a:actors.values()){ReapMark m=a.reapMarks.get(t);if(m==null)continue;if(m.expiresAt<=now){a.reapMarks.remove(t);continue;}boolean mature=m.mature||m.lastHealthFraction>=NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION&&healthFraction<NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION;a.reapMarks.put(t,new ReapMark(m.expiresAt,mature,healthFraction));}}

    public synchronized void clearTarget(String targetId){
        String t=require(targetId);
        for(Actor a:actors.values()){
            a.abalo.remove(t);a.demolitionWindow.remove(t);a.demolitionCooldown.remove(t);a.trauma.remove(t);a.sunderedUntil.remove(t);a.bonebreakerCooldown.remove(t);a.reapMarks.remove(t);a.blindSpotCooldown.remove(t);
            a.preparedDaggerActions.entrySet().removeIf(e->e.getValue().targetId.equals(t));
            a.preparedHammerActions.entrySet().removeIf(e->e.getValue().targetId.equals(t));
            if(a.repositionSample!=null&&a.repositionSample.targetId.equals(t))a.repositionSample=null;
        }
    }
    public synchronized void clearActor(String actorId){actors.remove(require(actorId));String prefix=actorId+'\0';claims.keySet().removeIf(k->k.startsWith(prefix));}
    public synchronized void clearAll(){actors.clear();claims.clear();}

    private static void prunePreparedDaggerActions(Actor a,long now){a.preparedDaggerActions.entrySet().removeIf(e->e.getValue().expiresAt<=now);}
    private static void prunePreparedHammerActions(Actor a,long now){a.preparedHammerActions.entrySet().removeIf(e->e.getValue().expiresAt<=now);}
    private static void clearFallbackReposition(Actor a){a.repositionSample=null;a.fallbackRepositionUntil=0L;a.fallbackDanceActivationUntil=0L;}
    private Actor actor(String id){return actors.computeIfAbsent(require(id),k->new Actor());}
    private static String require(String s){Objects.requireNonNull(s);if(s.isBlank())throw new IllegalArgumentException("blank id");return s;}
    private record TargetStack(int count,long expiresAt){}
    private record ReapMark(long expiresAt,boolean mature,double lastHealthFraction){}
    private record RepositionSample(String targetId,double playerX,double playerZ,double targetX,double targetZ){}
    private record PreparedDaggerAction(PreparedDaggerCommit kind,String targetId,int mastery,boolean consumeFirstHit,long expiresAt){}
    private record PreparedHammerAction(PreparedHammerCommit kind,String targetId,long expiresAt){}
    private static final class Actor{
        int flow;long flowExpiresAt,nextIdleDecayAt,dodgeRepositionUntil,fallbackRepositionUntil,dodgeDanceActivationUntil,fallbackDanceActivationUntil,danceUntil;boolean danceMoveAvailable,danceHitAvailable,fallbackRepositionSuppressed;int forcedRepositionQuietTicks;RepositionSample repositionSample;
        final Map<String,Long> blindSpotCooldown=new HashMap<>(),demolitionWindow=new HashMap<>(),demolitionCooldown=new HashMap<>(),sunderedUntil=new HashMap<>(),bonebreakerCooldown=new HashMap<>();
        final Map<String,TargetStack> abalo=new HashMap<>(),trauma=new HashMap<>();final Map<String,ReapMark> reapMarks=new HashMap<>();
        final Map<String,PreparedDaggerAction> preparedDaggerActions=new HashMap<>();
        final Map<String,PreparedHammerAction> preparedHammerActions=new HashMap<>();
    }
}
