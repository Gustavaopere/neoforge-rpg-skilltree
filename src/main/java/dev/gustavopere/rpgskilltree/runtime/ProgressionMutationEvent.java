package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import java.util.Objects;
import java.util.UUID;

/** Immutable post-commit notification for one canonical progression mutation. */
public record ProgressionMutationEvent(
    UUID playerId,
    Section section,
    CanonicalPlayerAttachmentData before,
    CanonicalPlayerAttachmentData after
) {
    public ProgressionMutationEvent {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(section, "section");
        Objects.requireNonNull(before, "before");
        Objects.requireNonNull(after, "after");
        if (before.equals(after)) {
            throw new IllegalArgumentException("progression mutation event requires a changed state");
        }
    }

    public enum Section {
        CORE,
        COMPATIBILITY
    }
}
