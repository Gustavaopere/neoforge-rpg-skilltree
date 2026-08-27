package dev.gustavopere.rpgskilltree.core;

/**
 * Versionable policy that converts completed Character Level transitions into
 * Core Progression Point credits.
 *
 * <p>Implementations must resolve ranges without iterating once per level so
 * legitimate large XP grants remain bounded in runtime cost.</p>
 */
public interface LevelCorePointAwardPolicy {
    long pointsAwarded(long beforeLevel, long afterLevel);

    /** Stable content representation used by progression-rules fingerprints. */
    String canonicalForm();
}
