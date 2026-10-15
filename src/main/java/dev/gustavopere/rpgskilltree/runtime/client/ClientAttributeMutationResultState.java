package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.runtime.network.AttributeRankMutationResultPayload;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Bounded, read-once receipt queue; the UI matches each verdict by transaction ID. */
public final class ClientAttributeMutationResultState {
    public static final int MAX_PENDING_RESULTS = 32;
    private static final Deque<AttributeRankMutationResultPayload> PENDING = new ArrayDeque<>();

    private ClientAttributeMutationResultState() {}

    public static synchronized void handle(AttributeRankMutationResultPayload result, IPayloadContext context) {
        Objects.requireNonNull(result, "result");
        if (PENDING.size() == MAX_PENDING_RESULTS) {
            PENDING.removeFirst();
        }
        PENDING.addLast(result);
    }

    public static synchronized Optional<AttributeRankMutationResultPayload> take() {
        return Optional.ofNullable(PENDING.pollFirst());
    }

    public static synchronized void clear() {
        PENDING.clear();
    }
}
