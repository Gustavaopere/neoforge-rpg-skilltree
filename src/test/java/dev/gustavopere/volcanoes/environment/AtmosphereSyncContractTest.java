package dev.gustavopere.volcanoes.environment;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class AtmosphereSyncContractTest {
    @Test
    void payloadEncodesExactlyOneQuantizedPlayerSnapshot() {
        AtmosphereSnapshot snapshot = AtmosphereSnapshot.from(
                new AtmosphereState(0.73, 0.184, 0.041, 37.5, 82.0, 14.2, 6.4, 0.77, 12.5));
        AtmosphereSyncPayload payload = new AtmosphereSyncPayload(snapshot);
        ByteBuf buffer = Unpooled.buffer();

        AtmosphereSyncPayload.STREAM_CODEC.encode(buffer, payload);
        assertEquals(20, buffer.readableBytes());
        assertEquals(payload, AtmosphereSyncPayload.STREAM_CODEC.decode(buffer));
    }

    @Test
    void clientStateAndServerTrackerAvoidRedundantFullStateSync() throws Exception {
        AtmosphereSnapshot first = AtmosphereSnapshot.from(AtmosphereState.standardOverworld());
        AtmosphereSnapshot changed = AtmosphereSnapshot.from(
                new AtmosphereState(0.80, 0.2095, 0.00042, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0));
        AtmosphereClientState client = new AtmosphereClientState();
        client.accept(new AtmosphereSyncPayload(first));
        assertEquals(first, client.snapshot());

        UUID playerId = UUID.fromString("00000000-0000-0000-0000-000000000601");
        AtmosphereSyncTracker tracker = new AtmosphereSyncTracker();
        assertTrue(tracker.needsSend(playerId, first));
        assertTrue(tracker.needsSend(playerId, first),
                "an unacknowledged/failed transport must remain retryable");
        tracker.markSent(playerId, first);
        assertFalse(tracker.needsSend(playerId, first));
        assertTrue(tracker.needsSend(playerId, changed));
        tracker.markSent(playerId, changed);
        assertFalse(tracker.needsSend(playerId, changed));

        tracker.forget(playerId);
        assertTrue(tracker.needsSend(playerId, changed));

        assertEquals(void.class,
                AtmosphereNetworking.class
                        .getMethod("register", RegisterPayloadHandlersEvent.class)
                        .getReturnType());
    }

    @Test
    void clientStateResetDropsSnapshotFromPreviousConnection() {
        AtmosphereClientState client = new AtmosphereClientState();
        AtmosphereSnapshot stale = AtmosphereSnapshot.from(
                new AtmosphereState(0.65, 0.15, 0.05, 80.0, 120.0, 20.0, 8.0, 0.35, 20.0));
        client.accept(new AtmosphereSyncPayload(stale));
        assertEquals(stale, client.snapshot());

        client.reset();

        assertEquals(
                AtmosphereSnapshot.from(AtmosphereState.standardOverworld()),
                client.snapshot(),
                "disconnect must not leak the previous server/world atmosphere into the next session");
    }
}
