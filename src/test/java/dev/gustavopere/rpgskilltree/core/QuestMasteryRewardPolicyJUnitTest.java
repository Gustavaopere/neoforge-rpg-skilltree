package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.api.RpgQuestProgressionApi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class QuestMasteryRewardPolicyJUnitTest {
    @Test
    void acceptsCanonicalReplaySafeRewards() {
        MasteryAward reward = MasteryAward.replaySafe(
            MasteryLaneCatalog.MAGIC_CASTING,
            25,
            "rpgskilltree:quest/arcane_initiation",
            "quest:arcane_initiation/reward/mastery"
        );

        assertSame(reward, QuestMasteryRewardPolicy.validate(reward));
    }

    @Test
    void rejectsNonCanonicalMasteryLanes() {
        MasteryAward reward = MasteryAward.replaySafe(
            "quest:invented_lane",
            25,
            "rpgskilltree:quest/invalid_lane",
            "quest:invalid_lane/reward/mastery"
        );

        assertThrows(IllegalArgumentException.class, () -> QuestMasteryRewardPolicy.validate(reward));
    }

    @Test
    void rejectsRepeatableUntrackedRewards() {
        MasteryAward reward = new MasteryAward(
            MasteryLaneCatalog.MAGIC_CASTING,
            25,
            "rpgskilltree:quest/repeatable_without_identity"
        );

        assertThrows(IllegalArgumentException.class, () -> QuestMasteryRewardPolicy.validate(reward));
    }

    @Test
    void publicApiRejectsInvalidMasteryBeforePlayerRuntime() {
        MasteryAward reward = MasteryAward.replaySafe(
            "quest:invented_lane",
            25,
            "rpgskilltree:quest/invalid_lane",
            "quest:invalid_lane/reward/mastery"
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> RpgQuestProgressionApi.applyAuthorizedMasteryReward(null, reward)
        );
    }

    @Test
    void publicApiRequiresPlayerAfterValidMasteryValidation() {
        MasteryAward reward = MasteryAward.replaySafe(
            MasteryLaneCatalog.MAGIC_CASTING,
            25,
            "rpgskilltree:quest/arcane_initiation",
            "quest:arcane_initiation/reward/mastery"
        );

        assertThrows(
            NullPointerException.class,
            () -> RpgQuestProgressionApi.applyAuthorizedMasteryReward(null, reward)
        );
    }
}
