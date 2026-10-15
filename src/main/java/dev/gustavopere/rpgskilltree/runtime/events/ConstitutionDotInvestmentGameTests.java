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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Real NeoForge damage events, not a replacement damage/tick simulation. */
// Sonar S2187 expects JUnit; these methods execute as NeoForge @GameTest.
@SuppressWarnings("java:S2187")
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class ConstitutionDotInvestmentGameTests {
    private ConstitutionDotInvestmentGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void constitutionMitigatesOnlyWhitelistedVanillaPeriodicDamage(GameTestHelper helper) {
        ServerPlayer player = player(helper, "constitution-dot");
        for (long rank : new long[] {0L, 11L, 12L, 30L, 300L, Long.MAX_VALUE}) {
            setRank(player, rank);
            double reduction = AttributeInvestmentBonuses.value(
                AttributeInvestmentBonuses.Benefit.CONSTITUTION_DAMAGE_OVER_TIME, rank);
            for (DamageSource source : new DamageSource[] {
                player.damageSources().onFire(),
                player.damageSources().wither()
            }) {
                LivingIncomingDamageEvent damage = incoming(player, source, 5.0F);
                InvestedConstitutionDotEvents.onIncomingDamage(damage);
                close(helper, 5.0D * (1.0D - reduction), damage.getAmount(),
                    "vanilla periodic damage at rank " + rank);
                InvestedConstitutionDotEvents.onIncomingDamage(damage);
                close(helper, 5.0D * (1.0D - reduction), damage.getAmount(),
                    "same damage event must never compound");
            }
        }

        setRank(player, 300L);
        for (DamageSource notDot : new DamageSource[] {
            player.damageSources().inFire(),
            player.damageSources().lava(),
            player.damageSources().magic(),
            player.damageSources().fall(),
            player.damageSources().drown()
        }) {
            LivingIncomingDamageEvent untouched = incoming(player, notDot, 5.0F);
            InvestedConstitutionDotEvents.onIncomingDamage(untouched);
            close(helper, 5.0D, untouched.getAmount(),
                "environmental and generic magic damage must not be reduced");
        }

        LivingIncomingDamageEvent canceled = incoming(player, player.damageSources().onFire(), 5.0F);
        canceled.setCanceled(true);
        InvestedConstitutionDotEvents.onIncomingDamage(canceled);
        close(helper, 5.0D, canceled.getAmount(), "canceled damage");

        for (float invalid : new float[] {0.0F, -3.0F, Float.NaN, Float.POSITIVE_INFINITY}) {
            LivingIncomingDamageEvent event = incoming(player, player.damageSources().wither(), invalid);
            InvestedConstitutionDotEvents.onIncomingDamage(event);
            if (Float.isNaN(invalid)) {
                helper.assertTrue(Float.isNaN(event.getAmount()), "NaN must stay unchanged");
            } else {
                helper.assertTrue(event.getAmount() == invalid,
                    "nonpositive or nonfinite damage must remain unchanged");
            }
        }

        Zombie zombie = new Zombie(EntityType.ZOMBIE, helper.getLevel());
        LivingIncomingDamageEvent nonPlayer = new LivingIncomingDamageEvent(
            zombie, new DamageContainer(zombie.damageSources().onFire(), 5.0F));
        InvestedConstitutionDotEvents.onIncomingDamage(nonPlayer);
        close(helper, 5.0D, nonPlayer.getAmount(), "no creature-wide Constitution mitigation");

        setRank(player, 0L);
        LivingIncomingDamageEvent afterRespec = incoming(player, player.damageSources().wither(), 5.0F);
        InvestedConstitutionDotEvents.onIncomingDamage(afterRespec);
        close(helper, 5.0D, afterRespec.getAmount(), "respec must restore vanilla damage");

        ServerPlayer noCore = player(helper, "constitution-dot-no-core");
        LivingIncomingDamageEvent emptyCore = incoming(noCore, noCore.damageSources().onFire(), 5.0F);
        InvestedConstitutionDotEvents.onIncomingDamage(emptyCore);
        close(helper, 5.0D, emptyCore.getAmount(), "no invested Core rank fails closed");
        helper.succeed();
    }

    private static LivingIncomingDamageEvent incoming(ServerPlayer player, DamageSource source, float amount) {
        return new LivingIncomingDamageEvent(player, new DamageContainer(source, amount));
    }

    private static ServerPlayer player(GameTestHelper helper, String name) {
        return new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), name), ClientInformation.createDefault());
    }

    private static void setRank(ServerPlayer player, long rank) {
        AttributeRanks ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(AttributeId.CONSTITUTION, rank));
        CoreProgressionState state = new CoreProgressionState(CharacterProgressionState.empty(),
            CorePointLedger.empty(), ranks, 1L, "0".repeat(64), 0, 0L);
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(state)));
    }

    private static void close(GameTestHelper helper, double expected, double actual, String label) {
        helper.assertTrue(Math.abs(expected - actual) < 1.0e-5D,
            label + " expected=" + expected + " actual=" + actual);
    }
}
