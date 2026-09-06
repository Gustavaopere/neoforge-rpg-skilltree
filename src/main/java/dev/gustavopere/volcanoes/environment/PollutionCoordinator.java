package dev.gustavopere.volcanoes.environment;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public final class PollutionCoordinator {
    private static final int DEFAULT_MAX_REMEMBERED_EMISSION_IDS = 4_096;

    private final PollutionAdapter adapter;
    private final int maxRememberedEmissionIds;
    private final LinkedHashSet<UUID> routedEmissionIds = new LinkedHashSet<>();

    public PollutionCoordinator(PollutionAdapter adapter) {
        this(adapter, DEFAULT_MAX_REMEMBERED_EMISSION_IDS);
    }

    public PollutionCoordinator(PollutionAdapter adapter, int maxRememberedEmissionIds) {
        this.adapter = Objects.requireNonNull(adapter, "adapter");
        if (maxRememberedEmissionIds <= 0) {
            throw new IllegalArgumentException("maxRememberedEmissionIds must be positive");
        }
        this.maxRememberedEmissionIds = maxRememberedEmissionIds;
    }

    public PollutionRoute route(PollutionEmission emission, Consumer<PollutionEmission> internalFallback) {
        Objects.requireNonNull(emission, "emission");
        Objects.requireNonNull(internalFallback, "internalFallback");
        if (!remember(emission.id())) {
            return PollutionRoute.DUPLICATE;
        }
        try {
            if (adapter.isAuthoritative()) {
                adapter.publish(emission);
                return PollutionRoute.EXTERNAL_AUTHORITY;
            }
            internalFallback.accept(emission);
            return PollutionRoute.INTERNAL_FALLBACK;
        } catch (RuntimeException | Error failure) {
            forget(emission.id());
            throw failure;
        }
    }

    public PollutionLoad sampleExternalOnly(String dimensionId, double x, double y, double z) {
        if (!adapter.supportsExternalReadback()) {
            return PollutionLoad.none();
        }
        return adapter.sampleExternalOnly(dimensionId, x, y, z).orElse(PollutionLoad.none());
    }

    public void forget(UUID emissionId) {
        UUID id = Objects.requireNonNull(emissionId, "emissionId");
        synchronized (routedEmissionIds) {
            routedEmissionIds.remove(id);
        }
    }

    int rememberedEmissionCount() {
        synchronized (routedEmissionIds) {
            return routedEmissionIds.size();
        }
    }

    private boolean remember(UUID emissionId) {
        synchronized (routedEmissionIds) {
            if (!routedEmissionIds.add(emissionId)) {
                return false;
            }
            if (routedEmissionIds.size() > maxRememberedEmissionIds) {
                var iterator = routedEmissionIds.iterator();
                iterator.next();
                iterator.remove();
            }
            return true;
        }
    }
}
