package dev.gustavopere.volcanoes.pressure;

import java.util.Objects;
import java.util.Optional;

/** Composes external pressure, reliable sealed interiors and pressure-rated equipment. */
public final class PressureEnvironmentResolver {
    private PressureEnvironmentResolver() {
    }

    public static PressureEnvironmentResult resolve(
            PressureSample external,
            Optional<EnclosedEnvironment> enclosedEnvironment,
            ProtectionSnapshot protection,
            ProtectionUseSession update
    ) {
        Objects.requireNonNull(external, "external");
        Objects.requireNonNull(enclosedEnvironment, "enclosedEnvironment");
        Objects.requireNonNull(protection, "protection");
        Objects.requireNonNull(update, "update");

        double externalPressureAtm = external.totalExternalAtm();
        Optional<EnclosedEnvironment> protectedInterior = enclosedEnvironment
                .filter(EnclosedEnvironment::protectsFromExternalPressure);
        if (protectedInterior.isPresent()) {
            return new PressureEnvironmentResult(
                    true,
                    externalPressureAtm,
                    protectedInterior.orElseThrow().internalPressureAtm(),
                    0.0,
                    0.0,
                    0.0);
        }

        double unprotectedOverpressureAtm = external.hydrostaticAtm();
        double appliedRatingAtm = 0.0;
        if (unprotectedOverpressureAtm > 0.0) {
            double activatedRatingAtm = update.activatedRating(
                    ProtectionCapability.PRESSURE_RATING,
                    unprotectedOverpressureAtm);
            appliedRatingAtm = Math.min(activatedRatingAtm, unprotectedOverpressureAtm);
        }

        return new PressureEnvironmentResult(
                false,
                externalPressureAtm,
                externalPressureAtm,
                unprotectedOverpressureAtm,
                Math.max(0.0, unprotectedOverpressureAtm - appliedRatingAtm),
                appliedRatingAtm);
    }
}
