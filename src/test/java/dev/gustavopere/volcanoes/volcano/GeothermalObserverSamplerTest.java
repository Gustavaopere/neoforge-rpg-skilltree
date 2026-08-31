package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalObserverSamplerTest {
    @Test
    void samplingNeverVisitsMoreThanConfiguredObserverBudget() {
        CountingList observers = new CountingList(10_000);

        List<Integer> sampled = GeothermalObserverSampler.sample(observers, 1_234L, 32, value -> value);

        assertEquals(32, sampled.size());
        assertEquals(32, sampled.stream().distinct().count());
        assertEquals(32, observers.getCalls,
                "sampling must read only the configured observer budget, not iterate all players");
        assertTrue(sampled.stream().allMatch(index -> index >= 0 && index < 10_000));
    }

    @Test
    void samplingRotatesDeterministicallyInBudgetSizedBatches() {
        List<Integer> observers = List.of(0, 1, 2, 3, 4);

        assertEquals(List.of(0, 1, 2), GeothermalObserverSampler.sample(observers, 0L, 3, value -> value));
        assertEquals(List.of(3, 4, 0), GeothermalObserverSampler.sample(observers, 1L, 3, value -> value));
        assertEquals(List.of(1, 2, 3), GeothermalObserverSampler.sample(observers, 2L, 3, value -> value));
        assertEquals(List.of(0, 1), GeothermalObserverSampler.sample(List.of(0, 1), 10L, 32, value -> value));
        assertEquals(List.of(), GeothermalObserverSampler.sample(List.of(), 10L, 32, value -> value));
        assertEquals(List.of(), GeothermalObserverSampler.sample(observers, 10L, 0, value -> value));
    }

    @Test
    void sweepTicksBoundsHowLongOneObserverCanRemainUnsampled() {
        assertEquals(1L, GeothermalObserverSampler.sweepTicks(0, 32));
        assertEquals(1L, GeothermalObserverSampler.sweepTicks(32, 32));
        assertEquals(2L, GeothermalObserverSampler.sweepTicks(33, 32));
        assertEquals(4L, GeothermalObserverSampler.sweepTicks(100, 32));
        assertEquals(313L, GeothermalObserverSampler.sweepTicks(10_000, 32));
    }

    private static final class CountingList extends AbstractList<Integer> {
        private final int size;
        private int getCalls;

        private CountingList(int size) {
            this.size = size;
        }

        @Override
        public Integer get(int index) {
            getCalls++;
            return index;
        }

        @Override
        public int size() {
            return size;
        }
    }
}
