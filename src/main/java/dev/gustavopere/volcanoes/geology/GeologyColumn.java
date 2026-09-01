package dev.gustavopere.volcanoes.geology;

import java.util.List;
import java.util.Objects;

/** Immutable virtual geological column covering one configured vertical range. */
public record GeologyColumn(int minY, int maxYExclusive, List<Stratum> strata) {
    public GeologyColumn {
        if (minY >= maxYExclusive) {
            throw new IllegalArgumentException("Column vertical range must have positive height");
        }
        Objects.requireNonNull(strata, "strata");
        if (strata.isEmpty()) {
            throw new IllegalArgumentException("A geology column must contain at least one stratum");
        }

        strata = List.copyOf(strata);
        int cursor = minY;
        for (Stratum stratum : strata) {
            Objects.requireNonNull(stratum, "stratum");
            if (stratum.minY() != cursor) {
                throw new IllegalArgumentException(
                        "Strata must be ordered and contiguous: expected minY " + cursor
                                + " but found " + stratum.minY());
            }
            if (stratum.maxYExclusive() > maxYExclusive) {
                throw new IllegalArgumentException("Stratum extends beyond the configured column range");
            }
            cursor = stratum.maxYExclusive();
        }
        if (cursor != maxYExclusive) {
            throw new IllegalArgumentException(
                    "Strata must cover the complete column range through Y=" + maxYExclusive);
        }
    }

    public String profileIdAt(int y) {
        if (y < minY || y >= maxYExclusive) {
            throw new IllegalArgumentException(
                    "Y coordinate " + y + " is outside geology column [" + minY + ", " + maxYExclusive + ")");
        }
        for (Stratum stratum : strata) {
            if (stratum.contains(y)) {
                return stratum.rockProfileId();
            }
        }
        throw new IllegalStateException("Validated geology column did not resolve Y=" + y);
    }
}
