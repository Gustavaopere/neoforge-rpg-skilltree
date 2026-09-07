package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0081A0100DefenseState;
import dev.gustavopere.rpgskilltree.core.BloodThirstService;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;

/** Server-only owner for transient state introduced by the canonical A0081-A0100 batch. */
public final class A0081A0100RuntimeState {
    private static final SustainResolver SUSTAIN = new SustainResolver();
    private static final CombatRecoveryService RECOVERY = new CombatRecoveryService();
    private static final A0081A0100DefenseState DEFENSE = new A0081A0100DefenseState();
    private static final Map<String, Map<String, Integer>> LAST_EFFECTIVE_RANKS = new HashMap<>();

    // A0087 is intentionally fail-closed until a canonical Cold Sweat body provider can prove
    // both mandatory receipts (metabolic heat and vanilla exhaustion) for the same activity.
    private static final BloodThirstService BLOOD_THIRST = new BloodThirstService(null);

    private A0081A0100RuntimeState() {}

    public static SustainResolver sustain() {
        return SUSTAIN;
    }

    public static CombatRecoveryService recovery() {
        return RECOVERY;
    }

    public static A0081A0100DefenseState defense() {
        return DEFENSE;
    }

    public static BloodThirstService bloodThirst() {
        return BLOOD_THIRST;
    }

    /**
     * Masks structurally unavailable combat nodes and clears transient sustain/recovery/defense
     * state whenever the effective rank snapshot changes. This is the A0081-A0100 counterpart of
     * A0061A0080RuntimeState and covers purchase/respec, rank loss, dependency loss and rules or
     * provider-availability changes without reviving an old reserve/window afterward.
     */
    public static synchronized CombatPerkRanks ranks(ServerPlayer player) {
        CombatPerkRanks persisted = CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());
        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(persisted);
        String actor = actorId(player);
        Map<String, Integer> current = effective.ranks();
        Map<String, Integer> previous = LAST_EFFECTIVE_RANKS.put(actor, current);
        if (previous != null && !previous.equals(current)) {
            SUSTAIN.clearActor(actor);
            RECOVERY.clearActor(actor);
            DEFENSE.clearActor(actor);
            BLOOD_THIRST.clearActor(actor);
            A0081A0090ProviderHitRegistry.clearActor(actor);
        }
        return effective;
    }

    public static String actorId(ServerPlayer player) {
        return player.getUUID().toString();
    }

    public static synchronized void clear(ServerPlayer player) {
        String actor = actorId(player);
        LAST_EFFECTIVE_RANKS.remove(actor);
        SUSTAIN.clearActor(actor);
        RECOVERY.clearActor(actor);
        DEFENSE.clearActor(actor);
        BLOOD_THIRST.clearActor(actor);
        A0081A0090ProviderHitRegistry.clearActor(actor);
    }

    public static synchronized void clearAll() {
        LAST_EFFECTIVE_RANKS.clear();
        SUSTAIN.clearAll();
        RECOVERY.clearAll();
        DEFENSE.clearAll();
        BLOOD_THIRST.clearAll();
        A0081A0090ProviderHitRegistry.clearAll();
    }
}
