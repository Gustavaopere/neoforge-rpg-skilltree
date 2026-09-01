package dev.gustavopere.volcanoes.tectonics;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Per-level SavedData containing only coarse regional tectonic stress. */
public final class TectonicRegionState extends SavedData {
    private static final Logger LOGGER = LoggerFactory.getLogger(TectonicRegionState.class);
    private static final String STORAGE_NAME = "volcanoes_tectonic_stress";
    private static final String SCHEMA_VERSION = "schema_version";
    private static final int LEGACY_SCHEMA_VERSION = 1;
    private static final int CURRENT_SCHEMA_VERSION = 2;
    private static final String REGIONS = "regions";
    private static final String REGION_X = "region_x";
    private static final String REGION_Z = "region_z";
    private static final String STRESS = "stress";
    private static final Factory<TectonicRegionState> FACTORY =
            new Factory<>(TectonicRegionState::new, TectonicRegionState::load);

    private final Map<RegionKey, Double> stressByRegion = new LinkedHashMap<>();
    private final CompoundTag preservedPayload;
    private boolean writable;

    public TectonicRegionState() {
        this(null);
    }

    private TectonicRegionState(CompoundTag preservedPayload) {
        this.preservedPayload = preservedPayload;
        this.writable = preservedPayload == null;
    }

    public static TectonicRegionState get(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        return level.getDataStorage().computeIfAbsent(FACTORY, STORAGE_NAME);
    }

    public boolean putStress(long regionX, long regionZ, double stress) {
        validateStress(stress);
        if (!writable) {
            return false;
        }
        RegionKey key = new RegionKey(regionX, regionZ);
        Double existing = stressByRegion.get(key);
        if (existing != null && Double.compare(existing, stress) == 0) {
            return false;
        }
        stressByRegion.put(key, stress);
        setDirty();
        return true;
    }

    public boolean contains(long regionX, long regionZ) {
        return stressByRegion.containsKey(new RegionKey(regionX, regionZ));
    }

    public double stressAt(long regionX, long regionZ) {
        return stressByRegion.getOrDefault(new RegionKey(regionX, regionZ), 0.0);
    }

    public int size() {
        return stressByRegion.size();
    }

    public List<RegionStress> entries() {
        List<RegionStress> result = new ArrayList<>(stressByRegion.size());
        stressByRegion.forEach((key, stress) -> result.add(new RegionStress(key.x(), key.z(), stress)));
        result.sort(Comparator
                .comparingLong(RegionStress::regionX)
                .thenComparingLong(RegionStress::regionZ));
        return List.copyOf(result);
    }

    public CompoundTag toTag() {
        return writeTo(new CompoundTag());
    }

    public static TectonicRegionState fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        int schemaVersion = readSchemaVersion(tag);
        if (schemaVersion > CURRENT_SCHEMA_VERSION) {
            LOGGER.error(
                    "Cannot read tectonic stress SavedData schema {} (current {}). Preserving payload fail-closed/read-only; no saved entries will be overwritten.",
                    schemaVersion,
                    CURRENT_SCHEMA_VERSION);
            return preserveReadOnly(tag);
        }
        if (schemaVersion < LEGACY_SCHEMA_VERSION) {
            LOGGER.warn("Invalid tectonic stress SavedData schema {}. Treating payload as legacy v1.", schemaVersion);
        }

        if (tag.contains(REGIONS) && !tag.contains(REGIONS, CompoundTag.TAG_LIST)) {
            LOGGER.error(
                    "Tectonic stress SavedData has an invalid regions NBT type. Preserving payload fail-closed/read-only.");
            return preserveReadOnly(tag);
        }
        if (tag.contains(REGIONS, CompoundTag.TAG_LIST)) {
            ListTag rawRegions = (ListTag) tag.get(REGIONS);
            if (!rawRegions.isEmpty() && rawRegions.getElementType() != CompoundTag.TAG_COMPOUND) {
                LOGGER.error(
                        "Tectonic stress SavedData regions list has a non-compound element type. Preserving payload fail-closed/read-only.");
                return preserveReadOnly(tag);
            }
        }

        TectonicRegionState state = new TectonicRegionState();
        ListTag regions = tag.getList(REGIONS, CompoundTag.TAG_COMPOUND);
        for (int index = 0; index < regions.size(); index++) {
            CompoundTag region = regions.getCompound(index);
            try {
                state.putLoaded(
                        region.getLong(REGION_X),
                        region.getLong(REGION_Z),
                        region.getDouble(STRESS));
            } catch (RuntimeException exception) {
                LOGGER.warn(
                        "Skipping corrupt tectonic stress entry at index {} while loading schema {}: {}",
                        index,
                        schemaVersion,
                        exception.getMessage());
            }
        }
        if (schemaVersion < CURRENT_SCHEMA_VERSION) {
            state.setDirty();
        }
        return state;
    }

    private static int readSchemaVersion(CompoundTag tag) {
        return tag.contains(SCHEMA_VERSION, CompoundTag.TAG_INT)
                ? tag.getInt(SCHEMA_VERSION)
                : LEGACY_SCHEMA_VERSION;
    }

    private static TectonicRegionState preserveReadOnly(CompoundTag tag) {
        return new TectonicRegionState(tag.copy());
    }

    private static TectonicRegionState load(CompoundTag tag, HolderLookup.Provider registries) {
        return fromTag(tag);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return writeTo(tag);
    }

    private CompoundTag writeTo(CompoundTag tag) {
        if (preservedPayload != null) {
            return preservedPayload.copy();
        }
        tag.putInt(SCHEMA_VERSION, CURRENT_SCHEMA_VERSION);
        ListTag regions = new ListTag();
        for (RegionStress entry : entries()) {
            CompoundTag region = new CompoundTag();
            region.putLong(REGION_X, entry.regionX());
            region.putLong(REGION_Z, entry.regionZ());
            region.putDouble(STRESS, entry.stress());
            regions.add(region);
        }
        tag.put(REGIONS, regions);
        return tag;
    }

    private void putLoaded(long regionX, long regionZ, double stress) {
        validateStress(stress);
        RegionKey key = new RegionKey(regionX, regionZ);
        Double existing = stressByRegion.putIfAbsent(key, stress);
        if (existing != null && Double.compare(existing, stress) != 0) {
            throw new IllegalArgumentException(
                    "Conflicting tectonic stress entries for region " + regionX + "," + regionZ);
        }
    }

    private static void validateStress(double stress) {
        if (!Double.isFinite(stress) || stress < 0.0 || stress > 1.0) {
            throw new IllegalArgumentException("stress must be within [0, 1]");
        }
    }

    public record RegionStress(long regionX, long regionZ, double stress) {
        public RegionStress {
            validateStress(stress);
        }
    }

    private record RegionKey(long x, long z) {
    }
}
