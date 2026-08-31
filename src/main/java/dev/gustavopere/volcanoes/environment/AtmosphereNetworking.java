package dev.gustavopere.volcanoes.environment;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class AtmosphereNetworking {
    private static final AtmosphereClientState CLIENT_STATE = new AtmosphereClientState();

    private AtmosphereNetworking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                AtmosphereSyncPayload.TYPE,
                AtmosphereSyncPayload.STREAM_CODEC,
                AtmosphereNetworking::handleSync);
    }

    static void handleSync(AtmosphereSyncPayload payload, IPayloadContext context) {
        CLIENT_STATE.accept(payload);
    }

    public static AtmosphereClientState clientState() {
        return CLIENT_STATE;
    }
}
