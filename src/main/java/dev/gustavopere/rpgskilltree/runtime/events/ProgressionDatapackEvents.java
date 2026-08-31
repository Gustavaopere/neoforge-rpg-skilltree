package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

/** Reconciles derived progression after the server has completed a full datapack reload. */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID)
public final class ProgressionDatapackEvents {
    private ProgressionDatapackEvents() {}

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        Objects.requireNonNull(event, "event");
        if (!shouldReconcile(event.getPlayer() != null)) return;
        reconcileRelevant(event.getRelevantPlayers(), PlayerProgressionRuntime::reconcilePlayerState);
    }

    static boolean shouldReconcile(boolean targetedPlayerSync) {
        return !targetedPlayerSync;
    }

    static <T> void reconcileRelevant(Stream<T> relevant, Consumer<T> reconcile) {
        Objects.requireNonNull(relevant, "relevant");
        Objects.requireNonNull(reconcile, "reconcile");
        relevant.forEach(reconcile);
    }
}
