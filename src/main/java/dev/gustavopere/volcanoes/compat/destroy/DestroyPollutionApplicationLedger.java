package dev.gustavopere.volcanoes.compat.destroy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.UUID;

/**
 * Bounded component-level idempotency ledger for a host that exposes independent pollution writes.
 * A component is remembered only after the mutation succeeds, so a partial host failure can retry
 * the missing component without applying already-successful components twice.
 */
final class DestroyPollutionApplicationLedger {
    private final int capacity;
    private final LinkedHashSet<ComponentKey> applied = new LinkedHashSet<>();

    DestroyPollutionApplicationLedger(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
    }

    boolean applyOnce(UUID emissionId, String component, Runnable mutation) {
        ComponentKey key = new ComponentKey(
                Objects.requireNonNull(emissionId, "emissionId"),
                Objects.requireNonNull(component, "component"));
        Objects.requireNonNull(mutation, "mutation");
        synchronized (applied) {
            if (applied.contains(key)) {
                return false;
            }
            mutation.run();
            applied.add(key);
            while (applied.size() > capacity) {
                var iterator = applied.iterator();
                iterator.next();
                iterator.remove();
            }
            return true;
        }
    }

    int size() {
        synchronized (applied) {
            return applied.size();
        }
    }

    private record ComponentKey(UUID emissionId, String component) {
    }
}
