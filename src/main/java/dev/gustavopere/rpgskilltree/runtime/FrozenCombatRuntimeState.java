package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CrossbowCadenceService;
import dev.gustavopere.rpgskilltree.core.FistSequenceService;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.FrozenMartialOffenseService;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/** Server owner for transient A0051-A0100 state; cooldowns and action claims outlive transient clears. */
public final class FrozenCombatRuntimeState {
    private static final CrossbowCadenceService CROSSBOW = new CrossbowCadenceService();
    private static final FistSequenceService FIST = new FistSequenceService();
    private static final FrozenMartialOffenseService OFFENSE = new FrozenMartialOffenseService();
    private static final Map<ItemStack, String> STACK_IDENTITIES = new IdentityHashMap<>();
    private static final AtomicLong NEXT_STACK_ID = new AtomicLong();

    private FrozenCombatRuntimeState() {}

    public static CrossbowCadenceService crossbow() { return CROSSBOW; }
    public static FistSequenceService fist() { return FIST; }
    public static FrozenMartialOffenseService offense() { return OFFENSE; }

    public static FrozenCombatPerkRanks ranks(ProgressionState progression) {
        Objects.requireNonNull(progression);
        return FrozenCombatPerkNodeBinding.ranks(progression.passiveNodes());
    }

    public static FrozenCombatPerkRanks ranks(ServerPlayer player) {
        return ranks(PlayerProgressionRuntime.get(player));
    }

    public static synchronized String stackIdentity(ServerPlayer owner, ItemStack stack) {
        Objects.requireNonNull(owner);
        Objects.requireNonNull(stack);
        if (stack.isEmpty()) return "";
        return STACK_IDENTITIES.computeIfAbsent(stack, ignored ->
            owner.getUUID() + "/stack/" + NEXT_STACK_ID.incrementAndGet());
    }

    public static synchronized void clearTransient(ServerPlayer player) {
        String actorId = player.getUUID().toString();
        CROSSBOW.clearTransient(actorId);
        FIST.clearTransient(actorId);
        STACK_IDENTITIES.entrySet().removeIf(entry -> entry.getValue().startsWith(actorId + "/"));
    }
}
