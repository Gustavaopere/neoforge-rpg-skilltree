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
            Set.of("ars:projectile"),
            25
        );

        ArsMasteryCausalAward causalAward = ArsMasteryCausalAward.arm(action);

        assertSame(action, causalAward.claimResolved());
        assertNull(causalAward.claimResolved());
    }

    @Test
    void adapterAwardsOnlyAfterArsReportsSpellResolution() throws IOException {
        String source = Files.readString(Path.of(
            System.getProperty("user.dir"),
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/ArsNouveauProgressionEvents.java"
        ));

        int castHandler = source.indexOf("public static void onSpellCast(SpellCastEvent event)");
        int resolveHandler = source.indexOf("public static void onSpellResolved(SpellResolveEvent.Post event)");

        assertTrue(castHandler >= 0, "SpellCastEvent must remain the access/arming boundary");
        assertTrue(resolveHandler > castHandler, "Mastery must wait for SpellResolveEvent.Post");

        String castSection = source.substring(castHandler, resolveHandler);
        assertTrue(castSection.contains("getOrCreateAttachment"), "SpellCastEvent must arm the causal award on Ars SpellContext");
        assertFalse(castSection.contains("PlayerProgressionRuntime.awardMastery"), "SpellCastEvent fires before Ars knows cast success");

        int nextHandler = source.indexOf("@SubscribeEvent", resolveHandler + 1);
        String resolveSection = nextHandler < 0
            ? source.substring(resolveHandler)
            : source.substring(resolveHandler, nextHandler);
        assertTrue(resolveSection.contains("claimResolved"), "resolution must claim the one-shot award");
        assertTrue(resolveSection.contains("PlayerProgressionRuntime.awardMastery"), "only resolved spells may award Mastery");
    }
}
