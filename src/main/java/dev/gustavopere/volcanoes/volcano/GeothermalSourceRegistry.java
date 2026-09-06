package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Bounded persistent authority for generated geothermal surface expressions.
 *
 * <p>The registry owns only stable source metadata. Optional consumers observe it through transient
 * lifecycle sinks; sink failures are isolated and never veto persistent source state.</p>
 */
public final class GeothermalSourceRegistry extends SavedData {
    static final int DEFAULT_MAX_SOURCES = 16_384;
    private static final String STORAGE_NAME = "volcanoes_geothermal_sources";
    private static final String SOURCES = "sources";
    private static final Factory<GeothermalSourceRegistry> FACTORY =
            new Factory<>(GeothermalSourceRegistry::new, GeothermalSourceRegistry::load);

    private final int maxSources;
    private final Map<UUID, GeothermalSource> sources = new LinkedHashMap<>();
    private final Set<GeothermalSourceLifecycleSink> lifecycleSinks = new LinkedHashSet<>();

    public GeothermalSourceRegistry() {
        this(DEFAULT_MAX_SOURCES);
    }

    public GeothermalSourceRegistry(int maxSources) {
        if (maxSources <= 0) {
            throw new IllegalArgumentException("maxSources must be positive");
        }
        this.maxSources = maxSources;
    }

    public static GeothermalSourceRegistry get(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        return level.getDataStorage().computeIfAbsent(FACTORY, STORAGE_NAME);
    }

    public boolean register(GeothermalSource source) {
        Objects.requireNonNull(source, "source");
        GeothermalSource existing = sources.get(source.persistenceId());
        if (existing != null) {
            if (existing.equals(source)) {
                return false;
            }
            throw new IllegalStateException(
                    "Conflicting geothermal source for persistence ID " + source.persistenceId());
        }
        if (sources.size() >= maxSources) {
            return false;
        }

        sources.put(source.persistenceId(), source);
        setDirty();
        dispatchUpsert(source);
        return true;
    }

    public boolean remove(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        GeothermalSource removed = sources.remove(persistenceId);
        if (removed == null) {
            return false;
        }
        setDirty();
        dispatchRemove(persistenceId);
        return true;
    }

    public Optional<GeothermalSource> get(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        return Optional.ofNullable(sources.get(persistenceId));
    }

    public int size() {
        return sources.size();
    }

    public List<GeothermalSource> all() {
        return sources.values().stream()
                .sorted(Comparator.comparing(source -> source.persistenceId().toString()))
                .toList();
    }

    public boolean registerLifecycleSink(GeothermalSourceLifecycleSink sink) {
        Objects.requireNonNull(sink, "sink");
        if (!lifecycleSinks.add(sink)) {
            return false;
        }
        for (GeothermalSource source : all()) {
            dispatchUpsert(sink, source);
        }
        return true;
    }

    public boolean unregisterLifecycleSink(GeothermalSourceLifecycleSink sink) {
        Objects.requireNonNull(sink, "sink");
        return lifecycleSinks.remove(sink);
    }

    public CompoundTag toTag() {
        return writeTo(new CompoundTag());
    }

    public static GeothermalSourceRegistry fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        GeothermalSourceRegistry registry = new GeothermalSourceRegistry();
        ListTag list = tag.getList(SOURCES, CompoundTag.TAG_COMPOUND);
        if (list.size() > registry.maxSources) {
            throw new IllegalArgumentException("saved geothermal source count exceeds configured capacity");
        }
        for (int index = 0; index < list.size(); index++) {
            registry.putLoaded(GeothermalSource.fromTag(list.getCompound(index)));
        }
        return registry;
    }

    private static GeothermalSourceRegistry load(CompoundTag tag, HolderLookup.Provider registries) {
        return fromTag(tag);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return writeTo(tag);
    }

    private CompoundTag writeTo(CompoundTag tag) {
        ListTag list = new ListTag();
        for (GeothermalSource source : all()) {
            list.add(source.toTag());
        }
        tag.put(SOURCES, list);
        return tag;
    }

    private void putLoaded(GeothermalSource source) {
        if (sources.size() >= maxSources) {
            throw new IllegalArgumentException("saved geothermal source count exceeds configured capacity");
        }
        GeothermalSource existing = sources.putIfAbsent(source.persistenceId(), source);
        if (existing != null && !existing.equals(source)) {
            throw new IllegalArgumentException(
                    "Conflicting geothermal sources in saved data for persistence ID "
                            + source.persistenceId());
        }
    }

    private void dispatchUpsert(GeothermalSource source) {
        for (GeothermalSourceLifecycleSink sink : List.copyOf(lifecycleSinks)) {
            dispatchUpsert(sink, source);
        }
    }

    private static void dispatchUpsert(GeothermalSourceLifecycleSink sink, GeothermalSource source) {
        try {
            sink.upsert(source);
        } catch (RuntimeException | LinkageError ignored) {
            // Optional observers never become geothermal authority.
        }
    }

    private void dispatchRemove(UUID persistenceId) {
        for (GeothermalSourceLifecycleSink sink : List.copyOf(lifecycleSinks)) {
            try {
                sink.remove(persistenceId);
            } catch (RuntimeException | LinkageError ignored) {
                // Optional observers never become geothermal authority.
            }
        }
    }
}
