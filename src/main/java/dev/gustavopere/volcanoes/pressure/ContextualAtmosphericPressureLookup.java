package dev.gustavopere.volcanoes.pressure;

import java.util.OptionalDouble;

/**
 * Optional contextual atmospheric-pressure authority for moving or projected environments.
 *
 * <p>The supplied Y is the atmospheric sampling height selected after water-depth resolution.
 * Returning empty delegates to the normal dimension pressure resolver.</p>
 */
@FunctionalInterface
public interface ContextualAtmosphericPressureLookup {
    OptionalDouble pressureAtm(PressureEntityContext context, double atmosphericSampleY);
}
