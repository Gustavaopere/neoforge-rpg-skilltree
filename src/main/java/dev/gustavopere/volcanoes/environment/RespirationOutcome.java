package dev.gustavopere.volcanoes.environment;

import java.util.Objects;
import java.util.Set;

public record RespirationOutcome(
        boolean canBreathe,
        int consumeAirAmount,
        int refillAirAmount,
        Set<AtmosphericHazard> hazards
) {
    public RespirationOutcome {
        if (consumeAirAmount < 0 || refillAirAmount < 0) {
            throw new IllegalArgumentException("air amounts must be non-negative");
        }
        hazards = Set.copyOf(Objects.requireNonNull(hazards, "hazards"));
    }
}
