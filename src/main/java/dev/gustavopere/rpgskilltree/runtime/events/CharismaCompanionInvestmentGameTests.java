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
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Real NeoForge incoming-damage event and real vanilla owner/pet fixtures. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class CharismaCompanionInvestmentGameTests {
    private CharismaCompanionInvestmentGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void tamedVanillaWolfDamageIsBoundedAndExactlyOnce(GameTestHelper helper) {
        ServerPlayer owner = player(helper, "charisma-wolf-owner");
        Wolf wolf = new Wolf(EntityType.WOLF, helper.getLevel());
        wolf.tame(owner);
        Zombie hostile = new Zombie(EntityType.ZOMBIE, helper.getLevel());

        // Rank 0 and pre-milestone must not affect the vanilla damage event.
        for (long rank : new long[] {0L, 7L, 8L, 300L, Long.MAX_VALUE}) {
            setRank(owner, rank);
            LivingIncomingDamageEvent event = incoming(hostile, wolf, 4.0F);
            InvestedCharismaCompanionDamageEvents.applyForOwner(event, owner);
            double bonus = AttributeInvestmentBonuses.value(
                AttributeInvestmentBonuses.Benefit.CHARISMA_COMPANION_DAMAGE, rank);
            check(helper, 4.0D * (1.0D + bonus), event.getAmount(), "rank " + rank);
            InvestedCharismaCompanionDamageEvents.applyForOwner(event, owner);
            check(helper, 4.0D * (1.0D + bonus), event.getAmount(), "same event twice " + rank);
        }

        setRank(owner, 300L);
        LivingIncomingDamageEvent independentHit = incoming(hostile, wolf, 2.0F);
        InvestedCharismaCompanionDamageEvents.applyForOwner(independentHit, owner);
        check(helper, 2.0D * (1.0D + AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_COMPANION_DAMAGE, 300L)),
            independentHit.getAmount(), "second distinct damage event");

        LivingIncomingDamageEvent cancelled = incoming(hostile, wolf, 4.0F);
        cancelled.setCanceled(true);
        InvestedCharismaCompanionDamageEvents.applyForOwner(cancelled, owner);
        check(helper, 4.0D, cancelled.getAmount(), "cancelled hit");

        LivingIncomingDamageEvent zero = incoming(hostile, wolf, 0.0F);
        InvestedCharismaCompanionDamageEvents.applyForOwner(zero, owner);
        check(helper, 0.0D, zero.getAmount(), "zero damage");

        LivingIncomingDamageEvent unregisteredOwner = incoming(hostile, wolf, 4.0F);
        InvestedCharismaCompanionDamageEvents.onIncomingDamage(unregisteredOwner);
        check(helper, 4.0D, unregisteredOwner.getAmount(),
            "detached owner must not be treated as online");

        Wolf wild = new Wolf(EntityType.WOLF, helper.getLevel());
        LivingIncomingDamageEvent wildEvent = incoming(hostile, wild, 4.0F);
        InvestedCharismaCompanionDamageEvents.applyForOwner(wildEvent, owner);
        check(helper, 4.0D, wildEvent.getAmount(), "wild wolf");

        ServerPlayer other = player(helper, "charisma-wolf-other");
        setRank(other, 300L);
        LivingIncomingDamageEvent wrongOwner = incoming(hostile, wolf, 4.0F);
        InvestedCharismaCompanionDamageEvents.applyForOwner(wrongOwner, other);
        check(helper, 4.0D, wrongOwner.getAmount(), "wrong owner");

        LivingIncomingDamageEvent wrongSource = new LivingIncomingDamageEvent(hostile,
            new DamageContainer(hostile.damageSources().generic(), 4.0F));
        InvestedCharismaCompanionDamageEvents.applyForOwner(wrongSource, owner);
        check(helper, 4.0D, wrongSource.getAmount(), "non-wolf damage source");

        // Friendlies and PvP cannot receive companion bonus.
        LivingIncomingDamageEvent pvp = new LivingIncomingDamageEvent(other,
            new DamageContainer(other.damageSources().mobAttack(wolf), 4.0F));
        InvestedCharismaCompanionDamageEvents.applyForOwner(pvp, owner);
        check(helper, 4.0D, pvp.getAmount(), "player target");

        setRank(owner, 0L);
        LivingIncomingDamageEvent afterRespec = incoming(hostile, wolf, 4.0F);
        InvestedCharismaCompanionDamageEvents.applyForOwner(afterRespec, owner);
        check(helper, 4.0D, afterRespec.getAmount(), "respec removes bonus");
        helper.succeed();
    }

    private static LivingIncomingDamageEvent incoming(Zombie victim, Wolf attacker, float amount) {
        return new LivingIncomingDamageEvent(victim,
            new DamageContainer(victim.damageSources().mobAttack(attacker), amount));
    }

    private static ServerPlayer player(GameTestHelper helper, String name) {
        return new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), name), ClientInformation.createDefault());
    }

    private static void setRank(ServerPlayer player, long rank) {
        var ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(AttributeId.CHARISMA, rank));
        var state = new CoreProgressionState(CharacterProgressionState.empty(),
            CorePointLedger.empty(), ranks, 1L, "0".repeat(64), 0, 0L);
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(state)));
    }

    private static void check(GameTestHelper helper, double expected, double actual, String label) {
        helper.assertTrue(Math.abs(expected - actual) < 1.0e-5D,
            label + ": expected=" + expected + ", actual=" + actual);
    }
}
