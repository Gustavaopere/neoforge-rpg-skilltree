package dev.gustavopere.volcanoes.pressure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

/** Setup-time registry for optional contextual pressure authorities such as moving sub-levels. */
public final class ContextualAtmosphericPressureRuntime {
    private static volatile List<ContextualAtmosphericPressureLookup> providers = List.of();

    private ContextualAtmosphericPressureRuntime() {
    }

    public static synchronized void register(ContextualAtmosphericPressureLookup provider) {
        ContextualAtmosphericPressureLookup checked = Objects.requireNonNull(provider, "provider");
        ArrayList<ContextualAtmosphericPressureLookup> updated = new ArrayList<>(providers);
        updated.add(checked);
        providers = List.copyOf(updated);
    }

    public static OptionalDouble pressureAtm(PressureEntityContext context, double atmosphericSampleY) {
        Objects.requireNonNull(context, "context");
        if (!Double.isFinite(atmosphericSampleY)) {
            return OptionalDouble.empty();
        }

        for (ContextualAtmosphericPressureLookup provider : providers) {
            OptionalDouble sampled;
            try {
                sampled = provider.pressureAtm(context, atmosphericSampleY);
            } catch (RuntimeException | LinkageError optionalHostFailure) {
                return OptionalDouble.empty();
            }
            if (sampled == null) {
                return OptionalDouble.empty();
            }
            if (sampled.isEmpty()) {
                continue;
            }
            double pressureAtm = sampled.getAsDouble();
            if (!Double.isFinite(pressureAtm) || pressureAtm < 0.0) {
                return OptionalDouble.empty();
            }
            return OptionalDouble.of(pressureAtm);
        }
        return OptionalDouble.empty();
    }
}
