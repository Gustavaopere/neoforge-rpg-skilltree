package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import java.util.Locale;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

/** Optional Iron's Spells 'n Spellbooks adapter. Loaded only when Iron's is present. */
public final class IronsSpellbookProgressionEvents {
    private IronsSpellbookProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpellCast(SpellOnCastEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!countsForMastery(event.getCastSource())) return;

        ResourceLocation schoolId = event.getSchoolType().getId();
        String discipline = schoolId.getNamespace().equals("irons_spellbooks")
            ? schoolId.getPath()
            : schoolId.getNamespace() + "/" + schoolId.getPath();

        SpellAction action = new SpellAction(
            new ActionOrigin("irons:" + event.getCastSource().name().toLowerCase(Locale.ROOT), 0),
            "irons",
            event.getSpellId(),
            discipline,
            Set.of(),
            Math.max(0, event.getManaCost())
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forIron(action));
    }

    static boolean countsForMastery(CastSource source) {
        return source == CastSource.SPELLBOOK || source == CastSource.SCROLL;
    }
}
