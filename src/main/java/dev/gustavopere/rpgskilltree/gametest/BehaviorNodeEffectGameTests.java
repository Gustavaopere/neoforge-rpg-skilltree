package dev.gustavopere.rpgskilltree.gametest;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.NodeBehaviorEffect;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ResolvedNodeBehaviorEffect;
import dev.gustavopere.rpgskilltree.runtime.effects.BehaviorNodeEffectRuntime;
import dev.gustavopere.rpgskilltree.runtime.effects.NodeBehaviorHandler;
import dev.gustavopere.rpgskilltree.runtime.effects.NodeBehaviorHandlerRegistry;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BehaviorNodeEffectGameTests {
    private static final String NODE_ID = "rpgskilltree:gametest_behavior";
    private static final String EFFECT_ID = "rpgskilltree:gametest/behavior";
    private static final ResourceLocation HANDLER_ID = ResourceLocation.parse("rpgskilltree:gametest_behavior_handler");

    private BehaviorNodeEffectGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void behavioralEffectsReconcileExactlyOnceAcrossRankRemovalAndSessionReplacement(GameTestHelper helper) {
        var registry = new NodeBehaviorHandlerRegistry();
        var handler = new CountingHandler();
        registry.register(HANDLER_ID, handler);
        var runtime = new BehaviorNodeEffectRuntime(registry);
        var effect = new NodeBehaviorEffect(EFFECT_ID, NODE_ID, HANDLER_ID.toString());
        UUID playerId = UUID.randomUUID();
        ServerPlayer firstPlayer = player(helper, playerId, "behavior-effect-first");

        runtime.refresh(firstPlayer, state(1), List.of(effect));
        helper.assertTrue(handler.applies == 1 && handler.removes == 0 && handler.lastRank == 1,
            "first refresh must apply exactly once at rank 1");

        runtime.refresh(firstPlayer, state(1), List.of(effect));
        helper.assertTrue(handler.applies == 1 && handler.removes == 0,
            "identical refresh must not duplicate a behavioral effect");

        runtime.refresh(firstPlayer, state(2), List.of(effect));
        helper.assertTrue(handler.applies == 2 && handler.removes == 1 && handler.lastRank == 2,
            "rank change must remove old state before one new application");

        runtime.refresh(firstPlayer, ProgressionState.empty(), List.of(effect));
        helper.assertTrue(handler.applies == 2 && handler.removes == 2,
            "unlearning the node must remove the applied behavior exactly once");

        runtime.refresh(firstPlayer, state(1), List.of(effect));
        ServerPlayer replacement = player(helper, playerId, "behavior-effect-replacement");
        runtime.refresh(replacement, state(1), List.of(effect));
        helper.assertTrue(handler.applies == 4 && handler.removes == 2,
            "replacement ServerPlayer with same UUID must receive a fresh application");

        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void clearingOnePlayerForcesFreshApplicationWithoutTouchingOtherSessions(GameTestHelper helper) {
        var registry = new NodeBehaviorHandlerRegistry();
        var handler = new CountingHandler();
        registry.register(HANDLER_ID, handler);
        var runtime = new BehaviorNodeEffectRuntime(registry);
        var effect = new NodeBehaviorEffect(EFFECT_ID, NODE_ID, HANDLER_ID.toString());
        ServerPlayer first = player(helper, UUID.randomUUID(), "behavior-clear-first");
        ServerPlayer second = player(helper, UUID.randomUUID(), "behavior-clear-second");

        runtime.refresh(first, state(1), List.of(effect));
        runtime.refresh(second, state(1), List.of(effect));
        runtime.clearPlayer(first.getUUID());
        runtime.refresh(first, state(1), List.of(effect));
        runtime.refresh(second, state(1), List.of(effect));

        helper.assertTrue(handler.applies == 3 && handler.removes == 0,
            "clearPlayer must invalidate only the selected player's idempotency state");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void clearingAllPlayersForcesFreshApplicationForEverySession(GameTestHelper helper) {
        var registry = new NodeBehaviorHandlerRegistry();
        var handler = new CountingHandler();
        registry.register(HANDLER_ID, handler);
        var runtime = new BehaviorNodeEffectRuntime(registry);
        var effect = new NodeBehaviorEffect(EFFECT_ID, NODE_ID, HANDLER_ID.toString());
        ServerPlayer first = player(helper, UUID.randomUUID(), "behavior-clear-all-first");
        ServerPlayer second = player(helper, UUID.randomUUID(), "behavior-clear-all-second");

        runtime.refresh(first, state(1), List.of(effect));
        runtime.refresh(second, state(1), List.of(effect));
        runtime.clearAll();
        runtime.refresh(first, state(1), List.of(effect));
        runtime.refresh(second, state(1), List.of(effect));

        helper.assertTrue(handler.applies == 4 && handler.removes == 0,
            "clearAll must invalidate every behavioral-effect session cache");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void unavailableOptionalBehaviorHandlerIsFailSoft(GameTestHelper helper) {
        var registry = new NodeBehaviorHandlerRegistry();
        registry.register(HANDLER_ID, new NodeBehaviorHandler() {
            @Override
            public boolean available() {
                return false;
            }

            @Override
            public void apply(ServerPlayer player, ResolvedNodeBehaviorEffect effect) {
                throw new AssertionError("unavailable handler must not be applied");
            }

            @Override
            public void remove(ServerPlayer player, ResolvedNodeBehaviorEffect effect) {
                throw new AssertionError("unavailable handler must not be removed");
            }
        });
        var runtime = new BehaviorNodeEffectRuntime(registry);
        var effect = new NodeBehaviorEffect(EFFECT_ID, NODE_ID, HANDLER_ID.toString());

        runtime.refresh(player(helper, UUID.randomUUID(), "behavior-effect-optional"), state(1), List.of(effect));
        helper.succeed();
    }

    private static ProgressionState state(int rank) {
        return ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.of(Map.of(NODE_ID, rank)));
    }

    private static ServerPlayer player(GameTestHelper helper, UUID id, String name) {
        return new ServerPlayer(
            helper.getLevel().getServer(),
            helper.getLevel(),
            new GameProfile(id, name),
            ClientInformation.createDefault()
        );
    }

    private static final class CountingHandler implements NodeBehaviorHandler {
        private int applies;
        private int removes;
        private int lastRank;

        @Override
        public boolean available() {
            return true;
        }

        @Override
        public void apply(ServerPlayer player, ResolvedNodeBehaviorEffect effect) {
            applies++;
            lastRank = effect.rank();
        }

        @Override
        public void remove(ServerPlayer player, ResolvedNodeBehaviorEffect effect) {
            removes++;
        }
    }
}
