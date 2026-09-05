package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Server-authoritative state machine for the stateful defensive perks A0104-A0106.
 *
 * <p>The state is keyed by canonical actor id so it survives ordinary player-object replacement
 * (respawn/dimension/logout-relogin while the server stays alive). Rank reconciliation removes
 * active effects without erasing cooldown deadlines. Cooldown snapshots can be hydrated from the
 * canonical player attachment so a full server restart cannot reset them either.</p>
 */
public final class A0101A0110DefenseState {
    public static final long SECOND_WIND_COOLDOWN_TICKS = 1200L;
    public static final long[] SECOND_WIND_PULSE_DELAYS = {20L, 40L, 60L, 80L, 100L};
    public static final double SECOND_WIND_PULSE_MAX_HEALTH_FRACTION = 0.024D;

    public static final long REACTIVE_SHELL_WINDOW_TICKS = 80L;
    public static final long REACTIVE_SHELL_DURATION_TICKS = 120L;
    public static final long REACTIVE_SHELL_COOLDOWN_TICKS = 400L;

    public static final long EMERGENCY_GUARD_DURATION_TICKS = 60L;
    public static final long EMERGENCY_GUARD_COOLDOWN_TICKS = 3600L;

    private final Map<String, ActorState> actors = new HashMap<>();

    /** Hydrates only anti-reset cooldown deadlines; active windows/receipts remain runtime-local. */
    public synchronized void hydrateCooldowns(String actorId, CombatPerkCooldownState persisted) {
        Objects.requireNonNull(actorId, "actorId");
        Objects.requireNonNull(persisted, "persisted");
        ActorState actor = actor(actorId);
        actor.secondWind.cooldownUntilTick = Math.max(
            actor.secondWind.cooldownUntilTick,
            persisted.secondWindCooldownUntilTick()
        );
        actor.reactiveShell.cooldownUntilTick = Math.max(
            actor.reactiveShell.cooldownUntilTick,
            persisted.reactiveShellCooldownUntilTick()
        );
        actor.emergencyGuard.cooldownUntilTick = Math.max(
            actor.emergencyGuard.cooldownUntilTick,
            persisted.emergencyGuardCooldownUntilTick()
        );
        pruneActorIfEmpty(actorId, actor);
    }

    public synchronized CombatPerkCooldownState cooldownSnapshot(String actorId) {
        Objects.requireNonNull(actorId, "actorId");
        ActorState actor = actors.get(actorId);
        if (actor == null) return CombatPerkCooldownState.empty();
        return new CombatPerkCooldownState(
            actor.secondWind.cooldownUntilTick,
            actor.reactiveShell.cooldownUntilTick,
            actor.emergencyGuard.cooldownUntilTick
        );
    }

    public synchronized void reconcileRanks(
        String actorId,
        boolean secondWindLearned,
        boolean reactiveShellLearned,
        boolean emergencyGuardLearned
    ) {
        ActorState actor = actor(actorId);
        if (!secondWindLearned) actor.secondWind.clearSchedulePreservingCooldown();
        if (!reactiveShellLearned) actor.reactiveShell.clearActivePreservingCooldown();
        if (!emergencyGuardLearned) actor.emergencyGuard.clearActivePreservingCooldown();
        pruneActorIfEmpty(actorId, actor);
    }

    /**
     * Records one confirmed direct hostile hit for A0104. The trigger hit cannot cancel its own
     * newly-created schedule; later distinct roots cancel one future unpaid pulse each.
     */
    public synchronized boolean recordSecondWindHit(
        String actorId,
        String rootActionId,
        long nowTick,
        double preHealthFraction,
        double postHealthFraction,
        boolean learned
    ) {
        requireActorAndRoot(actorId, rootActionId);
        finiteFraction(preHealthFraction, "preHealthFraction");
        finiteFraction(postHealthFraction, "postHealthFraction");
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick");

        ActorState actor = actor(actorId);
        SecondWind secondWind = actor.secondWind;
        if (!learned) {
            secondWind.clearSchedulePreservingCooldown();
            pruneActorIfEmpty(actorId, actor);
            return false;
        }

        secondWind.cancelNextUnpaidPulse(rootActionId);
        secondWind.finishIfExhausted();

        boolean crossing = preHealthFraction > 0.25D && postHealthFraction < 0.25D;
        if (!crossing || secondWind.scheduleActive() || nowTick < secondWind.cooldownUntilTick) {
            return false;
        }

        secondWind.activate(rootActionId, nowTick);
        return true;
    }

    /** Claims every non-cancelled A0104 pulse whose scheduled game tick has arrived. */
    public synchronized int claimSecondWindPulses(String actorId, long nowTick, boolean learned) {
        Objects.requireNonNull(actorId, "actorId");
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick");
        ActorState actor = actors.get(actorId);
        if (actor == null) return 0;
        if (!learned) {
            actor.secondWind.clearSchedulePreservingCooldown();
            pruneActorIfEmpty(actorId, actor);
            return 0;
        }
        int due = actor.secondWind.claimDue(nowTick);
        pruneActorIfEmpty(actorId, actor);
        return due;
    }

    /** Records one confirmed direct hostile hit for A0105; true means activation occurred now. */
    public synchronized boolean recordReactiveShellHit(
        String actorId,
        String rootActionId,
        long nowTick,
        boolean learned
    ) {
        requireActorAndRoot(actorId, rootActionId);
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick");
        ActorState actor = actor(actorId);
        ReactiveShell shell = actor.reactiveShell;
        if (!learned) {
            shell.clearActivePreservingCooldown();
            pruneActorIfEmpty(actorId, actor);
            return false;
        }

        shell.expireActive(nowTick);
        if (shell.active(nowTick)) return false;

        shell.pruneReceipts(nowTick);
        if (shell.receipts.stream().anyMatch(receipt -> receipt.rootActionId.equals(rootActionId))) {
            return false;
        }
        shell.receipts.addLast(new HitReceipt(nowTick, rootActionId));

        if (shell.receipts.size() < 3 || nowTick < shell.cooldownUntilTick) return false;
        shell.activeUntilTickExclusive = Math.addExact(nowTick, REACTIVE_SHELL_DURATION_TICKS);
        shell.cooldownUntilTick = Math.addExact(nowTick, REACTIVE_SHELL_COOLDOWN_TICKS);
        shell.receipts.clear();
        return true;
    }

    public synchronized boolean reactiveShellActive(String actorId, long nowTick, boolean learned) {
        Objects.requireNonNull(actorId, "actorId");
        ActorState actor = actors.get(actorId);
        if (actor == null) return false;
        if (!learned) {
            actor.reactiveShell.clearActivePreservingCooldown();
            pruneActorIfEmpty(actorId, actor);
            return false;
        }
        actor.reactiveShell.expireActive(nowTick);
        boolean active = actor.reactiveShell.active(nowTick);
        pruneActorIfEmpty(actorId, actor);
        return active;
    }

    /**
     * Applies A0106 after all earlier RPG reductions. Eligibility/exclusion is decided by the
     * caller from the real DamageSource. The returned damage is the only value the event bridge
     * should write back to LivingDamageEvent.Pre.
     */
    public synchronized EmergencyGuardResult applyEmergencyGuard(
        String actorId,
        long nowTick,
        double health,
        double maxHealth,
        double incomingDamage,
        boolean learned,
        boolean eligibleHostileDamage
    ) {
        Objects.requireNonNull(actorId, "actorId");
        finiteNonNegative(health, "health");
        finitePositive(maxHealth, "maxHealth");
        finiteNonNegative(incomingDamage, "incomingDamage");
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick");

        ActorState actor = actor(actorId);
        EmergencyGuard guard = actor.emergencyGuard;
        guard.expireActive(nowTick);

        if (!learned) {
            guard.clearActivePreservingCooldown();
            pruneActorIfEmpty(actorId, actor);
            return new EmergencyGuardResult(incomingDamage, false, false, false);
        }
        if (!eligibleHostileDamage || incomingDamage <= 0.0D) {
            return new EmergencyGuardResult(incomingDamage, false, false, guard.active(nowTick));
        }

        boolean activated = false;
        if (!guard.active(nowTick)
            && nowTick >= guard.cooldownUntilTick
            && health - incomingDamage < 0.15D * maxHealth) {
            guard.activeUntilTickExclusive = Math.addExact(nowTick, EMERGENCY_GUARD_DURATION_TICKS);
            guard.cooldownUntilTick = Math.addExact(nowTick, EMERGENCY_GUARD_COOLDOWN_TICKS);
            guard.fatalSafeguardAvailable = true;
            activated = true;
        }

        if (!guard.active(nowTick)) {
            return new EmergencyGuardResult(incomingDamage, activated, false, false);
        }

        double reduced = Math.max(0.0D, incomingDamage * 0.65D);
        boolean tokenConsumed = false;
        if (guard.fatalSafeguardAvailable && reduced >= health) {
            guard.fatalSafeguardAvailable = false;
            reduced = Math.max(0.0D, health - 1.0D);
            tokenConsumed = true;
        }
        return new EmergencyGuardResult(reduced, activated, tokenConsumed, true);
    }

    /** Clears ephemeral windows/receipts but deliberately preserves anti-reset cooldowns. */
    public synchronized void reconcilePlayerBoundary(String actorId) {
        Objects.requireNonNull(actorId, "actorId");
        ActorState actor = actors.get(actorId);
        if (actor == null) return;
        actor.secondWind.clearSchedulePreservingCooldown();
        actor.reactiveShell.clearReceiptsAndActivePreservingCooldown();
        actor.emergencyGuard.clearActivePreservingCooldown();
        pruneActorIfEmpty(actorId, actor);
    }

    public synchronized void clearAll() {
        actors.clear();
    }

    private ActorState actor(String actorId) {
        Objects.requireNonNull(actorId, "actorId");
        return actors.computeIfAbsent(actorId, ignored -> new ActorState());
    }

    private void pruneActorIfEmpty(String actorId, ActorState actor) {
        if (actor.empty()) actors.remove(actorId);
    }

    private static void requireActorAndRoot(String actorId, String rootActionId) {
        Objects.requireNonNull(actorId, "actorId");
        Objects.requireNonNull(rootActionId, "rootActionId");
        if (rootActionId.isBlank()) throw new IllegalArgumentException("rootActionId");
    }

    private static void finiteFraction(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name);
        }
    }

    private static void finiteNonNegative(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0D) throw new IllegalArgumentException(name);
    }

    private static void finitePositive(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0D) throw new IllegalArgumentException(name);
    }

    public record EmergencyGuardResult(
        double damage,
        boolean activated,
        boolean fatalSafeguardConsumed,
        boolean activeWindow
    ) {}

    private static final class ActorState {
        final SecondWind secondWind = new SecondWind();
        final ReactiveShell reactiveShell = new ReactiveShell();
        final EmergencyGuard emergencyGuard = new EmergencyGuard();

        boolean empty() {
            return secondWind.empty() && reactiveShell.empty() && emergencyGuard.empty();
        }
    }

    private static final class SecondWind {
        long activationTick = -1L;
        long cooldownUntilTick;
        int nextPulseIndex;
        final Set<Integer> cancelledPulseIndexes = new HashSet<>();
        final Set<String> cancellationRoots = new HashSet<>();

        boolean scheduleActive() {
            return activationTick >= 0L && nextPulseIndex < SECOND_WIND_PULSE_DELAYS.length;
        }

        void activate(String triggerRoot, long nowTick) {
            activationTick = nowTick;
            cooldownUntilTick = Math.addExact(nowTick, SECOND_WIND_COOLDOWN_TICKS);
            nextPulseIndex = 0;
            cancelledPulseIndexes.clear();
            cancellationRoots.clear();
            cancellationRoots.add(triggerRoot);
        }

        void cancelNextUnpaidPulse(String rootActionId) {
            if (!scheduleActive() || cancellationRoots.contains(rootActionId)) return;
            for (int index = nextPulseIndex; index < SECOND_WIND_PULSE_DELAYS.length; index++) {
                if (!cancelledPulseIndexes.contains(index)) {
                    cancelledPulseIndexes.add(index);
                    cancellationRoots.add(rootActionId);
                    return;
                }
            }
        }

        int claimDue(long nowTick) {
            if (!scheduleActive()) return 0;
            int due = 0;
            while (nextPulseIndex < SECOND_WIND_PULSE_DELAYS.length
                && Math.addExact(activationTick, SECOND_WIND_PULSE_DELAYS[nextPulseIndex]) <= nowTick) {
                if (!cancelledPulseIndexes.contains(nextPulseIndex)) due++;
                nextPulseIndex++;
            }
            finishIfExhausted();
            return due;
        }

        void finishIfExhausted() {
            if (activationTick >= 0L && nextPulseIndex >= SECOND_WIND_PULSE_DELAYS.length) {
                activationTick = -1L;
                nextPulseIndex = 0;
                cancelledPulseIndexes.clear();
                cancellationRoots.clear();
            }
        }

        void clearSchedulePreservingCooldown() {
            activationTick = -1L;
            nextPulseIndex = 0;
            cancelledPulseIndexes.clear();
            cancellationRoots.clear();
        }

        boolean empty() {
            return !scheduleActive() && cooldownUntilTick == 0L;
        }
    }

    private static final class ReactiveShell {
        final ArrayDeque<HitReceipt> receipts = new ArrayDeque<>();
        long activeUntilTickExclusive;
        long cooldownUntilTick;

        void pruneReceipts(long nowTick) {
            long oldestAllowed = nowTick - REACTIVE_SHELL_WINDOW_TICKS;
            while (!receipts.isEmpty() && receipts.peekFirst().tick < oldestAllowed) receipts.removeFirst();
        }

        boolean active(long nowTick) {
            return activeUntilTickExclusive > nowTick;
        }

        void expireActive(long nowTick) {
            if (activeUntilTickExclusive != 0L && nowTick >= activeUntilTickExclusive) {
                activeUntilTickExclusive = 0L;
            }
        }

        void clearActivePreservingCooldown() {
            activeUntilTickExclusive = 0L;
            receipts.clear();
        }

        void clearReceiptsAndActivePreservingCooldown() {
            activeUntilTickExclusive = 0L;
            receipts.clear();
        }

        boolean empty() {
            return receipts.isEmpty() && activeUntilTickExclusive == 0L && cooldownUntilTick == 0L;
        }
    }

    private static final class EmergencyGuard {
        long activeUntilTickExclusive;
        long cooldownUntilTick;
        boolean fatalSafeguardAvailable;

        boolean active(long nowTick) {
            return activeUntilTickExclusive > nowTick;
        }

        void expireActive(long nowTick) {
            if (activeUntilTickExclusive != 0L && nowTick >= activeUntilTickExclusive) {
                activeUntilTickExclusive = 0L;
                fatalSafeguardAvailable = false;
            }
        }

        void clearActivePreservingCooldown() {
            activeUntilTickExclusive = 0L;
            fatalSafeguardAvailable = false;
        }

        boolean empty() {
            return activeUntilTickExclusive == 0L && cooldownUntilTick == 0L && !fatalSafeguardAvailable;
        }
    }

    private record HitReceipt(long tick, String rootActionId) {}
}
