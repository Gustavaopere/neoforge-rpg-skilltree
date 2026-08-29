package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Converts an auditable reward-risk result into bounded quantity growth for existing stackable loot.
 *
 * <p>This policy never creates new loot entries and never reduces an existing drop. Quantity growth
 * has its own multiplier ceiling plus per-stack and per-kill extra-item budgets, so XP growth cannot
 * silently turn rare or boss drops into exponential farms.</p>
 */
public final class CappedEntityLootQuantityPolicy {
    private final BigDecimal maximumMultiplier;
    private final int maxExtraPerStack;
    private final int maxExtraPerKill;

    private CappedEntityLootQuantityPolicy(
        BigDecimal maximumMultiplier,
        int maxExtraPerStack,
        int maxExtraPerKill
    ) {
        this.maximumMultiplier = maximumMultiplier;
        this.maxExtraPerStack = maxExtraPerStack;
        this.maxExtraPerKill = maxExtraPerKill;
    }

    public static CappedEntityLootQuantityPolicy of(
        BigDecimal maximumMultiplier,
        int maxExtraPerStack,
        int maxExtraPerKill
    ) {
        Objects.requireNonNull(maximumMultiplier, "maximumMultiplier");
        if (maximumMultiplier.compareTo(BigDecimal.ONE) < 0) {
            throw new IllegalArgumentException("maximumMultiplier must be at least 1");
        }
        if (maxExtraPerStack < 0) {
            throw new IllegalArgumentException("maxExtraPerStack must be non-negative");
        }
        if (maxExtraPerKill < 0) {
            throw new IllegalArgumentException("maxExtraPerKill must be non-negative");
        }
        return new CappedEntityLootQuantityPolicy(maximumMultiplier, maxExtraPerStack, maxExtraPerKill);
    }

    /**
     * Returns the final count for one existing drop stack.
     *
     * @param currentCount current stack count; it is never decreased
     * @param maxStackSize item stack-size boundary; values at or below 1 are never duplicated
     * @param extraGrantedThisKill extra items already granted to earlier stacks in this death event
     * @param scaling already-resolved canonical reward-risk result
     */
    public int scaleCount(
        int currentCount,
        int maxStackSize,
        int extraGrantedThisKill,
        EntityRewardScalingResult scaling
    ) {
        if (currentCount < 0) {
            throw new IllegalArgumentException("currentCount must be non-negative");
        }
        if (maxStackSize <= 0) {
            throw new IllegalArgumentException("maxStackSize must be positive");
        }
        if (extraGrantedThisKill < 0) {
            throw new IllegalArgumentException("extraGrantedThisKill must be non-negative");
        }
        Objects.requireNonNull(scaling, "scaling");

        if (currentCount == 0 || maxStackSize <= 1 || currentCount >= maxStackSize) {
            return currentCount;
        }

        int remainingKillBudget = Math.max(0, maxExtraPerKill - extraGrantedThisKill);
        if (remainingKillBudget == 0 || maxExtraPerStack == 0) {
            return currentCount;
        }

        BigDecimal multiplier = scaling.finalMultiplier().min(maximumMultiplier);
        if (multiplier.compareTo(BigDecimal.ONE) <= 0) {
            return currentCount;
        }

        BigDecimal scaled = BigDecimal.valueOf(currentCount).multiply(multiplier);
        BigDecimal integral = scaled.setScale(0, RoundingMode.DOWN);
        long desiredCount;
        try {
            desiredCount = integral.longValueExact();
        } catch (ArithmeticException exception) {
            desiredCount = Long.MAX_VALUE;
        }

        long desiredExtra = Math.max(0L, desiredCount - currentCount);
        long stackRoom = (long) maxStackSize - currentCount;
        long grantedExtra = Math.min(
            Math.min(desiredExtra, maxExtraPerStack),
            Math.min(remainingKillBudget, stackRoom)
        );
        return currentCount + (int) grantedExtra;
    }

    public BigDecimal maximumMultiplier() {
        return maximumMultiplier;
    }

    public int maxExtraPerStack() {
        return maxExtraPerStack;
    }

    public int maxExtraPerKill() {
        return maxExtraPerKill;
    }
}
