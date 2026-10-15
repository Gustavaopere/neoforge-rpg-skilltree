package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.runtime.network.AttributeRankMutationResultPayload;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Read-once client mailbox; the UI accepts only a matching pending transaction ID. */
public final class ClientAttributeMutationResultState {
    private static final AtomicReference<AttributeRankMutationResultPayload> LATEST =
        new AtomicReference<>();

    private ClientAttributeMutationResultState() {}

    public static void handle(AttributeRankMutationResultPayload result, IPayloadContext context) {
        LATEST.set(result);
    }

    public static Optional<AttributeRankMutationResultPayload> take() {
        return Optional.ofNullable(LATEST.getAndSet(null));
    }

    public static void clear() {
        LATEST.set(null);
    }
}
