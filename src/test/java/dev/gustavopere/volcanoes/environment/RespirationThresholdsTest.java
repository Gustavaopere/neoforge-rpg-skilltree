package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class RespirationThresholdsTest {
    @Test
    void co2FractionThresholdsCannotExceedTheAtmosphereFractionDomain() {
        assertThrows(IllegalArgumentException.class, () -> thresholds(1.01, 1.01));
        assertThrows(IllegalArgumentException.class, () -> thresholds(0.02, 1.01));
    }

    private static RespirationThresholds thresholds(double co2, double severeCo2) {
        return new RespirationThresholds(
                0.16,
                0.10,
                co2,
                severeCo2,
                10.0,
                25.0,
                5.0,
                5.0,
                4,
                1,
                3);
    }
}
