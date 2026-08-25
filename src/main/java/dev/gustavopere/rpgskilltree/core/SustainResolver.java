package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/** One canonical sustain resolution per root damage event with a shared 20-tick final-healing cap. */
public final class SustainResolver {
    public static final int WINDOW_TICKS = 20;
    public static final double CAP_FRACTION = 0.03D;
    private static final long CLAIM_RETENTION_MILLIS = 30_000L;
    private static final int MAX_ACTORS = 4_096;
    private final CanonicalEventLedger events = new CanonicalEventLedger(16_384);
    private final LinkedHashMap<String, Deque<Payment>> payments = new LinkedHashMap<>();
    private final Map<Long, Payment> pending = new HashMap<>();
    private final AtomicLong paymentTokens = new AtomicLong();

    public synchronized Resolution resolve(Request request, long nowTick) {
        Objects.requireNonNull(request);
        validate(request, nowTick);
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.directOwnerProven()
            || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) return Resolution.ineligible();
        if (!events.claimPrimaryOnce(request.action(), "sustain:resolve", Math.multiplyExact(nowTick, 50L), CLAIM_RETENTION_MILLIS)) {
            return Resolution.duplicateResult();
        }
        if (request.nativeCorrelation() == NativeCorrelation.AMBIGUOUS) {
            return new Resolution(Status.AMBIGUOUS_NATIVE_FAIL_CLOSED, 0.0D, 0.0D, 0.0D,
                bucketUsed(request.action().actorId(), nowTick), 0.0D, 0L);
        }

        double coefficient = request.candidates().stream()
            .filter(Candidate::eligible)
            .mapToDouble(Candidate::coefficient)
            .max()
            .orElse(0.0D);
        if (coefficient <= 0.0D) {
            return new Resolution(Status.NO_ELIGIBLE_SOURCE, 0.0D, 0.0D, 0.0D,
                bucketUsed(request.action().actorId(), nowTick), 0.0D, 0L);
        }

        String actorId = request.action().actorId();
        Deque<Payment> actorPayments = actorPayments(actorId);
        prune(actorPayments, nowTick);
        double cap = request.maxHealth() * CAP_FRACTION;
        double used = sum(actorPayments);
        double nativeOffered = request.nativeCorrelation() == NativeCorrelation.EXACT_INTERCEPTED
            ? request.nativeFinalHealing() : 0.0D;
        double nativeAuthorized = Math.min(nativeOffered, Math.max(0.0D, cap - used));
        double eligibleDamage = Math.min(request.postMitigationDamage(), request.targetHealthBefore());
        double desiredFinal = eligibleDamage * coefficient * request.healingMultiplier();
        double coefficientRemainder = Math.max(0.0D, desiredFinal - nativeAuthorized);
        double capRemainder = Math.max(0.0D, cap - used - nativeAuthorized);
        double skillTree = Math.min(Math.min(coefficientRemainder, capRemainder), request.missingHealthAfterNative());
        double total = nativeAuthorized + skillTree;
        long token = 0L;
        if (total > 0.0D) {
            token = paymentTokens.incrementAndGet();
            Payment payment = new Payment(nowTick, total, nativeAuthorized);
            actorPayments.addLast(payment);
            pending.put(token, payment);
        }
        double maximumFinalHealing = Math.min(capRemainder + nativeAuthorized,
            request.missingHealthAfterNative() + nativeAuthorized);
        return new Resolution(Status.AUTHORIZED, coefficient, nativeAuthorized, skillTree, used + total,
            maximumFinalHealing, token);
    }

    /** Reconciles authorization to the final health delta after the healing event pipeline. */
    public synchronized boolean confirmFinalHealing(Resolution resolution, double actualFinalHealing) {
        Objects.requireNonNull(resolution);
        finiteNonNegative(actualFinalHealing, "actualFinalHealing");
        if (actualFinalHealing > resolution.maximumFinalHealing()) {
            throw new IllegalArgumentException("actual healing exceeds final cap envelope");
        }
        if (resolution.paymentToken() == 0L) return actualFinalHealing == 0.0D;
        Payment payment = pending.remove(resolution.paymentToken());
        if (payment == null) return false;
        payment.finalHealing = actualFinalHealing;
        return true;
    }

    /** Compatibility helper for callers that applied the intercepted native part separately. */
    public synchronized boolean confirmSkillTreeHealing(Resolution resolution, double actualSkillTreeHealing) {
        Objects.requireNonNull(resolution);
        finiteNonNegative(actualSkillTreeHealing, "actualSkillTreeHealing");
        if (actualSkillTreeHealing > resolution.skillTreeHealing()) throw new IllegalArgumentException("actual healing exceeds authorization");
        if (resolution.paymentToken() == 0L) return actualSkillTreeHealing == 0.0D;
        return confirmFinalHealing(resolution, resolution.nativeHealingCounted() + actualSkillTreeHealing);
    }

    public synchronized double bucketUsed(String actorId, long nowTick) {
        Objects.requireNonNull(actorId);
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick must be non-negative");
        Deque<Payment> actorPayments = payments.get(actorId);
        if (actorPayments == null) return 0.0D;
        prune(actorPayments, nowTick);
        return sum(actorPayments);
    }

    /** Transitions do not clear payments or event claims; only expired entries leave the moving window. */
    public synchronized void clearTransient(String actorId) { Objects.requireNonNull(actorId); }

    private Deque<Payment> actorPayments(String actorId) {
        Deque<Payment> existing = payments.get(actorId);
        if (existing != null) return existing;
        while (payments.size() >= MAX_ACTORS) {
            Iterator<Map.Entry<String, Deque<Payment>>> iterator = payments.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
        Deque<Payment> created = new ArrayDeque<>();
        payments.put(actorId, created);
        return created;
    }

    private static void prune(Deque<Payment> actorPayments, long nowTick) {
        while (!actorPayments.isEmpty() && nowTick - actorPayments.peekFirst().tick >= WINDOW_TICKS) {
            actorPayments.removeFirst();
        }
    }
    private static double sum(Deque<Payment> actorPayments) {
        double sum = 0.0D;
        for (Payment payment : actorPayments) sum += payment.finalHealing;
        return sum;
    }
    private static void validate(Request request, long nowTick) {
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick must be non-negative");
        finiteNonNegative(request.postMitigationDamage(), "postMitigationDamage");
        finiteNonNegative(request.targetHealthBefore(), "targetHealthBefore");
        finiteNonNegative(request.maxHealth(), "maxHealth");
        finiteNonNegative(request.missingHealthAfterNative(), "missingHealthAfterNative");
        finiteNonNegative(request.healingMultiplier(), "healingMultiplier");
        finiteNonNegative(request.nativeFinalHealing(), "nativeFinalHealing");
        if (request.maxHealth() <= 0.0D) throw new IllegalArgumentException("maxHealth must be positive");
        if (request.nativeCorrelation() == NativeCorrelation.NONE && request.nativeFinalHealing() != 0.0D) {
            throw new IllegalArgumentException("native healing requires exact correlation");
        }
    }
    private static void finiteNonNegative(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0D) throw new IllegalArgumentException(name + " must be finite and non-negative");
    }

    public enum NativeCorrelation { NONE, EXACT_INTERCEPTED, AMBIGUOUS }
    public enum Status { AUTHORIZED, DUPLICATE_EVENT, INELIGIBLE, AMBIGUOUS_NATIVE_FAIL_CLOSED, NO_ELIGIBLE_SOURCE }
    public record Candidate(String sourceId, double coefficient, boolean eligible) {
        public Candidate {
            Objects.requireNonNull(sourceId);
            if (sourceId.isBlank() || !Double.isFinite(coefficient) || coefficient < 0.0D) throw new IllegalArgumentException("invalid candidate");
        }
    }
    public record Request(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean directOwnerProven,
        double postMitigationDamage,
        double targetHealthBefore,
        double maxHealth,
        double missingHealthAfterNative,
        double healingMultiplier,
        NativeCorrelation nativeCorrelation,
        double nativeFinalHealing,
        List<Candidate> candidates
    ) {
        public Request {
            Objects.requireNonNull(action); Objects.requireNonNull(nativeCorrelation); Objects.requireNonNull(candidates);
            candidates = List.copyOf(candidates);
        }
    }
    public record Resolution(Status status, double selectedCoefficient, double nativeHealingCounted,
        double skillTreeHealing, double bucketUsed, double maximumFinalHealing, long paymentToken) {
        static Resolution ineligible() { return new Resolution(Status.INELIGIBLE, 0, 0, 0, 0, 0, 0); }
        static Resolution duplicateResult() { return new Resolution(Status.DUPLICATE_EVENT, 0, 0, 0, 0, 0, 0); }
    }
    private static final class Payment {
        final long tick;
        final double nativeHealing;
        double finalHealing;
        Payment(long tick, double finalHealing, double nativeHealing) {
            this.tick = tick;
            this.finalHealing = finalHealing;
            this.nativeHealing = nativeHealing;
        }
    }
}
