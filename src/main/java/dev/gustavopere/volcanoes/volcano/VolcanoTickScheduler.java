package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.TreeSet;
import java.util.UUID;

/** Ordered scheduler that keeps exactly one physical node per live volcano. */
public final class VolcanoTickScheduler {
    private static final long ERUPTING_INTERVAL = 200L;
    private static final long ACTIVE_INTERVAL = 1_200L;
    private static final long NEAR_ACTIVE_INTERVAL = 4_800L;
    private static final long DORMANT_INTERVAL = 24_000L;
    private static final long EXTINCT_INTERVAL = 120_000L;

    private final NavigableSet<Scheduled> queue = new TreeSet<>(Comparator
            .comparingLong(Scheduled::dueTick)
            .thenComparing(entry -> entry.persistenceId().toString()));
    private final Map<UUID, Scheduled> dueById = new HashMap<>();

    public long intervalTicksFor(VolcanoState state, MagmaChamber chamber) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(chamber, "chamber");
        return switch (state) {
            case ERUPTING -> ERUPTING_INTERVAL;
            case ACTIVE -> ACTIVE_INTERVAL;
            case DORMANT -> chamber.pressureMegapascals() >= 150.0 || chamber.gasFraction() >= 0.06
                    ? NEAR_ACTIVE_INTERVAL
                    : DORMANT_INTERVAL;
            case EXTINCT -> EXTINCT_INTERVAL;
        };
    }

    public void schedule(UUID persistenceId, VolcanoState state, MagmaChamber chamber, long currentTick) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        if (currentTick < 0L) {
            throw new IllegalArgumentException("currentTick must be non-negative");
        }
        long interval = intervalTicksFor(state, chamber);
        long dueTick = currentTick > Long.MAX_VALUE - interval ? Long.MAX_VALUE : currentTick + interval;
        Scheduled next = new Scheduled(persistenceId, dueTick);
        Scheduled previous = dueById.put(persistenceId, next);
        if (previous != null) {
            queue.remove(previous);
        }
        queue.add(next);
    }

    public OptionalLong nextDueTick(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        Scheduled due = dueById.get(persistenceId);
        return due == null ? OptionalLong.empty() : OptionalLong.of(due.dueTick());
    }

    public List<UUID> pollDue(long currentTick, int maxCount) {
        if (maxCount < 0) {
            throw new IllegalArgumentException("maxCount must be non-negative");
        }
        List<UUID> due = new ArrayList<>(Math.min(maxCount, dueById.size()));
        while (due.size() < maxCount && !queue.isEmpty()) {
            Scheduled next = queue.first();
            if (next.dueTick() > currentTick) {
                break;
            }
            queue.pollFirst();
            dueById.remove(next.persistenceId(), next);
            due.add(next.persistenceId());
        }
        return List.copyOf(due);
    }

    public int size() {
        return dueById.size();
    }

    private record Scheduled(UUID persistenceId, long dueTick) {
    }
}
