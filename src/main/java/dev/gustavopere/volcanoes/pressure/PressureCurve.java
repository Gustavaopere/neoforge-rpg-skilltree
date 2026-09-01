package dev.gustavopere.volcanoes.pressure;

import java.util.List;
import java.util.Objects;

/**
 * Immutable monotonic atmospheric-pressure curve. Altitudes must be strictly increasing and pressure must be
 * non-increasing. Values between control points are linearly interpolated; values outside the configured range
 * clamp to the nearest endpoint.
 */
public final class PressureCurve {
    private final List<PressureControlPoint> controlPoints;

    public PressureCurve(List<PressureControlPoint> controlPoints) {
        Objects.requireNonNull(controlPoints, "controlPoints");
        if (controlPoints.isEmpty()) {
            throw new IllegalArgumentException("pressure curve requires at least one control point");
        }

        this.controlPoints = List.copyOf(controlPoints);
        PressureControlPoint previous = null;
        for (PressureControlPoint point : this.controlPoints) {
            Objects.requireNonNull(point, "control point");
            if (previous != null) {
                if (point.altitudeY() <= previous.altitudeY()) {
                    throw new IllegalArgumentException("pressure curve altitudes must be strictly increasing");
                }
                if (point.pressureAtm() > previous.pressureAtm()) {
                    throw new IllegalArgumentException("atmospheric pressure must not increase with altitude");
                }
            }
            previous = point;
        }
    }

    public List<PressureControlPoint> controlPoints() {
        return controlPoints;
    }

    public double pressureAtm(double altitudeY) {
        if (!Double.isFinite(altitudeY)) {
            throw new IllegalArgumentException("altitudeY must be finite");
        }

        PressureControlPoint first = controlPoints.getFirst();
        if (altitudeY <= first.altitudeY()) {
            return first.pressureAtm();
        }

        PressureControlPoint last = controlPoints.getLast();
        if (altitudeY >= last.altitudeY()) {
            return last.pressureAtm();
        }

        int low = 0;
        int high = controlPoints.size() - 1;
        while (low + 1 < high) {
            int mid = (low + high) >>> 1;
            PressureControlPoint point = controlPoints.get(mid);
            if (altitudeY < point.altitudeY()) {
                high = mid;
            } else {
                low = mid;
            }
        }

        PressureControlPoint lower = controlPoints.get(low);
        PressureControlPoint upper = controlPoints.get(high);
        if (altitudeY == lower.altitudeY()) {
            return lower.pressureAtm();
        }
        if (altitudeY == upper.altitudeY()) {
            return upper.pressureAtm();
        }

        double fraction = (altitudeY - lower.altitudeY()) / (upper.altitudeY() - lower.altitudeY());
        return lower.pressureAtm() + fraction * (upper.pressureAtm() - lower.pressureAtm());
    }
}
