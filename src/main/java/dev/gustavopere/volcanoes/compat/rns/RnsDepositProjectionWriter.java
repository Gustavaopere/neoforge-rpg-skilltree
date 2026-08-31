package dev.gustavopere.volcanoes.compat.rns;

import java.util.Objects;

/**
 * Minimal host-facing mutation surface used by the neutral RNS lifecycle bridge.
 *
 * <p>The bridge never assumes a mutation succeeded merely because it was attempted. Implementations
 * return {@code true} only when the requested RNS projection state is known to hold after the call.</p>
 */
public interface RnsDepositProjectionWriter {
    boolean ensurePresent(RnsDepositProjectionPlanner.Projection projection);

    boolean ensureAbsent(RnsDepositProjectionPlanner.Projection projection);

    /** Wraps an optional host writer so API/runtime failures fail closed instead of escaping. */
    static RnsDepositProjectionWriter failSafe(RnsDepositProjectionWriter delegate) {
        Objects.requireNonNull(delegate, "delegate");
        return new RnsDepositProjectionWriter() {
            @Override
            public boolean ensurePresent(RnsDepositProjectionPlanner.Projection projection) {
                Objects.requireNonNull(projection, "projection");
                try {
                    return delegate.ensurePresent(projection);
                } catch (RuntimeException | LinkageError failure) {
                    return false;
                }
            }

            @Override
            public boolean ensureAbsent(RnsDepositProjectionPlanner.Projection projection) {
                Objects.requireNonNull(projection, "projection");
                try {
                    return delegate.ensureAbsent(projection);
                } catch (RuntimeException | LinkageError failure) {
                    return false;
                }
            }
        };
    }
}
