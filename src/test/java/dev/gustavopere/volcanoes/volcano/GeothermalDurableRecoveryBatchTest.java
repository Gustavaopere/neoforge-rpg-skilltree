package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Pure contract for bounded failure isolation inside one durable chunk-recovery batch. */
final class GeothermalDurableRecoveryBatchTest {
    @Test
    void poisonReceiptDoesNotEscapeOrStarveLaterReceiptInSameChunk() {
        GeothermalChunkHandoff poison = handoff(11L, 0);
        GeothermalChunkHandoff valid = handoff(12L, 16);
        AtomicInteger attempts = new AtomicInteger();

        GeothermalDurableRecoveryBatch.Result result = assertDoesNotThrow(() ->
                GeothermalDurableRecoveryBatch.process(
                        List.of(poison, valid),
                        2,
                        handoff -> {
                            attempts.incrementAndGet();
                            if (handoff.equals(poison)) {
                                throw new IllegalStateException("deterministic durable conflict");
                            }
                            return true;
                        }));

        assertEquals(2, attempts.get(), "one poison receipt must not abort later receipt reconciliation");
        assertEquals(2, result.attempted());
        assertEquals(1, result.acknowledged());
        assertEquals(List.of(poison), result.remaining(),
                "failed durable receipt must stay attached while the later authoritative receipt is removed");
    }

    @Test
    void falseAcknowledgementRetainsReceiptAndAttemptBudgetIsHard() {
        GeothermalChunkHandoff first = handoff(21L, 0);
        GeothermalChunkHandoff second = handoff(22L, 16);
        GeothermalChunkHandoff third = handoff(23L, 32);
        AtomicInteger attempts = new AtomicInteger();

        GeothermalDurableRecoveryBatch.Result result = GeothermalDurableRecoveryBatch.process(
                List.of(first, second, third),
                2,
                handoff -> {
                    attempts.incrementAndGet();
                    return handoff.equals(second);
                });

        assertEquals(2, attempts.get(), "durable recovery must not inspect more receipts than its allocated turn budget");
        assertEquals(2, result.attempted());
        assertEquals(1, result.acknowledged());
        assertEquals(List.of(first, third), result.remaining(),
                "false ACK and unattempted receipts must both remain durable for a later turn");
    }

    private static GeothermalChunkHandoff handoff(long worldSeed, int x) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.FUMAROLE);
        return new GeothermalChunkHandoff(
                worldSeed,
                new GeothermalFeaturePlacement(
                        GeothermalFeatureType.FUMAROLE,
                        new BlockPos(x, 80, 8),
                        profile.radiusBlocks(),
                        profile.heatSeverity(),
                        profile.gasSeverity(),
                        0.0));
    }
}
