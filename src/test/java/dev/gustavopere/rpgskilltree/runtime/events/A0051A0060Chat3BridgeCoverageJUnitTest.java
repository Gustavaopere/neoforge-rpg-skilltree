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
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0051A0060Chat3BridgeCoverageJUnitTest {
    private static final String EVENTS =
        "dev.gustavopere.rpgskilltree.runtime.events.A0041A0060ProjectileEvents";

    @Test
    void pendingLaunchFactoriesPreserveProvenanceAndSpecialState() throws Exception {
        Class<?> pendingClass = Class.forName(EVENTS + "$PendingLaunch");
        Method bowFactory = declaredMethod(
            pendingClass, "bow", String.class, String.class, long.class, BowShot.class
        );
        Method crossbowFactory = declaredMethod(
            pendingClass, "crossbow", String.class, String.class, String.class, long.class,
            CombatResult.class, CombatResult.class
        );
        Method neutralFactory = declaredMethod(
            pendingClass, "neutral", String.class, WeaponFamily.class, String.class, long.class
        );
        Method hasSpecial = declaredMethod(pendingClass, "hasSpecial");

        BowShot activeBow = new BowShot(true, true, 1.0D, 0.15D, 1.20D, 12.0D);
        Object bow = bowFactory.invoke(null, "actor", "bow-root", 1_000L, activeBow);
        assertEquals("actor", field(pendingClass, "actorId").get(bow));
        assertEquals(WeaponFamily.BOW, field(pendingClass, "family").get(bow));
        assertEquals("bow-root", field(pendingClass, "rootActionId").get(bow));
        assertNull(field(pendingClass, "weaponId").get(bow));
        assertEquals(1_250L, field(pendingClass, "expiresAt").getLong(bow));
        assertTrue(field(pendingClass, "launchConfirmed").getBoolean(bow));
        assertSame(activeBow, field(pendingClass, "bowShot").get(bow));
        assertTrue((boolean) hasSpecial.invoke(bow));

        CombatResult adjusted = new CombatResult(true, false, 1.15D, 1.0D, 1.0D, 0.0D, 0.0D);
        Object crossbow = crossbowFactory.invoke(
            null, "actor", "crossbow-root", "crossbow-stack/42", 2_000L,
            adjusted, CombatResult.neutral()
        );
        assertEquals(WeaponFamily.CROSSBOW, field(pendingClass, "family").get(crossbow));
        assertEquals("crossbow-stack/42", field(pendingClass, "weaponId").get(crossbow));
        assertEquals(2_250L, field(pendingClass, "expiresAt").getLong(crossbow));
        assertTrue(field(pendingClass, "launchConfirmed").getBoolean(crossbow));
        assertSame(adjusted, field(pendingClass, "adjusted").get(crossbow));
        assertTrue((boolean) hasSpecial.invoke(crossbow));

        CombatResult piercing = new CombatResult(true, false, 1.0D, 1.0D, 1.0D, 0.18D, 0.0D);
        Object piercingOnly = crossbowFactory.invoke(
            null, "actor", "piercing-root", "crossbow-stack/43", 2_500L,
            CombatResult.neutral(), piercing
        );
        assertTrue((boolean) hasSpecial.invoke(piercingOnly));

        Object neutral = neutralFactory.invoke(
            null, "actor", WeaponFamily.CROSSBOW, "projectile/root", 9_999L
        );
        assertEquals(9_999L, field(pendingClass, "expiresAt").getLong(neutral));
        assertFalse(field(pendingClass, "launchConfirmed").getBoolean(neutral));
        assertNull(field(pendingClass, "weaponId").get(neutral));
        assertFalse((boolean) hasSpecial.invoke(neutral));
    }

    @Test
    void projectileMetadataConstructorStoresCorrelationFacts() throws Exception {
        Class<?> metaClass = Class.forName(EVENTS + "$ProjectileMeta");
        Constructor<?> constructor = metaClass.getDeclaredConstructor(
            WeaponFamily.class, String.class, String.class, boolean.class, String.class, Vec3.class,
            double.class, boolean.class, boolean.class, boolean.class, boolean.class,
            BowShot.class, CombatResult.class
        );
        constructor.setAccessible(true);
        Vec3 origin = new Vec3(1.0D, 2.0D, 3.0D);
        BowShot bow = BowShot.neutral();
        CombatResult shot = new CombatResult(true, false, 1.0D, 1.25D, 1.0D, 0.0D, 0.0D);
        Object meta = constructor.newInstance(
            WeaponFamily.CROSSBOW, "actor", "root", true, "crossbow-stack/7", origin,
            1.12D, true, false, true, false, bow, shot
        );

        assertEquals(WeaponFamily.CROSSBOW, field(metaClass, "family").get(meta));
        assertEquals("actor", field(metaClass, "actorId").get(meta));
        assertEquals("root", field(metaClass, "rootActionId").get(meta));
        assertTrue(field(metaClass, "launchConfirmed").getBoolean(meta));
        assertEquals("crossbow-stack/7", field(metaClass, "weaponId").get(meta));
        assertSame(origin, field(metaClass, "origin").get(meta));
        assertEquals(1.12D, field(metaClass, "baseDamageMultiplier").getDouble(meta));
        assertTrue(field(metaClass, "critical").getBoolean(meta));
        assertFalse(field(metaClass, "criticalMultiplierNeeded").getBoolean(meta));
        assertTrue(field(metaClass, "sprintingAtLaunch").getBoolean(meta));
        assertFalse(field(metaClass, "stationaryAtLaunch").getBoolean(meta));
        assertSame(bow, field(metaClass, "bowShot").get(meta));
        assertSame(shot, field(metaClass, "crossbowShot").get(meta));
    }

    @Test
    void familyClassifierAndGeneratedWeaponIdsUseActualItemSemantics() throws Exception {
        Class<?> eventsClass = Class.forName(EVENTS);
        Method family = declaredMethod(eventsClass, "family", ItemStack.class);
        Method weaponId = declaredMethod(eventsClass, "newCrossbowWeaponId");

        ItemStack bow = new ItemStack(new BowItem(new Item.Properties()));
        ItemStack crossbow = new ItemStack(new CrossbowItem(new Item.Properties()));
        ItemStack other = new ItemStack(new Item(new Item.Properties()));

        assertNull(family.invoke(null, new Object[] {null}));
        assertNull(family.invoke(null, ItemStack.EMPTY));
        assertEquals(WeaponFamily.BOW, family.invoke(null, bow));
        assertEquals(WeaponFamily.CROSSBOW, family.invoke(null, crossbow));
        assertNull(family.invoke(null, other));

        String first = (String) weaponId.invoke(null);
        String second = (String) weaponId.invoke(null);
        assertTrue(first.startsWith("crossbow-stack/"));
        assertTrue(second.startsWith("crossbow-stack/"));
        assertNotEquals(first, second);
    }

    @Test
    void crossbowTrackBindsOneStackToOneWeaponIdentity() throws Exception {
        Class<?> trackClass = Class.forName(EVENTS + "$CrossbowTrack");
        Constructor<?> constructor = trackClass.getDeclaredConstructor(ItemStack.class, String.class);
        constructor.setAccessible(true);
        ItemStack stack = new ItemStack(new CrossbowItem(new Item.Properties()));
        Object track = constructor.newInstance(stack, "crossbow-stack/stable");

        assertSame(stack, field(trackClass, "stack").get(track));
        assertEquals("crossbow-stack/stable", field(trackClass, "weaponId").get(track));
        assertFalse(field(trackClass, "charged").getBoolean(track));
        assertFalse(field(trackClass, "using").getBoolean(track));
        assertEquals(0.0D, field(trackClass, "progress").getDouble(track));
    }

    @Test
    void crossbowWeaponIdentityReusesSameHeldStackAndRotatesOnReplacement() throws Exception {
        Class<?> eventsClass = Class.forName(EVENTS);
        Method crossbowWeaponId = declaredMethod(
            eventsClass, "crossbowWeaponId", ServerPlayer.class, ItemStack.class
        );
        ServerPlayer player = mock(ServerPlayer.class);
        UUID playerId = UUID.randomUUID();
        ItemStack firstStack = new ItemStack(new CrossbowItem(new Item.Properties()));
        ItemStack replacement = new ItemStack(new CrossbowItem(new Item.Properties()));
        when(player.getUUID()).thenReturn(playerId);
        when(player.getMainHandItem()).thenReturn(firstStack, firstStack, replacement);
        when(player.getOffhandItem()).thenReturn(ItemStack.EMPTY);

        String first = (String) crossbowWeaponId.invoke(null, player, firstStack);
        String reused = (String) crossbowWeaponId.invoke(null, player, firstStack);
        String rotated = (String) crossbowWeaponId.invoke(null, player, replacement);
        assertEquals(first, reused);
        assertNotEquals(first, rotated);

        ServerPlayer fallbackPlayer = mock(ServerPlayer.class);
        when(fallbackPlayer.getUUID()).thenReturn(UUID.randomUUID());
        when(fallbackPlayer.getMainHandItem()).thenReturn(ItemStack.EMPTY);
        when(fallbackPlayer.getOffhandItem()).thenReturn(ItemStack.EMPTY);
        String fallback = (String) crossbowWeaponId.invoke(null, fallbackPlayer, replacement);
        assertTrue(fallback.startsWith("crossbow-stack/"));

        A0041A0060ProjectileEvents.onServerStopped(null);
        String afterLifecycleClear = (String) crossbowWeaponId.invoke(null, player, replacement);
        assertNotEquals(rotated, afterLifecycleClear);
    }

    @Test
    void correlatedCrossbowJoinRegistersProjectileAndCommitsReservedPiercingShot() throws Exception {
        UUID playerId = UUID.randomUUID();
        UUID arrowId = UUID.randomUUID();
        String actor = playerId.toString();
        String root = "crossbow/root";
        long now = 5_000L;
        ServerPlayer player = serverPlayer(playerId, 100L);
        AbstractArrow arrow = mock(AbstractArrow.class);
        ItemStack crossbow = new ItemStack(new CrossbowItem(new Item.Properties()));
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
        assertTrue(state.reservePiercingBolt(actor, root, now));
        CombatResult piercing = new CombatResult(true, false, 1.0D, 1.25D, 1.0D, 0.18D, 0.0D);
        Object pending = pendingCrossbow(actor, root, "crossbow-stack/join", now, piercing);
        pendingMap().put(playerId, pending);
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0051", 2, "A0053", 2));
        A0001A0020CriticalService critical = new A0001A0020CriticalService(() -> 0.99D, 30_000L, 32);

        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class);
             MockedStatic<A0001A0020RuntimeState> critRuntime = mockStatic(A0001A0020RuntimeState.class);
             MockedStatic<A0061A0080RuntimeState> generalRuntime = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0041A0060RuntimeState.ranks(player)).thenReturn(ranks);
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            critRuntime.when(A0001A0020RuntimeState::critical).thenReturn(critical);
            generalRuntime.when(() -> A0061A0080RuntimeState.isStationary(player)).thenReturn(false);

            A0041A0060ProjectileEvents.onEntityJoin(event);
        }

        assertEquals(0, state.cadence(actor), "A0053 cost commits only once a correlated projectile exists");
        Object meta = projectileMetadata(arrow);
        assertNotNull(meta);
        Class<?> metaClass = meta.getClass();
        assertTrue(field(metaClass, "launchConfirmed").getBoolean(meta));
        assertEquals("crossbow-stack/join", field(metaClass, "weaponId").get(meta));
        CombatResult applied = (CombatResult) field(metaClass, "crossbowShot").get(meta);
        assertTrue(applied.applied());
        assertEquals(0.18D, applied.penetrationFraction());
        assertFalse(state.recordCrossbowProjectileFailure(actor, root, arrowId.toString(), now + 1L));
    }

    @Test
    void uncorrelatedCrossbowJoinStaysProvenanceNeutral() throws Exception {
        UUID playerId = UUID.randomUUID();
        UUID arrowId = UUID.randomUUID();
        ServerPlayer player = serverPlayer(playerId, 200L);
        AbstractArrow arrow = mock(AbstractArrow.class);
        ItemStack crossbow = new ItemStack(new CrossbowItem(new Item.Properties()));
        when(arrow.getOwner()).thenReturn(player);
        when(arrow.getWeaponItem()).thenReturn(crossbow);
        when(arrow.getUUID()).thenReturn(arrowId);
        when(arrow.isCritArrow()).thenReturn(false);
        when(arrow.position()).thenReturn(Vec3.ZERO);
        EntityJoinLevelEvent event = mock(EntityJoinLevelEvent.class);
        when(event.getEntity()).thenReturn(arrow);
        pendingMap().remove(playerId);

        A0041A0060CombatState state = new A0041A0060CombatState();
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0051", 2));
        A0001A0020CriticalService critical = new A0001A0020CriticalService(() -> 0.0D, 30_000L, 32);

        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class);
             MockedStatic<A0001A0020RuntimeState> critRuntime = mockStatic(A0001A0020RuntimeState.class);
             MockedStatic<A0061A0080RuntimeState> generalRuntime = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0041A0060RuntimeState.ranks(player)).thenReturn(ranks);
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            critRuntime.when(A0001A0020RuntimeState::critical).thenReturn(critical);
            generalRuntime.when(() -> A0061A0080RuntimeState.isStationary(player)).thenReturn(true);

            A0041A0060ProjectileEvents.onEntityJoin(event);
        }

        Object meta = projectileMetadata(arrow);
        assertNotNull(meta);
        Class<?> metaClass = meta.getClass();
        assertFalse(field(metaClass, "launchConfirmed").getBoolean(meta));
        assertNull(field(metaClass, "weaponId").get(meta));
        assertFalse(field(metaClass, "critical").getBoolean(meta),
            "derived crossbow arrows must not inherit the A0051 launch bonus");
        CombatResult neutral = (CombatResult) field(metaClass, "crossbowShot").get(meta);
        assertFalse(neutral.applied());
        assertFalse(state.recordCrossbowProjectileFailure(
            playerId.toString(), "projectile/" + arrowId, arrowId.toString(), 10_001L
        ));
    }

    @Test
    void multishotFailureCannotBeRewrittenAsSuccessAndExpiredRootsArePruned() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        assertTrue(state.registerCrossbowProjectile("p", "r", "a", 1_000L));
        assertTrue(state.registerCrossbowProjectile("p", "r", "b", 1_001L));
        assertFalse(state.recordCrossbowProjectileFailure("p", "r", "a", 1_100L));
        assertFalse(state.recordCrossbowProjectileSuccess("p", "r", "a", 1_101L));
        assertFalse(state.recordCrossbowProjectileFailure("p", "r", "b", 1_102L));
        assertTrue(state.sealCrossbowRoot("p", "r", 1_103L));
        assertFalse(state.recordCrossbowProjectileSuccess("p", "r", "b", 1_104L));
        assertFalse(state.registerCrossbowProjectile("p", "r", "c", 1_105L));

        assertTrue(state.registerCrossbowProjectile("p", "expiring", "x", 2_000L));
        state.pruneTransient(32_000L);
        assertFalse(state.recordCrossbowProjectileFailure("p", "expiring", "x", 32_001L));
        assertFalse(state.sealCrossbowRoot("p", "expiring", 32_001L));
    }

    @Test
    void adjustedReservationFailsClosedWhenCadenceChangesAndRearmInvalidatesOldRoot() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");
        state.armAdjustedMechanism("p", 2_000L, 10_000L);
        assertTrue(state.reserveAdjustedMechanism("p", "root-a", 10_100L));
        state.consumeCadence("p", 1);
        assertFalse(state.commitAdjustedMechanism("p", "root-a", 10_150L));
        assertEquals(2, state.cadence("p"));

        state.addCadence("p");
        state.armAdjustedMechanism("p", 2_000L, 10_200L);
        assertFalse(state.commitAdjustedMechanism("p", "root-a", 10_250L));
        assertTrue(state.reserveAdjustedMechanism("p", "root-b", 10_260L));
        state.armAdjustedMechanism("p", 2_000L, 10_270L);
        assertFalse(state.commitAdjustedMechanism("p", "root-b", 10_280L));
        assertTrue(state.reserveAdjustedMechanism("p", "root-c", 10_290L));
        assertTrue(state.commitAdjustedMechanism("p", "root-c", 10_300L));
        assertEquals(0, state.cadence("p"));
    }

    @Test
    void pruneAndRankReconciliationPreserveOnlyStillOwnedTransientState() {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");
        state.armAdjustedMechanism("p", 100L, 20_000L);
        assertTrue(state.reserveAdjustedMechanism("p", "short", 20_050L));
        state.pruneTransient(20_500L);
        assertFalse(state.commitAdjustedMechanism("p", "short", 20_501L));

        state.addSequence("p", 2, 21_000L);
        state.startFinalCombinationCooldown("p", 8_000L, 21_000L);
        CombatPerkRanks owned = CombatPerkRanks.of(Map.of(
            "A0050", 2, "A0051", 2, "A0052", 1,
            "A0057", 2, "A0058", 1, "A0060", 1
        ));
        state.reconcileForRanks("p", owned, 21_100L);
        assertEquals(3, state.cadence("p"));
        assertEquals(1, state.sequence("p", 21_100L));
        assertFalse(state.finalCombinationReady("p", 21_100L));

        CombatPerkRanks noCapstone = CombatPerkRanks.of(Map.of(
            "A0050", 2, "A0051", 2, "A0052", 1,
            "A0057", 2, "A0058", 1
        ));
        state.reconcileForRanks("p", noCapstone, 21_200L);
        assertEquals(3, state.cadence("p"));
        assertEquals(1, state.sequence("p", 21_200L));
        assertTrue(state.finalCombinationReady("p", 21_200L));
    }

    private static ServerPlayer serverPlayer(UUID id, long gameTime) {
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
        Class<?> pendingClass = Class.forName(EVENTS + "$PendingLaunch");
        Method factory = declaredMethod(
            pendingClass, "crossbow", String.class, String.class, String.class, long.class,
            CombatResult.class, CombatResult.class
        );
        return factory.invoke(null, actor, root, weaponId, now, CombatResult.neutral(), piercing);
    }

    @SuppressWarnings("unchecked")
    private static Map<UUID, Object> pendingMap() throws Exception {
        Class<?> eventsClass = Class.forName(EVENTS);
        Field pending = field(eventsClass, "PENDING");
        return (Map<UUID, Object>) pending.get(null);
    }

    private static Object projectileMetadata(AbstractArrow arrow) throws Exception {
        Class<?> eventsClass = Class.forName(EVENTS);
        Method metadata = declaredMethod(eventsClass, "metadata", AbstractArrow.class);
        return metadata.invoke(null, arrow);
    }

    private static Method declaredMethod(Class<?> owner, String name, Class<?>... parameterTypes)
        throws ReflectiveOperationException {
        Method method = owner.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    private static Field field(Class<?> owner, String name) throws ReflectiveOperationException {
        Field field = owner.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }
}
