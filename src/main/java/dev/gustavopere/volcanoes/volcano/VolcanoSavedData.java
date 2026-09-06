package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Per-level persistent registry for physical volcano sites and their coarse magma lifecycle. */
public final class VolcanoSavedData extends SavedData implements VolcanicRegionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VolcanoSavedData.class);
    private static final String STORAGE_NAME = "volcanoes_sites";
    private static final String SITES_SCHEMA_VERSION = "sites_schema_version";
    private static final String CHAMBERS_SCHEMA_VERSION = "chambers_schema_version";
    private static final int LEGACY_SCHEMA_VERSION = 1;
    private static final int CURRENT_SCHEMA_VERSION = 2;
    private static final String SITES = "sites";
    private static final String MAGMA_CHAMBER = "magma_chamber";
    private static final String ERUPTION = "eruption";
    private static final Factory<VolcanoSavedData> FACTORY =
            new Factory<>(VolcanoSavedData::new, VolcanoSavedData::load);

    private final Map<UUID, VolcanoSite> sites = new LinkedHashMap<>();
    private final Map<UUID, MagmaChamber> chambers = new LinkedHashMap<>();
    private final Map<UUID, EruptionEvent> eruptions = new LinkedHashMap<>();
    private boolean writable = true;

    public static VolcanoSavedData get(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        return level.getDataStorage().computeIfAbsent(FACTORY, STORAGE_NAME);
    }

    public boolean register(VolcanoSite site) {
        Objects.requireNonNull(site, "site");
        if (!writable) {
            return false;
        }
        VolcanoSite existing = sites.get(site.persistenceId());
        if (existing != null) {
            if (existing.equals(site)) {
                return false;
            }
            throw new IllegalStateException(
                    "Conflicting volcano site for persistence ID " + site.persistenceId());
        }
        sites.put(site.persistenceId(), site);
        setDirty();
        return true;
    }

    public Optional<VolcanoSite> get(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        return Optional.ofNullable(sites.get(persistenceId));
    }

    public Optional<MagmaChamber> chamber(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        return Optional.ofNullable(chambers.get(persistenceId));
    }

    public Optional<EruptionEvent> eruption(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        return Optional.ofNullable(eruptions.get(persistenceId));
    }

    public boolean updateLifecycle(UUID persistenceId, VolcanoState state, MagmaChamber chamber) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(chamber, "chamber");
        if (!writable) {
            return false;
        }
        VolcanoSite existing = sites.get(persistenceId);
        if (existing == null) {
            throw new IllegalArgumentException("Unknown volcano site " + persistenceId);
        }
        MagmaChamber existingChamber = chambers.get(persistenceId);
        if (existing.state() == state && chamber.equals(existingChamber)) {
            return false;
        }
        if (existing.state() != state) {
            sites.put(persistenceId, withState(existing, state));
        }
        chambers.put(persistenceId, chamber);
        setDirty();
        return true;
    }

    public boolean updateEruption(EruptionEvent event) {
        Objects.requireNonNull(event, "event");
        if (!writable) {
            return false;
        }
        UUID persistenceId = event.volcanoId();
        if (!sites.containsKey(persistenceId)) {
            throw new IllegalArgumentException("Unknown volcano site " + persistenceId);
        }
        EruptionEvent existing = eruptions.get(persistenceId);
        if (event.equals(existing)) {
            return false;
        }
        eruptions.put(persistenceId, event);
        setDirty();
        return true;
    }

    public boolean clearEruption(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        if (!writable) {
            return false;
        }
        if (eruptions.remove(persistenceId) == null) {
            return false;
        }
        setDirty();
        return true;
    }

    public int size() {
        return sites.size();
    }

    @Override
    public List<VolcanoSite> all() {
        return sites.values().stream()
                .sorted(Comparator.comparing(site -> site.persistenceId().toString()))
                .toList();
    }

    @Override
    public List<VolcanoSite> nearby(BlockPos center, double radius) {
        Objects.requireNonNull(center, "center");
        if (!Double.isFinite(radius) || radius < 0.0) {
            throw new IllegalArgumentException("radius must be finite and non-negative");
        }
        double radiusSquared = radius * radius;
        return sites.values().stream()
                .filter(site -> horizontalDistanceSquared(center, site.center()) <= radiusSquared)
                .sorted(Comparator
                        .comparingDouble((VolcanoSite site) -> horizontalDistanceSquared(center, site.center()))
                        .thenComparing(site -> site.persistenceId().toString()))
                .toList();
    }

    public CompoundTag toTag() {
        return writeTo(new CompoundTag());
    }

    public static VolcanoSavedData fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        VolcanoSavedData data = new VolcanoSavedData();
        int sitesSchemaVersion = readSchemaVersion(tag, SITES_SCHEMA_VERSION);
        int chambersSchemaVersion = readSchemaVersion(tag, CHAMBERS_SCHEMA_VERSION);
        if (sitesSchemaVersion > CURRENT_SCHEMA_VERSION || chambersSchemaVersion > CURRENT_SCHEMA_VERSION) {
            LOGGER.error(
                    "Cannot read volcano SavedData schemas sites={} chambers={} (current {}). Loading fail-closed/read-only; no saved entries will be overwritten.",
                    sitesSchemaVersion,
                    chambersSchemaVersion,
                    CURRENT_SCHEMA_VERSION);
            data.writable = false;
            return data;
        }

        ListTag list = tag.getList(SITES, CompoundTag.TAG_COMPOUND);
        for (int index = 0; index < list.size(); index++) {
            CompoundTag siteTag = list.getCompound(index);
            VolcanoSite site;
            try {
                site = VolcanoSite.fromTag(siteTag);
            } catch (RuntimeException exception) {
                LOGGER.warn(
                        "Skipping corrupt volcano site entry at index {} while loading sites schema {}: {}",
                        index,
                        sitesSchemaVersion,
                        exception.getMessage());
                continue;
            }

            MagmaChamber chamber = null;
            if (siteTag.contains(MAGMA_CHAMBER, CompoundTag.TAG_COMPOUND)) {
                try {
                    chamber = MagmaChamber.fromTag(siteTag.getCompound(MAGMA_CHAMBER));
                } catch (RuntimeException exception) {
                    LOGGER.warn(
                            "Skipping corrupt magma chamber for volcano {} at index {} while loading chamber schema {}: {}",
                            site.persistenceId(),
                            index,
                            chambersSchemaVersion,
                            exception.getMessage());
                }
            }

            EruptionEvent eruption = null;
            if (siteTag.contains(ERUPTION, CompoundTag.TAG_COMPOUND)) {
                try {
                    eruption = EruptionEvent.fromTag(siteTag.getCompound(ERUPTION));
                } catch (RuntimeException exception) {
                    LOGGER.warn(
                            "Skipping corrupt eruption state for volcano {} at index {}: {}",
                            site.persistenceId(),
                            index,
                            exception.getMessage());
                }
            }

            try {
                data.putLoaded(site, chamber, eruption);
            } catch (RuntimeException exception) {
                LOGGER.warn(
                        "Skipping conflicting volcano SavedData entry {} at index {}: {}",
                        site.persistenceId(),
                        index,
                        exception.getMessage());
            }
        }
        return data;
    }

    private static int readSchemaVersion(CompoundTag tag, String key) {
        return tag.contains(key, CompoundTag.TAG_INT)
                ? tag.getInt(key)
                : LEGACY_SCHEMA_VERSION;
    }

    private static VolcanoSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        return fromTag(tag);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return writeTo(tag);
    }

    private CompoundTag writeTo(CompoundTag tag) {
        tag.putInt(SITES_SCHEMA_VERSION, CURRENT_SCHEMA_VERSION);
        tag.putInt(CHAMBERS_SCHEMA_VERSION, CURRENT_SCHEMA_VERSION);
        ListTag list = new ListTag();
        for (VolcanoSite site : all()) {
            CompoundTag siteTag = site.toTag();
            UUID persistenceId = site.persistenceId();
            MagmaChamber chamber = chambers.get(persistenceId);
            if (chamber != null) {
                siteTag.put(MAGMA_CHAMBER, chamber.toTag());
            }
            EruptionEvent eruption = eruptions.get(persistenceId);
            if (eruption != null) {
                siteTag.put(ERUPTION, eruption.toTag());
            }
            list.add(siteTag);
        }
        tag.put(SITES, list);
        return tag;
    }

    private void putLoaded(VolcanoSite site, MagmaChamber chamber, EruptionEvent eruption) {
        VolcanoSite existing = sites.putIfAbsent(site.persistenceId(), site);
        if (existing != null && !existing.equals(site)) {
            throw new IllegalArgumentException(
                    "Conflicting volcano sites in saved data for persistence ID " + site.persistenceId());
        }
        if (chamber != null) {
            MagmaChamber existingChamber = chambers.putIfAbsent(site.persistenceId(), chamber);
            if (existingChamber != null && !existingChamber.equals(chamber)) {
                throw new IllegalArgumentException(
                        "Conflicting magma chambers in saved data for persistence ID " + site.persistenceId());
            }
        }
        if (eruption != null) {
            if (!site.persistenceId().equals(eruption.volcanoId())) {
                throw new IllegalArgumentException(
                        "Eruption persistence ID does not match volcano site " + site.persistenceId());
            }
            EruptionEvent existingEruption = eruptions.putIfAbsent(site.persistenceId(), eruption);
            if (existingEruption != null && !existingEruption.equals(eruption)) {
                throw new IllegalArgumentException(
                        "Conflicting eruptions in saved data for persistence ID " + site.persistenceId());
            }
        }
    }

    private static VolcanoSite withState(VolcanoSite site, VolcanoState state) {
        return new VolcanoSite(
                site.persistenceId(),
                site.center(),
                site.type(),
                state,
                site.tectonicContext(),
                site.plateId(),
                site.neighborPlateId(),
                site.initialVolcanicPotential());
    }

    private static double horizontalDistanceSquared(BlockPos first, BlockPos second) {
        double dx = (double) first.getX() - second.getX();
        double dz = (double) first.getZ() - second.getZ();
        return dx * dx + dz * dz;
    }
}
