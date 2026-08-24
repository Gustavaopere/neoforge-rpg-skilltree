package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Couples a benefit window to a canonical provider lease for its metabolic costs. */
public final class CanonicalBodyTradeoffService {
    private final Provider provider;
    private final Map<LeaseKey, LeaseState> states = new HashMap<>();

    public CanonicalBodyTradeoffService(Provider provider) { this.provider = provider; }

    public synchronized Activation activate(String actorId, LeaseRequest request, long nowTick) {
        require(actorId, request, nowTick);
        LeaseKey key = new LeaseKey(actorId, request.benefitId());
        LeaseState current = states.get(key);
        if (current != null && current.activeUntilTick > nowTick && maintain(key, current, nowTick)) {
            return new Activation(true, current.activeUntilTick, current.cooldownUntilTick);
        }
        if (current != null && current.activeUntilTick > 0L && current.activeUntilTick <= nowTick) {
            terminate(key, current, current.activeUntilTick);
        }
        if (current != null && current.cooldownUntilTick > nowTick) {
            return new Activation(false, 0L, current.cooldownUntilTick);
        }
        if (provider == null || !provider.acquire(actorId, request)) return Activation.inactive();
        long activeUntil = Math.addExact(nowTick, request.durationTicks());
        LeaseState state = new LeaseState(request, activeUntil, 0L, false);
        states.put(key, state);
        return new Activation(true, activeUntil, 0L);
    }

    public synchronized boolean benefitActive(String actorId, String benefitId, long nowTick) {
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick must be non-negative");
        LeaseKey key = new LeaseKey(requireId(actorId), requireId(benefitId));
        LeaseState state = states.get(key);
        if (state == null) return false;
        if (state.activeUntilTick <= nowTick) {
            terminate(key, state, state.activeUntilTick);
            return false;
        }
        return maintain(key, state, nowTick);
    }

    /** Clears only the transient lease; the anti-bypass cooldown remains. */
    public synchronized void clearTransient(String actorId, long nowTick) {
        actorId = requireId(actorId);
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick must be non-negative");
        for (var entry : states.entrySet()) {
            if (entry.getKey().actorId.equals(actorId) && entry.getValue().activeUntilTick > nowTick) {
                terminate(entry.getKey(), entry.getValue(), nowTick);
            }
        }
    }

    private boolean maintain(LeaseKey key, LeaseState state, long nowTick) {
        if (provider != null && provider.maintain(key.actorId, state.request)) return true;
        terminate(key, state, nowTick);
        return false;
    }

    private void terminate(LeaseKey key, LeaseState state, long endedAtTick) {
        if (!state.released && provider != null) {
            provider.release(key.actorId, state.request);
            state.released = true;
        }
        state.activeUntilTick = 0L;
        state.cooldownUntilTick = Math.max(state.cooldownUntilTick, Math.addExact(endedAtTick, state.request.cooldownTicks()));
    }

    private static void require(String actorId, LeaseRequest request, long nowTick) {
        requireId(actorId);
        Objects.requireNonNull(request);
        if (nowTick < 0L) throw new IllegalArgumentException("nowTick must be non-negative");
    }

    private static String requireId(String id) {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("id must not be blank");
        return id;
    }

    public interface Provider {
        boolean acquire(String actorId, LeaseRequest request);
        boolean maintain(String actorId, LeaseRequest request);
        void release(String actorId, LeaseRequest request);
    }

    public record LeaseRequest(String benefitId, double heatMultiplier, double exhaustionMultiplier, long durationTicks, long cooldownTicks) {
        public LeaseRequest {
            requireId(benefitId);
            if (!Double.isFinite(heatMultiplier) || heatMultiplier < 0.0D
                || !Double.isFinite(exhaustionMultiplier) || exhaustionMultiplier < 0.0D) {
                throw new IllegalArgumentException("metabolic costs must be finite and non-negative");
            }
            if (durationTicks <= 0L || cooldownTicks < 0L) throw new IllegalArgumentException("invalid lease timings");
        }
    }

    public record Activation(boolean active, long activeUntilTick, long cooldownUntilTick) {
        static Activation inactive() { return new Activation(false, 0L, 0L); }
    }

    private record LeaseKey(String actorId, String benefitId) {}
    private static final class LeaseState {
        final LeaseRequest request;
        long activeUntilTick;
        long cooldownUntilTick;
        boolean released;
        LeaseState(LeaseRequest request, long activeUntilTick, long cooldownUntilTick, boolean released) {
            this.request = request;
            this.activeUntilTick = activeUntilTick;
            this.cooldownUntilTick = cooldownUntilTick;
            this.released = released;
        }
    }
}
