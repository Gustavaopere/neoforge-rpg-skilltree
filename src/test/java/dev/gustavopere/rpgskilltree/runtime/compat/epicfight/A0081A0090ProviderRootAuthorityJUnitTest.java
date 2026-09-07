package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.gustavopere.rpgskilltree.runtime.AuthoritativeHitAttributionBridge;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class A0081A0090ProviderRootAuthorityJUnitTest {
    @AfterEach
    void clearBridge() {
        AuthoritativeHitAttributionBridge.clearAll();
    }

    @Test
    void reusesCanonicalProviderRootForSustainMetadata() {
        Object source = new Object();
        UUID target = UUID.randomUUID();
        AuthoritativeHitAttributionBridge.canonicalize(
            source,
            target,
            "epicfight/hit/42",
            "epicfight"
        );

        assertEquals(
            "epicfight/hit/42",
            A0061A0080EpicFightHooks.resolveProviderRootActionId(
                source,
                target,
                "martial/fallback/1"
            )
        );
    }

    @Test
    void preservesExistingMartialRootWhenNoProviderAuthorityExists() {
        assertEquals(
            "martial/fallback/2",
            A0061A0080EpicFightHooks.resolveProviderRootActionId(
                new Object(),
                UUID.randomUUID(),
                "martial/fallback/2"
            )
        );
    }

    @Test
    void rejectsMissingFallbackIdentity() {
        Object source = new Object();
        UUID target = UUID.randomUUID();
        assertThrows(
            IllegalArgumentException.class,
            () -> A0061A0080EpicFightHooks.resolveProviderRootActionId(source, target, null)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> A0061A0080EpicFightHooks.resolveProviderRootActionId(source, target, " ")
        );
    }
}
