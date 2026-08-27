package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.CoreProgressionSyncState;
import dev.gustavopere.rpgskilltree.core.CoreProgressionSyncStateCodec;
import dev.gustavopere.rpgskilltree.runtime.network.CoreProgressionSyncPayload;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Client mirror of the latest compact Core progression projection. */
public final class ClientCoreProgressionState {
    private static final AtomicReference<CoreProgressionSyncState> CURRENT = new AtomicReference<>();

    private ClientCoreProgressionState() {}

    public static Optional<CoreProgressionSyncState> get() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static void handleSync(CoreProgressionSyncPayload payload, IPayloadContext context) {
        CoreProgressionSyncState decoded = CoreProgressionSyncStateCodec.decode(payload.snapshot());
        CURRENT.set(decoded);
    }
}
