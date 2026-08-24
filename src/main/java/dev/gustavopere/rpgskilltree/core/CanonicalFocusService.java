package dev.gustavopere.rpgskilltree.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Canonical A0046/A0048 Focus lifecycle: preparation, release-time consumption, and projectile correlation.
 */
public final class CanonicalFocusService {
    public static final double PREPARE_THRESHOLD = 80.0D;
    public static final double PREPARED_SHOT_COST = 50.0D;
    public static final long REQUIRED_STABLE_AIM_MILLIS = 1_250L;

    private final long retentionMillis;
    private final int maxTrackedShots;
    private final CanonicalEventLedger ledger;
    private final Map<String, Preparation> preparations = new LinkedHashMap<>();
    private final LinkedHashMap<ActionKey, PreparedShot> shots = new LinkedHashMap<>();
    private final Map<String, ProjectileLink> projectiles = new LinkedHashMap<>();
    private final Map<String, Long> cooldowns = new LinkedHashMap<>();

    public CanonicalFocusService(long retentionMillis, int maxTrackedShots) {
        if (retentionMillis <= 0L) throw new IllegalArgumentException("retentionMillis must be positive");
        if (maxTrackedShots <= 0) throw new IllegalArgumentException("maxTrackedShots must be positive");
        this.retentionMillis = retentionMillis;
        this.maxTrackedShots = maxTrackedShots;
        this.ledger = new CanonicalEventLedger(Math.multiplyExact(maxTrackedShots, 4));
    }

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

    public synchronized PreparationStatus beginPreparation(
        CanonicalActionIdentity preparation,
        boolean serverAuthoritative,
        boolean eligibleActor,
        long nowMillis
    ) {
        Objects.requireNonNull(preparation);
        requireNow(nowMillis);
        prune(nowMillis);
        if (!eligible(preparation, serverAuthoritative, eligibleActor, true)) {
            return PreparationStatus.INELIGIBLE;
        }
        Long cooldownUntil = cooldowns.get(preparation.actorId());
        if (cooldownUntil != null && cooldownUntil > nowMillis) return PreparationStatus.COOLDOWN_ACTIVE;
        Preparation previous = preparations.get(preparation.actorId());
        if (previous != null && previous.action.sameAction(preparation)) return PreparationStatus.DUPLICATE;
        preparations.put(
            preparation.actorId(),
            new Preparation(preparation, nowMillis, Math.addExact(nowMillis, retentionMillis), false)
        );
        return PreparationStatus.STARTED;
    }

    public synchronized PreparationStatus armPreparation(
        CanonicalActionIdentity preparation,
        boolean stillStable,
        NotionCombatPerkState state,
        long nowMillis
    ) {
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
        if (nowMillis - current.startedAtMillis < REQUIRED_STABLE_AIM_MILLIS) {
            return PreparationStatus.TOO_EARLY;
        }
        if (state.focus(preparation.actorId()) < PREPARE_THRESHOLD) {
            return PreparationStatus.INSUFFICIENT_FOCUS;
        }
        preparations.put(
            preparation.actorId(),
            new Preparation(current.action, current.startedAtMillis, current.expiresAtMillis, true)
        );
        return PreparationStatus.ARMED;
    }

    public synchronized ReleaseStatus release(
        ReleaseRequest request,
        NotionCombatPerkState state,
        long nowMillis
    ) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(state);
        requireNow(nowMillis);
        prune(nowMillis);
        CanonicalActionIdentity shot = request.shot();
        if (!eligible(shot, request.serverAuthoritative(), request.eligibleActor(), true)) {
            return ReleaseStatus.INELIGIBLE;
        }
        if (!ledger.claimPrimaryOnce(shot, "focus:release", nowMillis, retentionMillis)) {
            return ReleaseStatus.DUPLICATE;
        }
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
        shots.put(
            ActionKey.of(shot),
            new PreparedShot(shot, Math.addExact(nowMillis, retentionMillis), false)
        );
        cooldowns.put(shot.actorId(), Math.addExact(nowMillis, request.cooldownMillis()));
        return ReleaseStatus.PREPARED_CONSUMED;
    }

    public synchronized ProjectileStatus attachProjectile(ProjectileRequest request) {
        Objects.requireNonNull(request);
        CanonicalActionIdentity shot = request.shot();
        if (!request.serverAuthoritative()
            || !request.eligibleOwner()
            || request.ownerActorId() == null
            || request.ownerActorId().isBlank()
            || !shot.actorId().equals(request.ownerActorId())) {
            return ProjectileStatus.INELIGIBLE_OWNER;
        }
        if (!ProcGuard.mayTriggerSecondaryEffect(shot.origin())) return ProjectileStatus.PROC_DEPTH_REJECTED;
        if (!request.directPlayerOwned()) return ProjectileStatus.NOT_DIRECT_PLAYER_PROJECTILE;
        PreparedShot preparedShot = shots.get(ActionKey.of(shot));
        if (preparedShot == null) return ProjectileStatus.UNKNOWN_SHOT;
        ProjectileLink previous = projectiles.get(request.projectileId());
        if (previous != null) return ProjectileStatus.DUPLICATE;
        projectiles.put(
            request.projectileId(),
            new ProjectileLink(ActionKey.of(shot), preparedShot.expiresAtMillis)
        );
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

    public synchronized void clearActor(String actorId) {
        Objects.requireNonNull(actorId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        preparations.remove(actorId);
        cooldowns.remove(actorId);
        shots.entrySet().removeIf(entry -> entry.getKey().actorId.equals(actorId));
        projectiles.entrySet().removeIf(entry -> entry.getValue().shotKey.actorId.equals(actorId));
        ledger.clearActor(actorId);
    }

    private void prune(long nowMillis) {
        preparations.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
        shots.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
        projectiles.entrySet().removeIf(entry ->
            entry.getValue().expiresAtMillis <= nowMillis || !shots.containsKey(entry.getValue().shotKey));
        cooldowns.entrySet().removeIf(entry -> entry.getValue() <= nowMillis);
    }

    private void makeShotRoom() {
        while (shots.size() >= maxTrackedShots) {
            Iterator<Map.Entry<ActionKey, PreparedShot>> iterator = shots.entrySet().iterator();
            ActionKey removed = iterator.next().getKey();
            iterator.remove();
            projectiles.entrySet().removeIf(entry -> entry.getValue().shotKey.equals(removed));
        }
    }

    private static boolean eligible(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct
    ) {
        return serverAuthoritative
            && eligibleActor
            && direct
            && ProcGuard.mayTriggerSecondaryEffect(action.origin());
    }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    private static void requireProjectileId(String projectileId) {
        Objects.requireNonNull(projectileId);
        if (projectileId.isBlank()) throw new IllegalArgumentException("projectileId must not be blank");
    }

    public enum ProductionStatus {
        APPLIED,
        DUPLICATE,
        INELIGIBLE,
        NO_GAIN,
        UNSUPPORTED_UNSPECIFIED_AMOUNT
    }

    public enum PreparationStatus {
        STARTED,
        ARMED,
        TOO_EARLY,
        INSUFFICIENT_FOCUS,
        COOLDOWN_ACTIVE,
        CANCELLED_UNSTABLE,
        NOT_STARTED,
        DUPLICATE,
        INELIGIBLE
    }

    public enum ReleaseStatus {
        PREPARED_CONSUMED,
        DUPLICATE,
        NOT_FULLY_DRAWN,
        NOT_PREPARED,
        INSUFFICIENT_FOCUS,
        INELIGIBLE
    }

    public enum ProjectileStatus {
        ATTACHED,
        DUPLICATE,
        UNKNOWN_SHOT,
        INELIGIBLE_OWNER,
        NOT_DIRECT_PLAYER_PROJECTILE,
        PROC_DEPTH_REJECTED
    }

    public record ProductionRequest(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        OptionalDouble amount,
        double multiplier
    ) {
        public ProductionRequest {
            Objects.requireNonNull(action);
            Objects.requireNonNull(amount);
            if (amount.isPresent() && (!Double.isFinite(amount.getAsDouble()) || amount.getAsDouble() < 0.0D)) {
                throw new IllegalArgumentException("amount must be finite and non-negative");
            }
            if (!Double.isFinite(multiplier) || multiplier <= 0.0D) {
                throw new IllegalArgumentException("multiplier must be finite and positive");
            }
        }
    }

    public record ReleaseRequest(
        CanonicalActionIdentity shot,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean fullyDrawn,
        long cooldownMillis
    ) {
        public ReleaseRequest {
            Objects.requireNonNull(shot);
            if (cooldownMillis <= 0L) throw new IllegalArgumentException("cooldownMillis must be positive");
        }
    }

    public record ProjectileRequest(
        CanonicalActionIdentity shot,
        String projectileId,
        String ownerActorId,
        boolean serverAuthoritative,
        boolean eligibleOwner,
        boolean directPlayerOwned
    ) {
        public ProjectileRequest {
            Objects.requireNonNull(shot);
            requireProjectileId(projectileId);
        }
    }

    private record ActionKey(String actorId, String actionId) {
        static ActionKey of(CanonicalActionIdentity action) {
            return new ActionKey(action.actorId(), action.actionId());
        }
    }

    private record Preparation(
        CanonicalActionIdentity action,
        long startedAtMillis,
        long expiresAtMillis,
        boolean armed
    ) {}

    private record PreparedShot(CanonicalActionIdentity action, long expiresAtMillis, boolean resolved) {}

    private record ProjectileLink(ActionKey shotKey, long expiresAtMillis) {}
}
