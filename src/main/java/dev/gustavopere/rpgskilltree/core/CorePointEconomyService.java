package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Applies point transactions while enforcing the independent main-tree budget. */
public final class CorePointEconomyService {
    private CorePointEconomyService() {}

    public static CorePointLedger apply(
        CorePointLedger ledger,
        MainPerkBudget perkBudget,
        CorePointTransaction transaction
    ) {
        Objects.requireNonNull(ledger);
        Objects.requireNonNull(perkBudget);
        Objects.requireNonNull(transaction);

        long currentMainPerkAllocation = ledger.allocated(CorePointAllocation.MAIN_PERK);
        if (currentMainPerkAllocation > perkBudget.total()) {
            throw new IllegalStateException("persisted main perk allocation exceeds current perk budget");
        }

        if (transaction.kind() == CorePointTransactionKind.SPEND
            && transaction.allocation() == CorePointAllocation.MAIN_PERK) {
            long projected = Math.addExact(currentMainPerkAllocation, transaction.amount());
            if (projected > perkBudget.total()) {
                throw new IllegalArgumentException("main perk allocation exceeds perk budget");
            }
        }

        return ledger.apply(transaction);
    }
}
