package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

/** Canonical A0046/A0048 Focus lifecycle and projectile correlation. */
public final class CanonicalFocusService {
    public static final double PREPARE_THRESHOLD = 80.0D;
    public static final double PREPARED_SHOT_COST = 50.0D;
    public static final long REQUIRED_STABLE_AIM_MILLIS = 1_250L;

    public static final long FOCUS_INTERVAL_MILLIS = 500L;
    public static final long SUDDEN_AIM_WINDOW_MILLIS = 250L;
    public static final long SUDDEN_AIM_COOLDOWN_MILLIS = 500L;
    public static final double SUDDEN_AIM_THRESHOLD_DEGREES = 45.0D;
    public static final double DISTANT_HIT_MIN_DISTANCE = 12.0D;

    private final long retentionMillis;
    private final int maxTrackedShots;
    private final CanonicalEventLedger ledger;
    private final Map<String, Preparation> preparations = new LinkedHashMap<>();
    private final LinkedHashMap<ActionKey, PreparedShot> shots = new LinkedHashMap<>();
    private final Map<String, ProjectileLink> projectiles = new LinkedHashMap<>();
    private final Map<String, Long> cooldowns = new LinkedHashMap<>();
    private final Map<String, AimTracker> aimTrackers = new LinkedHashMap<>();
    private final Map<String, DistantCredit> distantCredits = new LinkedHashMap<>();

    public CanonicalFocusService(long retentionMillis, int maxTrackedShots) {
        if (retentionMillis <= 0L) throw new IllegalArgumentException("retentionMillis must be positive");
        if (maxTrackedShots <= 0) throw new IllegalArgumentException("maxTrackedShots must be positive");
        this.retentionMillis = retentionMillis;
        this.maxTrackedShots = maxTrackedShots;
        this.ledger = new CanonicalEventLedger(Math.multiplyExact(maxTrackedShots, 4));
    }

    /** Generic producer kept for canonical callers outside the frozen A0046 aim/hit producers. */
    public ProductionStatus produce(ProductionRequest request, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(state);
        if (!eligible(request.action(), request.serverAuthoritative(), request.eligibleActor(), request.direct())) {
            return ProductionStatus.INELIGIBLE;
        }
        if (request.amount().isEmpty()) return ProductionStatus.UNSUPPORTED_UNSPECIFIED_AMOUNT;
        double amount = request.amount().getAsDouble();
        if (amount == 0.0D) return ProductionStatus.NO_GAIN;
        if (!ledger.claimPrimaryOnce(request.action(), "focus:producer", nowMillis, retentionMillis)) {
            return ProductionStatus.DUPLICATE;
        }
        state.addFocus(request.action().actorId(), amount * request.multiplier(), nowMillis);
        return ProductionStatus.APPLIED;
    }

    public synchronized AimStatus sampleAim(AimSampleRequest request, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(state);
        requireNow(nowMillis);
        prune(nowMillis);
        if (request.rank() == 0) return AimStatus.NOT_LEARNED;
        if (!eligible(request.action(), request.serverAuthoritative(), request.eligibleActor(), true) || !request.bowInUse()) {
            aimTrackers.remove(request.action().actorId());
            return AimStatus.INELIGIBLE;
        }
        String actorId = request.action().actorId();
        AimTracker tracker = aimTrackers.get(actorId);
        if (tracker == null || !tracker.action.sameAction(request.action())) {
            tracker = new AimTracker(request.action(), nowMillis, request.yaw(), request.pitch(), nowMillis);
            aimTrackers.put(actorId, tracker);
            return AimStatus.TRACKING;
        }
        double angularDelta = angularDelta(tracker.lastYaw, tracker.lastPitch, request.yaw(), request.pitch());
        tracker.lastYaw = request.yaw();
        tracker.lastPitch = request.pitch();
        tracker.lastSampleMillis = nowMillis;
        tracker.angularSteps.addLast(new AngularStep(nowMillis, angularDelta));
        while (!tracker.angularSteps.isEmpty() && tracker.angularSteps.peekFirst().atMillis() < nowMillis - SUDDEN_AIM_WINDOW_MILLIS) {
            tracker.angularSteps.removeFirst();
        }
        double accumulated = 0.0D;
        for (AngularStep step : tracker.angularSteps) accumulated += step.degrees();
        if (accumulated > SUDDEN_AIM_THRESHOLD_DEGREES && nowMillis >= tracker.suddenCooldownUntilMillis) {
            consumeFocusClamped(state, actorId, 10.0D * request.lossScalar());
            tracker.suddenCooldownUntilMillis = Math.addExact(nowMillis, SUDDEN_AIM_COOLDOWN_MILLIS);
            tracker.lastIntervalMillis = nowMillis;
            tracker.angularSteps.clear();
            return AimStatus.SUDDEN_CHANGE_DRAIN;
        }
        if (nowMillis - tracker.lastIntervalMillis < FOCUS_INTERVAL_MILLIS) return AimStatus.TRACKING;
        tracker.lastIntervalMillis = nowMillis;
        if (request.sprinting()) {
            consumeFocusClamped(state, actorId, 6.0D * request.lossScalar());
            return AimStatus.SPRINT_DRAIN;
        }
        double intervalGain = request.rank() >= 2 ? 5.0D : 4.0D;
        state.addFocus(actorId, intervalGain * request.gainScalar(), nowMillis);
        return AimStatus.STABLE_GAIN;
    }

    public synchronized void endAimTracking(String actorId) {
        requireActorId(actorId);
        aimTrackers.remove(actorId);
    }

    public synchronized DistantHitStatus creditDistantProjectileHit(DistantHitRequest request, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(state);
        requireNow(nowMillis);
        prune(nowMillis);
        if (request.rank() == 0) return DistantHitStatus.NOT_LEARNED;
        if (!eligible(request.action(), request.serverAuthoritative(), request.eligibleOwner(), request.directPlayerOwned())) return DistantHitStatus.INELIGIBLE;
        if (request.distanceFromShot() < DISTANT_HIT_MIN_DISTANCE) return DistantHitStatus.TOO_CLOSE;
        if (distantCredits.containsKey(request.projectileId())) return DistantHitStatus.DUPLICATE_PROJECTILE;
        distantCredits.put(request.projectileId(), new DistantCredit(request.action().actorId(), Math.addExact(nowMillis, retentionMillis)));
        state.addFocus(request.action().actorId(), request.rank() >= 2 ? 12.5D : 10.0D, nowMillis);
        return DistantHitStatus.APPLIED;
    }

    public synchronized boolean applyHeavyImpactLoss(String actorId, boolean serverAuthoritative, boolean provenHeavyImpact, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(state);
        requireActorId(actorId);
        requireNow(nowMillis);
        if (!serverAuthoritative || !provenHeavyImpact) return false;
        consumeFocusClamped(state, actorId, 25.0D);
        return true;
    }

    public synchronized boolean applyCancelledDrawLoss(String actorId, boolean serverAuthoritative, boolean eligibleActor, double drawFraction, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(state);
        requireActorId(actorId);
        requireNow(nowMillis);
        if (!Double.isFinite(drawFraction) || drawFraction < 0.0D) throw new IllegalArgumentException("drawFraction must be finite and non-negative");
        if (!serverAuthoritative || !eligibleActor || drawFraction < 0.80D) return false;
        consumeFocusClamped(state, actorId, 15.0D);
        return true;
    }

    public synchronized PreparationStatus beginPreparation(CanonicalActionIdentity preparation, boolean serverAuthoritative, boolean eligibleActor, long nowMillis) {
        Objects.requireNonNull(preparation);
        requireNow(nowMillis);
        prune(nowMillis);
        if (!eligible(preparation, serverAuthoritative, eligibleActor, true)) return PreparationStatus.INELIGIBLE;
        Long cooldownUntil = cooldowns.get(preparation.actorId());
        if (cooldownUntil != null && cooldownUntil > nowMillis) return PreparationStatus.COOLDOWN_ACTIVE;
        Preparation previous = preparations.get(preparation.actorId());
        if (previous != null && previous.action.sameAction(preparation)) return PreparationStatus.DUPLICATE;
        preparations.put(preparation.actorId(), new Preparation(preparation, nowMillis, Math.addExact(nowMillis, retentionMillis), false));
        return PreparationStatus.STARTED;
    }

    public synchronized PreparationStatus armPreparation(CanonicalActionIdentity preparation, boolean stillStable, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(preparation);
        Objects.requireNonNull(state);
        requireNow(nowMillis);
        prune(nowMillis);
        if (!ProcGuard.mayTriggerSecondaryEffect(preparation.origin())) return PreparationStatus.INELIGIBLE;
        Preparation current = preparations.get(preparation.actorId());
        if (current == null || !current.action.sameAction(preparation)) return PreparationStatus.NOT_STARTED;
        if (!stillStable) {
            preparations.remove(preparation.actorId());
            return PreparationStatus.CANCELLED_UNSTABLE;
        }
        if (nowMillis - current.startedAtMillis < REQUIRED_STABLE_AIM_MILLIS) return PreparationStatus.TOO_EARLY;
        if (state.focus(preparation.actorId()) < PREPARE_THRESHOLD) return PreparationStatus.INSUFFICIENT_FOCUS;
        preparations.put(preparation.actorId(), new Preparation(current.action, current.startedAtMillis, current.expiresAtMillis, true));
        return PreparationStatus.ARMED;
    }

    public synchronized ReleaseStatus release(ReleaseRequest request, NotionCombatPerkState state, long nowMillis) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(state);
        requireNow(nowMillis);
        prune(nowMillis);
        CanonicalActionIdentity shot = request.shot();
        if (!eligible(shot, request.serverAuthoritative(), request.eligibleActor(), true)) return ReleaseStatus.INELIGIBLE;
        if (!ledger.claimPrimaryOnce(shot, "focus:release", nowMillis, retentionMillis)) return ReleaseStatus.DUPLICATE;
        if (!request.fullyDrawn()) return ReleaseStatus.NOT_FULLY_DRAWN;
        Preparation preparation = preparations.get(shot.actorId());
        if (preparation == null || !preparation.armed) return ReleaseStatus.NOT_PREPARED;
        if (state.focus(shot.actorId()) < PREPARED_SHOT_COST) {
            preparations.remove(shot.actorId());
            return ReleaseStatus.INSUFFICIENT_FOCUS;
        }
        state.consumeFocus(shot.actorId(), PREPARED_SHOT_COST);
        preparations.remove(shot.actorId());
        makeShotRoom();
        shots.put(ActionKey.of(shot), new PreparedShot(shot, Math.addExact(nowMillis, retentionMillis), false));
        cooldowns.put(shot.actorId(), Math.addExact(nowMillis, request.cooldownMillis()));
        return ReleaseStatus.PREPARED_CONSUMED;
    }

    public synchronized ProjectileStatus attachProjectile(ProjectileRequest request) {
        Objects.requireNonNull(request);
        CanonicalActionIdentity shot = request.shot();
        if (!request.serverAuthoritative() || !request.eligibleOwner() || request.ownerActorId() == null || request.ownerActorId().isBlank() || !shot.actorId().equals(request.ownerActorId())) return ProjectileStatus.INELIGIBLE_OWNER;
        if (!ProcGuard.mayTriggerSecondaryEffect(shot.origin())) return ProjectileStatus.PROC_DEPTH_REJECTED;
        if (!request.directPlayerOwned()) return ProjectileStatus.NOT_DIRECT_PLAYER_PROJECTILE;
        PreparedShot preparedShot = shots.get(ActionKey.of(shot));
        if (preparedShot == null) return ProjectileStatus.UNKNOWN_SHOT;
        if (projectiles.containsKey(request.projectileId())) return ProjectileStatus.DUPLICATE;
        projectiles.put(request.projectileId(), new ProjectileLink(ActionKey.of(shot), preparedShot.expiresAtMillis));
        return ProjectileStatus.ATTACHED;
    }

    public synchronized Optional<CanonicalActionIdentity> projectileAction(String projectileId, long nowMillis) {
        requireProjectileId(projectileId);
        requireNow(nowMillis);
        prune(nowMillis);
        ProjectileLink link = projectiles.get(projectileId);
        if (link == null) return Optional.empty();
        PreparedShot shot = shots.get(link.shotKey);
        return shot == null ? Optional.empty() : Optional.of(shot.action.withSource("neoforge:projectile"));
    }

    public synchronized boolean claimPreparedHit(String projectileId, long nowMillis) {
        requireProjectileId(projectileId);
        requireNow(nowMillis);
        prune(nowMillis);
        ProjectileLink link = projectiles.get(projectileId);
        if (link == null) return false;
        PreparedShot shot = shots.get(link.shotKey);
        if (shot == null || shot.resolved) return false;
        shots.put(link.shotKey, new PreparedShot(shot.action, shot.expiresAtMillis, true));
        return true;
    }

    /** Clears entity-bound state while preserving cooldown/claim/antiabuse guards. */
    public synchronized void clearTransientActorPreservingGuards(String actorId) {
        requireActorId(actorId);
        preparations.remove(actorId);
        aimTrackers.remove(actorId);
        shots.entrySet().removeIf(entry -> entry.getKey().actorId.equals(actorId));
        projectiles.entrySet().removeIf(entry -> entry.getValue().shotKey.actorId.equals(actorId));
        // cooldowns, ledger claims, and distantCredits intentionally survive recreation/dimension.
    }

    /** Full session teardown, used on logout. */
    public synchronized void clearActor(String actorId) {
        requireActorId(actorId);
        preparations.remove(actorId);
        cooldowns.remove(actorId);
        aimTrackers.remove(actorId);
        shots.entrySet().removeIf(entry -> entry.getKey().actorId.equals(actorId));
        projectiles.entrySet().removeIf(entry -> entry.getValue().shotKey.actorId.equals(actorId));
        distantCredits.entrySet().removeIf(entry -> entry.getValue().actorId.equals(actorId));
        ledger.clearActor(actorId);
    }

    private void prune(long nowMillis) {
        preparations.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
        shots.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
        projectiles.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis || !shots.containsKey(entry.getValue().shotKey));
        cooldowns.entrySet().removeIf(entry -> entry.getValue() <= nowMillis);
        aimTrackers.entrySet().removeIf(entry -> entry.getValue().lastSampleMillis <= nowMillis - retentionMillis);
        distantCredits.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private void makeShotRoom() {
        while (shots.size() >= maxTrackedShots) {
            Iterator<Map.Entry<ActionKey, PreparedShot>> iterator = shots.entrySet().iterator();
            ActionKey removed = iterator.next().getKey();
            iterator.remove();
            projectiles.entrySet().removeIf(entry -> entry.getValue().shotKey.equals(removed));
        }
    }

    private static void consumeFocusClamped(NotionCombatPerkState state, String actorId, double amount) {
        if (!Double.isFinite(amount) || amount < 0.0D) throw new IllegalArgumentException("loss must be finite and non-negative");
        double current = state.focus(actorId);
        if (current <= 0.0D || amount == 0.0D) return;
        state.consumeFocus(actorId, Math.min(current, amount));
    }

    private static double angularDelta(double previousYaw, double previousPitch, double yaw, double pitch) {
        double yawDelta = Math.abs(wrapDegrees(yaw - previousYaw));
        double pitchDelta = Math.abs(pitch - previousPitch);
        return Math.hypot(yawDelta, pitchDelta);
    }

    private static double wrapDegrees(double degrees) {
        double wrapped = degrees % 360.0D;
        if (wrapped >= 180.0D) wrapped -= 360.0D;
        if (wrapped < -180.0D) wrapped += 360.0D;
        return wrapped;
    }

    private static boolean eligible(CanonicalActionIdentity action, boolean serverAuthoritative, boolean eligibleActor, boolean direct) {
        return serverAuthoritative && eligibleActor && direct && ProcGuard.mayTriggerSecondaryEffect(action.origin());
    }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    private static void requireProjectileId(String projectileId) {
        Objects.requireNonNull(projectileId);
        if (projectileId.isBlank()) throw new IllegalArgumentException("projectileId must not be blank");
    }

    private static void requireActorId(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
    }

    public enum ProductionStatus { APPLIED, DUPLICATE, INELIGIBLE, NO_GAIN, UNSUPPORTED_UNSPECIFIED_AMOUNT }
    public enum AimStatus { TRACKING, STABLE_GAIN, SPRINT_DRAIN, SUDDEN_CHANGE_DRAIN, INELIGIBLE, NOT_LEARNED }
    public enum DistantHitStatus { APPLIED, DUPLICATE_PROJECTILE, TOO_CLOSE, INELIGIBLE, NOT_LEARNED }
    public enum PreparationStatus { STARTED, ARMED, TOO_EARLY, INSUFFICIENT_FOCUS, COOLDOWN_ACTIVE, CANCELLED_UNSTABLE, NOT_STARTED, DUPLICATE, INELIGIBLE }
    public enum ReleaseStatus { PREPARED_CONSUMED, DUPLICATE, NOT_FULLY_DRAWN, NOT_PREPARED, INSUFFICIENT_FOCUS, INELIGIBLE }
    public enum ProjectileStatus { ATTACHED, DUPLICATE, UNKNOWN_SHOT, INELIGIBLE_OWNER, NOT_DIRECT_PLAYER_PROJECTILE, PROC_DEPTH_REJECTED }

    public record ProductionRequest(CanonicalActionIdentity action, boolean serverAuthoritative, boolean eligibleActor, boolean direct, OptionalDouble amount, double multiplier) {
        public ProductionRequest {
            Objects.requireNonNull(action);
            Objects.requireNonNull(amount);
            if (amount.isPresent() && (!Double.isFinite(amount.getAsDouble()) || amount.getAsDouble() < 0.0D)) throw new IllegalArgumentException("amount must be finite and non-negative");
            if (!Double.isFinite(multiplier) || multiplier <= 0.0D) throw new IllegalArgumentException("multiplier must be finite and positive");
        }
    }

    public record AimSampleRequest(CanonicalActionIdentity action, boolean serverAuthoritative, boolean eligibleActor, boolean bowInUse, boolean sprinting, int rank, double yaw, double pitch, double gainScalar, double lossScalar) {
        public AimSampleRequest {
            Objects.requireNonNull(action);
            if (rank < 0 || rank > 2) throw new IllegalArgumentException("rank must be in 0..2");
            if (!Double.isFinite(yaw) || !Double.isFinite(pitch)) throw new IllegalArgumentException("aim angles must be finite");
            if (!Double.isFinite(gainScalar) || gainScalar <= 0.0D || !Double.isFinite(lossScalar) || lossScalar <= 0.0D) throw new IllegalArgumentException("body-state scalars must be finite and positive");
        }
    }

    public record DistantHitRequest(CanonicalActionIdentity action, String projectileId, boolean serverAuthoritative, boolean eligibleOwner, boolean directPlayerOwned, double distanceFromShot, int rank) {
        public DistantHitRequest {
            Objects.requireNonNull(action);
            requireProjectileId(projectileId);
            if (!Double.isFinite(distanceFromShot) || distanceFromShot < 0.0D) throw new IllegalArgumentException("distanceFromShot must be finite and non-negative");
            if (rank < 0 || rank > 2) throw new IllegalArgumentException("rank must be in 0..2");
        }
    }

    public record ReleaseRequest(CanonicalActionIdentity shot, boolean serverAuthoritative, boolean eligibleActor, boolean fullyDrawn, long cooldownMillis) {
        public ReleaseRequest {
            Objects.requireNonNull(shot);
            if (cooldownMillis <= 0L) throw new IllegalArgumentException("cooldownMillis must be positive");
        }
    }

    public record ProjectileRequest(CanonicalActionIdentity shot, String projectileId, String ownerActorId, boolean serverAuthoritative, boolean eligibleOwner, boolean directPlayerOwned) {
        public ProjectileRequest {
            Objects.requireNonNull(shot);
            requireProjectileId(projectileId);
        }
    }

    private record ActionKey(String actorId, String actionId) {
        static ActionKey of(CanonicalActionIdentity action) { return new ActionKey(action.actorId(), action.actionId()); }
    }
    private record Preparation(CanonicalActionIdentity action, long startedAtMillis, long expiresAtMillis, boolean armed) {}
    private record PreparedShot(CanonicalActionIdentity action, long expiresAtMillis, boolean resolved) {}
    private record ProjectileLink(ActionKey shotKey, long expiresAtMillis) {}
    private record AngularStep(long atMillis, double degrees) {}
    private record DistantCredit(String actorId, long expiresAtMillis) {}

    private static final class AimTracker {
        final CanonicalActionIdentity action;
        final Deque<AngularStep> angularSteps = new ArrayDeque<>();
        long lastIntervalMillis;
        long lastSampleMillis;
        long suddenCooldownUntilMillis;
        double lastYaw;
        double lastPitch;
        AimTracker(CanonicalActionIdentity action, long lastIntervalMillis, double lastYaw, double lastPitch, long lastSampleMillis) {
            this.action = action;
            this.lastIntervalMillis = lastIntervalMillis;
            this.lastYaw = lastYaw;
            this.lastPitch = lastPitch;
            this.lastSampleMillis = lastSampleMillis;
        }
    }
}
