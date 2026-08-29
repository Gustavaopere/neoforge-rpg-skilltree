package dev.gustavopere.rpgskilltree.core;

import java.util.Locale;
import java.util.Objects;

/** Structured outcome for a server-authoritative node purchase attempt. */
public record NodePurchaseResult(ProgressionState state, Status status) {
    public enum Status {
        ACCEPTED,
        UNKNOWN_NODE,
        REQUIREMENTS_NOT_SATISFIED,
        MAX_RANK_REACHED,
        NOT_CONNECTED,
        INSUFFICIENT_POINTS,
        DUPLICATE_REQUEST,
        REQUEST_ID_CONFLICT
    }

    public NodePurchaseResult {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(status, "status");
    }

    public static NodePurchaseResult accepted(ProgressionState state) {
        return new NodePurchaseResult(state, Status.ACCEPTED);
    }

    public static NodePurchaseResult rejected(ProgressionState state, Status status) {
        if (status == Status.ACCEPTED) {
            throw new IllegalArgumentException("rejected purchase cannot use ACCEPTED status");
        }
        return new NodePurchaseResult(state, status);
    }

    public boolean accepted() {
        return status == Status.ACCEPTED;
    }

    public String messageKey() {
        return "purchase.rpgskilltree." + status.name().toLowerCase(Locale.ROOT);
    }
}
