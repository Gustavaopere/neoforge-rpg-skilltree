package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Server-authoritative A0052-A0054 shot/reload state keyed by canonical action and projectile. */
public final class CrossbowCadenceService {
    private static final int MAX_CHARGES = 3;
    private static final long PROJECTILE_RETENTION_MILLIS = 30_000L;
    private final Map<String, ActorState> actors = new HashMap<>();
    private final Map<String, ProjectileState> projectiles = new HashMap<>();
    private final Map<ActionKey, ActionShot> resolvedShots = new HashMap<>();

    public synchronized ShotEffect fire(ShotRequest request, long nowMillis) {
        Objects.requireNonNull(request);
        requireNow(nowMillis);
        prune(nowMillis);
        if (!eligible(request.action(), request.serverAuthoritative(), request.eligibleActor(), request.directProjectile())
            || !request.fullyCharged() || request.stackIdentity().isBlank() || request.projectileId().isBlank()) {
            return ShotEffect.none();
        }
        ActionKey key = ActionKey.of(request.action());
        ActionShot previous = resolvedShots.get(key);
        if (previous != null) {
            projectiles.putIfAbsent(request.projectileId(), new ProjectileState(
                request.action(), request.stackIdentity(), previous, false));
            return ShotEffect.duplicateResult();
        }
        if (projectiles.containsKey(request.projectileId())) return ShotEffect.duplicateResult();

        ActorState actor = actors.computeIfAbsent(request.action().actorId(), ignored -> new ActorState());
        if (actor.pendingAction != null) loseCharge(actor);
        actor.pendingAction = key;

        double penetration = 0.0D;
        double impact = 0.0D;
        double damage = 0.0D;
        if (request.a0054Rank() > 0 && actor.adjustedUntilMillis > nowMillis && actor.charges == MAX_CHARGES) {
            damage = 0.15D;
            actor.charges = 0;
            actor.adjustedUntilMillis = 0L;
        } else if (request.a0053Rank() > 0 && actor.charges >= 2
            && (request.penetrationProviderAvailable() || request.impactProviderAvailable())) {
            actor.charges -= 2;
            if (request.penetrationProviderAvailable()) penetration = request.a0053Rank() >= 2 ? 0.15D : 0.10D;
            if (request.impactProviderAvailable()) impact = request.a0053Rank() >= 2 ? 0.25D : 0.15D;
        }

        ShotEffect effect = new ShotEffect(false, damage, penetration, impact);
        ActionShot shot = new ActionShot(effect, Math.addExact(nowMillis, PROJECTILE_RETENTION_MILLIS));
        resolvedShots.put(key, shot);
        projectiles.put(request.projectileId(), new ProjectileState(
            request.action(), request.stackIdentity(), shot, false));
        return effect;
    }

    public synchronized boolean confirmHit(String projectileId, CanonicalActionIdentity action, long nowMillis) {
        Objects.requireNonNull(projectileId);
        Objects.requireNonNull(action);
        requireNow(nowMillis);
        prune(nowMillis);
        ProjectileState projectile = projectiles.get(projectileId);
        if (projectile == null || !projectile.action.sameAction(action) || projectile.hitConfirmed) return false;
        projectile.hitConfirmed = true;
        ActorState actor = actors.computeIfAbsent(action.actorId(), ignored -> new ActorState());
        actor.lastHitStackIdentity = projectile.stackIdentity;
        actor.lastHitAtMillis = nowMillis;
        if (ActionKey.of(action).equals(actor.pendingAction)) actor.pendingAction = null;
        return true;
    }

    /** Called only after an observed uncharged -> charged transition with native ammo consumption. */
    public synchronized ReloadResult completeReload(ReloadRequest request, long nowMillis) {
        Objects.requireNonNull(request);
        requireNow(nowMillis);
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.nativeAmmoConsumed()
            || !request.sameStackTransition() || request.a0052Rank() <= 0 || request.stackIdentity().isBlank()) {
            return ReloadResult.rejected(charges(request.actorId()));
        }
        ActorState actor = actors.computeIfAbsent(request.actorId(), ignored -> new ActorState());
        long window = request.a0052Rank() >= 2 ? 8_000L : 6_000L;
        if (!request.stackIdentity().equals(actor.lastHitStackIdentity)
            || actor.lastHitAtMillis < 0L || nowMillis - actor.lastHitAtMillis > window) {
            return ReloadResult.rejected(actor.charges);
        }
        boolean armed = false;
        if (actor.charges == MAX_CHARGES && request.a0054Rank() > 0) {
            long duration = request.crossbowMastery() >= 100 ? 10_000L : request.crossbowMastery() >= 90 ? 9_000L : 8_000L;
            actor.adjustedUntilMillis = Math.addExact(nowMillis, duration);
            armed = true;
        } else {
            actor.charges = Math.min(MAX_CHARGES, actor.charges + 1);
        }
        actor.lastHitStackIdentity = null;
        actor.lastHitAtMillis = -1L;
        return new ReloadResult(true, actor.charges, armed);
    }

    /** Claims first-impact modifiers once; subsequent piercing/duplicate callbacks receive no effect. */
    public synchronized Optional<ShotEffect> claimFirstImpact(
        String projectileId,
        CanonicalActionIdentity action,
        long nowMillis
    ) {
        requireNow(nowMillis);
        prune(nowMillis);
        ProjectileState projectile = projectiles.get(projectileId);
        if (projectile == null || projectile.shot.impactClaimed || !projectile.action.sameAction(action)) return Optional.empty();
        projectile.shot.impactClaimed = true;
        return Optional.of(projectile.shot.effect);
    }

    public synchronized void missOrCancel(String actorId) {
        ActorState actor = actors.get(actorId);
        if (actor == null) return;
        loseCharge(actor);
        actor.pendingAction = null;
    }

    public synchronized int charges(String actorId) {
        ActorState actor = actors.get(actorId);
        return actor == null ? 0 : actor.charges;
    }

    /** Transient combo state clears; canonical action claims deliberately remain retained. */
    public synchronized void clearTransient(String actorId) { actors.remove(actorId); }

    private static boolean eligible(CanonicalActionIdentity action, boolean server, boolean actor, boolean direct) {
        return server && actor && direct && ProcGuard.mayTriggerSecondaryEffect(action.origin());
    }

    private static void loseCharge(ActorState actor) {
        actor.charges = Math.max(0, actor.charges - 1);
        actor.adjustedUntilMillis = 0L;
    }

    private void prune(long nowMillis) {
        projectiles.entrySet().removeIf(entry -> entry.getValue().shot.expiresAtMillis <= nowMillis);
        resolvedShots.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    public record ShotRequest(
        CanonicalActionIdentity action,
        String projectileId,
        String stackIdentity,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean directProjectile,
        boolean fullyCharged,
        int a0053Rank,
        int a0054Rank,
        boolean penetrationProviderAvailable,
        boolean impactProviderAvailable
    ) {
        public ShotRequest { Objects.requireNonNull(action); Objects.requireNonNull(projectileId); Objects.requireNonNull(stackIdentity); }
    }

    public record ReloadRequest(
        String actorId,
        String stackIdentity,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean sameStackTransition,
        boolean nativeAmmoConsumed,
        int a0052Rank,
        int a0054Rank,
        int crossbowMastery
    ) {
        public ReloadRequest { Objects.requireNonNull(actorId); Objects.requireNonNull(stackIdentity); }
    }

    public record ShotEffect(boolean duplicate, double damageBonus, double penetrationBonus, double impactBonus) {
        static ShotEffect none() { return new ShotEffect(false, 0.0D, 0.0D, 0.0D); }
        static ShotEffect duplicateResult() { return new ShotEffect(true, 0.0D, 0.0D, 0.0D); }
        public boolean active() { return damageBonus > 0.0D || penetrationBonus > 0.0D || impactBonus > 0.0D; }
    }

    public record ReloadResult(boolean credited, int charges, boolean adjustedMechanismArmed) {
        static ReloadResult rejected(int charges) { return new ReloadResult(false, charges, false); }
    }

    private static final class ActorState {
        int charges;
        ActionKey pendingAction;
        String lastHitStackIdentity;
        long lastHitAtMillis = -1L;
        long adjustedUntilMillis;
    }

    private static final class ProjectileState {
        final CanonicalActionIdentity action;
        final String stackIdentity;
        final ActionShot shot;
        boolean hitConfirmed;

        ProjectileState(CanonicalActionIdentity action, String stackIdentity, ActionShot shot, boolean hitConfirmed) {
            this.action = action;
            this.stackIdentity = stackIdentity;
            this.shot = shot;
            this.hitConfirmed = hitConfirmed;
        }
    }

    private static final class ActionShot {
        final ShotEffect effect;
        final long expiresAtMillis;
        boolean impactClaimed;

        ActionShot(ShotEffect effect, long expiresAtMillis) {
            this.effect = effect;
            this.expiresAtMillis = expiresAtMillis;
        }
    }

    private record ActionKey(String actorId, String actionId) {
        static ActionKey of(CanonicalActionIdentity action) { return new ActionKey(action.actorId(), action.actionId()); }
    }
}
