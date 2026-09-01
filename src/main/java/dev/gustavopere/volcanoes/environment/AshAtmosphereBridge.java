package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshEmissionLifecycleSink;
import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Fail-isolated lifecycle bridge from Stage-03 ash authority into the bounded Atmosphere index.
 * Pending state is latest-wins per stable upstream UUID and retries rotate so a saturated source
 * cannot permanently starve later sources.
 */
public final class AshAtmosphereBridge implements AshEmissionLifecycleSink {
    private static final int DEFAULT_MAX_PENDING = 16_384;

    private final AshAtmosphereProjectionPolicy policy;
    private final int maxPending;
    private final Map<UUID, AshPlumeEmission> pending = new HashMap<>();
    private final ArrayDeque<UUID> retryOrder = new ArrayDeque<>();
    private final Map<ServerLevel, Set<UUID>> admittedByLevel = new WeakHashMap<>();

    public AshAtmosphereBridge() {
        this(AshAtmosphereProjectionPolicy.defaults(), DEFAULT_MAX_PENDING);
    }

    public AshAtmosphereBridge(AshAtmosphereProjectionPolicy policy, int maxPending) {
        this.policy = Objects.requireNonNull(policy, "policy");
        if (maxPending <= 0) {
            throw new IllegalArgumentException("maxPending must be positive");
        }
        this.maxPending = maxPending;
    }

    @Override
    public synchronized void upsert(AshPlumeEmission emission) {
        Objects.requireNonNull(emission, "emission");
        UUID id = emission.sourceId();
        if (!emission.active()) {
            remove(id);
            return;
        }
        if (pending.containsKey(id)) {
            pending.put(id, emission);
            return;
        }
        if (pending.size() >= maxPending) {
            // Fail closed under a pathological upstream cardinality breach: do not exceed the
            // declared retry bound. Existing authoritative retries remain intact.
            return;
        }
        pending.put(id, emission);
        retryOrder.addLast(id);
    }

    @Override
    public void remove(UUID id) {
        Objects.requireNonNull(id, "id");
        List<ServerLevel> levels;
        synchronized (this) {
            pending.remove(id);
            retryOrder.removeIf(id::equals);
            levels = new ArrayList<>();
            for (Map.Entry<ServerLevel, Set<UUID>> entry : admittedByLevel.entrySet()) {
                if (entry.getValue().remove(id)) {
                    levels.add(entry.getKey());
                }
            }
        }
        for (ServerLevel level : levels) {
            if (level != null) {
                AtmosphereRuntime.sourceSinkFor(level).remove(id);
            }
        }
    }

    /** Attempts at most {@code budget} pending projections for this server level. */
    public int flush(ServerLevel level, int budget) {
        Objects.requireNonNull(level, "level");
        if (budget <= 0) {
            return 0;
        }
        AtmosphericSourceSink sink = AtmosphereRuntime.sourceSinkFor(level);
        String dimensionId = level.dimension().location().toString();
        int attempts = 0;
        while (attempts < budget) {
            UUID id;
            AshPlumeEmission emission;
            synchronized (this) {
                id = retryOrder.pollFirst();
                if (id == null) {
                    break;
                }
                emission = pending.get(id);
                if (emission == null) {
                    continue;
                }
            }

            attempts++;
            AtmosphericSourceAdmission admission;
            try {
                admission = sink.tryUpsert(AshAtmosphereProjection.project(dimensionId, emission, policy));
            } catch (IllegalArgumentException malformedProjection) {
                // A malformed mapping is not capacity pressure and must not become an immortal retry.
                synchronized (this) {
                    pending.remove(id, emission);
                }
                continue;
            }

            synchronized (this) {
                AshPlumeEmission latest = pending.get(id);
                if (latest == null) {
                    continue;
                }
                if (admission == AtmosphericSourceAdmission.ACCEPTED && latest.equals(emission)) {
                    pending.remove(id);
                    admittedByLevel.computeIfAbsent(level, ignored -> new HashSet<>()).add(id);
                } else {
                    // Capacity rejection, or a concurrent latest-wins replacement, rotates to tail.
                    retryOrder.addLast(id);
                }
            }
        }
        return attempts;
    }

    public synchronized int pendingCount() {
        return pending.size();
    }

    public int maxPending() {
        return maxPending;
    }

    synchronized void forgetLevel(ServerLevel level) {
        admittedByLevel.remove(Objects.requireNonNull(level, "level"));
    }
}
