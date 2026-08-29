package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Immutable complete set of independently configured world-scaling curves. */
public final class ScalingCurveSet {
    private final Map<ScalingCurveFamily, CappedLinearScalingCurve> curves;

    private ScalingCurveSet(Map<ScalingCurveFamily, CappedLinearScalingCurve> curves) {
        this.curves = Map.copyOf(curves);
    }

    public static ScalingCurveSet of(Map<ScalingCurveFamily, CappedLinearScalingCurve> curves) {
        Objects.requireNonNull(curves, "curves");
        EnumMap<ScalingCurveFamily, CappedLinearScalingCurve> copy = new EnumMap<>(ScalingCurveFamily.class);
        for (ScalingCurveFamily family : ScalingCurveFamily.values()) {
            CappedLinearScalingCurve curve = curves.get(family);
            if (curve == null) {
                throw new IllegalArgumentException("missing scaling curve family: " + family);
            }
            copy.put(family, curve);
        }
        if (curves.size() != ScalingCurveFamily.values().length) {
            throw new IllegalArgumentException("scaling curve set must contain exactly one curve per family");
        }
        return new ScalingCurveSet(copy);
    }

    public CappedLinearScalingCurve curve(ScalingCurveFamily family) {
        Objects.requireNonNull(family, "family");
        return curves.get(family);
    }

    public Map<ScalingCurveFamily, CappedLinearScalingCurve> asMap() {
        return curves;
    }
}
