package dev.gustavopere.volcanoes.pressure;

import java.util.List;
import java.util.Objects;

/** Loader-neutral player state needed for one server pressure tick. */
public record PressurePlayerTickSnapshot(
        PressureEntityContext context,
        boolean immersedInWater,
        List<ProtectionContribution> hostResolvedContributions
) {
    public PressurePlayerTickSnapshot {
        context = Objects.requireNonNull(context, "context");
        hostResolvedContributions = List.copyOf(Objects.requireNonNull(
                hostResolvedContributions,
                "hostResolvedContributions"));
        if (hostResolvedContributions.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("hostResolvedContributions must not contain null");
        }
    }

    public PressurePlayerTickSnapshot(
            PressureEntityContext context,
            boolean immersedInWater
    ) {
        this(context, immersedInWater, List.of());
    }
}
