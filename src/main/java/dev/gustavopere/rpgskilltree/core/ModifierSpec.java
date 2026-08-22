package dev.gustavopere.rpgskilltree.core;
import java.util.Objects;
public record ModifierSpec(String statKey, ModifierOperation operation, double amount, String sourceId, int priority) {
    public ModifierSpec {
        Objects.requireNonNull(statKey); Objects.requireNonNull(operation); Objects.requireNonNull(sourceId);
        if (statKey.isBlank() || sourceId.isBlank()) throw new IllegalArgumentException("statKey/sourceId must not be blank");
        if (!Double.isFinite(amount)) throw new IllegalArgumentException("amount must be finite");
    }
}
