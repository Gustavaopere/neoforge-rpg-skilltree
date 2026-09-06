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
import org.junit.jupiter.api.Test;

final class ArsMasteryCausalityJUnitTest {
    @Test
    void resolvedCastCanAwardMasteryExactlyOnce() {
        SpellAction action = new SpellAction(
            new ActionOrigin("ars:spellcast", 0),
            "ars",
            "ars_nouveau:glyph_projectile>ars_nouveau:glyph_harm",
            "composition",
            Set.of("projectile"),
            25
        );

        ArsMasteryClaim claim = ArsMasteryClaim.arm(action);

        assertSame(action, claim.claimResolved());
        assertNull(claim.claimResolved());
    }

    @Test
    void adapterAwardsOnlyAfterArsReportsSpellResolution() throws IOException {
        String source = Files.readString(Path.of(
            System.getProperty("user.dir"),
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/ArsNouveauProgressionEvents.java"
        ));

        int castHandler = source.indexOf("public static void onSpellCast(SpellCastEvent event)");
        int resolveHandler = source.indexOf("public static void onSpellResolved(SpellResolveEvent.Post event)");
        int armHelper = source.indexOf("static void armCausalAward(SpellContext context, SpellAction action)");

        assertTrue(castHandler >= 0, "SpellCastEvent must remain the access/arming boundary");
        assertTrue(resolveHandler > castHandler, "Mastery must wait for SpellResolveEvent.Post");
        assertTrue(armHelper > resolveHandler, "causal attachment helper must remain explicit and auditable");

        String castSection = source.substring(castHandler, resolveHandler);
        assertTrue(castSection.contains("armCausalAward"), "SpellCastEvent must delegate causal arming to the Ars SpellContext helper");
        assertFalse(castSection.contains("PlayerProgressionRuntime.awardMastery"), "SpellCastEvent fires before Ars knows cast success");

        int nextHandler = source.indexOf("@SubscribeEvent", resolveHandler + 1);
        String resolveSection = nextHandler < 0
            ? source.substring(resolveHandler)
            : source.substring(resolveHandler, nextHandler);
        assertTrue(resolveSection.contains("claimResolved"), "resolution must claim the one-shot award");
        assertTrue(resolveSection.contains("PlayerProgressionRuntime.awardMastery"), "only resolved spells may award Mastery");

        int nextHelper = source.indexOf("static SpellAction claimResolved(SpellContext context)", armHelper + 1);
        String armSection = nextHelper < 0
            ? source.substring(armHelper)
            : source.substring(armHelper, nextHelper);
        assertTrue(armSection.contains("getOrCreateAttachment"), "causal helper must attach the one-shot award to Ars SpellContext");
    }
}
