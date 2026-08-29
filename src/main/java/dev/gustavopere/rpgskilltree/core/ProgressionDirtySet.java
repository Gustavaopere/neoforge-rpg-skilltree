package dev.gustavopere.rpgskilltree.core;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/** Immutable set of semantic downstream work caused by progression mutations. */
public final class ProgressionDirtySet {
    private static final ProgressionDirtySet EMPTY = new ProgressionDirtySet(EnumSet.noneOf(ProgressionDirtyReason.class));

    private final Set<ProgressionDirtyReason> reasons;

    private ProgressionDirtySet(EnumSet<ProgressionDirtyReason> reasons) {
        this.reasons = reasons.isEmpty()
            ? Collections.emptySet()
            : Collections.unmodifiableSet(EnumSet.copyOf(reasons));
    }

    public static ProgressionDirtySet empty() {
        return EMPTY;
    }

    public static ProgressionDirtySet of(ProgressionDirtyReason... reasons) {
        Objects.requireNonNull(reasons, "reasons");
        if (reasons.length == 0) return EMPTY;

        EnumSet<ProgressionDirtyReason> values = EnumSet.noneOf(ProgressionDirtyReason.class);
        for (ProgressionDirtyReason reason : reasons) {
            values.add(Objects.requireNonNull(reason, "reason"));
        }
        return new ProgressionDirtySet(values);
    }

    public boolean isEmpty() {
        return reasons.isEmpty();
    }

    public boolean contains(ProgressionDirtyReason reason) {
        return reasons.contains(Objects.requireNonNull(reason, "reason"));
    }

    public Set<ProgressionDirtyReason> reasons() {
        return reasons;
    }

    public ProgressionDirtySet merge(ProgressionDirtySet other) {
        Objects.requireNonNull(other, "other");
        if (other.isEmpty()) return this;
        if (isEmpty()) return other;

        EnumSet<ProgressionDirtyReason> merged = EnumSet.copyOf(reasons);
        merged.addAll(other.reasons);
        if (merged.size() == reasons.size()) return this;
        return new ProgressionDirtySet(merged);
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof ProgressionDirtySet that
            && reasons.equals(that.reasons);
    }

    @Override
    public int hashCode() {
        return reasons.hashCode();
    }

    @Override
    public String toString() {
        return "ProgressionDirtySet" + reasons;
    }
}
