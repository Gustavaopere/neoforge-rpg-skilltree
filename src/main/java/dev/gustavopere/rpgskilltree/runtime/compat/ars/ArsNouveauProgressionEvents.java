package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import com.hollingsworth.arsnouveau.api.event.FamiliarSummonEvent;
import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
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
        if (player instanceof FakePlayer) return;
        if (event.spell == null || event.spell.isEmpty()) return;
        List<String> glyphIds = event.spell.serializeRecipe().stream().map(ResourceLocation::toString).toList();
        if (glyphIds.isEmpty()) return;
        Set<String> tags = ArsCompositionClassifier.classify(glyphIds);
        String signature = String.join(">", glyphIds);
        SpellAction action = new SpellAction(new ActionOrigin("ars:spellcast", 0), "ars", signature, "composition", tags, Math.max(0, event.spell.getCost()));
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

    private static ProgressionState progressionFor(LivingEntity entity) {
        if (!(entity instanceof Player) || entity instanceof FakePlayer) return null;
        if (entity instanceof ServerPlayer serverPlayer) return PlayerProgressionRuntime.get(serverPlayer);
        if (entity.level().isClientSide()) return ClientProgressionState.get();
        return null;
    }
}
