package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Stable identity for one server-authoritative action across provider callbacks.
 *
 * <p>The actor/action pair is the canonical key. The origin is audit metadata and may change from
 * PRE to POST without creating a second action. Derived effects keep the pair and increment
 * {@link ActionOrigin#procDepth()}.
 */
public record CanonicalActionIdentity(String actorId, String actionId, ActionOrigin origin) {
    public CanonicalActionIdentity {
        requireId(actorId, "actorId");
        requireId(actionId, "actionId");
        Objects.requireNonNull(origin);
    }

    public static CanonicalActionIdentity root(String actorId, String actionId, String sourceId) {
        return new CanonicalActionIdentity(actorId, actionId, new ActionOrigin(sourceId, 0));
    }

    /** Changes provider-stage metadata without changing the action or proc depth. */
    public CanonicalActionIdentity withSource(String sourceId) {
        return new CanonicalActionIdentity(actorId, actionId, new ActionOrigin(sourceId, origin.procDepth()));
    }

    /** Creates derived-effect metadata while retaining correlation with the root action. */
    public CanonicalActionIdentity child(String sourceId) {
        return new CanonicalActionIdentity(actorId, actionId, origin.child(sourceId));
    }

    public boolean sameAction(CanonicalActionIdentity other) {
        return other != null && actorId.equals(other.actorId) && actionId.equals(other.actionId);
    }

    private static void requireId(String value, String name) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
    }
}
