package dev.gustavopere.rpgskilltree.core;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Canonical A0112/A0114 selector; provider debit and repair cross one atomic boundary. */
public final class CanonicalMaintenanceService {
    private static final long OUT_OF_COMBAT_TICKS = 200L;
    private final int maxPlayers;
    private final Map<String, Long> lastHostileCombat = new HashMap<>();
    private final Map<String, EnumMap<Mode, Long>> nextAllowed = new HashMap<>();

    public CanonicalMaintenanceService(int maxPlayers) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        this.maxPlayers = maxPlayers;
    }

    public synchronized void recordHostileCombat(String playerId, long nowTick) {
        requireId(playerId, "playerId");
        requireTick(nowTick);
        makeRoom(playerId);
        lastHostileCombat.put(playerId, nowTick);
    }

    public synchronized Result maintain(
        String playerId,
        Mode mode,
        FrozenSurvivalPerkRanks ranks,
        List<Candidate> candidates,
        AtomicMaintenanceAdapter adapter,
        long nowTick
    ) {
        requireId(playerId, "playerId");
        Objects.requireNonNull(mode);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(candidates);
        Objects.requireNonNull(adapter);
        requireTick(nowTick);

        int rank = ranks.rank(mode == Mode.AUTO ? "A0112" : "A0114");
        if (rank <= 0) return Result.of(Status.INACTIVE);
        long allowed = nextAllowed.getOrDefault(playerId, new EnumMap<>(Mode.class))
            .getOrDefault(mode, 0L);
        if (allowed > nowTick) return Result.of(Status.COOLDOWN);
        Long hostileTick = lastHostileCombat.get(playerId);
        if (hostileTick == null || nowTick - hostileTick < OUT_OF_COMBAT_TICKS) {
            return Result.of(Status.IN_COMBAT);
        }

        Optional<Candidate> selected = candidates.stream()
            .filter(candidate -> eligible(candidate, mode))
            .min(Comparator.comparingDouble(Candidate::durabilityRatio)
                .thenComparing(Candidate::position)
                .thenComparing(Candidate::itemInstanceId));
        if (selected.isEmpty()) return Result.of(Status.NO_CANDIDATE);
        Candidate candidate = selected.orElseThrow();
        Transaction transaction = new Transaction(
            playerId, mode, candidate.itemInstanceId(), candidate.resourceId(),
            candidate.resourceAmount(), candidate.repairAmount());
        if (!adapter.debitAndRepairAtomically(transaction)) {
            return Result.of(Status.TRANSACTION_FAILED);
        }
        makeRoom(playerId);
        nextAllowed.computeIfAbsent(playerId, ignored -> new EnumMap<>(Mode.class))
            .put(mode, Math.addExact(nowTick, interval(mode, rank)));
        return new Result(Status.SUCCESS, Optional.of(candidate.itemInstanceId()));
    }

    /** Successful-cycle cooldowns and the authoritative combat clock are intentionally retained. */
    public synchronized void clearTransient(String playerId) {
        requireId(playerId, "playerId");
    }

    private static boolean eligible(Candidate candidate, Mode mode) {
        Objects.requireNonNull(candidate);
        return candidate.providerMapped()
            && candidate.activeOrEquipped()
            && candidate.resourcePayable()
            && candidate.durabilityRemaining() > 0
            && candidate.durabilityRemaining() < candidate.durabilityMaximum()
            && (mode != Mode.BOUND_RELIC || candidate.boundOrAttuned());
    }

    private static long interval(Mode mode, int rank) {
        if (mode == Mode.BOUND_RELIC) return 400L;
        return switch (rank) {
            case 1 -> 600L;
            case 2 -> 480L;
            default -> 360L;
        };
    }

    private void makeRoom(String playerId) {
        if (lastHostileCombat.containsKey(playerId) || lastHostileCombat.size() < maxPlayers) return;
        String evicted = lastHostileCombat.keySet().iterator().next();
        lastHostileCombat.remove(evicted);
        nextAllowed.remove(evicted);
    }

    public enum Mode { AUTO, BOUND_RELIC }

    public enum Position { MAIN_HAND, OFF_HAND, HEAD, CHEST, LEGS, FEET, ADAPTER }

    public enum Status { SUCCESS, INACTIVE, IN_COMBAT, COOLDOWN, NO_CANDIDATE, TRANSACTION_FAILED }

    @FunctionalInterface
    public interface AtomicMaintenanceAdapter {
        /** Returns true only when exact resource debit and exact provider repair committed together. */
        boolean debitAndRepairAtomically(Transaction transaction);
    }

    public record Candidate(
        String itemInstanceId,
        Position position,
        int durabilityRemaining,
        int durabilityMaximum,
        boolean activeOrEquipped,
        boolean boundOrAttuned,
        boolean providerMapped,
        boolean resourcePayable,
        String resourceId,
        double resourceAmount,
        int repairAmount
    ) {
        public Candidate {
            requireId(itemInstanceId, "itemInstanceId");
            Objects.requireNonNull(position);
            requireId(resourceId, "resourceId");
            if (durabilityMaximum <= 0 || durabilityRemaining < 0
                || durabilityRemaining > durabilityMaximum || repairAmount <= 0
                || !Double.isFinite(resourceAmount) || resourceAmount <= 0.0D) {
                throw new IllegalArgumentException("invalid maintenance candidate");
            }
        }

        private double durabilityRatio() { return (double)durabilityRemaining / durabilityMaximum; }
    }

    public record Transaction(
        String playerId,
        Mode mode,
        String itemInstanceId,
        String resourceId,
        double resourceAmount,
        int repairAmount
    ) {}

    public record Result(Status status, Optional<String> itemInstanceId) {
        public Result {
            Objects.requireNonNull(status);
            itemInstanceId = Objects.requireNonNull(itemInstanceId);
        }

        private static Result of(Status status) { return new Result(status, Optional.empty()); }
    }

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    private static void requireTick(long tick) {
        if (tick < 0L) throw new IllegalArgumentException("tick must be non-negative");
    }
}
