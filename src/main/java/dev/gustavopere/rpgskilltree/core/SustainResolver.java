package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** A0082-A0086 canonical one-resolution sustain pipeline with a shared moving 20-tick cap. */
public final class SustainResolver {
    public static final long WINDOW_TICKS = 20L;
    public static final double CAP_FRACTION = 0.03D;
    private final Set<String> claimedRoots = new HashSet<>();
    private final Map<String, Deque<Payment>> payments = new HashMap<>();

    public synchronized Resolution resolve(Request request,long nowTick) {
        Objects.requireNonNull(request); validate(request,nowTick);
        if(!request.serverAuthoritative() || !request.eligibleActor() || !request.directOwnerProven()) return Resolution.ineligible();
        String claim = request.actorId()+"\u0000"+request.rootActionId();
        if(!claimedRoots.add(claim)) return Resolution.duplicate();
        if(request.nativeCorrelation()==NativeCorrelation.AMBIGUOUS) return Resolution.ambiguous();
        double coefficient=request.candidates().stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).filter(v->v>0.0D).max().orElse(0.0D);
        if(coefficient<=0.0D) return Resolution.noSource();

        Deque<Payment> bucket=payments.computeIfAbsent(request.actorId(),ignored->new ArrayDeque<>());
        prune(bucket,nowTick);
        double used=bucket.stream().mapToDouble(Payment::amount).sum();
        double cap=request.maxHealth()*CAP_FRACTION;
        double nativeCounted=request.nativeCorrelation()==NativeCorrelation.EXACT_INTERCEPTED
            ? Math.min(request.nativeFinalHealing(),Math.max(0.0D,cap-used)) : 0.0D;
        double eligibleDamage=Math.min(request.postMitigationDamage(),request.targetHealthBefore());
        double desired=eligibleDamage*coefficient*request.healingMultiplier();
        double skill=Math.min(Math.max(0.0D,desired-nativeCounted),Math.max(0.0D,cap-used-nativeCounted));
        skill=Math.min(skill,request.missingHealthAfterNative());
        double total=nativeCounted+skill;
        if(total>0.0D) bucket.addLast(new Payment(nowTick,total));
        return new Resolution(Status.AUTHORIZED,coefficient,nativeCounted,skill,used+total);
    }

    public synchronized double bucketUsed(String actorId,long nowTick){
        Objects.requireNonNull(actorId); if(actorId.isBlank()||nowTick<0L) throw new IllegalArgumentException();
        Deque<Payment> bucket=payments.get(actorId); if(bucket==null) return 0.0D; prune(bucket,nowTick);
        return bucket.stream().mapToDouble(Payment::amount).sum();
    }

    public synchronized void clearActor(String actorId){ Objects.requireNonNull(actorId); payments.remove(actorId); claimedRoots.removeIf(k->k.startsWith(actorId+"\u0000")); }
    public synchronized void clearAll(){ payments.clear(); claimedRoots.clear(); }

    private static void prune(Deque<Payment> bucket,long nowTick){ while(!bucket.isEmpty() && nowTick-bucket.peekFirst().tick()>=WINDOW_TICKS) bucket.removeFirst(); }
    private static void validate(Request r,long nowTick){
        if(nowTick<0L) throw new IllegalArgumentException("nowTick");
        if(r.actorId().isBlank()||r.rootActionId().isBlank()) throw new IllegalArgumentException("identity");
        finite(r.postMitigationDamage(),"postMitigationDamage"); finite(r.targetHealthBefore(),"targetHealthBefore");
        finite(r.maxHealth(),"maxHealth"); finite(r.missingHealthAfterNative(),"missingHealthAfterNative"); finite(r.healingMultiplier(),"healingMultiplier"); finite(r.nativeFinalHealing(),"nativeFinalHealing");
        if(r.maxHealth()<=0.0D) throw new IllegalArgumentException("maxHealth");
        if(r.nativeCorrelation()==NativeCorrelation.NONE && r.nativeFinalHealing()!=0.0D) throw new IllegalArgumentException("native correlation");
        for(Double v:r.candidates()) if(v==null||!Double.isFinite(v)||v<0.0D) throw new IllegalArgumentException("candidate");
    }
    private static void finite(double v,String n){ if(!Double.isFinite(v)||v<0.0D) throw new IllegalArgumentException(n); }

    public enum NativeCorrelation { NONE, EXACT_INTERCEPTED, AMBIGUOUS }
    public enum Status { AUTHORIZED, DUPLICATE_EVENT, INELIGIBLE, AMBIGUOUS_NATIVE_FAIL_CLOSED, NO_ELIGIBLE_SOURCE }
    public record Request(String actorId,String rootActionId,boolean serverAuthoritative,boolean eligibleActor,boolean directOwnerProven,
                          double postMitigationDamage,double targetHealthBefore,double maxHealth,double missingHealthAfterNative,
                          double healingMultiplier,NativeCorrelation nativeCorrelation,double nativeFinalHealing,List<Double> candidates){
        public Request { Objects.requireNonNull(actorId); Objects.requireNonNull(rootActionId); Objects.requireNonNull(nativeCorrelation); Objects.requireNonNull(candidates); candidates=List.copyOf(candidates); }
    }
    public record Resolution(Status status,double selectedCoefficient,double nativeHealingCounted,double skillTreeHealing,double bucketUsed){
        private static Resolution ineligible(){ return new Resolution(Status.INELIGIBLE,0,0,0,0); }
        private static Resolution duplicate(){ return new Resolution(Status.DUPLICATE_EVENT,0,0,0,0); }
        private static Resolution ambiguous(){ return new Resolution(Status.AMBIGUOUS_NATIVE_FAIL_CLOSED,0,0,0,0); }
        private static Resolution noSource(){ return new Resolution(Status.NO_ELIGIBLE_SOURCE,0,0,0,0); }
    }
    private record Payment(long tick,double amount) {}
}
