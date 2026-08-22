package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class BossProgress {
    private final Set<String> creditedRewardKeys;

    private BossProgress(Set<String> creditedRewardKeys) {
        this.creditedRewardKeys = Set.copyOf(creditedRewardKeys);
    }

    public static BossProgress empty() {
        return new BossProgress(Set.of());
    }

    public static BossProgress of(Set<String> creditedRewardKeys) {
        Objects.requireNonNull(creditedRewardKeys);
        if (creditedRewardKeys.stream().anyMatch(key -> key == null || key.isBlank())) {
            throw new IllegalArgumentException("boss reward keys must not be blank");
        }
        return new BossProgress(creditedRewardKeys);
    }

    public BossRewardResult creditFirstDefeat(String rewardKey, BossRewardDefinition definition) {
        Objects.requireNonNull(rewardKey);
        Objects.requireNonNull(definition);
        if (rewardKey.isBlank()) throw new IllegalArgumentException("rewardKey must not be blank");
        if (creditedRewardKeys.contains(rewardKey)) return new BossRewardResult(this, 0, false);
        Set<String> next = new HashSet<>(creditedRewardKeys);
        next.add(rewardKey);
        return new BossRewardResult(new BossProgress(next), definition.points(), true);
    }

    public boolean hasCredit(String rewardKey) {
        return creditedRewardKeys.contains(rewardKey);
    }

    public Set<String> creditedRewardKeys() {
        return creditedRewardKeys;
    }
}
