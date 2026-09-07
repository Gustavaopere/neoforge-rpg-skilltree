package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0081A0090ProviderHitRegistry;
import dev.gustavopere.rpgskilltree.runtime.A0081A0090ProviderHitRegistry.PhysicalHitReceipt;
import dev.gustavopere.rpgskilltree.runtime.A0081A0090SustainRuntime;
import dev.gustavopere.rpgskilltree.runtime.A0081A0100RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSustainEvents;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSustainVersionContract;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0081A0090EventCoverageJUnitTest {
    private static final UUID PLAYER_ID = UUID.fromString("00000000-0000-0000-0000-000000008181");
    private static final UUID TARGET_ID = UUID.fromString("00000000-0000-0000-0000-000000008182");

    @AfterEach
    void clearStaticRegistries() {
        A0081A0090ProviderHitRegistry.clearAll();
        IronsSustainEvents.onServerStopped(mock(ServerStoppedEvent.class));
        A0081A0100CombatEvents.onServerStopped(mock(ServerStoppedEvent.class));
    }

    @Test
    void bowLaunchAndProjectileJoinCarryOneRootIntoPostMitigationSustain() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(20L);
        ServerPlayer player = eligiblePlayer(level, PLAYER_ID);

        ItemStack bowStack = mock(ItemStack.class);
        BowItem bow = mock(BowItem.class);
        ItemStack copiedWeapon = mock(ItemStack.class);
        when(bowStack.getItem()).thenReturn(bow);
        when(bowStack.copy()).thenReturn(copiedWeapon);

        ArrowLooseEvent loose = mock(ArrowLooseEvent.class);
        when(loose.getEntity()).thenReturn(player);
        when(loose.getBow()).thenReturn(bowStack);
        A0081A0100CombatEvents.onArrowLoose(loose);

        AbstractArrow arrow = mock(AbstractArrow.class);
        when(arrow.getOwner()).thenReturn(player);
        when(arrow.getWeaponItem()).thenReturn(bowStack);
        EntityJoinLevelEvent join = mock(EntityJoinLevelEvent.class);
        when(join.getEntity()).thenReturn(arrow);
        A0081A0100CombatEvents.onProjectileJoin(join);

        Player target = hostileTarget(TARGET_ID, 30.0F);
        DamageSource source = mock(DamageSource.class);
        when(source.is(any(TagKey.class))).thenReturn(true);
        when(source.getDirectEntity()).thenReturn(arrow);
        when(source.getEntity()).thenReturn(player);

        LivingIncomingDamageEvent incoming = mock(LivingIncomingDamageEvent.class);
        when(incoming.getSource()).thenReturn(source);
        when(incoming.getEntity()).thenReturn(target);
        when(incoming.getAmount()).thenReturn(8.0F);

        LivingDamageEvent.Post post = mock(LivingDamageEvent.Post.class);
        when(post.getSource()).thenReturn(source);
        when(post.getEntity()).thenReturn(target);
        when(post.getNewDamage()).thenReturn(6.0F);

        try (MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class);
             MockedStatic<A0081A0090SustainRuntime> sustain = mockStatic(A0081A0090SustainRuntime.class)) {
            runtime.when(() -> A0081A0100RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0082", 3)));

            A0081A0100CombatEvents.onIncomingDamage(incoming);
            A0081A0100CombatEvents.onDamagePost(post);

            sustain.verify(() -> A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                eq(player),
                startsWith("sustain-ranged/"),
                eq(30.0D),
                eq(6.0D),
                eq(false),
                eq(copiedWeapon)
            ));
        }
    }

    @Test
    void expiredCrossbowCorrelationFailsClosedInsteadOfInventingProjectileRoot() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(10L, 20L, 20L, 20L);
        ServerPlayer player = eligiblePlayer(level, PLAYER_ID);

        ItemStack crossbowStack = mock(ItemStack.class);
        CrossbowItem crossbow = mock(CrossbowItem.class);
        when(crossbowStack.getItem()).thenReturn(crossbow);

        ArrowLooseEvent loose = mock(ArrowLooseEvent.class);
        when(loose.getEntity()).thenReturn(player);
        when(loose.getBow()).thenReturn(crossbowStack);
        A0081A0100CombatEvents.onArrowLoose(loose);

        AbstractArrow arrow = mock(AbstractArrow.class);
        when(arrow.getOwner()).thenReturn(player);
        when(arrow.getWeaponItem()).thenReturn(crossbowStack);
        EntityJoinLevelEvent join = mock(EntityJoinLevelEvent.class);
        when(join.getEntity()).thenReturn(arrow);
        A0081A0100CombatEvents.onProjectileJoin(join);

        Player target = hostileTarget(TARGET_ID, 30.0F);
        DamageSource source = mock(DamageSource.class);
        when(source.is(any(TagKey.class))).thenReturn(true);
        when(source.getDirectEntity()).thenReturn(arrow);
        when(source.getEntity()).thenReturn(player);

        LivingIncomingDamageEvent incoming = mock(LivingIncomingDamageEvent.class);
        when(incoming.getSource()).thenReturn(source);
        when(incoming.getEntity()).thenReturn(target);
        when(incoming.getAmount()).thenReturn(8.0F);

        LivingDamageEvent.Post post = mock(LivingDamageEvent.Post.class);
        when(post.getSource()).thenReturn(source);
        when(post.getEntity()).thenReturn(target);
        when(post.getNewDamage()).thenReturn(6.0F);

        try (MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class);
             MockedStatic<A0081A0090SustainRuntime> sustain = mockStatic(A0081A0090SustainRuntime.class)) {
            A0081A0100CombatEvents.onIncomingDamage(incoming);
            A0081A0100CombatEvents.onDamagePost(post);
            runtime.verify(() -> A0081A0100RuntimeState.ranks(player), never());
            sustain.verifyNoInteractions();
        }
    }

    @Test
    void providerReceiptHasPriorityAndIsConsumedEvenWhenDamageDoesNotAuthorizeHealing() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(30L);
        ServerPlayer player = eligiblePlayer(level, PLAYER_ID);
        ItemStack weapon = mock(ItemStack.class);
        ItemStack copy = mock(ItemStack.class);
        when(weapon.copy()).thenReturn(copy);

        Player target = hostileTarget(TARGET_ID, 40.0F);
        DamageSource source = mock(DamageSource.class);
        PhysicalHitReceipt receipt = new PhysicalHitReceipt(
            player, "actor", "provider-root", 40.0D, true, weapon
        );
        A0081A0090ProviderHitRegistry.remember(source, TARGET_ID, receipt);

        LivingDamageEvent.Post post = mock(LivingDamageEvent.Post.class);
        when(post.getSource()).thenReturn(source);
        when(post.getEntity()).thenReturn(target);
        when(post.getNewDamage()).thenReturn(5.0F);

        try (MockedStatic<A0081A0090SustainRuntime> sustain = mockStatic(A0081A0090SustainRuntime.class)) {
            A0081A0100CombatEvents.onDamagePost(post);
            sustain.verify(() -> A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                player, "provider-root", 40.0D, 5.0D, true, copy
            ));
        }
        assertFalse(A0081A0090ProviderHitRegistry.take(source, TARGET_ID) != null);

        DamageSource zeroSource = mock(DamageSource.class);
        UUID zeroTarget = UUID.fromString("00000000-0000-0000-0000-000000008183");
        A0081A0090ProviderHitRegistry.remember(zeroSource, zeroTarget, receipt);
        Player zeroEntity = hostileTarget(zeroTarget, 10.0F);
        LivingDamageEvent.Post zeroPost = mock(LivingDamageEvent.Post.class);
        when(zeroPost.getSource()).thenReturn(zeroSource);
        when(zeroPost.getEntity()).thenReturn(zeroEntity);
        when(zeroPost.getNewDamage()).thenReturn(0.0F);

        try (MockedStatic<A0081A0090SustainRuntime> sustain = mockStatic(A0081A0090SustainRuntime.class)) {
            A0081A0100CombatEvents.onDamagePost(zeroPost);
            sustain.verifyNoInteractions();
        }
        assertFalse(A0081A0090ProviderHitRegistry.take(zeroSource, zeroTarget) != null);
    }

    @Test
    void tickAndPlayerLifecycleClearPendingCombatStateWithoutProcessingAnotherRoot() {
        ServerLevel playerLevel = mock(ServerLevel.class);
        when(playerLevel.getGameTime()).thenReturn(1L);
        ServerPlayer player = eligiblePlayer(playerLevel, PLAYER_ID);

        ItemStack bowStack = mock(ItemStack.class);
        when(bowStack.getItem()).thenReturn(mock(BowItem.class));
        ArrowLooseEvent loose = mock(ArrowLooseEvent.class);
        when(loose.getEntity()).thenReturn(player);
        when(loose.getBow()).thenReturn(bowStack);
        A0081A0100CombatEvents.onArrowLoose(loose);

        MinecraftServer server = mock(MinecraftServer.class);
        ServerLevel overworld = mock(ServerLevel.class);
        when(overworld.getGameTime()).thenReturn(100L);
        PlayerList playerList = mock(PlayerList.class);
        when(playerList.getPlayers()).thenReturn(List.of());
        when(server.overworld()).thenReturn(overworld);
        when(server.getPlayerList()).thenReturn(playerList);
        ServerTickEvent.Post tick = mock(ServerTickEvent.Post.class);
        when(tick.getServer()).thenReturn(server);

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class)) {
            A0081A0100CombatEvents.onServerTick(tick);
        }

        LivingDeathEvent death = mock(LivingDeathEvent.class);
        when(death.getEntity()).thenReturn(player);
        PlayerEvent.PlayerLoggedOutEvent logout = mock(PlayerEvent.PlayerLoggedOutEvent.class);
        when(logout.getEntity()).thenReturn(player);
        PlayerEvent.PlayerChangedDimensionEvent dimension = mock(PlayerEvent.PlayerChangedDimensionEvent.class);
        when(dimension.getEntity()).thenReturn(player);
        PlayerEvent.PlayerRespawnEvent respawn = mock(PlayerEvent.PlayerRespawnEvent.class);
        when(respawn.getEntity()).thenReturn(player);

        try (MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class);
             MockedStatic<A0061A0080RuntimeState> previous = mockStatic(A0061A0080RuntimeState.class)) {
            A0081A0100CombatEvents.onDeath(death);
            A0081A0100CombatEvents.onLogout(logout);
            A0081A0100CombatEvents.onDimension(dimension);
            A0081A0100CombatEvents.onRespawn(respawn);
            runtime.verify(() -> A0081A0100RuntimeState.clear(player), times(4));
            previous.verify(() -> A0061A0080RuntimeState.clear(player), times(4));

            A0081A0100CombatEvents.onServerStopped(mock(ServerStoppedEvent.class));
            runtime.verify(A0081A0100RuntimeState::clearAll);
            previous.verify(A0061A0080RuntimeState::clearAll);
        }
    }

    @Test
    void ironsDirectMagicAdapterRequiresExactOperationalContractAndHandsOffPostDamageOnce() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(40L);
        ServerPlayer player = eligiblePlayer(level, PLAYER_ID);
        Player target = hostileTarget(TARGET_ID, 45.0F);
        DamageSource source = mock(DamageSource.class);
        when(source.isDirect()).thenReturn(true);
        when(source.getEntity()).thenReturn(player);

        LivingIncomingDamageEvent incoming = mock(LivingIncomingDamageEvent.class);
        when(incoming.getSource()).thenReturn(source);
        when(incoming.getEntity()).thenReturn(target);
        when(incoming.getAmount()).thenReturn(9.0F);

        LivingDamageEvent.Post post = mock(LivingDamageEvent.Post.class);
        when(post.getSource()).thenReturn(source);
        when(post.getEntity()).thenReturn(target);
        when(post.getNewDamage()).thenReturn(7.0F);

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<IronsSustainVersionContract> contract = mockStatic(IronsSustainVersionContract.class);
             MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class);
             MockedStatic<A0081A0090SustainRuntime> sustain = mockStatic(A0081A0090SustainRuntime.class)) {
            integrations.when(() ->
                OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(true);
            integrations.when(() ->
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(IronsSustainVersionContract.SUPPORTED_RELEASE);
            contract.when(() ->
                IronsSustainVersionContract.supportsVersion(IronsSustainVersionContract.SUPPORTED_RELEASE)
            ).thenReturn(true);
            contract.when(IronsSustainVersionContract::runtimeContractPresent).thenReturn(true);
            contract.when(() -> IronsSustainVersionContract.isSpellDamageSource(source)).thenReturn(true);
            contract.when(() -> IronsSustainVersionContract.lifestealPercent(source)).thenReturn(0.0F);
            runtime.when(() -> A0081A0100RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0083", 3)));

            assertTrue(IronsSustainEvents.operational());
            IronsSustainEvents.onIncomingDamage(incoming);
            IronsSustainEvents.onDamagePost(post);

            sustain.verify(() -> A0081A0090SustainRuntime.resolveDirectMagicHit(
                eq(player),
                startsWith("irons-direct-magic/"),
                eq(45.0D),
                eq(7.0D),
                eq(false)
            ));
        }
    }

    @Test
    void ironsNativeLifestealIsMarkedAmbiguousAndLifecycleDropsPendingReceipt() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(50L);
        ServerPlayer player = eligiblePlayer(level, PLAYER_ID);
        Player target = hostileTarget(TARGET_ID, 50.0F);
        DamageSource source = mock(DamageSource.class);
        when(source.isDirect()).thenReturn(true);
        when(source.getEntity()).thenReturn(player);

        LivingIncomingDamageEvent incoming = mock(LivingIncomingDamageEvent.class);
        when(incoming.getSource()).thenReturn(source);
        when(incoming.getEntity()).thenReturn(target);
        when(incoming.getAmount()).thenReturn(10.0F);

        LivingDamageEvent.Post post = mock(LivingDamageEvent.Post.class);
        when(post.getSource()).thenReturn(source);
        when(post.getEntity()).thenReturn(target);
        when(post.getNewDamage()).thenReturn(8.0F);

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<IronsSustainVersionContract> contract = mockStatic(IronsSustainVersionContract.class);
             MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class);
             MockedStatic<A0081A0090SustainRuntime> sustain = mockStatic(A0081A0090SustainRuntime.class)) {
            integrations.when(() ->
                OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(true);
            integrations.when(() ->
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(IronsSustainVersionContract.SUPPORTED_RELEASE);
            contract.when(() ->
                IronsSustainVersionContract.supportsVersion(IronsSustainVersionContract.SUPPORTED_RELEASE)
            ).thenReturn(true);
            contract.when(IronsSustainVersionContract::runtimeContractPresent).thenReturn(true);
            contract.when(() -> IronsSustainVersionContract.isSpellDamageSource(source)).thenReturn(true);
            contract.when(() -> IronsSustainVersionContract.lifestealPercent(source)).thenReturn(0.25F);
            runtime.when(() -> A0081A0100RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0083", 3)));

            IronsSustainEvents.onIncomingDamage(incoming);
            IronsSustainEvents.onDamagePost(post);
            sustain.verify(() -> A0081A0090SustainRuntime.resolveDirectMagicHit(
                eq(player),
                anyString(),
                eq(50.0D),
                eq(8.0D),
                eq(true)
            ));
        }

        DamageSource lifecycleSource = mock(DamageSource.class);
        when(lifecycleSource.isDirect()).thenReturn(true);
        when(lifecycleSource.getEntity()).thenReturn(player);
        LivingIncomingDamageEvent lifecycleIncoming = mock(LivingIncomingDamageEvent.class);
        when(lifecycleIncoming.getSource()).thenReturn(lifecycleSource);
        when(lifecycleIncoming.getEntity()).thenReturn(target);
        when(lifecycleIncoming.getAmount()).thenReturn(10.0F);
        LivingDamageEvent.Post lifecyclePost = mock(LivingDamageEvent.Post.class);
        when(lifecyclePost.getSource()).thenReturn(lifecycleSource);
        when(lifecyclePost.getEntity()).thenReturn(target);
        when(lifecyclePost.getNewDamage()).thenReturn(8.0F);

        PlayerEvent.PlayerLoggedOutEvent logout = mock(PlayerEvent.PlayerLoggedOutEvent.class);
        when(logout.getEntity()).thenReturn(player);
        PlayerEvent.PlayerChangedDimensionEvent dimension = mock(PlayerEvent.PlayerChangedDimensionEvent.class);
        when(dimension.getEntity()).thenReturn(player);
        PlayerEvent.PlayerRespawnEvent respawn = mock(PlayerEvent.PlayerRespawnEvent.class);
        when(respawn.getEntity()).thenReturn(player);

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<IronsSustainVersionContract> contract = mockStatic(IronsSustainVersionContract.class);
             MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class);
             MockedStatic<A0081A0090SustainRuntime> sustain = mockStatic(A0081A0090SustainRuntime.class)) {
            integrations.when(() ->
                OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(true);
            integrations.when(() ->
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(IronsSustainVersionContract.SUPPORTED_RELEASE);
            contract.when(() ->
                IronsSustainVersionContract.supportsVersion(IronsSustainVersionContract.SUPPORTED_RELEASE)
            ).thenReturn(true);
            contract.when(IronsSustainVersionContract::runtimeContractPresent).thenReturn(true);
            contract.when(() -> IronsSustainVersionContract.isSpellDamageSource(lifecycleSource)).thenReturn(true);
            contract.when(() -> IronsSustainVersionContract.lifestealPercent(lifecycleSource)).thenReturn(0.0F);
            runtime.when(() -> A0081A0100RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0083", 3)));

            IronsSustainEvents.onIncomingDamage(lifecycleIncoming);
            IronsSustainEvents.onLogout(logout);
            IronsSustainEvents.onDamagePost(lifecyclePost);
            sustain.verifyNoInteractions();

            IronsSustainEvents.onDimension(dimension);
            IronsSustainEvents.onRespawn(respawn);
            IronsSustainEvents.onServerStopped(mock(ServerStoppedEvent.class));
        }
    }

    @Test
    void ironsOperationalFailsClosedForMissingProviderAndVersionDrift() {
        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<IronsSustainVersionContract> contract = mockStatic(IronsSustainVersionContract.class)) {
            assertFalse(IronsSustainEvents.operational());

            integrations.when(() ->
                OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(true);
            integrations.when(() ->
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn("wrong");
            contract.when(() -> IronsSustainVersionContract.supportsVersion("wrong")).thenReturn(false);
            assertFalse(IronsSustainEvents.operational());

            integrations.when(() ->
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(IronsSustainVersionContract.SUPPORTED_RELEASE);
            contract.when(() ->
                IronsSustainVersionContract.supportsVersion(IronsSustainVersionContract.SUPPORTED_RELEASE)
            ).thenReturn(true);
            contract.when(IronsSustainVersionContract::runtimeContractPresent).thenReturn(false);
            assertFalse(IronsSustainEvents.operational());
        }
    }

    private static ServerPlayer eligiblePlayer(ServerLevel level, UUID uuid) {
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(player.getUUID()).thenReturn(uuid);
        when(level.isClientSide()).thenReturn(false);
        when(player.isAlive()).thenReturn(true);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);
        return player;
    }

    private static Player hostileTarget(UUID uuid, float health) {
        Player target = mock(Player.class);
        when(target.getUUID()).thenReturn(uuid);
        when(target.getHealth()).thenReturn(health);
        when(target.isInvulnerable()).thenReturn(false);
        return target;
    }
}
