package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Attachment envelope that distinguishes an entity that has never been scaled from persisted scaling state. */
public final class EntityScalingAttachmentData {
    private static final EntityScalingAttachmentData UNINITIALIZED = new EntityScalingAttachmentData(Optional.empty());

    private final Optional<EntityScalingSnapshot> snapshot;

    private EntityScalingAttachmentData(Optional<EntityScalingSnapshot> snapshot) {
        this.snapshot = Objects.requireNonNull(snapshot, "snapshot");
    }

    public static EntityScalingAttachmentData uninitialized() {
        return UNINITIALIZED;
    }

    public static EntityScalingAttachmentData initialized(EntityScalingSnapshot snapshot) {
        return new EntityScalingAttachmentData(Optional.of(Objects.requireNonNull(snapshot, "snapshot")));
    }

    public boolean initialized() {
        return snapshot.isPresent();
    }

    public Optional<EntityScalingSnapshot> snapshot() {
        return snapshot;
    }

    public EntityScalingSnapshot requireSnapshot() {
        return snapshot.orElseThrow(() -> new IllegalStateException("entity scaling attachment is not initialized"));
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof EntityScalingAttachmentData data
            && snapshot.equals(data.snapshot);
    }

    @Override
    public int hashCode() {
        return snapshot.hashCode();
    }

    @Override
    public String toString() {
        return initialized() ? "EntityScalingAttachmentData[" + requireSnapshot() + "]" : "EntityScalingAttachmentData[uninitialized]";
    }
}
