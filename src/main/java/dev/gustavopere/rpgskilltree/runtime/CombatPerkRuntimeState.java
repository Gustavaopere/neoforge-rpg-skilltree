package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkState;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/** Server-runtime owner for combat resources that must never be persisted in ProgressionState. */
public final class CombatPerkRuntimeState {
    private static final NotionCombatPerkState STATE = new NotionCombatPerkState();

    private CombatPerkRuntimeState() {}

    public static NotionCombatPerkState state() {
        return STATE;
    }

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

    public static void clear(ServerPlayer player) {
        STATE.clear(actorId(player));
    }
}
