package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalBodyTradeoffService;
import dev.gustavopere.rpgskilltree.core.BloodThirstService;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.core.CrossbowCadenceService;
import dev.gustavopere.rpgskilltree.core.FistSequenceService;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.FrozenMartialOffenseService;
import dev.gustavopere.rpgskilltree.core.FrozenMartialTacticsService;
import dev.gustavopere.rpgskilltree.core.FrozenMartialTacticsService.Stance;
import dev.gustavopere.rpgskilltree.core.MartialRhythmService;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.StationaryStateService;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/** Server owner for transient A0051-A0100 state; cooldowns and action claims outlive transient clears. */
public final class FrozenCombatRuntimeState {
    private static final String STANCE_DATA = "rpgskilltree.frozen_martial_stance";
    private static final CrossbowCadenceService CROSSBOW = new CrossbowCadenceService();
    private static final FistSequenceService FIST = new FistSequenceService();
    private static final FrozenMartialOffenseService OFFENSE = new FrozenMartialOffenseService();
    private static final FrozenMartialTacticsService TACTICS = new FrozenMartialTacticsService();
    private static final StationaryStateService STATIONARY = new StationaryStateService();
    // The frozen provider audit found no installed body/metabolism API. A null provider is deliberate fail-closed state.
    private static final CanonicalBodyTradeoffService BODY = new CanonicalBodyTradeoffService(null);
    private static final MartialRhythmService RHYTHM = new MartialRhythmService(BODY);
    private static final SustainResolver SUSTAIN = new SustainResolver();
    private static final CombatRecoveryService RECOVERY = new CombatRecoveryService();
    private static final BloodThirstService BLOOD_THIRST = new BloodThirstService(BODY);
    private static final Map<ItemStack, String> STACK_IDENTITIES = new IdentityHashMap<>();
    private static final AtomicLong NEXT_STACK_ID = new AtomicLong();

    private FrozenCombatRuntimeState() {}

    public static CrossbowCadenceService crossbow() { return CROSSBOW; }
    public static FistSequenceService fist() { return FIST; }
    public static FrozenMartialOffenseService offense() { return OFFENSE; }
    public static FrozenMartialTacticsService tactics() { return TACTICS; }
    public static StationaryStateService stationary() { return STATIONARY; }
    public static CanonicalBodyTradeoffService body() { return BODY; }
    public static MartialRhythmService rhythm() { return RHYTHM; }
    public static SustainResolver sustain() { return SUSTAIN; }
    public static CombatRecoveryService recovery() { return RECOVERY; }
    public static BloodThirstService bloodThirst() { return BLOOD_THIRST; }

    public static synchronized boolean toggleStance(ServerPlayer player, Stance requested) {
        FrozenCombatPerkRanks ranks = ranks(player);
        String actorId = player.getUUID().toString();
        Stance target = TACTICS.stance(actorId) == requested ? Stance.NONE : requested;
        boolean changed = TACTICS.setStance(
            actorId, target, ranks.learned("A0076"), ranks.learned("A0077"),
            Math.multiplyExact(player.level().getGameTime(), 50L));
        if (changed) {
            if (target == Stance.NONE) player.getPersistentData().remove(STANCE_DATA);
            else player.getPersistentData().putString(STANCE_DATA, target.name());
        }
        return changed;
    }

    /** Persistent postures are restored only after their current ranks are revalidated. */
    public static synchronized void revalidateStance(ServerPlayer player) {
        FrozenCombatPerkRanks ranks = ranks(player);
        String actorId = player.getUUID().toString();
        boolean aggressive = ranks.learned("A0076");
        boolean cautious = ranks.learned("A0077");
        TACTICS.revalidateStance(actorId, aggressive, cautious);
        String stored = player.getPersistentData().getString(STANCE_DATA);
        Stance desired;
        try { desired = stored.isBlank() ? Stance.NONE : Stance.valueOf(stored); }
        catch (IllegalArgumentException ignored) { desired = Stance.NONE; }
        if (desired == Stance.AGGRESSIVE && !aggressive || desired == Stance.CAUTIOUS && !cautious) {
            player.getPersistentData().remove(STANCE_DATA);
            return;
        }
        if (desired != Stance.NONE && TACTICS.stance(actorId) == Stance.NONE) {
            TACTICS.setStance(actorId, desired, aggressive, cautious,
                Math.multiplyExact(player.level().getGameTime(), 50L));
        }
    }

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
        TACTICS.clearTransient(actorId);
        STATIONARY.invalidate(actorId);
        RHYTHM.clearTransient(actorId, player.level().getGameTime());
        SUSTAIN.clearTransient(actorId);
        RECOVERY.clearTransient(actorId);
        BLOOD_THIRST.clearTransient(actorId, player.level().getGameTime());
        STACK_IDENTITIES.entrySet().removeIf(entry -> entry.getValue().startsWith(actorId + "/"));
    }
}
