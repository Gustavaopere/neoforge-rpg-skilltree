package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** A0087 hostile-loss trigger. Mandatory heat+exhaustion tradeoffs are provider-owned and fail closed. */
public final class BloodThirstService {
    private static final long LOSS_WINDOW_TICKS=120L;
    private static final long ACTIVE_TICKS=120L;
    private static final long COOLDOWN_TICKS=900L;
    private final BodyProvider body;
    private final Map<String,ArrayDeque<DamageSample>> damage=new HashMap<>();
    private final Map<String,Lease> leases=new HashMap<>();

    public BloodThirstService(BodyProvider body){ this.body=body; }

    public synchronized boolean recordHostileDamage(String actorId,double actualDamage,double maxHealth,boolean eligible,long nowTick){
        actorId=id(actorId); time(nowTick); finite(actualDamage,"actualDamage"); finite(maxHealth,"maxHealth"); if(maxHealth<=0)throw new IllegalArgumentException("maxHealth");
        if(!eligible||actualDamage<=0.0D||body==null) return false;
        Lease existing=leases.get(actorId); if(existing!=null&&nowTick<existing.cooldownUntil()) return false;
        ArrayDeque<DamageSample> q=damage.computeIfAbsent(actorId,k->new ArrayDeque<>());
        while(!q.isEmpty()&&nowTick-q.peekFirst().tick()>LOSS_WINDOW_TICKS)q.removeFirst();
        q.addLast(new DamageSample(nowTick,actualDamage));
        double total=q.stream().mapToDouble(DamageSample::amount).sum();
        if(total<maxHealth*0.25D) return false;
        if(!body.acquire(actorId,0.20D,0.15D,ACTIVE_TICKS,COOLDOWN_TICKS)) return false;
        q.clear(); leases.put(actorId,new Lease(nowTick+ACTIVE_TICKS,nowTick+COOLDOWN_TICKS)); return true;
    }

    public synchronized boolean active(String actorId,long nowTick){
        actorId=id(actorId); time(nowTick); Lease lease=leases.get(actorId); if(lease==null||nowTick>=lease.activeUntil()) return false;
        if(body==null||!body.maintain(actorId,0.20D,0.15D)){ bodyRelease(actorId); leases.put(actorId,new Lease(nowTick,lease.cooldownUntil())); return false; }
        return true;
    }
    public double weaponMinimumCoefficient(String actorId,long nowTick){ return active(actorId,nowTick)?0.03D:0.0D; }
    public double healingReceivedMultiplier(String actorId,long nowTick){ return active(actorId,nowTick)?1.08D:1.0D; }
    public synchronized void clearActor(String actorId){ actorId=id(actorId); damage.remove(actorId); bodyRelease(actorId); leases.remove(actorId); }
    public synchronized void clearAll(){ if(body!=null) for(String actor:leases.keySet()) body.release(actor); damage.clear(); leases.clear(); }
    private void bodyRelease(String actor){ if(body!=null) body.release(actor); }

    /** Hydration is deliberately absent here: it is an optional separate causal receipt, never inferred from exhaustion. */
    public interface BodyProvider {
        boolean acquire(String actorId,double heatContribution,double exhaustionContribution,long durationTicks,long cooldownTicks);
        boolean maintain(String actorId,double heatContribution,double exhaustionContribution);
        void release(String actorId);
    }
    private record DamageSample(long tick,double amount){}
    private record Lease(long activeUntil,long cooldownUntil){}
    private static String id(String s){Objects.requireNonNull(s);if(s.isBlank())throw new IllegalArgumentException("actorId");return s;}
    private static void time(long t){if(t<0)throw new IllegalArgumentException("nowTick");}
    private static void finite(double v,String n){if(!Double.isFinite(v)||v<0)throw new IllegalArgumentException(n);}
}
