package dev.gustavopere.rpgskilltree.runtime.compat.create;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.core.CreateAdvancementMasteryPolicy;
import dev.gustavopere.rpgskilltree.runtime.CreateMasteryMilestoneRuntime;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

final class CreateProgressionEventsJUnitTest {
    @Test
    void eligibleServerPlayerCreditsAuditedCreateAdvancement() {
        ServerPlayer player = mock(ServerPlayer.class);
        AdvancementEvent.AdvancementEarnEvent event = eventFor(player, "create", "mechanical_press");
        var milestone = CreateAdvancementMasteryPolicy.confirmed("create", "mechanical_press").orElseThrow();

        try (MockedStatic<CreateMasteryMilestoneRuntime> runtime = mockStatic(CreateMasteryMilestoneRuntime.class)) {
            CreateProgressionEvents.onAdvancementEarned(event);

            runtime.verify(() -> CreateMasteryMilestoneRuntime.awardIfNew(player, milestone), times(1));
        }
    }

    @Test
    void foreignAdvancementFailsClosedWithoutMutation() {
        ServerPlayer player = mock(ServerPlayer.class);
        AdvancementEvent.AdvancementEarnEvent event = eventFor(player, "minecraft", "story/mine_stone");
        var createMilestone = CreateAdvancementMasteryPolicy.confirmed("create", "mechanical_press").orElseThrow();

        try (MockedStatic<CreateMasteryMilestoneRuntime> runtime = mockStatic(CreateMasteryMilestoneRuntime.class)) {
            CreateProgressionEvents.onAdvancementEarned(event);

            runtime.verify(() -> CreateMasteryMilestoneRuntime.awardIfNew(player, createMilestone), never());
        }
    }

    @Test
    void creativeSpectatorAndFakePlayersAreIneligible() {
        ServerPlayer creative = mock(ServerPlayer.class);
        when(creative.isCreative()).thenReturn(true);

        ServerPlayer spectator = mock(ServerPlayer.class);
        when(spectator.isSpectator()).thenReturn(true);

        FakePlayer fakePlayer = mock(FakePlayer.class);

        try (MockedStatic<CreateMasteryMilestoneRuntime> runtime = mockStatic(CreateMasteryMilestoneRuntime.class)) {
            CreateProgressionEvents.onAdvancementEarned(eventFor(creative, "create", "mechanical_press"));
            CreateProgressionEvents.onAdvancementEarned(eventFor(spectator, "create", "mechanical_press"));
            CreateProgressionEvents.onAdvancementEarned(eventFor(fakePlayer, "create", "mechanical_press"));

            runtime.verifyNoInteractions();
        }
    }

    @Test
    void nonServerPlayerIsIgnoredBeforeAdvancementLookup() {
        Player clientSidePlayer = mock(Player.class);
        AdvancementEvent.AdvancementEarnEvent event = mock(AdvancementEvent.AdvancementEarnEvent.class);
        when(event.getEntity()).thenReturn(clientSidePlayer);

        try (MockedStatic<CreateMasteryMilestoneRuntime> runtime = mockStatic(CreateMasteryMilestoneRuntime.class)) {
            CreateProgressionEvents.onAdvancementEarned(event);

            runtime.verifyNoInteractions();
        }
    }

    private static AdvancementEvent.AdvancementEarnEvent eventFor(Player player, String namespace, String path) {
        AdvancementEvent.AdvancementEarnEvent event = mock(AdvancementEvent.AdvancementEarnEvent.class);
        AdvancementHolder advancement = mock(AdvancementHolder.class);
        when(event.getEntity()).thenReturn(player);
        when(event.getAdvancement()).thenReturn(advancement);
        when(advancement.id()).thenReturn(ResourceLocation.fromNamespaceAndPath(namespace, path));
        return event;
    }
}
