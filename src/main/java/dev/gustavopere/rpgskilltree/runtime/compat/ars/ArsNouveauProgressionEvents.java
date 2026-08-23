package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.ArsCompositionClassifier;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.MagicAccessRuntime;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;

/** Optional Ars Nouveau adapter. Loaded only when Ars Nouveau is present. */
public final class ArsNouveauProgressionEvents {
    private ArsNouveauProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onSpellPreCast(SpellCastEvent event) {
        if (event.isCanceled()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer) return;
        if (MagicAccessRuntime.requireArcaneAccess(player)) return;
        event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpellCast(SpellCastEvent event) {
        if (event.isCanceled()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer) return;
        if (event.spell == null || event.spell.isEmpty()) return;

        List<String> glyphIds = event.spell.serializeRecipe().stream().map(ResourceLocation::toString).toList();
        if (glyphIds.isEmpty()) return;

        Set<String> tags = ArsCompositionClassifier.classify(glyphIds);
        String spellSignature = String.join(">", glyphIds);
        SpellAction action = new SpellAction(
            new ActionOrigin("ars:spellcast", 0),
            "ars",
            spellSignature,
            "composition",
            tags,
            Math.max(0, event.spell.getCost())
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forArs(action));
    }
}
