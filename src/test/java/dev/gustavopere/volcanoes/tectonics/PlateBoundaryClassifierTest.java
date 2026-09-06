package dev.gustavopere.volcanoes.tectonics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PlateBoundaryClassifierTest {
    private final PlateBoundaryClassifier classifier = new PlateBoundaryClassifier();

    @Test
    void classifiesConvergentMotionAlongBoundaryNormal() {
        PlateBoundarySample sample = classifier.classify(sample(
                PlateVector.fromAngle(0.0),
                PlateVector.fromAngle(Math.PI),
                new PlateVector(1.0, 0.0),
                128.0,
                0.0));

        assertEquals(BoundaryType.CONVERGENT, sample.type());
        assertTrue(sample.normalRelativeSpeed() > 1.9);
        assertTrue(sample.volcanicPotential() >= 0.8);
    }

    @Test
    void classifiesDivergentMotionAwayFromBoundaryNormal() {
        PlateBoundarySample sample = classifier.classify(sample(
                PlateVector.fromAngle(Math.PI),
                PlateVector.fromAngle(0.0),
                new PlateVector(1.0, 0.0),
                128.0,
                0.0));

        assertEquals(BoundaryType.DIVERGENT, sample.type());
        assertTrue(sample.normalRelativeSpeed() < -1.9);
        assertTrue(sample.volcanicPotential() >= 0.45);
        assertTrue(sample.volcanicPotential() < 0.8);
    }

    @Test
    void classifiesTransformMotionFromTangentialShear() {
        PlateBoundarySample sample = classifier.classify(sample(
                PlateVector.fromAngle(Math.PI / 2.0),
                PlateVector.fromAngle(-Math.PI / 2.0),
                new PlateVector(1.0, 0.0),
                128.0,
                0.0));

        assertEquals(BoundaryType.TRANSFORM, sample.type());
        assertTrue(sample.shearSpeed() > 1.9);
        assertTrue(sample.volcanicPotential() < 0.25);
    }

    @Test
    void distantLocationsAreInteriorEvenWhenPlateMotionsWouldConverge() {
        PlateBoundarySample sample = classifier.classify(sample(
                PlateVector.fromAngle(0.0),
                PlateVector.fromAngle(Math.PI),
                new PlateVector(1.0, 0.0),
                8_000.0,
                0.0));

        assertEquals(BoundaryType.INTERIOR, sample.type());
        assertTrue(sample.volcanicPotential() < 0.2);
    }

    @Test
    void independentHotspotRaisesInteriorVolcanicPotential() {
        PlateBoundarySample sample = classifier.classify(sample(
                PlateVector.fromAngle(0.0),
                PlateVector.fromAngle(0.0),
                new PlateVector(1.0, 0.0),
                8_000.0,
                1.0));

        assertEquals(BoundaryType.INTERIOR, sample.type());
        assertTrue(sample.volcanicPotential() >= 0.9);
    }

    private static PlateSample sample(
            PlateVector motion,
            PlateVector neighborMotion,
            PlateVector boundaryNormal,
            double boundaryDistance,
            double hotspotIntensity
    ) {
        return new PlateSample(
                new PlateId(1L),
                0.0,
                0.0,
                motion,
                new PlateId(2L),
                neighborMotion,
                boundaryNormal,
                boundaryDistance,
                hotspotIntensity);
    }
}
