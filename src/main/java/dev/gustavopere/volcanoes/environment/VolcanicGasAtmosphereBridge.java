package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.VolcanicGasEmission;
import dev.gustavopere.volcanoes.volcano.VolcanicGasEmissionLifecycleSink;
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

/** Bounded latest-wins bridge from Stage03 volcanic gas metadata into Atmosphere. */
public final class VolcanicGasAtmosphereBridge implements VolcanicGasEmissionLifecycleSink {
    private static final int DEFAULT_MAX_PENDING = 16_384;
    private final VolcanicGasAtmosphereProjectionPolicy policy;
    private final int maxPending;
    private final Map<UUID, VolcanicGasEmission> pending = new HashMap<>();
    private final ArrayDeque<UUID> retryOrder = new ArrayDeque<>();
    private final Map<ServerLevel, Set<UUID>> admittedByLevel = new WeakHashMap<>();

    public VolcanicGasAtmosphereBridge() {
        this(VolcanicGasAtmosphereProjectionPolicy.defaults(), DEFAULT_MAX_PENDING);
    }

    public VolcanicGasAtmosphereBridge(VolcanicGasAtmosphereProjectionPolicy policy, int maxPending) {
        this.policy = Objects.requireNonNull(policy, "policy");
        if (maxPending <= 0) throw new IllegalArgumentException("maxPending must be positive");
        this.maxPending = maxPending;
    }

    @Override
    public synchronized void upsert(VolcanicGasEmission emission) {
        Objects.requireNonNull(emission, "emission");
        UUID id = emission.sourceId();
        if (pending.containsKey(id)) {
            pending.put(id, emission);
            return;
        }
        if (pending.size() >= maxPending) return;
        pending.put(id, emission);
        retryOrder.addLast(id);
    }

    @Override
    public void remove(UUID id) {
        Objects.requireNonNull(id, "id");
        List<ServerLevel> levels = new ArrayList<>();
        synchronized (this) {
            pending.remove(id);
            retryOrder.removeIf(id::equals);
            for (Map.Entry<ServerLevel, Set<UUID>> entry : admittedByLevel.entrySet()) {
                if (entry.getValue().remove(id)) levels.add(entry.getKey());
            }
        }
        for (ServerLevel level : levels) {
            if (level != null) AtmosphereRuntime.sourceSinkFor(level).remove(id);
        }
    }

    public int flush(ServerLevel level, int budget) {
        Objects.requireNonNull(level, "level");
        if (budget <= 0) return 0;
        AtmosphericSourceSink sink = AtmosphereRuntime.sourceSinkFor(level);
        String dimensionId = level.dimension().location().toString();
        int attempts = 0;
        while (attempts < budget) {
            UUID id;
            VolcanicGasEmission emission;
            synchronized (this) {
                id = retryOrder.pollFirst();
                if (id == null) break;
                emission = pending.get(id);
                if (emission == null) continue;
            }
            attempts++;
            AtmosphericSourceAdmission admission;
            try {
                admission = sink.tryUpsert(VolcanicGasAtmosphereProjection.project(dimensionId, emission, policy));
            } catch (IllegalArgumentException malformed) {
                synchronized (this) { pending.remove(id, emission); }
                continue;
            }
            synchronized (this) {
                VolcanicGasEmission latest = pending.get(id);
                if (latest == null) continue;
                if (admission == AtmosphericSourceAdmission.ACCEPTED && latest.equals(emission)) {
                    pending.remove(id);
                    admittedByLevel.computeIfAbsent(level, ignored -> new HashSet<>()).add(id);
                } else {
                    retryOrder.addLast(id);
                }
            }
        }
        return attempts;
    }

    public synchronized int pendingCount() { return pending.size(); }
    public int maxPending() { return maxPending; }
    synchronized void forgetLevel(ServerLevel level) { admittedByLevel.remove(Objects.requireNonNull(level, "level")); }
}
