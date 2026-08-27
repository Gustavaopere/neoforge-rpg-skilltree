package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Pattern;

/** Compact client-facing projection of the authoritative Core progression state. */
public record CoreProgressionSyncState(
    long level,
    long xpIntoLevel,
    BigInteger xpToNextLevel,
    long totalCorePoints,
    long attributeAllocated,
    long mainPerkAllocated,
    long availableCorePoints,
    long mainPerkBudget,
    long rulesVersion,
    String rulesFingerprint
) {
    private static final Pattern SHA256 = Pattern.compile("[0-9a-f]{64}");

    public CoreProgressionSyncState {
        Objects.requireNonNull(xpToNextLevel);
        Objects.requireNonNull(rulesFingerprint);
        if (level < 0L) throw new IllegalArgumentException("level must be non-negative");
        if (xpIntoLevel < 0L) throw new IllegalArgumentException("xpIntoLevel must be non-negative");
        if (xpToNextLevel.signum() < 0) throw new IllegalArgumentException("xpToNextLevel must be non-negative");
        if (totalCorePoints < 0L) throw new IllegalArgumentException("totalCorePoints must be non-negative");
        if (attributeAllocated < 0L) throw new IllegalArgumentException("attributeAllocated must be non-negative");
        if (mainPerkAllocated < 0L) throw new IllegalArgumentException("mainPerkAllocated must be non-negative");
        if (availableCorePoints < 0L) throw new IllegalArgumentException("availableCorePoints must be non-negative");
        if (mainPerkBudget < 0L) throw new IllegalArgumentException("mainPerkBudget must be non-negative");
        if (rulesVersion <= 0L) throw new IllegalArgumentException("rulesVersion must be positive");
        if (!SHA256.matcher(rulesFingerprint).matches()) {
            throw new IllegalArgumentException("rulesFingerprint must be a lowercase SHA-256 hex digest");
        }
        long allocated = Math.addExact(attributeAllocated, mainPerkAllocated);
        if (allocated > totalCorePoints || availableCorePoints != totalCorePoints - allocated) {
            throw new IllegalArgumentException("Core point projection is internally inconsistent");
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

    public static CoreProgressionSyncState from(
        CoreProgressionState state,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        CharacterProgressionState character = validated.characterProgression();
        CorePointLedger ledger = validated.corePoints();
        BigInteger nextCost = character.level() == Long.MAX_VALUE
            ? BigInteger.ZERO
            : rules.levelCurve().xpToNextLevel(character.level());
        return new CoreProgressionSyncState(
            character.level(),
            character.xpIntoLevel(),
            nextCost,
            ledger.totalCredits(),
            ledger.allocated(CorePointAllocation.ATTRIBUTE),
            ledger.allocated(CorePointAllocation.MAIN_PERK),
            ledger.available(),
            rules.mainPerkBudget().total(),
            rules.version(),
            rules.fingerprint()
        );
    }
}
