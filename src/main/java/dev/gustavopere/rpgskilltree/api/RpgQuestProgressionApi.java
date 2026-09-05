package dev.gustavopere.rpgskilltree.api;

import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionQuerySnapshot;
import dev.gustavopere.rpgskilltree.core.MasteryAward;
import dev.gustavopere.rpgskilltree.core.ProgressionReward;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.QuestConditionEvaluation;
import dev.gustavopere.rpgskilltree.core.QuestMasteryRewardPolicy;
import dev.gustavopere.rpgskilltree.core.QuestProgressionCondition;
import dev.gustavopere.rpgskilltree.core.QuestProgressionConditionService;
import dev.gustavopere.rpgskilltree.core.QuestProgressionSnapshot;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import dev.gustavopere.rpgskilltree.runtime.CorePlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.List;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/**
 * Stable server-side integration surface for quest, NPC and narrative addons.
 *
 * <p>Consumers query immutable snapshots, evaluate declarative conditions and submit
 * typed replay-safe rewards. Attachments, persistence formats and balance-rule catalogs
 * remain implementation details of the RPG runtime.</p>
 */
public final class RpgQuestProgressionApi {
    /** Public integration contract version for external quest/NPC adapters. */
    public static final int CONTRACT_VERSION = QuestProgressionSnapshot.CONTRACT_VERSION;

    private RpgQuestProgressionApi() {}

    /** Returns a read-only combined snapshot without materializing progression state as a side effect. */
    public static QuestProgressionSnapshot query(ServerPlayer player) {
        Objects.requireNonNull(player, "player");
        CoreProgressionQuerySnapshot core = CorePlayerProgressionRuntime.queryProgression(player);
        CanonicalPlayerAttachmentData canonical = CanonicalPlayerAttachmentRuntime.observe(player);
        ProgressionState compatibility = canonical.compatibilityProgression();
        return QuestProgressionSnapshot.from(
            core,
            compatibility.mastery(),
            compatibility.classProgression(),
            compatibility.specializations(),
            compatibility.passiveNodes()
        );
    }

    /** Evaluates one reusable condition against a fresh authoritative server snapshot. */
    public static QuestConditionEvaluation evaluate(
        ServerPlayer player,
        QuestProgressionCondition condition
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(condition, "condition");
        return QuestProgressionConditionService.evaluate(query(player), condition);
    }

    /**
     * Applies a trusted server-side typed reward and returns the resulting public snapshot.
     * Exact reward replays remain no-ops through the persisted Core reward-claim contract.
     */
    public static QuestProgressionSnapshot applyReward(
        ServerPlayer player,
        ProgressionReward reward
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(reward, "reward");
        CorePlayerProgressionRuntime.applyProgressionReward(player, reward);
        return query(player);
    }

    /**
     * Applies a replay-safe mastery reward after the trusted narrative layer has authorized it.
     *
     * <p>The adapter remains responsible for evaluating the narrative/quest rule that permits
     * the reward. This boundary validates the mastery payload before delegating to the canonical
     * compatibility progression runtime, preserving its normal reconciliation, persistence,
     * mutation event and owner-sync pipeline.</p>
     */
    public static QuestProgressionSnapshot applyAuthorizedMasteryReward(
        ServerPlayer player,
        MasteryAward reward
    ) {
        MasteryAward validatedReward = QuestMasteryRewardPolicy.validate(reward);
        Objects.requireNonNull(player, "player");
        PlayerProgressionRuntime.awardMastery(player, List.of(validatedReward));
        return query(player);
    }
}
