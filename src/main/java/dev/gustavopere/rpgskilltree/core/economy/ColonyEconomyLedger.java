package dev.gustavopere.rpgskilltree.core.economy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Canonical V1 monetary mutation service.
 *
 * <p>Only MINT and RETIRE have audited executable semantics. Every other modeled kind remains
 * fail-closed until its counterparty/authority contract is defined.</p>
 */
public final class ColonyEconomyLedger {
    private final List<EconomyTransaction> transactions = new ArrayList<>();
    private final Set<UUID> transactionIds = new HashSet<>();
    private final Set<String> causalKeys = new HashSet<>();

    public EconomyMutationResult apply(ColonyEconomyState state, EconomyCommand command, long gameTime) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(command, "command");

        if (transactionIds.contains(command.transactionId()) || causalKeys.contains(command.causalKey())) {
            return EconomyMutationResult.rejected(EconomyMutationResult.Status.DUPLICATE, state);
        }
        if (command.amount() <= 0L) {
            return EconomyMutationResult.rejected(EconomyMutationResult.Status.INVALID_AMOUNT, state);
        }

        return switch (command.kind()) {
            case MINT -> mint(state, command, gameTime);
            case RETIRE -> retire(state, command, gameTime);
            case ADMIN_ADJUSTMENT, TAX, CONSTRUCTION_CHARGE, REFUND, TREASURY_DEPOSIT, TREASURY_WITHDRAWAL ->
                EconomyMutationResult.rejected(EconomyMutationResult.Status.UNSUPPORTED_KIND, state);
        };
    }

    public List<EconomyTransaction> transactions() {
        return List.copyOf(transactions);
    }

    private EconomyMutationResult mint(ColonyEconomyState state, EconomyCommand command, long gameTime) {
        try {
            long issuedSupply = Math.addExact(state.issuedSupply(), command.amount());
            long treasuryBalance = Math.addExact(state.treasuryBalance(), command.amount());
            ColonyEconomyState updated = copyMoneyState(
                state,
                issuedSupply,
                state.retiredSupply(),
                treasuryBalance
            );
            return recordApplied(updated, command, gameTime);
        } catch (ArithmeticException failure) {
            return EconomyMutationResult.rejected(EconomyMutationResult.Status.OVERFLOW, state);
        }
    }

    private EconomyMutationResult retire(ColonyEconomyState state, EconomyCommand command, long gameTime) {
        if (state.treasuryBalance() < command.amount()) {
            return EconomyMutationResult.rejected(EconomyMutationResult.Status.INSUFFICIENT_TREASURY, state);
        }

        try {
            long treasuryBalance = Math.subtractExact(state.treasuryBalance(), command.amount());
            long retiredSupply = Math.addExact(state.retiredSupply(), command.amount());
            ColonyEconomyState updated = copyMoneyState(
                state,
                state.issuedSupply(),
                retiredSupply,
                treasuryBalance
            );
            return recordApplied(updated, command, gameTime);
        } catch (ArithmeticException failure) {
            return EconomyMutationResult.rejected(EconomyMutationResult.Status.OVERFLOW, state);
        }
    }

    private EconomyMutationResult recordApplied(
        ColonyEconomyState updated,
        EconomyCommand command,
        long gameTime
    ) {
        EconomyTransaction transaction = new EconomyTransaction(
            command.transactionId(),
            command.causalKey(),
            command.kind(),
            command.amount(),
            gameTime,
            updated.issuedSupply(),
            updated.retiredSupply(),
            updated.treasuryBalance()
        );
        transactions.add(transaction);
        transactionIds.add(command.transactionId());
        causalKeys.add(command.causalKey());
        return EconomyMutationResult.applied(updated, transaction);
    }

    private static ColonyEconomyState copyMoneyState(
        ColonyEconomyState state,
        long issuedSupply,
        long retiredSupply,
        long treasuryBalance
    ) {
        return new ColonyEconomyState(
            state.colonyKey(),
            issuedSupply,
            retiredSupply,
            treasuryBalance,
            state.reservedBalance(),
            state.activeCirculation(),
            state.priceIndex(),
            state.taxRate(),
            state.currentEconomicCapacity(),
            state.lastSettlementTick(),
            state.schemaVersion()
        );
    }
}
