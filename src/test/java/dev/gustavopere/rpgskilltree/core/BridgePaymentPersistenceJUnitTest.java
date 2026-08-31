package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

final class BridgePaymentPersistenceJUnitTest {
    @Test
    void paidBridgeProvenanceSurvivesProgressionCodecRoundTrip() {
        ProgressionState source = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty()
                .award(PassivePointSource.ADMIN, 20)
                .spend(10))
            .withClassProgression(ClassProgressionState.of(
                Set.of("geomancer"),
                Set.of("geomancer")
            ));

        ProgressionState decoded = ProgressionStateCodec.decode(
            ProgressionStateCodec.encode(source)
        );

        assertTrue(decoded.classProgression().isUnlocked("geomancer"));
        assertTrue(decoded.classProgression().bridgePaid("geomancer"));
        assertEquals(10, decoded.passivePoints().spent());
    }
}
