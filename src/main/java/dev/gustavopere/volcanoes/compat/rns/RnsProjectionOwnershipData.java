package dev.gustavopere.volcanoes.compat.rns;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Persistent historical attribution for custom RNS identities inserted by Volcanoes.
 *
 * <p>RNS identifies custom deposits by structure plus chunk, but exposes no owner field or durable
 * per-record token. This ledger is therefore useful for persistence diagnostics and stale-claim
 * cleanup, but it is never sufficient by itself to authorize destructive host removal. Live host
 * record continuity is checked separately by {@link RnsHostDepositProjectionWriter}.</p>
 */
final class RnsProjectionOwnershipData extends SavedData {
    private static final String STORAGE_NAME = "volcanoes_rns_projection_ownership";
    private static final String ENTRIES = "entries";
    private static final String SOURCE_ID = "source_id";
    private static final String RNS_DEPOSIT_ID = "rns_deposit_id";
    private static final String CHUNK_X = "chunk_x";
    private static final String CHUNK_Z = "chunk_z";
    private static final String CENTER = "center";

    private static final Factory<RnsProjectionOwnershipData> FACTORY =
            new Factory<>(RnsProjectionOwnershipData::new, RnsProjectionOwnershipData::load);

    private final Map<OwnershipKey, Ownership> ownership = new LinkedHashMap<>();

    static RnsProjectionOwnershipData get(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        return level.getDataStorage().computeIfAbsent(FACTORY, STORAGE_NAME);
    }

    boolean owns(RnsDepositProjectionPlanner.Projection projection) {
        Objects.requireNonNull(projection, "projection");
        return expectedOwnership(projection).equals(ownership.get(key(projection)));
    }

    boolean claimedByOther(RnsDepositProjectionPlanner.Projection projection) {
        Objects.requireNonNull(projection, "projection");
        Ownership existing = ownership.get(key(projection));
        return existing != null && !existing.equals(expectedOwnership(projection));
    }

    boolean claim(RnsDepositProjectionPlanner.Projection projection) {
        Objects.requireNonNull(projection, "projection");
        OwnershipKey key = key(projection);
        Ownership expected = expectedOwnership(projection);
        Ownership existing = ownership.get(key);
        if (existing != null) {
            return existing.equals(expected);
        }
        ownership.put(key, expected);
        setDirty();
        return true;
    }

    boolean release(RnsDepositProjectionPlanner.Projection projection) {
        Objects.requireNonNull(projection, "projection");
        OwnershipKey key = key(projection);
        Ownership expected = expectedOwnership(projection);
        Ownership existing = ownership.get(key);
        if (!expected.equals(existing)) {
            return false;
        }
        ownership.remove(key);
        setDirty();
        return true;
    }

    /** Clears any historical claim for this host identity; callers must first prove removal is safe. */
    boolean clearIdentity(RnsDepositProjectionPlanner.Projection projection) {
        Objects.requireNonNull(projection, "projection");
        Ownership removed = ownership.remove(key(projection));
        if (removed == null) {
            return false;
        }
        setDirty();
        return true;
    }

    int size() {
        return ownership.size();
    }

    CompoundTag toTag() {
        return writeTo(new CompoundTag());
    }

    static RnsProjectionOwnershipData fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        RnsProjectionOwnershipData data = new RnsProjectionOwnershipData();
        Set<OwnershipKey> ambiguous = new LinkedHashSet<>();
        ListTag entries = tag.getList(ENTRIES, CompoundTag.TAG_COMPOUND);
        for (int index = 0; index < entries.size(); index++) {
            CompoundTag entry = entries.getCompound(index);
            OwnershipKey parsedKey = null;
            try {
                parsedKey = new OwnershipKey(
                        ResourceLocation.parse(entry.getString(RNS_DEPOSIT_ID)),
                        entry.getInt(CHUNK_X),
                        entry.getInt(CHUNK_Z));
                if (ambiguous.contains(parsedKey)) {
                    continue;
                }
                Ownership value = new Ownership(
                        entry.getUUID(SOURCE_ID),
                        BlockPos.of(entry.getLong(CENTER)));
                Ownership existing = data.ownership.putIfAbsent(parsedKey, value);
                if (existing != null && !existing.equals(value)) {
                    invalidateAmbiguous(data, ambiguous, parsedKey);
                }
            } catch (RuntimeException malformedEntry) {
                if (parsedKey != null) {
                    // Once the identity itself is parseable, a malformed ownership value is a
                    // conflicting duplicate candidate. Any earlier authority for that key becomes
                    // ambiguous and must be revoked fail-closed.
                    invalidateAmbiguous(data, ambiguous, parsedKey);
                }
                // If even the identity is unparseable, it cannot authorize any valid host mutation.
            }
        }
        return data;
    }

    private static void invalidateAmbiguous(
            RnsProjectionOwnershipData data,
            Set<OwnershipKey> ambiguous,
            OwnershipKey key
    ) {
        data.ownership.remove(key);
        ambiguous.add(key);
    }

    private static RnsProjectionOwnershipData load(CompoundTag tag, HolderLookup.Provider registries) {
        return fromTag(tag);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return writeTo(tag);
    }

    private CompoundTag writeTo(CompoundTag tag) {
        ListTag entries = new ListTag();
        for (Map.Entry<OwnershipKey, Ownership> owned : ownership.entrySet()) {
            CompoundTag entry = new CompoundTag();
            entry.putUUID(SOURCE_ID, owned.getValue().sourceId());
            entry.putString(RNS_DEPOSIT_ID, owned.getKey().rnsDepositId().toString());
            entry.putInt(CHUNK_X, owned.getKey().chunkX());
            entry.putInt(CHUNK_Z, owned.getKey().chunkZ());
            entry.putLong(CENTER, owned.getValue().center().asLong());
            entries.add(entry);
        }
        tag.put(ENTRIES, entries);
        return tag;
    }

    private static OwnershipKey key(RnsDepositProjectionPlanner.Projection projection) {
        ChunkPos chunk = new ChunkPos(projection.center());
        return new OwnershipKey(projection.rnsDepositId(), chunk.x, chunk.z);
    }

    private static Ownership expectedOwnership(RnsDepositProjectionPlanner.Projection projection) {
        return new Ownership(projection.sourceId(), projection.center().immutable());
    }

    private record OwnershipKey(ResourceLocation rnsDepositId, int chunkX, int chunkZ) {
        private OwnershipKey {
            Objects.requireNonNull(rnsDepositId, "rnsDepositId");
        }
    }

    private record Ownership(UUID sourceId, BlockPos center) {
        private Ownership {
            Objects.requireNonNull(sourceId, "sourceId");
            center = Objects.requireNonNull(center, "center").immutable();
        }
    }
}
