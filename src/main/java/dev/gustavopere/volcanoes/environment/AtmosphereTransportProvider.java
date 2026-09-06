package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

@FunctionalInterface
public interface AtmosphereTransportProvider {
    AtmosphereTransport sample(AtmosphericSource source);

    static AtmosphereTransportProvider stillAir() {
        return source -> {
            Objects.requireNonNull(source, "source");
            return AtmosphereTransport.stillAir();
        };
    }
}
