package dev.gustavopere.volcanoes.environment;

/** Atmosphere-owned conversion limits for normalized Stage-03 volcanic ash strengths. */
public record AshAtmosphereProjectionPolicy(
        double maxParticulatesMgM3,
        double maxSmokeMgM3
) {
    public AshAtmosphereProjectionPolicy {
        if (!Double.isFinite(maxParticulatesMgM3) || maxParticulatesMgM3 <= 0.0) {
            throw new IllegalArgumentException("maxParticulatesMgM3 must be finite and positive");
        }
        if (!Double.isFinite(maxSmokeMgM3) || maxSmokeMgM3 <= 0.0) {
            throw new IllegalArgumentException("maxSmokeMgM3 must be finite and positive");
        }
    }

    public static AshAtmosphereProjectionPolicy defaults() {
        return new AshAtmosphereProjectionPolicy(8.0, 6.0);
    }
}
