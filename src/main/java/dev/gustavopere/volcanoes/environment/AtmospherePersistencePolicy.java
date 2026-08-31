package dev.gustavopere.volcanoes.environment;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public record AtmospherePersistencePolicy(boolean enabled, int maxSources) {
    public AtmospherePersistencePolicy {
        if (maxSources <= 0) {
            throw new IllegalArgumentException("maxSources must be positive");
        }
        if (maxSources > AtmosphericSourceIndex.DEFAULT_MAX_SOURCES) {
            throw new IllegalArgumentException(
                    "maxSources must not exceed active Atmosphere source capacity "
                            + AtmosphericSourceIndex.DEFAULT_MAX_SOURCES);
        }
    }

    public boolean shouldPersist(AtmosphericSource source) {
        return enabled && Objects.requireNonNull(source, "source").persistent();
    }

    public List<AtmosphericSource> selectForPersistence(Collection<AtmosphericSource> sources) {
        if (!enabled) {
            return List.of();
        }
        return Objects.requireNonNull(sources, "sources").stream()
                .filter(this::shouldPersist)
                .sorted(Comparator.comparing(source -> source.id().toString()))
                .limit(maxSources)
                .toList();
    }
}
