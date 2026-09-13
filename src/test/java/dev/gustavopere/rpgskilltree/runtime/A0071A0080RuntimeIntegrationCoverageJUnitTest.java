package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.StationaryStateService;
import dev.gustavopere.rpgskilltree.runtime.compat.A0079ForcedMovementCompat;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.create.A0079CreateTransportCompat;
import dev.gustavopere.rpgskilltree.runtime.compat.sable.A0079SableTransportCompat;
import dev.gustavopere.rpgskilltree.runtime.network.MartialStanceIntentPayload;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0071A0080RuntimeIntegrationCoverageJUnitTest {
    @AfterEach
    void clearRuntimeState() {
        A0061A0080RuntimeState.clearAll();
    }

    @Test
    void martialStanceCyclesOnlyThroughServerAvailableStancesAndHonorsCooldown() {
        ServerLevel level = mock(ServerLevel.class);
        ServerPlayer player = eligiblePlayer(level);
        when(level.getGameTime()).thenReturn(20L, 20L, 60L, 100L, 140L);

        A0061A0080CombatState state = new A0061A0080CombatState();
        CombatPerkRanks bothRanks = CombatPerkRanks.of(Map.of("A0076", 1, "A0077", 1));

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<CombatPerkAvailabilityRuntime> availability = mockStatic(CombatPerkAvailabilityRuntime.class)) {
            runtime.when(() -> A0061A0080RuntimeState.ranks(player)).thenReturn(bothRanks);
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            availability.when(() -> CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0076")).thenReturn(true);
            availability.when(() -> CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0077")).thenReturn(true);

            assertTrue(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.AGGRESSIVE, state.stance("actor"));

            assertFalse(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.AGGRESSIVE, state.stance("actor"));

            assertTrue(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.CAUTIOUS, state.stance("actor"));

            assertTrue(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.NONE, state.stance("actor"));
        }
    }

    @Test
    void martialStanceHandlesSingleAndUnavailableChoicesFailClosed() {
        ServerLevel level = mock(ServerLevel.class);
        ServerPlayer player = eligiblePlayer(level);
        when(level.getGameTime()).thenReturn(20L, 60L, 100L, 140L);
        A0061A0080CombatState state = new A0061A0080CombatState();

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<CombatPerkAvailabilityRuntime> availability = mockStatic(CombatPerkAvailabilityRuntime.class)) {
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            availability.when(() -> CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0076")).thenReturn(true);
            availability.when(() -> CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0077")).thenReturn(true);

            runtime.when(() -> A0061A0080RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0076", 1)))
                .thenReturn(CombatPerkRanks.of(Map.of("A0076", 1)))
                .thenReturn(CombatPerkRanks.of(Map.of("A0077", 1)))
                .thenReturn(CombatPerkRanks.of(Map.of()));

            assertTrue(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.AGGRESSIVE, state.stance("actor"));
            assertTrue(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.NONE, state.stance("actor"));
            assertTrue(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.CAUTIOUS, state.stance("actor"));
            assertFalse(MartialStanceRuntime.cycle(player));
            assertEquals(A0061A0080CombatState.Stance.NONE, state.stance("actor"));
        }
    }

    @Test
    void martialStanceRejectsIneligiblePlayersBeforeReadingProgression() {
        assertFalse(MartialStanceRuntime.cycle(null));

        ServerLevel clientLevel = mock(ServerLevel.class);
        when(clientLevel.isClientSide()).thenReturn(true);
        ServerPlayer clientPlayer = mock(ServerPlayer.class);
        when(clientPlayer.level()).thenReturn(clientLevel);
        assertFalse(MartialStanceRuntime.cycle(clientPlayer));

        ServerLevel serverLevel = mock(ServerLevel.class);
        ServerPlayer creative = mock(ServerPlayer.class);
        when(creative.level()).thenReturn(serverLevel);
        when(creative.isCreative()).thenReturn(true);
        assertFalse(MartialStanceRuntime.cycle(creative));

        ServerPlayer spectator = mock(ServerPlayer.class);
        when(spectator.level()).thenReturn(serverLevel);
        when(spectator.isSpectator()).thenReturn(true);
        assertFalse(MartialStanceRuntime.cycle(spectator));
    }

    @Test
    void reconcileClearsStaleStanceAndInvalidatesPassengerOrForcedTransport() {
        ServerLevel level = mock(ServerLevel.class);
        ServerPlayer player = eligiblePlayer(level);
        when(player.isPassenger()).thenReturn(false, true);
        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.switchStance("actor", A0061A0080CombatState.Stance.AGGRESSIVE, 1_000L));
        StationaryStateService stationary = mock(StationaryStateService.class);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<A0079ForcedMovementCompat> forced = mockStatic(A0079ForcedMovementCompat.class)) {
            runtime.when(() -> A0061A0080RuntimeState.ranks(player)).thenReturn(CombatPerkRanks.of(Map.of()));
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            runtime.when(A0061A0080RuntimeState::stationary).thenReturn(stationary);
            forced.when(() -> A0079ForcedMovementCompat.forcedOrUnclassified(player)).thenReturn(false);

            MartialStanceRuntime.reconcile(player);
            assertEquals(A0061A0080CombatState.Stance.NONE, state.stance("actor"));
            verify(stationary, never()).invalidate("actor");

            MartialStanceRuntime.reconcile(player);
            verify(stationary).invalidate("actor");
        }
    }

    @Test
    void forcedMovementBoundaryFailsClosedForNullVersionDriftAndAdapterFailure() {
        ServerPlayer player = mock(ServerPlayer.class);
        assertTrue(A0079ForcedMovementCompat.forcedOrUnclassified(null));

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class)) {
            assertFalse(A0079ForcedMovementCompat.forcedOrUnclassified(player));
        }

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class)) {
            integrations.when(() -> OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.SABLE)).thenReturn(true);
            integrations.when(() -> OptionalIntegrations.version(OptionalIntegrations.Provider.SABLE)).thenReturn("2.0.4");
            assertTrue(A0079ForcedMovementCompat.forcedOrUnclassified(player));
        }

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<A0079SableTransportCompat> sable = mockStatic(A0079SableTransportCompat.class)) {
            integrations.when(() -> OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.SABLE)).thenReturn(true);
            integrations.when(() -> OptionalIntegrations.version(OptionalIntegrations.Provider.SABLE)).thenReturn("2.0.5");
            sable.when(() -> A0079SableTransportCompat.insideMovingSubLevel(player)).thenReturn(true);
            assertTrue(A0079ForcedMovementCompat.forcedOrUnclassified(player));
            sable.when(() -> A0079SableTransportCompat.insideMovingSubLevel(player)).thenThrow(new LinkageError("provider drift"));
            assertTrue(A0079ForcedMovementCompat.forcedOrUnclassified(player));
        }

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class)) {
            integrations.when(() -> OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.CREATE)).thenReturn(true);
            integrations.when(() -> OptionalIntegrations.version(OptionalIntegrations.Provider.CREATE)).thenReturn("6.0.9");
            assertTrue(A0079ForcedMovementCompat.forcedOrUnclassified(player));
        }

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<A0079CreateTransportCompat> create = mockStatic(A0079CreateTransportCompat.class)) {
            integrations.when(() -> OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.CREATE)).thenReturn(true);
            integrations.when(() -> OptionalIntegrations.version(OptionalIntegrations.Provider.CREATE)).thenReturn("6.0.10");
            create.when(() -> A0079CreateTransportCompat.onActiveBelt(player)).thenReturn(true);
            assertTrue(A0079ForcedMovementCompat.forcedOrUnclassified(player));
            create.when(() -> A0079CreateTransportCompat.onActiveBelt(player)).thenThrow(new RuntimeException("provider failure"));
            assertTrue(A0079ForcedMovementCompat.forcedOrUnclassified(player));
        }
    }

    @Test
    void martialStancePayloadIsIntentOnlyAndExecutesCycleOnlyForServerPlayer() {
        MartialStanceIntentPayload disabled = new MartialStanceIntentPayload(false);
        assertEquals(MartialStanceIntentPayload.PAYLOAD_TYPE, disabled.type());
        IPayloadContext ignored = mock(IPayloadContext.class);
        MartialStanceIntentPayload.handle(disabled, ignored);
        verifyNoInteractions(ignored);

        IPayloadContext context = mock(IPayloadContext.class);
        ServerPlayer serverPlayer = mock(ServerPlayer.class);
        when(context.player()).thenReturn(serverPlayer);
        doAnswer(invocation -> {
            ((Runnable) invocation.getArgument(0)).run();
            return null;
        }).when(context).enqueueWork(any(Runnable.class));

        try (MockedStatic<MartialStanceRuntime> stance = mockStatic(MartialStanceRuntime.class)) {
            MartialStanceIntentPayload.handle(new MartialStanceIntentPayload(true), context);
            stance.verify(() -> MartialStanceRuntime.cycle(serverPlayer));
        }

        IPayloadContext nonServerContext = mock(IPayloadContext.class);
        Player nonServerPlayer = mock(Player.class);
        when(nonServerContext.player()).thenReturn(nonServerPlayer);
        doAnswer(invocation -> {
            ((Runnable) invocation.getArgument(0)).run();
            return null;
        }).when(nonServerContext).enqueueWork(any(Runnable.class));
        try (MockedStatic<MartialStanceRuntime> stance = mockStatic(MartialStanceRuntime.class)) {
            MartialStanceIntentPayload.handle(new MartialStanceIntentPayload(true), nonServerContext);
            stance.verifyNoInteractions();
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
