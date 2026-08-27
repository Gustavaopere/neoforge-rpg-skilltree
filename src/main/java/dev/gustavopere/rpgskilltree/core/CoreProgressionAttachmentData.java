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
        if (!(other instanceof CoreProgressionAttachmentData that)) return false;
        if (state == null || that.state == null) return state == that.state;
        return state.characterProgression().equals(that.state.characterProgression())
            && state.corePoints().checkpoint().equals(that.state.corePoints().checkpoint())
            && state.rulesVersion() == that.state.rulesVersion()
            && state.rulesFingerprint().equals(that.state.rulesFingerprint())
            && state.migrationSourceFormatVersion() == that.state.migrationSourceFormatVersion()
            && state.discardedLegacyCapXp() == that.state.discardedLegacyCapXp();
    }

    @Override
    public int hashCode() {
        if (state == null) return 0;
        return Objects.hash(
            state.characterProgression(),
            state.corePoints().checkpoint(),
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }

    @Override
    public String toString() {
        return isInitialized()
            ? "CoreProgressionAttachmentData[initialized]"
            : "CoreProgressionAttachmentData[uninitialized]";
    }
}
