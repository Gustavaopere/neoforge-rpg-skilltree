package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class PressureExposureTrackerTest {
    private static final PressureExposureConfig CONFIG = new PressureExposureConfig(
            3,
            0.75,
            1.50,
            2.50,
            0.80,
            0.50,
            2.0);

    @Test
    void ordinarySwimmingPressureStaysNormalAndResetsExposure() {
        PressureExposureTracker tracker = new PressureExposureTracker();
        UUID entity = UUID.randomUUID();

        assertEquals(PressureExposureStage.NORMAL, tracker.update(entity, 0.20, CONFIG).stage());
        tracker.update(entity, 1.0, CONFIG);
        assertEquals(PressureExposureStage.NORMAL, tracker.update(entity, 0.20, CONFIG).stage());
    }

    @Test
    void zeroOverpressureStaysNormalEvenWhenDiscomfortThresholdIsZero() {
        PressureExposureTracker tracker = new PressureExposureTracker();
        UUID entity = UUID.randomUUID();
        PressureExposureConfig zeroThreshold = new PressureExposureConfig(
                0,
                0.0,
                0.5,
                1.0,
                0.80,
                0.50,
                2.0);

        assertEquals(PressureExposureStage.NORMAL, tracker.update(entity, 0.0, zeroThreshold).stage());
        assertEquals(PressureExposureStage.DISCOMFORT, tracker.update(entity, 0.01, zeroThreshold).stage());
        assertEquals(PressureExposureStage.NORMAL, tracker.update(entity, 0.0, zeroThreshold).stage());
    }

    @Test
    void hazardProgressesThroughGraceDiscomfortImpairmentAndBarotrauma() {
        PressureExposureTracker tracker = new PressureExposureTracker();
        UUID entity = UUID.randomUUID();

        assertEquals(PressureExposureStage.GRACE, tracker.update(entity, 1.0, CONFIG).stage());
        tracker.update(entity, 1.0, CONFIG);
        assertEquals(PressureExposureStage.GRACE, tracker.update(entity, 1.0, CONFIG).stage());

        PressureExposureResult discomfort = tracker.update(entity, 1.0, CONFIG);
        assertEquals(PressureExposureStage.DISCOMFORT, discomfort.stage());
        assertEquals(1.0, discomfort.movementMultiplier(), 1.0e-9);

        PressureExposureResult impairment = tracker.update(entity, 1.75, CONFIG);
        assertEquals(PressureExposureStage.IMPAIRED, impairment.stage());
        assertEquals(0.80, impairment.movementMultiplier(), 1.0e-9);
        assertEquals(0.50, impairment.neurologicalPenalty(), 1.0e-9);

        PressureExposureResult barotrauma = tracker.update(entity, 3.0, CONFIG);
        assertEquals(PressureExposureStage.BAROTRAUMA, barotrauma.stage());
        assertEquals(2.0, barotrauma.damage(), 1.0e-9);
    }

    @Test
    void pressureRatingReducesOnlyTheUnprotectedOverpressure() {
        assertEquals(0.0, PressureExposureTracker.unprotectedOverpressureAtm(3.0, 1.0, 2.0), 1.0e-9);
        assertEquals(1.5, PressureExposureTracker.unprotectedOverpressureAtm(4.0, 1.0, 1.5), 1.0e-9);
        assertThrows(IllegalArgumentException.class,
                () -> PressureExposureTracker.unprotectedOverpressureAtm(Double.NaN, 1.0, 0.0));
    }
}
