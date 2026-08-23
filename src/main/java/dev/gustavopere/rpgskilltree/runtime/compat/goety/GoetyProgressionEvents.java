package dev.gustavopere.rpgskilltree.runtime.compat.goety;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.api.magic.ISummonSpell;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.events.spell.BlockMagicEvent;
import com.Polarice3.Goety.common.events.spell.CastMagicEvent;
import com.Polarice3.Goety.common.events.spell.CastingMagicEvent;
import com.Polarice3.Goety.common.events.spell.ChangeSoulEnergyEvent;
import com.Polarice3.Goety.common.events.spell.TouchMagicEvent;
import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.GoetySpellClassifier;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Optional Goety adapter. Spell callbacks only create short-lived candidates;
 * mastery is persisted when Goety confirms the action through a Soul Energy loss.
 */
public final class GoetyProgressionEvents {
    private static final Map<UUID, PendingCast> PENDING = new HashMap<>();
    private static final long MAX_CONFIRMATION_AGE_TICKS = 1L;

    private GoetyProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCast(CastMagicEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        queue(player, event.getSpell(), resolveWand(player, event.getSpell()), "cast");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCasting(CastingMagicEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        queue(player, event.getSpell(), event.getUseItem(), "channel");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTouch(TouchMagicEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        queue(player, event.getSpell(), event.getUseItem(), "touch");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlock(BlockMagicEvent event) {
        if (!(event.getCaster() instanceof ServerPlayer player)) return;
        queue(player, event.getSpell(), resolveWand(player, event.getSpell()), "block");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSoulSpent(ChangeSoulEnergyEvent.Loss event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        PendingCast pending = PENDING.remove(player.getUUID());
        if (pending == null || player instanceof FakePlayer || player.isCreative()) return;
        long age = player.level().getGameTime() - pending.gameTime();
        if (age < 0L || age > MAX_CONFIRMATION_AGE_TICKS) return;

        SpellAction candidate = pending.action();
        SpellAction confirmed = new SpellAction(
            candidate.origin(),
            candidate.provider(),
            candidate.spellId(),
            candidate.discipline(),
            candidate.tags(),
            Math.max(0, event.getSoulChange())
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forGoety(confirmed));
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PENDING.remove(event.getEntity().getUUID());
    }

    private static void queue(ServerPlayer player, ISpell spell, ItemStack wandStack, String source) {
        if (player instanceof FakePlayer || player.isCreative() || spell == null) return;
        if (!isUsableWand(player, wandStack, spell)) return;

        ItemStack focus = IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty()) return;
        ResourceLocation focusId = BuiltInRegistries.ITEM.getKey(focus.getItem());
        if (focusId == null) return;

        List<String> types = spell.getSpellTypes().stream().map(SpellType::getBaseName).toList();
        Set<String> tags = GoetySpellClassifier.classify(types, spell instanceof ISummonSpell);
        SpellAction action = new SpellAction(
            new ActionOrigin("goety:" + source, 0),
            "goety",
            focusId.toString(),
            GoetySpellClassifier.primaryDiscipline(tags),
            tags,
            0
        );
        PENDING.put(player.getUUID(), new PendingCast(action, player.level().getGameTime()));
    }

    private static boolean isUsableWand(ServerPlayer player, ItemStack stack, ISpell spell) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof IWand wand)) return false;
        ISpell heldSpell = wand.getSpell(stack);
        if (heldSpell == null) return false;
        boolean sameSpell = heldSpell == spell || heldSpell.getClass().equals(spell.getClass());
        return sameSpell && !wand.cannotCast(player, stack, spell);
    }

    private static ItemStack resolveWand(ServerPlayer player, ISpell spell) {
        ItemStack using = player.getUseItem();
        if (isMatchingWand(using, spell)) return using;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (isMatchingWand(stack, spell)) return stack;
        }
        return ItemStack.EMPTY;
    }

    private static boolean isMatchingWand(ItemStack stack, ISpell spell) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof IWand wand)) return false;
        ISpell heldSpell = wand.getSpell(stack);
        return heldSpell != null && (heldSpell == spell || heldSpell.getClass().equals(spell.getClass()));
    }

    private record PendingCast(SpellAction action, long gameTime) {}
}
