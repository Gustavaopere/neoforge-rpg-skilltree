package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyLedger;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyCommand;
import dev.gustavopere.rpgskilltree.core.economy.EconomyMutationResult;
import dev.gustavopere.rpgskilltree.core.economy.EconomyTransaction;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.server.MinecraftServer;

/** Canonical server-side repository for colony economy state and ledger mutations. */
public final class ColonyEconomyRepository {
    private final ColonyEconomySavedData data;

    public ColonyEconomyRepository(ColonyEconomySavedData data) {
        this.data = Objects.requireNonNull(data, "data");
    }

    public static ColonyEconomyRepository forServer(MinecraftServer server) {
        return new ColonyEconomyRepository(ColonyEconomySavedData.get(server));
    }

    public Optional<ColonyEconomyState> find(EconomyColonyKey key) {
        Objects.requireNonNull(key, "key");
        ColonyEconomySavedData.StoredEconomy stored = data.get(key);
        return stored == null ? Optional.empty() : Optional.of(stored.state());
    }

    public List<EconomyTransaction> transactions(EconomyColonyKey key) {
        Objects.requireNonNull(key, "key");
        ColonyEconomySavedData.StoredEconomy stored = data.get(key);
        return stored == null ? List.of() : stored.ledger().transactions();
    }

    public EconomyMutationResult apply(EconomyColonyKey key, EconomyCommand command, long gameTime) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(command, "command");

        ColonyEconomySavedData.StoredEconomy stored = data.get(key);
        if (stored == null) {
            ColonyEconomyState initial = ColonyEconomyState.empty(key);
            ColonyEconomyLedger transientLedger = new ColonyEconomyLedger();
            EconomyMutationResult result = transientLedger.apply(initial, command, gameTime);
            if (result.status() == EconomyMutationResult.Status.APPLIED) {
                data.put(key, result.state(), transientLedger);
            }
            return result;
        }

        EconomyMutationResult result = stored.ledger().apply(stored.state(), command, gameTime);
        if (result.status() == EconomyMutationResult.Status.APPLIED) {
            data.replaceState(key, result.state());
        }
        return result;
    }

    /** Stores a deterministic settlement result without fabricating a monetary transaction. */
    public void storeSettledState(ColonyEconomyState state) {
        Objects.requireNonNull(state, "state");
        data.replaceState(state.colonyKey(), state);
    }
}
