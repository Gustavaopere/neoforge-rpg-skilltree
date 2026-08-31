package dev.gustavopere.volcanoes.pressure;

import java.util.Optional;

/**
 * Optional integration SPI. Empty means not applicable. A present but unreliable/flooded state is authoritative
 * and must fail closed rather than allowing a lower-priority provider to grant protection.
 */
public interface EnclosedEnvironmentProvider {
    int priority();

    Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query);
}
