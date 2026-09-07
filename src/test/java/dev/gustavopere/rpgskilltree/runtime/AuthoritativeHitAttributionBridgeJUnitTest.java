package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class AuthoritativeHitAttributionBridgeJUnitTest {
    @AfterEach
    void clearBridge() {
        AuthoritativeHitAttributionBridge.clearAll();
    }

    @Test
    void firstProviderRootBecomesCanonicalForEveryObserverOnTheSameHit() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();

        var first = AuthoritativeHitAttributionBridge.canonicalize(
            damageSource,
            targetId,
            "epicfight/first-root",
            "epicfight"
        );
        var second = AuthoritativeHitAttributionBridge.canonicalize(
            damageSource,
            targetId,
            "sustain/competing-root",
            "neoforge"
        );

        assertEquals("epicfight/first-root", first.rootActionId());
        assertEquals(first, second);
        assertEquals(first, AuthoritativeHitAttributionBridge.find(damageSource, targetId).orElseThrow());
    }

    @Test
    void differentTargetsOnTheSameSourceRemainIndependentAndDiscardIsScoped() {
        Object damageSource = new Object();
        UUID firstTarget = UUID.randomUUID();
        UUID secondTarget = UUID.randomUUID();

        AuthoritativeHitAttributionBridge.canonicalize(damageSource, firstTarget, "root/one", "epicfight");
        AuthoritativeHitAttributionBridge.canonicalize(damageSource, secondTarget, "root/two", "epicfight");
        AuthoritativeHitAttributionBridge.discard(damageSource, firstTarget);

        assertTrue(AuthoritativeHitAttributionBridge.find(damageSource, firstTarget).isEmpty());
        assertEquals("root/two", AuthoritativeHitAttributionBridge.find(damageSource, secondTarget).orElseThrow().rootActionId());
    }

    @Test
    void emptySourceBucketsStayNeutralAndSingleEntryDiscardRemovesTheBucket() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();

        assertTrue(AuthoritativeHitAttributionBridge.find(damageSource, targetId).isEmpty());
        AuthoritativeHitAttributionBridge.discard(damageSource, targetId);

        AuthoritativeHitAttributionBridge.canonicalize(damageSource, targetId, "root/only", "epicfight");
        AuthoritativeHitAttributionBridge.discard(damageSource, targetId);

        assertTrue(AuthoritativeHitAttributionBridge.find(damageSource, targetId).isEmpty());
    }

    @Test
    void nullLookupAndDiscardAreNeutralButInvalidPublicationFailsClosed() {
        UUID targetId = UUID.randomUUID();
        assertTrue(AuthoritativeHitAttributionBridge.find(null, targetId).isEmpty());
        assertTrue(AuthoritativeHitAttributionBridge.find(new Object(), null).isEmpty());
        AuthoritativeHitAttributionBridge.discard(null, targetId);
        AuthoritativeHitAttributionBridge.discard(new Object(), null);

        assertThrows(NullPointerException.class, () ->
            AuthoritativeHitAttributionBridge.canonicalize(null, targetId, "root", "epicfight"));
        assertThrows(NullPointerException.class, () ->
            AuthoritativeHitAttributionBridge.canonicalize(new Object(), null, "root", "epicfight"));
        assertThrows(NullPointerException.class, () ->
            AuthoritativeHitAttributionBridge.canonicalize(new Object(), targetId, null, "epicfight"));
        assertThrows(NullPointerException.class, () ->
            AuthoritativeHitAttributionBridge.canonicalize(new Object(), targetId, "root", null));
        assertThrows(IllegalArgumentException.class, () ->
            AuthoritativeHitAttributionBridge.canonicalize(new Object(), targetId, " ", "epicfight"));
        assertThrows(IllegalArgumentException.class, () ->
            AuthoritativeHitAttributionBridge.canonicalize(new Object(), targetId, "root", " "));
    }
}
