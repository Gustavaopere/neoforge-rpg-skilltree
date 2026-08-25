package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Persistent HOT/COLD charge state with online-only consecutive exposure timers. */
public final class AcclimationLedger {
    public static final int MAX_CHARGES = 5;
    private static final long GAIN_TICKS = 12_000L;
    private static final long OUTSIDE_DECAY_TICKS = 24_000L;
    private static final long OPPOSITE_DECAY_TICKS = 6_000L;
    private final int maxPlayers;
    private final Map<String, PlayerState> players = new HashMap<>();

    public AcclimationLedger(int maxPlayers) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        this.maxPlayers = maxPlayers;
    }

    public synchronized Snapshot observe(String playerId, ThermalState state, long nowTick) {
        requireId(playerId, "playerId");
        Objects.requireNonNull(state);
        requireTick(nowTick);
        PlayerState player = players.get(playerId);
        if (player == null) {
            if (players.size() >= maxPlayers) throw new IllegalStateException("acclimation resident capacity exceeded");
            player = new PlayerState();
            players.put(playerId, player);
        }
        if (state == ThermalState.UNKNOWN) {
            suspend(playerId);
            return player.snapshot();
        }
        if (player.lastObservedTick == null) {
            player.lastObservedTick = nowTick;
            player.state = state;
            return player.snapshot();
        }
        if (nowTick < player.lastObservedTick) throw new IllegalArgumentException("nowTick moved backwards");
        long elapsed = nowTick - player.lastObservedTick;
        player.advance(elapsed);
        player.lastObservedTick = nowTick;
        if (player.state != state) {
            player.state = state;
            player.hotProgress = 0L;
            player.coldProgress = 0L;
        }
        return player.snapshot();
    }

    /** Stops online timers without modifying persistent charges. */
    public synchronized void suspend(String playerId) {
        requireId(playerId, "playerId");
        PlayerState player = players.get(playerId);
        if (player == null) return;
        player.lastObservedTick = null;
        player.state = ThermalState.UNKNOWN;
        player.hotProgress = 0L;
        player.coldProgress = 0L;
    }

    public synchronized Snapshot snapshot(String playerId) {
        requireId(playerId, "playerId");
        PlayerState player = players.get(playerId);
        return player == null ? new Snapshot(0, 0) : player.snapshot();
    }

    public synchronized void restore(String playerId, Snapshot snapshot) {
        requireId(playerId, "playerId");
        Objects.requireNonNull(snapshot);
        PlayerState player = players.computeIfAbsent(playerId, ignored -> {
            if (players.size() >= maxPlayers) throw new IllegalStateException("acclimation resident capacity exceeded");
            return new PlayerState();
        });
        player.hotCharges = snapshot.hotCharges();
        player.coldCharges = snapshot.coldCharges();
        player.lastObservedTick = null;
        player.state = ThermalState.UNKNOWN;
        player.hotProgress = 0L;
        player.coldProgress = 0L;
    }

    public enum ThermalState { HOT, COLD, NEUTRAL, UNKNOWN }

    public record Snapshot(int hotCharges, int coldCharges) {
        public Snapshot {
            if (hotCharges < 0 || hotCharges > MAX_CHARGES || coldCharges < 0 || coldCharges > MAX_CHARGES) {
                throw new IllegalArgumentException("charges must be in [0,5]");
            }
        }
    }

    private static final class PlayerState {
        private int hotCharges;
        private int coldCharges;
        private ThermalState state = ThermalState.UNKNOWN;
        private Long lastObservedTick;
        private long hotProgress;
        private long coldProgress;

        private void advance(long elapsed) {
            if (elapsed <= 0L) return;
            switch (state) {
                case HOT -> {
                    hotProgress = advanceGain(hotProgress, elapsed, true);
                    coldProgress = advanceDecay(coldProgress, elapsed, false, OPPOSITE_DECAY_TICKS);
                }
                case COLD -> {
                    coldProgress = advanceGain(coldProgress, elapsed, false);
                    hotProgress = advanceDecay(hotProgress, elapsed, true, OPPOSITE_DECAY_TICKS);
                }
                case NEUTRAL -> {
                    hotProgress = advanceDecay(hotProgress, elapsed, true, OUTSIDE_DECAY_TICKS);
                    coldProgress = advanceDecay(coldProgress, elapsed, false, OUTSIDE_DECAY_TICKS);
                }
                case UNKNOWN -> { }
            }
        }

        private long advanceGain(long progress, long elapsed, boolean hot) {
            int charges = hot ? hotCharges : coldCharges;
            if (charges >= MAX_CHARGES) return 0L;
            long total = Math.addExact(progress, elapsed);
            while (total >= GAIN_TICKS && charges < MAX_CHARGES) {
                total -= GAIN_TICKS;
                charges++;
            }
            if (hot) hotCharges = charges; else coldCharges = charges;
            return charges >= MAX_CHARGES ? 0L : total;
        }

        private long advanceDecay(long progress, long elapsed, boolean hot, long threshold) {
            int charges = hot ? hotCharges : coldCharges;
            if (charges <= 0) return 0L;
            long total = Math.addExact(progress, elapsed);
            while (total >= threshold && charges > 0) {
                total -= threshold;
                charges--;
            }
            if (hot) hotCharges = charges; else coldCharges = charges;
            return charges <= 0 ? 0L : total;
        }

        private Snapshot snapshot() { return new Snapshot(hotCharges, coldCharges); }
    }

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    private static void requireTick(long tick) {
        if (tick < 0L) throw new IllegalArgumentException("tick must be non-negative");
    }
}
