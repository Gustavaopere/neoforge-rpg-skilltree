package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.colony.IColony;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomicInputs;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomySettlementService;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomySnapshot;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyRepository;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.server.MinecraftServer;

/** Bounded round-robin settlement pass over live MineColonies colonies. */
public final class MineColoniesEconomySettlementBridge {
    public static final int DEFAULT_MAX_COLONIES_PER_PASS = 64;

    private final int maxColoniesPerPass;
    private int cursor;

    public MineColoniesEconomySettlementBridge() {
        this(DEFAULT_MAX_COLONIES_PER_PASS);
    }

    public MineColoniesEconomySettlementBridge(int maxColoniesPerPass) {
        if (maxColoniesPerPass <= 0) {
            throw new IllegalArgumentException("maxColoniesPerPass must be positive");
        }
        this.maxColoniesPerPass = maxColoniesPerPass;
    }

    public synchronized SettlementPassResult settleNextBatch(MinecraftServer server, long gameTime) {
        if (server == null) {
            throw new IllegalArgumentException("server must not be null");
        }
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }

        List<IColony> colonies = new ArrayList<>(IMinecoloniesAPI.getInstance().getColonyManager().getAllColonies());
        colonies.removeIf(colony -> MineColoniesEconomyAdapter.binding(colony).isEmpty());
        colonies.sort(Comparator.comparing(colony ->
            MineColoniesEconomyAdapter.binding(colony).orElseThrow().persistentKey()
        ));

        if (colonies.isEmpty()) {
            cursor = 0;
            return new SettlementPassResult(0, 0, 0);
        }

        ColonyEconomySavedData data = ColonyEconomySavedData.get(server);
        ColonyEconomyRepository repository = new ColonyEconomyRepository(data);
        EconomyParameters parameters = EconomyParameters.defaults();

        int observed = colonies.size();
        int count = Math.min(maxColoniesPerPass, observed);
        int start = Math.floorMod(cursor, observed);
        int settled = 0;
        int skipped = 0;

        for (int offset = 0; offset < count; offset++) {
            IColony colony = colonies.get((start + offset) % observed);
            try {
                NativeColonyBinding nativeBinding = MineColoniesEconomyAdapter.binding(colony).orElse(null);
                ColonyEconomicInputs inputs = MineColoniesEconomyAdapter.economicInputs(colony).orElse(null);
                if (nativeBinding == null || inputs == null) {
                    skipped++;
                    continue;
                }

                EconomyColonyKey economyKey = data.resolveOrCreateBinding(nativeBinding);
                if (data.isArchived(economyKey)) {
                    skipped++;
                    continue;
                }
                ColonyEconomyState state = repository.find(economyKey).orElseGet(() -> ColonyEconomyState.empty(economyKey));
                ColonyEconomySnapshot snapshot = ColonyEconomySettlementService.settle(
                    state,
                    inputs,
                    parameters,
                    gameTime
                );
                repository.storeSettledState(snapshot.state());
                settled++;
            } catch (RuntimeException | LinkageError failure) {
                skipped++;
            }
        }

        cursor = (start + count) % observed;
        return new SettlementPassResult(observed, settled, skipped);
    }

    public int cursor() {
        return cursor;
    }

    public record SettlementPassResult(int observedColonies, int settledColonies, int skippedColonies) {
        public SettlementPassResult {
            if (observedColonies < 0 || settledColonies < 0 || skippedColonies < 0) {
                throw new IllegalArgumentException("settlement pass counters must be non-negative");
            }
            if (settledColonies + skippedColonies > observedColonies) {
                throw new IllegalArgumentException("processed colonies must not exceed observed colonies");
            }
        }
    }
}
