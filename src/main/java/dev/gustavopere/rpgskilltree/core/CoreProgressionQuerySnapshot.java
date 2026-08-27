package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Stable read-only projection for quest/provider integrations.
 *
 * <p>This contract is deliberately distinct from the client network snapshot so
 * UI/packet evolution does not become an integration API. Main-tree budget
 * availability and grandfathered overage are represented separately and never
 * become negative.</p>
 */
public record CoreProgressionQuerySnapshot(
    long level,
    long xpIntoLevel,
    BigInteger xpToNextLevel,
    long totalCorePoints,
    long availableCorePoints,
    long attributeAllocatedCorePoints,
    long mainPerkAllocatedCorePoints,
    long mainPerkBudgetTotal,
    long mainPerkBudgetAvailable,
    long mainPerkBudgetOverage,
    AttributeRanks attributeRanks,
    long rulesVersion,
    String rulesFingerprint
) {
    private static final Pattern SHA256 = Pattern.compile("[0-9a-f]{64}");

    public CoreProgressionQuerySnapshot {
        Objects.requireNonNull(xpToNextLevel, "xpToNextLevel");
        Objects.requireNonNull(attributeRanks, "attributeRanks");
        Objects.requireNonNull(rulesFingerprint, "rulesFingerprint");
        if (level < 0L) throw new IllegalArgumentException("level must be non-negative");
        if (xpIntoLevel < 0L) throw new IllegalArgumentException("xpIntoLevel must be non-negative");
        if (xpToNextLevel.signum() < 0) throw new IllegalArgumentException("xpToNextLevel must be non-negative");
        if (totalCorePoints < 0L) throw new IllegalArgumentException("totalCorePoints must be non-negative");
        if (availableCorePoints < 0L) throw new IllegalArgumentException("availableCorePoints must be non-negative");
        if (attributeAllocatedCorePoints < 0L) {
            throw new IllegalArgumentException("attributeAllocatedCorePoints must be non-negative");
        }
        if (mainPerkAllocatedCorePoints < 0L) {
            throw new IllegalArgumentException("mainPerkAllocatedCorePoints must be non-negative");
        }
        if (mainPerkBudgetTotal < 0L) throw new IllegalArgumentException("mainPerkBudgetTotal must be non-negative");
        if (mainPerkBudgetAvailable < 0L) {
            throw new IllegalArgumentException("mainPerkBudgetAvailable must be non-negative");
        }
        if (mainPerkBudgetOverage < 0L) {
            throw new IllegalArgumentException("mainPerkBudgetOverage must be non-negative");
        }
        if (rulesVersion <= 0L) throw new IllegalArgumentException("rulesVersion must be positive");
        if (!SHA256.matcher(rulesFingerprint).matches()) {
            throw new IllegalArgumentException("rulesFingerprint must be a lowercase SHA-256 hex digest");
        }

        long allocatedCorePoints = Math.addExact(attributeAllocatedCorePoints, mainPerkAllocatedCorePoints);
        if (allocatedCorePoints > totalCorePoints || availableCorePoints != totalCorePoints - allocatedCorePoints) {
            throw new IllegalArgumentException("Core point query is internally inconsistent");
        }

        if (mainPerkAllocatedCorePoints <= mainPerkBudgetTotal) {
            if (mainPerkBudgetAvailable != mainPerkBudgetTotal - mainPerkAllocatedCorePoints
                || mainPerkBudgetOverage != 0L) {
                throw new IllegalArgumentException("main perk budget query is internally inconsistent");
            }
        } else if (mainPerkBudgetAvailable != 0L
            || mainPerkBudgetOverage != mainPerkAllocatedCorePoints - mainPerkBudgetTotal) {
            throw new IllegalArgumentException("grandfathered main perk overage is internally inconsistent");
        }

        if (level == Long.MAX_VALUE) {
            if (xpIntoLevel != 0L || xpToNextLevel.signum() != 0) {
                throw new IllegalArgumentException("technical maximum level cannot have partial or next-level XP");
            }
        } else {
            if (xpToNextLevel.signum() <= 0) {
                throw new IllegalArgumentException("non-terminal level must have a positive next-level cost");
            }
            if (BigInteger.valueOf(xpIntoLevel).compareTo(xpToNextLevel) >= 0) {
                throw new IllegalArgumentException("xpIntoLevel must be below the next-level cost");
            }
        }
    }
}
