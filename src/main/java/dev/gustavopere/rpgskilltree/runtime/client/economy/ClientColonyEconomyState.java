package dev.gustavopere.rpgskilltree.runtime.client.economy;

import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomyColonyContext;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomyMintPreflightResultPayload;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomySnapshotPayload;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Read-only client cache. All mutation authority remains server-side. */
public final class ClientColonyEconomyState {
    private static final Map<EconomyColonyContext, EconomySnapshotPayload> SNAPSHOTS = new ConcurrentHashMap<>();
    private static final AtomicReference<EconomyMintPreflightResultPayload> PREFLIGHT = new AtomicReference<>();

    private ClientColonyEconomyState() {}

    public static Optional<EconomySnapshotPayload> snapshot(EconomyColonyContext colony) {
        return Optional.ofNullable(SNAPSHOTS.get(colony));
    }

    public static Optional<EconomyMintPreflightResultPayload> latestPreflight() {
        return Optional.ofNullable(PREFLIGHT.get());
    }

    public static void handleSnapshot(EconomySnapshotPayload payload, IPayloadContext context) {
        SNAPSHOTS.put(payload.colony(), payload);
    }

    public static void handlePreflight(EconomyMintPreflightResultPayload payload, IPayloadContext context) {
        PREFLIGHT.set(payload);
    }

    public static void clear() {
        SNAPSHOTS.clear();
        PREFLIGHT.set(null);
    }
}
