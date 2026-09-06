package dev.gustavopere.rpgskilltree.core.economy;

/** Canonical monetary mutation kinds. Only explicitly audited kinds are executable in V1. */
public enum EconomyTransactionKind {
    MINT,
    RETIRE,
    ADMIN_ADJUSTMENT,
    TAX,
    CONSTRUCTION_CHARGE,
    REFUND,
    TREASURY_DEPOSIT,
    TREASURY_WITHDRAWAL
}
