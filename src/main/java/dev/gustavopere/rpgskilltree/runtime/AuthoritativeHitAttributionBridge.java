package dev.gustavopere.rpgskilltree.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Provider-neutral correlation boundary for one authoritative combat hit.
 *
 * <p>The source object is intentionally opaque so common runtime code never classloads an optional
 * combat provider. The first provider that publishes a root for a source/target pair owns that
 * lineage; observers may reuse it without consuming the attribution. Entries are explicitly
 * discarded by the provider POST hook and source keys are weak as a fail-safe.</p>
 */
public final class AuthoritativeHitAttributionBridge {
    private static final Map<Object, Map<UUID, Attribution>> ATTRIBUTIONS = new WeakHashMap<>();

    private AuthoritativeHitAttributionBridge() {}

    public static synchronized Attribution canonicalize(
        Object damageSource,
        UUID targetId,
        String candidateRootActionId,
        String providerId
    ) {
        Objects.requireNonNull(damageSource, "damageSource");
        Objects.requireNonNull(targetId, "targetId");
        Objects.requireNonNull(candidateRootActionId, "candidateRootActionId");
        Objects.requireNonNull(providerId, "providerId");
        if (candidateRootActionId.isBlank()) {
            throw new IllegalArgumentException("candidateRootActionId must not be blank");
        }
        if (providerId.isBlank()) throw new IllegalArgumentException("providerId must not be blank");

        Map<UUID, Attribution> byTarget = ATTRIBUTIONS.computeIfAbsent(damageSource, ignored -> new HashMap<>());
        return byTarget.computeIfAbsent(targetId, ignored -> new Attribution(candidateRootActionId, providerId));
    }

    public static synchronized Optional<Attribution> find(Object damageSource, UUID targetId) {
        if (damageSource == null || targetId == null) return Optional.empty();
        Map<UUID, Attribution> byTarget = ATTRIBUTIONS.get(damageSource);
        return byTarget == null ? Optional.empty() : Optional.ofNullable(byTarget.get(targetId));
    }

    public static synchronized void discard(Object damageSource, UUID targetId) {
        if (damageSource == null || targetId == null) return;
        Map<UUID, Attribution> byTarget = ATTRIBUTIONS.get(damageSource);
        if (byTarget == null) return;
        byTarget.remove(targetId);
        if (byTarget.isEmpty()) ATTRIBUTIONS.remove(damageSource);
    }

    public static synchronized void clearAll() {
        ATTRIBUTIONS.clear();
    }

    public record Attribution(String rootActionId, String providerId) {
        public Attribution {
            Objects.requireNonNull(rootActionId, "rootActionId");
            Objects.requireNonNull(providerId, "providerId");
        }
    }
}
