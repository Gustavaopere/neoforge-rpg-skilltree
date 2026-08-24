package dev.gustavopere.rpgskilltree.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Bounded server-runtime correlation between provider events that do not share an event object. */
public final class CanonicalActionCorrelationService {
    private final long pendingRetentionMillis;
    private final long projectileRetentionMillis;
    private final int maxTracked;
    private final Map<String, Long> actorSequences = new LinkedHashMap<>();
    private final LinkedHashMap<MeleeKey, TimedAction> pendingMelee = new LinkedHashMap<>();
    private final LinkedHashMap<String, TimedAction> pendingShots = new LinkedHashMap<>();
    private final LinkedHashMap<String, ProjectileAction> projectiles = new LinkedHashMap<>();

    public CanonicalActionCorrelationService(long retentionMillis, int maxTracked) {
        this(retentionMillis, retentionMillis, maxTracked);
    }

    public CanonicalActionCorrelationService(
        long pendingRetentionMillis,
        long projectileRetentionMillis,
        int maxTracked
    ) {
        if (pendingRetentionMillis <= 0L) {
            throw new IllegalArgumentException("pendingRetentionMillis must be positive");
        }
        if (projectileRetentionMillis <= 0L) {
            throw new IllegalArgumentException("projectileRetentionMillis must be positive");
        }
        if (maxTracked <= 0) throw new IllegalArgumentException("maxTracked must be positive");
        this.pendingRetentionMillis = pendingRetentionMillis;
        this.projectileRetentionMillis = projectileRetentionMillis;
        this.maxTracked = maxTracked;
    }

    public synchronized CanonicalActionIdentity newRoot(String actorId, String sourceId, long nowMillis) {
        requireId(actorId, "actorId");
        requireId(sourceId, "sourceId");
        requireNow(nowMillis);
        long sequence = Math.incrementExact(actorSequences.getOrDefault(actorId, 0L));
        actorSequences.put(actorId, sequence);
        String actionId = Long.toUnsignedString(nowMillis, 36) + '-' + Long.toUnsignedString(sequence, 36);
        return CanonicalActionIdentity.root(actorId, actionId, sourceId);
    }

    public synchronized void recordMeleeDecision(
        CanonicalActionIdentity action,
        String targetId,
        long nowMillis
    ) {
        Objects.requireNonNull(action);
        requireId(targetId, "targetId");
        requireRoot(action);
        requireNow(nowMillis);
        prune(nowMillis);
        makeRoom(pendingMelee);
        pendingMelee.put(
            new MeleeKey(action.actorId(), targetId),
            new TimedAction(action, Math.addExact(nowMillis, pendingRetentionMillis))
        );
    }

    public synchronized Optional<CanonicalActionIdentity> claimMeleeForProvider(
        String actorId,
        String targetId,
        long nowMillis
    ) {
        requireId(actorId, "actorId");
        requireId(targetId, "targetId");
        requireNow(nowMillis);
        prune(nowMillis);
        TimedAction action = pendingMelee.remove(new MeleeKey(actorId, targetId));
        return action == null ? Optional.empty() : Optional.of(action.action.withSource("epicfight:damage_pre"));
    }

    public synchronized void recordShot(CanonicalActionIdentity shot, long nowMillis) {
        Objects.requireNonNull(shot);
        requireRoot(shot);
        requireNow(nowMillis);
        prune(nowMillis);
        makeRoom(pendingShots);
        pendingShots.put(
            shot.actorId(),
            new TimedAction(shot, Math.addExact(nowMillis, pendingRetentionMillis))
        );
    }

    public synchronized Optional<CanonicalActionIdentity> correlateProjectile(
        String ownerActorId,
        String projectileId,
        long nowMillis
    ) {
        requireId(ownerActorId, "ownerActorId");
        requireId(projectileId, "projectileId");
        requireNow(nowMillis);
        prune(nowMillis);
        ProjectileAction previous = projectiles.get(projectileId);
        if (previous != null) {
            return previous.action.actorId().equals(ownerActorId)
                ? Optional.of(previous.action)
                : Optional.empty();
        }
        TimedAction shot = pendingShots.get(ownerActorId);
        if (shot == null) return Optional.empty();
        makeRoom(projectiles);
        projectiles.put(
            projectileId,
            new ProjectileAction(shot.action, Math.addExact(nowMillis, projectileRetentionMillis))
        );
        return Optional.of(shot.action.withSource("neoforge:projectile_spawn"));
    }

    public synchronized Optional<CanonicalActionIdentity> projectileAction(String projectileId, long nowMillis) {
        requireId(projectileId, "projectileId");
        requireNow(nowMillis);
        prune(nowMillis);
        ProjectileAction action = projectiles.get(projectileId);
        return action == null
            ? Optional.empty()
            : Optional.of(action.action.withSource("neoforge:projectile"));
    }

    public synchronized void clearActor(String actorId) {
        requireId(actorId, "actorId");
        actorSequences.remove(actorId);
        pendingMelee.keySet().removeIf(key -> key.actorId.equals(actorId));
        pendingShots.remove(actorId);
        projectiles.entrySet().removeIf(entry -> entry.getValue().action.actorId().equals(actorId));
    }

    private void prune(long nowMillis) {
        pendingMelee.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
        pendingShots.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
        projectiles.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis <= nowMillis);
    }

    private <K, V> void makeRoom(LinkedHashMap<K, V> map) {
        while (map.size() >= maxTracked) {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
    }

    private static void requireRoot(CanonicalActionIdentity action) {
        if (!ProcGuard.mayTriggerSecondaryEffect(action.origin())) {
            throw new IllegalArgumentException("only root actions may be correlated");
        }
    }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    private static void requireId(String value, String name) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
    }

    private record MeleeKey(String actorId, String targetId) {}

    private record TimedAction(CanonicalActionIdentity action, long expiresAtMillis) {}

    private record ProjectileAction(CanonicalActionIdentity action, long expiresAtMillis) {}
}
