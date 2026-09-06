package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy.BowShot;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy.CombatResult;
import dev.gustavopere.rpgskilltree.core.A0041A0060CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0041A0060RuntimeState;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0051A0060Chat3EventFlowCoverageJUnitTest {
    private static final String EVENTS =
        "dev.gustavopere.rpgskilltree.runtime.events.A0041A0060ProjectileEvents";

    @BeforeEach
    @AfterEach
    void clearStaticLifecycle() {
        A0041A0060ProjectileEvents.onServerStopped(null);
    }

    @Test
    void crossbowArrowLooseReservesPiercingWithoutPrematureCadenceCommit() throws Exception {
        UUID playerId = UUID.randomUUID();
        String actor = playerId.toString();
        ServerPlayer player = serverPlayer(playerId, 100L);
        ItemStack crossbow = new ItemStack(new CrossbowItem(new Item.Properties()));
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

        Object pending = pendingMap().get(playerId);
        assertNotNull(pending);
        Class<?> pendingClass = pending.getClass();
        assertEquals(WeaponFamily.CROSSBOW, field(pendingClass, "family").get(pending));
        assertTrue(field(pendingClass, "launchConfirmed").getBoolean(pending));
        String weaponId = (String) field(pendingClass, "weaponId").get(pending);
        assertTrue(weaponId.startsWith("crossbow-stack/"));
        CombatResult piercing = (CombatResult) field(pendingClass, "piercing").get(pending);
        assertTrue(piercing.applied());
        assertEquals(2, state.cadence(actor), "release may reserve but must not consume Cadence");

        String root = (String) field(pendingClass, "rootActionId").get(pending);
        state.discardPiercingBolt(actor, root);
    }

    @Test
    void expiredPendingSealsAllFailRootAndLosesCadenceExactlyOnce() throws Exception {
        UUID playerId = UUID.randomUUID();
        String actor = playerId.toString();
        String root = "root/expired";
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence(actor);
        state.addCadence(actor);
        assertTrue(state.registerCrossbowProjectile(actor, root, "arrow", 1_000L));
        assertFalse(state.recordCrossbowProjectileFailure(actor, root, "arrow", 1_100L));
        pendingMap().put(playerId, pendingCrossbow(actor, root, "crossbow-stack/tick", 1_000L));

        MinecraftServer server = mock(MinecraftServer.class);
        ServerLevel overworld = mock(ServerLevel.class);
        PlayerList players = mock(PlayerList.class);
        when(server.overworld()).thenReturn(overworld);
        when(overworld.getGameTime()).thenReturn(30L);
        when(server.getPlayerList()).thenReturn(players);
        when(players.getPlayers()).thenReturn(List.of());
        ServerTickEvent.Post event = mock(ServerTickEvent.Post.class);
        when(event.getServer()).thenReturn(server);

        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class)) {
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            A0041A0060ProjectileEvents.onServerTick(event);
        }

        assertEquals(1, state.cadence(actor));
        assertFalse(pendingMap().containsKey(playerId));
        assertFalse(state.sealCrossbowRoot(actor, root, 1_501L), "settled root cannot lose Cadence twice");
    }

    @Test
    void projectileImpactSettlesOneSealedCrossbowMissAndDeduplicatesCallback() throws Exception {
        UUID playerId = UUID.randomUUID();
        UUID arrowId = UUID.randomUUID();
        String actor = playerId.toString();
        String root = "root/impact";
        ServerPlayer player = serverPlayer(playerId, 200L);
        AbstractArrow arrow = mock(AbstractArrow.class);
        when(arrow.getOwner()).thenReturn(player);
        when(arrow.getUUID()).thenReturn(arrowId);
        projectileMap().put(arrow, projectileMeta(actor, root, true, "crossbow-stack/impact"));

        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence(actor);
        state.addCadence(actor);
        assertTrue(state.registerCrossbowProjectile(actor, root, arrowId.toString(), 9_900L));
        assertFalse(state.sealCrossbowRoot(actor, root, 9_950L));

        ProjectileImpactEvent event = mock(ProjectileImpactEvent.class);
        when(event.getProjectile()).thenReturn(arrow);
        when(event.getRayTraceResult()).thenReturn(mock(HitResult.class));

        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class)) {
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            A0041A0060ProjectileEvents.onProjectileImpact(event);
            assertEquals(1, state.cadence(actor));
            A0041A0060ProjectileEvents.onProjectileImpact(event);
        }

        assertEquals(1, state.cadence(actor), "duplicate impact callback cannot settle twice");
        Object meta = projectileMap().get(arrow);
        assertTrue(field(meta.getClass(), "failureRecorded").getBoolean(meta));
    }

    @Test
    void damagePostRecordsCrossbowSuccessAndWeaponBoundHitReceipt() throws Exception {
        UUID playerId = UUID.randomUUID();
        UUID arrowId = UUID.randomUUID();
        String actor = playerId.toString();
        String root = "root/hit";
        String weaponId = "crossbow-stack/hit";
        ServerPlayer player = serverPlayer(playerId, 300L);
        ServerPlayer target = mock(ServerPlayer.class);
        when(target.isAlive()).thenReturn(true);
        when(target.isInvulnerable()).thenReturn(false);
        when(player.isAlliedTo(target)).thenReturn(false);

        AbstractArrow arrow = mock(AbstractArrow.class);
        when(arrow.getOwner()).thenReturn(player);
        when(arrow.getUUID()).thenReturn(arrowId);
        projectileMap().put(arrow, projectileMeta(actor, root, true, weaponId));

        DamageSource source = mock(DamageSource.class);
        when(source.getDirectEntity()).thenReturn(arrow);
        LivingDamageEvent.Post event = mock(LivingDamageEvent.Post.class);
        when(event.getSource()).thenReturn(source);
        when(event.getEntity()).thenReturn(target);
        when(event.getNewDamage()).thenReturn(4.0F);

        A0041A0060CombatState state = new A0041A0060CombatState();
        assertTrue(state.registerCrossbowProjectile(actor, root, arrowId.toString(), 14_900L));
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0052", 1));

        try (MockedStatic<A0041A0060RuntimeState> runtime = mockStatic(A0041A0060RuntimeState.class)) {
            runtime.when(() -> A0041A0060RuntimeState.ranks(player)).thenReturn(ranks);
            runtime.when(A0041A0060RuntimeState::state).thenReturn(state);
            A0041A0060ProjectileEvents.onDamagePost(event);
        }

        Object meta = projectileMap().get(arrow);
        assertTrue(field(meta.getClass(), "confirmedHit").getBoolean(meta));
        assertTrue(state.consumeCrossbowHitReceipt(actor, weaponId, 10_000L, 15_001L));
        assertFalse(state.consumeCrossbowHitReceipt(actor, weaponId, 10_000L, 15_002L));
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

    private static Object pendingCrossbow(String actor, String root, String weaponId, long now) throws Exception {
        Class<?> pendingClass = Class.forName(EVENTS + "$PendingLaunch");
        Method factory = declaredMethod(
            pendingClass, "crossbow", String.class, String.class, String.class, long.class,
            CombatResult.class, CombatResult.class
        );
        return factory.invoke(
            null, actor, root, weaponId, now, CombatResult.neutral(), CombatResult.neutral()
        );
    }

    private static Object projectileMeta(String actor, String root, boolean confirmed, String weaponId)
        throws Exception {
        Class<?> metaClass = Class.forName(EVENTS + "$ProjectileMeta");
        Constructor<?> constructor = metaClass.getDeclaredConstructor(
            WeaponFamily.class, String.class, String.class, boolean.class, String.class, Vec3.class,
            double.class, boolean.class, boolean.class, boolean.class, boolean.class,
            BowShot.class, CombatResult.class
        );
        constructor.setAccessible(true);
        return constructor.newInstance(
            WeaponFamily.CROSSBOW, actor, root, confirmed, weaponId, Vec3.ZERO,
            1.0D, false, false, false, true, BowShot.neutral(), CombatResult.neutral()
        );
    }

    @SuppressWarnings("unchecked")
    private static Map<UUID, Object> pendingMap() throws Exception {
        Field field = field(Class.forName(EVENTS), "PENDING");
        return (Map<UUID, Object>) field.get(null);
    }

    @SuppressWarnings("unchecked")
    private static WeakHashMap<AbstractArrow, Object> projectileMap() throws Exception {
        Field field = field(Class.forName(EVENTS), "PROJECTILES");
        return (WeakHashMap<AbstractArrow, Object>) field.get(null);
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
