package dev.gustavopere.rpgskilltree.compendium.discovery;

import java.util.Objects;

/** Immutable one-shot reward definition addressed by a stable reward id. */
public record DiscoveryRewardDefinition(
    String rewardId,
    DiscoveryRewardKind kind,
    long amount,
    String sourceId
) {
    public DiscoveryRewardDefinition {
        rewardId = requireText(rewardId, "rewardId");
        Objects.requireNonNull(kind, "kind");
        if (amount <= 0L) throw new IllegalArgumentException("reward amount must be positive");
        sourceId = requireText(sourceId, "sourceId");
    }

    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " must not be blank");
        }
        return value.trim();
    }
}
