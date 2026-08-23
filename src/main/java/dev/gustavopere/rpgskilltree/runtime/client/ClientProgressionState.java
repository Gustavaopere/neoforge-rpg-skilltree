package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionStateCodec;
import dev.gustavopere.rpgskilltree.runtime.network.ProgressionSyncPayload;
import java.util.concurrent.atomic.AtomicReference;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ClientProgressionState {
    private static final AtomicReference<ProgressionState> CURRENT = new AtomicReference<>(ProgressionState.empty());

    private ClientProgressionState() {}

    public static ProgressionState get() {
        return CURRENT.get();
    }

    public static void handleSync(ProgressionSyncPayload payload, IPayloadContext context) {
        ProgressionState decoded = ProgressionStateCodec.decode(payload.snapshot());
        CURRENT.set(decoded);
    }
}
