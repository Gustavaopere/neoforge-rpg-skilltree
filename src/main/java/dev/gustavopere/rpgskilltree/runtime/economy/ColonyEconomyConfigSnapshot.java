package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import java.util.Objects;

/** Immutable validated server-side economy configuration consumed by runtime services. */
public record ColonyEconomyConfigSnapshot(
    boolean enabled,
    long settlementIntervalTicks,
    long maxMutationAmount,
    EconomyParameters parameters
) {
    public ColonyEconomyConfigSnapshot {
        if (settlementIntervalTicks <= 0L) {
            throw new IllegalArgumentException("settlementIntervalTicks must be positive");
        }
        if (maxMutationAmount <= 0L) {
            throw new IllegalArgumentException("maxMutationAmount must be positive");
        }
        parameters = Objects.requireNonNull(parameters, "parameters");
    }

    public static ColonyEconomyConfigSnapshot defaults() {
        return new ColonyEconomyConfigSnapshot(
            true,
            1_200L,
            Long.MAX_VALUE,
            EconomyParameters.defaults()
        );
    }
}
