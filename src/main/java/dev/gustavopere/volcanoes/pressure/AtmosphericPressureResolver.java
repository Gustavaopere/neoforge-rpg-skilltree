package dev.gustavopere.volcanoes.pressure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

/**
 * Selects exactly one atmospheric-pressure authority and falls back to the built-in curve when
 * no registered provider applies.
 *
 * <p>Providers are sorted only when registered. Sampling reads an immutable snapshot, so the
 * per-entity hot path performs no reflection, service discovery, or sorting.</p>
 */
public final class AtmosphericPressureResolver implements AtmosphericPressureLookup {
    private static final Comparator<AtmosphericPressureProvider> BY_PRIORITY_AND_ID =
            Comparator.comparingInt(AtmosphericPressureProvider::priority)
                    .reversed()
                    .thenComparing(AtmosphericPressureResolver::normalizedId);

    private final AtmosphericPressureLookup fallback;
    private volatile List<AtmosphericPressureProvider> providers = List.of();

    public AtmosphericPressureResolver(AtmosphericPressureLookup fallback) {
        this.fallback = Objects.requireNonNull(fallback, "fallback");
    }

    public synchronized void register(AtmosphericPressureProvider provider) {
        AtmosphericPressureProvider checked = Objects.requireNonNull(provider, "provider");
        String id = normalizedId(checked);
        for (AtmosphericPressureProvider existing : providers) {
            if (normalizedId(existing).equals(id)) {
                throw new IllegalArgumentException("duplicate atmospheric pressure provider id: " + id);
            }
        }

        ArrayList<AtmosphericPressureProvider> next = new ArrayList<>(providers);
        next.add(checked);
        next.sort(BY_PRIORITY_AND_ID);
        providers = List.copyOf(next);
    }

    @Override
    public double pressureAtm(String dimensionId, double altitudeY) {
        String checkedDimensionId = Objects.requireNonNull(dimensionId, "dimensionId");
        if (checkedDimensionId.isBlank()) {
            throw new IllegalArgumentException("dimensionId must not be blank");
        }
        if (!checkedDimensionId.equals(checkedDimensionId.trim())) {
            throw new IllegalArgumentException("dimensionId must not contain leading or trailing whitespace");
        }
        if (!Double.isFinite(altitudeY)) {
            throw new IllegalArgumentException("altitudeY must be finite");
        }

        for (AtmosphericPressureProvider provider : providers) {
            OptionalDouble sampled;
            try {
                sampled = provider.pressureAtm(checkedDimensionId, altitudeY);
            } catch (RuntimeException | LinkageError integrationFailure) {
                // A broken optional authority must not crash pressure sampling or fall through to another
                // external model whose semantics may be unrelated. The built-in datapack model is the
                // conservative Stage 05 fallback when an integration cannot be queried reliably.
                return fallbackPressure(checkedDimensionId, altitudeY);
            }
            if (sampled == null) {
                return fallbackPressure(checkedDimensionId, altitudeY);
            }
            if (sampled.isPresent()) {
                double pressureAtm = sampled.getAsDouble();
                if (!isValidPressure(pressureAtm)) {
                    return fallbackPressure(checkedDimensionId, altitudeY);
                }
                return pressureAtm;
            }
        }
        return fallbackPressure(checkedDimensionId, altitudeY);
    }

    private double fallbackPressure(String dimensionId, double altitudeY) {
        return requireValidPressure(fallback.pressureAtm(dimensionId, altitudeY), "built-in fallback");
    }

    private static String normalizedId(AtmosphericPressureProvider provider) {
        String id = Objects.requireNonNull(provider.id(), "provider id").trim();
        if (id.isEmpty()) {
            throw new IllegalArgumentException("provider id must not be blank");
        }
        return id;
    }

    private static boolean isValidPressure(double pressureAtm) {
        return Double.isFinite(pressureAtm) && pressureAtm >= 0.0;
    }

    private static double requireValidPressure(double pressureAtm, String authority) {
        if (!isValidPressure(pressureAtm)) {
            throw new IllegalStateException(authority + " returned invalid pressure: " + pressureAtm + " atm");
        }
        return pressureAtm;
    }
}
