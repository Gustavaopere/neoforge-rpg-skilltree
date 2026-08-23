package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Confirmed Eidolon ritual completion attributed to the initiating player. */
public record EidolonRitualAction(
    ActionOrigin origin,
    String ritualId,
    Set<String> tags,
    boolean firstCompletion
) {
    public EidolonRitualAction {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(ritualId);
        Objects.requireNonNull(tags);
        tags = Set.copyOf(tags);
        if (ritualId.isBlank()) throw new IllegalArgumentException("ritualId must not be blank");
    }
}
