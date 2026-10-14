package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.CharacterProgressionState;
import dev.gustavopere.rpgskilltree.core.CorePointLedger;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsInvestmentAttributePolicy;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsInvestmentCooldownEvents;
import io.redspace.ironsspellbooks.api.events.SpellCooldownAddedEvent;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import java.util.Map;
import java.util.UUID;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/**
 * Exact-provider GameTest. Uses the real native Pre event and ServerPlayer's
 * canonical Core attachment; never allocates a second cooldown store.
 */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class IronsCooldownInvestmentGameTests {
    private IronsCooldownInvestmentGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void determinationAffectsOnlyLegitimateIronCastsAndPreservesOneTick(GameTestHelper helper) {
        if (!IronsInvestmentAttributePolicy.operational(
            OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS),
            OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS))) {
            helper.succeed(); // Optional provider absent: the event class is never touched.
            return;
        }

        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "iron-cooldown"), ClientInformation.createDefault());
        // Provider-present GameTests do not load the Core rules datapack. Build a
        // structurally valid Core attachment directly rather than changing global
        // rules or bypassing the production read-only attachment boundary.
        var invested = new CoreProgressionState(
            CharacterProgressionState.empty(),
            CorePointLedger.empty(),
            AttributeRanks.of(Map.of(AttributeId.DETERMINATION, 10L)),
            1L,
            "0".repeat(64),
            0,
            0L
        );
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(invested)));

        var book = new SpellCooldownAddedEvent.Pre(1000, null, player, CastSource.SPELLBOOK);
        IronsInvestmentCooldownEvents.onCooldownPre(book);
        helper.assertTrue(book.getEffectiveCooldown() == 998, "spellbook 1000 ticks -> 998");
        var sword = new SpellCooldownAddedEvent.Pre(1000, null, player, CastSource.SWORD);
        IronsInvestmentCooldownEvents.onCooldownPre(sword);
        helper.assertTrue(sword.getEffectiveCooldown() == 998, "sword cooldown adjustment");
        var scroll = new SpellCooldownAddedEvent.Pre(1000, null, player, CastSource.SCROLL);
        IronsInvestmentCooldownEvents.onCooldownPre(scroll);
        helper.assertTrue(scroll.getEffectiveCooldown() == 1000, "scroll must remain unchanged");
        var command = new SpellCooldownAddedEvent.Pre(1000, null, player, CastSource.COMMAND);
        IronsInvestmentCooldownEvents.onCooldownPre(command);
        helper.assertTrue(command.getEffectiveCooldown() == 1000, "command cast must remain unchanged");
        var shortest = new SpellCooldownAddedEvent.Pre(1, null, player, CastSource.SPELLBOOK);
        IronsInvestmentCooldownEvents.onCooldownPre(shortest);
        helper.assertTrue(shortest.getEffectiveCooldown() == 1, "positive cooldown must never become zero");

        helper.succeed();
    }
}
