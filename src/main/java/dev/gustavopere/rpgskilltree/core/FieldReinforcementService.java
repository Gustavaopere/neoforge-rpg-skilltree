package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** A0113 progress and one-shot repair bonus keyed by exact player and persistent tool instance. */
public final class FieldReinforcementService {
    private static final int REQUIRED_HARVESTS = 12;
    private static final long READY_TICKS = 600L;
    private final CanonicalEventLedger harvestClaims;
    private final Map<Key, Integer> progress = new HashMap<>();
    private final Map<Key, Long> readyUntil = new HashMap<>();
    private final Set<String> invalidInstances = new HashSet<>();

    public FieldReinforcementService(int maxClaims) {
        harvestClaims = new CanonicalEventLedger(maxClaims);
    }

    public synchronized boolean recordHarvest(Harvest harvest, int rank, long nowTick) {
        Objects.requireNonNull(harvest);
        requireTick(nowTick);
        if (harvest.duplicatedInstanceDetected()) {
            invalidateDuplicatedInstance(harvest.toolInstanceId());
            return false;
        }
        if (rank <= 0 || !harvest.legitimate() || invalidInstances.contains(harvest.toolInstanceId())
            || !ProcGuard.mayTriggerSecondaryEffect(harvest.action().origin())) return false;
        if (!harvestClaims.claimPrimaryOnce(harvest.action(),
            "field_reinforcement:" + harvest.playerId() + ":" + harvest.toolInstanceId(), nowTick, READY_TICKS)) {
            return false;
        }
        Key key = new Key(harvest.playerId(), harvest.toolInstanceId());
        Long ready = readyUntil.get(key);
        if (ready != null && ready > nowTick) return false;
        if (ready != null) readyUntil.remove(key);
        int count = progress.merge(key, 1, Integer::sum);
        if (count < REQUIRED_HARVESTS) return false;
        progress.remove(key);
        readyUntil.put(key, Math.addExact(nowTick, READY_TICKS));
        return true;
    }

    public synchronized double claimRepair(Repair repair, int rank, long nowTick) {
        Objects.requireNonNull(repair);
        requireTick(nowTick);
        if (rank <= 0 || !repair.nativeResourceFullyConsumed()
            || invalidInstances.contains(repair.toolInstanceId())) return 0.0D;
        Key key = new Key(repair.playerId(), repair.toolInstanceId());
        Long expiry = readyUntil.get(key);
        if (expiry == null || expiry <= nowTick) {
            if (expiry != null) readyUntil.remove(key);
            return 0.0D;
        }
        readyUntil.remove(key);
        double coefficient = switch (rank) {
            case 1 -> 0.15D;
            case 2 -> 0.25D;
            default -> 0.35D;
        };
        return repair.nativeDurabilityRestored() * coefficient;
    }

    public synchronized void invalidateDuplicatedInstance(String toolInstanceId) {
        requireId(toolInstanceId, "toolInstanceId");
        invalidInstances.add(toolInstanceId);
        progress.keySet().removeIf(key -> key.toolInstanceId.equals(toolInstanceId));
        readyUntil.keySet().removeIf(key -> key.toolInstanceId.equals(toolInstanceId));
    }

    public synchronized void clearTransient(String playerId) {
        requireId(playerId, "playerId");
        progress.keySet().removeIf(key -> key.playerId.equals(playerId));
        readyUntil.keySet().removeIf(key -> key.playerId.equals(playerId));
    }

    public record Harvest(
        String playerId,
        String toolInstanceId,
        CanonicalActionIdentity action,
        boolean legitimate,
        boolean duplicatedInstanceDetected
    ) {
        public Harvest {
            requireId(playerId, "playerId");
            requireId(toolInstanceId, "toolInstanceId");
            Objects.requireNonNull(action);
        }
    }

    public record Repair(
        String playerId,
        String toolInstanceId,
        double nativeDurabilityRestored,
        boolean nativeResourceFullyConsumed
    ) {
        public Repair {
            requireId(playerId, "playerId");
            requireId(toolInstanceId, "toolInstanceId");
            if (!Double.isFinite(nativeDurabilityRestored) || nativeDurabilityRestored <= 0.0D) {
                throw new IllegalArgumentException("nativeDurabilityRestored must be positive");
            }
        }
    }

    private record Key(String playerId, String toolInstanceId) {}

    private static void requireId(String value, String field) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    private static void requireTick(long tick) {
        if (tick < 0L) throw new IllegalArgumentException("tick must be non-negative");
    }
}
