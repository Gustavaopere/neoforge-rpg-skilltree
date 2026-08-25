package dev.gustavopere.rpgskilltree.core;

import java.util.Optional;

/** Pure, provider-agnostic quote policy for certified impact-to-stamina conversion. */
public final class ImpactStaminaConversionPolicy {
    public static final double MAX_CONVERSION_FRACTION = 0.35D;

    private ImpactStaminaConversionPolicy() {}

    public enum PressureClass {
        NONE,
        SHORT,
        LONG,
        HOLD,
        KNOCKDOWN,
        NEUTRALIZE,
        FALL
    }

    public record Quote(double reducedImpact, double staminaCost) {}

    public static Optional<Quote> quote(
        double impact,
        double stunShieldSnapshot,
        PressureClass pressureClass,
        double fraction
    ) {
        if (!Double.isFinite(impact) || impact <= 0.0D) return Optional.empty();
        if (!Double.isFinite(stunShieldSnapshot) || stunShieldSnapshot < 0.0D) return Optional.empty();
        if (!Double.isFinite(fraction) || fraction <= 0.0D || fraction > MAX_CONVERSION_FRACTION) {
            return Optional.empty();
        }
        if (pressureClass == null || switch (pressureClass) {
            case HOLD, KNOCKDOWN, NEUTRALIZE, FALL -> true;
            case NONE, SHORT, LONG -> false;
        }) {
            return Optional.empty();
        }

        double requestedImpact = impact * (1.0D - fraction);
        double reducedImpact = requestedImpact;

        if (pressureClass == PressureClass.SHORT || pressureClass == PressureClass.LONG) {
            // Epic Fight has already executed `stunShield > impact` before the approved commit point.
            // If this snapshot contradicts a surviving SHORT/LONG, fail closed rather than replaying that decision.
            if (stunShieldSnapshot > impact) return Optional.empty();
            reducedImpact = Math.max(requestedImpact, stunShieldSnapshot);
        }

        double staminaCost = impact - reducedImpact;
        if (!Double.isFinite(reducedImpact) || !Double.isFinite(staminaCost) || staminaCost <= 0.0D) {
            return Optional.empty();
        }
        return Optional.of(new Quote(reducedImpact, staminaCost));
    }
}
