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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Event-level coverage of the native Constitution incoming-heal hook. */
// Sonar S2187 expects JUnit; these methods execute as NeoForge @GameTest.
@SuppressWarnings("java:S2187")
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class ConstitutionHealingInvestmentGameTests {
    private ConstitutionHealingInvestmentGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void constitutionAdjustsOnlyUncancelledFinitePlayerHeals(GameTestHelper helper) {
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "constitution-heal"), ClientInformation.createDefault());
        for (long rank : new long[] {0L, 7L, 8L, 30L, 300L, Long.MAX_VALUE}) {
            setRank(player, rank);
            LivingHealEvent event = new LivingHealEvent(player, 4.0F);
            InvestedConstitutionHealingEvents.onHeal(event);
            double contribution = AttributeInvestmentBonuses.value(
                AttributeInvestmentBonuses.Benefit.CONSTITUTION_HEALING_RECEIVED, rank);
            close(helper, 4.0D * (1.0D + contribution), event.getAmount(),
                "native incoming heal rank " + rank);
        }

        setRank(player, 300L);
        LivingHealEvent canceled = new LivingHealEvent(player, 4.0F);
        canceled.setCanceled(true);
        InvestedConstitutionHealingEvents.onHeal(canceled);
        close(helper, 4.0D, canceled.getAmount(), "cancelled healing event");

        for (float invalid : new float[] {0.0F, -1.0F, Float.NaN, Float.POSITIVE_INFINITY}) {
            LivingHealEvent event = new LivingHealEvent(player, invalid);
            InvestedConstitutionHealingEvents.onHeal(event);
            if (Float.isNaN(invalid)) {
                helper.assertTrue(Float.isNaN(event.getAmount()), "NaN must remain rejected");
            } else {
                helper.assertTrue(event.getAmount() == invalid, "nonpositive or nonfinite heal rejected");
            }
        }

        Zombie other = new Zombie(EntityType.ZOMBIE, helper.getLevel());
        LivingHealEvent nonPlayer = new LivingHealEvent(other, 4.0F);
        InvestedConstitutionHealingEvents.onHeal(nonPlayer);
        close(helper, 4.0D, nonPlayer.getAmount(), "not a ServerPlayer");

        setRank(player, 0L);
        LivingHealEvent afterRefund = new LivingHealEvent(player, 4.0F);
        InvestedConstitutionHealingEvents.onHeal(afterRefund);
        close(helper, 4.0D, afterRefund.getAmount(), "rank refund removes bonus");
        helper.succeed();
    }

    private static void setRank(ServerPlayer player, long rank) {
        AttributeRanks ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(AttributeId.CONSTITUTION, rank));
        var state = new CoreProgressionState(CharacterProgressionState.empty(),
            CorePointLedger.empty(), ranks, 1L, "0".repeat(64), 0, 0L);
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(state)));
    }

    private static void close(GameTestHelper helper, double expected, double actual, String name) {
        helper.assertTrue(Math.abs(expected - actual) < 1e-5,
            name + " expected " + expected + " actual " + actual);
    }
}
