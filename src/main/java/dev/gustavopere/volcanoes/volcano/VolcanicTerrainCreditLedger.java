package dev.gustavopere.volcanoes.volcano;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BooleanSupplier;

/**
 * Bounded per-volcano terrain-work credits reserved by eruption scheduling and consumed later by
 * ballistic impacts or pyroclastic trail interaction.
 *
 * <p>The ledger never creates work: callers may only add credits that already came from
 * {@link VolcanicHazardAllocation}. Empty entries are removed eagerly so dormant or exhausted
 * volcanoes do not accumulate bookkeeping state.</p>
 */
public final class VolcanicTerrainCreditLedger {
    private final int maxBombCreditsPerVolcano;
    private final int maxFlowCreditsPerVolcano;
    private final Map<UUID, Credits> creditsByVolcano = new HashMap<>();

    public VolcanicTerrainCreditLedger(int maxBombCreditsPerVolcano, int maxFlowCreditsPerVolcano) {
        if (maxBombCreditsPerVolcano < 0 || maxFlowCreditsPerVolcano < 0) {
            throw new IllegalArgumentException("terrain credit caps must be non-negative");
        }
        this.maxBombCreditsPerVolcano = maxBombCreditsPerVolcano;
        this.maxFlowCreditsPerVolcano = maxFlowCreditsPerVolcano;
    }

    public void add(UUID volcanoId, int bombCredits, int flowCredits) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        if (bombCredits < 0 || flowCredits < 0) {
            throw new IllegalArgumentException("terrain credits must be non-negative");
        }
        Credits current = creditsByVolcano.getOrDefault(volcanoId, Credits.NONE);
        int nextBomb = cappedSum(current.bomb(), bombCredits, maxBombCreditsPerVolcano);
        int nextFlow = cappedSum(current.flow(), flowCredits, maxFlowCreditsPerVolcano);
        putOrRemove(volcanoId, nextBomb, nextFlow);
    }

    public boolean trySpendBomb(UUID volcanoId, BooleanSupplier mutation) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        Objects.requireNonNull(mutation, "mutation");
        if (bombCredits(volcanoId) == 0 || !mutation.getAsBoolean()) {
            return false;
        }
        return tryConsumeBomb(volcanoId);
    }

    public boolean trySpendFlow(UUID volcanoId, BooleanSupplier mutation) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        Objects.requireNonNull(mutation, "mutation");
        if (flowCredits(volcanoId) == 0 || !mutation.getAsBoolean()) {
            return false;
        }
        return tryConsumeFlow(volcanoId);
    }

    public boolean tryConsumeBomb(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        Credits current = creditsByVolcano.get(volcanoId);
        if (current == null || current.bomb() == 0) {
            return false;
        }
        putOrRemove(volcanoId, current.bomb() - 1, current.flow());
        return true;
    }

    public boolean tryConsumeFlow(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        Credits current = creditsByVolcano.get(volcanoId);
        if (current == null || current.flow() == 0) {
            return false;
        }
        putOrRemove(volcanoId, current.bomb(), current.flow() - 1);
        return true;
    }

    public int bombCredits(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        Credits credits = creditsByVolcano.get(volcanoId);
        return credits == null ? 0 : credits.bomb();
    }

    public int flowCredits(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        Credits credits = creditsByVolcano.get(volcanoId);
        return credits == null ? 0 : credits.flow();
    }

    public void clear(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        creditsByVolcano.remove(volcanoId);
    }

    public int size() {
        return creditsByVolcano.size();
    }

    private void putOrRemove(UUID volcanoId, int bombCredits, int flowCredits) {
        if (bombCredits == 0 && flowCredits == 0) {
            creditsByVolcano.remove(volcanoId);
        } else {
            creditsByVolcano.put(volcanoId, new Credits(bombCredits, flowCredits));
        }
    }

    private static int cappedSum(int current, int added, int cap) {
        return (int) Math.min((long) cap, (long) current + added);
    }

    private record Credits(int bomb, int flow) {
        private static final Credits NONE = new Credits(0, 0);
    }
}
