package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Server-authoritative transient state for A0021-A0040. Nothing here is persisted. */
public final class A0021A0040CombatState {
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
    public synchronized void recordHorizontalMovement(String actorId,long now){Actor a=actor(actorId);a.lastHorizontalMovementAt=now;a.nextIdleDecayAt=Math.addExact(now,NotionCombatPerkRules.A0022_IDLE_BEFORE_DECAY_MILLIS);}
    public synchronized void tickFlow(String actorId,boolean inCombat,long now){Actor a=actor(actorId);if(flow(actorId,now)<=0||!inCombat)return;if(a.nextIdleDecayAt==0L)a.nextIdleDecayAt=Math.addExact(now,NotionCombatPerkRules.A0022_IDLE_BEFORE_DECAY_MILLIS);if(now<a.nextIdleDecayAt)return;long steps=1L+(now-a.nextIdleDecayAt)/1_000L;loseFlow(actorId,(int)Math.min(steps,Integer.MAX_VALUE),now);if(a.flow>0)a.nextIdleDecayAt=Math.addExact(a.nextIdleDecayAt,Math.multiplyExact(steps,1_000L));}
    public synchronized boolean blindSpotReady(String actorId,String target,long now){return actor(actorId).blindSpotCooldown.getOrDefault(require(target),0L)<=now;}
    public synchronized void startBlindSpotCooldown(String actorId,String target,long now){actor(actorId).blindSpotCooldown.put(require(target),Math.addExact(now,NotionCombatPerkRules.A0023_TARGET_COOLDOWN_MILLIS));}

    public synchronized void armDodgeReposition(String actorId,long now){actor(actorId).repositionUntil=Math.addExact(now,NotionCombatPerkRules.A0022_REPOSITION_WINDOW_MILLIS);}
    public synchronized boolean repositionActive(String actorId,long now){return actor(actorId).repositionUntil>now;}
    public synchronized void activateDance(String actorId,int mastery,long now){Actor a=actor(actorId);a.danceUntil=Math.addExact(now,NotionCombatPerkRules.shadowDanceDurationMillis(mastery));a.danceMoveAvailable=true;a.danceHitAvailable=true;}
    public synchronized boolean consumeDanceMove(String actorId,long now){Actor a=actor(actorId);if(a.danceUntil<=now||!a.danceMoveAvailable)return false;a.danceMoveAvailable=false;return true;}
    public synchronized boolean consumeDanceHit(String actorId,long now){Actor a=actor(actorId);if(a.danceUntil<=now||!a.danceHitAvailable)return false;a.danceHitAvailable=false;return true;}

    public synchronized int abalo(String actorId,String target,long now){TargetStack s=actor(actorId).abalo.get(require(target));if(s==null)return 0;if(s.expiresAt<=now){actor(actorId).abalo.remove(target);return 0;}return s.count;}
    public synchronized int addAbalo(String actorId,String target,long now){Actor a=actor(actorId);String t=require(target);int count=Math.min(NotionCombatPerkRules.ABALO_CAP,abalo(actorId,t,now)+1);a.abalo.put(t,new TargetStack(count,Math.addExact(now,NotionCombatPerkRules.ABALO_DURATION_MILLIS)));return count;}
    public synchronized int consumeAbalo(String actorId,String target,int amount,long now){Actor a=actor(actorId);String t=require(target);int current=abalo(actorId,t,now),used=Math.min(Math.max(amount,0),current),left=current-used;if(left==0)a.abalo.remove(t);else a.abalo.put(t,new TargetStack(left,Math.addExact(now,NotionCombatPerkRules.ABALO_DURATION_MILLIS)));return used;}
    public synchronized boolean demolitionReady(String actorId,String target,long now){return actor(actorId).demolitionCooldown.getOrDefault(require(target),0L)<=now;}
    public synchronized void armDemolition(String actorId,String target,int mastery,long now){Actor a=actor(actorId);String t=require(target);if(!demolitionReady(actorId,t,now))return;a.demolitionWindow.put(t,Math.addExact(now,4_000L));a.demolitionCooldown.put(t,Math.addExact(now,NotionCombatPerkRules.demolitionCooldownMillis(mastery)));}
    public synchronized boolean consumeDemolition(String actorId,String target,long now){Long until=actor(actorId).demolitionWindow.remove(require(target));return until!=null&&until>now;}

    public synchronized int trauma(String actorId,String target,long now){TargetStack s=actor(actorId).trauma.get(require(target));if(s==null)return 0;if(s.expiresAt<=now){actor(actorId).trauma.remove(target);return 0;}return s.count;}
    public synchronized int addTrauma(String actorId,String target,int rank,long now){Actor a=actor(actorId);String t=require(target);int count=Math.min(NotionCombatPerkRules.TRAUMA_CAP,trauma(actorId,t,now)+1);a.trauma.put(t,new TargetStack(count,Math.addExact(now,NotionCombatPerkRules.traumaDurationMillis(rank))));return count;}
    public synchronized int consumeTrauma(String actorId,String target,int amount,long now){Actor a=actor(actorId);String t=require(target);int current=trauma(actorId,t,now),used=Math.min(Math.max(amount,0),current),left=current-used;if(left==0)a.trauma.remove(t);else a.trauma.put(t,new TargetStack(left,now+1));return used;}
    public synchronized void markSundered(String actorId,String target,int rank,long now){actor(actorId).sunderedUntil.put(require(target),Math.addExact(now,NotionCombatPerkRules.sunderDurationMillis(rank)));}
    public synchronized boolean isSundered(String actorId,String target,long now){return actor(actorId).sunderedUntil.getOrDefault(require(target),0L)>now;}
    public synchronized boolean bonebreakerReady(String actorId,String target,long now){return actor(actorId).bonebreakerCooldown.getOrDefault(require(target),0L)<=now;}
    public synchronized void startBonebreakerCooldown(String actorId,String target,int mastery,long now){actor(actorId).bonebreakerCooldown.put(require(target),Math.addExact(now,NotionCombatPerkRules.bonebreakerCooldownMillis(mastery)));}

    public synchronized void applyReapingMark(String actorId,String target,int rank,double healthFraction,long now){Actor a=actor(actorId);String t=require(target);boolean mature=healthFraction<NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION;ReapMark old=a.reapMarks.get(t);if(old!=null&&old.expiresAt>now)mature|=old.mature;a.reapMarks.put(t,new ReapMark(Math.addExact(now,NotionCombatPerkRules.reapingMarkDurationMillis(rank)),mature));}
    public synchronized boolean reapMarked(String actorId,String target,long now){ReapMark m=actor(actorId).reapMarks.get(require(target));if(m==null)return false;if(m.expiresAt<=now){actor(actorId).reapMarks.remove(target);return false;}return true;}
    public synchronized boolean reapMature(String actorId,String target,double healthFraction,long now){Actor a=actor(actorId);String t=require(target);ReapMark m=a.reapMarks.get(t);if(m==null||m.expiresAt<=now){a.reapMarks.remove(t);return false;}if(!m.mature&&healthFraction<NotionCombatPerkRules.REAP_MATURE_HEALTH_FRACTION)a.reapMarks.put(t,new ReapMark(m.expiresAt,true));return a.reapMarks.get(t).mature;}

    public synchronized void clearTarget(String targetId){String t=require(targetId);for(Actor a:actors.values()){a.abalo.remove(t);a.demolitionWindow.remove(t);a.demolitionCooldown.remove(t);a.trauma.remove(t);a.sunderedUntil.remove(t);a.bonebreakerCooldown.remove(t);a.reapMarks.remove(t);a.blindSpotCooldown.remove(t);}}
    public synchronized void clearActor(String actorId){actors.remove(require(actorId));String prefix=actorId+'\0';claims.keySet().removeIf(k->k.startsWith(prefix));}
    public synchronized void clearAll(){actors.clear();claims.clear();}

    private Actor actor(String id){return actors.computeIfAbsent(require(id),k->new Actor());}
    private static String require(String s){Objects.requireNonNull(s);if(s.isBlank())throw new IllegalArgumentException("blank id");return s;}
    private record TargetStack(int count,long expiresAt){}
    private record ReapMark(long expiresAt,boolean mature){}
    private static final class Actor{
        int flow;long flowExpiresAt,lastHorizontalMovementAt,nextIdleDecayAt,repositionUntil,danceUntil;boolean danceMoveAvailable,danceHitAvailable;
        final Map<String,Long> blindSpotCooldown=new HashMap<>(),demolitionWindow=new HashMap<>(),demolitionCooldown=new HashMap<>(),sunderedUntil=new HashMap<>(),bonebreakerCooldown=new HashMap<>();
        final Map<String,TargetStack> abalo=new HashMap<>(),trauma=new HashMap<>();final Map<String,ReapMark> reapMarks=new HashMap<>();
    }
}
