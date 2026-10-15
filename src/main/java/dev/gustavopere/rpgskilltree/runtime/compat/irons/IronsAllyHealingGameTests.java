package dev.gustavopere.rpgskilltree.runtime.compat.irons;

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
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import io.redspace.ironsspellbooks.api.events.SpellHealEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import java.util.Map;
import java.util.UUID;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

// Sonar S2187 expects JUnit; these methods execute as NeoForge @GameTest.
@SuppressWarnings("java:S2187")
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class IronsAllyHealingGameTests {
    private IronsAllyHealingGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void charismaBoostsExactlyOneAttributedIronAllyHeal(GameTestHelper helper) {
        if (!IronsInvestmentAttributePolicy.operational(
            OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS),
            OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS))) {
            helper.succeed();
            return;
        }
        ServerPlayer caster = player(helper, "charisma-healer");
        ServerPlayer target = player(helper, "charisma-target");
        target.setHealth(8.0F);
        setRank(caster, 10L);

        SpellHealEvent spell = new SpellHealEvent(caster, target, 4.0F, SchoolRegistry.HOLY.get());
        IronsAllyHealingEvents.onSpellHeal(spell);
        LivingHealEvent heal = new LivingHealEvent(target, 4.0F);
        IronsAllyHealingEvents.onLivingHeal(heal);

        double multiplier = 1.0D + AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_ALLY_HEALING, 10L);
        close(helper, 4.0D * multiplier, heal.getAmount(), "attributed first ally heal");

        IronsAllyHealingEvents.onLivingHeal(heal);
        close(helper, 4.0D * multiplier, heal.getAmount(), "same event must not compound");

        LivingHealEvent unrelated = new LivingHealEvent(target, 4.0F);
        IronsAllyHealingEvents.onLivingHeal(unrelated);
        close(helper, 4.0D, unrelated.getAmount(), "no leftover heal token");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void charismaNeverCreditsSelfHealOrMismatchedAmount(GameTestHelper helper) {
        if (!IronsInvestmentAttributePolicy.operational(
            OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS),
            OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS))) {
            helper.succeed();
            return;
        }
        ServerPlayer caster = player(helper, "charisma-source");
        ServerPlayer target = player(helper, "charisma-recipient");
        caster.setHealth(7.0F);
        target.setHealth(7.0F);
        setRank(caster, 300L);

        IronsAllyHealingEvents.onSpellHeal(
            new SpellHealEvent(caster, caster, 4.0F, SchoolRegistry.HOLY.get()));
        LivingHealEvent own = new LivingHealEvent(caster, 4.0F);
        IronsAllyHealingEvents.onLivingHeal(own);
        close(helper, 4.0D, own.getAmount(), "self healing stays excluded");

        IronsAllyHealingEvents.onSpellHeal(
            new SpellHealEvent(caster, target, 4.0F, SchoolRegistry.HOLY.get()));
        LivingHealEvent mismatch = new LivingHealEvent(target, 3.0F);
        IronsAllyHealingEvents.onLivingHeal(mismatch);
        close(helper, 3.0D, mismatch.getAmount(), "mismatched heal must fail closed");

        LivingHealEvent afterMismatch = new LivingHealEvent(target, 4.0F);
        IronsAllyHealingEvents.onLivingHeal(afterMismatch);
        close(helper, 4.0D, afterMismatch.getAmount(), "mismatch must consume token");

        target.setHealth(target.getMaxHealth());
        IronsAllyHealingEvents.onSpellHeal(
            new SpellHealEvent(caster, target, 4.0F, SchoolRegistry.HOLY.get()));
        LivingHealEvent full = new LivingHealEvent(target, 4.0F);
        IronsAllyHealingEvents.onLivingHeal(full);
        close(helper, 4.0D, full.getAmount(), "no bonus on overheal");

        target.setHealth(7.0F);
        setRank(caster, 0L);
        IronsAllyHealingEvents.onSpellHeal(
            new SpellHealEvent(caster, target, 4.0F, SchoolRegistry.HOLY.get()));
        LivingHealEvent afterRespec = new LivingHealEvent(target, 4.0F);
        IronsAllyHealingEvents.onLivingHeal(afterRespec);
        close(helper, 4.0D, afterRespec.getAmount(), "respec removes all bonus");
        helper.succeed();
    }

    private static ServerPlayer player(GameTestHelper helper, String name) {
        return new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), name), ClientInformation.createDefault());
    }

    private static void setRank(ServerPlayer player, long rank) {
        AttributeRanks ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(AttributeId.CHARISMA, rank));
        CoreProgressionState state = new CoreProgressionState(CharacterProgressionState.empty(),
            CorePointLedger.empty(), ranks, 1L, "0".repeat(64), 0, 0L);
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(state)));
    }

    private static void close(GameTestHelper helper, double expected, double actual, String reason) {
        helper.assertTrue(Math.abs(expected - actual) < 1.0e-5D,
            reason + ": expected=" + expected + ", actual=" + actual);
    }
}
