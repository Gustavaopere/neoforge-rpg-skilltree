package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState.FirstBloodReservation;
import dev.gustavopere.rpgskilltree.core.A0061A0080ReservationReceiptContext;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0073A0080ProjectileEventCoverageJUnitTest {
    private static final String ACTOR = "actor";
    private static final UUID TARGET_UUID = UUID.fromString("00000000-0000-0000-0000-000000000073");
    private static final String TARGET = TARGET_UUID.toString();

    @AfterEach
    void clearThreadReceipt() {
        A0061A0080ReservationReceiptContext.clear();
    }

    @SuppressWarnings("deprecation")
    @Test
    void positiveProjectilePostCommitsExecutionFirstBloodAndOpportunityForExactRoot() {
        Fixture fixture = fixture();
        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.armExecution(ACTOR, TARGET, "execution-opener", 1_000L));
        assertTrue(state.reserveExecution(ACTOR, TARGET, "root", 1_100L));
        assertEquals(FirstBloodReservation.OPENER, state.reserveFirstBlood(ACTOR, TARGET, "root", 0.90D, 1_100L));
        assertTrue(state.armOpportunity(ACTOR, 1_000L));
        assertTrue(state.reserveOpportunity(ACTOR, "root", 1_100L));

        publishReceipt("root", true, false, FirstBloodReservation.OPENER, true, true);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            wireRuntime(runtime, fixture.player, state, CombatPerkRanks.of(Map.of("A0073", 1, "A0074", 1, "A0080", 1)));

            A0073A0080ProjectileCommitEvents.onIncomingFinal(fixture.incoming);
            assertNull(A0061A0080ReservationReceiptContext.take(ACTOR, TARGET));
            A0073A0080ProjectileCommitEvents.onDamagePost(fixture.post);

            assertTrue(state.executionCoolingDown(ACTOR, TARGET, 1_501L));
            assertTrue(state.firstBloodWindowActive(ACTOR, TARGET, 1_501L));
            assertFalse(state.reserveOpportunity(ACTOR, "another", 1_501L));
        }
    }

    @Test
    void canceledOrZeroIncomingRollsBackEveryReservedTransitionInsteadOfPublishingPendingHit() {
        Fixture fixture = fixture();
        when(fixture.incoming.isCanceled()).thenReturn(true);
        A0061A0080CombatState state = new A0061A0080CombatState();
        prepareAllReservations(state, "root", 1_100L);
        publishReceipt("root", true, false, FirstBloodReservation.OPENER, true, true);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0061A0080RuntimeState.actorId(fixture.player)).thenReturn(ACTOR);
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            A0073A0080ProjectileCommitEvents.onIncomingFinal(fixture.incoming);

            assertTrue(state.reserveExecution(ACTOR, TARGET, "retry", 1_101L));
            assertEquals(FirstBloodReservation.OPENER, state.reserveFirstBlood(ACTOR, TARGET, "retry", 0.90D, 1_101L));
            assertTrue(state.reserveOpportunity(ACTOR, "retry", 1_101L));

            A0073A0080ProjectileCommitEvents.onDamagePost(fixture.post);
        }
    }

    @Test
    void rankLossBetweenPreAndPostRollsBackReservationsFailClosed() {
        Fixture fixture = fixture();
        A0061A0080CombatState state = new A0061A0080CombatState();
        prepareAllReservations(state, "root", 1_100L);
        publishReceipt("root", true, false, FirstBloodReservation.OPENER, true, true);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            wireRuntime(runtime, fixture.player, state, CombatPerkRanks.of(Map.of()));
            A0073A0080ProjectileCommitEvents.onIncomingFinal(fixture.incoming);
            A0073A0080ProjectileCommitEvents.onDamagePost(fixture.post);

            assertTrue(state.reserveExecution(ACTOR, TARGET, "retry", 1_501L));
            assertEquals(FirstBloodReservation.OPENER, state.reserveFirstBlood(ACTOR, TARGET, "retry", 0.90D, 1_501L));
            assertTrue(state.reserveOpportunity(ACTOR, "retry", 1_501L));
        }
    }

    @Test
    void armCandidateAndUnreservedFirstBloodHistoryUseTheirDedicatedPostBranches() {
        Fixture fixture = fixture();
        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.reserveExecutionArmCandidate(ACTOR, TARGET, "root", 1_100L));
        state.markFirstBloodHitPending(ACTOR, TARGET, "root", 1_100L);
        publishReceipt("root", false, true, FirstBloodReservation.NONE, true, false);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            wireRuntime(runtime, fixture.player, state, CombatPerkRanks.of(Map.of("A0073", 1, "A0074", 1)));
            A0073A0080ProjectileCommitEvents.onIncomingFinal(fixture.incoming);
            A0073A0080ProjectileCommitEvents.onDamagePost(fixture.post);
            assertTrue(state.executionWindowActive(ACTOR, TARGET, 1_501L));

            assertEquals(FirstBloodReservation.NONE,
                state.reserveFirstBlood(ACTOR, TARGET, "too-soon", 0.90D, 1_502L));
        }
    }

    @Test
    void invalidProjectileContextClearsThreadReceiptAndIneligiblePostRollsBack() {
        Fixture fixture = fixture();
        A0061A0080ReservationReceiptContext.begin(ACTOR, TARGET, "orphan");
        A0061A0080ReservationReceiptContext.markOpportunityReserved();
        LivingIncomingDamageEvent unrelated = mock(LivingIncomingDamageEvent.class);
        DamageSource unrelatedSource = mock(DamageSource.class);
        when(unrelated.getSource()).thenReturn(unrelatedSource);
        A0073A0080ProjectileCommitEvents.onIncomingFinal(unrelated);
        assertNull(A0061A0080ReservationReceiptContext.take(ACTOR, TARGET));

        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.armOpportunity(ACTOR, 1_000L));
        assertTrue(state.reserveOpportunity(ACTOR, "root", 1_100L));
        publishReceipt("root", false, false, FirstBloodReservation.NONE, false, true);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(() -> A0061A0080RuntimeState.actorId(fixture.player)).thenReturn(ACTOR);
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
            A0073A0080ProjectileCommitEvents.onIncomingFinal(fixture.incoming);
            when(fixture.player.isCreative()).thenReturn(true);
            A0073A0080ProjectileCommitEvents.onDamagePost(fixture.post);
            assertTrue(state.reserveOpportunity(ACTOR, "retry", 1_501L));
        }
    }

    @Test
    void unrelatedSecondArrowPostCannotConsumeFirstArrowOpportunityReservation() {
        Fixture fixture = fixture();
        AbstractArrow secondArrow = mock(AbstractArrow.class);
        when(secondArrow.getOwner()).thenReturn(fixture.player);
        DamageSource secondSource = mock(DamageSource.class);
        when(secondSource.getDirectEntity()).thenReturn(secondArrow);
        LivingDamageEvent.Post secondPost = mock(LivingDamageEvent.Post.class);
        when(secondPost.getSource()).thenReturn(secondSource);
        when(secondPost.getEntity()).thenReturn(fixture.target);
        when(secondPost.getNewDamage()).thenReturn(10.0F);

        A0061A0080CombatState state = new A0061A0080CombatState();
        assertTrue(state.armOpportunity(ACTOR, 1_000L));
        assertTrue(state.reserveOpportunity(ACTOR, "reserved-arrow-root", 1_100L));
        publishReceipt("reserved-arrow-root", false, false, FirstBloodReservation.NONE, false, true);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            wireRuntime(runtime, fixture.player, state, CombatPerkRanks.of(Map.of("A0080", 1)));
            A0073A0080ProjectileCommitEvents.onIncomingFinal(fixture.incoming);

            A0073A0080ProjectileCommitEvents.onDamagePost(secondPost);

            state.rollbackOpportunity(ACTOR, "reserved-arrow-root");
            assertTrue(state.reserveOpportunity(ACTOR, "retry-after-unrelated-arrow", 1_501L));
        }
    }

    @Test
    void twoFirstBloodOpenersCommitInInvertedArrowOrderWithoutCrossConsumption() {
        Fixture first = fixture();
        UUID secondTargetUuid = UUID.fromString("00000000-0000-0000-0000-000000000074");
        String secondTargetId = secondTargetUuid.toString();
        LivingEntity secondTarget = mock(LivingEntity.class);
        when(secondTarget.getUUID()).thenReturn(secondTargetUuid);
        AbstractArrow secondArrow = mock(AbstractArrow.class);
        when(secondArrow.getOwner()).thenReturn(first.player);
        DamageSource secondSource = mock(DamageSource.class);
        when(secondSource.getDirectEntity()).thenReturn(secondArrow);
        LivingIncomingDamageEvent secondIncoming = mock(LivingIncomingDamageEvent.class);
        when(secondIncoming.getSource()).thenReturn(secondSource);
        when(secondIncoming.getEntity()).thenReturn(secondTarget);
        when(secondIncoming.getAmount()).thenReturn(10.0F);
        when(secondIncoming.isCanceled()).thenReturn(false);
        LivingDamageEvent.Post secondPost = mock(LivingDamageEvent.Post.class);
        when(secondPost.getSource()).thenReturn(secondSource);
        when(secondPost.getEntity()).thenReturn(secondTarget);
        when(secondPost.getNewDamage()).thenReturn(10.0F);

        A0061A0080CombatState state = new A0061A0080CombatState();
        assertEquals(FirstBloodReservation.OPENER,
            state.reserveFirstBlood(ACTOR, TARGET, "first-root", 0.90D, 1_100L));
        assertEquals(FirstBloodReservation.OPENER,
            state.reserveFirstBlood(ACTOR, secondTargetId, "second-root", 0.90D, 1_100L));

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            wireRuntime(runtime, first.player, state, CombatPerkRanks.of(Map.of("A0074", 1)));

            publishReceipt("first-root", false, false, FirstBloodReservation.OPENER, true, false);
            A0073A0080ProjectileCommitEvents.onIncomingFinal(first.incoming);
            publishReceiptForTarget(secondTargetId, "second-root", FirstBloodReservation.OPENER);
            A0073A0080ProjectileCommitEvents.onIncomingFinal(secondIncoming);

            A0073A0080ProjectileCommitEvents.onDamagePost(secondPost);
            assertTrue(state.firstBloodWindowActive(ACTOR, secondTargetId, 1_501L));
            assertFalse(state.firstBloodWindowActive(ACTOR, TARGET, 1_501L));

            A0073A0080ProjectileCommitEvents.onDamagePost(first.post);
            assertTrue(state.firstBloodWindowActive(ACTOR, TARGET, 1_501L));
        }
    }

    @Test
    void lifecycleHandlersClearActorTargetArrowAndGlobalTransientState() {
        ServerPlayer player = mock(ServerPlayer.class);
        LivingEntity target = mock(LivingEntity.class);
        when(target.getUUID()).thenReturn(TARGET_UUID);
        LivingDeathEvent death = mock(LivingDeathEvent.class);
        when(death.getEntity()).thenReturn(target);

        EntityLeaveLevelEvent leave = mock(EntityLeaveLevelEvent.class);
        ServerLevel level = mock(ServerLevel.class);
        when(level.isClientSide()).thenReturn(false);
        when(leave.getLevel()).thenReturn(level);
        when(leave.getEntity()).thenReturn(target);

        PlayerEvent.PlayerLoggedOutEvent logout = mock(PlayerEvent.PlayerLoggedOutEvent.class);
        when(logout.getEntity()).thenReturn(player);
        PlayerEvent.PlayerChangedDimensionEvent dimension = mock(PlayerEvent.PlayerChangedDimensionEvent.class);
        when(dimension.getEntity()).thenReturn(player);
        PlayerEvent.PlayerRespawnEvent respawn = mock(PlayerEvent.PlayerRespawnEvent.class);
        when(respawn.getEntity()).thenReturn(player);
        ServerStoppedEvent stopped = mock(ServerStoppedEvent.class);
        A0061A0080CombatState state = mock(A0061A0080CombatState.class);

        try (MockedStatic<A0061A0080RuntimeState> runtime = mockStatic(A0061A0080RuntimeState.class)) {
            runtime.when(A0061A0080RuntimeState::state).thenReturn(state);

            A0073A0080ProjectileCommitEvents.onDeath(death);
            verify(state).clearTarget(TARGET);

            A0073A0080ProjectileCommitEvents.onEntityLeave(leave);
            verify(state, org.mockito.Mockito.times(2)).clearTarget(TARGET);

            A0073A0080ProjectileCommitEvents.onLogout(logout);
            A0073A0080ProjectileCommitEvents.onDimension(dimension);
            A0073A0080ProjectileCommitEvents.onRespawn(respawn);
            runtime.verify(() -> A0061A0080RuntimeState.clear(player), org.mockito.Mockito.times(3));

            A0073A0080ProjectileCommitEvents.onServerStopped(stopped);
            runtime.verify(A0061A0080RuntimeState::clearAll);
        }
    }

    @SuppressWarnings("deprecation")
    private static void prepareAllReservations(A0061A0080CombatState state, String root, long now) {
        assertTrue(state.armExecution(ACTOR, TARGET, "execution-opener", now - 100L));
        assertTrue(state.reserveExecution(ACTOR, TARGET, root, now));
        assertEquals(FirstBloodReservation.OPENER, state.reserveFirstBlood(ACTOR, TARGET, root, 0.90D, now));
        assertTrue(state.armOpportunity(ACTOR, now - 100L));
        assertTrue(state.reserveOpportunity(ACTOR, root, now));
    }

    private static void publishReceipt(String root, boolean executionReserved, boolean executionArmCandidate,
                                       FirstBloodReservation firstBlood, boolean firstBloodTracked,
                                       boolean opportunityReserved) {
        A0061A0080ReservationReceiptContext.begin(ACTOR, TARGET, root);
        if (executionReserved) A0061A0080ReservationReceiptContext.markExecutionReserved();
        if (executionArmCandidate) A0061A0080ReservationReceiptContext.markExecutionArmCandidate();
        if (firstBloodTracked) A0061A0080ReservationReceiptContext.markFirstBlood(firstBlood);
        if (opportunityReserved) A0061A0080ReservationReceiptContext.markOpportunityReserved();
    }

    private static void publishReceiptForTarget(String targetId, String root, FirstBloodReservation reservation) {
        A0061A0080ReservationReceiptContext.begin(ACTOR, targetId, root);
        A0061A0080ReservationReceiptContext.markFirstBlood(reservation);
    }

    private static void wireRuntime(MockedStatic<A0061A0080RuntimeState> runtime, ServerPlayer player,
                                    A0061A0080CombatState state, CombatPerkRanks ranks) {
        runtime.when(() -> A0061A0080RuntimeState.actorId(player)).thenReturn(ACTOR);
        runtime.when(A0061A0080RuntimeState::state).thenReturn(state);
        runtime.when(() -> A0061A0080RuntimeState.ranks(player)).thenReturn(ranks);
    }

    private static Fixture fixture() {
        ServerLevel level = mock(ServerLevel.class);
        when(level.isClientSide()).thenReturn(false);
        when(level.getGameTime()).thenReturn(30L);
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.level()).thenReturn(level);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);

        AbstractArrow arrow = mock(AbstractArrow.class);
        when(arrow.getOwner()).thenReturn(player);
        LivingEntity target = mock(LivingEntity.class);
        when(target.getUUID()).thenReturn(TARGET_UUID);
        DamageSource source = mock(DamageSource.class);
        when(source.getDirectEntity()).thenReturn(arrow);

        LivingIncomingDamageEvent incoming = mock(LivingIncomingDamageEvent.class);
        when(incoming.getSource()).thenReturn(source);
        when(incoming.getEntity()).thenReturn(target);
        when(incoming.getAmount()).thenReturn(10.0F);
        when(incoming.isCanceled()).thenReturn(false);

        LivingDamageEvent.Post post = mock(LivingDamageEvent.Post.class);
        when(post.getSource()).thenReturn(source);
        when(post.getEntity()).thenReturn(target);
        when(post.getNewDamage()).thenReturn(10.0F);
        return new Fixture(level, player, arrow, target, incoming, post);
    }

    private record Fixture(ServerLevel level, ServerPlayer player, AbstractArrow arrow, LivingEntity target,
                           LivingIncomingDamageEvent incoming, LivingDamageEvent.Post post) {}
}