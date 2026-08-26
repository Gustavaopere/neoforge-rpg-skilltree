package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Canonical BODY-state modulation exported to A0046 Focus.
 *
 * <p>The three axes are deliberately independent: body temperature, hydration and
 * fatigue/exhaustion. World climate/season data is not a body-temperature scalar,
 * and fatigue/exhaustion is never converted into hydration here. A missing provider
 * is represented by {@link Axis#neutral()} for only that axis.
 */
public record CanonicalBodyStateScalars(
    Axis temperature,
    Axis hydration,
    Axis fatigueExhaustion
) {
    public CanonicalBodyStateScalars {
        Objects.requireNonNull(temperature);
        Objects.requireNonNull(hydration);
        Objects.requireNonNull(fatigueExhaustion);
    }

    public static CanonicalBodyStateScalars neutral() {
        return new CanonicalBodyStateScalars(Axis.neutral(), Axis.neutral(), Axis.neutral());
    }

    public double gainMultiplier() {
        return temperature.gainMultiplier() * hydration.gainMultiplier() * fatigueExhaustion.gainMultiplier();
    }

    public double lossMultiplier() {
        return temperature.lossMultiplier() * hydration.lossMultiplier() * fatigueExhaustion.lossMultiplier();
    }

    /** One independently sourced canonical BODY axis. */
    public record Axis(double gainMultiplier, double lossMultiplier) {
        public Axis {
            requirePositiveFinite(gainMultiplier, "gainMultiplier");
            requirePositiveFinite(lossMultiplier, "lossMultiplier");
        }

        public static Axis neutral() {
            return new Axis(1.0D, 1.0D);
        }
    }

    private static void requirePositiveFinite(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and positive");
        }
    }
}
