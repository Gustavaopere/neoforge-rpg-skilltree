package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.A0021A0040CombatPolicy.BeforeResult;
import dev.gustavopere.rpgskilltree.core.A0021A0040CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Adapter-level tests for the A0031-A0040 paths that plain domain tests cannot execute. */
final class A0031A0040EpicFightAdapterCoverageJUnitTest {
    @AfterEach
    void clearStaticAdapterState() {
        A0021A0040EpicFightHooks.onServerStopped(mock(ServerStoppedEvent.class));
        A0021A0040MasteryHooks.onServerStopped(mock(ServerStoppedEvent.class));
        A0021A0040RuntimeState.clearAll();
    }

    @Test
    void exactVanillaMaceFallbackIsSharedAndExternalUnknownsFailClosed() throws Exception {
        ItemStack mace = mock(ItemStack.class);
        ItemStack unknown = mock(ItemStack.class);
        when(mace.is(Items.MACE)).thenReturn(true);
        when(unknown.is(Items.MACE)).thenReturn(false);

        assertEquals(Optional.of(WeaponFamily.MACE), invokeFallback(A0021A0040EpicFightHooks.class, mace));
        assertEquals(Optional.empty(), invokeFallback(A0021A0040EpicFightHooks.class, unknown));
        assertEquals(Optional.of(WeaponFamily.MACE), invokeFallback(A0021A0040MasteryHooks.class, mace));
        assertEquals(Optional.empty(), invokeFallback(A0021A0040MasteryHooks.class, unknown));
    }

    @Test
    void descompassoAppliesStrongestMovementAndOnlyScalesCanonicalPhysicalDamage() throws Exception {
        UUID id = UUID.randomUUID();
        ServerPlayer target = mock(ServerPlayer.class);
        ServerLevel level = mock(ServerLevel.class);
        AttributeInstance movement = mock(AttributeInstance.class);
        when(target.getUUID()).thenReturn(id);
        when(target.level()).thenReturn(level);
        when(target.getAttribute(Attributes.MOVEMENT_SPEED)).thenReturn(movement);
        when(movement.getModifier(any(ResourceLocation.class))).thenReturn(null);
        when(level.getGameTime()).thenReturn(1L);

        Method apply = privateMethod(
            A0021A0040EpicFightHooks.class,
            "applyDescompasso",
            LivingEntity.class, double.class, double.class, long.class, long.class
        );
        apply.invoke(null, target, 0.92D, 0.90D, 3_000L, 0L);

        ArgumentCaptor<AttributeModifier> modifier = ArgumentCaptor.forClass(AttributeModifier.class);
        verify(movement).addOrUpdateTransientModifier(modifier.capture());
        assertEquals(-0.10D, modifier.getValue().amount(), 1.0E-9D);

        LivingIncomingDamageEvent physical = mock(LivingIncomingDamageEvent.class);
        DamageSource source = mock(DamageSource.class);
        when(physical.isCanceled()).thenReturn(false);
        when(physical.getAmount()).thenReturn(10.0F);
        when(physical.getSource()).thenReturn(source);
        when(source.is(any(TagKey.class))).thenReturn(true);
        when(source.getEntity()).thenReturn(target);

        A0021A0040EpicFightHooks.onDescompassoOutgoingDamage(physical);
        verify(physical).setAmount(9.2F);

        LivingIncomingDamageEvent nonPhysical = mock(LivingIncomingDamageEvent.class);
        DamageSource nonPhysicalSource = mock(DamageSource.class);
        when(nonPhysical.getAmount()).thenReturn(10.0F);
        when(nonPhysical.getSource()).thenReturn(nonPhysicalSource);
        when(nonPhysicalSource.is(any(TagKey.class))).thenReturn(false);
        A0021A0040EpicFightHooks.onDescompassoOutgoingDamage(nonPhysical);
        verify(nonPhysical, never()).setAmount(any(Float.class));

        when(level.getGameTime()).thenReturn(100L);
        A0021A0040EpicFightHooks.onDescompassoOutgoingDamage(physical);
        verify(movement).removeModifier(any(ResourceLocation.class));
    }

    @Test
    void descompassoRejectsInvalidPlansAndServerTickExpiresState() throws Exception {
        UUID id = UUID.randomUUID();
        LivingEntity target = mock(LivingEntity.class);
        AttributeInstance movement = mock(AttributeInstance.class);
        when(target.getUUID()).thenReturn(id);
        when(target.getAttribute(Attributes.MOVEMENT_SPEED)).thenReturn(movement);
        when(movement.getModifier(any(ResourceLocation.class))).thenReturn(null);

        Method apply = privateMethod(
            A0021A0040EpicFightHooks.class,
            "applyDescompasso",
            LivingEntity.class, double.class, double.class, long.class, long.class
        );
        apply.invoke(null, target, 0.92D, 0.90D, 0L, 0L);
        apply.invoke(null, target, 0.0D, 0.90D, 1_000L, 0L);
        apply.invoke(null, target, 1.01D, 0.90D, 1_000L, 0L);
        apply.invoke(null, target, 0.92D, 0.0D, 1_000L, 0L);
        apply.invoke(null, target, 0.92D, 1.01D, 1_000L, 0L);
        verify(movement, never()).addOrUpdateTransientModifier(any(AttributeModifier.class));

        apply.invoke(null, target, 0.92D, 0.90D, 1_000L, 0L);
        clearInvocations(movement);

        MinecraftServer server = mock(MinecraftServer.class);
        ServerLevel level = mock(ServerLevel.class);
        ServerTickEvent.Post tick = mock(ServerTickEvent.Post.class);
        when(tick.getServer()).thenReturn(server);
        when(server.overworld()).thenReturn(level);
        when(level.getGameTime()).thenReturn(100L);
        when(server.getAllLevels()).thenReturn(List.of(level));
        when(level.getEntity(id)).thenReturn(target);

        A0021A0040EpicFightHooks.onServerTickPost(tick);
        verify(movement).removeModifier(any(ResourceLocation.class));
    }

    @Test
    void vanillaPostCommitsPreparedSunderAndBonebreakerOnlyAfterActualDamage() throws Exception {
        UUID actorUuid = UUID.randomUUID();
        UUID targetUuid = UUID.randomUUID();
        ServerPlayer player = mock(ServerPlayer.class);
        ServerPlayer target = mock(ServerPlayer.class);
        ServerLevel level = mock(ServerLevel.class);
        DamageSource source = mock(DamageSource.class);
        LivingDamageEvent.Post event = mock(LivingDamageEvent.Post.class);
        AttributeInstance armor = mock(AttributeInstance.class);
        AttributeInstance movement = mock(AttributeInstance.class);
        when(player.getUUID()).thenReturn(actorUuid);
        when(player.level()).thenReturn(level);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);
        when(player.isAlliedTo(target)).thenReturn(false);
        when(target.getUUID()).thenReturn(targetUuid);
        when(target.level()).thenReturn(level);
        when(target.isInvulnerable()).thenReturn(false);
        when(target.getHealth()).thenReturn(10.0F);
        when(target.getMaxHealth()).thenReturn(20.0F);
        when(target.getAttribute(Attributes.ARMOR)).thenReturn(armor);
        when(target.getAttribute(Attributes.MOVEMENT_SPEED)).thenReturn(movement);
        when(armor.getModifier(any(ResourceLocation.class))).thenReturn(null);
        when(movement.getModifier(any(ResourceLocation.class))).thenReturn(null);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(20L);
        when(event.getEntity()).thenReturn(target);
        when(event.getSource()).thenReturn(source);
        when(source.getDirectEntity()).thenReturn(player);
        when(event.getNewDamage()).thenReturn(4.0F);

        String actor = actorUuid.toString();
        String targetId = targetUuid.toString();
        String root = "adapter-confirmed-root";
        long now = 1_000L;
        A0021A0040CombatState state = new A0021A0040CombatState();
        for (int i = 0; i < 3; i++) state.addTrauma(actor, targetId, 2, i);
        assertTrue(state.prepareSunder(actor, targetId, root, 2, now));
        assertTrue(state.prepareBonebreaker(actor, targetId, root, 80, now));

        BeforeResult specialty = new BeforeResult(
            1.0D, 1.0D, 1.0D, 0.0D,
            true, 0.12D, 6_000L,
            true, 0.92D, 0.90D, 3_000L
        );
        putVanillaPending(actor, targetId, root, specialty, 31_000L);
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0035", 2, "A0036", 1));

        try (MockedStatic<A0021A0040RuntimeState> runtime = mockStatic(A0021A0040RuntimeState.class)) {
            runtime.when(A0021A0040RuntimeState::state).thenReturn(state);
            runtime.when(() -> A0021A0040RuntimeState.ranks(player)).thenReturn(ranks);
            A0021A0040EpicFightHooks.onLivingDamagePost(event);
        }

        assertTrue(state.isSundered(actor, targetId, now));
        assertFalse(state.bonebreakerReady(actor, targetId, now));
        verify(armor).addOrUpdateTransientModifier(any(AttributeModifier.class));
        verify(movement).addOrUpdateTransientModifier(any(AttributeModifier.class));
    }

    @Test
    void vanillaPostZeroDamageRollsBackPreparedMaceActions() throws Exception {
        UUID actorUuid = UUID.randomUUID();
        UUID targetUuid = UUID.randomUUID();
        ServerPlayer player = mock(ServerPlayer.class);
        ServerPlayer target = mock(ServerPlayer.class);
        ServerLevel level = mock(ServerLevel.class);
        DamageSource source = mock(DamageSource.class);
        LivingDamageEvent.Post event = mock(LivingDamageEvent.Post.class);
        when(player.getUUID()).thenReturn(actorUuid);
        when(player.level()).thenReturn(level);
        when(player.isAlliedTo(target)).thenReturn(false);
        when(target.getUUID()).thenReturn(targetUuid);
        when(target.level()).thenReturn(level);
        when(target.isInvulnerable()).thenReturn(false);
        when(target.getHealth()).thenReturn(10.0F);
        when(target.getMaxHealth()).thenReturn(20.0F);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(20L);
        when(event.getEntity()).thenReturn(target);
        when(event.getSource()).thenReturn(source);
        when(source.getDirectEntity()).thenReturn(player);
        when(event.getNewDamage()).thenReturn(0.0F);

        String actor = actorUuid.toString();
        String targetId = targetUuid.toString();
        String root = "adapter-zero-root";
        long now = 1_000L;
        A0021A0040CombatState state = new A0021A0040CombatState();
        for (int i = 0; i < 3; i++) state.addTrauma(actor, targetId, 2, i);
        assertTrue(state.prepareSunder(actor, targetId, root, 2, now));
        assertTrue(state.prepareBonebreaker(actor, targetId, root, 80, now));
        putVanillaPending(actor, targetId, root, BeforeResult.neutral(), 31_000L);

        try (MockedStatic<A0021A0040RuntimeState> runtime = mockStatic(A0021A0040RuntimeState.class)) {
            runtime.when(A0021A0040RuntimeState::state).thenReturn(state);
            runtime.when(() -> A0021A0040RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0035", 2, "A0036", 1)));
            A0021A0040EpicFightHooks.onLivingDamagePost(event);
        }

        assertEquals(3, state.trauma(actor, targetId, now));
        assertFalse(state.isSundered(actor, targetId, now));
        assertTrue(state.bonebreakerReady(actor, targetId, now));
        assertTrue(state.prepareSunder(actor, targetId, root, 2, now));
        assertTrue(state.prepareBonebreaker(actor, targetId, root, 80, now));
    }

    @Test
    void deathCleansDescompassoModifierAndState() throws Exception {
        UUID id = UUID.randomUUID();
        LivingEntity target = mock(LivingEntity.class);
        AttributeInstance movement = mock(AttributeInstance.class);
        LivingDeathEvent death = mock(LivingDeathEvent.class);
        when(target.getUUID()).thenReturn(id);
        when(target.getAttribute(Attributes.MOVEMENT_SPEED)).thenReturn(movement);
        when(target.getAttribute(Attributes.ARMOR)).thenReturn(null);
        when(movement.getModifier(any(ResourceLocation.class))).thenReturn(null);
        when(death.getEntity()).thenReturn(target);

        privateMethod(
            A0021A0040EpicFightHooks.class,
            "applyDescompasso",
            LivingEntity.class, double.class, double.class, long.class, long.class
        ).invoke(null, target, 0.92D, 0.90D, 3_000L, 0L);
        clearInvocations(movement);

        A0021A0040EpicFightHooks.onDeath(death);
        verify(movement).removeModifier(any(ResourceLocation.class));
    }

    @Test
    void masteryAdapterCreditsFiniteDiscoveryOnce() throws Exception {
        ServerPlayer player = mock(ServerPlayer.class);
        LivingEntity target = mock(LivingEntity.class);
        when(target.getType()).thenReturn(EntityType.ZOMBIE);
        ProgressionState progression = mock(ProgressionState.class, RETURNS_DEEP_STUBS);
        when(progression.discoveries().contains(anyString())).thenReturn(false);

        Method award = privateMethod(
            A0021A0040MasteryHooks.class,
            "award",
            ServerPlayer.class, LivingEntity.class, WeaponFamily.class, double.class
        );
        try (MockedStatic<PlayerProgressionRuntime> runtime = mockStatic(PlayerProgressionRuntime.class)) {
            runtime.when(() -> PlayerProgressionRuntime.get(player)).thenReturn(progression);
            award.invoke(null, player, target, WeaponFamily.MACE, 4.0D);
            runtime.verify(() -> PlayerProgressionRuntime.awardMasteryAndDiscoveries(
                eq(player), anyCollection(), anyCollection()
            ));
        }
    }

    @SuppressWarnings("unchecked")
    private static Optional<WeaponFamily> invokeFallback(Class<?> owner, ItemStack stack) throws Exception {
        Method method = privateMethod(owner, "vanillaFallbackFamily", ItemStack.class);
        return (Optional<WeaponFamily>) method.invoke(null, stack);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void putVanillaPending(
        String actor,
        String target,
        String root,
        BeforeResult specialty,
        long expiresAt
    ) throws Exception {
        Class<?> pendingClass = Class.forName(A0021A0040EpicFightHooks.class.getName() + "$PendingVanilla");
        Constructor<?> constructor = pendingClass.getDeclaredConstructor(
            String.class, WeaponFamily.class, boolean.class, boolean.class, boolean.class, BeforeResult.class, long.class
        );
        constructor.setAccessible(true);
        Object pending = constructor.newInstance(root, WeaponFamily.MACE, false, true, false, specialty, expiresAt);
        Field field = A0021A0040EpicFightHooks.class.getDeclaredField("VANILLA_PENDING");
        field.setAccessible(true);
        Map map = (Map) field.get(null);
        map.put(actor + '\0' + target, pending);
    }

    private static Method privateMethod(Class<?> owner, String name, Class<?>... parameterTypes) throws Exception {
        Method method = owner.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }
}
