package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Bounded in-memory storage for stationary pyroclastic trail samples.
 *
 * <p>Sampling cadence is driven by the authoritative flow-head age. The buffer owns no world tick
 * source and never loads chunks. Oldest samples are evicted deterministically at the hard cap.</p>
 */
public final class PyroclasticTrailBuffer {
    private final int capacity;
    private final long sampleIntervalTicks;
    private final long sampleLifetimeTicks;
    private final List<PyroclasticTrailState> samples = new ArrayList<>();

    public PyroclasticTrailBuffer(int capacity, long sampleIntervalTicks, long sampleLifetimeTicks) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        if (sampleIntervalTicks <= 0L) {
            throw new IllegalArgumentException("sampleIntervalTicks must be positive");
        }
        if (sampleLifetimeTicks <= 0L) {
            throw new IllegalArgumentException("sampleLifetimeTicks must be positive");
        }
        this.capacity = capacity;
        this.sampleIntervalTicks = sampleIntervalTicks;
        this.sampleLifetimeTicks = sampleLifetimeTicks;
    }

    public boolean record(PyroclasticFlowState head) {
        Objects.requireNonNull(head, "head");
        if (!head.active() || head.ageTicks() % sampleIntervalTicks != 0L) {
            return false;
        }

        if (samples.size() >= capacity) {
            samples.remove(0);
        }
        samples.add(PyroclasticTrailState.fromHead(head, sampleLifetimeTicks));
        return true;
    }

    public void tick() {
        List<PyroclasticTrailState> advanced = new ArrayList<>(samples.size());
        for (PyroclasticTrailState sample : samples) {
            PyroclasticTrailState next = PyroclasticTrailDynamics.step(sample);
            if (next.active()) {
                advanced.add(next);
            }
        }
        samples.clear();
        samples.addAll(advanced);
    }

    public void clear(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        samples.removeIf(sample -> sample.volcanoId().equals(volcanoId));
    }

    public List<PyroclasticTrailState> samples() {
        return List.copyOf(samples);
    }

    public int size() {
        return samples.size();
    }
}
