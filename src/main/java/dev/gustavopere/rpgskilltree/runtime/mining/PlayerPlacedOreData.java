package dev.gustavopere.rpgskilltree.runtime.mining;

import dev.gustavopere.rpgskilltree.core.AntiFarmService;
import dev.gustavopere.rpgskilltree.core.BlockProvenanceAntiFarmService;
import dev.gustavopere.rpgskilltree.core.PlacedBlockProvenance;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * Per-dimension provenance for ore blocks placed by players.
 * Natural/generated ores are intentionally absent from this set and remain XP-eligible.
 */
public final class PlayerPlacedOreData extends SavedData {
    private static final String DATA_NAME = "rpgskilltree_player_placed_ores";
    private static final String POSITIONS_KEY = "positions";

    private final PlacedBlockProvenance provenance;

    public PlayerPlacedOreData() {
        this(new PlacedBlockProvenance());
    }

    private PlayerPlacedOreData(PlacedBlockProvenance provenance) {
        this.provenance = provenance;
    }

    public static PlayerPlacedOreData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
            new SavedData.Factory<>(PlayerPlacedOreData::new, PlayerPlacedOreData::load),
            DATA_NAME
        );
    }

    private static PlayerPlacedOreData load(CompoundTag tag, HolderLookup.Provider registries) {
        var positions = Arrays.stream(tag.getLongArray(POSITIONS_KEY)).boxed().toList();
        return new PlayerPlacedOreData(new PlacedBlockProvenance(positions));
    }

    public void mark(BlockPos pos) {
        if (provenance.mark(pos.asLong())) setDirty();
    }

    /** Non-consuming anti-farm view over the same persisted placement provenance. */
    public AntiFarmService antiFarmService() {
        return new BlockProvenanceAntiFarmService(provenance);
    }

    /** Returns true when the position was player-placed and consumes its provenance marker. */
    public boolean consume(BlockPos pos) {
        boolean removed = provenance.consume(pos.asLong());
        if (removed) setDirty();
        return removed;
    }

    public void removeAll(Collection<BlockPos> destroyedPositions) {
        int removed = provenance.removeAll(destroyedPositions.stream().map(BlockPos::asLong).toList());
        if (removed > 0) setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putLongArray(POSITIONS_KEY, provenance.snapshot().stream().mapToLong(Long::longValue).toArray());
        return tag;
    }
}
