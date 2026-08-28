package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.Objects;

/**
 * Single persisted server-side envelope for player RPG state during the legacy-to-Core transition.
 *
 * <p>{@code coreProgression} is authoritative for uncapped character level, RPG XP,
 * Core Progression Points, fundamental attributes and typed reward claims.
 * {@code compatibilityProgression} temporarily owns the still-unmigrated tree/class/mastery
 * domains consumed by legacy services. Keeping both inside one envelope removes independent
 * persistence locations while those domains are migrated deliberately.</p>
 */
public final class CanonicalPlayerState {
    private final CoreProgressionState coreProgression;
    private final ProgressionState compatibilityProgression;

    public CanonicalPlayerState(
        CoreProgressionState coreProgression,
        ProgressionState compatibilityProgression
    ) {
        this.coreProgression = Objects.requireNonNull(coreProgression, "coreProgression");
        this.compatibilityProgression = Objects.requireNonNull(
            compatibilityProgression,
            "compatibilityProgression"
        );
    }

    public CoreProgressionState coreProgression() {
        return coreProgression;
    }

    public ProgressionState compatibilityProgression() {
        return compatibilityProgression;
    }

    public CanonicalPlayerState withCoreProgression(CoreProgressionState next) {
        Objects.requireNonNull(next, "next");
        if (next == coreProgression) return this;
        return new CanonicalPlayerState(next, compatibilityProgression);
    }

    public CanonicalPlayerState withCompatibilityProgression(ProgressionState next) {
        Objects.requireNonNull(next, "next");
        if (next == compatibilityProgression) return this;
        return new CanonicalPlayerState(coreProgression, next);
    }

    /** Persisted-value equality rather than identity equality of the internal immutable ledgers. */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CanonicalPlayerState state)) return false;
        return Arrays.equals(
                CoreProgressionStateCodec.encode(coreProgression),
                CoreProgressionStateCodec.encode(state.coreProgression)
            )
            && Arrays.equals(
                ProgressionStateCodec.encode(compatibilityProgression),
                ProgressionStateCodec.encode(state.compatibilityProgression)
            );
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(CoreProgressionStateCodec.encode(coreProgression));
        result = 31 * result + Arrays.hashCode(ProgressionStateCodec.encode(compatibilityProgression));
        return result;
    }

    @Override
    public String toString() {
        return "CanonicalPlayerState{" +
            "level=" + coreProgression.characterProgression().level() +
            ", compatibilityMasteries=" + compatibilityProgression.mastery().experience().size() +
            '}';
    }
}
