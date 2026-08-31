package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Per-level persistent store for geological deposits.
 *
 * <p>Deposits are keyed by their stable persistence ID, making repeated chunk/worldgen discovery
 * idempotent. A reused ID with different content is treated as a conflict and fails closed instead
 * of silently rewriting geological history, except for the one reviewed upgrade path from the
 * legacy generic hydrothermal mineral identity to an exact canonical copper/iron/gold identity
 * with otherwise identical deposit content. Lifecycle sinks are transient observers only: they are
 * replayed from the authoritative SavedData after registration and can never veto core state.</p>
 */
public final class DepositRegistry extends SavedData implements GeologicalDepositSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepositRegistry.class);
    private static final String STORAGE_NAME = "volcanoes_geological_deposits";
    private static final String SCHEMA_VERSION = "schema_version";
    private static final int LEGACY_SCHEMA_VERSION = 1;
    private static final int CURRENT_SCHEMA_VERSION = 2;
    private static final String DEPOSITS = "deposits";
    private static final Factory<DepositRegistry> FACTORY =
            new Factory<>(DepositRegistry::new, DepositRegistry::load);

    private final Map<UUID, GeologicalDeposit> deposits = new LinkedHashMap<>();
    private final Set<DepositLifecycleSink> lifecycleSinks = new LinkedHashSet<>();
    private boolean writable = true;

    public static DepositRegistry get(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        return level.getDataStorage().computeIfAbsent(FACTORY, STORAGE_NAME);
    }

    public boolean register(GeologicalDeposit deposit) {
        Objects.requireNonNull(deposit, "deposit");
        if (!writable) {
            return false;
        }
        GeologicalDeposit existing = deposits.get(deposit.persistenceId());
        if (existing != null) {
            if (existing.equals(deposit)) {
                return false;
            }
            if (isLegacyHydrothermalIdentityUpgrade(existing, deposit)) {
                deposits.put(deposit.persistenceId(), deposit);
                setDirty();
                dispatchUpsert(deposit);
                return true;
            }
            throw new IllegalStateException(
                    "Conflicting geological deposit for persistence ID " + deposit.persistenceId());
        }

        deposits.put(deposit.persistenceId(), deposit);
        setDirty();
        dispatchUpsert(deposit);
        return true;
    }

    public boolean remove(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        if (!writable) {
            return false;
        }
        GeologicalDeposit removed = deposits.remove(persistenceId);
        if (removed == null) {
            return false;
        }
        setDirty();
        dispatchRemove(persistenceId);
        return true;
    }

    public Optional<GeologicalDeposit> get(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        return Optional.ofNullable(deposits.get(persistenceId));
    }

    public int size() {
        return deposits.size();
    }

    @Override
    public List<GeologicalDeposit> all() {
        return deposits.values().stream()
                .sorted(Comparator.comparing(deposit -> deposit.persistenceId().toString()))
                .toList();
    }

    @Override
    public List<GeologicalDeposit> nearby(BlockPos center, double radius) {
        Objects.requireNonNull(center, "center");
        if (!Double.isFinite(radius) || radius < 0.0) {
            throw new IllegalArgumentException("radius must be finite and non-negative");
        }
        double radiusSquared = radius * radius;
        return deposits.values().stream()
                .filter(deposit -> distanceSquared(center, deposit.center()) <= radiusSquared)
                .sorted(Comparator
                        .comparingDouble((GeologicalDeposit deposit) -> distanceSquared(center, deposit.center()))
                        .thenComparing(deposit -> deposit.persistenceId().toString()))
                .toList();
    }

    /**
     * Registers a transient observer and immediately replays the authoritative current snapshot in
     * deterministic persistence-ID order. Observer failures are isolated from geological state.
     */
    public boolean registerLifecycleSink(DepositLifecycleSink sink) {
        Objects.requireNonNull(sink, "sink");
        if (!lifecycleSinks.add(sink)) {
            return false;
        }
        for (GeologicalDeposit deposit : all()) {
            dispatchUpsert(sink, deposit);
        }
        return true;
    }

    public boolean unregisterLifecycleSink(DepositLifecycleSink sink) {
        Objects.requireNonNull(sink, "sink");
        return lifecycleSinks.remove(sink);
    }

    public CompoundTag toTag() {
        return writeTo(new CompoundTag());
    }

    public static DepositRegistry fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        DepositRegistry registry = new DepositRegistry();
        int schemaVersion = readSchemaVersion(tag);
        if (schemaVersion > CURRENT_SCHEMA_VERSION) {
            LOGGER.error(
                    "Cannot read geological deposit SavedData schema {} (current {}). Loading fail-closed/read-only; no saved entries will be overwritten.",
                    schemaVersion,
                    CURRENT_SCHEMA_VERSION);
            registry.writable = false;
            return registry;
        }
        if (schemaVersion < LEGACY_SCHEMA_VERSION) {
            LOGGER.warn("Invalid geological deposit SavedData schema {}. Treating payload as legacy v1.", schemaVersion);
        }

        ListTag list = tag.getList(DEPOSITS, CompoundTag.TAG_COMPOUND);
        for (int index = 0; index < list.size(); index++) {
            try {
                GeologicalDeposit deposit = GeologicalDeposit.fromTag(list.getCompound(index));
                registry.putLoaded(deposit);
            } catch (RuntimeException exception) {
                LOGGER.warn(
                        "Skipping corrupt geological deposit entry at index {} while loading schema {}: {}",
                        index,
                        schemaVersion,
                        exception.getMessage());
            }
        }
        return registry;
    }

    private static int readSchemaVersion(CompoundTag tag) {
        return tag.contains(SCHEMA_VERSION, CompoundTag.TAG_INT)
                ? tag.getInt(SCHEMA_VERSION)
                : LEGACY_SCHEMA_VERSION;
    }

    private static DepositRegistry load(CompoundTag tag, HolderLookup.Provider registries) {
        return fromTag(tag);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return writeTo(tag);
    }

    private CompoundTag writeTo(CompoundTag tag) {
        tag.putInt(SCHEMA_VERSION, CURRENT_SCHEMA_VERSION);
        ListTag list = new ListTag();
        for (GeologicalDeposit deposit : all()) {
            list.add(deposit.toTag());
        }
        tag.put(DEPOSITS, list);
        return tag;
    }

    private void putLoaded(GeologicalDeposit deposit) {
        GeologicalDeposit existing = deposits.putIfAbsent(deposit.persistenceId(), deposit);
        if (existing != null && !existing.equals(deposit)) {
            throw new IllegalArgumentException(
                    "Conflicting geological deposits in saved data for persistence ID "
                            + deposit.persistenceId());
        }
    }

    private static boolean isLegacyHydrothermalIdentityUpgrade(
            GeologicalDeposit existing,
            GeologicalDeposit incoming
    ) {
        if (existing.origin() != DepositOrigin.HYDROTHERMAL
                || incoming.origin() != DepositOrigin.HYDROTHERMAL) {
            return false;
        }
        if (!existing.resourceTag().equals(GeologyResourceTags.MINERAL_RESOURCES.location())
                || !isCanonicalHydrothermalMetal(incoming.resourceTag())) {
            return false;
        }
        return existing.center().equals(incoming.center())
                && Double.compare(existing.radius(), incoming.radius()) == 0
                && Double.compare(existing.richness(), incoming.richness()) == 0;
    }

    private static boolean isCanonicalHydrothermalMetal(ResourceLocation resourceTag) {
        return resourceTag.equals(GeologyResourceTags.COPPER_ORES.location())
                || resourceTag.equals(GeologyResourceTags.IRON_ORES.location())
                || resourceTag.equals(GeologyResourceTags.GOLD_ORES.location());
    }

    private void dispatchUpsert(GeologicalDeposit deposit) {
        for (DepositLifecycleSink sink : List.copyOf(lifecycleSinks)) {
            dispatchUpsert(sink, deposit);
        }
    }

    private static void dispatchUpsert(DepositLifecycleSink sink, GeologicalDeposit deposit) {
        try {
            sink.upsert(deposit);
        } catch (RuntimeException | LinkageError ignored) {
            // Optional/integration observers never become geological authority.
        }
    }

    private void dispatchRemove(UUID persistenceId) {
        for (DepositLifecycleSink sink : List.copyOf(lifecycleSinks)) {
            try {
                sink.remove(persistenceId);
            } catch (RuntimeException | LinkageError ignored) {
                // Optional/integration observers never become geological authority.
            }
        }
    }

    private static double distanceSquared(BlockPos first, BlockPos second) {
        double dx = (double) first.getX() - second.getX();
        double dy = (double) first.getY() - second.getY();
        double dz = (double) first.getZ() - second.getZ();
        return dx * dx + dy * dy + dz * dz;
    }
}
