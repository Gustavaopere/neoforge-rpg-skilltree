package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure bootstrap policy for adopting the uncapped Core progression state. */
public final class CoreProgressionBootstrap {
    private CoreProgressionBootstrap() {}

    /** Creates a native Level-0 Core state for a player with no legacy progression. */
    public static CoreProgressionState newPlayer(ProgressionRulesSnapshot rules) {
        Objects.requireNonNull(rules);
        return CoreProgressionState.nativeState(
            CharacterProgressionState.empty(),
            CorePointLedger.empty(),
            rules
        );
    }

    /**
     * Converts an already-decoded legacy attachment into the current Core model.
     *
     * <p>The legacy codec has already upgraded older binary layouts to its current
     * in-memory shape by this point, so the migration audit records that decoded
     * format version rather than inventing an unavailable original on-disk version.</p>
     */
    public static CoreProgressionState migrateDecodedLegacy(
        ProgressionState legacy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(legacy);
        Objects.requireNonNull(rules);
        CoreProgressionState migrated = CoreProgressionState.fromMigration(
            LegacyProgressionMigration.migrate(
                legacy,
                ProgressionStateCodec.CURRENT_VERSION,
                rules
            )
        );
        ProgressionRewardClaims claims = migrated.progressionRewardClaims();
        for (String discoveryKey : legacy.discoveries().discoveredKeys().stream().sorted().toList()) {
            claims = claims.claimCompletion(discoveryKey);
        }
        return migrated.withProgressionRewardClaims(claims);
    }

    /**
     * Reuses a persisted Core state only when it is interpreted by the exact same
     * rules snapshot and its partial-XP position remains valid for that curve.
     */
    public static CoreProgressionState resume(
        CoreProgressionState persisted,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(persisted);
        Objects.requireNonNull(rules);
        if (persisted.rulesVersion() != rules.version()
            || !persisted.rulesFingerprint().equals(rules.fingerprint())) {
            throw new IllegalStateException(
                "persisted Core progression rules do not match current rules snapshot"
            );
        }

        try {
            CharacterProgressionService.grantXp(
                persisted.characterProgression(),
                0L,
                rules.levelCurve()
            );
        } catch (IllegalArgumentException | ArithmeticException invalidPosition) {
            throw new IllegalStateException(
                "persisted Core character progression is invalid for current rules",
                invalidPosition
            );
        }
        return persisted;
    }
}
