package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/** Bounded deduplicating handoff from worldgen threads to the server-tick persistence bridge. */
public final class GeothermalPendingQueue {
    static final int MAX_PROCESSING_FAILURES = 3;

    private final int capacity;
    private final int maxPerDrain;
    private final Map<UUID, Entry> pending = new LinkedHashMap<>();
    private long nextReservationId;

    public GeothermalPendingQueue(int capacity, int maxPerDrain) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        if (maxPerDrain <= 0 || maxPerDrain > capacity) {
            throw new IllegalArgumentException("maxPerDrain must be within [1, capacity]");
        }
        this.capacity = capacity;
        this.maxPerDrain = maxPerDrain;
    }

    public synchronized boolean enqueue(long worldSeed, GeothermalFeaturePlacement placement) {
        Optional<Reservation> reservation = reserve(worldSeed, placement);
        return reservation.filter(this::commit).isPresent();
    }

    public synchronized Optional<Reservation> reserve(long worldSeed, GeothermalFeaturePlacement placement) {
        Objects.requireNonNull(placement, "placement");
        UUID sourceId = GeothermalSource.fromPlacement(worldSeed, placement).persistenceId();
        if (pending.containsKey(sourceId) || pending.size() >= capacity) {
            return Optional.empty();
        }
        long reservationId = nextReservationId;
        nextReservationId = Math.incrementExact(nextReservationId);
        pending.put(sourceId, new Entry(new Pending(worldSeed, placement), reservationId, false, 0, false));
        return Optional.of(new Reservation(sourceId, reservationId));
    }

    public synchronized boolean commit(Reservation reservation) {
        return commit(reservation, false);
    }

    public synchronized boolean commit(Reservation reservation, boolean hydrothermalDepositPhysicallyRealized) {
        Objects.requireNonNull(reservation, "reservation");
        Entry entry = pending.get(reservation.sourceId());
        if (entry == null || entry.committed() || entry.reservationId() != reservation.reservationId()) {
            return false;
        }
        Pending committedPending = new Pending(
                entry.pending().worldSeed(),
                entry.pending().placement(),
                hydrothermalDepositPhysicallyRealized);
        pending.put(reservation.sourceId(), entry.withCommitted(committedPending));
        return true;
    }

    public synchronized boolean cancel(Reservation reservation) {
        Objects.requireNonNull(reservation, "reservation");
        Entry entry = pending.get(reservation.sourceId());
        if (entry == null || entry.committed() || entry.reservationId() != reservation.reservationId()) {
            return false;
        }
        pending.remove(reservation.sourceId());
        return true;
    }

    public synchronized int processCommitted(Predicate<Pending> acknowledge) {
        return processCommitted(acknowledge, maxPerDrain);
    }

    /** Processes at most the caller's current turn allocation without consuming quarantined/reserved work. */
    public synchronized int processCommitted(Predicate<Pending> acknowledge, int maxAttempts) {
        Objects.requireNonNull(acknowledge, "acknowledge");
        if (maxAttempts < 0) {
            throw new IllegalArgumentException("maxAttempts must be non-negative");
        }
        int limit = Math.min(maxPerDrain, maxAttempts);
        List<UUID> candidates = new ArrayList<>(Math.min(limit, pending.size()));
        for (Map.Entry<UUID, Entry> mapEntry : pending.entrySet()) {
            if (candidates.size() >= limit) {
                break;
            }
            Entry entry = mapEntry.getValue();
            if (entry.committed() && !entry.quarantined()) {
                candidates.add(mapEntry.getKey());
            }
        }

        int acknowledged = 0;
        for (UUID sourceId : candidates) {
            Entry entry = pending.get(sourceId);
            if (entry == null || !entry.committed() || entry.quarantined()) {
                continue;
            }
            try {
                if (acknowledge.test(entry.pending())) {
                    pending.remove(sourceId);
                    acknowledged++;
                } else {
                    rotateToTail(sourceId, entry);
                }
            } catch (RuntimeException | LinkageError failure) {
                int failures = entry.processingFailures() + 1;
                Entry failed = entry.withProcessingFailure(failures, failures >= MAX_PROCESSING_FAILURES);
                rotateToTail(sourceId, failed);
            }
        }
        return acknowledged;
    }

    /** Complete durable authority may retire only committed work with the same deterministic identity. */
    public synchronized boolean acknowledgeResolved(UUID sourceId) {
        Objects.requireNonNull(sourceId, "sourceId");
        Entry entry = pending.get(sourceId);
        if (entry == null || !entry.committed()) {
            return false;
        }
        pending.remove(sourceId);
        return true;
    }

    public synchronized boolean hasProcessableCommittedWork() {
        for (Entry entry : pending.values()) {
            if (entry.committed() && !entry.quarantined()) {
                return true;
            }
        }
        return false;
    }

    synchronized List<Pending> drain() {
        if (pending.isEmpty()) {
            return List.of();
        }
        List<UUID> ids = new ArrayList<>(Math.min(maxPerDrain, pending.size()));
        for (Map.Entry<UUID, Entry> entry : pending.entrySet()) {
            if (ids.size() >= maxPerDrain) {
                break;
            }
            if (entry.getValue().committed() && !entry.getValue().quarantined()) {
                ids.add(entry.getKey());
            }
        }
        List<Pending> drained = new ArrayList<>(ids.size());
        for (UUID id : ids) {
            drained.add(pending.remove(id).pending());
        }
        return List.copyOf(drained);
    }

    public synchronized int quarantinedCount() {
        int count = 0;
        for (Entry entry : pending.values()) {
            if (entry.quarantined()) {
                count++;
            }
        }
        return count;
    }

    public synchronized int size() {
        return pending.size();
    }

    public synchronized boolean isEmpty() {
        return pending.isEmpty();
    }

    private void rotateToTail(UUID sourceId, Entry entry) {
        pending.remove(sourceId);
        pending.put(sourceId, entry);
    }

    public record Reservation(UUID sourceId, long reservationId) {
        public Reservation {
            sourceId = Objects.requireNonNull(sourceId, "sourceId");
            if (reservationId < 0L) {
                throw new IllegalArgumentException("reservationId must be non-negative");
            }
        }
    }

    public record Pending(
            long worldSeed,
            GeothermalFeaturePlacement placement,
            boolean hydrothermalDepositPhysicallyRealized
    ) {
        public Pending {
            placement = Objects.requireNonNull(placement, "placement");
        }

        public Pending(long worldSeed, GeothermalFeaturePlacement placement) {
            this(worldSeed, placement, false);
        }
    }

    private record Entry(Pending pending, long reservationId, boolean committed, int processingFailures, boolean quarantined) {
        private Entry {
            pending = Objects.requireNonNull(pending, "pending");
            if (reservationId < 0L) {
                throw new IllegalArgumentException("reservationId must be non-negative");
            }
            if (processingFailures < 0) {
                throw new IllegalArgumentException("processingFailures must be non-negative");
            }
            if (quarantined && !committed) {
                throw new IllegalArgumentException("only committed work can be quarantined");
            }
        }

        private Entry withCommitted(Pending committedPending) {
            return new Entry(committedPending, reservationId, true, processingFailures, quarantined);
        }

        private Entry withProcessingFailure(int failures, boolean quarantine) {
            return new Entry(pending, reservationId, committed, failures, quarantine);
        }
    }
}
