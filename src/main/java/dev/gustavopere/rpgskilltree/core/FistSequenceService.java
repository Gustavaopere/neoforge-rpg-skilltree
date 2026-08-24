package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Canonical A0058-A0060 sequence ledger; provider facts are explicit inputs, never inferred from damage. */
public final class FistSequenceService {
    private static final int MAX_SEQUENCE = 5;
    private static final String A0060_COOLDOWN = "A0060:combination-final";
    private final Map<String, ActorState> actors = new HashMap<>();
    private final Map<String, Long> a0060Cooldowns = new HashMap<>();
    private final Set<ActionKey> creditedActions = new HashSet<>();
    private final Set<ActionKey> activationClaims = new HashSet<>();

    public synchronized int confirmedDirectHit(HitRequest request, long nowMillis) {
        Objects.requireNonNull(request);
        requireNow(nowMillis);
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.direct()
            || !request.hostileTarget() || !request.fistWeapon() || request.a0058Rank() <= 0
            || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) {
            return sequence(request.action().actorId());
        }
        ActionKey key = ActionKey.of(request.action());
        if (!creditedActions.add(key)) return sequence(request.action().actorId());

        ActorState actor = actors.computeIfAbsent(request.action().actorId(), ignored -> new ActorState());
        long window = request.a0058Rank() >= 2 ? 2_500L : 2_000L;
        if (actor.lastHitAtMillis >= 0L && nowMillis - actor.lastHitAtMillis > window) actor.actions.clear();
        actor.lastHitAtMillis = nowMillis;
        if (actor.actions.size() == MAX_SEQUENCE) actor.actions.removeFirst();
        actor.actions.addLast(request.action());
        return actor.actions.size();
    }

    public synchronized FinisherEffect activate(FinisherRequest request, long nowMillis) {
        Objects.requireNonNull(request);
        requireNow(nowMillis);
        if (!request.serverAuthoritative() || !request.eligibleActor() || !request.direct()
            || !request.hostileTarget() || !request.fistWeapon()
            || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())
            || !request.heavyOrFinisherConfirmed()) {
            return FinisherEffect.inactive();
        }
        ActionKey activation = ActionKey.of(request.action());
        if (!activationClaims.add(activation)) return FinisherEffect.duplicate();
        ActorState actor = actors.get(request.action().actorId());
        if (actor == null) return FinisherEffect.inactive();

        if (request.a0060Rank() > 0 && actor.actions.size() == MAX_SEQUENCE
            && cooldownReady(request.action().actorId(), nowMillis)
            && (request.damageProviderAvailable() || request.impactProviderAvailable())) {
            List<CanonicalActionIdentity> receipts = List.copyOf(actor.actions);
            actor.actions.clear();
            long cooldown = request.fistMastery() >= 100 ? 6_000L : request.fistMastery() >= 90 ? 7_000L : 8_000L;
            a0060Cooldowns.put(request.action().actorId(), Math.addExact(nowMillis, cooldown));
            return new FinisherEffect(
                false,
                request.damageProviderAvailable() ? 0.18D : 0.0D,
                0.0D,
                request.impactProviderAvailable() ? 0.25D : 0.0D,
                receipts,
                true
            );
        }

        if (request.a0059Rank() > 0 && actor.actions.size() >= 3
            && (request.guardPressureProviderAvailable() || request.impactProviderAvailable())) {
            for (int i = 0; i < 3; i++) actor.actions.removeFirst();
            double pressure = request.guardPressureProviderAvailable()
                ? request.a0059Rank() >= 2 ? 0.40D : 0.25D : 0.0D;
            double impact = request.impactProviderAvailable()
                ? request.a0059Rank() >= 2 ? 0.15D : 0.10D : 0.0D;
            return new FinisherEffect(false, 0.0D, pressure, impact, List.of(), false);
        }
        return FinisherEffect.inactive();
    }

    public synchronized void reset(String actorId) { actors.remove(actorId); }

    public synchronized int sequence(String actorId) {
        ActorState actor = actors.get(actorId);
        return actor == null ? 0 : actor.actions.size();
    }

    public synchronized boolean cooldownReady(String actorId, long nowMillis) {
        return a0060Cooldowns.getOrDefault(actorId, 0L) <= nowMillis;
    }

    /** Does not clear cooldown or canonical claims, preventing lifecycle bypass. */
    public synchronized void clearTransient(String actorId) { actors.remove(actorId); }

    private static void requireNow(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
    }

    public record HitRequest(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        boolean hostileTarget,
        boolean fistWeapon,
        int a0058Rank
    ) { public HitRequest { Objects.requireNonNull(action); } }

    public record FinisherRequest(
        CanonicalActionIdentity action,
        boolean serverAuthoritative,
        boolean eligibleActor,
        boolean direct,
        boolean hostileTarget,
        boolean fistWeapon,
        boolean heavyOrFinisherConfirmed,
        boolean damageProviderAvailable,
        boolean guardPressureProviderAvailable,
        boolean impactProviderAvailable,
        int a0059Rank,
        int a0060Rank,
        int fistMastery
    ) { public FinisherRequest { Objects.requireNonNull(action); } }

    public record FinisherEffect(
        boolean duplicate,
        double damageBonus,
        double guardPressureBonus,
        double impactBonus,
        List<CanonicalActionIdentity> receiptActions,
        boolean combinationFinal
    ) {
        public FinisherEffect { receiptActions = List.copyOf(receiptActions); }
        static FinisherEffect inactive() { return new FinisherEffect(false, 0.0D, 0.0D, 0.0D, List.of(), false); }
        static FinisherEffect duplicate() { return new FinisherEffect(true, 0.0D, 0.0D, 0.0D, List.of(), false); }
        public boolean active() { return damageBonus > 0.0D || guardPressureBonus > 0.0D || impactBonus > 0.0D; }
    }

    private static final class ActorState {
        final Deque<CanonicalActionIdentity> actions = new ArrayDeque<>();
        long lastHitAtMillis = -1L;
    }

    private record ActionKey(String actorId, String actionId) {
        static ActionKey of(CanonicalActionIdentity action) { return new ActionKey(action.actorId(), action.actionId()); }
    }
}
