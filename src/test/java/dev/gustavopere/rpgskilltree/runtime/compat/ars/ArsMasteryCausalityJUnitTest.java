package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ArsMasteryCausalityJUnitTest {
    @Test
    void resolvedCastCanAwardMasteryExactlyOnceForOriginalCaster() {
        SpellAction action = new SpellAction(
            new ActionOrigin("ars:spellcast", 0),
            "ars",
            "ars_nouveau:glyph_projectile>ars_nouveau:glyph_harm",
            "composition",
            Set.of("projectile"),
            25
        );
        UUID casterId = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();

        ArsMasteryClaim claim = ArsMasteryClaim.arm(casterId, action);

        assertNull(claim.claimResolved(otherId), "a different resolver must fail closed without consuming the claim");
        assertSame(action, claim.claimResolved(casterId), "the original caster must retain the live claim");
        assertNull(claim.claimResolved(casterId), "the original caster may claim the causal award only once");
    }

    @Test
    void adapterAwardsOnlyAfterArsReportsSpellResolution() throws IOException {
        String source = Files.readString(Path.of(
            System.getProperty("user.dir"),
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/ArsNouveauProgressionEvents.java"
        ));

        int castHandler = source.indexOf("public static void onSpellCast(SpellCastEvent event)");
        int resolveHandler = source.indexOf("public static void onSpellResolved(SpellResolveEvent.Post event)");
        int armHelper = source.indexOf("static void armCausalAward(SpellContext context, UUID casterId, SpellAction action)");

        assertTrue(castHandler >= 0, "SpellCastEvent must remain the access/arming boundary");
        assertTrue(resolveHandler > castHandler, "Mastery must wait for SpellResolveEvent.Post");
        assertTrue(armHelper > resolveHandler, "causal attachment helper must remain explicit and auditable");

        String castSection = source.substring(castHandler, resolveHandler);
        assertTrue(castSection.contains("armCausalAward"), "SpellCastEvent must delegate causal arming to the Ars SpellContext helper");
        assertTrue(castSection.contains("player.getUUID()"), "causal arming must bind the original server player identity");
        assertFalse(castSection.contains("PlayerProgressionRuntime.awardMastery"), "SpellCastEvent fires before Ars knows cast success");

        int nextHandler = source.indexOf("@SubscribeEvent", resolveHandler + 1);
        String resolveSection = nextHandler < 0
            ? source.substring(resolveHandler)
            : source.substring(resolveHandler, nextHandler);
        assertTrue(resolveSection.contains("claimResolved"), "resolution must claim the one-shot award");
        assertTrue(resolveSection.contains("player.getUUID()"), "resolution must verify the resolving server player identity before claiming");
        assertTrue(resolveSection.contains("PlayerProgressionRuntime.awardMastery"), "only resolved spells may award Mastery");

        int nextHelper = source.indexOf("static SpellAction claimResolved(SpellContext context, UUID resolverId)", armHelper + 1);
        String armSection = nextHelper < 0
            ? source.substring(armHelper)
            : source.substring(armHelper, nextHelper);
        assertTrue(armSection.contains("getOrCreateAttachment"), "causal helper must attach the one-shot award to Ars SpellContext");
    }
}
