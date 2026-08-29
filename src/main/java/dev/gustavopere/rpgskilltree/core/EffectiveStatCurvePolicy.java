package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Objects;

/** Effective-stat policy backed by one explicitly configured scaling curve. */
public final class EffectiveStatCurvePolicy implements EffectiveStatPolicy {
    private final CappedLinearScalingCurve curve;

    private EffectiveStatCurvePolicy(CappedLinearScalingCurve curve) {
        this.curve = curve;
    }

    public static EffectiveStatCurvePolicy of(CappedLinearScalingCurve curve) {
        return new EffectiveStatCurvePolicy(Objects.requireNonNull(curve, "curve"));
    }

    @Override
    public BigDecimal resolve(EffectiveStatContext context) {
        Objects.requireNonNull(context, "context");
        return curve.apply(context.providerValue(), context.progressionLevel());
    }

    public CappedLinearScalingCurve curve() {
        return curve;
    }
}
