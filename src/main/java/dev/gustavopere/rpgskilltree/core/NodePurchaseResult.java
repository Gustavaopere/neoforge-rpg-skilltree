package dev.gustavopere.rpgskilltree.core;

import java.util.Locale;
import java.util.Objects;

/** Structured outcome for a server-authoritative node purchase attempt. */
public record NodePurchaseResult(ProgressionState state, Status status) {
    public enum Status {
        ACCEPTED,
        UNKNOWN_NODE,
        UNAVAILABLE_NODE,
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

    public String fallbackMessage() {
        return switch (status) {
            case ACCEPTED -> "Node purchase confirmed.";
            case UNKNOWN_NODE -> "Unknown skill-tree node.";
            case UNAVAILABLE_NODE -> "Este nó está indisponível enquanto a integração obrigatória não estiver operacional.";
            case REQUIREMENTS_NOT_SATISFIED -> "This node's requirements are not satisfied.";
            case MAX_RANK_REACHED -> "This node is already at maximum rank.";
            case NOT_CONNECTED -> "This node is not connected to the learned tree.";
            case INSUFFICIENT_POINTS -> "Not enough passive points for this purchase.";
            case DUPLICATE_REQUEST -> "This purchase request was already processed.";
            case REQUEST_ID_CONFLICT -> "Invalid purchase request: request id was reused.";
        };
    }
}
