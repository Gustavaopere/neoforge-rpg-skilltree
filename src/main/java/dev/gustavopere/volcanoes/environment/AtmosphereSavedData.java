package dev.gustavopere.volcanoes.environment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

/** Persistent storage only; AtmosphereField remains the sole atmospheric runtime. */
public final class AtmosphereSavedData extends SavedData implements AtmosphericSourceLifecycleSink {
    static final int CURRENT_SCHEMA_VERSION = 1;

    private static final System.Logger LOGGER = System.getLogger(AtmosphereSavedData.class.getName());
    private static final Comparator<UUID> CANONICAL_UUID_ORDER = Comparator.comparing(UUID::toString);
    private static final String STORAGE_NAME = "volcanoes_atmosphere_sources";
    private static final String SCHEMA_VERSION = "schema_version";
    private static final String SOURCES = "sources";

    private final AtmospherePersistencePolicy policy;
    private final Map<UUID, AtmosphericSource> sources = new LinkedHashMap<>();
    private final CompoundTag preservedUnknownPayload;

    public AtmosphereSavedData(AtmospherePersistencePolicy policy) {
        this(policy, null);
    }

    private AtmosphereSavedData(AtmospherePersistencePolicy policy, CompoundTag preservedUnknownPayload) {
        this.policy = Objects.requireNonNull(policy, "policy");
        this.preservedUnknownPayload = preservedUnknownPayload;
    }

    public static AtmosphereSavedData get(ServerLevel level, AtmospherePersistencePolicy policy) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(policy, "policy");
        Factory<AtmosphereSavedData> factory = new Factory<>(
                () -> new AtmosphereSavedData(policy),
                (tag, registries) -> fromTag(tag, policy));
        return level.getDataStorage().computeIfAbsent(factory, STORAGE_NAME);
    }

    @Override
    public void upsert(AtmosphericSource source) {
        ensureWritable();
        Objects.requireNonNull(source, "source");
        if (!policy.shouldPersist(source)) {
            if (sources.remove(source.id()) != null) {
                setDirty();
            }
            return;
        }
        AtmosphericSource existing = sources.get(source.id());
        if (existing == null && sources.size() >= policy.maxSources()) {
            throw new IllegalStateException(
                    "Atmosphere persistent source limit reached: " + policy.maxSources());
        }
        if (source.equals(existing)) {
            return;
        }
        sources.put(source.id(), source);
        setDirty();
    }

    @Override
    public void remove(UUID id) {
        ensureWritable();
        Objects.requireNonNull(id, "id");
        if (sources.remove(id) != null) {
            setDirty();
        }
    }

    public Optional<AtmosphericSource> source(UUID id) {
        return Optional.ofNullable(sources.get(Objects.requireNonNull(id, "id")));
    }

    public int size() {
        return sources.size();
    }

    public List<AtmosphericSource> all() {
        return sources.values().stream()
                .sorted(Comparator.comparing(AtmosphericSource::id, CANONICAL_UUID_ORDER))
                .toList();
    }

    public CompoundTag toTag() {
        return writeTo(new CompoundTag());
    }

    public static AtmosphereSavedData fromTag(CompoundTag tag, AtmospherePersistencePolicy policy) {
        Objects.requireNonNull(tag, "tag");
        Objects.requireNonNull(policy, "policy");

        boolean hasSchemaVersion = tag.contains(SCHEMA_VERSION);
        if (hasSchemaVersion && !tag.contains(SCHEMA_VERSION, CompoundTag.TAG_INT)) {
            LOGGER.log(System.Logger.Level.WARNING,
                    "Atmosphere SavedData has an invalid schema_version NBT type; preserving payload read-only");
            return new AtmosphereSavedData(policy, tag.copy());
        }

        if (hasSchemaVersion) {
            int schemaVersion = tag.getInt(SCHEMA_VERSION);
            if (schemaVersion != CURRENT_SCHEMA_VERSION) {
                LOGGER.log(System.Logger.Level.WARNING,
                        "Atmosphere SavedData explicit schema {0} is unsupported by this build; preserving payload read-only",
                        schemaVersion);
                return new AtmosphereSavedData(policy, tag.copy());
            }
        }

        if (!tag.contains(SOURCES)) {
            LOGGER.log(System.Logger.Level.WARNING,
                    "Atmosphere SavedData is missing the required sources list; preserving payload read-only");
            return new AtmosphereSavedData(policy, tag.copy());
        }
        if (!tag.contains(SOURCES, CompoundTag.TAG_LIST)) {
            LOGGER.log(System.Logger.Level.WARNING,
                    "Atmosphere SavedData has an invalid sources NBT type; preserving payload read-only");
            return new AtmosphereSavedData(policy, tag.copy());
        }
        ListTag rawSources = (ListTag) tag.get(SOURCES);
        if (!rawSources.isEmpty() && rawSources.getElementType() != CompoundTag.TAG_COMPOUND) {
            LOGGER.log(System.Logger.Level.WARNING,
                    "Atmosphere SavedData sources list has a non-compound element type; preserving payload read-only");
            return new AtmosphereSavedData(policy, tag.copy());
        }

        AtmosphereSavedData data = new AtmosphereSavedData(policy);
        TreeMap<UUID, AtmosphericSource> accepted = new TreeMap<>(CANONICAL_UUID_ORDER);
        ListTag list = tag.getList(SOURCES, CompoundTag.TAG_COMPOUND);
        boolean normalized = !hasSchemaVersion;
        for (int index = 0; index < list.size(); index++) {
            AtmosphericSource source;
            try {
                source = AtmosphericSource.fromTag(list.getCompound(index));
            } catch (IllegalArgumentException | NullPointerException malformedPersistedSource) {
                LOGGER.log(System.Logger.Level.WARNING,
                        "Skipping malformed Atmosphere persisted source entry at index " + index,
                        malformedPersistedSource);
                normalized = true;
                continue;
            }
            if (!policy.shouldPersist(source)) {
                normalized = true;
                continue;
            }

            AtmosphericSource existing = accepted.get(source.id());
            if (existing != null) {
                normalized = true;
                if (!existing.equals(source)) {
                    LOGGER.log(System.Logger.Level.WARNING,
                            "Skipping conflicting duplicate Atmosphere source UUID {0}; preserving first accepted authority",
                            source.id());
                }
                continue;
            }

            if (accepted.size() < policy.maxSources()) {
                accepted.put(source.id(), source);
                continue;
            }

            normalized = true;
            UUID highestAcceptedId = accepted.lastKey();
            if (CANONICAL_UUID_ORDER.compare(source.id(), highestAcceptedId) < 0) {
                accepted.pollLastEntry();
                accepted.put(source.id(), source);
            }
        }
        data.sources.putAll(accepted);
        if (normalized) {
            data.setDirty();
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return writeTo(tag);
    }

    private void ensureWritable() {
        if (preservedUnknownPayload != null) {
            throw new IllegalStateException(
                    "Atmosphere SavedData uses an unsupported schema and is read-only to prevent downgrade data loss");
        }
    }

    private CompoundTag writeTo(CompoundTag tag) {
        if (preservedUnknownPayload != null) {
            return preservedUnknownPayload.copy();
        }
        tag.putInt(SCHEMA_VERSION, CURRENT_SCHEMA_VERSION);
        ListTag list = new ListTag();
        for (AtmosphericSource source : all()) {
            list.add(source.toTag());
        }
        tag.put(SOURCES, list);
        return tag;
    }
}
