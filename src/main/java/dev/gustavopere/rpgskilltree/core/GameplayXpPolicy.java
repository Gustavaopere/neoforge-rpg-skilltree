package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

/**
 * Initial character-XP policy for concrete gameplay events.
 *
 * <p>These values are deliberately bounded and are expected to become data/config driven once
 * runtime telemetry gives us real progression timings. Passive boss points remain governed by
 * {@link BossRewardRegistry}; this policy only handles repeatable character XP.</p>
 */
public final class GameplayXpPolicy {
    private static final long NORMAL_COMBAT_MIN = 10L;
    private static final long NORMAL_COMBAT_MAX = 150L;
    private static final long BOSS_COMBAT_MIN = 100L;
    private static final long BOSS_COMBAT_MAX = 2_000L;

    private GameplayXpPolicy() {}

    public static CharacterXpAward combatKill(String entityId, double maxHealth, boolean boss) {
        if (entityId == null || entityId.isBlank()) throw new IllegalArgumentException("entityId must not be blank");
        if (!Double.isFinite(maxHealth) || maxHealth <= 0.0) throw new IllegalArgumentException("maxHealth must be positive and finite");

        long raw = boss
            ? Math.round(50.0 + maxHealth * 2.0)
            : Math.round(10.0 + maxHealth * 0.75);
        long amount = boss
            ? clamp(raw, BOSS_COMBAT_MIN, BOSS_COMBAT_MAX)
            : clamp(raw, NORMAL_COMBAT_MIN, NORMAL_COMBAT_MAX);
        String source = boss ? "combat:boss/" + entityId : "combat:kill/" + entityId;
        return new CharacterXpAward(source, amount, Set.of(ProgressionDomain.MARTIAL));
    }

    public static CharacterXpAward oreMined(String blockId, boolean rare) {
        validateDiscoveryId(blockId, "blockId");
        return new CharacterXpAward(
            "mining:ore/" + blockId,
            rare ? 20L : 8L,
            Set.of(ProgressionDomain.MINING)
        );
    }

    public static CharacterXpAward biomeDiscovery(String biomeId) {
        validateDiscoveryId(biomeId, "biomeId");
        return new CharacterXpAward(
            "exploration:biome/" + biomeId,
            25L,
            Set.of(ProgressionDomain.SURVIVAL)
        );
    }

    public static CharacterXpAward dimensionDiscovery(String dimensionId) {
        validateDiscoveryId(dimensionId, "dimensionId");
        return new CharacterXpAward(
            "exploration:dimension/" + dimensionId,
            100L,
            Set.of(ProgressionDomain.SURVIVAL)
        );
    }

    private static void validateDiscoveryId(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " must not be blank");
    }

    private static long clamp(long value, long minimum, long maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}
