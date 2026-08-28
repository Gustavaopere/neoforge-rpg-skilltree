package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryRewardDefinition;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryRewardKind;
import dev.gustavopere.rpgskilltree.core.ProgressionReward;
import dev.gustavopere.rpgskilltree.runtime.CorePlayerProgressionRuntime;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/** Executes trusted one-shot discovery rewards through canonical progression services. */
public final class CompendiumDiscoveryRewardBridge {
    private static final String CLAIM_PREFIX = "compendium_discovery:";

    private CompendiumDiscoveryRewardBridge() {}

    public static void apply(ServerPlayer player, DiscoveryRewardDefinition reward) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(reward, "reward");

        if (reward.kind() != DiscoveryRewardKind.CHARACTER_XP) {
            throw new UnsupportedOperationException("unsupported discovery reward kind: " + reward.kind());
        }

        ProgressionReward typedReward = ProgressionReward.characterXp(
            CLAIM_PREFIX + reward.rewardId(),
            reward.amount(),
            reward.sourceId()
        );
        CorePlayerProgressionRuntime.applyProgressionReward(player, typedReward);
    }
}
