package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class AuthoritativeHitAttributionBridgeJUnitTest {
    @AfterEach
    void clearBridge() {
        AuthoritativeHitAttributionBridge.clearAll();
    }

    @Test
    void providerRootIsClaimedExactlyOnceByTheGenericDamagePipeline() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();

        AuthoritativeHitAttributionBridge.publish(
            damageSource,
            targetId,
            "epicfight/authoritative-root",
            "epicfight"
        );

        var claimed = AuthoritativeHitAttributionBridge.claim(damageSource, targetId);
        assertTrue(claimed.isPresent());
        assertEquals("epicfight/authoritative-root", claimed.orElseThrow().rootActionId());
        assertEquals("epicfight", claimed.orElseThrow().providerId());
        assertTrue(AuthoritativeHitAttributionBridge.claim(damageSource, targetId).isEmpty());
    }

    @Test
    void differentTargetsOnTheSameDamageSourceRemainIndependent() {
        Object damageSource = new Object();
        UUID firstTarget = UUID.randomUUID();
        UUID secondTarget = UUID.randomUUID();

        AuthoritativeHitAttributionBridge.publish(damageSource, firstTarget, "root/one", "epicfight");
        AuthoritativeHitAttributionBridge.publish(damageSource, secondTarget, "root/two", "epicfight");

        assertEquals("root/one", AuthoritativeHitAttributionBridge.claim(damageSource, firstTarget).orElseThrow().rootActionId());
        assertEquals("root/two", AuthoritativeHitAttributionBridge.claim(damageSource, secondTarget).orElseThrow().rootActionId());
    }

    @Test
    void discardingProviderEvidenceLeavesNoFallbackAliasBehind() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();
        AuthoritativeHitAttributionBridge.publish(damageSource, targetId, "root/cancelled", "epicfight");

        AuthoritativeHitAttributionBridge.discard(damageSource, targetId);

        assertTrue(AuthoritativeHitAttributionBridge.claim(damageSource, targetId).isEmpty());
    }
}
