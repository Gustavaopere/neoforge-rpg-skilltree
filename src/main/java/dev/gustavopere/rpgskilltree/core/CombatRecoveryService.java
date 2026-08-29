package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/** A0081 delayed reserve; intentionally independent from SustainResolver. */
public final class CombatRecoveryService {
    private static final long HEAL_DELAY_MILLIS=3_000L;
    private static final long PARCEL_INTERVAL_MILLIS=1_000L;
    private static final long RESERVE_EXPIRY_MILLIS=10_000L;
    private final Set<String> claimedRoots=new HashSet<>();
    private final Map<String,ActorState> actors=new HashMap<>();
    private final AtomicLong tokens=new AtomicLong();

    public synchronized double recordDamage(DamageRequest r,long nowMillis){
        Objects.requireNonNull(r); time(nowMillis); validate(r);
        if(!r.serverAuthoritative()||!r.eligibleActor()||!r.directMelee()||!r.hostileTarget()||!r.rhythmActive()||r.rank()<=0) return 0.0D;
        if(!claimedRoots.add(r.actorId()+"\u0000"+r.rootActionId())) return 0.0D;
        ActorState s=actors.computeIfAbsent(r.actorId(),ignored->new ActorState()); expire(s,nowMillis);
        double rate=r.rank()==1?0.15D:r.rank()==2?0.20D:0.25D;
        double eligible=Math.min(r.postMitigationDamage(),r.targetHealthBefore());
        double capacity=r.maxHealth()*0.08D;
        double added=Math.min(eligible*rate,Math.max(0.0D,capacity-s.reserve));
        s.reserve+=added; s.lastCombatMillis=nowMillis;
        if(s.lastHostileDamageMillis<0L) s.lastHostileDamageMillis=nowMillis;
        s.phaseStarted=false; s.pending=null;
        return added;
    }

    public synchronized void recordHostileDamage(String actorId,boolean eligible,long nowMillis){
        time(nowMillis); if(!eligible) return; ActorState s=actors.computeIfAbsent(id(actorId),ignored->new ActorState());
        s.lastHostileDamageMillis=nowMillis; s.lastCombatMillis=nowMillis; s.phaseStarted=false; s.snapshot=0.0D; s.parcelsOffered=0; s.pending=null;
    }

    public synchronized Optional<Installment> offerInstallment(String actorId,double maxHealth,double missingHealth,long nowMillis){
        actorId=id(actorId); time(nowMillis); finite(maxHealth,"maxHealth"); finite(missingHealth,"missingHealth");
        ActorState s=actors.get(actorId); if(s==null) return Optional.empty(); expire(s,nowMillis);
        if(s.reserve<=0.0D||missingHealth<=0.0D||s.pending!=null||nowMillis-s.lastHostileDamageMillis<HEAL_DELAY_MILLIS) return Optional.empty();
        if(!s.phaseStarted){ s.phaseStarted=true; s.snapshot=s.reserve; s.parcelsOffered=0; s.nextParcelMillis=nowMillis; }
        if(s.parcelsOffered>=4||nowMillis<s.nextParcelMillis) return Optional.empty();
        double attempted=Math.min(Math.min(s.snapshot*0.25D,s.reserve),missingHealth); if(attempted<=0.0D) return Optional.empty();
        Installment i=new Installment(actorId,tokens.incrementAndGet(),attempted); s.pending=i; s.parcelsOffered++; s.nextParcelMillis=nowMillis+PARCEL_INTERVAL_MILLIS; return Optional.of(i);
    }

    public synchronized boolean confirmHealed(Installment i,double actual){
        Objects.requireNonNull(i); finite(actual,"actualHealing"); if(actual>i.attemptedHealing()) throw new IllegalArgumentException("actualHealing");
        ActorState s=actors.get(i.actorId()); if(s==null||s.pending==null||s.pending.token()!=i.token()) return false;
        s.reserve=Math.max(0.0D,s.reserve-actual); s.pending=null; return true;
    }
    public synchronized double reserve(String actorId,long nowMillis){ ActorState s=actors.get(id(actorId)); time(nowMillis); if(s==null)return 0.0D; expire(s,nowMillis); return s.reserve; }
    public synchronized void clearActor(String actorId){ actorId=id(actorId); actors.remove(actorId); String prefix=actorId+"\u0000"; claimedRoots.removeIf(k->k.startsWith(prefix)); }
    public synchronized void clearAll(){ actors.clear(); claimedRoots.clear(); }

    private static void expire(ActorState s,long now){ if(s.lastCombatMillis>=0L&&now-s.lastCombatMillis>RESERVE_EXPIRY_MILLIS){ s.reserve=0; s.snapshot=0; s.phaseStarted=false; s.parcelsOffered=0; s.pending=null; } }
    private static void validate(DamageRequest r){ id(r.actorId()); id(r.rootActionId()); finite(r.maxHealth(),"maxHealth"); finite(r.postMitigationDamage(),"postMitigationDamage"); finite(r.targetHealthBefore(),"targetHealthBefore"); if(r.maxHealth()<=0||r.rank()>3) throw new IllegalArgumentException("request"); }
    private static String id(String s){ Objects.requireNonNull(s); if(s.isBlank())throw new IllegalArgumentException("id"); return s; }
    private static void time(long t){ if(t<0L)throw new IllegalArgumentException("time"); }
    private static void finite(double v,String n){ if(!Double.isFinite(v)||v<0)throw new IllegalArgumentException(n); }

    public record DamageRequest(String actorId,String rootActionId,boolean serverAuthoritative,boolean eligibleActor,boolean directMelee,boolean hostileTarget,boolean rhythmActive,double maxHealth,double postMitigationDamage,double targetHealthBefore,int rank){}
    public record Installment(String actorId,long token,double attemptedHealing){}
    private static final class ActorState{ double reserve,snapshot; long lastHostileDamageMillis=-1,lastCombatMillis=-1,nextParcelMillis; int parcelsOffered; boolean phaseStarted; Installment pending; }
}
