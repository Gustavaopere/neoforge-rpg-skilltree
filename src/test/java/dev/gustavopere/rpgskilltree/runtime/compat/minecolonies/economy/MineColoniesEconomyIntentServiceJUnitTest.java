package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.managers.interfaces.ICitizenManager;
import com.minecolonies.api.colony.managers.interfaces.IRegisteredStructureManager;
import com.minecolonies.api.colony.permissions.Action;
import com.minecolonies.api.colony.permissions.IPermissions;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMutationResult;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

final class MineColoniesEconomyIntentServiceJUnitTest {
    private static final NativeColonyBinding REQUESTED = new NativeColonyBinding(Level.OVERWORLD.location(), 7);

    @Test
    void mintIsServerResolvedPermissionCheckedAndReplaySafe() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ServerPlayer player = mock(ServerPlayer.class);
        IColony colony = validColony(player, true);
        UUID intent = UUID.fromString("00000000-0000-0000-0000-000000001501");

        MineColoniesEconomyIntentResult first = MineColoniesEconomyIntentService.mint(
            player, colony, REQUESTED, intent, 20L, 1_000L, data
        );
        MineColoniesEconomyIntentResult replay = MineColoniesEconomyIntentService.mint(
            player, colony, REQUESTED, intent, 20L, 1_001L, data
        );

        assertEquals(MineColoniesEconomyIntentStatus.APPLIED, first.status());
        assertEquals(20L, first.state().orElseThrow().effectiveSupply());
        assertEquals(MineColoniesEconomyIntentStatus.DUPLICATE, replay.status());
        assertEquals(20L, replay.state().orElseThrow().effectiveSupply());
    }

    @Test
    void wrongColonyAndPermissionDenialNeverCreateEconomy() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ServerPlayer player = mock(ServerPlayer.class);
        IColony colony = validColony(player, false);

        MineColoniesEconomyIntentResult wrong = MineColoniesEconomyIntentService.mint(
            player,
            colony,
            new NativeColonyBinding(Level.OVERWORLD.location(), 8),
            UUID.fromString("00000000-0000-0000-0000-000000001502"),
            1L,
            1L,
            data
        );
        MineColoniesEconomyIntentResult denied = MineColoniesEconomyIntentService.mint(
            player,
            colony,
            REQUESTED,
            UUID.fromString("00000000-0000-0000-0000-000000001503"),
            1L,
            2L,
            data
        );

        assertEquals(MineColoniesEconomyIntentStatus.WRONG_COLONY, wrong.status());
        assertEquals(MineColoniesEconomyIntentStatus.PERMISSION_DENIED, denied.status());
        assertTrue(data.binding(REQUESTED).isEmpty());
    }

    @Test
    void invalidAndProtocolOversizedAmountsFailBeforeMutation() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ServerPlayer player = mock(ServerPlayer.class);
        IColony colony = validColony(player, true);

        MineColoniesEconomyIntentResult invalid = MineColoniesEconomyIntentService.mint(
            player,
            colony,
            REQUESTED,
            UUID.fromString("00000000-0000-0000-0000-000000001504"),
            -1L,
            1L,
            data
        );
        MineColoniesEconomyIntentResult oversized = MineColoniesEconomyIntentService.mint(
            player,
            colony,
            REQUESTED,
            UUID.fromString("00000000-0000-0000-0000-000000001505"),
            (long) Integer.MAX_VALUE + 1L,
            2L,
            data
        );

        assertEquals(MineColoniesEconomyIntentStatus.INVALID_AMOUNT, invalid.status());
        assertEquals(MineColoniesEconomyIntentStatus.PROTOCOL_LIMIT_EXCEEDED, oversized.status());
        assertTrue(data.binding(REQUESTED).isEmpty());
    }

    @Test
    void preflightIsReadOnlyAndUsesServerDerivedCapacity() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ServerPlayer player = mock(ServerPlayer.class);
        IColony colony = validColony(player, true);

        MineColoniesEconomyPreflightResult result = MineColoniesEconomyIntentService.preflightMint(
            player, colony, REQUESTED, 20L, data
        );

        assertEquals(MineColoniesEconomyIntentStatus.ACCEPTED, result.status());
        assertTrue(result.preflight().isPresent());
        assertEquals(2L, result.preflight().orElseThrow().economicCapacity());
        assertFalse(data.binding(REQUESTED).isPresent(), "preflight must not allocate persistent identity");
    }

    @Test
    void retireMapsCanonicalInsufficientTreasuryWithoutSideEffects() {
        ColonyEconomySavedData data = new ColonyEconomySavedData();
        ServerPlayer player = mock(ServerPlayer.class);
        IColony colony = validColony(player, true);

        MineColoniesEconomyIntentResult result = MineColoniesEconomyIntentService.retire(
            player,
            colony,
            REQUESTED,
            UUID.fromString("00000000-0000-0000-0000-000000001506"),
            1L,
            3L,
            data
        );

        assertEquals(MineColoniesEconomyIntentStatus.INSUFFICIENT_TREASURY, result.status());
        assertEquals(EconomyMutationResult.Status.INSUFFICIENT_TREASURY, result.ledgerStatus().orElseThrow());
    }

    private static IColony validColony(ServerPlayer player, boolean permitted) {
        IColony colony = mock(IColony.class);
        IPermissions permissions = mock(IPermissions.class);
        ICitizenManager citizens = mock(ICitizenManager.class);
        IRegisteredStructureManager buildings = mock(IRegisteredStructureManager.class);
        when(colony.getID()).thenReturn(7);
        when(colony.getDimension()).thenReturn(Level.OVERWORLD);
        when(colony.getPermissions()).thenReturn(permissions);
        when(permissions.hasPermission(player, Action.MANAGE_HUTS)).thenReturn(permitted);
        when(colony.getCitizenManager()).thenReturn(citizens);
        when(citizens.getCitizens()).thenReturn(List.of());
        when(colony.getServerBuildingManager()).thenReturn(buildings);
        when(buildings.getBuildings()).thenReturn(Map.of());
        when(buildings.getWareHouses()).thenReturn(List.of());
        return colony;
    }
}
