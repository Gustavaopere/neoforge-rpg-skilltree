package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/** A0081 delayed recovery reserve, intentionally independent from SustainResolver. */
public final class CombatRecoveryService {
    private static final long HEAL_DELAY_MILLIS = 3_000L;
    private static final long PARCEL_INTERVAL_MILLIS = 1_000L;
    private static final long RESERVE_EXPIRY_MILLIS = 10_000L;
    private final CanonicalEventLedger damageEvents = new CanonicalEventLedger(8_192);
    private final Map<String, ActorState> actors = new HashMap<>();
    private final AtomicLong tokens = new AtomicLong();

    public synchronized double recordDamage(DamageRequest request, long nowMillis) {
        Objects.requireNonNull(request);
        requireTime(nowMillis);
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.directMelee()
            || !request.hostileTarget() || !request.rhythmActive() || request.rank() <= 0
            || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) return 0.0D;
        if (!damageEvents.claimPrimaryOnce(request.action(), "A0081:reserve", nowMillis, 30_000L)) return 0.0D;
        validate(request);
        ActorState actor = actors.computeIfAbsent(request.action().actorId(), ignored -> new ActorState());
        expire(actor, nowMillis);
        double rate = request.rank() >= 3 ? 0.25D : request.rank() == 2 ? 0.20D : 0.15D;
        double eligibleDamage = Math.min(request.postMitigationDamage(), request.targetHealthBefore());
        double capacity = request.maxHealth() * 0.08D;
        double added = Math.min(eligibleDamage * rate, Math.max(0.0D, capacity - actor.reserve));
        actor.reserve += added;
        actor.lastCombatMillis = nowMillis;
        if (actor.lastHostileDamageMillis < 0L) actor.lastHostileDamageMillis = nowMillis;
        actor.phaseStarted = false;
        actor.pending = null;
        return added;
    }

    public synchronized void recordHostileDamage(String actorId, boolean eligible, long nowMillis) {
        requireTime(nowMillis);
        if (!eligible) return;
        ActorState actor = actors.computeIfAbsent(requireId(actorId), ignored -> new ActorState());
        actor.lastHostileDamageMillis = nowMillis;
        actor.lastCombatMillis = nowMillis;
        actor.phaseStarted = false;
        actor.snapshot = 0.0D;
        actor.parcelsOffered = 0;
        actor.pending = null;
    }

    public synchronized Optional<Installment> offerInstallment(
        String actorId,
        double maxHealth,
        double missingHealth,
        long nowMillis
    ) {
        actorId = requireId(actorId);
        requireTime(nowMillis);
        finiteNonNegative(maxHealth, "maxHealth");
        finiteNonNegative(missingHealth, "missingHealth");
        ActorState actor = actors.get(actorId);
        if (actor == null) return Optional.empty();
        expire(actor, nowMillis);
        if (actor.reserve <= 0.0D || missingHealth <= 0.0D || actor.pending != null) return Optional.empty();
        if (nowMillis - actor.lastHostileDamageMillis < HEAL_DELAY_MILLIS) return Optional.empty();
        if (!actor.phaseStarted) {
            actor.phaseStarted = true;
            actor.snapshot = actor.reserve;
            actor.parcelsOffered = 0;
            actor.nextParcelMillis = nowMillis;
        }
        if (actor.parcelsOffered >= 4 || nowMillis < actor.nextParcelMillis) return Optional.empty();
        double attempted = Math.min(Math.min(actor.snapshot * 0.25D, actor.reserve), missingHealth);
        if (attempted <= 0.0D) return Optional.empty();
        Installment installment = new Installment(actorId, tokens.incrementAndGet(), attempted);
        actor.pending = installment;
        actor.parcelsOffered++;
        actor.nextParcelMillis = Math.addExact(nowMillis, PARCEL_INTERVAL_MILLIS);
        return Optional.of(installment);
    }

    public synchronized boolean confirmHealed(Installment installment, double actualHealing) {
        Objects.requireNonNull(installment);
        finiteNonNegative(actualHealing, "actualHealing");
        if (actualHealing > installment.attemptedHealing()) throw new IllegalArgumentException("actual healing exceeds attempt");
        ActorState actor = actors.get(installment.actorId());
        if (actor == null || actor.pending == null || actor.pending.token() != installment.token()) return false;
        actor.reserve = Math.max(0.0D, actor.reserve - actualHealing);
        actor.pending = null;
        return true;
    }

    public synchronized double reserve(String actorId, long nowMillis) {
        requireTime(nowMillis);
        ActorState actor = actors.get(requireId(actorId));
        if (actor == null) return 0.0D;
        expire(actor, nowMillis);
        return actor.reserve;
    }

    public synchronized void clearTransient(String actorId) {
        ActorState actor = actors.get(requireId(actorId));
        if (actor == null) return;
        actor.phaseStarted = false;
        actor.snapshot = 0.0D;
        actor.pending = null;
    }

    private static void expire(ActorState actor, long nowMillis) {
        if (actor.lastCombatMillis >= 0L && nowMillis - actor.lastCombatMillis > RESERVE_EXPIRY_MILLIS) {
            actor.reserve = 0.0D;
            actor.phaseStarted = false;
            actor.snapshot = 0.0D;
            actor.parcelsOffered = 0;
            actor.pending = null;
        }
    }
    private static void validate(DamageRequest request) {
        finiteNonNegative(request.maxHealth(), "maxHealth");
        finiteNonNegative(request.postMitigationDamage(), "postMitigationDamage");
        finiteNonNegative(request.targetHealthBefore(), "targetHealthBefore");
        if (request.maxHealth() <= 0.0D || request.rank() > 3) throw new IllegalArgumentException("invalid recovery request");
    }
    private static String requireId(String id) { Objects.requireNonNull(id); if (id.isBlank()) throw new IllegalArgumentException("blank id"); return id; }
    private static void requireTime(long now) { if (now < 0L) throw new IllegalArgumentException("negative time"); }
    private static void finiteNonNegative(double value, String name) { if (!Double.isFinite(value) || value < 0) throw new IllegalArgumentException(name); }

    public record DamageRequest(CanonicalActionIdentity action, boolean serverAuthoritative, boolean eligibleActor,
        boolean directMelee, boolean hostileTarget, boolean rhythmActive, double maxHealth,
        double postMitigationDamage, double targetHealthBefore, int rank) {
        public DamageRequest { Objects.requireNonNull(action); }
    }
    public record Installment(String actorId, long token, double attemptedHealing) {}
    private static final class ActorState {
        double reserve;
        double snapshot;
        long lastHostileDamageMillis = -1L;
        long lastCombatMillis = -1L;
        long nextParcelMillis;
        int parcelsOffered;
        boolean phaseStarted;
        Installment pending;
    }
}
