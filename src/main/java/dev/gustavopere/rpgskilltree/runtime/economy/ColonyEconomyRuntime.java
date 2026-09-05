package dev.gustavopere.rpgskilltree.runtime.economy;

import java.util.Objects;

/** Bounded provider-neutral scheduler for discrete colony-economy settlement passes. */
public final class ColonyEconomyRuntime {
    private final long settlementIntervalTicks;
    private long lastSuccessfulSettlementTick;
    private long highestObservedTick;
    private boolean anchored;

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
        if (anchored && gameTime < highestObservedTick) {
            throw new IllegalArgumentException("gameTime must not move backwards");
        }
        highestObservedTick = gameTime;

        if (!providerActive) {
            return false;
        }
        if (!anchored) {
            anchored = true;
            lastSuccessfulSettlementTick = gameTime;
            return false;
        }
        if (gameTime - lastSuccessfulSettlementTick < settlementIntervalTicks) {
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
