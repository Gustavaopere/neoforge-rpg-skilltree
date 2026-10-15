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
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/**
 * Tests the exact NeoForge Added event boundary; these tests do not assert that
 * every vanilla or modded effect call uses Added (forceAddEffect is excluded).
 */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class DeterminationControlInvestmentGameTests {
    private DeterminationControlInvestmentGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void firstShulkerLevitationIsShortenedOnceAndOnlyOnce(GameTestHelper helper) {
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "determination-control"), ClientInformation.createDefault());
        Shulker shulker = new Shulker(EntityType.SHULKER, helper.getLevel());

        for (long rank : new long[] {0L, 7L, 8L, 30L, 300L, Long.MAX_VALUE}) {
            setRank(player, rank);
            MobEffectInstance fresh = new MobEffectInstance(MobEffects.LEVITATION, 200, 1);
            MobEffectEvent.Added event = new MobEffectEvent.Added(player, null, fresh, shulker);
            InvestedDeterminationControlEvents.onAdded(event);
            double reduction = AttributeInvestmentBonuses.value(
                AttributeInvestmentBonuses.Benefit.DETERMINATION_CONTROL_DURATION, rank);
            int expected = rank < 8L ? 200 : Math.max(1, (int) Math.floor(200.0D * (1.0D - reduction)));
            check(helper, expected, fresh.getDuration(), "rank " + rank);
            check(helper, 1, fresh.getAmplifier(), "amplifier must not change");
            InvestedDeterminationControlEvents.onAdded(event);
            check(helper, expected, fresh.getDuration(), "repeated event must not compound");
        }

        setRank(player, 300L);
        MobEffectInstance previous = new MobEffectInstance(MobEffects.LEVITATION, 30, 1);
        MobEffectInstance renewal = new MobEffectInstance(MobEffects.LEVITATION, 200, 0);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(player, previous, renewal, shulker));
        check(helper, 200, renewal.getDuration(), "already active effect must not be altered");
        check(helper, 30, previous.getDuration(), "existing effect must remain intact");

        // Actual projectile ownership is the normal hostile source: a Shulker
        // bullet is eligible only when it demonstrably belongs to a Shulker.
        ShulkerBullet hostileBullet = new ShulkerBullet(
            helper.getLevel(), shulker, player, Direction.Axis.Y);
        MobEffectInstance bulletLevitation = new MobEffectInstance(MobEffects.LEVITATION, 200);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(player, null, bulletLevitation, hostileBullet));
        double bulletReduction = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.DETERMINATION_CONTROL_DURATION, 300L);
        check(helper, Math.max(1, (int) Math.floor(200.0D * (1.0D - bulletReduction))),
            bulletLevitation.getDuration(), "verified Shulker bullet owner");

        ShulkerBullet orphanBullet = new ShulkerBullet(EntityType.SHULKER_BULLET, helper.getLevel());
        MobEffectInstance orphanLevitation = new MobEffectInstance(MobEffects.LEVITATION, 200);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(player, null, orphanLevitation, orphanBullet));
        check(helper, 200, orphanLevitation.getDuration(),
            "ownerless Shulker bullet must fail closed");

        Zombie zombie = new Zombie(EntityType.ZOMBIE, helper.getLevel());
        for (MobEffectEvent.Added ignored : new MobEffectEvent.Added[] {
            new MobEffectEvent.Added(player, null, new MobEffectInstance(MobEffects.LEVITATION, 200), null),
            new MobEffectEvent.Added(player, null, new MobEffectInstance(MobEffects.LEVITATION, 200), zombie),
            new MobEffectEvent.Added(player, null, new MobEffectInstance(MobEffects.DARKNESS, 200), shulker),
            new MobEffectEvent.Added(player, null, new MobEffectInstance(MobEffects.REGENERATION, 200), shulker),
            new MobEffectEvent.Added(player, null, new MobEffectInstance(MobEffects.LEVITATION, -1), shulker),
            new MobEffectEvent.Added(player, null, new MobEffectInstance(MobEffects.LEVITATION, 1), shulker)
        }) {
            int before = ignored.getEffectInstance().getDuration();
            InvestedDeterminationControlEvents.onAdded(ignored);
            check(helper, before, ignored.getEffectInstance().getDuration(), "non-allowlisted effect/source");
        }

        setRank(player, 0L);
        MobEffectInstance refunded = new MobEffectInstance(MobEffects.LEVITATION, 200);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(player, null, refunded, shulker));
        check(helper, 200, refunded.getDuration(), "rank respec must restore full duration");

        ServerPlayer noCore = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "determination-absent"), ClientInformation.createDefault());
        MobEffectInstance absent = new MobEffectInstance(MobEffects.LEVITATION, 200);
        InvestedDeterminationControlEvents.onAdded(
            new MobEffectEvent.Added(noCore, null, absent, shulker));
        check(helper, 200, absent.getDuration(), "no Core state must fail closed");
        helper.succeed();
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

    private static void check(GameTestHelper helper, int expected, int actual, String label) {
        helper.assertTrue(expected == actual,
            label + " expected=" + expected + " actual=" + actual);
    }
}
