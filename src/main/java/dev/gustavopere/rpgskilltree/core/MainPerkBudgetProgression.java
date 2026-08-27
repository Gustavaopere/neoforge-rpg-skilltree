package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Player-specific increases to the finite main-perk allocation budget.
 *
 * <p>Each grant has a stable provenance id so quest, boss and milestone adapters
 * can safely replay completion notifications without widening the player's build
 * twice. The configured rules snapshot remains the base budget; this state stores
 * only earned additions.</p>
 */
public final class MainPerkBudgetProgression {
    private static final MainPerkBudgetProgression EMPTY = new MainPerkBudgetProgression(Map.of(), 0L);

    private final Map<String, Long> grants;
    private final long bonus;

    private MainPerkBudgetProgression(Map<String, Long> grants, long bonus) {
        this.grants = Map.copyOf(grants);
        this.bonus = bonus;
    }

    public static MainPerkBudgetProgression empty() {
        return EMPTY;
    }

    public static MainPerkBudgetProgression of(Map<String, Long> grants) {
        Objects.requireNonNull(grants);
        if (grants.isEmpty()) return EMPTY;

        HashMap<String, Long> copy = new HashMap<>();
        long total = 0L;
        for (Map.Entry<String, Long> entry : grants.entrySet()) {
            String grantId = requireGrantId(entry.getKey());
            Long amount = Objects.requireNonNull(entry.getValue(), "perk budget grant amount");
            if (amount <= 0L) throw new IllegalArgumentException("perk budget grant amount must be positive");
            if (copy.put(grantId, amount) != null) {
                throw new IllegalArgumentException("duplicate perk budget grant id: " + grantId);
            }
            total = Math.addExact(total, amount);
        }
        return new MainPerkBudgetProgression(copy, total);
    }

    public MainPerkBudgetProgression grant(String grantId, long amount) {
        String validatedId = requireGrantId(grantId);
        if (amount <= 0L) throw new IllegalArgumentException("perk budget grant amount must be positive");

        Long existing = grants.get(validatedId);
        if (existing != null) {
            if (existing == amount) return this;
            throw new IllegalArgumentException(
                "perk budget grant id already used with different amount: " + validatedId
            );
        }

        long nextBonus = Math.addExact(bonus, amount);
        HashMap<String, Long> next = new HashMap<>(grants);
        next.put(validatedId, amount);
        return new MainPerkBudgetProgression(next, nextBonus);
    }

    public Map<String, Long> grants() {
        return grants;
    }

    public long bonus() {
        return bonus;
    }

    public MainPerkBudget effectiveBudget(MainPerkBudget baseBudget) {
        Objects.requireNonNull(baseBudget);
        if (bonus == 0L) return baseBudget;
        return baseBudget.increase(bonus);
    }

    private static String requireGrantId(String grantId) {
        if (grantId == null || grantId.isBlank()) {
            throw new IllegalArgumentException("perk budget grant id must not be blank");
        }
        return grantId;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof MainPerkBudgetProgression progression
            && bonus == progression.bonus
            && grants.equals(progression.grants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grants, bonus);
    }

    @Override
    public String toString() {
        return "MainPerkBudgetProgression[bonus=" + bonus + ", grants=" + grants + "]";
    }
}
