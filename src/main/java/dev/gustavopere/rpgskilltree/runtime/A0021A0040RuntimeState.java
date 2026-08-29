package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0021A0040CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import net.minecraft.server.level.ServerPlayer;

/** Server-only transient owner for A0021-A0040; critical RNG remains owned by A0001A0020RuntimeState. */
public final class A0021A0040RuntimeState {
    private static final A0021A0040CombatState STATE = new A0021A0040CombatState();
    private A0021A0040RuntimeState() {}
    public static A0021A0040CombatState state(){return STATE;}
    public static CombatPerkRanks ranks(ServerPlayer player){return CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());}
    public static String actorId(ServerPlayer player){return player.getUUID().toString();}
    public static void clear(ServerPlayer player){STATE.clearActor(actorId(player));}
    public static void clearTarget(String targetId){STATE.clearTarget(targetId);}
    public static void clearAll(){STATE.clearAll();}
}
