package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Serializable attachment envelope that distinguishes absent Core initialization from real state. */
public final class CoreProgressionAttachmentData {
    private static final CoreProgressionAttachmentData UNINITIALIZED =
        new CoreProgressionAttachmentData(null);

    private final CoreProgressionState state;

    private CoreProgressionAttachmentData(CoreProgressionState state) {
        this.state = state;
    }

    public static CoreProgressionAttachmentData uninitialized() {
        return UNINITIALIZED;
    }

    public static CoreProgressionAttachmentData initialized(CoreProgressionState state) {
        return new CoreProgressionAttachmentData(Objects.requireNonNull(state));
    }

    public boolean isInitialized() {
        return state != null;
    }

    public Optional<CoreProgressionState> state() {
        return Optional.ofNullable(state);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CoreProgressionAttachmentData that
            && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state);
    }

    @Override
    public String toString() {
        return isInitialized()
            ? "CoreProgressionAttachmentData[initialized]"
            : "CoreProgressionAttachmentData[uninitialized]";
    }
}
