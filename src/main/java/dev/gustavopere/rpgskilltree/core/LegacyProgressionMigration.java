package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/** Converts persisted 1..100 progression into the uncapped Level-0 model. */
public final class LegacyProgressionMigration {
    private LegacyProgressionMigration() {}

    public static LegacyProgressionMigrationResult migrate(
        ProgressionState legacyState,
        int sourceFormatVersion,
        ProgressionRulesSnapshot targetRules
    ) {
        Objects.requireNonNull(legacyState);
        Objects.requireNonNull(targetRules);
        if (sourceFormatVersion < 1 || sourceFormatVersion > ProgressionStateCodec.CURRENT_VERSION) {
            throw new IllegalArgumentException("unsupported legacy progression format version: " + sourceFormatVersion);
        }

        CharacterLevelCurve legacyCurve = CharacterLevelCurve.defaultCurve();
        int legacyLevel = legacyCurve.levelForTotalXp(legacyState.totalCharacterXp());
        long targetLevel = legacyLevel - 1L;
        long targetPartialXp;
        long discardedCapXp = 0L;

        if (legacyLevel == legacyCurve.maxLevel()) {
            long capFloor = legacyCurve.xpRequiredForLevel(legacyCurve.maxLevel());
            discardedCapXp = legacyState.totalCharacterXp() - capFloor;
            targetPartialXp = 0L;
        } else {
            long legacyFloor = legacyCurve.xpRequiredForLevel(legacyLevel);
            long legacyIntoLevel = legacyState.totalCharacterXp() - legacyFloor;
            long legacyLevelCost = legacyCurve.xpToNextLevel(legacyLevel);
            BigInteger targetLevelCost = targetRules.levelCurve().xpToNextLevel(targetLevel);
            if (targetLevelCost.signum() <= 0) {
                throw new IllegalArgumentException("target progression curve must have positive level costs");
            }

            BigInteger scaledPartial = BigInteger.valueOf(legacyIntoLevel)
                .multiply(targetLevelCost)
                .divide(BigInteger.valueOf(legacyLevelCost));
            try {
                targetPartialXp = scaledPartial.longValueExact();
            } catch (ArithmeticException overflow) {
                throw new ArithmeticException("migrated partial XP exceeds the persisted long representation");
            }
        }

        CorePointLedger migratedPoints = migratePointLedger(
            legacyState.passivePoints(), sourceFormatVersion, targetRules.version());
        return new LegacyProgressionMigrationResult(
            new CharacterProgressionState(targetLevel, targetPartialXp),
            migratedPoints,
            discardedCapXp,
            sourceFormatVersion,
            targetRules.version(),
            targetRules.fingerprint()
        );
    }

    private static CorePointLedger migratePointLedger(
        PassivePointLedger legacyLedger,
        int sourceFormatVersion,
        long targetRulesVersion
    ) {
        CorePointLedger migrated = CorePointLedger.empty();
        for (PassivePointSource source : legacyLedger.earnedBySource().keySet().stream()
            .sorted(Comparator.comparing(Enum::name))
            .toList()) {
            int amount = legacyLedger.earned(source);
            if (amount <= 0) continue;
            String stableSource = source.name().toLowerCase(Locale.ROOT);
            migrated = migrated.apply(CorePointTransaction.credit(
                "legacy-v" + sourceFormatVersion + ":credit:" + stableSource,
                CorePointTransactionKind.MIGRATION,
                amount,
                "legacy:passive_points/" + stableSource,
                targetRulesVersion
            ));
        }

        if (legacyLedger.spent() > 0) {
            // Legacy spending had no attribute/main-tree partition. Every legacy
            // spend belongs to the finite tree side; allocations above a new
            // budget are grandfathered and can only move downward via refunds.
            migrated = migrated.apply(CorePointTransaction.allocate(
                "legacy-v" + sourceFormatVersion + ":allocation:main_perk",
                CorePointTransactionKind.SPEND,
                legacyLedger.spent(),
                "legacy:passive_points/spent",
                CorePointAllocation.MAIN_PERK,
                targetRulesVersion
            ));
        }
        return migrated;
    }
}
