package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.StationaryStateService;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.MartialStanceRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightVersionContract;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0071A0080GeneralEventCoverageJUnitTest {
    @Test
    void epicFightOwnershipRequiresLoadedSupportedExactVersion() {
        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<EpicFightVersionContract> versions = mockStatic(EpicFightVersionContract.class)) {
            assertFalse(A0076A0079GeneralEvents.epicFightBridgeOwnsEvents());

            integrations.when(() -> OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT)).thenReturn(true);
            integrations.when(() -> OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT)).thenReturn("21.17.3.1");
            versions.when(() -> EpicFightVersionContract.supportsVersion("21.17.3.1")).thenReturn(true);
            assertTrue(A0076A0079GeneralEvents.epicFightBridgeOwnsEvents());

            versions.when(() -> EpicFightVersionContract.supportsVersion("21.17.3.1")).thenReturn(false);
            assertFalse(A0076A0079GeneralEvents.epicFightBridgeOwnsEvents());
        }
    }

    @Test
    void incomingPhysicalDamageAppliesServerOwnedStanceResistanceAndSkipsCanceledDamage() {
        ServerLevel level = mock(ServerLevel.class);
        ServerPlayer player = eligiblePlayer(level);
        LivingIncomingDamageEvent event = mock(LivingIncomingDamageEvent.class);
        DamageSource source = mock(DamageSource.class);
        when(event.getEntity()).thenReturn(player);
        when(event.getAmount()).thenReturn(100.0F);
        when(event.getSource()).thenReturn(source);
        when(source.is(any(TagKey.class))).thenReturn(true);

        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.switchStance("actor", A0061A0080CombatState.Stance.CAUTIOUS, 1_000L));

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<MartialStanceRuntime> stance = mockStatic(MartialStanceRuntime.class)) {
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");

            A0076A0079GeneralEvents.onIncomingPhysicalDamage(event);
            stance.verify(() -> MartialStanceRuntime.reconcile(player));
            verify(event).setAmount(92.0F);

            when(event.isCanceled()).thenReturn(true);
            A0076A0079GeneralEvents.onIncomingPhysicalDamage(event);
            verify(event).setAmount(92.0F);
        }
    }

    @Test
    void incomingDamageSkipsNonPhysicalAndEpicOwnedPaths() {
        ServerLevel level = mock(ServerLevel.class);
        ServerPlayer player = eligiblePlayer(level);
        LivingIncomingDamageEvent event = mock(LivingIncomingDamageEvent.class);
        DamageSource source = mock(DamageSource.class);
        when(event.getEntity()).thenReturn(player);
        when(event.getAmount()).thenReturn(10.0F);
        when(event.getSource()).thenReturn(source);
        when(source.is(any(TagKey.class))).thenReturn(false);

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<MartialStanceRuntime> stance = mockStatic(MartialStanceRuntime.class)) {
            A0076A0079GeneralEvents.onIncomingPhysicalDamage(event);
            stance.verifyNoInteractions();
            verify(event, never()).setAmount(any(Float.class));
        }

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<EpicFightVersionContract> versions = mockStatic(EpicFightVersionContract.class);
             MockedStatic<MartialStanceRuntime> stance = mockStatic(MartialStanceRuntime.class)) {
            integrations.when(() -> OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT)).thenReturn(true);
            integrations.when(() -> OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT)).thenReturn("21.17.3.1");
            versions.when(() -> EpicFightVersionContract.supportsVersion("21.17.3.1")).thenReturn(true);
            when(source.is(any(TagKey.class))).thenReturn(true);
            A0076A0079GeneralEvents.onIncomingPhysicalDamage(event);
            stance.verifyNoInteractions();
        }
    }

    @Test
    void tickTeleportAndKnockbackReconcileOrInvalidateExactlyOnceWhenFallbackOwnsEvents() {
        ServerLevel level = mock(ServerLevel.class);
        ServerPlayer player = eligiblePlayer(level);
        MinecraftServer server = mock(MinecraftServer.class);
        PlayerList playerList = mock(PlayerList.class);
        ServerTickEvent.Post tick = mock(ServerTickEvent.Post.class);
        when(tick.getServer()).thenReturn(server);
        when(server.getPlayerList()).thenReturn(playerList);
        when(playerList.getPlayers()).thenReturn(List.of(player));

        EntityTeleportEvent teleport = mock(EntityTeleportEvent.class);
        when(teleport.getEntity()).thenReturn(player);
        LivingKnockBackEvent knockback = mock(LivingKnockBackEvent.class);
        when(knockback.getEntity()).thenReturn(player);
        StationaryStateService stationary = mock(StationaryStateService.class);

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<MartialStanceRuntime> stance = mockStatic(MartialStanceRuntime.class)) {
            runtime.when(A0061A0080RuntimeState::stationary).thenReturn(stationary);
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");

            A0076A0079GeneralEvents.onServerTick(tick);
            stance.verify(() -> MartialStanceRuntime.reconcile(player));

            A0076A0079GeneralEvents.onTeleport(teleport);
            A0076A0079GeneralEvents.onKnockback(knockback);
            verify(stationary, org.mockito.Mockito.times(2)).invalidate("actor");
        }
    }

    @Test
    void fallbackEventEligibilityRejectsClientCreativeAndSpectatorPlayers() {
        ServerLevel clientLevel = mock(ServerLevel.class);
        when(clientLevel.isClientSide()).thenReturn(true);
        ServerPlayer client = mock(ServerPlayer.class);
        when(client.level()).thenReturn(clientLevel);

        ServerLevel serverLevel = mock(ServerLevel.class);
        ServerPlayer creative = mock(ServerPlayer.class);
        when(creative.level()).thenReturn(serverLevel);
        when(creative.isCreative()).thenReturn(true);
        ServerPlayer spectator = mock(ServerPlayer.class);
        when(spectator.level()).thenReturn(serverLevel);
        when(spectator.isSpectator()).thenReturn(true);

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            StationaryStateService stationary = mock(StationaryStateService.class);
            runtime.when(A0061A0080RuntimeState::stationary).thenReturn(stationary);

            for (ServerPlayer player : List.of(client, creative, spectator)) {
                EntityTeleportEvent event = mock(EntityTeleportEvent.class);
                when(event.getEntity()).thenReturn(player);
                A0076A0079GeneralEvents.onTeleport(event);
            }
            verify(stationary, never()).invalidate(any());
        }
    }

    private static ServerPlayer eligiblePlayer(ServerLevel level) {
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(level.isClientSide()).thenReturn(false);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);
        return player;
    }
}
