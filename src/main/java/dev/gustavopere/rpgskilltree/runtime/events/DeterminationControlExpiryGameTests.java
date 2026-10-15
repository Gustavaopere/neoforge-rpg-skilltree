package dev.gustavopere.rpgskilltree.runtime.events;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CharacterProgressionState;
import dev.gustavopere.rpgskilltree.core.CorePointLedger;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import java.util.Map;
import java.util.UUID;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

// Sonar S2187 assumes JUnit; the runtime runs these methods as NeoForge GameTests.
@SuppressWarnings("java:S2187")
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class DeterminationControlExpiryGameTests {
    private DeterminationControlExpiryGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void shulkerLevitationEndsAtInvestedCutoffWithoutMutatingDuration(GameTestHelper helper) {
        ServerPlayer player = player(helper, "determination-cutoff");
        setRank(player, 8L);
        Shulker shulker = new Shulker(EntityType.SHULKER, helper.getLevel());
        MobEffectInstance effect = new MobEffectInstance(MobEffects.LEVITATION, 200);
        helper.assertTrue(player.addEffect(effect, shulker), "fresh vanilla levitation must install");
        // Manual invocation here is deliberate: verifies the event handler without
        // relying on player networking/ticking for a detached test fixture.
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(player, null, effect, shulker));
        helper.assertTrue(effect.getDuration() == 200, "incoming instance must remain unmodified");
        int adjusted = (int) Math.floor(200D * (1D - AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.DETERMINATION_CONTROL_DURATION, 8L)));
        advance(helper, player, effect, adjusted - 1);
        InvestedDeterminationControlEvents.onPlayerTick(new PlayerTickEvent.Post(player));
        helper.assertTrue(player.getEffect(MobEffects.LEVITATION) == effect,
            "levitation must remain through tick before invested cutoff");
        advance(helper, player, effect, 1);
        InvestedDeterminationControlEvents.onPlayerTick(new PlayerTickEvent.Post(player));
        helper.assertTrue(player.getEffect(MobEffects.LEVITATION) == null,
            "levitation must expire at invested cutoff, not at vanilla duration");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void sourcesRespecAndRefreshFailClosed(GameTestHelper helper) {
        Shulker shulker = new Shulker(EntityType.SHULKER, helper.getLevel());
        Zombie zombie = new Zombie(EntityType.ZOMBIE, helper.getLevel());
        ServerPlayer nonShulker = player(helper, "determination-foreign");
        setRank(nonShulker, 300L);
        MobEffectInstance fromZombie = new MobEffectInstance(MobEffects.LEVITATION, 40);
        nonShulker.addEffect(fromZombie, zombie);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(nonShulker, null, fromZombie, zombie));
        advance(helper, nonShulker, fromZombie, 39);
        InvestedDeterminationControlEvents.onPlayerTick(new PlayerTickEvent.Post(nonShulker));
        helper.assertTrue(nonShulker.getEffect(MobEffects.LEVITATION) == fromZombie,
            "other mobs must keep full vanilla duration");

        ServerPlayer refunded = player(helper, "determination-respec");
        setRank(refunded, 300L);
        MobEffectInstance initial = new MobEffectInstance(MobEffects.LEVITATION, 40);
        refunded.addEffect(initial, shulker);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(refunded, null, initial, shulker));
        setRank(refunded, 0L);
        advance(helper, refunded, initial, 39);
        InvestedDeterminationControlEvents.onPlayerTick(new PlayerTickEvent.Post(refunded));
        helper.assertTrue(refunded.getEffect(MobEffects.LEVITATION) == initial,
            "respec must not terminate an already running effect prematurely");

        ServerPlayer refreshed = player(helper, "determination-refresh");
        setRank(refreshed, 300L);
        MobEffectInstance old = new MobEffectInstance(MobEffects.LEVITATION, 40);
        refreshed.addEffect(old, shulker);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(refreshed, null, old, shulker));
        MobEffectInstance renewed = new MobEffectInstance(MobEffects.LEVITATION, 80);
        refreshed.addEffect(renewed, zombie);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(refreshed, old, renewed, zombie));
        helper.assertTrue(refreshed.getEffect(MobEffects.LEVITATION) == old,
            "vanilla must merge into existing effect object");
        advance(helper, refreshed, old, 79);
        InvestedDeterminationControlEvents.onPlayerTick(new PlayerTickEvent.Post(refreshed));
        helper.assertTrue(refreshed.getEffect(MobEffects.LEVITATION) == old,
            "refresh must invalidate original early-expiry receipt");
        helper.succeed();
    }

    private static void advance(GameTestHelper helper, ServerPlayer player,
                                MobEffectInstance effect, int ticks) {
        for (int i = 0; i < ticks; i++) {
            helper.assertTrue(effect.tick(player, () -> {}),
                "native MobEffectInstance.tick must remain active in fixture");
        }
    }

    private static ServerPlayer player(GameTestHelper helper, String name) {
        return new DetachedEffectsPlayer(helper, name);
    }

    /**
     * A GameTest-created ServerPlayer has no network connection. Vanilla
     * ServerPlayer effect lifecycle normally sends packets through that
     * connection; override just those transport callbacks, keeping the real
     * LivingEntity addEffect/update/remove storage and tick behavior intact.
     */
    private static final class DetachedEffectsPlayer extends ServerPlayer {
        DetachedEffectsPlayer(GameTestHelper helper, String name) {
            super(helper.getLevel().getServer(), helper.getLevel(),
                new GameProfile(UUID.randomUUID(), name), ClientInformation.createDefault());
        }

        @Override
        protected void onEffectAdded(MobEffectInstance effect, Entity source) {}

        @Override
        protected void onEffectUpdated(MobEffectInstance effect, boolean forced, Entity source) {}

        @Override
        protected void onEffectRemoved(MobEffectInstance effect) {}
    }

    private static void setRank(ServerPlayer player, long rank) {
        AttributeRanks ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(AttributeId.DETERMINATION, rank));
        CoreProgressionState state = new CoreProgressionState(CharacterProgressionState.empty(),
            CorePointLedger.empty(), ranks, 1L, "0".repeat(64), 0, 0L);
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(state)));
    }
}
