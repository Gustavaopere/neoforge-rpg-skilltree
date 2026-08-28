package dev.gustavopere.rpgskilltree.api;

import dev.gustavopere.rpgskilltree.core.CoreProgressionQuerySnapshot;
import dev.gustavopere.rpgskilltree.core.ProgressionReward;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.QuestConditionEvaluation;
import dev.gustavopere.rpgskilltree.core.QuestProgressionCondition;
import dev.gustavopere.rpgskilltree.core.QuestProgressionConditionService;
import dev.gustavopere.rpgskilltree.core.QuestProgressionSnapshot;
import dev.gustavopere.rpgskilltree.runtime.CorePlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/**
 * Stable server-side integration surface for quest, NPC and narrative addons.
 *
 * <p>Consumers query immutable snapshots, evaluate declarative conditions and submit
 * typed replay-safe rewards. Attachments, codecs and balance-rule catalogs remain
 * implementation details of the RPG runtime.</p>
 */
public final class RpgQuestProgressionApi {
    private RpgQuestProgressionApi() {}

    /** Returns a read-only combined snapshot without materializing progression state as a side effect. */
    public static QuestProgressionSnapshot query(
        ServerPlayer player
    ) {
        Objects.requireNonNull(player, "player");
        CoreProgressionQuerySnapshot core = CorePlayerProgressionRuntime.queryProgression(player);
        ProgressionState legacy = legacyState(player);
        return QuestProgressionSnapshot.from(
            core,
            legacy.mastery(),
            legacy.classProgression(),
            legacy.passiveNodes()
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

    private static ProgressionState legacyState(ServerPlayer player) {
        if (player.hasData(ModAttachments.PROGRESSION)) {
            return player.getData(ModAttachments.PROGRESSION);
        }
        return ProgressionState.empty();
    }
}
