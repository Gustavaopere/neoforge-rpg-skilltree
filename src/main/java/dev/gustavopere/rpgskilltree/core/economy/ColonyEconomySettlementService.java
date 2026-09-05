package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;

/** Pure provider-free settlement and preflight calculations. */
public final class ColonyEconomySettlementService {
    private ColonyEconomySettlementService() {}

    public static ColonyEconomySnapshot settle(
        ColonyEconomyState state,
        ColonyEconomicInputs inputs,
        EconomyParameters parameters,
        long gameTime
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(inputs, "inputs");
        Objects.requireNonNull(parameters, "parameters");
        if (gameTime < state.lastSettlementTick()) {
            throw new IllegalArgumentException("gameTime must not move backwards");
        }

        long economicCapacity = EconomyMath.capacity(inputs, parameters);
        long effectiveSupply = state.effectiveSupply();
        double targetPriceIndex = EconomyMath.targetPriceIndex(effectiveSupply, economicCapacity, parameters);
        double nextPriceIndex = EconomyMath.convergePriceIndex(state.priceIndex(), targetPriceIndex, parameters);
        double periodInflationRate = (nextPriceIndex - state.priceIndex()) / state.priceIndex();
        if (!Double.isFinite(periodInflationRate)) {
            throw new ArithmeticException("period inflation rate is not finite");
        }

        ColonyEconomyState updated = new ColonyEconomyState(
            state.colonyKey(),
            state.issuedSupply(),
            state.retiredSupply(),
            state.treasuryBalance(),
            state.reservedBalance(),
            state.activeCirculation(),
            nextPriceIndex,
            state.taxRate(),
            economicCapacity,
            gameTime,
            state.schemaVersion()
        );
        return new ColonyEconomySnapshot(
            updated,
            economicCapacity,
            effectiveSupply,
            targetPriceIndex,
            periodInflationRate
        );
    }

    public static EconomyPreflight simulateMint(
        ColonyEconomyState state,
        long amount,
        long economicCapacity,
        EconomyParameters parameters
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(parameters, "parameters");
        if (amount <= 0L) {
            throw new IllegalArgumentException("mint amount must be positive");
        }
        if (economicCapacity <= 0L) {
            throw new IllegalArgumentException("economicCapacity must be positive");
        }

        long currentEffectiveSupply = state.effectiveSupply();
        long projectedEffectiveSupply = Math.addExact(currentEffectiveSupply, amount);
        double projectedTargetPriceIndex = EconomyMath.targetPriceIndex(
            projectedEffectiveSupply,
            economicCapacity,
            parameters
        );
        return new EconomyPreflight(
            state,
            currentEffectiveSupply,
            projectedEffectiveSupply,
            economicCapacity,
            state.priceIndex(),
            projectedTargetPriceIndex
        );
    }
}
