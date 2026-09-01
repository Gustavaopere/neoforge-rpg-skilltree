package dev.gustavopere.volcanoes.compat.create;

import dev.gustavopere.volcanoes.environment.AtmosphereState;
import dev.gustavopere.volcanoes.environment.RespirationProtection;
import dev.gustavopere.volcanoes.environment.RespirationThresholds;

import java.util.Objects;

/**
 * Host-neutral decision for when Create pressurized air may satisfy Atmosphere hypoxia.
 *
 * <p>This class does not inspect or mutate Create equipment. It only decides whether the
 * verified host adapter should expose oxygen and whether one Create air unit is due on the
 * host's native 20-tick debit cadence.</p>
 */
public record CreateRespirationDecision(
        RespirationProtection protection,
        int airDebitUnits
) {
    public static final double BREATHABLE_OXYGEN_PARTIAL_PRESSURE_ATM =
            RespirationThresholds.defaults().minimumOxygenPartialPressureAtm();
    public static final int CREATE_AIR_DEBIT_INTERVAL_TICKS = 20;

    public CreateRespirationDecision {
        protection = Objects.requireNonNull(protection, "protection");
        if (airDebitUnits < 0 || airDebitUnits > 1) {
            throw new IllegalArgumentException("airDebitUnits must be 0 or 1");
        }
    }

    public static CreateRespirationDecision evaluate(
            AtmosphereState atmosphere,
            boolean divingHelmetEquipped,
            boolean backtankHasAir,
            long gameTime
    ) {
        Objects.requireNonNull(atmosphere, "atmosphere");

        boolean hypoxic = atmosphere.oxygenPartialPressureAtm()
                < RespirationThresholds.defaults().minimumOxygenPartialPressureAtm();
        if (!hypoxic || !divingHelmetEquipped || !backtankHasAir) {
            return new CreateRespirationDecision(RespirationProtection.NONE, 0);
        }

        RespirationProtection oxygenOnly = RespirationProtection.of(
                0.0,
                0.0,
                0.0,
                BREATHABLE_OXYGEN_PARTIAL_PRESSURE_ATM);
        int debit = Math.floorMod(gameTime, CREATE_AIR_DEBIT_INTERVAL_TICKS) == 0 ? 1 : 0;
        return new CreateRespirationDecision(oxygenOnly, debit);
    }
}
