package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.runtime.AuthoritativeHitAttributionBridge;
import java.lang.reflect.Method;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

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

    @Test
    void preAuthorityRequiresBothHostileTargetAndDirectPlayerEvidence() {
        assertTrue(EpicFightProgressionHooks.shouldPublishHitAuthority(true, true));
        assertFalse(EpicFightProgressionHooks.shouldPublishHitAuthority(false, true));
        assertFalse(EpicFightProgressionHooks.shouldPublishHitAuthority(true, false));
        assertFalse(EpicFightProgressionHooks.shouldPublishHitAuthority(false, false));
    }

    @Test
    void preCallbackPublishesOnlyForAnEligibleDirectHostileHit() throws Exception {
        ServerPlayer attacker = mock(ServerPlayer.class);
        Player hostileTarget = mock(Player.class);
        ServerLevel level = mock(ServerLevel.class);
        EpicFightDamageSource damageSource = mock(EpicFightDamageSource.class);
        DealDamageEvent.Pre event = mock(DealDamageEvent.Pre.class, RETURNS_DEEP_STUBS);
        UUID targetId = UUID.fromString("00000000-0000-0000-0000-000000000605");

        when(event.getEntityPatch().getOriginal()).thenReturn(attacker);
        when(event.getTarget()).thenReturn(hostileTarget);
        when(event.getDamageSource()).thenReturn(damageSource);
        when(damageSource.getDirectEntity()).thenReturn(attacker);
        when(hostileTarget.getUUID()).thenReturn(targetId);
        when(attacker.level()).thenReturn(level);
        when(level.getGameTime()).thenReturn(602L);

        invokePreAuthority(event);

        var attribution = AuthoritativeHitAttributionBridge.find(damageSource, targetId).orElseThrow();
        assertEquals("epicfight", attribution.providerId());
        assertTrue(attribution.rootActionId().startsWith("epicfight/hit/602/"));

        EpicFightHitAuthority.discard(damageSource, targetId);
        when(damageSource.getDirectEntity()).thenReturn(null);
        invokePreAuthority(event);
        assertTrue(AuthoritativeHitAttributionBridge.find(damageSource, targetId).isEmpty());
    }

    private static void invokePreAuthority(DealDamageEvent.Pre event) throws Exception {
        Method callback = EpicFightProgressionHooks.class.getDeclaredMethod(
            "onDealDamageAuthority",
            DealDamageEvent.Pre.class
        );
        callback.setAccessible(true);
        callback.invoke(null, event);
    }
}
