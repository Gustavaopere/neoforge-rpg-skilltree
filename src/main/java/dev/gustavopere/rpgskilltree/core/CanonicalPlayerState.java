package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.Objects;

/**
 * Single persisted server-side envelope for player RPG state during the legacy-to-Core transition.
 *
 * <p>{@code coreProgression} is authoritative for uncapped character level, RPG XP,
 * Core Progression Points, fundamental attributes and typed reward claims.
 * {@code compatibilityProgression} temporarily owns the still-unmigrated tree/class/mastery
 * domains consumed by legacy services. {@code originClass} is the first class-oriented
 * identity moved into the canonical envelope under ADR 016. Keeping these domains inside
 * one envelope removes independent persistence locations while migration proceeds deliberately.</p>
 */
public final class CanonicalPlayerState {
    private final CoreProgressionState coreProgression;
    private final ProgressionState compatibilityProgression;
    private final OriginClassState originClass;

    /**
     * Compatibility constructor for existing call sites. A missing explicit origin is intentional
     * and must never be inferred silently from legacy class state.
     */
    public CanonicalPlayerState(
        CoreProgressionState coreProgression,
        ProgressionState compatibilityProgression
    ) {
        this(coreProgression, compatibilityProgression, OriginClassState.empty());
    }

    public CanonicalPlayerState(
        CoreProgressionState coreProgression,
        ProgressionState compatibilityProgression,
        OriginClassState originClass
    ) {
        this.coreProgression = Objects.requireNonNull(coreProgression, "coreProgression");
        this.compatibilityProgression = Objects.requireNonNull(
            compatibilityProgression,
            "compatibilityProgression"
        );
        this.originClass = Objects.requireNonNull(originClass, "originClass");
    }

    public CoreProgressionState coreProgression() {
        return coreProgression;
    }

    public ProgressionState compatibilityProgression() {
        return compatibilityProgression;
    }

    public OriginClassState originClass() {
        return originClass;
    }

    public CanonicalPlayerState withCoreProgression(CoreProgressionState next) {
        Objects.requireNonNull(next, "next");
        if (next == coreProgression) return this;
        return new CanonicalPlayerState(next, compatibilityProgression, originClass);
    }

    public CanonicalPlayerState withCompatibilityProgression(ProgressionState next) {
        Objects.requireNonNull(next, "next");
        if (next == compatibilityProgression) return this;
        return new CanonicalPlayerState(coreProgression, next, originClass);
    }

    public CanonicalPlayerState withOriginClass(OriginClassState next) {
        Objects.requireNonNull(next, "next");
        if (next == originClass) return this;
        return new CanonicalPlayerState(coreProgression, compatibilityProgression, next);
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
            )
            && originClass.equals(state.originClass);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(CoreProgressionStateCodec.encode(coreProgression));
        result = 31 * result + Arrays.hashCode(ProgressionStateCodec.encode(compatibilityProgression));
        result = 31 * result + originClass.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CanonicalPlayerState{" +
            "level=" + coreProgression.characterProgression().level() +
            ", compatibilityMasteries=" + compatibilityProgression.mastery().experience().size() +
            ", originClass=" + originClass.selectedClassId().orElse("unselected") +
            '}';
    }
}
