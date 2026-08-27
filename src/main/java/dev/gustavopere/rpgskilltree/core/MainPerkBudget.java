package dev.gustavopere.rpgskilltree.core;

/** Finite allocation ceiling for the main perk tree. */
public record MainPerkBudget(long total) {
    public MainPerkBudget {
        if (total < 0L) throw new IllegalArgumentException("perk budget must be non-negative");
    }

    public MainPerkBudget increase(long amount) {
        if (amount <= 0L) throw new IllegalArgumentException("budget increase must be positive");
        return new MainPerkBudget(Math.addExact(total, amount));
    }
}
