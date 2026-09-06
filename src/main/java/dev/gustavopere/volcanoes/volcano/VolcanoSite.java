package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;
import java.util.UUID;

/** Persistent identity and immutable geological birth context for one physical volcano site. */
public record VolcanoSite(
        UUID persistenceId,
        BlockPos center,
        VolcanoType type,
        VolcanoState state,
        TectonicContext tectonicContext,
        long plateId,
        long neighborPlateId,
        double initialVolcanicPotential
) {
    private static final String ID = "id";
    private static final String CENTER = "center";
    private static final String TYPE = "type";
    private static final String STATE = "state";
    private static final String TECTONIC_CONTEXT = "tectonic_context";
    private static final String PLATE_ID = "plate_id";
    private static final String NEIGHBOR_PLATE_ID = "neighbor_plate_id";
    private static final String INITIAL_VOLCANIC_POTENTIAL = "initial_volcanic_potential";

    public VolcanoSite {
        persistenceId = Objects.requireNonNull(persistenceId, "persistenceId");
        center = Objects.requireNonNull(center, "center").immutable();
        type = Objects.requireNonNull(type, "type");
        state = Objects.requireNonNull(state, "state");
        tectonicContext = Objects.requireNonNull(tectonicContext, "tectonicContext");
        if (!Double.isFinite(initialVolcanicPotential)
                || initialVolcanicPotential < 0.0
                || initialVolcanicPotential > 1.0) {
            throw new IllegalArgumentException("initialVolcanicPotential must be within [0, 1]");
        }
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID(ID, persistenceId);
        tag.putLong(CENTER, center.asLong());
        tag.putString(TYPE, type.name());
        tag.putString(STATE, state.name());
        tag.putString(TECTONIC_CONTEXT, tectonicContext.name());
        tag.putLong(PLATE_ID, plateId);
        tag.putLong(NEIGHBOR_PLATE_ID, neighborPlateId);
        tag.putDouble(INITIAL_VOLCANIC_POTENTIAL, initialVolcanicPotential);
        return tag;
    }

    public static VolcanoSite fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        return new VolcanoSite(
                tag.getUUID(ID),
                BlockPos.of(tag.getLong(CENTER)),
                VolcanoType.valueOf(tag.getString(TYPE)),
                VolcanoState.valueOf(tag.getString(STATE)),
                TectonicContext.valueOf(tag.getString(TECTONIC_CONTEXT)),
                tag.getLong(PLATE_ID),
                tag.getLong(NEIGHBOR_PLATE_ID),
                tag.getDouble(INITIAL_VOLCANIC_POTENTIAL));
    }
}
