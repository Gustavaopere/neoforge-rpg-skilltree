package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Immutable, replay-addressable typed reward emitted by quest/boss/milestone adapters. */
public record ProgressionReward(
    String rewardId,
    ProgressionRewardType type,
    long amount,
    String sourceId
) {
    public ProgressionReward {
        Objects.requireNonNull(type, "type");
        rewardId = requireText(rewardId, "rewardId");
        sourceId = requireText(sourceId, "sourceId");
        if (amount <= 0L) throw new IllegalArgumentException("reward amount must be positive");
    }

    public static ProgressionReward characterXp(String rewardId, long amount, String sourceId) {
        return new ProgressionReward(rewardId, ProgressionRewardType.CHARACTER_XP, amount, sourceId);
    }

    public static ProgressionReward corePoints(String rewardId, long amount, String sourceId) {
        return new ProgressionReward(rewardId, ProgressionRewardType.CORE_POINTS, amount, sourceId);
    }

    public static ProgressionReward mainPerkBudget(String rewardId, long amount, String sourceId) {
        return new ProgressionReward(rewardId, ProgressionRewardType.MAIN_PERK_BUDGET, amount, sourceId);
    }

    public String canonicalClaimPayload() {
        return type.name() + "|amount=" + amount + "|source=" + sourceId;
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be blank");
        }
        return value;
    }
}
