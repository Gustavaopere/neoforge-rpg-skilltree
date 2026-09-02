package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.A0021A0040CombatPolicy.BeforeResult;
import dev.gustavopere.rpgskilltree.core.A0021A0040CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Exercises the provider-native POST path separately from the vanilla fallback bridge. */
final class A0031A0040EpicFightProviderPostCoverageJUnitTest {
    @AfterEach
    void clearState() {
        A0021A0040EpicFightHooks.onServerStopped(mock(net.neoforged.neoforge.event.server.ServerStoppedEvent.class));
        A0021A0040RuntimeState.clearAll();
    }

    @Test
    void providerPostCommitsPreparedMaceEffectsOnlyAfterConfirmedDamage() throws Exception {
        UUID actorUuid = UUID.randomUUID();
        UUID targetUuid = UUID.randomUUID();
        ServerPlayer player = mock(ServerPlayer.class);
        ServerPlayer target = mock(ServerPlayer.class);
        ServerLevel level = mock(ServerLevel.class);
        AttributeInstance armor = mock(AttributeInstance.class);
        AttributeInstance movement = mock(AttributeInstance.class);
        EpicFightDamageSource source = mock(EpicFightDamageSource.class);
        DealDamageEvent.Post event = mock(DealDamageEvent.Post.class, RETURNS_DEEP_STUBS);

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
        when(event.getEntityPatch().getOriginal()).thenReturn(player);
        when(event.getTarget()).thenReturn(target);
        when(event.getDamageSource()).thenReturn(source);
        when(event.getModifiedDamage()).thenReturn(4.0F);

        String actor = actorUuid.toString();
        String targetId = targetUuid.toString();
        String root = "provider-post-root";
        long now = 1_000L;
        A0021A0040CombatState state = new A0021A0040CombatState();
        state.markSundered(actor, targetId, 2, 0L);
        for (int i = 0; i < 3; i++) state.addTrauma(actor, targetId, 2, i);
        assertTrue(state.prepareSunder(actor, targetId, root, 2, now));
        assertTrue(state.prepareBonebreaker(actor, targetId, root, 80, now));

        BeforeResult specialty = new BeforeResult(
            1.0D, 1.0D, 1.0D, 0.0D,
            true, 0.12D, 6_000L,
            true, 0.92D, 0.90D, 3_000L
        );
        putPending(source, targetId, root, specialty);

        try (MockedStatic<A0021A0040RuntimeState> runtime = mockStatic(A0021A0040RuntimeState.class)) {
            runtime.when(A0021A0040RuntimeState::state).thenReturn(state);
            runtime.when(() -> A0021A0040RuntimeState.ranks(player))
                .thenReturn(CombatPerkRanks.of(Map.of("A0035", 2, "A0036", 1)));
            privateMethod(A0021A0040EpicFightHooks.class, "onDamagePost", DealDamageEvent.Post.class)
                .invoke(null, event);
        }

        assertTrue(state.isSundered(actor, targetId, now));
        assertFalse(state.bonebreakerReady(actor, targetId, now));
        verify(armor).addOrUpdateTransientModifier(any(AttributeModifier.class));
        verify(movement).addOrUpdateTransientModifier(any(AttributeModifier.class));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void putPending(
        EpicFightDamageSource source,
        String targetId,
        String root,
        BeforeResult specialty
    ) throws Exception {
        Class<?> pendingClass = Class.forName(A0021A0040EpicFightHooks.class.getName() + "$PendingHit");
        Constructor<?> constructor = pendingClass.getDeclaredConstructor(
            String.class, WeaponFamily.class,
            boolean.class, boolean.class, boolean.class, boolean.class,
            boolean.class, boolean.class, BeforeResult.class
        );
        constructor.setAccessible(true);
        Object pending = constructor.newInstance(
            root, WeaponFamily.MACE,
            false, false, false, false,
            true, false, specialty
        );
        Field field = A0021A0040EpicFightHooks.class.getDeclaredField("PENDING");
        field.setAccessible(true);
        Map map = (Map) field.get(null);
        Map<String, Object> byTarget = new HashMap<>();
        byTarget.put(targetId, pending);
        map.put(source, byTarget);
    }

    private static Method privateMethod(Class<?> owner, String name, Class<?>... parameterTypes) throws Exception {
        Method method = owner.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }
}
