package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Thread-safe progression-rules provider whose authoritative snapshot is
 * installed explicitly by the runtime data layer.
 *
 * <p>No default rules exist here. Until a snapshot is installed, the provider
 * remains unconfigured and {@link #requireCurrent()} fails closed.</p>
 */
public final class InstallableProgressionRulesProvider implements ProgressionRulesProvider {
    private final AtomicReference<ProgressionRulesSnapshot> current = new AtomicReference<>();

    @Override
    public Optional<ProgressionRulesSnapshot> current() {
        return Optional.ofNullable(current.get());
    }

    /** Atomically installs {@code rules}, returning the previously active snapshot if any. */
    public Optional<ProgressionRulesSnapshot> install(ProgressionRulesSnapshot rules) {
        Objects.requireNonNull(rules, "rules");
        return Optional.ofNullable(current.getAndSet(rules));
    }

    /** Atomically removes the active snapshot and returns the previous value if any. */
    public Optional<ProgressionRulesSnapshot> clear() {
        return Optional.ofNullable(current.getAndSet(null));
    }
}
