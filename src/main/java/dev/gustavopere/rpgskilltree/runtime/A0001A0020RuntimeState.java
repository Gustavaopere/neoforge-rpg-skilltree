package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0001A0020CriticalService;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkState;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.server.level.ServerPlayer;

/** Server-only transient owner for the closed A0001-A0020 batch. */
public final class A0001A0020RuntimeState {
    private static final NotionCombatPerkState STATE = new NotionCombatPerkState();
    private static final A0001A0020CriticalService CRITICAL = new A0001A0020CriticalService(
        () -> ThreadLocalRandom.current().nextDouble(), 30_000L, 8_192);

    private A0001A0020RuntimeState() {}

    public static CombatPerkRanks ranks(ServerPlayer player) {
        return CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());
    }

    public static NotionCombatPerkState state() { return STATE; }
    public static A0001A0020CriticalService critical() { return CRITICAL; }
    public static String actorId(ServerPlayer player) { return player.getUUID().toString(); }

    public static void clear(ServerPlayer player) {
        String actorId = actorId(player);
        STATE.clearTransient(actorId);
        CRITICAL.clearActor(actorId);
    }

    public static void clearAll() {
        STATE.clearAll();
        CRITICAL.clear();
    }
}
