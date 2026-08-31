package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.ClassResolutionQueryService;
import dev.gustavopere.rpgskilltree.core.EmergentClassResolution;
import dev.gustavopere.rpgskilltree.core.InvestmentState;
import dev.gustavopere.rpgskilltree.runtime.data.ArchetypeCatalog;
import java.util.Objects;

/**
 * Runtime read boundary for the modern data-driven archetype catalog.
 *
 * <p>This class intentionally accepts an {@link InvestmentState} instead of a
 * player. Building that state from live progression remains the responsibility
 * of the canonical progression layer once authoritative contribution metadata
 * exists.</p>
 */
public final class ClassResolutionRuntime {
    private ClassResolutionRuntime() {}

    public static EmergentClassResolution resolve(InvestmentState state) {
        Objects.requireNonNull(state, "state");
        return ClassResolutionQueryService.resolve(state, ArchetypeCatalog.definitions());
    }
}
