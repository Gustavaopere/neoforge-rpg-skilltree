package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import dev.gustavopere.rpgskilltree.core.SpellAction;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/** Provider-free one-shot claim shared by all Ars contexts descended from one cast. */
final class ArsMasteryClaim {
    private final UUID casterId;
    private final SpellAction action;
    private final AtomicBoolean claimed = new AtomicBoolean(false);

    private ArsMasteryClaim(UUID casterId, SpellAction action) {
        this.casterId = Objects.requireNonNull(casterId, "casterId");
        this.action = Objects.requireNonNull(action, "action");
    }

    static ArsMasteryClaim arm(UUID casterId, SpellAction action) {
        return new ArsMasteryClaim(casterId, action);
    }

    SpellAction claimResolved(UUID resolverId) {
        if (!casterId.equals(resolverId)) return null;
        return claimed.compareAndSet(false, true) ? action : null;
    }
}
