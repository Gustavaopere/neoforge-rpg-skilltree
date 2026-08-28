package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Attachment envelope that distinguishes an entity that has never been scaled from persisted scaling state. */
public final class EntityScalingAttachmentData {
    private static final EntityScalingAttachmentData UNINITIALIZED = new EntityScalingAttachmentData(Optional.empty());

    private final Optional<EntityScalingState> state;

    private EntityScalingAttachmentData(Optional<EntityScalingState> state) {
        this.state = Objects.requireNonNull(state, "state");
    }

    public static EntityScalingAttachmentData uninitialized() {
        return UNINITIALIZED;
    }

    public static EntityScalingAttachmentData initialized(EntityScalingState state) {
        return new EntityScalingAttachmentData(Optional.of(Objects.requireNonNull(state, "state")));
    }

    public boolean initialized() {
        return state.isPresent();
    }

    public Optional<EntityScalingState> state() {
        return state;
    }

    public EntityScalingState requireState() {
        return state.orElseThrow(() -> new IllegalStateException("entity scaling attachment is not initialized"));
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof EntityScalingAttachmentData data
            && state.equals(data.state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public String toString() {
        return initialized() ? "EntityScalingAttachmentData[" + requireState() + "]" : "EntityScalingAttachmentData[uninitialized]";
    }
}
