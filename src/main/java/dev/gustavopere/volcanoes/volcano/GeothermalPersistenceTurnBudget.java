package dev.gustavopere.volcanoes.volcano;

/** Shared hard allocation for transient and durable geothermal persistence work. */
public final class GeothermalPersistenceTurnBudget {
    private static final int MAX_ATTEMPTS_PER_TICK = 16;
    private static final int MAX_DURABLE_HANDOFFS_PER_CHUNK = 8;

    private GeothermalPersistenceTurnBudget() {
    }

    public static Allocation allocate(boolean hasTransientWork, boolean hasRecoveryWork) {
        if (hasTransientWork && hasRecoveryWork) {
            return new Allocation(8, 1);
        }
        if (hasTransientWork) {
            return new Allocation(MAX_ATTEMPTS_PER_TICK, 0);
        }
        if (hasRecoveryWork) {
            return new Allocation(0, MAX_ATTEMPTS_PER_TICK / MAX_DURABLE_HANDOFFS_PER_CHUNK);
        }
        return new Allocation(0, 0);
    }

    public record Allocation(int transientAttempts, int recoveryChunks) {
        public Allocation {
            if (transientAttempts < 0 || recoveryChunks < 0) {
                throw new IllegalArgumentException("allocation values must be non-negative");
            }
            int worstCaseAttempts = transientAttempts + recoveryChunks * MAX_DURABLE_HANDOFFS_PER_CHUNK;
            if (worstCaseAttempts > MAX_ATTEMPTS_PER_TICK) {
                throw new IllegalArgumentException("allocation exceeds global persistence attempt budget");
            }
        }
    }
}
