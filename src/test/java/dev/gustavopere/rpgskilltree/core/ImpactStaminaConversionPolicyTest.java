package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.ImpactStaminaConversionPolicy.PressureClass;
import dev.gustavopere.rpgskilltree.core.ImpactStaminaConversionPolicy.Quote;
import java.util.Optional;

public final class ImpactStaminaConversionPolicyTest {
    private ImpactStaminaConversionPolicyTest() {}

    public static void main(String[] args) {
        nonePressureQuotesRequestedFractionWithoutRetroactiveConstraint();
        shortPressureStopsAtStunShieldBoundary();
        longPressureStopsAtStunShieldBoundary();
        shortAndLongNeverCrossPreviousDecisionBoundary();
        zeroSafeReductionProducesNoQuote();
        inconsistentShortLongSnapshotFailsClosed();
        specialControlsFailClosed();
        nonPositiveImpactFailsClosed();
        nonFiniteImpactFailsClosed();
        invalidFractionFailsClosed();
        invalidShieldSnapshotFailsClosed();
        arithmeticPreservesNativeDoublePrecisionWithoutRounding();
        System.out.println("ImpactStaminaConversionPolicyTest: PASS");
    }

    private static void nonePressureQuotesRequestedFractionWithoutRetroactiveConstraint() {
        Quote q = quote(10.0D, 0.0D, PressureClass.NONE, 0.35D);
        require(close(q.reducedImpact(), 6.5D), "35% of 10 leaves 6.5");
        require(close(q.staminaCost(), 3.5D), "cost equals converted pressure");
    }

    private static void shortPressureStopsAtStunShieldBoundary() {
        Quote q = quote(10.0D, 8.0D, PressureClass.SHORT, 0.35D);
        require(close(q.reducedImpact(), 8.0D), "SHORT cannot cross shield boundary");
        require(close(q.staminaCost(), 2.0D), "SHORT charges only safe pressure");
    }

    private static void longPressureStopsAtStunShieldBoundary() {
        Quote q = quote(10.0D, 7.25D, PressureClass.LONG, 0.35D);
        require(close(q.reducedImpact(), 7.25D), "LONG cannot cross shield boundary");
        require(close(q.staminaCost(), 2.75D), "LONG charges only safe pressure");
    }

    private static void shortAndLongNeverCrossPreviousDecisionBoundary() {
        for (PressureClass p : new PressureClass[] {PressureClass.SHORT, PressureClass.LONG}) {
            Quote q = quote(9.0D, 6.2D, p, 0.35D);
            require(q.reducedImpact() >= 6.2D, p + " crossed prior decision boundary");
            require(q.reducedImpact() <= 9.0D, p + " increased impact");
        }
    }

    private static void zeroSafeReductionProducesNoQuote() {
        require(ImpactStaminaConversionPolicy.quote(10.0D, 10.0D, PressureClass.SHORT, 0.35D).isEmpty(),
            "SHORT at equal shield has zero safe conversion");
        require(ImpactStaminaConversionPolicy.quote(10.0D, 10.0D, PressureClass.LONG, 0.35D).isEmpty(),
            "LONG at equal shield has zero safe conversion");
    }

    private static void inconsistentShortLongSnapshotFailsClosed() {
        require(ImpactStaminaConversionPolicy.quote(10.0D, 10.01D, PressureClass.SHORT, 0.35D).isEmpty(),
            "SHORT with shield above impact contradicts prior provider decision");
        require(ImpactStaminaConversionPolicy.quote(10.0D, 11.0D, PressureClass.LONG, 0.35D).isEmpty(),
            "LONG with shield above impact contradicts prior provider decision");
    }

    private static void specialControlsFailClosed() {
        for (PressureClass p : new PressureClass[] {
            PressureClass.HOLD, PressureClass.KNOCKDOWN, PressureClass.NEUTRALIZE, PressureClass.FALL
        }) {
            require(ImpactStaminaConversionPolicy.quote(10.0D, 0.0D, p, 0.35D).isEmpty(),
                p + " must be fail-closed");
        }
    }

    private static void nonPositiveImpactFailsClosed() {
        require(ImpactStaminaConversionPolicy.quote(0.0D, 0.0D, PressureClass.NONE, 0.35D).isEmpty(), "zero impact");
        require(ImpactStaminaConversionPolicy.quote(-1.0D, 0.0D, PressureClass.NONE, 0.35D).isEmpty(), "negative impact");
    }

    private static void nonFiniteImpactFailsClosed() {
        for (double v : new double[] {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            require(ImpactStaminaConversionPolicy.quote(v, 0.0D, PressureClass.NONE, 0.35D).isEmpty(),
                "non-finite impact: " + v);
        }
    }

    private static void invalidFractionFailsClosed() {
        for (double v : new double[] {0.0D, -0.1D, 0.3500000001D, 1.0D, Double.NaN,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            require(ImpactStaminaConversionPolicy.quote(10.0D, 0.0D, PressureClass.NONE, v).isEmpty(),
                "invalid fraction: " + v);
        }
    }

    private static void invalidShieldSnapshotFailsClosed() {
        for (double v : new double[] {-0.01D, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            require(ImpactStaminaConversionPolicy.quote(10.0D, v, PressureClass.SHORT, 0.35D).isEmpty(),
                "invalid shield: " + v);
        }
    }

    private static void arithmeticPreservesNativeDoublePrecisionWithoutRounding() {
        double impact = 1.0D / 3.0D;
        double fraction = 0.17D;
        Quote q = quote(impact, 0.0D, PressureClass.NONE, fraction);
        double expected = impact * (1.0D - fraction);
        require(Double.doubleToLongBits(q.reducedImpact()) == Double.doubleToLongBits(expected), "reduced impact rounded");
        require(Double.doubleToLongBits(q.staminaCost()) == Double.doubleToLongBits(impact - expected), "cost rounded");
    }

    private static Quote quote(double impact, double shield, PressureClass p, double fraction) {
        Optional<Quote> q = ImpactStaminaConversionPolicy.quote(impact, shield, p, fraction);
        require(q.isPresent(), "expected quote for " + p + " impact=" + impact + " shield=" + shield);
        return q.orElseThrow();
    }

    private static boolean close(double a, double b) { return Math.abs(a - b) < 1.0E-9D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
