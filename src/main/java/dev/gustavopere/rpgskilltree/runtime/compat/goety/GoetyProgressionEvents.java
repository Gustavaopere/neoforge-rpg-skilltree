package dev.gustavopere.rpgskilltree.runtime.compat.goety;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.api.magic.ISummonSpell;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.events.spell.BlockMagicEvent;
import com.Polarice3.Goety.common.events.spell.CastMagicEvent;
import com.Polarice3.Goety.common.events.spell.CastingMagicEvent;
import com.Polarice3.Goety.common.events.spell.ChangeSoulEnergyEvent;
import com.Polarice3.Goety.common.events.spell.TouchMagicEvent;
import com.Polarice3.Goety.common.items.magic.CommandFocus;
import com.Polarice3.Goety.common.items.magic.OrderFocus;
import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.CombatAction;
import dev.gustavopere.rpgskilltree.core.GoetyCommandAction;
import dev.gustavopere.rpgskilltree.core.GoetySoulPolicy;
import dev.gustavopere.rpgskilltree.core.GoetySpellClassifier;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Optional Goety adapter. Spell callbacks only create short-lived candidates;
 * mastery is persisted when Goety confirms the action through a Soul Energy loss.
 * Soul economics, hostile servant outcomes and servant orders are integrated
 * through Goety/NeoForge public state rather than duplicated progression systems.
 */
public final class GoetyProgressionEvents {
    private static final Map<UUID, PendingCast> PENDING = new HashMap<>();
    private static final Map<UUID, PendingCommand> PENDING_COMMANDS = new HashMap<>();
    private static final long MAX_CONFIRMATION_AGE_TICKS = 1L;
    private static final long MAX_COMMAND_CONFIRMATION_AGE_TICKS = 2L;

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
        if (pending == null || !eligible(player)) return;
        long age = player.level().getGameTime() - pending.gameTime();
        if (age < 0L || age > MAX_CONFIRMATION_AGE_TICKS) return;

        var state = PlayerProgressionRuntime.get(player);
        int adjustedCost = GoetySoulPolicy.adjustedSpellCost(
            state.passiveNodes(),
            state.classProgression(),
            pending.action().tags(),
            Math.max(0, event.getSoulChange())
        );
        event.setSoulChange(adjustedCost);

        SpellAction candidate = pending.action();
        SpellAction confirmed = new SpellAction(
            candidate.origin(),
            candidate.provider(),
            candidate.spellId(),
            candidate.discipline(),
            candidate.tags(),
            adjustedCost
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forGoety(confirmed));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSoulGain(ChangeSoulEnergyEvent.Gain event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        var state = PlayerProgressionRuntime.get(player);
        int adjustedGain = GoetySoulPolicy.adjustedGain(
            state.passiveNodes(),
            state.classProgression(),
            Math.max(0, event.getSoulChange())
        );
        event.setSoulChange(adjustedGain);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServantHostileKill(LivingDeathEvent event) {
        if (event.getEntity().getType().getCategory() != MobCategory.MONSTER) return;
        if (!(event.getSource().getEntity() instanceof IOwned owned)) return;
        if (!(owned.getMasterOwner() instanceof ServerPlayer player) || !eligible(player)) return;

        ResourceLocation targetId = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
        if (targetId == null) return;

        var state = PlayerProgressionRuntime.get(player);
        Set<String> tags = new HashSet<>();
        tags.add("servant_kill");
        tags.add("summoning");
        if (state.classProgression().isUnlocked("necromancer")) tags.add("necromancer");
        if (state.classProgression().isUnlocked("warlock")) tags.add("warlock");

        CombatAction action = new CombatAction(
            new ActionOrigin("goety:servant_kill", 0),
            "goety",
            "servant",
            targetId.toString(),
            Set.copyOf(tags),
            Math.max(1.0D, event.getEntity().getMaxHealth())
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forGoetyServant(action));
    }

    /**
     * NeoForge interaction events fire before the held item performs its action.
     * Record only an intent here; PlayerTickEvent.Post confirms Goety's actual
     * servant state before any mastery is awarded.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCommandEntityIntent(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        if (!(event.getTarget() instanceof LivingEntity target)) return;

        ItemStack wandStack = player.getItemInHand(event.getHand());
        ItemStack focus = commandFocus(wandStack);
        if (focus.isEmpty()) return;

        Set<UUID> servants = commandableEntityServants(player, focus, target);
        if (servants.isEmpty()) return;

        ResourceLocation targetType = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        if (targetType == null) return;
        String mode = focus.getItem() instanceof OrderFocus ? "order_entity" : "command_entity";
        PENDING_COMMANDS.put(player.getUUID(), PendingCommand.entity(
            mode,
            servants,
            target.getUUID(),
            targetType.toString(),
            player.level().getGameTime()
        ));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCommandBlockIntent(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !eligible(player)) return;

        ItemStack wandStack = player.getItemInHand(event.getHand());
        ItemStack focus = commandFocus(wandStack);
        if (focus.isEmpty()) return;

        Map<UUID, BlockPos> expectedPositions = commandableBlockServants(player, focus, event.getPos());
        if (expectedPositions.isEmpty()) return;

        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(player.level().getBlockState(event.getPos()).getBlock());
        if (blockId == null) return;
        String mode = focus.getItem() instanceof OrderFocus ? "order_block" : "command_block";
        PENDING_COMMANDS.put(player.getUUID(), PendingCommand.block(
            mode,
            expectedPositions,
            blockId.toString(),
            player.level().getGameTime()
        ));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        PendingCommand pending = PENDING_COMMANDS.get(player.getUUID());
        if (pending == null) return;

        long age = player.level().getGameTime() - pending.gameTime();
        if (!eligible(player) || age < 0L || age > MAX_COMMAND_CONFIRMATION_AGE_TICKS) {
            PENDING_COMMANDS.remove(player.getUUID());
            return;
        }

        int confirmedServants = pending.targetEntityId() != null
            ? confirmedEntityCommands(player, pending)
            : confirmedBlockCommands(player, pending);
        if (confirmedServants <= 0) {
            if (age >= MAX_COMMAND_CONFIRMATION_AGE_TICKS) {
                PENDING_COMMANDS.remove(player.getUUID());
            }
            return;
        }

        PENDING_COMMANDS.remove(player.getUUID());
        Set<String> tags = new HashSet<>();
        tags.add("confirmed_command");
        tags.add(pending.targetEntityId() != null ? "entity_target" : "block_target");
        tags.add(pending.actionId().startsWith("order_") ? "group_order" : "single_command");
        GoetyCommandAction action = new GoetyCommandAction(
            new ActionOrigin("goety:" + pending.actionId(), 0),
            "goety",
            pending.actionId(),
            pending.targetId(),
            Set.copyOf(tags),
            confirmedServants
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forGoetyCommand(action));
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        PENDING.remove(playerId);
        PENDING_COMMANDS.remove(playerId);
    }

    private static void queue(ServerPlayer player, ISpell spell, ItemStack wandStack, String source) {
        if (!eligible(player) || spell == null) return;
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

    private static ItemStack commandFocus(ItemStack wandStack) {
        if (wandStack == null || wandStack.isEmpty() || !(wandStack.getItem() instanceof IWand)) return ItemStack.EMPTY;
        ItemStack focus = IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty()) return ItemStack.EMPTY;
        return focus.getItem() instanceof CommandFocus || focus.getItem() instanceof OrderFocus ? focus : ItemStack.EMPTY;
    }

    private static Set<UUID> commandableEntityServants(ServerPlayer player, ItemStack focus, LivingEntity target) {
        Set<UUID> result = new LinkedHashSet<>();
        for (LivingEntity entity : selectedServants(focus)) {
            if (!(entity instanceof IServant servant) || !commandableBy(player, entity, servant)) continue;
            if (entity == target) continue;
            if (servant.getCommandPosEntity() == target && servant.getCommandTick() > 0) continue;
            result.add(entity.getUUID());
        }
        return Set.copyOf(result);
    }

    private static Map<UUID, BlockPos> commandableBlockServants(ServerPlayer player, ItemStack focus, BlockPos clicked) {
        Map<UUID, BlockPos> result = new LinkedHashMap<>();
        BlockPos above = clicked.above();
        for (LivingEntity entity : selectedServants(focus)) {
            if (!(entity instanceof IServant servant) || !commandableBy(player, entity, servant)) continue;
            BlockPos expected = null;
            if (servant.canCommandToBlock(player.level(), clicked)) {
                expected = clicked;
            } else if (servant.canCommandToBlock(player.level(), above)) {
                expected = above;
            }
            if (expected == null) continue;
            if (expected.equals(servant.getCommandPos()) && servant.getCommandTick() > 0) continue;
            result.put(entity.getUUID(), expected);
        }
        return Map.copyOf(result);
    }

    private static List<LivingEntity> selectedServants(ItemStack focus) {
        if (focus.getItem() instanceof CommandFocus) {
            LivingEntity servant = CommandFocus.getServant(focus);
            return servant == null ? List.of() : List.of(servant);
        }
        if (focus.getItem() instanceof OrderFocus) {
            return List.copyOf(OrderFocus.getServants(focus));
        }
        return List.of();
    }

    private static boolean commandableBy(ServerPlayer player, LivingEntity entity, IServant servant) {
        return servant.canBeCommanded()
            && servant.getTrueOwner() == player
            && entity.isAlive()
            && entity.distanceTo(player) <= 64.0F;
    }

    private static int confirmedEntityCommands(ServerPlayer player, PendingCommand pending) {
        Entity targetEntity = player.serverLevel().getEntity(pending.targetEntityId());
        if (!(targetEntity instanceof LivingEntity target) || !target.isAlive()) return 0;

        int confirmed = 0;
        for (UUID servantId : pending.servantIds()) {
            Entity entity = player.serverLevel().getEntity(servantId);
            if (!(entity instanceof LivingEntity living) || !(living instanceof IServant servant)) continue;
            if (servant.getTrueOwner() != player) continue;
            if (servant.getCommandPosEntity() == target && servant.getCommandTick() > 0) confirmed++;
        }
        return confirmed;
    }

    private static int confirmedBlockCommands(ServerPlayer player, PendingCommand pending) {
        int confirmed = 0;
        for (Map.Entry<UUID, BlockPos> entry : pending.expectedBlockPositions().entrySet()) {
            Entity entity = player.serverLevel().getEntity(entry.getKey());
            if (!(entity instanceof LivingEntity living) || !(living instanceof IServant servant)) continue;
            if (servant.getTrueOwner() != player) continue;
            if (entry.getValue().equals(servant.getCommandPos()) && servant.getCommandTick() > 0) confirmed++;
        }
        return confirmed;
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

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    private record PendingCast(SpellAction action, long gameTime) {}

    private record PendingCommand(
        String actionId,
        Set<UUID> servantIds,
        UUID targetEntityId,
        Map<UUID, BlockPos> expectedBlockPositions,
        String targetId,
        long gameTime
    ) {
        private PendingCommand {
            servantIds = Set.copyOf(servantIds);
            expectedBlockPositions = Map.copyOf(expectedBlockPositions);
        }

        static PendingCommand entity(
            String actionId,
            Set<UUID> servantIds,
            UUID targetEntityId,
            String targetId,
            long gameTime
        ) {
            return new PendingCommand(actionId, servantIds, targetEntityId, Map.of(), targetId, gameTime);
        }

        static PendingCommand block(
            String actionId,
            Map<UUID, BlockPos> expectedPositions,
            String targetId,
            long gameTime
        ) {
            return new PendingCommand(actionId, expectedPositions.keySet(), null, expectedPositions, targetId, gameTime);
        }
    }
}
