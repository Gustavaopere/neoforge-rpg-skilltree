package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0081A0100DefenseState;
import dev.gustavopere.rpgskilltree.core.BloodThirstService;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import net.minecraft.server.level.ServerPlayer;

/** Server-only owner for transient state introduced by the canonical A0081-A0100 batch. */
public final class A0081A0100RuntimeState {
    private static final SustainResolver SUSTAIN = new SustainResolver();
    private static final CombatRecoveryService RECOVERY = new CombatRecoveryService();
    private static final A0081A0100DefenseState DEFENSE = new A0081A0100DefenseState();

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

    public static CombatPerkRanks ranks(ServerPlayer player) {
        return CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());
    }

    public static String actorId(ServerPlayer player) {
        return player.getUUID().toString();
    }

    public static void clear(ServerPlayer player) {
        String actor = actorId(player);
        SUSTAIN.clearActor(actor);
        RECOVERY.clearActor(actor);
        DEFENSE.clearActor(actor);
        BLOOD_THIRST.clearActor(actor);
    }

    public static void clearAll() {
        SUSTAIN.clearAll();
        RECOVERY.clearAll();
        DEFENSE.clearAll();
        BLOOD_THIRST.clearAll();
    }
}
