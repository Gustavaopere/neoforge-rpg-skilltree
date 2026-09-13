package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import dev.gustavopere.rpgskilltree.core.economy.EconomyPreflight;
import java.util.Optional;

/** Read-only server projection for one mint preflight request. */
public record MineColoniesEconomyPreflightResult(
    MineColoniesEconomyIntentStatus status,
    EconomyPreflight resolvedPreflight
) {
    public Optional<EconomyPreflight> preflight() {
        return Optional.ofNullable(resolvedPreflight);
    }
}
