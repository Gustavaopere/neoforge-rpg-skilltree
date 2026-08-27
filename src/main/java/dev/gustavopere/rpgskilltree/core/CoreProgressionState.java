package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.regex.Pattern;

/** Persisted authoritative state for the uncapped RPG Core foundation. */
public record CoreProgressionState(
    CharacterProgressionState characterProgression,
    CorePointLedger corePoints,
    long rulesVersion,
    String rulesFingerprint,
    int migrationSourceFormatVersion,
    long discardedLegacyCapXp
) {
    private static final Pattern SHA256 = Pattern.compile("[0-9a-f]{64}");

    public CoreProgressionState {
        Objects.requireNonNull(characterProgression);
        Objects.requireNonNull(corePoints);
        Objects.requireNonNull(rulesFingerprint);
        if (rulesVersion <= 0L) throw new IllegalArgumentException("rulesVersion must be positive");
        if (!SHA256.matcher(rulesFingerprint).matches()) {
            throw new IllegalArgumentException("rulesFingerprint must be a lowercase SHA-256 hex digest");
        }
        if (migrationSourceFormatVersion < 0) {
            throw new IllegalArgumentException("migrationSourceFormatVersion must be non-negative");
        }
        if (discardedLegacyCapXp < 0L) {
            throw new IllegalArgumentException("discardedLegacyCapXp must be non-negative");
        }
        if (migrationSourceFormatVersion == 0 && discardedLegacyCapXp != 0L) {
            throw new IllegalArgumentException("native progression cannot contain discarded legacy XP");
        }
    }

    public static CoreProgressionState nativeState(
        CharacterProgressionState characterProgression,
        CorePointLedger corePoints,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(rules);
        return new CoreProgressionState(
            characterProgression,
            corePoints,
            rules.version(),
            rules.fingerprint(),
            0,
            0L
        );
    }

    public static CoreProgressionState fromMigration(LegacyProgressionMigrationResult migration) {
        Objects.requireNonNull(migration);
        return new CoreProgressionState(
            migration.characterProgression(),
            migration.corePoints(),
            migration.targetRulesVersion(),
            migration.targetRulesFingerprint(),
            migration.sourceFormatVersion(),
            migration.discardedLegacyCapXp()
        );
    }
}
