package dev.gustavopere.volcanoes.environment;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AtmospherePressureSnapshotDomainContractTest {
    @Test
    void playerSnapshotPreservesPressureAcrossTheHistoricalUnsignedShortCeiling() {
        for (double pressureAtm : new double[]{65.534, 65.536, 100.0}) {
            AtmosphereState state = new AtmosphereState(
                    pressureAtm,
                    0.2095,
                    0.00042,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.5,
                    0.0);

            AtmosphereSnapshot restored = AtmosphereSnapshot.from(state);
            assertEquals(
                    pressureAtm,
                    restored.toAtmosphereState().totalPressureAtm(),
                    0.0011,
                    "pressure sync must not saturate at the historical 65.535-atm short ceiling");
        }
    }

    @Test
    void widenedPressureFieldKeepsTheSnapshotCompactAndCodecAligned() {
        AtmosphereSnapshot snapshot = AtmosphereSnapshot.from(new AtmosphereState(
                100.0,
                0.2095,
                0.00042,
                0.0,
                0.0,
                0.0,
                0.0,
                0.5,
                0.0));
        AtmosphereSyncPayload payload = new AtmosphereSyncPayload(snapshot);
        ByteBuf buffer = Unpooled.buffer();

        AtmosphereSyncPayload.STREAM_CODEC.encode(buffer, payload);

        assertEquals(20, snapshot.encodedSizeBytes());
        assertEquals(20, buffer.readableBytes());
        assertEquals(payload, AtmosphereSyncPayload.STREAM_CODEC.decode(buffer));
    }
}
