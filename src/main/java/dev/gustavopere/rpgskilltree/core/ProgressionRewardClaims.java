package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Persistent exact-replay protection for one-time typed progression rewards. */
public final class ProgressionRewardClaims {
    private static final ProgressionRewardClaims EMPTY = new ProgressionRewardClaims(Map.of());

    private final Map<String, String> claims;

    private ProgressionRewardClaims(Map<String, String> claims) {
        this.claims = Map.copyOf(claims);
    }

    public static ProgressionRewardClaims empty() {
        return EMPTY;
    }

    public static ProgressionRewardClaims of(Map<String, String> claims) {
        Objects.requireNonNull(claims);
        if (claims.isEmpty()) return EMPTY;
        HashMap<String, String> copy = new HashMap<>();
        for (Map.Entry<String, String> entry : claims.entrySet()) {
            String rewardId = requireText(entry.getKey(), "reward claim id");
            String payload = requireText(entry.getValue(), "reward claim payload");
            if (copy.put(rewardId, payload) != null) {
                throw new IllegalArgumentException("duplicate reward claim id: " + rewardId);
            }
        }
        return new ProgressionRewardClaims(copy);
    }

    /** Returns true for an exact replay and throws if the id was reused for a different reward. */
    public boolean isClaimed(ProgressionReward reward) {
        Objects.requireNonNull(reward);
        String existing = claims.get(reward.rewardId());
        if (existing == null) return false;
        if (existing.equals(reward.canonicalClaimPayload())) return true;
        throw new IllegalArgumentException(
            "progression reward id already claimed with different payload: " + reward.rewardId()
        );
    }

    public ProgressionRewardClaims claim(ProgressionReward reward) {
        if (isClaimed(reward)) return this;
        HashMap<String, String> next = new HashMap<>(claims);
        next.put(reward.rewardId(), reward.canonicalClaimPayload());
        return new ProgressionRewardClaims(next);
    }

    public Map<String, String> claims() {
        return claims;
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be blank");
        }
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof ProgressionRewardClaims rewardClaims
            && claims.equals(rewardClaims.claims);
    }

    @Override
    public int hashCode() {
        return claims.hashCode();
    }

    @Override
    public String toString() {
        return "ProgressionRewardClaims" + claims;
    }
}
