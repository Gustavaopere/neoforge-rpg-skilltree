package dev.gustavopere.rpgskilltree.runtime.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import dev.gustavopere.rpgskilltree.core.CreateAdvancementMasteryPolicy;
import dev.gustavopere.rpgskilltree.core.DiscoveryProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.CreateMasteryMilestoneRuntime;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.ServerPlayer;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class A0051A0060Chat3CreateMasteryCoverageJUnitTest {
    @Test
    void newMilestoneAwardsMasteryAndPersistsDiscovery() {
        ServerPlayer player = mock(ServerPlayer.class);
        var milestone = CreateAdvancementMasteryPolicy.confirmed("create", "mechanical_press").orElseThrow();

        try (MockedStatic<PlayerProgressionRuntime> progression = mockStatic(PlayerProgressionRuntime.class)) {
            progression.when(() -> PlayerProgressionRuntime.get(player)).thenReturn(ProgressionState.empty());

            assertTrue(CreateMasteryMilestoneRuntime.awardIfNew(player, milestone));

            progression.verify(() -> PlayerProgressionRuntime.awardMasteryAndDiscoveries(
                player,
                milestone.awards(),
                List.of(milestone.discoveryKey())
            ), times(1));
        }
    }

    @Test
    void discoveredMilestoneRejectsReplayBeforeMutation() {
        ServerPlayer player = mock(ServerPlayer.class);
        var milestone = CreateAdvancementMasteryPolicy.confirmed("create", "mechanical_press").orElseThrow();
        ProgressionState discovered = ProgressionState.empty().withDiscoveries(
            DiscoveryProgress.of(Set.of(milestone.discoveryKey()))
        );

        try (MockedStatic<PlayerProgressionRuntime> progression = mockStatic(PlayerProgressionRuntime.class)) {
            progression.when(() -> PlayerProgressionRuntime.get(player)).thenReturn(discovered);

            assertFalse(CreateMasteryMilestoneRuntime.awardIfNew(player, milestone));

            progression.verify(() -> PlayerProgressionRuntime.awardMasteryAndDiscoveries(
                player,
                milestone.awards(),
                List.of(milestone.discoveryKey())
            ), never());
        }
    }
}
