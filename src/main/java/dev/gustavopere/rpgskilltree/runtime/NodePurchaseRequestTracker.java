package dev.gustavopere.rpgskilltree.runtime;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

/** Bounded per-player replay window for client purchase request ids. */
public final class NodePurchaseRequestTracker {
    public enum Decision {
        NEW,
        REPLAY,
        CONFLICT
    }

    private final int maxRequestsPerPlayer;
    private final Map<UUID, LinkedHashMap<String, ResourceLocation>> requestsByPlayer = new HashMap<>();

    public NodePurchaseRequestTracker(int maxRequestsPerPlayer) {
        if (maxRequestsPerPlayer <= 0) {
            throw new IllegalArgumentException("maxRequestsPerPlayer must be positive");
        }
        this.maxRequestsPerPlayer = maxRequestsPerPlayer;
    }

    public synchronized Decision checkAndRecord(
        UUID playerId,
        String requestId,
        ResourceLocation nodeId
    ) {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(requestId, "requestId");
        Objects.requireNonNull(nodeId, "nodeId");
        if (requestId.isBlank()) throw new IllegalArgumentException("requestId must not be blank");

        LinkedHashMap<String, ResourceLocation> requests = requestsByPlayer.computeIfAbsent(
            playerId,
            ignored -> new LinkedHashMap<>()
        );
        ResourceLocation existing = requests.get(requestId);
        if (existing != null) {
            return existing.equals(nodeId) ? Decision.REPLAY : Decision.CONFLICT;
        }

        requests.put(requestId, nodeId);
        while (requests.size() > maxRequestsPerPlayer) {
            String oldest = requests.keySet().iterator().next();
            requests.remove(oldest);
        }
        return Decision.NEW;
    }

    public synchronized void clear(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        requestsByPlayer.remove(playerId);
    }

    public synchronized void clearAll() {
        requestsByPlayer.clear();
    }
}
