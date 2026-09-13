package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/**
 * Canonical persisted identity for the player's immutable class of origin.
 *
 * <p>An empty state is intentional for new players that have not chosen yet and for
 * legacy saves that predate ADR 016. Ordinary gameplay may select an origin once;
 * replacing an existing different origin requires a future explicit admin/migration path.</p>
 */
public final class OriginClassState {
    private static final OriginClassState EMPTY = new OriginClassState(null);

    private final String selectedClassId;

    private OriginClassState(String selectedClassId) {
        this.selectedClassId = selectedClassId;
    }

    public static OriginClassState empty() {
        return EMPTY;
    }

    public Optional<String> selectedClassId() {
        return Optional.ofNullable(selectedClassId);
    }

    public OriginClassState select(String classId) {
        String validated = ProgressionProvenanceId.requireNamespacedId(classId, "origin class id");
        if (selectedClassId == null) return new OriginClassState(validated);
        if (selectedClassId.equals(validated)) return this;
        throw new IllegalStateException(
            "origin class is already selected as " + selectedClassId + "; ordinary mutation cannot replace it"
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof OriginClassState state)) return false;
        return Objects.equals(selectedClassId, state.selectedClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(selectedClassId);
    }

    @Override
    public String toString() {
        return selectedClassId == null ? "OriginClassState[unselected]" : "OriginClassState[" + selectedClassId + ']';
    }
}
