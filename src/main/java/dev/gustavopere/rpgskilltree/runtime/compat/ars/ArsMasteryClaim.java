package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import dev.gustavopere.rpgskilltree.core.SpellAction;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/** Provider-free one-shot claim shared by all Ars contexts descended from one cast. */
final class ArsMasteryClaim {
    private final SpellAction action;
    private final AtomicBoolean claimed = new AtomicBoolean(false);

    private ArsMasteryClaim(SpellAction action) {
        this.action = Objects.requireNonNull(action, "action");
    }

    static ArsMasteryClaim arm(SpellAction action) {
        return new ArsMasteryClaim(action);
    }

    SpellAction claimResolved() {
        return claimed.compareAndSet(false, true) ? action : null;
    }
}
