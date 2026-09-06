package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PressureSampleTest {
    @Test
    void totalExternalPressureRemainsFiniteForValidComponents() {
        PressureSample sample = new PressureSample(1.0, 2.5);

        assertEquals(3.5, sample.totalExternalAtm(), 1.0e-9);
    }

    @Test
    void totalExternalPressureRejectsFiniteComponentOverflow() {
        PressureSample sample = new PressureSample(Double.MAX_VALUE, Double.MAX_VALUE);

        assertThrows(IllegalStateException.class, sample::totalExternalAtm);
    }
}
