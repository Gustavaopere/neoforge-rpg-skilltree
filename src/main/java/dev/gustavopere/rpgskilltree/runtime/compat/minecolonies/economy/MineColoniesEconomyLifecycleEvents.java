package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.eventbus.events.colony.ColonyDeletedModEvent;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyEvents;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** Public-provider lifecycle hooks needed to keep native colony IDs from inheriting old money. */
public final class MineColoniesEconomyLifecycleEvents {
    private static final AtomicBoolean INSTALLED = new AtomicBoolean();

    private MineColoniesEconomyLifecycleEvents() {}

    public static void install() {
        if (!INSTALLED.compareAndSet(false, true)) {
            return;
        }
        try {
            IMinecoloniesAPI.getInstance().getEventBus().subscribe(
                ColonyDeletedModEvent.class,
                MineColoniesEconomyLifecycleEvents::onColonyDeleted
            );
            NeoForge.EVENT_BUS.register(ColonyEconomyEvents.class);
        } catch (RuntimeException | LinkageError failure) {
            INSTALLED.set(false);
            throw failure;
        }
    }

    private static void onColonyDeleted(ColonyDeletedModEvent event) {
        if (event == null) {
            return;
        }
        NativeColonyBinding binding = MineColoniesEconomyAdapter.binding(event.getColony()).orElse(null);
        if (binding == null) {
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        ColonyEconomySavedData.get(server).archiveBinding(binding);
    }
}
