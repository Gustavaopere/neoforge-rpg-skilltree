package dev.gustavopere.volcanoes.tectonics;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SeismicRuntimeContractTest {
    @Test
    void shakePayloadUsesStableVolcanoesChannelAndValidatesValues() {
        SeismicShakePayload payload = new SeismicShakePayload(0.75f, 30);

        assertTrue(payload instanceof CustomPacketPayload);
        assertEquals(
                ResourceLocation.fromNamespaceAndPath("volcanoes", "seismic_shake"),
                SeismicShakePayload.TYPE.id());
        assertEquals(0.75f, payload.amplitude());
        assertEquals(30, payload.durationTicks());
        assertThrows(IllegalArgumentException.class, () -> new SeismicShakePayload(-0.01f, 20));
        assertThrows(IllegalArgumentException.class, () -> new SeismicShakePayload(1.01f, 20));
        assertThrows(IllegalArgumentException.class, () -> new SeismicShakePayload(0.5f, 0));
    }

    @Test
    void clientShakeStateDecaysDeterministicallyWithoutMinecraftClientDependency() {
        SeismicClientShakeState state = new SeismicClientShakeState();
        long start = 1_000_000_000L;
        state.accept(new SeismicShakePayload(0.8f, 20), start);

        assertEquals(0.8, state.amplitudeAt(start), 1.0e-6);
        double halfway = state.amplitudeAt(start + 500_000_000L);
        assertTrue(halfway > 0.0 && halfway < 0.8);
        assertEquals(0.0, state.amplitudeAt(start + 1_000_000_000L), 1.0e-12);
    }

    @Test
    void networkingAndServerEffectsExposeNeoForgeRuntimeHooks() throws Exception {
        assertEquals(
                void.class,
                SeismicNetworking.class
                        .getMethod("register", RegisterPayloadHandlersEvent.class)
                        .getReturnType());
        assertEquals(
                int.class,
                SeismicServerEffects.class
                        .getMethod("apply", ServerLevel.class, SeismicEvent.class)
                        .getReturnType());
    }
}
