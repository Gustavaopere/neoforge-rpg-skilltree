package dev.gustavopere.volcanoes.environment;

@FunctionalInterface
public interface AtmosphereService {
    AtmosphereState sample(String dimensionId, long worldSeed, double x, double y, double z);

    static AtmosphereService fallback() {
        return (dimensionId, worldSeed, x, y, z) -> AtmosphereState.standardOverworld();
    }
}
