package dev.gustavopere.volcanoes.pressure;

import java.util.OptionalDouble;

/**
 * Optional atmospheric-pressure authority contributed by an integration.
 *
 * <p>Returning {@link OptionalDouble#empty()} means the provider is not authoritative for the
 * requested dimension/altitude. A present value declares authority and must be finite and
 * non-negative. Providers are direct adapters; implementations must not perform reflection or
 * unbounded discovery from the sampling path.</p>
 *
 * <p>If an optional provider cannot be queried because it throws a runtime/linkage failure, or
 * violates the SPI by returning {@code null}, the resolver abandons external authorities for that
 * sample and uses the built-in datapack pressure model. It does not fall through to a lower-priority
 * external provider. A present but non-finite/negative pressure remains an explicit contract error
 * rather than being silently replaced.</p>
 */
public interface AtmosphericPressureProvider {
    String id();

    int priority();

    OptionalDouble pressureAtm(String dimensionId, double altitudeY);
}
