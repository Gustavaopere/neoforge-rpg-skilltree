package dev.gustavopere.rpgskilltree.runtime.economy;

import java.util.Objects;

/** Bounded provider-neutral scheduler for discrete colony-economy settlement passes. */
public final class ColonyEconomyRuntime {
    private final long settlementIntervalTicks;
    private long lastSuccessfulSettlementTick = 0L;
    private long highestObservedTick = 0L;

    public ColonyEconomyRuntime(long settlementIntervalTicks) {
        if (settlementIntervalTicks <= 0L) {
            throw new IllegalArgumentException("settlementIntervalTicks must be positive");
        }
        this.settlementIntervalTicks = settlementIntervalTicks;
    }

    public boolean tryRun(boolean providerActive, long gameTime, Runnable settlementPass) {
        Objects.requireNonNull(settlementPass, "settlementPass");
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }
        if (gameTime < highestObservedTick) {
            throw new IllegalArgumentException("gameTime must not move backwards");
        }
        highestObservedTick = gameTime;

        if (!providerActive || gameTime - lastSuccessfulSettlementTick < settlementIntervalTicks) {
            return false;
        }

        settlementPass.run();
        lastSuccessfulSettlementTick = gameTime;
        return true;
    }

    public long settlementIntervalTicks() {
        return settlementIntervalTicks;
    }

    public long lastSuccessfulSettlementTick() {
        return lastSuccessfulSettlementTick;
    }
}
