package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.IronStudyPolicy;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.MagicAccessRuntime;
import io.redspace.ironsspellbooks.api.events.InscribeSpellEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import java.util.Locale;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;

/** Optional Iron's Spells 'n Spellbooks adapter. Loaded only when Iron's is present. */
public final class IronsSpellbookProgressionEvents {
    private static final String BLACK_ARCANA_PREFIX = "black_arcana:";

    private IronsSpellbookProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onSpellPreCast(SpellPreCastEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer) return;
        if (event.getCastSource() == CastSource.COMMAND) return;
        if (MagicAccessRuntime.requireArcaneAccess(player)) return;
        event.setCanceled(true);
    }

    /**
     * Makes permanent native Iron's spellbook inscription a learned-magic progression mechanic.
     * Externally-owned Black Arcana spells keep their own progression contract instead of being
     * evaluated as native Iron's content by school/level heuristics.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInscribeSpell(InscribeSpellEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer || player.isCreative()) return;
        if (!MagicAccessRuntime.requireArcaneAccess(player)) {
            event.setCanceled(true);
            return;
        }

        var spellData = event.getSpellData();
        if (externallyOwnsProgression(spellData.getSpell().getSpellId())) return;

        ResourceLocation schoolId = spellData.getSpell().getSchoolType().getId();
        String discipline = normalizeSchool(schoolId);
        var state = PlayerProgressionRuntime.get(player);
        var evaluation = IronStudyPolicy.evaluate(
            state.mastery(),
            "irons:" + discipline,
            spellData.getLevel(),
            state.classProgression().isUnlocked("mage")
        );
        if (evaluation.allowed()) return;

        event.setCanceled(true);
        var requirement = evaluation.requirement();
        if (!evaluation.mageSatisfied()) {
            player.displayClientMessage(Component.literal(
                "Inscrição bloqueada: magias de nível " + spellData.getLevel()
                    + "+ exigem a identidade Mago. Pratique Iron's Spells com scrolls e desenvolva o tronco Arcano."
            ), true);
            return;
        }

        player.displayClientMessage(Component.literal(
            "Inscrição bloqueada: domínio Iron's " + evaluation.currentCastingMastery() + "/"
                + requirement.castingMastery() + " e " + discipline + " "
                + evaluation.currentSchoolMastery() + "/" + requirement.schoolMastery()
                + ". Pratique a magia com scrolls antes de catalogá-la permanentemente."
        ), true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpellCast(SpellOnCastEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer) return;
        if (!countsForMastery(event.getCastSource())) return;
        if (externallyOwnsProgression(event.getSpellId())) return;

        String discipline = normalizeSchool(event.getSchoolType().getId());
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

    static boolean externallyOwnsProgression(String spellId) {
        return spellId != null && spellId.startsWith(BLACK_ARCANA_PREFIX);
    }

    static String normalizeSchool(ResourceLocation schoolId) {
        return schoolId.getNamespace().equals("irons_spellbooks")
            ? schoolId.getPath()
            : schoolId.getNamespace() + "/" + schoolId.getPath();
    }
}
