package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Safety policy for replacing the authoritative progression rules while a server is live. */
public final class ProgressionRulesTransitionPolicy {
    private ProgressionRulesTransitionPolicy() {}

    public static ProgressionRulesTransition classify(
        Optional<ProgressionRulesSnapshot> current,
        ProgressionRulesSnapshot next
    ) {
        Objects.requireNonNull(current, "current");
        Objects.requireNonNull(next, "next");
        if (current.isEmpty()) {
            return ProgressionRulesTransition.INITIAL_INSTALL;
        }

        ProgressionRulesSnapshot active = current.orElseThrow();
        if (!active.rulesId().equals(next.rulesId())) {
            return ProgressionRulesTransition.RULES_ID_CHANGED;
        }
        if (active.version() != next.version()) {
            return ProgressionRulesTransition.VERSION_CHANGED;
        }
        if (!active.fingerprint().equals(next.fingerprint())) {
            return ProgressionRulesTransition.SAME_VERSION_CONTENT_CHANGED;
        }
        return ProgressionRulesTransition.IDENTICAL_RELOAD;
    }

    /**
     * Allows only first installation or an exact idempotent reload. Any actual
     * rules change needs an explicit migration boundary before it can become live.
     */
    public static ProgressionRulesTransition requireLiveReloadSafe(
        Optional<ProgressionRulesSnapshot> current,
        ProgressionRulesSnapshot next
    ) {
        ProgressionRulesTransition transition = classify(current, next);
        return switch (transition) {
            case INITIAL_INSTALL, IDENTICAL_RELOAD -> transition;
            case SAME_VERSION_CONTENT_CHANGED -> throw new IllegalStateException(
                "Core progression rules content changed without a rules version bump"
            );
            case VERSION_CHANGED -> throw new IllegalStateException(
                "Core progression rules version changed; explicit state migration is required"
            );
            case RULES_ID_CHANGED -> throw new IllegalStateException(
                "Core progression rules identity changed; explicit state migration is required"
            );
        };
    }
}
