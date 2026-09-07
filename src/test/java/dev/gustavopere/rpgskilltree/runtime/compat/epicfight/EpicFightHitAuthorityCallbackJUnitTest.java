package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

final class EpicFightHitAuthorityCallbackJUnitTest {
    @AfterEach
    void clearAuthority() {
        AuthoritativeHitAttributionBridge.clearAll();
    }

    @Test
    void providerPrePublishesCanonicalRootAndPostDiscardsIt() throws Exception {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(123L);

        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);

        Player target = mock(Player.class);
        UUID targetId = UUID.fromString("00000000-0000-0000-0000-000000000602");
        when(target.getUUID()).thenReturn(targetId);
        when(target.isInvulnerable()).thenReturn(false);
        when(player.isAlliedTo(target)).thenReturn(false);

        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        when(source.getDirectEntity()).thenReturn(player);

        ServerPlayerPatch patch = mock(ServerPlayerPatch.class);
        when(patch.getOriginal()).thenReturn(player);

        DealDamageEvent.Pre pre = new DealDamageEvent.Pre(patch, target, source, 5.0F);
        invoke("onDealDamageAuthority", DealDamageEvent.Pre.class, pre);

        var attribution = AuthoritativeHitAttributionBridge.find(source, targetId).orElseThrow();
        assertEquals("epicfight", attribution.providerId());
        assertTrue(attribution.rootActionId().startsWith("epicfight/hit/123/"));

        DealDamageEvent.Post post = new DealDamageEvent.Post(patch, target, source, 0.0F);
        invoke("onDealDamage", DealDamageEvent.Post.class, post);
        assertTrue(AuthoritativeHitAttributionBridge.find(source, targetId).isEmpty());
    }

    @Test
    void providerPreFailsClosedForIneligibleOrNonAuthoritativeEvidence() throws Exception {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(124L);

        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(player.isSpectator()).thenReturn(false);

        Player target = mock(Player.class);
        UUID targetId = UUID.fromString("00000000-0000-0000-0000-000000000603");
        when(target.getUUID()).thenReturn(targetId);
        when(target.isInvulnerable()).thenReturn(false);
        when(player.isAlliedTo(target)).thenReturn(false);

        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        ServerPlayerPatch patch = mock(ServerPlayerPatch.class);
        when(patch.getOriginal()).thenReturn(player);
        DealDamageEvent.Pre pre = new DealDamageEvent.Pre(patch, target, source, 5.0F);

        when(player.isCreative()).thenReturn(true);
        when(source.getDirectEntity()).thenReturn(player);
        invoke("onDealDamageAuthority", DealDamageEvent.Pre.class, pre);
        assertTrue(AuthoritativeHitAttributionBridge.find(source, targetId).isEmpty());

        when(player.isCreative()).thenReturn(false);
        when(player.isAlliedTo(target)).thenReturn(true);
        invoke("onDealDamageAuthority", DealDamageEvent.Pre.class, pre);
        assertTrue(AuthoritativeHitAttributionBridge.find(source, targetId).isEmpty());

        when(player.isAlliedTo(target)).thenReturn(false);
        when(source.getDirectEntity()).thenReturn(target);
        invoke("onDealDamageAuthority", DealDamageEvent.Pre.class, pre);
        assertTrue(AuthoritativeHitAttributionBridge.find(source, targetId).isEmpty());
    }

    private static void invoke(String name, Class<?> parameterType, Object event) throws Exception {
        Method method = EpicFightProgressionHooks.class.getDeclaredMethod(name, parameterType);
        method.setAccessible(true);
        method.invoke(null, event);
    }
}
