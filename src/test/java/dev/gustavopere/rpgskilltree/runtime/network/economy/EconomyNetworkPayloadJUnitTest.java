package dev.gustavopere.rpgskilltree.runtime.network.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.client.economy.ClientColonyEconomyState;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class EconomyNetworkPayloadJUnitTest {
    private static final EconomyColonyContext COLONY = new EconomyColonyContext(
        ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"),
        17
    );

    @AfterEach
    void clearClientState() {
        ClientColonyEconomyState.clear();
    }

    @Test
    void colonyContextRoundTripsAndRejectsInvalidAuthorityLookupData() {
        assertEquals(COLONY, roundTrip(EconomyColonyContext.STREAM_CODEC, COLONY));
        assertThrows(IllegalArgumentException.class, () -> new EconomyColonyContext(null, 1));
        assertThrows(
            IllegalArgumentException.class,
            () -> new EconomyColonyContext(ResourceLocation.fromNamespaceAndPath("minecraft", "overworld"), -1)
        );
    }

    @Test
    void snapshotRoundTripsAndUpdatesReadOnlyClientCache() {
        EconomySnapshotPayload payload = new EconomySnapshotPayload(
            COLONY,
            new EconomySnapshotPayload.Balances(100L, 20L, 40L, 10L, 30L),
            new EconomySnapshotPayload.Metrics(75L, 1_200L, 135.5D, 0.25D, true)
        );

        EconomySnapshotPayload decoded = roundTrip(EconomySnapshotPayload.STREAM_CODEC, payload);
        assertEquals(payload, decoded);
        assertEquals(EconomySnapshotPayload.TYPE, payload.type());
        assertEquals(80L, payload.balances().effectiveSupply());
        assertFalse(ClientColonyEconomyState.snapshot(COLONY).isPresent());

        EconomySnapshotPayload.handle(payload, null);

        assertEquals(payload, ClientColonyEconomyState.snapshot(COLONY).orElseThrow());
        ClientColonyEconomyState.clear();
        assertTrue(ClientColonyEconomyState.snapshot(COLONY).isEmpty());
    }

    @Test
    void snapshotBalancesRejectMalformedWireState() {
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Balances(-1L, 0L, 0L, 0L, 0L));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Balances(1L, -1L, 0L, 0L, 0L));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Balances(1L, 0L, -1L, 0L, 0L));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Balances(1L, 0L, 0L, -1L, 0L));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Balances(1L, 0L, 0L, 0L, -1L));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Balances(1L, 2L, 0L, 0L, 0L));
    }

    @Test
    void snapshotMetricsRejectMalformedWireState() {
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Metrics(-1L, 0L, 100.0D, 0.1D, false));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Metrics(0L, -1L, 100.0D, 0.1D, false));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Metrics(0L, 0L, Double.NaN, 0.1D, false));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Metrics(0L, 0L, 0.0D, 0.1D, false));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Metrics(0L, 0L, 100.0D, Double.NaN, false));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Metrics(0L, 0L, 100.0D, -0.1D, false));
        assertThrows(IllegalArgumentException.class, () -> new EconomySnapshotPayload.Metrics(0L, 0L, 100.0D, 1.1D, false));
    }

    @Test
    void preflightRequestAndResultRoundTripWithValidatedProjection() {
        EconomyMintPreflightPayload request = new EconomyMintPreflightPayload(COLONY, 25L);
        assertEquals(request, roundTrip(EconomyMintPreflightPayload.STREAM_CODEC, request));
        assertEquals(EconomyMintPreflightPayload.TYPE, request.type());

        EconomyMintPreflightResultPayload.Projection projection =
            new EconomyMintPreflightResultPayload.Projection(50L, 75L, 80L, 110.0D, 145.0D);
        EconomyMintPreflightResultPayload result = new EconomyMintPreflightResultPayload(COLONY, "APPLIED", projection);

        assertEquals(result, roundTrip(EconomyMintPreflightResultPayload.STREAM_CODEC, result));
        assertEquals(EconomyMintPreflightResultPayload.TYPE, result.type());
        assertTrue(ClientColonyEconomyState.latestPreflight().isEmpty());

        EconomyMintPreflightResultPayload.handle(result, null);

        assertEquals(result, ClientColonyEconomyState.latestPreflight().orElseThrow());
        assertEquals(
            new EconomyMintPreflightResultPayload.Projection(0L, 0L, 0L, 100.0D, 100.0D),
            EconomyMintPreflightResultPayload.Projection.unavailable()
        );
    }

    @Test
    void preflightResultRejectsMalformedWireState() {
        EconomyMintPreflightResultPayload.Projection valid =
            new EconomyMintPreflightResultPayload.Projection(10L, 20L, 30L, 100.0D, 120.0D);

        assertThrows(IllegalArgumentException.class, () -> new EconomyMintPreflightResultPayload(COLONY, null, valid));
        assertThrows(IllegalArgumentException.class, () -> new EconomyMintPreflightResultPayload(COLONY, "   ", valid));
        assertThrows(IllegalArgumentException.class, () -> new EconomyMintPreflightResultPayload(COLONY, "x".repeat(65), valid));
        assertThrows(IllegalArgumentException.class, () -> new EconomyMintPreflightResultPayload(COLONY, "APPLIED", null));

        assertThrows(IllegalArgumentException.class,
            () -> new EconomyMintPreflightResultPayload.Projection(-1L, 0L, 0L, 100.0D, 100.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyMintPreflightResultPayload.Projection(10L, 9L, 0L, 100.0D, 100.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyMintPreflightResultPayload.Projection(0L, 0L, -1L, 100.0D, 100.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyMintPreflightResultPayload.Projection(0L, 0L, 0L, Double.NaN, 100.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyMintPreflightResultPayload.Projection(0L, 0L, 0L, 0.0D, 100.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyMintPreflightResultPayload.Projection(0L, 0L, 0L, 100.0D, Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyMintPreflightResultPayload.Projection(0L, 0L, 0L, 100.0D, 0.0D));
    }

    @Test
    void mutationAndSnapshotRequestPayloadsRoundTripWithoutGrantingAuthority() {
        UUID intentId = UUID.fromString("00000000-0000-0000-0000-000000002001");
        EconomyMintPayload mint = new EconomyMintPayload(COLONY, intentId, 42L);
        EconomyRetirePayload retire = new EconomyRetirePayload(COLONY, intentId, 21L);
        EconomySnapshotRequestPayload snapshotRequest = new EconomySnapshotRequestPayload(COLONY);

        assertEquals(mint, roundTrip(EconomyMintPayload.STREAM_CODEC, mint));
        assertEquals(retire, roundTrip(EconomyRetirePayload.STREAM_CODEC, retire));
        assertEquals(snapshotRequest, roundTrip(EconomySnapshotRequestPayload.STREAM_CODEC, snapshotRequest));
        assertEquals(EconomyMintPayload.TYPE, mint.type());
        assertEquals(EconomyRetirePayload.TYPE, retire.type());
        assertEquals(EconomySnapshotRequestPayload.TYPE, snapshotRequest.type());

        assertThrows(IllegalArgumentException.class, () -> new EconomyMintPayload(COLONY, null, 1L));
        assertThrows(IllegalArgumentException.class, () -> new EconomyRetirePayload(COLONY, null, 1L));
    }

    private static <T> T roundTrip(StreamCodec<ByteBuf, T> codec, T value) {
        ByteBuf buffer = Unpooled.buffer();
        try {
            codec.encode(buffer, value);
            return codec.decode(buffer);
        } finally {
            buffer.release();
        }
    }
}
