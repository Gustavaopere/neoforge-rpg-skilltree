package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoRegistrationQueueHardeningTest {
    @Test
    void duplicatePendingRegistrationIsRetainedOnlyOnce() {
        VolcanoRegistrationQueue<String> queue = new VolcanoRegistrationQueue<>(16);

        for (int i = 0; i < 100; i++) {
            queue.enqueue(0L, "chunk-a");
        }

        assertTrue(queue.drainReady(1L).isEmpty());
        assertEquals(List.of("chunk-a"), queue.drainReady(2L));
        assertTrue(queue.isEmpty());
    }
}
