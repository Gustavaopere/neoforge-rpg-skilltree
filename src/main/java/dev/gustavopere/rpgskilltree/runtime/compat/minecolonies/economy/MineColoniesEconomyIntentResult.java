package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMutationResult;
import java.util.Optional;

/** Result exposed by the server-side MineColonies economy intent boundary. */
public record MineColoniesEconomyIntentResult(
    MineColoniesEconomyIntentStatus status,
    ColonyEconomyState resolvedState,
    EconomyMutationResult.Status canonicalLedgerStatus
) {
    public Optional<ColonyEconomyState> state() {
        return Optional.ofNullable(resolvedState);
    }

    public Optional<EconomyMutationResult.Status> ledgerStatus() {
        return Optional.ofNullable(canonicalLedgerStatus);
    }
}
