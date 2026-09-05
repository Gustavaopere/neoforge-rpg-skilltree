package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.api.RpgQuestProgressionApi;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionQuerySnapshot;
import dev.gustavopere.rpgskilltree.core.MasteryAward;
import dev.gustavopere.rpgskilltree.core.MasteryLaneCatalog;
import dev.gustavopere.rpgskilltree.core.QuestProgressionSnapshot;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import dev.gustavopere.rpgskilltree.runtime.CorePlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

/**
 * Loaded-NeoForge coverage for the shared quest-to-mastery boundary using a canonical
 * Epic Fight mastery lane. The plain JUnit source set cannot instrument ServerPlayer.
 */
final class A0031A0040EpicFightQuestRewardCoverageJUnitTest {
    @Test
    void authorizedQuestRewardDelegatesEpicFightMasteryToCanonicalRuntime() {
        ServerPlayer player = mock(ServerPlayer.class);
        CoreProgressionQuerySnapshot core = mock(CoreProgressionQuerySnapshot.class);
        MasteryAward reward = MasteryAward.replaySafe(
            MasteryLaneCatalog.EPICFIGHT_WEAPON,
            25,
            "rpgskilltree:quest/combat_initiation",
            "quest:combat_initiation/reward/mastery"
        );

        try (MockedStatic<PlayerProgressionRuntime> progressionRuntime = mockStatic(PlayerProgressionRuntime.class);
             MockedStatic<CorePlayerProgressionRuntime> coreRuntime = mockStatic(CorePlayerProgressionRuntime.class);
             MockedStatic<CanonicalPlayerAttachmentRuntime> attachmentRuntime = mockStatic(CanonicalPlayerAttachmentRuntime.class)) {
            coreRuntime.when(() -> CorePlayerProgressionRuntime.queryProgression(player)).thenReturn(core);
            attachmentRuntime.when(() -> CanonicalPlayerAttachmentRuntime.observe(player))
                .thenReturn(CanonicalPlayerAttachmentData.empty());

            QuestProgressionSnapshot snapshot = RpgQuestProgressionApi.applyAuthorizedMasteryReward(player, reward);

            progressionRuntime.verify(
                () -> PlayerProgressionRuntime.awardMastery(player, List.of(reward)),
                times(1)
            );
            assertSame(core, snapshot.core());
        }
    }
}
