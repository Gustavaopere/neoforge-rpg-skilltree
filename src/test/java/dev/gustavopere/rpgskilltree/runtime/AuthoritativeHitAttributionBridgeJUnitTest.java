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
    void firstProviderRootBecomesCanonicalForEveryAdapterOnTheSameHit() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();

        var first = AuthoritativeHitAttributionBridge.canonicalize(
            damageSource,
            targetId,
            "epicfight/first-root",
            "epicfight:a0001-a0020"
        );
        var second = AuthoritativeHitAttributionBridge.canonicalize(
            damageSource,
            targetId,
            "martial/competing-root",
            "epicfight:a0061-a0080"
        );

        assertEquals("epicfight/first-root", first.rootActionId());
        assertEquals("epicfight/first-root", second.rootActionId());
        assertEquals("epicfight:a0001-a0020", second.providerId());
        assertEquals(first, AuthoritativeHitAttributionBridge.find(damageSource, targetId).orElseThrow());
    }

    @Test
    void genericNeoForgeObserverCanReuseCanonicalRootWithoutConsumingIt() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();
        var canonical = AuthoritativeHitAttributionBridge.canonicalize(
            damageSource,
            targetId,
            "epicfight/authoritative-root",
            "epicfight"
        );

        assertEquals(canonical, AuthoritativeHitAttributionBridge.find(damageSource, targetId).orElseThrow());
        assertEquals(canonical, AuthoritativeHitAttributionBridge.find(damageSource, targetId).orElseThrow());
    }

    @Test
    void differentTargetsOnTheSameDamageSourceRemainIndependent() {
        Object damageSource = new Object();
        UUID firstTarget = UUID.randomUUID();
        UUID secondTarget = UUID.randomUUID();

        AuthoritativeHitAttributionBridge.canonicalize(damageSource, firstTarget, "root/one", "epicfight");
        AuthoritativeHitAttributionBridge.canonicalize(damageSource, secondTarget, "root/two", "epicfight");

        assertEquals("root/one", AuthoritativeHitAttributionBridge.find(damageSource, firstTarget).orElseThrow().rootActionId());
        assertEquals("root/two", AuthoritativeHitAttributionBridge.find(damageSource, secondTarget).orElseThrow().rootActionId());
    }

    @Test
    void discardingProviderEvidenceLeavesNoFallbackAliasBehind() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();
        AuthoritativeHitAttributionBridge.canonicalize(damageSource, targetId, "root/cancelled", "epicfight");

        AuthoritativeHitAttributionBridge.discard(damageSource, targetId);

        assertTrue(AuthoritativeHitAttributionBridge.find(damageSource, targetId).isEmpty());
    }
}
