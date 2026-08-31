package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

public record AtmosphereSnapshot(
        int totalPressureMilliAtm,
        short oxygenTenThousandths,
        short carbonDioxideTenThousandths,
        short sulfurDioxideTenthsPpm,
        short toxicGasTenthsPpm,
        short particulatesTwentiethsMgM3,
        short smokeTwentiethsMgM3,
        short humidityTwoHundredths,
        short thermalTenthsC
) {
    public static AtmosphereSnapshot from(AtmosphereState state) {
        Objects.requireNonNull(state, "state");
        return new AtmosphereSnapshot(
                pressureQuantize(state.totalPressureAtm()),
                unsignedQuantize(state.oxygenFraction(), 10000.0),
                unsignedQuantize(state.carbonDioxideFraction(), 10000.0),
                unsignedQuantize(state.sulfurDioxidePpm(), 10.0),
                unsignedQuantize(state.toxicGasPpm(), 10.0),
                unsignedQuantize(state.particulatesMgM3(), 20.0),
                unsignedQuantize(state.smokeMgM3(), 20.0),
                unsignedQuantize(state.relativeHumidity(), 200.0),
                signedQuantize(state.thermalModifierC(), 10.0));
    }

    public AtmosphereSnapshot {
        if (totalPressureMilliAtm < 0) {
            throw new IllegalArgumentException("totalPressureMilliAtm must be non-negative");
        }
    }

    public AtmosphereState toAtmosphereState() {
        return new AtmosphereState(
                totalPressureMilliAtm / 1000.0,
                unsigned(oxygenTenThousandths) / 10000.0,
                unsigned(carbonDioxideTenThousandths) / 10000.0,
                unsigned(sulfurDioxideTenthsPpm) / 10.0,
                unsigned(toxicGasTenthsPpm) / 10.0,
                unsigned(particulatesTwentiethsMgM3) / 20.0,
                unsigned(smokeTwentiethsMgM3) / 20.0,
                unsigned(humidityTwoHundredths) / 200.0,
                thermalTenthsC / 10.0);
    }

    public int encodedSizeBytes() {
        return Integer.BYTES + Short.BYTES * 8;
    }

    private static int pressureQuantize(double pressureAtm) {
        double nonNegative = Math.max(0.0, pressureAtm);
        long quantized = Math.round(nonNegative * 1000.0);
        return (int) Math.min(Integer.MAX_VALUE, quantized);
    }

    private static short unsignedQuantize(double value, double scale) {
        long quantized = Math.round(Math.max(0.0, value) * scale);
        return (short) Math.min(0xffffL, quantized);
    }

    private static short signedQuantize(double value, double scale) {
        long quantized = Math.round(value * scale);
        return (short) Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, quantized));
    }

    private static int unsigned(short value) {
        return Short.toUnsignedInt(value);
    }
}
