package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.gustavopere.rpgskilltree.runtime.AuthoritativeHitAttributionBridge;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class A0081A0100HitAuthorityJUnitTest {
    @AfterEach
    void clearBridge() {
        AuthoritativeHitAttributionBridge.clearAll();
    }

    @Test
    void reusesEpicFightAuthoritativeRootInsteadOfCreatingParallelFallback() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();
        AuthoritativeHitAttributionBridge.canonicalize(
            damageSource,
            targetId,
            "epicfight/hit/42",
            "epicfight"
        );

        assertEquals(
            "epicfight/hit/42",
            A0081A0100CombatEvents.resolveOutgoingRootActionId(
                damageSource,
                targetId,
                "sustain/fallback/1"
            )
        );
    }

    @Test
    void preservesNeoForgeFallbackWhenNoProviderAuthorshipExists() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();

        assertEquals(
            "sustain/fallback/2",
            A0081A0100CombatEvents.resolveOutgoingRootActionId(
                damageSource,
                targetId,
                "sustain/fallback/2"
            )
        );
    }

    @Test
    void rejectsMissingFallbackIdentityBeforeConsultingProviderAuthorship() {
        Object damageSource = new Object();
        UUID targetId = UUID.randomUUID();

        assertThrows(
            IllegalArgumentException.class,
            () -> A0081A0100CombatEvents.resolveOutgoingRootActionId(damageSource, targetId, null)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> A0081A0100CombatEvents.resolveOutgoingRootActionId(damageSource, targetId, " ")
        );
    }
}
