package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import dev.gustavopere.rpgskilltree.runtime.A0081A0090ProviderHitRegistry.PhysicalHitReceipt;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsSustainVersionContract;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0081A0090RuntimeCoverageJUnitTest {
    private static final UUID PLAYER_ID = UUID.fromString("00000000-0000-0000-0000-000000008081");
    private static final UUID OTHER_PLAYER_ID = UUID.fromString("00000000-0000-0000-0000-000000008082");

    @AfterEach
    void clearState() {
        A0081A0090ProviderHitRegistry.clearAll();
        A0081A0100RuntimeState.clearAll();
    }

    @Test
    void providerHitRegistryIsOneShotActorScopedAndValidatesReceipts() {
        DamageSource source = mock(DamageSource.class);
        ServerPlayer player = mock(ServerPlayer.class);
        ItemStack original = mock(ItemStack.class);
        ItemStack copied = mock(ItemStack.class);
        when(original.copy()).thenReturn(copied);

        UUID firstTarget = UUID.fromString("10000000-0000-0000-0000-000000000001");
        PhysicalHitReceipt first = new PhysicalHitReceipt(
            player, "actor-a", "root-a", 24.0D, true, original
        );
        assertSame(copied, first.weaponStack());

        A0081A0090ProviderHitRegistry.remember(source, firstTarget, first);
        assertSame(first, A0081A0090ProviderHitRegistry.take(source, firstTarget));
        assertNull(A0081A0090ProviderHitRegistry.take(source, firstTarget));

        UUID discardedTarget = UUID.fromString("10000000-0000-0000-0000-000000000002");
        A0081A0090ProviderHitRegistry.remember(source, discardedTarget, first);
        A0081A0090ProviderHitRegistry.discard(source, discardedTarget);
        assertNull(A0081A0090ProviderHitRegistry.take(source, discardedTarget));

        UUID actorATarget = UUID.fromString("10000000-0000-0000-0000-000000000003");
        UUID actorBTarget = UUID.fromString("10000000-0000-0000-0000-000000000004");
        PhysicalHitReceipt actorA = new PhysicalHitReceipt(
            player, "actor-a", "root-a2", 10.0D, false, original
        );
        PhysicalHitReceipt actorB = new PhysicalHitReceipt(
            player, "actor-b", "root-b", 12.0D, false, original
        );
        A0081A0090ProviderHitRegistry.remember(source, actorATarget, actorA);
        A0081A0090ProviderHitRegistry.remember(source, actorBTarget, actorB);
        A0081A0090ProviderHitRegistry.clearActor("actor-a");
        assertNull(A0081A0090ProviderHitRegistry.take(source, actorATarget));
        assertSame(actorB, A0081A0090ProviderHitRegistry.take(source, actorBTarget));

        assertNull(A0081A0090ProviderHitRegistry.take(null, firstTarget));
        assertNull(A0081A0090ProviderHitRegistry.take(source, null));
        assertThrows(NullPointerException.class,
            () -> A0081A0090ProviderHitRegistry.remember(null, firstTarget, first));
        assertThrows(NullPointerException.class,
            () -> A0081A0090ProviderHitRegistry.remember(source, null, first));
        assertThrows(NullPointerException.class,
            () -> A0081A0090ProviderHitRegistry.remember(source, firstTarget, null));
        assertThrows(NullPointerException.class,
            () -> new PhysicalHitReceipt(null, "actor", "root", 1.0D, true, original));
        assertThrows(NullPointerException.class,
            () -> new PhysicalHitReceipt(player, "actor", "root", 1.0D, true, null));
        assertThrows(IllegalArgumentException.class,
            () -> new PhysicalHitReceipt(player, "", "root", 1.0D, true, original));
        assertThrows(IllegalArgumentException.class,
            () -> new PhysicalHitReceipt(player, "actor", " ", 1.0D, true, original));
        assertThrows(IllegalArgumentException.class,
            () -> new PhysicalHitReceipt(player, "actor", "root", -1.0D, true, original));
        assertThrows(IllegalArgumentException.class,
            () -> new PhysicalHitReceipt(player, "actor", "root", Double.NaN, true, original));

        A0081A0090ProviderHitRegistry.clearAll();
    }

    @Test
    void availabilityMasksFailClosedNodesAndOnlyOpensExactIronsContract() {
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0081"));
        assertTrue(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0082"));
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0084"));
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0085"));
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0086"));
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0087"));
        assertTrue(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0088"));
        assertTrue(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0089"));
        assertTrue(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0090"));

        ResourceLocation a0081 = ResourceLocation.parse(CombatPerkNodeBinding.nodeId("A0081"));
        ResourceLocation a0082 = ResourceLocation.parse(CombatPerkNodeBinding.nodeId("A0082"));
        assertFalse(CombatPerkAvailabilityRuntime.isAvailable(a0081));
        assertTrue(CombatPerkAvailabilityRuntime.isAvailable(a0082));
        assertTrue(CombatPerkAvailabilityRuntime.isAvailable(
            ResourceLocation.parse("rpgskilltree:utility/unrelated")
        ));

        CombatPerkRanks persisted = CombatPerkRanks.of(Map.of(
            "A0081", 1,
            "A0082", 3,
            "A0087", 1,
            "A0088", 5
        ));
        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(persisted);
        assertEquals(0, effective.rank("A0081"));
        assertEquals(3, effective.rank("A0082"));
        assertEquals(0, effective.rank("A0087"));
        assertEquals(5, effective.rank("A0088"));

        ProgressionState state = state(Map.of("A0081", 1, "A0082", 2, "A0088", 3));
        CombatPerkRanks progressRanks = CombatPerkAvailabilityRuntime.effectiveRanks(state.passiveNodes());
        assertEquals(0, progressRanks.rank("A0081"));
        assertEquals(2, progressRanks.rank("A0082"));
        assertEquals(3, progressRanks.rank("A0088"));

        ProgressionState accessState = CombatPerkAvailabilityRuntime.effectiveAccessState(state);
        assertEquals(0, accessState.passiveNodes().rank(CombatPerkNodeBinding.nodeId("A0081")));
        assertEquals(2, accessState.passiveNodes().rank(CombatPerkNodeBinding.nodeId("A0082")));
        assertEquals(1, state.passiveNodes().rank(CombatPerkNodeBinding.nodeId("A0081")));

        try (MockedStatic<OptionalIntegrations> integrations = mockStatic(OptionalIntegrations.class);
             MockedStatic<IronsSustainVersionContract> contract = mockStatic(IronsSustainVersionContract.class)) {
            assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0083"));

            integrations.when(() ->
                OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(true);
            integrations.when(() ->
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn("drifted");
            contract.when(() -> IronsSustainVersionContract.supportsVersion("drifted"))
                .thenReturn(false);
            assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0083"));

            integrations.when(() ->
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            ).thenReturn(IronsSustainVersionContract.SUPPORTED_RELEASE);
            contract.when(() ->
                IronsSustainVersionContract.supportsVersion(IronsSustainVersionContract.SUPPORTED_RELEASE)
            ).thenReturn(true);
            contract.when(IronsSustainVersionContract::runtimeContractPresent).thenReturn(false);
            assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0083"));

            contract.when(IronsSustainVersionContract::runtimeContractPresent).thenReturn(true);
            assertTrue(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0083"));
        }
    }

    @Test
    void runtimeStateMasksRanksAndClearsTransientClaimsOnlyWhenEffectiveSnapshotChanges() {
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.getUUID()).thenReturn(PLAYER_ID);

        ProgressionState first = state(Map.of("A0081", 1, "A0082", 1));
        ProgressionState changed = state(Map.of("A0081", 1, "A0082", 2));

        try (MockedStatic<PlayerProgressionRuntime> progression = mockStatic(PlayerProgressionRuntime.class)) {
            progression.when(() -> PlayerProgressionRuntime.get(player))
                .thenReturn(first, first, changed);

            CombatPerkRanks firstRanks = A0081A0100RuntimeState.ranks(player);
            assertEquals(0, firstRanks.rank("A0081"));
            assertEquals(1, firstRanks.rank("A0082"));

            SustainResolver resolver = A0081A0100RuntimeState.sustain();
            String actor = A0081A0100RuntimeState.actorId(player);
            var initial = resolver.resolve(sustainRequest(actor, "same-root"), 0L);
            assertEquals(SustainResolver.Status.AUTHORIZED, initial.status());

            CombatPerkRanks unchangedRanks = A0081A0100RuntimeState.ranks(player);
            assertEquals(1, unchangedRanks.rank("A0082"));
            var duplicate = resolver.resolve(sustainRequest(actor, "same-root"), 1L);
            assertEquals(SustainResolver.Status.DUPLICATE_EVENT, duplicate.status());

            CombatPerkRanks changedRanks = A0081A0100RuntimeState.ranks(player);
            assertEquals(2, changedRanks.rank("A0082"));
            var reopened = resolver.resolve(sustainRequest(actor, "same-root"), 2L);
            assertEquals(SustainResolver.Status.AUTHORIZED, reopened.status());
        }

        assertNotNull(A0081A0100RuntimeState.recovery());
        assertNotNull(A0081A0100RuntimeState.defense());
        assertNotNull(A0081A0100RuntimeState.bloodThirst());
        A0081A0100RuntimeState.clear(player);
        A0081A0100RuntimeState.clearAll();
    }

    @Test
    void physicalSustainUsesRecoveryOnceHealsOnceAndFailsClosedForAmbiguousNativeSource() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(10L);
        ServerPlayer player = eligiblePlayer(level, PLAYER_ID);
        when(player.getMaxHealth()).thenReturn(100.0F);
        when(player.getHealth()).thenReturn(50.0F);
        ItemStack ordinaryWeapon = mock(ItemStack.class);

        CombatRecoveryService recovery = mock(CombatRecoveryService.class);
        SustainResolver sustain = new SustainResolver();
        A0061A0080CombatState previousState = mock(A0061A0080CombatState.class);
        when(previousState.sustainedRhythmActive("physical", 500L)).thenReturn(true);

        try (MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class);
             MockedStatic<A0061A0080RuntimeState> previous = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0081A0100RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0081", 3, "A0082", 3)));
            runtime.when(() -> A0081A0100RuntimeState.actorId(player)).thenReturn("physical");
            runtime.when(A0081A0100RuntimeState::recovery).thenReturn(recovery);
            runtime.when(A0081A0100RuntimeState::sustain).thenReturn(sustain);
            previous.when(A0061A0080RuntimeState::state).thenReturn(previousState);

            A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                player, "physical-root", 20.0D, 10.0D, true, ordinaryWeapon
            );

            verify(recovery).recordDamage(any(CombatRecoveryService.DamageRequest.class), eq(500L));
            verify(player).heal(anyFloat());

            ServerPlayer ambiguous = eligiblePlayer(level, OTHER_PLAYER_ID);
            when(ambiguous.getMaxHealth()).thenReturn(100.0F);
            when(ambiguous.getHealth()).thenReturn(50.0F);
            ItemStack nativeWeapon = mock(ItemStack.class);
            when(nativeWeapon.is(any(TagKey.class))).thenReturn(true);

            runtime.when(() -> A0081A0100RuntimeState.ranks(ambiguous))
                .thenReturn(CombatPerkRanks.of(Map.of("A0082", 3)));
            runtime.when(() -> A0081A0100RuntimeState.actorId(ambiguous)).thenReturn("ambiguous");

            A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                ambiguous, "ambiguous-root", 20.0D, 10.0D, false, nativeWeapon
            );
            verify(ambiguous, never()).heal(anyFloat());

            ServerPlayer noCandidate = eligiblePlayer(level,
                UUID.fromString("00000000-0000-0000-0000-000000008083"));
            runtime.when(() -> A0081A0100RuntimeState.ranks(noCandidate))
                .thenReturn(CombatPerkRanks.empty());
            runtime.when(() -> A0081A0100RuntimeState.actorId(noCandidate)).thenReturn("none");
            A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                noCandidate, "no-candidate", 20.0D, 10.0D, false, ordinaryWeapon
            );
            verify(noCandidate, never()).heal(anyFloat());

            ServerPlayer creative = eligiblePlayer(level,
                UUID.fromString("00000000-0000-0000-0000-000000008084"));
            when(creative.isCreative()).thenReturn(true);
            A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                creative, "creative", 20.0D, 10.0D, false, ordinaryWeapon
            );
            runtime.verify(() -> A0081A0100RuntimeState.ranks(creative), never());
        }

        assertThrows(NullPointerException.class, () ->
            A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                null, "root", 1.0D, 1.0D, false, ordinaryWeapon
            )
        );
        assertThrows(NullPointerException.class, () ->
            A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                player, null, 1.0D, 1.0D, false, ordinaryWeapon
            )
        );
        assertThrows(NullPointerException.class, () ->
            A0081A0090SustainRuntime.resolvePhysicalWeaponHit(
                player, "root", 1.0D, 1.0D, false, null
            )
        );

        assertFalse(A0081A0090SustainRuntime.hasAmbiguousNativeWeaponLifesteal(null));
        ItemStack empty = mock(ItemStack.class);
        when(empty.isEmpty()).thenReturn(true);
        assertFalse(A0081A0090SustainRuntime.hasAmbiguousNativeWeaponLifesteal(empty));
        ItemStack tagged = mock(ItemStack.class);
        when(tagged.is(any(TagKey.class))).thenReturn(true);
        assertTrue(A0081A0090SustainRuntime.hasAmbiguousNativeWeaponLifesteal(tagged));
    }

    @Test
    void directMagicSustainHealsOnlyWhenNativeCorrelationIsUnambiguous() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.getGameTime()).thenReturn(20L);
        ServerPlayer safe = eligiblePlayer(level, PLAYER_ID);
        when(safe.getMaxHealth()).thenReturn(100.0F);
        when(safe.getHealth()).thenReturn(50.0F);

        ServerPlayer ambiguous = eligiblePlayer(level, OTHER_PLAYER_ID);
        when(ambiguous.getMaxHealth()).thenReturn(100.0F);
        when(ambiguous.getHealth()).thenReturn(50.0F);

        SustainResolver sustain = new SustainResolver();
        CombatPerkRanks magicRanks = CombatPerkRanks.of(Map.of("A0083", 3));

        try (MockedStatic<A0081A0100RuntimeState> runtime = mockStatic(A0081A0100RuntimeState.class)) {
            runtime.when(() -> A0081A0100RuntimeState.ranks(safe)).thenReturn(magicRanks);
            runtime.when(() -> A0081A0100RuntimeState.actorId(safe)).thenReturn("magic-safe");
            runtime.when(() -> A0081A0100RuntimeState.ranks(ambiguous)).thenReturn(magicRanks);
            runtime.when(() -> A0081A0100RuntimeState.actorId(ambiguous)).thenReturn("magic-ambiguous");
            runtime.when(A0081A0100RuntimeState::sustain).thenReturn(sustain);

            A0081A0090SustainRuntime.resolveDirectMagicHit(
                safe, "magic-root-safe", 30.0D, 10.0D, false
            );
            verify(safe).heal(anyFloat());

            A0081A0090SustainRuntime.resolveDirectMagicHit(
                ambiguous, "magic-root-ambiguous", 30.0D, 10.0D, true
            );
            verify(ambiguous, never()).heal(anyFloat());

            ServerPlayer noRank = eligiblePlayer(level,
                UUID.fromString("00000000-0000-0000-0000-000000008085"));
            runtime.when(() -> A0081A0100RuntimeState.ranks(noRank))
                .thenReturn(CombatPerkRanks.empty());
            A0081A0090SustainRuntime.resolveDirectMagicHit(
                noRank, "magic-no-rank", 30.0D, 10.0D, false
            );
            verify(noRank, never()).heal(anyFloat());
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

    private static ProgressionState state(Map<String, Integer> catalogRanks) {
        java.util.LinkedHashMap<String, Integer> nodeRanks = new java.util.LinkedHashMap<>();
        catalogRanks.forEach((code, rank) ->
            nodeRanks.put(CombatPerkNodeBinding.nodeId(code), rank)
        );
        return ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.of(nodeRanks));
    }

    private static SustainResolver.Request sustainRequest(String actor, String root) {
        return new SustainResolver.Request(
            actor,
            root,
            true,
            true,
            true,
            10.0D,
            10.0D,
            100.0D,
            100.0D,
            1.0D,
            SustainResolver.NativeCorrelation.NONE,
            0.0D,
            List.of(0.01D)
        );
    }
}
