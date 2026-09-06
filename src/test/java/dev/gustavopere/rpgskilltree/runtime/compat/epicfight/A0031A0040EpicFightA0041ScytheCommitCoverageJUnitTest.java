package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0021A0040CombatState;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatState;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0041A0060RuntimeState;
import java.lang.reflect.Method;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Loaded-provider coverage for the provider-confirmed POST boundary that owns A0041 consumption. */
final class A0031A0040EpicFightA0041ScytheCommitCoverageJUnitTest {
    @AfterEach
    void clearTransientState() {
        A0021A0040RuntimeState.clearAll();
        A0041A0060RuntimeState.clearAll();
    }

    @Test
    void helperClassificationFailsClosedAndClampsHealthFraction() throws Exception {
        Method eligible = privateMethod(A0041ScytheCommitHooks.class, "eligible", ServerPlayer.class);
        Method hostile = privateMethod(A0041ScytheCommitHooks.class, "hostileIdentity", ServerPlayer.class, LivingEntity.class);
        Method health = privateMethod(A0041ScytheCommitHooks.class, "healthFraction", LivingEntity.class);

        ServerPlayer actor = mock(ServerPlayer.class);
        ServerPlayer target = mock(ServerPlayer.class);
        ServerLevel level = mock(ServerLevel.class);
        when(actor.level()).thenReturn(level);
        when(level.isClientSide()).thenReturn(false);
        when(actor.isCreative()).thenReturn(false);
        when(actor.isSpectator()).thenReturn(false);
        assertTrue((boolean) eligible.invoke(null, actor));

        when(actor.isCreative()).thenReturn(true);
        assertFalse((boolean) eligible.invoke(null, actor));
        when(actor.isCreative()).thenReturn(false);
        when(actor.isSpectator()).thenReturn(true);
        assertFalse((boolean) eligible.invoke(null, actor));
        when(actor.isSpectator()).thenReturn(false);

        when(actor.isAlliedTo(target)).thenReturn(false);
        when(target.isInvulnerable()).thenReturn(false);
        assertTrue((boolean) hostile.invoke(null, actor, target));
        assertFalse((boolean) hostile.invoke(null, actor, actor));
        when(actor.isAlliedTo(target)).thenReturn(true);
        assertFalse((boolean) hostile.invoke(null, actor, target));
        when(actor.isAlliedTo(target)).thenReturn(false);
        when(target.isInvulnerable()).thenReturn(true);
        assertFalse((boolean) hostile.invoke(null, actor, target));

        when(target.getMaxHealth()).thenReturn(0.0F);
        assertEquals(0.0D, (double) health.invoke(null, target), 1.0E-9D);
        when(target.getMaxHealth()).thenReturn(20.0F);
        when(target.getHealth()).thenReturn(30.0F);
        assertEquals(1.0D, (double) health.invoke(null, target), 1.0E-9D);
        when(target.getHealth()).thenReturn(-5.0F);
        assertEquals(0.0D, (double) health.invoke(null, target), 1.0E-9D);
        when(target.getHealth()).thenReturn(5.0F);
        assertEquals(0.25D, (double) health.invoke(null, target), 1.0E-9D);
    }

    @Test
    void scytheClassificationRequiresARealEpicFightCapability() throws Exception {
        Method scythe = privateMethod(A0041ScytheCommitHooks.class, "scythe", EpicFightDamageSource.class);
        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        ItemStack usedItem = mock(ItemStack.class);
        CapabilityItem capability = mock(CapabilityItem.class, RETURNS_DEEP_STUBS);
        when(source.getUsedItem()).thenReturn(usedItem);

        try (MockedStatic<EpicFightCapabilities> capabilities = mockStatic(EpicFightCapabilities.class)) {
            capabilities.when(() -> EpicFightCapabilities.getItemStackCapability(usedItem)).thenReturn(null);
            assertFalse((boolean) scythe.invoke(null, source));

            capabilities.when(() -> EpicFightCapabilities.getItemStackCapability(usedItem)).thenReturn(capability);
            when(capability.isEmpty()).thenReturn(true);
            assertFalse((boolean) scythe.invoke(null, source));

            when(capability.isEmpty()).thenReturn(false);
            when(capability.getWeaponCategory().toString()).thenReturn("scythe");
            assertTrue((boolean) scythe.invoke(null, source));

            when(capability.getWeaponCategory().toString()).thenReturn("sword");
            assertFalse((boolean) scythe.invoke(null, source));
        }
    }

    @Test
    void zeroDamagePostRollsBackReservationWithoutConsumingLegacyMark() throws Exception {
        Fixture fixture = fixture(1_000L);
        A0021A0040CombatState legacy = A0021A0040RuntimeState.state();
        A0041A0060CombatState state = A0041A0060RuntimeState.state();
        String actorId = fixture.actor.getUUID().toString();
        String targetId = fixture.target.getUUID().toString();

        legacy.applyReapingMark(actorId, targetId, 2, 0.70D, 1_000L);
        assertTrue(legacy.reapMature(actorId, targetId, 0.40D, 1_050L));
        assertTrue(state.reserveScytheCut(actorId, targetId, "root-zero", 1_050L));
        fixture.event.setModifiedDamage(0.0F);

        invokeDamagePost(fixture);

        assertTrue(legacy.reapMarked(actorId, targetId, 1_051L));
        assertTrue(state.reserveScytheCut(actorId, targetId, "root-next", 1_052L),
            "zero provider damage must release the reservation for a later causal root");
    }

    @Test
    void positiveProviderConfirmedPostConsumesMatureMarkExactlyOnce() throws Exception {
        Fixture fixture = fixture(2_000L);
        A0021A0040CombatState legacy = A0021A0040RuntimeState.state();
        A0041A0060CombatState state = A0041A0060RuntimeState.state();
        String actorId = fixture.actor.getUUID().toString();
        String targetId = fixture.target.getUUID().toString();

        legacy.applyReapingMark(actorId, targetId, 2, 0.70D, 2_000L);
        assertTrue(legacy.reapMature(actorId, targetId, 0.40D, 2_050L));
        assertTrue(state.reserveScytheCut(actorId, targetId, "root-hit", 2_050L));
        fixture.event.setModifiedDamage(4.0F);

        invokeDamagePost(fixture);

        assertFalse(legacy.reapMarked(actorId, targetId, 2_051L));
        assertFalse(state.commitScytheCutReservation(actorId, targetId, "root-hit", 2_052L),
            "POST must consume the reservation itself");
    }

    @SuppressWarnings("unchecked")
    private static Fixture fixture(long gameTimeMillis) {
        ServerPlayer actor = mock(ServerPlayer.class);
        ServerPlayer target = mock(ServerPlayer.class);
        ServerLevel level = mock(ServerLevel.class);
        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        ItemStack usedItem = mock(ItemStack.class);
        CapabilityItem capability = mock(CapabilityItem.class, RETURNS_DEEP_STUBS);
        LivingEntityPatch<ServerPlayer> patch = (LivingEntityPatch<ServerPlayer>) mock(LivingEntityPatch.class);
        DealDamageEvent.Post event = new DealDamageEvent.Post(patch, target, source, 0.0F);
        UUID actorUuid = UUID.randomUUID();
        UUID targetUuid = UUID.randomUUID();

        when(actor.getUUID()).thenReturn(actorUuid);
        when(target.getUUID()).thenReturn(targetUuid);
        when(actor.level()).thenReturn(level);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(gameTimeMillis / 50L);
        when(actor.isCreative()).thenReturn(false);
        when(actor.isSpectator()).thenReturn(false);
        when(actor.isAlliedTo(target)).thenReturn(false);
        when(target.isInvulnerable()).thenReturn(false);
        when(target.getHealth()).thenReturn(8.0F);
        when(target.getMaxHealth()).thenReturn(20.0F);
        when(patch.getOriginal()).thenReturn(actor);
        when(source.getDirectEntity()).thenReturn(actor);
        when(source.getUsedItem()).thenReturn(usedItem);
        when(capability.isEmpty()).thenReturn(false);
        when(capability.getWeaponCategory().toString()).thenReturn("scythe");

        return new Fixture(actor, target, event, usedItem, capability);
    }

    private static void invokeDamagePost(Fixture fixture) throws Exception {
        try (MockedStatic<EpicFightCapabilities> capabilities = mockStatic(EpicFightCapabilities.class)) {
            capabilities.when(() -> EpicFightCapabilities.getItemStackCapability(fixture.usedItem)).thenReturn(fixture.capability);
            privateMethod(A0041ScytheCommitHooks.class, "onDamagePost", DealDamageEvent.Post.class)
                .invoke(null, fixture.event);
        }
    }

    private static Method privateMethod(Class<?> owner, String name, Class<?>... parameterTypes) throws Exception {
        Method method = owner.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    private record Fixture(
        ServerPlayer actor,
        ServerPlayer target,
        DealDamageEvent.Post event,
        ItemStack usedItem,
        CapabilityItem capability
    ) {}
}
