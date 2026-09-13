package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.AuthoritativeHitAttributionBridge;
import dev.gustavopere.rpgskilltree.runtime.MartialTargetClassifier;
import dev.gustavopere.rpgskilltree.runtime.MartialTargetClassifier.TargetClass;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

final class A0071A0080EpicFightPrePostCoverageJUnitTest {
    @Test
    void prePostTransactionCommitsFirstBloodThenArmsAndConsumesExecution() throws Exception {
        ServerLevel level = mock(ServerLevel.class);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(20L, 21L, 22L, 23L, 24L, 25L);

        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);
        when(player.isSprinting()).thenReturn(true);

        Player target = mock(Player.class);
        UUID targetId = UUID.fromString("00000000-0000-0000-0000-000000000074");
        when(target.getUUID()).thenReturn(targetId);
        when(target.isAlive()).thenReturn(true);
        when(target.isInvulnerable()).thenReturn(false);
        when(target.getMaxHealth()).thenReturn(20.0F);
        when(target.getHealth()).thenReturn(18.0F, 2.0F, 2.0F);
        when(player.isAlliedTo(target)).thenReturn(false);

        ItemStack mace = mock(ItemStack.class);
        when(mace.is(Items.MACE)).thenReturn(true);
        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        when(source.getDirectEntity()).thenReturn(player);
        when(source.getUsedItem()).thenReturn(mace);

        ServerPlayerPatch patch = mock(ServerPlayerPatch.class);
        when(patch.getOriginal()).thenReturn(player);
        DealDamageEvent.Pre pre = new DealDamageEvent.Pre(patch, target, source, 5.0F);
        DealDamageEvent.Post post = new DealDamageEvent.Post(patch, target, source, 5.0F);

        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of(
            "A0061", 1, "A0065", 1, "A0066", 1, "A0071", 1,
            "A0073", 1, "A0074", 1, "A0078", 1, "A0079", 1, "A0080", 1
        ));
        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.armOpportunity("actor", 900L));

        Method onPre = privateMethod("onDamagePre", DealDamageEvent.Pre.class);
        Method onPost = privateMethod("onDamagePost", DealDamageEvent.Post.class);

        try (MockedStatic<EpicFightCapabilities> capabilities = mockStatic(EpicFightCapabilities.class);
             MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<MartialTargetClassifier> classifier = mockStatic(MartialTargetClassifier.class)) {
            capabilities.when(() -> EpicFightCapabilities.getItemStackCapability(mace)).thenReturn(null);
            runtime.when(() -> A0061A0080RuntimeState.ranks(player)).thenReturn(ranks);
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            runtime.when(() -> A0061A0080RuntimeState.isStationary(player)).thenReturn(true);
            classifier.when(() -> MartialTargetClassifier.classify(target)).thenReturn(TargetClass.ELITE);

            onPre.invoke(null, pre);
            onPost.invoke(null, post);
            assertTrue(state.firstBloodWindowActive("actor", targetId.toString(), 1_051L));

            onPre.invoke(null, pre);
            onPost.invoke(null, post);
            assertFalse(state.firstBloodWindowActive("actor", targetId.toString(), 1_151L));
            assertTrue(state.executionWindowActive("actor", targetId.toString(), 1_151L));

            onPre.invoke(null, pre);
            onPost.invoke(null, post);
            assertTrue(state.executionCoolingDown("actor", targetId.toString(), 1_251L));
        }

        verify(source, atLeastOnce()).attachDamageModifier(any(ValueModifier.class));
        verify(source, atLeastOnce()).attachArmorNegationModifier(any(ValueModifier.class));
        verify(source, atLeastOnce()).attachImpactModifier(any(ValueModifier.class));
    }

    @Test
    void postZeroDamageRollsBackPreparedTransitions() throws Exception {
        ServerLevel level = mock(ServerLevel.class);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(20L, 21L);
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);

        Player target = mock(Player.class);
        UUID targetId = UUID.fromString("00000000-0000-0000-0000-000000000075");
        when(target.getUUID()).thenReturn(targetId);
        when(target.isAlive()).thenReturn(true);
        when(target.isInvulnerable()).thenReturn(false);
        when(target.getMaxHealth()).thenReturn(20.0F);
        when(target.getHealth()).thenReturn(2.0F);
        when(player.isAlliedTo(target)).thenReturn(false);

        ItemStack mace = mock(ItemStack.class);
        when(mace.is(Items.MACE)).thenReturn(true);
        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        when(source.getDirectEntity()).thenReturn(player);
        when(source.getUsedItem()).thenReturn(mace);

        ServerPlayerPatch patch = mock(ServerPlayerPatch.class);
        when(patch.getOriginal()).thenReturn(player);
        DealDamageEvent.Pre pre = new DealDamageEvent.Pre(patch, target, source, 1.0F);
        DealDamageEvent.Post post = new DealDamageEvent.Post(patch, target, source, 0.0F);

        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0073", 1));
        A0061A0080CombatState state = new A0061A0080CombatState();
        Method onPre = privateMethod("onDamagePre", DealDamageEvent.Pre.class);
        Method onPost = privateMethod("onDamagePost", DealDamageEvent.Post.class);

        try (MockedStatic<EpicFightCapabilities> capabilities = mockStatic(EpicFightCapabilities.class);
             MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<MartialTargetClassifier> classifier = mockStatic(MartialTargetClassifier.class)) {
            capabilities.when(() -> EpicFightCapabilities.getItemStackCapability(mace)).thenReturn(null);
            runtime.when(() -> A0061A0080RuntimeState.ranks(player)).thenReturn(ranks);
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            runtime.when(() -> A0061A0080RuntimeState.isStationary(player)).thenReturn(false);
            classifier.when(() -> MartialTargetClassifier.classify(target)).thenReturn(TargetClass.ELITE);

            onPre.invoke(null, pre);
            onPost.invoke(null, post);
            assertFalse(state.executionWindowActive("actor", targetId.toString(), 1_051L));
            assertTrue(state.reserveExecutionArmCandidate("actor", targetId.toString(), "retry", 1_051L));
        }
    }

    @Test
    void stage0602ProviderNativePrePublishesAuthoritativeRoot() throws Exception {
        AuthoritativeHitAttributionBridge.clearAll();
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(602L);

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

        Method authority = EpicFightProgressionHooks.class.getDeclaredMethod(
            "onDealDamageAuthority",
            DealDamageEvent.Pre.class
        );
        authority.setAccessible(true);
        authority.invoke(null, pre);

        var attribution = AuthoritativeHitAttributionBridge.find(source, targetId).orElseThrow();
        assertEquals("epicfight", attribution.providerId());
        assertTrue(attribution.rootActionId().startsWith("epicfight/hit/602/"));
        AuthoritativeHitAttributionBridge.discard(source, targetId);
    }

    private static Method privateMethod(String name, Class<?> type) throws Exception {
        Method method = A0061A0080EpicFightHooks.class.getDeclaredMethod(name, type);
        method.setAccessible(true);
        return method;
    }
}
