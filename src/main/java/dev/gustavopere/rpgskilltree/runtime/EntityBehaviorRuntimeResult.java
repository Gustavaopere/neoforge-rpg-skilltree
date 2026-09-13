package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityBehaviorKey;
import java.util.List;
import java.util.Objects;

/** Immutable trace of behavior packages reconciled or left inactive because no handler was installed. */
public record EntityBehaviorRuntimeResult(
    List<EntityBehaviorKey> applied,
    List<EntityBehaviorKey> missing
) {
    public EntityBehaviorRuntimeResult {
        Objects.requireNonNull(applied, "applied");
        Objects.requireNonNull(missing, "missing");
        applied = List.copyOf(applied);
        missing = List.copyOf(missing);
    }
}
