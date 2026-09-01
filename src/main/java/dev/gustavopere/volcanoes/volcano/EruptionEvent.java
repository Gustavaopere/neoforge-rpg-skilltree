package dev.gustavopere.volcanoes.volcano;

import net.minecraft.nbt.CompoundTag;

import java.util.Objects;
import java.util.UUID;

/** Persistable progress for one eruption bound to a stable volcano site UUID. */
public record EruptionEvent(
        UUID volcanoId,
        EruptionPhase phase,
        EruptionProfile profile,
        long startedTick,
        long elapsedTicks
) {
    private static final String VOLCANO_ID = "volcano_id";
    private static final String PHASE = "phase";
    private static final String PROFILE = "profile";
    private static final String STARTED_TICK = "started_tick";
    private static final String ELAPSED_TICKS = "elapsed_ticks";

    public EruptionEvent {
        volcanoId = Objects.requireNonNull(volcanoId, "volcanoId");
        phase = Objects.requireNonNull(phase, "phase");
        profile = Objects.requireNonNull(profile, "profile");
        if (startedTick < 0L) {
            throw new IllegalArgumentException("startedTick must be non-negative");
        }
        if (elapsedTicks < 0L) {
            throw new IllegalArgumentException("elapsedTicks must be non-negative");
        }
    }

    public boolean isComplete() {
        return phase == EruptionPhase.DORMANT;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID(VOLCANO_ID, volcanoId);
        tag.putString(PHASE, phase.name());
        tag.put(PROFILE, profile.toTag());
        tag.putLong(STARTED_TICK, startedTick);
        tag.putLong(ELAPSED_TICKS, elapsedTicks);
        return tag;
    }

    public static EruptionEvent fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        return new EruptionEvent(
                tag.getUUID(VOLCANO_ID),
                EruptionPhase.valueOf(tag.getString(PHASE)),
                EruptionProfile.fromTag(tag.getCompound(PROFILE)),
                tag.getLong(STARTED_TICK),
                tag.getLong(ELAPSED_TICKS));
    }
}
