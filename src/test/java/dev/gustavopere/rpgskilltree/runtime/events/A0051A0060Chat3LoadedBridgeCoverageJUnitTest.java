package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0001A0020CriticalService;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy.BowShot;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy.CombatResult;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0001A0020RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0041A0060RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0051A0060Chat3LoadedBridgeCoverageJUnitTest {
    private static final String EVENTS =
        "dev.gustavopere.rpgskilltree.runtime.events.A0041A0060ProjectileEvents";

    @AfterEach
    void clearLifecycle() {
        A0041A0060ProjectileEvents.onServerStopped(null);
    }

    @Test
    void pendingFactoriesAndMetadataPreserveLaunchProvenance() throws Exception {
        Class<?> pendingClass = Class.forName(EVENTS + "$PendingLaunch");
        Method bowFactory = method(pendingClass, "bow", String.class, String.class, long.class, BowShot.class);
        Method crossbowFactory = method(
            pendingClass, "crossbow", String.class, String.class, String.class, long.class,
            CombatResult.class, CombatResult.class
        );
        Method neutralFactory = method(
            pendingClass, "neutral", String.class, WeaponFamily.class, String.class, long.class
        );
        Method hasSpecial = method(pendingClass, "hasSpecial");

        BowShot bowShot = new BowShot(true, true, 1.0D, 0.15D, 1.20D, 12.0D);
        Object bow = bowFactory.invoke(null, "actor", "bow/root", 1_000L, bowShot);
        assertTrue(field(pendingClass, "launchConfirmed").getBoolean(bow));
        assertNull(field(pendingClass, "weaponId").get(bow));
        assertSame(bowShot, field(pendingClass, "bowShot").get(bow));
        assertTrue((boolean) hasSpecial.invoke(bow));

        CombatResult piercing = new CombatResult(true, false, 1.0D, 1.25D, 1.0D, 0.18D, 0.0D);
        Object crossbow = crossbowFactory.invoke(
            null, "actor", "crossbow/root", "crossbow-stack/1", 2_000L,
            CombatResult.neutral(), piercing
        );
        assertEquals(WeaponFamily.CROSSBOW, field(pendingClass, "family").get(crossbow));
        assertEquals("crossbow-stack/1", field(pendingClass, "weaponId").get(crossbow));
        assertEquals(2_250L, field(pendingClass, "expiresAt").getLong(crossbow));
        assertTrue((boolean) hasSpecial.invoke(crossbow));

        Object neutral = neutralFactory.invoke(null, "actor", WeaponFamily.CROSSBOW, "derived", 3_000L);
        assertFalse(field(pendingClass, "launchConfirmed").getBoolean(neutral));
        assertFalse((boolean) hasSpecial.invoke(neutral));

        Class<?> metaClass = Class.forName(EVENTS + "$ProjectileMeta");
        Constructor<?> ctor = metaClass.getDeclaredConstructor(
            WeaponFamily.class, String.class, String.class, boolean.class, String.class, Vec3.class,
            double.class, boolean.class, boolean.class, boolean.class, boolean.class,
            BowShot.class, CombatResult.class
        );
        ctor.setAccessible(true);
        Vec3 origin = new Vec3(1.0D, 2.0D, 3.0D);
        Object meta = ctor.newInstance(
            WeaponFamily.CROSSBOW, "actor", "crossbow/root", true, "crossbow-stack/1", origin,
            1.1D, true, false, true, false, BowShot.neutral(), piercing
        );
        assertTrue(field(metaClass, "launchConfirmed").getBoolean(meta));
        assertEquals("crossbow-stack/1", field(metaClass, "weaponId").get(meta));
        assertSame(origin, field(metaClass, "origin").get(meta));
        assertSame(piercing, field(metaClass, "crossbowShot").get(meta));
    }

    @Test
    void familyAndWeaponIdentityUseRegisteredVanillaStacks() throws Exception {
        Class<?> eventsClass = Class.forName(EVENTS);
        Method family = method(eventsClass, "family", ItemStack.class);
        Method newWeaponId = method(eventsClass, "newCrossbowWeaponId");
        Method weaponId = method(eventsClass, "crossbowWeaponId", ServerPlayer.class, ItemStack.class);

        ItemStack bow = new ItemStack(Items.BOW);
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        ItemStack other = new ItemStack(Items.STICK);
        assertNull(family.invoke(null, new Object[] {null}));
        assertNull(family.invoke(null, ItemStack.EMPTY));
        assertEquals(WeaponFamily.BOW, family.invoke(null, bow));
        assertEquals(WeaponFamily.CROSSBOW, family.invoke(null, crossbow));
        assertNull(family.invoke(null, other));

        String generated1 = (String) newWeaponId.invoke(null);
        String generated2 = (String) newWeaponId.invoke(null);
        assertNotEquals(generated1, generated2);

        ServerPlayer player = mock(ServerPlayer.class);
        UUID id = UUID.randomUUID();
        ItemStack replacement = new ItemStack(Items.CROSSBOW);
        when(player.getUUID()).thenReturn(id);
        when(player.getMainHandItem()).thenReturn(crossbow, crossbow, replacement);
        when(player.getOffhandItem()).thenReturn(ItemStack.EMPTY);
        String first = (String) weaponId.invoke(null, player, crossbow);
        String reused = (String) weaponId.invoke(null, player, crossbow);
        String rotated = (String) weaponId.invoke(null, player, replacement);
        assertEquals(first, reused);
        assertNotEquals(first, rotated);
    }

    @Test
    void crossbowTrackReadsRegisteredCrossbowState() throws Exception {
        Class<?> trackClass = Class.forName(EVENTS + "$CrossbowTrack");
        Constructor<?> ctor = trackClass.getDeclaredConstructor(ItemStack.class, String.class);
        ctor.setAccessible(true);
        ItemStack stack = new ItemStack(Items.CROSSBOW);
        Object track = ctor.newInstance(stack, "crossbow-stack/stable");
        assertSame(stack, field(trackClass, "stack").get(track));
        assertEquals("crossbow-stack/stable", field(trackClass, "weaponId").get(track));
        assertFalse(field(trackClass, "charged").getBoolean(track));
    }

    @Test
    void arrowLooseReservesPiercingWithoutPrematureCadenceCommit() throws Exception {
        UUID id = UUID.randomUUID();
        String actor = id.toString();
        ServerPlayer player = player(id, 100L);
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        when(player.getMainHandItem()).thenReturn(crossbow);
        when(player.getOffhandItem()).thenReturn(ItemStack.EMPTY);
        ArrowLooseEvent event = mock(ArrowLooseEvent.class);
        when(event.getEntity()).thenReturn(player);
        when(event.getBow()).thenReturn(crossbow);
        when(event.isCanceled()).thenReturn(false);

        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence(actor);
        state.addCadence(actor);
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0053", 2));
        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class)) {
            runtime.when(() -> A0041A0060RuntimeState.ranks(player)).thenReturn(ranks);
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            A0041A0060ProjectileEvents.onArrowLoose(event);
        }

        Object pending = pending().get(id);
        assertNotNull(pending);
        Class<?> type = pending.getClass();
        assertEquals(WeaponFamily.CROSSBOW, field(type, "family").get(pending));
        assertTrue(field(type, "launchConfirmed").getBoolean(pending));
        assertTrue(((CombatResult) field(type, "piercing").get(pending)).applied());
        assertEquals(2, state.cadence(actor));
    }

    @Test
    void correlatedJoinCommitsReservedPiercingAndDerivedJoinStaysNeutral() throws Exception {
        UUID id = UUID.randomUUID();
        UUID arrowId = UUID.randomUUID();
        String actor = id.toString();
        String root = "crossbow/root";
        ServerPlayer player = player(id, 100L);
        AbstractArrow arrow = mock(AbstractArrow.class);
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        when(arrow.getOwner()).thenReturn(player);
        when(arrow.getWeaponItem()).thenReturn(crossbow);
        when(arrow.getUUID()).thenReturn(arrowId);
        when(arrow.isCritArrow()).thenReturn(false);
        when(arrow.position()).thenReturn(Vec3.ZERO);
        EntityJoinLevelEvent event = mock(EntityJoinLevelEvent.class);
        when(event.getEntity()).thenReturn(arrow);

        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence(actor);
        state.addCadence(actor);
        assertTrue(state.reservePiercingBolt(actor, root, 5_000L));
        CombatResult piercing = new CombatResult(true, false, 1.0D, 1.25D, 1.0D, 0.18D, 0.0D);
        pending().put(id, pendingCrossbow(actor, root, "crossbow-stack/join", 5_000L, piercing));
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0051", 2, "A0053", 2));
        A0001A0020CriticalService critical = new A0001A0020CriticalService(() -> 0.99D, 30_000L, 32);

        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class);
             MockedStatic<A0001A0020RuntimeState> crit = mockStatic(A0001A0020RuntimeState.class);
             MockedStatic<A0061A0080RuntimeState> general = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0041A0060RuntimeState.ranks(player)).thenReturn(ranks);
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            crit.when(A0001A0020RuntimeState::critical).thenReturn(critical);
            general.when(() -> A0061A0080RuntimeState.isStationary(player)).thenReturn(false);
            A0041A0060ProjectileEvents.onEntityJoin(event);
        }
        assertEquals(0, state.cadence(actor));
        Object correlated = metadata(arrow);
        assertNotNull(correlated);
        assertTrue(field(correlated.getClass(), "launchConfirmed").getBoolean(correlated));
        assertEquals("crossbow-stack/join", field(correlated.getClass(), "weaponId").get(correlated));
        assertTrue(((CombatResult) field(correlated.getClass(), "crossbowShot").get(correlated)).applied());

        UUID derivedId = UUID.randomUUID();
        AbstractArrow derived = mock(AbstractArrow.class);
        when(derived.getOwner()).thenReturn(player);
        when(derived.getWeaponItem()).thenReturn(crossbow);
        when(derived.getUUID()).thenReturn(derivedId);
        when(derived.isCritArrow()).thenReturn(false);
        when(derived.position()).thenReturn(Vec3.ZERO);
        EntityJoinLevelEvent derivedEvent = mock(EntityJoinLevelEvent.class);
        when(derivedEvent.getEntity()).thenReturn(derived);
        pending().remove(id);
        A0001A0020CriticalService derivedCritical = new A0001A0020CriticalService(() -> 0.0D, 30_000L, 32);
        CombatPerkRanks derivedRanks = CombatPerkRanks.of(Map.of("A0051", 2));
        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class);
             MockedStatic<A0001A0020RuntimeState> crit = mockStatic(A0001A0020RuntimeState.class);
             MockedStatic<A0061A0080RuntimeState> general = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0041A0060RuntimeState.ranks(player)).thenReturn(derivedRanks);
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            crit.when(A0001A0020RuntimeState::critical).thenReturn(derivedCritical);
            general.when(() -> A0061A0080RuntimeState.isStationary(player)).thenReturn(true);
            A0041A0060ProjectileEvents.onEntityJoin(derivedEvent);
        }
        Object neutral = metadata(derived);
        assertNotNull(neutral);
        assertFalse(field(neutral.getClass(), "launchConfirmed").getBoolean(neutral));
        assertNull(field(neutral.getClass(), "weaponId").get(neutral));
        assertFalse(field(neutral.getClass(), "critical").getBoolean(neutral));
    }

    private static ServerPlayer player(UUID id, long gameTime) {
        ServerPlayer player = mock(ServerPlayer.class);
        ServerLevel level = mock(ServerLevel.class);
        when(player.getUUID()).thenReturn(id);
        when(player.level()).thenReturn(level);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(gameTime);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);
        when(player.isSprinting()).thenReturn(false);
        return player;
    }

    private static Object pendingCrossbow(
        String actor, String root, String weaponId, long now, CombatResult piercing
    ) throws Exception {
        Class<?> type = Class.forName(EVENTS + "$PendingLaunch");
        Method factory = method(
            type, "crossbow", String.class, String.class, String.class, long.class,
            CombatResult.class, CombatResult.class
        );
        return factory.invoke(null, actor, root, weaponId, now, CombatResult.neutral(), piercing);
    }

    @SuppressWarnings("unchecked")
    private static Map<UUID, Object> pending() throws Exception {
        return (Map<UUID, Object>) field(Class.forName(EVENTS), "PENDING").get(null);
    }

    private static Object metadata(AbstractArrow arrow) throws Exception {
        return method(Class.forName(EVENTS), "metadata", AbstractArrow.class).invoke(null, arrow);
    }

    private static Method method(Class<?> owner, String name, Class<?>... params) throws Exception {
        Method method = owner.getDeclaredMethod(name, params);
        method.setAccessible(true);
        return method;
    }

    private static Field field(Class<?> owner, String name) throws Exception {
        Field field = owner.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }
}
