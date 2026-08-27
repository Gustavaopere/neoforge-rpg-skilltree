package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Auditable output of converting a legacy 1..100 progression save. */
public record LegacyProgressionMigrationResult(
    CharacterProgressionState characterProgression,
    CorePointLedger corePoints,
    long discardedLegacyCapXp,
    int sourceFormatVersion,
    long targetRulesVersion,
    String targetRulesFingerprint
) {
    public LegacyProgressionMigrationResult {
        Objects.requireNonNull(characterProgression);
        Objects.requireNonNull(corePoints);
        Objects.requireNonNull(targetRulesFingerprint);
        if (discardedLegacyCapXp < 0L) {
            throw new IllegalArgumentException("discardedLegacyCapXp must be non-negative");
        }
        if (sourceFormatVersion <= 0) {
            throw new IllegalArgumentException("sourceFormatVersion must be positive");
        }
        if (targetRulesVersion <= 0L) {
            throw new IllegalArgumentException("targetRulesVersion must be positive");
        }
        if (targetRulesFingerprint.isBlank()) {
            throw new IllegalArgumentException("targetRulesFingerprint must not be blank");
        }
    }
}
