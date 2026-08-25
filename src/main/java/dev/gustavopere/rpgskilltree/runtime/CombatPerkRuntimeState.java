package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalTargetDebuffService;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkState;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/** Server-runtime owner for combat resources that must never be persisted in ProgressionState. */
public final class CombatPerkRuntimeState {
    private static final NotionCombatPerkState STATE = new NotionCombatPerkState();
    private static final CanonicalTargetDebuffService TARGET_DEBUFFS = new CanonicalTargetDebuffService();

    private CombatPerkRuntimeState() {}

    public static NotionCombatPerkState state() { return STATE; }
    public static CanonicalTargetDebuffService targetDebuffs() { return TARGET_DEBUFFS; }

    public static CombatPerkRanks ranks(ProgressionState progression) {
        Objects.requireNonNull(progression);
        return CombatPerkNodeBinding.ranks(progression.passiveNodes());
    }

    public static CombatPerkRanks ranks(ServerPlayer player) {
        Objects.requireNonNull(player);
        return ranks(PlayerProgressionRuntime.get(player));
    }

    public static String actorId(ServerPlayer player) {
        Objects.requireNonNull(player);
        return player.getUUID().toString();
    }

    /** Entity-bound teardown for death/respawn/clone/dimension without resetting exploit guards. */
    public static void clearTransientPreservingGuards(ServerPlayer player) {
        CanonicalCombatRuntimeState.clearTransientPreservingGuards(player);
        String actorId = actorId(player);
        TARGET_DEBUFFS.clearSource(actorId);
        STATE.clearTransientPreservingGuards(actorId);
    }

    /** Full session teardown. */
    public static void clear(ServerPlayer player) {
        CanonicalCombatRuntimeState.clear(player);
        String actorId = actorId(player);
        TARGET_DEBUFFS.clearSource(actorId);
        STATE.clear(actorId);
    }
}
