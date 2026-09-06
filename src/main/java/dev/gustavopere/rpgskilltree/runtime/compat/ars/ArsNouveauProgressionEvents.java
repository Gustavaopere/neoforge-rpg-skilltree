package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import com.hollingsworth.arsnouveau.api.event.FamiliarSummonEvent;
import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SpellResolveEvent;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.ArsCompositionClassifier;
import dev.gustavopere.rpgskilltree.core.ArsNativeProgressionPolicy;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.client.ClientProgressionState;
import dev.gustavopere.rpgskilltree.runtime.compat.MagicAccessRuntime;
import java.util.List;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;

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
        if (player instanceof FakePlayer || event.context == null) return;
        SpellAction action = actionFor(event.spell);
        if (action == null) return;
        armCausalAward(event.context, action);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpellResolved(SpellResolveEvent.Post event) {
        if (!(event.shooter instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer || event.context == null) return;
        SpellAction action = claimResolved(event.context);
        if (action == null) return;
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forArs(action));
    }

    @SubscribeEvent
    public static void onMaxMana(MaxManaCalcEvent event) {
        ProgressionState state = progressionFor(event.getEntity());
        if (state == null) return;
        event.setMax(ArsNativeProgressionPolicy.adjustMaxMana(event.getMax(), state.passiveNodes(), state.classProgression().isUnlocked("sorcerer")));
    }

    @SubscribeEvent
    public static void onManaRegen(ManaRegenCalcEvent event) {
        ProgressionState state = progressionFor(event.getEntity());
        if (state == null) return;
        event.setRegen(ArsNativeProgressionPolicy.adjustManaRegen(event.getRegen(), state.passiveNodes(), state.classProgression().isUnlocked("sorcerer")));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFamiliarSummon(FamiliarSummonEvent event) {
        if (!(event.owner instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer || player.isCreative()) return;
        ProgressionState state = PlayerProgressionRuntime.get(player);
        if (ArsNativeProgressionPolicy.canSummonFamiliar(state.passiveNodes())) return;
        event.setCanceled(true);
        player.displayClientMessage(Component.literal("Entre no ramo de Invocação da Árvore RPG para vincular familiares do Ars Nouveau."), true);
    }

    static SpellAction actionFor(Spell spell) {
        if (spell == null || spell.isEmpty()) return null;
        List<String> glyphIds = spell.serializeRecipe().stream().map(ResourceLocation::toString).toList();
        if (glyphIds.isEmpty()) return null;
        Set<String> tags = ArsCompositionClassifier.classify(glyphIds);
        String signature = String.join(">", glyphIds);
        return new SpellAction(
            new ActionOrigin("ars:spellcast", 0),
            "ars",
            signature,
            "composition",
            tags,
            Math.max(0, spell.getCost())
        );
    }

    static void armCausalAward(SpellContext context, SpellAction action) {
        if (context == null || action == null) return;
        context.getOrCreateAttachment(ArsMasteryCausalAward.ID, ArsMasteryCausalAward.arm(action));
    }

    static SpellAction claimResolved(SpellContext context) {
        if (context == null) return null;
        ArsMasteryCausalAward causalAward = causalAwardFor(context);
        return causalAward == null ? null : causalAward.claimResolved();
    }

    static ArsMasteryCausalAward causalAwardFor(SpellContext context) {
        SpellContext current = context;
        while (current != null) {
            ArsMasteryCausalAward award = current.getAttachment(ArsMasteryCausalAward.ID);
            if (award != null) return award;
            current = current.getPreviousContext();
        }
        return null;
    }

    private static ProgressionState progressionFor(LivingEntity entity) {
        if (!(entity instanceof Player) || entity instanceof FakePlayer) return null;
        if (entity instanceof ServerPlayer serverPlayer) return PlayerProgressionRuntime.get(serverPlayer);
        if (entity.level().isClientSide()) return ClientProgressionState.get();
        return null;
    }
}
