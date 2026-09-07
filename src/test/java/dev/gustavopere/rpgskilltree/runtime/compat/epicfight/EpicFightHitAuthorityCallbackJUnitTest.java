package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.AuthoritativeHitAttributionBridge;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class EpicFightHitAuthorityCallbackJUnitTest {
    @AfterEach
    void clearAuthority() {
        AuthoritativeHitAttributionBridge.clearAll();
    }

    @Test
    void providerBoundaryPublishesFirstWriterAndDiscardsWithoutMinecraftRuntime() {
        Object source = new Object();
        UUID targetId = UUID.fromString("00000000-0000-0000-0000-000000000602");

        var first = EpicFightHitAuthority.publish(source, targetId, 123L);
        var repeated = EpicFightHitAuthority.publish(source, targetId, 124L);

        assertEquals("epicfight", first.providerId());
        assertTrue(first.rootActionId().startsWith("epicfight/hit/123/"));
        assertEquals(first.rootActionId(), repeated.rootActionId());
        assertEquals(first, AuthoritativeHitAttributionBridge.find(source, targetId).orElseThrow());

        EpicFightHitAuthority.discard(source, targetId);
        assertTrue(AuthoritativeHitAttributionBridge.find(source, targetId).isEmpty());
    }

    @Test
    void providerBoundaryKeepsTargetsIndependent() {
        Object source = new Object();
        UUID firstTarget = UUID.fromString("00000000-0000-0000-0000-000000000603");
        UUID secondTarget = UUID.fromString("00000000-0000-0000-0000-000000000604");

        var first = EpicFightHitAuthority.publish(source, firstTarget, 200L);
        var second = EpicFightHitAuthority.publish(source, secondTarget, 200L);

        assertNotEquals(first.rootActionId(), second.rootActionId());
        EpicFightHitAuthority.discard(source, firstTarget);
        assertTrue(AuthoritativeHitAttributionBridge.find(source, firstTarget).isEmpty());
        assertEquals(second, AuthoritativeHitAttributionBridge.find(source, secondTarget).orElseThrow());
    }
}
