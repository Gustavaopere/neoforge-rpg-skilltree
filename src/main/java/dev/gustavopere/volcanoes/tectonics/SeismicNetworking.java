package dev.gustavopere.volcanoes.tectonics;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/** Common-side registration for the client-bound seismic shake payload. */
public final class SeismicNetworking {
    private static final SeismicClientShakeState CLIENT_SHAKE_STATE = new SeismicClientShakeState();

    private SeismicNetworking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SeismicShakePayload.TYPE,
                SeismicShakePayload.STREAM_CODEC,
                SeismicNetworking::handleShake);
    }

    static void handleShake(SeismicShakePayload payload, IPayloadContext context) {
        CLIENT_SHAKE_STATE.accept(payload, System.nanoTime());
    }

    public static SeismicClientShakeState clientShakeState() {
        return CLIENT_SHAKE_STATE;
    }
}
