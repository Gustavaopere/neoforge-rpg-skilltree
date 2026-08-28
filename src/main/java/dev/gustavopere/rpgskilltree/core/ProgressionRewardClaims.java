package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Persistent exact-replay and first-completion protection for progression rewards. */
public final class ProgressionRewardClaims {
    private static final ProgressionRewardClaims EMPTY = new ProgressionRewardClaims(Map.of());
    private static final String COMPLETION_PREFIX = "completion:";
    private static final String COMPLETION_PAYLOAD = "completion:v1";

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

    /**
     * Returns whether a stable first-completion key has already been claimed.
     *
     * <p>Completion claims intentionally do not encode XP amount or balance revision.
     * Once a biome, dimension, advancement or similar semantic completion happened,
     * later balance changes must not make it eligible again or turn replay into a
     * payload mismatch.</p>
     */
    public boolean isCompletionClaimed(String completionKey) {
        String claimId = completionClaimId(completionKey);
        String existing = claims.get(claimId);
        if (existing == null) return false;
        if (COMPLETION_PAYLOAD.equals(existing)) return true;
        throw new IllegalArgumentException(
            "completion claim id is already used by a different claim payload: " + claimId
        );
    }

    public ProgressionRewardClaims claimCompletion(String completionKey) {
        String claimId = completionClaimId(completionKey);
        if (isCompletionClaimed(completionKey)) return this;
        HashMap<String, String> next = new HashMap<>(claims);
        next.put(claimId, COMPLETION_PAYLOAD);
        return new ProgressionRewardClaims(next);
    }

    public Map<String, String> claims() {
        return claims;
    }

    private static String completionClaimId(String completionKey) {
        return COMPLETION_PREFIX + requireText(completionKey, "completionKey");
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
