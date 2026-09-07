package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.StationaryStateService;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.MartialStanceRuntime;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

final class A0071A0080EpicFightRuntimeCoverageJUnitTest {
    @Test
    void versionAndPurePrivateBoundariesStayConservative() throws Exception {
        assertFalse(A0061A0080EpicFightHooks.supportsVersion(null));
        assertTrue(A0061A0080EpicFightHooks.supportsVersion(A0061A0080EpicFightHooks.SUPPORTED_VERSION_PREFIX + ".1"));
        assertFalse(A0061A0080EpicFightHooks.supportsVersion("0.0.0"));

        Method hasRuntimeEffect = method("hasRuntimeEffect", CombatPerkRanks.class);
        assertFalse((boolean) hasRuntimeEffect.invoke(null, CombatPerkRanks.of(Map.of())));
        assertTrue((boolean) hasRuntimeEffect.invoke(null, CombatPerkRanks.of(Map.of("A0079", 1))));

        LivingEntity target = mock(LivingEntity.class);
        Method healthFraction = method("healthFraction", LivingEntity.class);
        when(target.getMaxHealth()).thenReturn(0.0F);
        assertTrue((double) healthFraction.invoke(null, target) == 0.0D);
        when(target.getMaxHealth()).thenReturn(20.0F);
        when(target.getHealth()).thenReturn(30.0F);
        assertTrue((double) healthFraction.invoke(null, target) == 1.0D);
        when(target.getHealth()).thenReturn(-2.0F);
        assertTrue((double) healthFraction.invoke(null, target) == 0.0D);
    }

    @Test
    void exactVanillaMaceFallbackIsPhysicalAndUnknownItemIsNot() throws Exception {
        Method physicalMelee = method("physicalMelee", ItemStack.class);
        ItemStack mace = mock(ItemStack.class);
        ItemStack other = mock(ItemStack.class);
        when(mace.is(Items.MACE)).thenReturn(true);
        when(other.is(Items.MACE)).thenReturn(false);
        try (MockedStatic<EpicFightCapabilities> capabilities = mockStatic(EpicFightCapabilities.class)) {
            capabilities.when(() -> EpicFightCapabilities.getItemStackCapability(mace)).thenReturn(null);
            capabilities.when(() -> EpicFightCapabilities.getItemStackCapability(other)).thenReturn(null);
            assertTrue((boolean) physicalMelee.invoke(null, mace));
            assertFalse((boolean) physicalMelee.invoke(null, other));
        }
    }

    @Test
    void directHostileDamageArmsRetaliationOnlyForEligibleDirectHostileHit() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(20L);
        ServerPlayer player = eligiblePlayer(level);
        Player attacker = mock(Player.class);
        when(attacker.isAlive()).thenReturn(true);
        when(attacker.isInvulnerable()).thenReturn(false);
        when(player.isAlliedTo(attacker)).thenReturn(false);

        DamageSource source = mock(DamageSource.class);
        when(source.getDirectEntity()).thenReturn(attacker);
        when(source.getEntity()).thenReturn(attacker);
        LivingDamageEvent.Post event = mock(LivingDamageEvent.Post.class);
        when(event.getEntity()).thenReturn(player);
        when(event.getSource()).thenReturn(source);
        when(event.getNewDamage()).thenReturn(4.0F);

        A0061A0080CombatState state = new A0061A0080CombatState();
        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0061A0080RuntimeState.ranks(player)).thenReturn(CombatPerkRanks.of(Map.of("A0072", 1)));
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            A0061A0080EpicFightHooks.onDirectHostileDamageTaken(event);
            assertTrue(state.retaliationActive("actor", 1_001L));
        }
    }

    @Test
    void incomingPhysicalDamageAndServerTickOwnStanceAndStationarySampling() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.isClientSide()).thenReturn(false);
        ServerPlayer player = eligiblePlayer(level);
        when(player.getX()).thenReturn(1.0D);
        when(player.getY()).thenReturn(2.0D);
        when(player.getZ()).thenReturn(3.0D);
        when(player.isPassenger()).thenReturn(false);

        LivingIncomingDamageEvent incoming = mock(LivingIncomingDamageEvent.class);
        DamageSource source = mock(DamageSource.class);
        when(incoming.getEntity()).thenReturn(player);
        when(incoming.getSource()).thenReturn(source);
        when(incoming.getAmount()).thenReturn(100.0F);
        when(source.is(any(TagKey.class))).thenReturn(true);

        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.switchStance("actor", A0061A0080CombatState.Stance.AGGRESSIVE, 1_000L));
        StationaryStateService stationary = mock(StationaryStateService.class);

        MinecraftServer server = mock(MinecraftServer.class);
        PlayerList playerList = mock(PlayerList.class);
        ServerTickEvent.Post tick = mock(ServerTickEvent.Post.class);
        when(tick.getServer()).thenReturn(server);
        when(server.getPlayerList()).thenReturn(playerList);
        when(playerList.getPlayers()).thenReturn(List.of(player));

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class);
             MockedStatic<MartialStanceRuntime> stance = mockStatic(MartialStanceRuntime.class)) {
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::stationary).thenReturn(stationary);

            A0061A0080EpicFightHooks.onIncomingPhysicalDamage(incoming);
            verify(incoming).setAmount(105.0F);
            stance.verify(() -> MartialStanceRuntime.reconcile(player));

            A0061A0080EpicFightHooks.onServerTick(tick);
            stance.verify(() -> MartialStanceRuntime.reconcile(player), org.mockito.Mockito.times(2));
            verify(stationary).sample("actor", 1.0D, 2.0D, 3.0D, false);
        }
    }

    @Test
    void teleportKnockbackAndLifecycleCleanupInvalidateOrClearTransientState() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.isClientSide()).thenReturn(false);
        ServerPlayer player = eligiblePlayer(level);
        StationaryStateService stationary = mock(StationaryStateService.class);
        A0061A0080CombatState state = mock(A0061A0080CombatState.class);

        EntityTeleportEvent teleport = mock(EntityTeleportEvent.class);
        when(teleport.getEntity()).thenReturn(player);
        LivingKnockBackEvent knockback = mock(LivingKnockBackEvent.class);
        when(knockback.getEntity()).thenReturn(player);
        LivingDeathEvent death = mock(LivingDeathEvent.class);
        when(death.getEntity()).thenReturn(player);

        PlayerEvent.PlayerLoggedOutEvent logout = mock(PlayerEvent.PlayerLoggedOutEvent.class);
        when(logout.getEntity()).thenReturn(player);
        PlayerEvent.PlayerChangedDimensionEvent dimension = mock(PlayerEvent.PlayerChangedDimensionEvent.class);
        when(dimension.getEntity()).thenReturn(player);
        PlayerEvent.PlayerRespawnEvent respawn = mock(PlayerEvent.PlayerRespawnEvent.class);
        when(respawn.getEntity()).thenReturn(player);
        ServerStoppedEvent stopped = mock(ServerStoppedEvent.class);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(A0061A0080RuntimeState::stationary).thenReturn(stationary);
            runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn("actor");
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);

            A0061A0080EpicFightHooks.onTeleport(teleport);
            A0061A0080EpicFightHooks.onKnockback(knockback);
            verify(stationary, org.mockito.Mockito.times(2)).invalidate("actor");

            A0061A0080EpicFightHooks.onDeath(death);
            verify(state).clearTarget(player.getUUID().toString());
            runtime.verify(() -> A0061A0080RuntimeState.clear(player));

            A0061A0080EpicFightHooks.onLogout(logout);
            A0061A0080EpicFightHooks.onDimension(dimension);
            A0061A0080EpicFightHooks.onRespawn(respawn);
            runtime.verify(() -> A0061A0080RuntimeState.clear(player), org.mockito.Mockito.times(4));

            A0061A0080EpicFightHooks.onServerStopped(stopped);
            runtime.verify(A0061A0080RuntimeState::clearAll);
        }
    }

    @Test
    void rootActionIsUniqueUntilAHitIsRemembered() throws Exception {
        Method rootAction = method("rootAction", EpicFightDamageSource.class, String.class, long.class);
        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        String first = (String) rootAction.invoke(null, source, "target", 1_000L);
        String second = (String) rootAction.invoke(null, source, "target", 1_000L);
        assertNotEquals(first, second);
    }

    private static Method method(String name, Class<?>... parameters) throws Exception {
        Method method = A0061A0080EpicFightHooks.class.getDeclaredMethod(name, parameters);
        method.setAccessible(true);
        return method;
    }

    private static ServerPlayer eligiblePlayer(ServerLevel level) {
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);
        return player;
    }
}
