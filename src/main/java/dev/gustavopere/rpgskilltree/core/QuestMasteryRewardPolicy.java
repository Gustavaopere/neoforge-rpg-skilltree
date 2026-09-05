package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Safety boundary for mastery rewards emitted by trusted quest and narrative adapters.
 *
 * <p>Quest rewards must target a canonical mastery lane and carry a stable replay key.
 * Narrative eligibility remains the responsibility of the trusted server-side caller;
 * this policy validates the mutation payload before it reaches the canonical mastery runtime.</p>
 */
public final class QuestMasteryRewardPolicy {
    private QuestMasteryRewardPolicy() {}

    public static MasteryAward validate(MasteryAward reward) {
        Objects.requireNonNull(reward, "reward");
        if (!MasteryLaneCatalog.isCanonical(reward.laneId())) {
            throw new IllegalArgumentException("quest mastery reward requires a canonical mastery lane: " + reward.laneId());
        }
        if (!reward.replaySafe()) {
            throw new IllegalArgumentException("quest mastery reward requires a stable replay key");
        }
        return reward;
    }
}
